import Service.DsmService;

import java.io.IOException;
import java.nio.file.*;
import java.sql.SQLException;

public class Main {
    
    private static DsmService dsmService = new DsmService();
    private static String rootPath = "src/Files";
    
    public static void main(String[] args) throws SQLException {
    
        long startTime = System.currentTimeMillis();
        
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(rootPath))) {
            for (Path file: stream) {
                // System.out.println(file.getFileName());
                dsmService.run(rootPath + "/" + file.getFileName().toString());
            }
        } catch (IOException | DirectoryIteratorException ex) {
            System.err.println(ex);
        }
    
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("코드 실행 시간: %20dms", endTime - startTime));
    }
}
