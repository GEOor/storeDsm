package Service;

import Model.Dsm;
import Repository.DsmRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DsmService {
	
	private File file;
	private ArrayList<Dsm> arr = new ArrayList<>();
	private DsmRepository dsmRepository = new DsmRepository();
	
	public void run(String pathName) throws SQLException{
		// 파일 객체 생성
		file = new File(pathName);
		setArr();
	}
	
	private void setArr() throws SQLException {
		try{
			//입력 스트림 생성
			FileReader file_reader = new FileReader(file);
			
			String temp = "";
			double x = 0.0, y = 0.0, z = 0.0;
			int cur = 0, flag = 0, cnt = 0;
			
			while((cur = file_reader.read()) != -1){
				char c = (char)cur;
				if(('0' <= c && c <= '9') || c == '.') {
					temp += c;
				}
				else{
					if(temp.equals("")) {
						continue;
					}
					if(flag == 0) {
						x = Double.parseDouble(temp);
					}
					else if(flag == 1){
						y = Double.parseDouble(temp);
					}
					else {
						z = Double.parseDouble(temp);
						arr.add(new Dsm(x, y, z));
						cnt++;
						if(cnt >= 1000000) { // heap overFlow 발생하기 때문에 100만 단위로 끊어줌
							dsmRepository.saveDsm(arr);
							// printDsm(arr);
							cnt = 0;
							arr.clear();
						}
					}
					flag++;
					flag %= 3;
					temp = "";
				}
			}
			// 마지막 남은 좌표들 한번에 저장
			dsmRepository.saveDsm(arr);
			arr.clear(); // clear 해줘야지 추후 여러개의 dsm 파일 저장 시 오류 없이 가능
			file_reader.close();
			
		} catch (FileNotFoundException e) {
			e.getStackTrace();
		} catch(IOException e){
			e.getStackTrace();
		}
	}
	
	/**
	 * test용 x,y,z 찍어보는 함수
	 * @param dsm
	 */
	void printDsm(ArrayList<Dsm>dsm){
		for(Dsm temp : dsm) {
			System.out.println(temp.getX() + " " + temp.getY() + " " + temp.getZ());
		}
	}
	
}
