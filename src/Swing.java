import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Swing {

    final String allStr = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя.,\":-!? ";

    private static String title;

    void frame() {
        JFrame jframe1 = new JFrame("Выберете действие: Шифрование / Расшифровка / Метод Brute force");

        jframe1.setBounds(300, 130, 600, 150);
        jframe1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe1.setLayout(new BoxLayout(jframe1.getContentPane(), BoxLayout.Y_AXIS));

        JButton jButton1 = new JButton("Шифрование");
        JButton jButton2 = new JButton("Расшифровка");
        JButton jButton3 = new JButton("Метод Brute force");

        JPanel jPanel1 = new JPanel();
        JPanel jPanel2 = new JPanel();
        JPanel jPanel3 = new JPanel();

        jPanel1.setLayout(new FlowLayout());
        jPanel2.setLayout(new FlowLayout());
        jPanel3.setLayout(new FlowLayout());

        jPanel1.add(jButton1);
        jPanel2.add(jButton2);
        jPanel3.add(jButton3);
        jframe1.add(jPanel1);
        jframe1.add(jPanel2);
        jframe1.add(jPanel3);

        jframe1.setVisible(true);


        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                title = "Выбран режим ШИФРОВАНИЯ";
                jframe1.setVisible(false);
                Encryption enc = new Encryption();
                enc.frame(title);
            }
        });


        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                title = "Выбран режим РАСШИФРОВКИ";
                jframe1.setVisible(false);
                Decoding dec = new Decoding();
                dec.frame(title);
            }
        });


        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                title = "Выбран Метод Brutte force";
                jframe1.setVisible(false);
                BruteForce bruteForce = new BruteForce();
                bruteForce.frame(title);
            }
        });
    }
}
