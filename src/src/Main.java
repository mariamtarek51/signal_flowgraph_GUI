import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    Main() {
        start();
    }

    private void start() {
        InputExecution view = new InputExecution();
        view.setVisible(true);}
       /* setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(400, 200, 600, 250);
        setTitle("Signal FLow Graph Solver");
        setLayout(null);
        setResizable(false);*/



    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main view = new Main();
                    //view.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
