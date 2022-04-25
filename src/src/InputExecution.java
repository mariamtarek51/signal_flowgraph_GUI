import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputExecution extends JFrame {

    private static final long serialVersionUID = 1L;
    JLabel main_label,startL, endL, gainL, clear1L,clear2L,guide;
    JTextField input_field,startT, endT, gainT, clear1T, clear2T;
    JButton solveBTN,nextBTN,clearBTN,enter;
    JPanel drawPanel;
    int width, height;

    InputExecution() {
        initialize();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel k=new JLabel("Solving Signal FLow Graph");
        k.setFont(new Font("Algerian",Font.BOLD,32));
        k.setBounds(450,0,1000,40);
        k.setForeground(Color.blue);
        getContentPane().add(k);
        setTitle("Solving Signal FLow Graph");
        setLayout(null);
        //setResizable(true);
        //Taking total nodes number
        Font font = new Font("TimesNewRomans", Font.BOLD, 16);
        main_label = new JLabel("Enter total number of nodes then press enter ");
        main_label.setBounds(30, 35, 700, 40);
        main_label.setFont(font);
        getContentPane().add(main_label);

        input_field = new JTextField();
        input_field.setBounds(380, 35, 500, 30);
        input_field.setFont(font);
        getContentPane().add(input_field);

        enter = new JButton("Enter");
        enter.setBounds(890, 35, 200, 30);
        enter.setFont(font);
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {


                if (!input_field.getText().isEmpty() && isNumeric(input_field.getText())) {
                    int n = Integer.parseInt(input_field.getText());
                    NeedsData.numOfNodes = n;
                    NeedsData.segmentsGains = new double[n][n];
                    dispose();
                    InputExecution graph=new InputExecution();
                    graph.setVisible(true);
                    dispose();
                } else {
                    WarningScreen errView = new WarningScreen("Enter Valid input !");
                    errView.setVisible(true);
                }
            }
        });
        getContentPane().add(enter);
        //------------------------graph with nodes appeared------------------
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) screenSize.getWidth() ;
        height = (int) screenSize.getHeight() ;
        setSize(width,height);
        NeedsData.width = width-260;
        NeedsData.height = height-100;

        Font btnF=new Font("Calibri",Font.ITALIC,20);
        //Start node
        startL = new JLabel("Initial Node No.:");
        startL.setBounds(30, 100, 150, 50);
        startL.setFont(btnF);
        getContentPane().add(startL);
        startT = new JTextField();
        startT.setBounds(175, 100, 60, 50);
        startT.setFont(btnF);
        getContentPane().add(startT);

        //end node
        endL = new JLabel("Terminal Node No.:");
        endL.setBounds(30, 180, 160, 50);
        endL.setFont(btnF);
        getContentPane().add(endL);
        endT = new JTextField();
        endT.setBounds(175, 180, 60, 50);
        endT.setFont(btnF);
        getContentPane().add(endT);

        //gain on the determined edge
        gainL = new JLabel("Its Gain:");
        gainL.setBounds(30, 260, 160, 50);
        gainL.setFont(btnF);
        getContentPane().add(gainL);
        gainT = new JTextField();
        gainT.setBounds(175, 260, 60, 50);
        gainT.setFont(btnF);
        getContentPane().add(gainT);

        drawPanel = new GUI();
        drawPanel.setBounds(240, 70, width-260, height-130);
        drawPanel.setBackground(Color.lightGray);
        getContentPane().add(drawPanel);

        nextBTN = new JButton("Next");
        nextBTN.setBounds(15, 320, 100, 50);
        nextBTN.setFont(btnF);
        getContentPane().add(nextBTN);
        solveBTN = new JButton("Solve");
        solveBTN.setFont(btnF);
        solveBTN.setBackground(Color.green);
        solveBTN.setBorderPainted(true);
        solveBTN.setBounds(120, 320, 120, 50);
        getContentPane().add(solveBTN);

        guide=new JLabel("<html>If you want to delete <br> drawn edge.Determine its:</html>");
        guide.setFont(new Font("Calibri",Font.BOLD,21));
        guide.setBounds(9,420,300,50);
        getContentPane().add(guide);
        clear1L = new JLabel("Initial Node:");
        clear1L.setBounds(30, 480, 160, 40);
        clear1L.setFont(btnF);
        getContentPane().add(clear1L);
        clear1T = new JTextField();
        clear1T.setBounds(175, 480, 60, 40);
        clear1T.setFont(btnF);
        getContentPane().add(clear1T);

        clear2L = new JLabel("Terminal Node:");
        clear2L.setBounds(30, 540, 160, 40);
        clear2L.setFont(btnF);
        getContentPane().add((clear2L));
        clear2T = new JTextField();
        clear2T.setBounds(175, 540, 60, 40);
        clear2T.setFont(btnF);
        getContentPane().add(clear2T);

        clearBTN = new JButton("clear");
        clearBTN.setBounds(70, 590, 100, 50);
        clearBTN.setBackground(Color.red);
        clearBTN.setFont(btnF);
        getContentPane().add(clearBTN);

       solveBTN.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

               Solver mason = new Solver();
                mason.setSFG(NeedsData.segmentsGains);
                NeedsData.forwardPaths = mason.getForwardPaths();
                NeedsData.loops = mason.getLoops();
                NeedsData.nonTouchingloops = mason.getNonTouchingLoops();
                NeedsData.loopsGain = mason.getLoopGains();
                NeedsData.forwardPathsGain = mason.getForwardPathGains();
                NeedsData.nonTouchingloopsGain = mason.getNonTouchingLoopGains();
                NeedsData.overAllTF = mason.transferFn();
                GuiResult result = new GuiResult();
                result.setVisible(true);
            }
        });
       nextBTN.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!isNumeric(startT.getText())) {
                    WarningScreen errView = new WarningScreen(
                            "Initial Node, Invalid numeric value!");
                    errView.setVisible(true);
                } else if (!isNumeric(endT.getText())) {
                    WarningScreen errView = new WarningScreen(
                            "Terminal Node, Invalid numeric value!");
                    errView.setVisible(true);
                } else if (!isNumeric(gainT.getText())) {
                    WarningScreen errView = new WarningScreen(
                            "Segment gain, Invalid numeric value!");
                    errView.setVisible(true);
                } else {
                    int n1 = Integer.parseInt(startT.getText()), n2 = Integer
                            .parseInt(endT.getText());
                    if (n1 > NeedsData.numOfNodes || n2 >NeedsData.numOfNodes) {
                        WarningScreen errView = new WarningScreen(
                                "Node number exceeded max number of nodes!");
                        errView.setVisible(true);
                    } else if (n1 < 1 || n2 < 1) {
                        WarningScreen errView = new WarningScreen(
                                "Invalid node number!");
                        errView.setVisible(true);
                    } else if (n2 == 1) {
                        WarningScreen errView = new WarningScreen(
                                "No feedback allowed to node 1");
                        errView.setVisible(true);
                    } else {
                        double g = Double.parseDouble(gainT.getText());
                        NeedsData.segmentsGains[n1 - 1][n2 - 1] = g;
                        drawPanel.repaint();
                        endT.setText("");
                        startT.setText("");
                        gainT.setText("");
                    }
                }
            }
        });

        clearBTN.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!isNumeric(clear1T.getText())) {
                    WarningScreen errView = new WarningScreen(
                            " Initial node has invalid numeric value or is not found!");
                    errView.setVisible(true);
                } else if (!isNumeric(clear2T.getText())) {
                    WarningScreen errView = new WarningScreen(
                            "Terminal node has invalid numeric value or is not found!");
                    errView.setVisible(true);
                } else {
                    int n1 = Integer.parseInt(clear1T.getText()), n2 = Integer
                            .parseInt(clear2T.getText());
                    if (n1 > NeedsData.numOfNodes || n2 > NeedsData.numOfNodes) {
                        WarningScreen errView = new WarningScreen(
                                "Node no. exceeded max number of nodes!");
                        errView.setVisible(true);
                    } else if (n1 < 1 || n2 < 1) {
                        WarningScreen errView = new WarningScreen(
                                "invalid node number!");
                        errView.setVisible(true);
                    } else if (NeedsData.segmentsGains[n1 - 1][n2 - 1] == 0) {
                        WarningScreen errView = new WarningScreen(
                                "That edge doesn't exist!");
                        errView.setVisible(true);
                    } else {
                        NeedsData.segmentsGains[n1 - 1][n2 - 1] = 0;
                        drawPanel.repaint();
                        clear1T.setText("");
                        clear2T.setText("");
                    }
                }
            }
        });


    }
    Boolean isNumeric(String input){
        Boolean flag=true;
        for(int a=0;a<input.length();a++)
        {
            if(a==0 && input.charAt(a) == '-')
                continue;
            if( !Character.isDigit(input.charAt(a)))
                flag=false;
        }
        return flag;
    }
}
