package flow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;

public class GuiResult extends JFrame {

    public void init() {

        JLabel loopsLabel, ForwardPathsLabel , TransferFuncLabel , nontouchLabel, loopsGainLabel, ForwardPathsGainLabel, nonTouchGainLabel, TransferFuncLabel_body;

        JTextPane loopsLabel_body, ForwardPathsLabel_body, nontouchLabel_body, loopsGainLabel_body, ForwardPathsGainLabel_body, nonTouchGainLabel_body;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth() - 120;
        int height = (int) screenSize.getHeight() - 100;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 50, width, height);
        setTitle("Signal FLow Graph");
        setLayout(null);
        setResizable(true);

        ForwardPathsLabel = new JLabel("Forward Paths");
        ForwardPathsGainLabel = new JLabel("");
        ForwardPathsGainLabel_body = new JTextPane();
        ForwardPathsLabel_body = new JTextPane();
        JLabel ForwardPathsDeltaLabel = new JLabel("");
        JTextPane ForwardPathsDeltaLabel_body = new JTextPane();

        loopsLabel = new JLabel("Loops");
        loopsGainLabel = new JLabel("");  
        loopsGainLabel_body = new JTextPane();
        loopsLabel_body = new JTextPane();
        loopsLabel_body.setBackground(Color.LIGHT_GRAY);
        loopsGainLabel_body.setBackground(Color.LIGHT_GRAY);

        nontouchLabel = new JLabel("Non-Touching loops");
        nonTouchGainLabel = new JLabel(""); 
        nonTouchGainLabel_body = new JTextPane();
        nontouchLabel_body = new JTextPane();
        //nonTouchGainLabel_body.setBackground(Color.LIGHT_GRAY);

        TransferFuncLabel = new JLabel("Transfer function =   ");
        TransferFuncLabel_body = new JLabel();

        JLabel DeltaLabel = new JLabel("Determinant of SFG (delta) =   ");
        JLabel DeltaLabel_body = new JLabel();
        
        //this part to set bounds to the view in the results
        ForwardPathsLabel.setBounds(100, 0, 450, 40);
        ForwardPathsLabel_body.setBounds(0, 40, 250, height - 200);
        ForwardPathsGainLabel.setBounds(300, 0, 150, 40);
        ForwardPathsGainLabel_body.setBounds(250, 40, 150, height - 200);
        ForwardPathsDeltaLabel.setBounds(450, 0, 100, 40);
        ForwardPathsDeltaLabel_body.setBounds(400, 40, 100, height - 200);

        loopsLabel.setBounds(500, 0, 250, 40);
        loopsLabel_body.setBounds(500, 40, 250, height - 200);
        loopsGainLabel.setBounds(800, 0, 100, 40);
        loopsGainLabel_body.setBounds(750, 40, 100, height - 200);

        nontouchLabel.setBounds(850, 0, 250, 40);
        nontouchLabel_body.setBounds(850, 40, 250, height - 200);
        nonTouchGainLabel.setBounds(1150, 0, 150, 40);
        nonTouchGainLabel_body.setBounds(1100, 40, 150, height - 200);
    

        TransferFuncLabel.setBounds(20, height - 90, 350, 40);
        TransferFuncLabel_body.setBounds(350, height - 90, 300, 40);
        Font font = new Font("TimesRoman", Font.BOLD, 32);
        TransferFuncLabel.setFont(font);
        TransferFuncLabel_body.setFont(font);

        DeltaLabel.setBounds(20, height - 120, 500, 40);
        DeltaLabel_body.setBounds(500, height - 120, 300, 40);
        DeltaLabel.setFont(font);
        DeltaLabel_body.setFont(font);
        font = new Font("TimesRoman", Font.BOLD, 22);

        loopsLabel.setFont(font);
        ForwardPathsLabel.setFont(font);
        nontouchLabel.setFont(font);
        loopsGainLabel.setFont(font);
        ForwardPathsGainLabel.setFont(font);
        nonTouchGainLabel.setFont(font);

        getContentPane().add(loopsLabel);
        getContentPane().add(ForwardPathsLabel);
        getContentPane().add(TransferFuncLabel);
        getContentPane().add(DeltaLabel);
        getContentPane().add(nontouchLabel);
        getContentPane().add(loopsGainLabel);
        getContentPane().add(ForwardPathsGainLabel);
        getContentPane().add(ForwardPathsDeltaLabel);
        getContentPane().add(nonTouchGainLabel);

