package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Dsm 파일에 대한 ReadStream 을 관리하는 도메인
 */
public class Dsm {

    private final File dsm;
    private final BufferedReader reader;

    public Dsm(File dsm) throws FileNotFoundException {
        this.dsm = dsm;
        reader = new BufferedReader(new FileReader(dsm));
    }

    public BufferedReader getReader() {
        return reader;
    }

    public String getDsmName() {
        return dsm.getName();
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
