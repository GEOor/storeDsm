import Service.DsmService;
import java.sql.SQLException;

public class Main {
    
    private static DsmService dsmService = new DsmService();
    private static String rootPath = "src/Files";
    
    public static void main(String[] args) {
    
        long startTime = System.currentTimeMillis();
        DsmService dsmService = new DsmService();
        dsmService.save();

        long endTime = System.currentTimeMillis();
        System.out.println(String.format("코드 실행 시간: %20dms", endTime - startTime));
    }
}
