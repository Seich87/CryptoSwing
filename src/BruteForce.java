import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class BruteForce {
    void frame(String title) {
        JFrame frame = new JFrame(title);
        frame.setBounds(300, 130, 600, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JLabel jLabel = new JLabel("Внесите в строку ниже путь к файлу с зашифрованным текстом");
        JTextField textField = new JTextField(40);
        JButton jButton = new JButton("Выбрать");

        JPanel jPanel1 = new JPanel();
        JPanel jPanel2 = new JPanel();
        JPanel jPanel3 = new JPanel();

        jPanel1.setLayout(new FlowLayout());
        jPanel2.setLayout(new FlowLayout());
        jPanel3.setLayout(new FlowLayout());


        jPanel1.add(jLabel);
        jPanel2.add(textField);

        jPanel3.add(jButton);

        frame.add(jPanel1);
        frame.add(jPanel2);
        frame.add(jPanel3);

        frame.setVisible(true);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Path path;
                if (!(Paths.get(textField.getText().trim()).toFile().isFile())) {
                    jLabel.setText("Введен некорректный путь к файлу с зашифрованным текстом");
                } else {
                    path = Path.of(textField.getText().trim());
                    bruteForce(path);
                    frame.setVisible(false);

                }
            }

        });
    }

    private void bruteForce(Path path) {
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

            for (int k = 1; k < arrAll.size(); k++) {
                ArrayList<Character> arrCode = arrCodeAdd(arrAll, arrInFile, k);
                if (textCheck(arrCode)) {
                    Writer writer = new FileWriter("C:\\Java\\MyProject\\CryptoSwing\\src\\BruteForceFile.txt");
                    for (Character ch : arrCode) {
                        writer.write(ch);
                    }
                    writer.flush();
                    writer.close();
                    frameFinalBruteForce(k);
                } else {
                    arrCode.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ArrayList<Character> arrCodeAdd(ArrayList<Character> arrAll, ArrayList<Character> arrInFile, int k) {
        ArrayList<Character> arrCode = new ArrayList<>();
        for (int i = 0; i < arrInFile.size(); i++) {
            if (!arrAll.contains(arrInFile.get(i))) {
                arrCode.add(i, arrInFile.get(i));
            } else {
                for (int j = 0; j < arrAll.size(); j++) {
                    if (arrAll.get(j).equals(arrInFile.get(i)) && (j - k) < 0) {
                        arrCode.add(i, arrAll.get(j - k + arrAll.size()));
                    } else if (arrAll.get(j).equals(arrInFile.get(i))) {
                        arrCode.add(i, arrAll.get(j - k));
                    }
                }
            }
        }
        return arrCode;
    }


    private boolean textCheck(ArrayList<Character> arrCode) {
        return point(arrCode) && comma(arrCode) && exclamationMark(arrCode) && questionMark(arrCode);
    }


    private boolean point(ArrayList<Character> arrCode) {
        if (arrCode.contains('.')) {
            return (arrCode.get(arrCode.indexOf('.') + 1).equals(' ') || arrCode.get(arrCode.indexOf('.') + 1).equals('.') || arrCode.get(arrCode.indexOf('.') + 1).equals(',') || arrCode.get(arrCode.indexOf('.') + 2).equals('\n'));
        }
        return true;
    }

    private boolean comma(ArrayList<Character> arrCode) {
        if (arrCode.contains(',')) {
            return (arrCode.get(arrCode.indexOf(',') + 1).equals(' '));
        }
        return true;
    }

    private boolean exclamationMark(ArrayList<Character> arrCode) {
        if (arrCode.contains('!')) {
            return (arrCode.get(arrCode.indexOf('!') + 1).equals(' ') || arrCode.get(arrCode.indexOf('!') + 2).equals('\n'));
        }
        return true;
    }

    private boolean questionMark(ArrayList<Character> arrCode) {
        if (arrCode.contains('?')) {
            return (arrCode.get(arrCode.indexOf('?') + 1).equals(' ') || arrCode.get(arrCode.indexOf('?') + 2).equals('\n'));
        }
        return true;
    }

    private void frameFinalBruteForce(int k) {
        JFrame frame3 = new JFrame("Выполнено");
        frame3.setBounds(300, 130, 600, 150);
        frame3.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame3.setLayout(new BoxLayout(frame3.getContentPane(), BoxLayout.Y_AXIS));

        JLabel jLabel1 = new JLabel("Расшифрованный текст записан в файл: C:\\Java\\MyProject\\CryptoSwing\\src\\BruteForceFile.txt");
        JLabel jLabel2 = new JLabel("Ключ шифрования: " + k);

        JButton jButton = new JButton("Закрыть");

        JPanel jPanel1 = new JPanel();
        JPanel jPanel2 = new JPanel();
        JPanel jPanel3 = new JPanel();

        jPanel1.setLayout(new FlowLayout());
        jPanel2.setLayout(new FlowLayout());
        jPanel3.setLayout(new FlowLayout());

        jPanel1.add(jLabel1);
        jPanel2.add(jLabel2);
        jPanel3.add(jButton);

        frame3.add(jPanel1);
        frame3.add(jPanel2);
        frame3.add(jPanel3);

        frame3.setVisible(true);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}



