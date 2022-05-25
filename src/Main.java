import Service.DsmService;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        DsmService dsmService = new DsmService();
        dsmService.save();
    }
}