        getContentPane().add(loopsLabel_body);
        getContentPane().add(ForwardPathsLabel_body);
        getContentPane().add(TransferFuncLabel_body);
        getContentPane().add(DeltaLabel_body);
        getContentPane().add(nontouchLabel_body);
        getContentPane().add(loopsGainLabel_body);
        getContentPane().add(ForwardPathsGainLabel_body);
        getContentPane().add(ForwardPathsDeltaLabel_body);
        getContentPane().add(nonTouchGainLabel_body);


        // this part to print results in the view
        StringBuilder sb = new StringBuilder();
        sb.append("<font size=\"5\">");
        String[] tempArr = NeedsData.forwardPaths;
        for (int i = 0; i < tempArr.length; i++)
            sb.append("P"+(i + 1) + "= " + tempArr[i] + "<br>");
        sb.append("</font>");

        ForwardPathsLabel_body.setContentType("text/html");
        ForwardPathsLabel_body.setEditable(false);
        ForwardPathsLabel_body.setText(sb.toString());
        //ForwardPathsLabel_body.setForeground(Color.blue);

        sb = new StringBuilder();
        sb.append("<font size=\"5\">");
        Double [] tempDo = NeedsData.smallDelta;
        for (int i = 0; i < tempDo.length; i++)
            sb.append( "&#9651;"+ (i+1) +"= " + tempDo[i].doubleValue() + "<br>");
        sb.append("</font>");

        ForwardPathsDeltaLabel_body.setContentType("text/html");
        ForwardPathsDeltaLabel_body.setEditable(false);
        ForwardPathsDeltaLabel_body.setText(sb.toString());

        sb = new StringBuilder();
        sb.append("<font size=\"5\">");
        tempArr = NeedsData.loops;
        for (int i = 0; i < tempArr.length; i++)
            sb.append("L"+(i + 1) + "= " + tempArr[i] + "<br>");
        sb.append("</font>");
        loopsLabel_body.setContentType("text/html");
        loopsLabel_body.setEditable(false);
        loopsLabel_body.setText(sb.toString());

        sb = new StringBuilder();
        sb.append("<font size=\"5\">");
        tempArr = NeedsData.nonTouchingloops;
        for (int i = 0; i < tempArr.length; i++)
            sb.append((i + 1) + ") " + tempArr[i] + "<br>");
        sb.append("</font>");

        nontouchLabel_body.setContentType("text/html");
        nontouchLabel_body.setEditable(false);
        nontouchLabel_body.setText(sb.toString());

        sb = new StringBuilder();
        sb.append("<font size=\"5\">");
        Double [] tempDou = NeedsData.forwardPathsGain;
        for (int i = 0; i < tempDou.length; i++)
            sb.append( "gain = " + tempDou[i].doubleValue() + "<br>");
        sb.append("</font>");

        ForwardPathsGainLabel_body.setContentType("text/html");
        ForwardPathsGainLabel_body.setEditable(false);
        ForwardPathsGainLabel_body.setText(sb.toString());

        sb = new StringBuilder();
        sb.append("<font size=\"5\">");
        tempDou = NeedsData.loopsGain;
        for (int i = 0; i < tempDou.length; i++)
            sb.append("gain = " + tempDou[i].doubleValue() + "<br>");
        sb.append("</font>");

        loopsGainLabel_body.setContentType("text/html");
        loopsGainLabel_body.setEditable(false);
        loopsGainLabel_body.setText(sb.toString());

        sb = new StringBuilder();
        sb.append("<font size=\"5\">");
        tempDou = NeedsData.nonTouchingloopsGain;
        for (int i = 0; i < tempDou.length; i++)
            sb.append("gain = " + tempDou[i].doubleValue() + "<br>");
        sb.append("</font>");

        nonTouchGainLabel_body.setContentType("text/html");
        nonTouchGainLabel_body.setEditable(false);
        nonTouchGainLabel_body.setText(sb.toString());

        TransferFuncLabel_body.setText(NeedsData.overAllTF + "");
        DeltaLabel_body.setText(NeedsData.deltaDeno+ "");
    }

    GuiResult() {
        init();
    }
}