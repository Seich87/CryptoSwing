import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Swing {
    private static String title;


    void frame() {
        JFrame jframe = new JFrame("Выберете действие: Шифрование / Расшифровка / Метод Brute force");

        jframe.setBounds(300, 130, 600, 250);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setLayout(new BoxLayout(jframe.getContentPane(), BoxLayout.Y_AXIS));

        JButton jButton1 = new JButton("Шифрование");
        JButton jButton2 = new JButton("Расшифровка");
        JButton jButton3 = new JButton("Метод Brute force");
        JButton jButton4 = new JButton("Метод Static Analyze");


        JPanel jPanel1 = new JPanel();
        JPanel jPanel2 = new JPanel();
        JPanel jPanel3 = new JPanel();
        JPanel jPanel4 = new JPanel();

        jPanel1.setLayout(new FlowLayout());
        jPanel2.setLayout(new FlowLayout());
        jPanel3.setLayout(new FlowLayout());
        jPanel4.setLayout(new FlowLayout());

        jPanel1.add(jButton1);
        jPanel2.add(jButton2);
        jPanel3.add(jButton3);
        jPanel4.add(jButton4);

        jframe.add(jPanel1);
        jframe.add(jPanel2);
        jframe.add(jPanel3);
        jframe.add(jPanel4);

        jframe.setVisible(true);


        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                title = "Выбран режим ШИФРОВАНИЯ";
                jframe.setVisible(false);
                CryptoUtils enc = new Encryption();
                someMethod(title, enc);

            }
        });


        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                title = "Выбран режим РАСШИФРОВКИ";
                jframe.setVisible(false);
                CryptoUtils dec = new Decoding();
                someMethod(title, dec);
            }
        });


        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                title = "Выбран Метод BRUTE FORCE";
                jframe.setVisible(false);
                BruteForce bruteForce = new BruteForce();
                bruteForce.frame(title);
            }
        });

        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                title = "Выбран Метод STATIC ANALYZE";
                jframe.setVisible(false);
                StaticAnalyze staticAnalyze = new StaticAnalyze();
                staticAnalyze.statAnalyze(title);
            }
        });


    }

    private void someMethod(String title, CryptoUtils cryptoUtils) {
        cryptoUtils.execute(title, cryptoUtils);
    }
}
