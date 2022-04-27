import java.awt.EventQueue;

import javax.swing.JFrame;
public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    Main() {
        start();
    }

    private void start() {
        InputExecution view = new InputExecution();
        view.setVisible(true);}

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
