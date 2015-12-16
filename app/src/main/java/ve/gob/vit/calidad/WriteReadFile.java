package ve.gob.vit.calidad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.os.Environment;

/**
 * Created by willicab on 14/12/15.
 */
public class WriteReadFile {
    public static String uri = Environment.getExternalStorageDirectory().getAbsolutePath() + "/calidad_report.txt";

    public static void writeToFile(String data) {
        try {
            File myFile = new File(uri);
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile,  true);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            myOutWriter.close();
            fOut.close();
        } catch (Exception e) {
            //
        }
    }

    public static String readFromFile(String path) {
        try {
            File myFile = new File(path);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(fIn));
            String aDataRow = "";
            String aBuffer = "";
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer += aDataRow + "\n";
            }
            myReader.close();
            return aBuffer;
        } catch (Exception e) {
            return e.toString();
        }
    }

    public static void removeFile(String path) {
        File file = new File(uri);
        file.delete();
    }
}
