import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.util.mxStyleUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;


public class GUI extends JFrame {
    mxGraph graph;
    JPanel MainScreen, ScreenComp;

    public GUI() {
        super("Signal-Flow Graph");

        initial_Shape();

        graph = new mxGraph(){

        };
        // graph.setAllowDanglingEdges(false);
        //graph.getModel().beginUpdate();
        try
        {
            //addvertex();
        }
        finally
        {
            // graph.getModel().endUpdate();
        }
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setConnectable(true);
        graphComponent.getViewport().setOpaque(true);

        graphComponent.getViewport().setBackground(Color.darkGray);
        graph.getModel().setGeometry(graph.getDefaultParent(),
                new mxGeometry(980, 750,
                        0, 0));
        graph.setMinimumGraphSize(new mxRectangle(980, 750, 0, 0));
        graph.setCollapseToPreferredSize(true);
        graph.setVertexLabelsMovable(false);
        graph.setEdgeLabelsMovable(false);
        graph.setAllowLoops(true);

        graph.addListener(mxEvent.LABEL_CHANGED, (sender, evt) -> {
                    mxICell vertex =(mxICell) evt.getProperties().get("vertex");
                }

        );
        MainScreen.add(graphComponent);
        //setEdgeStyle();
    }
    private void initial_Shape(){
        getContentPane().setLayout(new BorderLayout());
        MainScreen=new JPanel();
        MainScreen.setBackground(Color.cyan);
        MainScreen.setBounds(100,100,100 ,110);

        ScreenComp=new JPanel();
        ScreenComp.setBounds(0,0,1 ,1);
        ScreenComp.setLayout(null);
        ScreenComp.setBackground(Color.lightGray);
        getContentPane().add(ScreenComp);
        getContentPane().add(MainScreen, BorderLayout.LINE_END);
        LinkedList<Object> Redo=new LinkedList<>();
        JLabel l1;
        l1=new JLabel("Solving Signal Flow diagram");
        l1.setFont(new Font("TimesNewRoman",Font.ITALIC,30));
        l1.setBounds(500,-30, 2500,200);
        l1.setForeground(Color.blue);
        ScreenComp.add(l1);
        //BUTTONS
        Font font = new Font("TimesNewRoman", Font.BOLD, 20);
        //Button to add vertex
        JButton addVertexBtn = new JButton("Add Node");
        addVertexBtn.setBounds(40,100,150, 40);
        addVertexBtn.setFont(font);
        addVertexBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //addvertex();
            }
        });ScreenComp.add(addVertexBtn);
        //button to add edge
        JButton addEdge=new JButton("Add Edge");
        addEdge.setBounds(200,100, 150,40);
        addEdge.setFont(font);
        addEdge.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //addedge();
                graph.addListener(mxEvent.CELL_CONNECTED, (sender, evt) -> {

                            if (!(boolean) evt.getProperties().get("source")) {
                                mxICell edge =(mxICell) evt.getProperties().get("edge");
                                edge.setValue("1");
                            }
                        }

                );
            }
        });
        ScreenComp.add(addEdge);
        //start analysis
        JButton analysis=new JButton("Start Analysis");
        analysis.setFont(font);
        analysis.setBounds(360,100,250,40);
        analysis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // if(checkEdgesValue() && checkNodesNames() && textExists()){
                //   startCalculation();
                //}
            }
        });ScreenComp.add(analysis);
        //reset your graph
        JButton reset=new JButton("Reset");
        reset.setBounds(1090,100,100,40);
        reset.setFont(font);
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                graph.refresh();
            }
        });ScreenComp.add(reset);
        //output node text:
        JLabel txtlbl = new JLabel("Enter Destination node");
        txtlbl.setBounds(40, 150, 220, 20);
        txtlbl.setFont(font);
        JTextField txt = new JTextField();
        txt.setBounds(260, 150, 150, 25);
        txt.setFont(font);
        ScreenComp.add(txt);
        ScreenComp.add(txtlbl);
    }
    public static void main(String[] args) {
        GUI frame = new GUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}