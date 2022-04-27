import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class WarningScreen extends JFrame {

    private JLabel label;

    WarningScreen(String str) {
        initialize(str);
    }

    private void initialize(String str) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(400, 200, 600, 200);
        setTitle("WARNING!");
        setLayout(null);
        setResizable(false);

        label = new JLabel(str);
        label.setBounds(50, 0, 500, 200);
        Font font = new Font("Serif", Font.PLAIN, 24);
        label.setFont(font);
        getContentPane().add(label);

    }
}
