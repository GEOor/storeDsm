package Service;

import Model.Dsm;
import Repository.DsmRepository;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DsmService {

    private final DsmRepository dsmRepository;

    public DsmService() {
        dsmRepository = new DsmRepository();
    }

    public void save() {
        dsmRepository.save(getDsmList());
    }

    /**
     * DSM 파일들에 대한 read stream 생성
     */
    private List<Dsm> getDsmList() {
        File[] files = findDsm();
        List<Dsm> dsmList = new ArrayList<>();
        for (File file : files) {
            try {
                dsmList.add(new Dsm(file));
            } catch (FileNotFoundException e) {
                System.err.printf("error : %s\n", file.getName());
                e.printStackTrace();
            }
        }
        return dsmList;
    }

    /**
     * 지정한 경로 내의 모든 DSM 파일 가져옴
     */
    private File[] findDsm() {
        String path = "src/Files";
        String extension = "xyz";
        File directory = new File(path);
        return directory.listFiles((dir, name) -> name.endsWith(extension));
    }
}
