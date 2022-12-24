import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Decoding extends CryptoUtils {
    @Override
    void execute(String title, CryptoUtils cryptoUtils) {
        super.execute(title, cryptoUtils);
    }

    public void crypToFile(Path path, int key) {
        String allStr = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя.,\":-!? ";
        char[] allChar = allStr.toCharArray();
        ArrayList<Character> arrAll = new ArrayList<>();
        for (char a : allChar) {
            arrAll.add(a);
        }

        ArrayList<Character> arrInFile = new ArrayList<>();
        try (FileReader fileReader = new FileReader(String.valueOf(path));
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while (bufferedReader.ready()) {
                char a = (char) bufferedReader.read();
                arrInFile.add(a);
            }

            ArrayList<Character> arrCode = new ArrayList<>();

            for (int i = 0; i < arrInFile.size(); i++) {
                if (!arrAll.contains(arrInFile.get(i))) {
                    arrCode.add(i, arrInFile.get(i));
                } else {
                    for (int j = 0; j < arrAll.size(); j++) {
                        if (arrAll.get(j).equals(arrInFile.get(i)) && (j - key) < 0) {
                            arrCode.add(i, arrAll.get(j - key + arrAll.size()));
                        } else if (arrAll.get(j).equals(arrInFile.get(i))) {
                            arrCode.add(i, arrAll.get(j - key));
                        }
                    }
                }
            }

            Writer writer = new FileWriter("C:\\Java\\MyProject\\CryptoSwing\\src\\decodingFile.txt");
            for (Character ch : arrCode) {
                writer.write(ch);
            }
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}





