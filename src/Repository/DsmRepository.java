package Repository;

import Model.Dsm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DsmRepository {
	
	public void saveDsm(ArrayList<Dsm> dsm) throws SQLException {
		String url = "jdbc:postgresql://localhost:5432/geor";
		String user = "postgres";
		String password = ""; //password 입력
		try
		{
			Connection connect = null;
			connect = DriverManager.getConnection(url, user, password);
			
			if(connect != null) {
				System.out.println("Connection successful!!");
			}
			else {
				throw new SQLException("no connection...");
			}
			
			connect.setAutoCommit(false);
			
			// sql문
			String sql = "INSERT INTO DSM VALUES(?, ?, ?)";
			PreparedStatement ps = connect.prepareStatement(sql);
			
			for(Dsm temp : dsm){
				ps.setDouble(1, temp.getX());
				ps.setDouble(2, temp.getY());
				ps.setDouble(3, temp.getZ());
				ps.addBatch();
				ps.clearParameters();
			}
			
			ps.executeBatch();
			ps.clearParameters(); //Batch 초기화
			connect.commit();
			
		} catch (SQLException ex) {
			throw ex;
		}
	}
}
