import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class StaticAnalyze {

    public void statAnalyze(String title) {
        JFrame frame = new JFrame(title);
        frame.setBounds(300, 130, 800, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JLabel jLabel1 = new JLabel("Внесите в строку ниже путь к файлу с зашифрованным текстом");
        JTextField textField1 = new JTextField(40);
        JLabel jLabel2 = new JLabel("Внесите в строку ниже путь к файлу текстом того же автора для применения метода СТАТИЧЕСКОГО АНАЛИЗА");
        JTextField textField2 = new JTextField(40);

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
                Path path1, path2;
                if (!(Paths.get(textField1.getText().trim()).toFile().isFile())) {
                    jLabel1.setText("Введен некорректный путь к файлу с зашифрованным текстом");
                } else if (!(Paths.get(textField2.getText().trim()).toFile().isFile())) {
                    jLabel2.setText("Введен некорректный путь к файлу с текстом аналогичного автора");
                } else {
                    path1 = Path.of(textField1.getText().trim());
                    path2 = Path.of(textField2.getText().trim());
                    fileToMap(path1, path2);
                    frame.setVisible(false);
                    frameFinalStaticAnalyze();
                }
            }

        });
    }

    private void fileToMap(Path path1, Path path2) {
        String allStr = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя";

        char[] allChar = allStr.toCharArray();
        ArrayList<Character> arrAll = new ArrayList<>();
        for (char a : allChar) {
            arrAll.add(a);
        }


        ArrayList<Character> arrEncryptionFile = new ArrayList<>();

        try (FileReader fileReader = new FileReader(String.valueOf(path1));
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while (bufferedReader.ready()) {
                char a = (char) bufferedReader.read();
                arrEncryptionFile.add(Character.toLowerCase(a));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayList<Character> arrOtherText = new ArrayList<>();

        try (FileReader fileReader = new FileReader(String.valueOf(path2));
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while (bufferedReader.ready()) {
                char a = (char) bufferedReader.read();
                arrOtherText.add(Character.toLowerCase(a));
            }


            Map<Character, Integer> mapEncryptionFile = new HashMap<>();
            for (char chars : arrEncryptionFile) {
                if (Character.isLetter(chars)) {
                    if (!mapEncryptionFile.containsKey(chars)) {
                        mapEncryptionFile.put(chars, 1);
                    } else {
                        mapEncryptionFile.put(chars, mapEncryptionFile.get(chars) + 1);
                    }
                }
            }


            mapEncryptionFile.entrySet().stream()
                    .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                    .forEach(System.out::println);

            System.out.println();


            Map<Character, Integer> mapOtherTextFile = new HashMap<>();
            for (char chars : arrOtherText) {
                if (Character.isLetter(chars)) {
                    if (!mapOtherTextFile.containsKey(chars)) {
                        mapOtherTextFile.put(chars, 1);
                    } else {
                        mapOtherTextFile.put(chars, mapOtherTextFile.get(chars) + 1);
                    }
                }
            }


            mapOtherTextFile.entrySet().stream()
                    .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                    .forEach(System.out::println);


            List<Integer> listEncr = new ArrayList<>(mapEncryptionFile.values());
            Collections.sort(listEncr);
            Collections.reverse(listEncr);


            List<Integer> listOtherTxt = new ArrayList<>(mapOtherTextFile.values());
            Collections.sort(listOtherTxt);
            Collections.reverse(listOtherTxt);

            System.out.println();

            for (int a : listEncr) {
                System.out.print(a + ",");
            }

            System.out.println();

            for (int a : listOtherTxt) {
                System.out.print(a + ",");
            }

            System.out.println();


            ArrayList<Character> arrCode = new ArrayList<>();

            for (int i = 0; i < arrEncryptionFile.size(); i++) {
                if (!arrAll.contains(arrEncryptionFile.get(i))) {
                    arrCode.add(i, arrEncryptionFile.get(i));
                } else {
                    for (Map.Entry<Character, Integer> entryScrypt : mapEncryptionFile.entrySet()) {
                        if (entryScrypt.getKey().equals(arrEncryptionFile.get(i))) {
                            int cryptValue = entryScrypt.getValue();
                            int arrIndexArrEncr = listEncr.indexOf(cryptValue);
                            int arrOtherTxtValue = listOtherTxt.get(arrIndexArrEncr);
                            for (Map.Entry<Character, Integer> entry : mapOtherTextFile.entrySet()) {
                                if (entry.getValue().equals(arrOtherTxtValue)) {
                                    char chOut = entry.getKey();
                                    arrCode.add(i, chOut);
                                }
                            }
                        }
                    }
                }
            }


            Writer writer = new FileWriter("C:\\Java\\MyProject\\CryptoSwing\\src\\StaticAnalyzeFile.txt");
            for (char ch : arrCode) {
                writer.write(ch);
            }
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void frameFinalStaticAnalyze() {
        JFrame frame3 = new JFrame("Выполнено");
        frame3.setBounds(300, 130, 600, 150);
        frame3.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame3.setLayout(new BoxLayout(frame3.getContentPane(), BoxLayout.Y_AXIS));

        JLabel jLabel = new JLabel("Расшифрованный текст записан в файл: C:\\Java\\MyProject\\CryptoSwing\\src\\StaticAnalyzeFile.txt");
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


