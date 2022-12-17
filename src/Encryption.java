import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Encryption {

    void frame(String title) {
        JFrame frame = new JFrame(title);
        frame.setBounds(300, 130, 600, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JLabel jLabel1 = new JLabel("Внесите в строку ниже путь к файлу с исходным текстом");
        JTextField textField1 = new JTextField(40);
        JLabel jLabel2 = new JLabel("Внесите в строку ниже ключ от 1 до 73");
        JTextField textField2 = new JTextField(10);
        JButton jButton = new JButton("Выбрать");

        JPanel jPanel1 = new JPanel();
        JPanel jPanel2 = new JPanel();
        JPanel jPanel3 = new JPanel();
        JPanel jPanel4 = new JPanel();
        JPanel jPanel5 = new JPanel();

        jPanel1.setLayout(new FlowLayout());
        jPanel2.setLayout(new FlowLayout());
        jPanel3.setLayout(new FlowLayout());
        jPanel4.setLayout(new FlowLayout());
        jPanel5.setLayout(new FlowLayout());

        jPanel1.add(jLabel1);
        jPanel2.add(textField1);

        jPanel3.add(jLabel2);
        jPanel4.add(textField2);

        jPanel5.add(jButton);

        frame.add(jPanel1);
        frame.add(jPanel2);
        frame.add(jPanel3);
        frame.add(jPanel4);
        frame.add(jPanel5);

        frame.setVisible(true);


        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Path path;
                int key;
                if (!(Paths.get(textField1.getText().trim()).toFile().isFile())) {
                    jLabel1.setText("Введен некорректный путь к файлу с текстом");
                } else {
                    path = Path.of(textField1.getText().trim());
                    if (Integer.parseInt(textField2.getText().trim()) < 1 || Integer.parseInt(textField2.getText().trim()) > 73) {
                        jLabel2.setText("Введите ключ от 1 до 73 включительно");
                    } else {
                        key = Integer.parseInt(textField2.getText().trim());
                        encryptionToFile(path, key);
                        frame.setVisible(false);
                        frameFinalEncryption();

                    }
                }
            }
        });
    }

    private void encryptionToFile(Path path, int key) {

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

                        if ((arrInFile.get(i).equals(arrAll.get(j)) && (j + key) >= arrAll.size())) {
                            arrCode.add(i, arrAll.get(j + key - arrAll.size()));
                        } else if (arrInFile.get(i).equals(arrAll.get(j))) {
                            arrCode.add(i, arrAll.get(j + key));
                        }
                    }
                }
            }

            Writer writer = new FileWriter("C:\\Java\\MyProject\\CryptoSwing\\src\\encryptionFile.txt");
            for (Character ch : arrCode) {
                writer.write(ch);
            }
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.getStackTrace();
        }

    }

    private static void frameFinalEncryption() {
        JFrame frame3 = new JFrame("Выполнено");
        frame3.setBounds(300, 130, 600, 150);
        frame3.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame3.setLayout(new BoxLayout(frame3.getContentPane(), BoxLayout.Y_AXIS));

        JLabel jLabel = new JLabel("Шифр записан в файл: C:\\Java\\MyProject\\CryptoSwing\\src\\encryptionFile.txt");
        JButton jButton = new JButton("Закрыть");

        JPanel jPanel1 = new JPanel();
        JPanel jPanel2 = new JPanel();
        jPanel1.setLayout(new FlowLayout());
        jPanel2.setLayout(new FlowLayout());

        jPanel1.add(jLabel);
        jPanel2.add(jButton);

        frame3.add(jPanel1);
        frame3.add(jPanel2);

        frame3.setVisible(true);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}



