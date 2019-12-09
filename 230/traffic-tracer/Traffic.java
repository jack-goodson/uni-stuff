import java.awt.*;
import java.nio.file.Paths;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JFrame;
import java.io.File;
import java.math.*;
import java.io.IOException;
import javax.swing.*;

public class Traffic extends JFrame implements ComponentListener, ItemListener {

    private JComboBox<String> hostComboBox;
    private Font font;
    private JPanel radioButtonPanel;
    private ButtonGroup radioButtons;
    private JRadioButton radioButtonNormalCase;
    private JRadioButton radioButtonUpperCase;
    private Font radioFont;
    private int yAxisPoint;
    private int xAxisPoint;
    private String[] rel;
    private Set<String> sourceIDs = new HashSet<String>();
    private Set<String> destIDs = new HashSet<String>();
    private String time;
    private ArrayList<int[]> dataPerSec = new ArrayList<int[]>();
    private int maxDataFlow;
    private File textFile;
    private boolean comBoBool = true;



    ArrayList<String[]> splitData = new ArrayList<String[]>();
    ArrayList<Graph> toDraw = new ArrayList<>();
    ArrayList<Graph> toPlot = new ArrayList<>();
    ArrayList<String> timeLabels = new ArrayList<>();

    /**
     *Set up the X and Y axis, establish JFrames and sizes
     **/

    public Traffic() {
        super("Traffic Watcher");
        comBoBool = true;

        Graph yAxis = new Graph(60, 30, 60, 380);
        toDraw.add(yAxis);

        Graph xAxis = new Graph(60, 380, 940, 380);
        toDraw.add(xAxis);

        Graph labelOne = new Graph("Volume (Bytes)", 10, 15);
        toDraw.add(labelOne);

        Graph labelTwo = new Graph("Time (s)", 940, 385);
        toDraw.add(labelTwo);

        setLayout(new FlowLayout());
        setSize(1050,520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        font = new Font("Sans-serif", Font.PLAIN, 18);

        this.addComponentListener(this);
        setVisible(true);

        GraphDrawer p = new GraphDrawer(toDraw, toPlot);
        p.setPreferredSize(new Dimension(1000, 420));
        this.add(p);

        setupMenu();
        setUpRadioB();

    }

    /**
     *Set up file selection menu with labels and event listeners
     **/

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        fileMenu.setFont(font);
        menuBar.add(fileMenu);
        JMenuItem fileMenuOpen = new JMenuItem("Add Trace File");
        fileMenuOpen.setFont(font);
        fileMenuOpen.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileChooser = new JFileChooser(".");
                        int retval = fileChooser.showOpenDialog(Traffic.this);
                        if (retval == JFileChooser.APPROVE_OPTION) {
                            File f = fileChooser.getSelectedFile();

                            textFile = f;

                            dataSorter();
                        }
                    }
                }
        );
        fileMenu.add(fileMenuOpen);
        JMenuItem fileMenuQuit = new JMenuItem("Quit");
        fileMenuQuit.setFont(font);
        fileMenu.add(fileMenuQuit);
        fileMenuQuit.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                }
        );
    }

    /**
     *Set up radio button with event listeners
     **/

    public void setUpRadioB(){
        radioButtonPanel = new JPanel();
        radioButtonPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.anchor = GridBagConstraints.WEST;
        radioButtons = new ButtonGroup();
        radioButtonPanel.setLayout( new FlowLayout(FlowLayout.LEFT) );
        radioFont = new Font("Sans-serif", Font.PLAIN, 10);
        radioButtonNormalCase = new JRadioButton("Source Hosts");
        radioButtonNormalCase.setFont(radioFont);
        radioButtonNormalCase.setSelected(true);
        radioButtons.add(radioButtonNormalCase);
        radioButtonNormalCase.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e) {
                        setUpHostComboBox();

                    }
                }
        );
        radioButtonPanel.add(radioButtonNormalCase, c);
        radioButtonUpperCase = new JRadioButton("Destination Hosts");
        radioButtonUpperCase.setFont(radioFont);
        radioButtons.add(radioButtonUpperCase);
        radioButtonUpperCase.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e) {
                        setUpHostComboBox();

                    }
                }
        );
        radioButtonPanel.add(radioButtonUpperCase, c);
        add(radioButtonPanel);
    }

    /**
     *Set combo box with event listener
     **/

    public void setUpHostComboBox(){
        if(comBoBool == true){
            hostComboBox = new JComboBox<String>();
            hostComboBox.setModel((MutableComboBoxModel)new DefaultComboBoxModel());
            hostComboBox.addItemListener(this);
            hostComboBox.setFont(radioFont);
            hostComboBox.setMinimumSize(new Dimension(300,200));
            comBoBool = false;
        }
        hostComboBox.removeAllItems();
        if(radioButtonNormalCase.isSelected() == true){

            for(String s: sourceIDs){
                hostComboBox.addItem(s);
            }
        }if(radioButtonUpperCase.isSelected() == true){
            for(String s: destIDs){
                hostComboBox.addItem(s);
            }
        }
        hostComboBox.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e) {
                        labelData();

                    }
                }
        );

        add(hostComboBox);
        hostComboBox.setVisible(true);
        revalidate();

        labelData();
        }

    /**
     *Sort data and find all unique IP addresses
     **/
    public void dataSorter(){

        ArrayList<String> data = new ArrayList<String>();
        data.clear();
        splitData.clear();
        time = "";
        Scanner input = null;
        try{
            input = new Scanner(Paths.get(textFile.toString()));
        }catch (IOException ioExc) {
            return;
        }while (input.hasNext()) {
            String currentLine = input.nextLine();
            data.add(currentLine);

        }


        for(int y= 0; y < data.size(); y++){

            splitData.add(data.get(y).split("\t"));
        }

        for(int y = 0; y < splitData.size(); y++){
            rel = splitData.get(y);
            if(rel[2].matches("(([0-1]?[0-9]{1,2}\\.)|(2[0-4][0-9]\\.)|(25[0-5]\\.)){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))")){
                sourceIDs.add(rel[2]);
            }

            if(rel[4].matches("(([0-1]?[0-9]{1,2}\\.)|(2[0-4][0-9]\\.)|(25[0-5]\\.)){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))")){
                destIDs.add(rel[4]);
            }
            time = rel[1];

            sourceIDs.remove("");
            destIDs.remove("");
            }
        setUpHostComboBox();
        }

    /**
     *Label X and Y axis according to volume of data
     **/

    public void labelData(){

        dataPerSec.clear();

        toDraw.clear();

        Graph yAxis = new Graph(60, 30, 60, 380);
        toDraw.add(yAxis);

        Graph xAxis = new Graph(50, 380, 940, 380);
        toDraw.add(xAxis);

        Graph labelOne = new Graph("Volume (Bytes)", 10, 15);
        toDraw.add(labelOne);

        Graph labelTwo = new Graph("Time (s)", 940, 385);
        toDraw.add(labelTwo);

        /** Label X Axis **/

        double timeNum = 0;

        timeNum = Double.parseDouble(time);

        double rounded = timeNum/ 18;


        timeLabels.clear();
        timeLabels.add("0");
        double d = rounded;
        BigDecimal bd = new BigDecimal(d);
        bd = bd.round(new MathContext(1));
        rounded = bd.doubleValue();

        String strTime = String.valueOf((int)rounded);
        double loopTime = rounded;

        for(int i = 0; i < 18; i++){
            timeLabels.add(strTime);
            loopTime += rounded;
            bd = bd.round(new MathContext(1));
            rounded = bd.doubleValue();
            strTime = String.valueOf((int)loopTime);
        }

        xAxisPoint = 60;
        for(int i = 0; i < 18; i++) {
            toDraw.add(new Graph(xAxisPoint,370,xAxisPoint,390));
            toDraw.add(new Graph(timeLabels.get(i), xAxisPoint-10, 405));
            xAxisPoint += 50;
        }


        /** Label Y Axis **/
                                                           //This block changes times to whole numbers for
        double dataSize = 0;                                //the entire data set
        String maxTime = "";
        for(int i = 0; i < splitData.size(); i++){
            String[] item = splitData.get(i);
            int timeInSec = (int) Math.rint(Double.parseDouble(item[1]));
            item[1] = String.valueOf(timeInSec);
            maxTime = item[1];
            }

        for(int i = 0; i <= Integer.valueOf(maxTime); i++){ // Add the time values into datapersec
            int[] toAdd = {i, 0};
            dataPerSec.add(toAdd);
        }
        for (int[] s : dataPerSec) {
            if(s[1] > maxDataFlow){
                maxDataFlow = s[1];
            }
        }
        for (int i = 0; i < splitData.size(); i++) {
            String[] item = splitData.get(i);

            if(radioButtonNormalCase.isSelected()){ try{
                if (item.length >= 8 && item[8].matches("-?\\d+(\\.\\d+)?") && hostComboBox.getSelectedItem().equals(item[2])) {
                    int[] times = dataPerSec.get(Integer.valueOf(item[1]));
                    times[1] += Integer.valueOf(item[8]);

                }
            }catch (Exception e){}}

            else if(radioButtonUpperCase.isSelected()){
                try{
                if (item.length >= 8 && item[8].matches("-?\\d+(\\.\\d+)?") && hostComboBox.getSelectedItem().equals(item[4])) {

                    int[] times = dataPerSec.get(Integer.valueOf(item[1]));
                    times[1] += Integer.valueOf(item[8]);

                }
                }catch (Exception e){}

            }
        }

        maxDataFlow = 0;
        for (int[] s : dataPerSec) {
            if(s[1] > maxDataFlow){
                maxDataFlow = s[1];
            }
        }

        /** label Y axis **/

        double dataNum = Double.valueOf(maxDataFlow);

        rounded = dataNum / 7;

        rounded = rounded / 1000;

        ArrayList<String> dataLabels = new ArrayList<>();
        dataLabels.add("0");
        double c = rounded;
        BigDecimal bc = new BigDecimal(c);
        bc = bc.round(new MathContext(2));
        rounded = bc.doubleValue();

        String strData = String.valueOf((int)rounded);
        double loopData = rounded;
        for(int i = 0; i < 7; i++){
            dataLabels.add(strData);
            loopData += rounded;
            bc = bc.round(new MathContext(1));
            rounded = bc.doubleValue();
            strData = String.valueOf((int)loopData);
        }

        yAxisPoint = 30;
        Collections.reverse(dataLabels);
        for(int i = 0; i < 8; i++) {
            toDraw.add(new Graph(50,yAxisPoint,70,yAxisPoint));
            toDraw.add(new Graph(dataLabels.get(i) + "k", 10, yAxisPoint+5));
            yAxisPoint += 50;
        }

        repaint();

        dataPlotter();

    }

    /**
     *plot the data given
     **/

    public void dataPlotter(){
        toPlot.clear();

        double unitY = 350 / (double)(maxDataFlow / 1000);
        double unitX = 850 / Double.valueOf(timeLabels.get(17));

        double holder = unitX;

        int startPoint = 60;


        for(int[] d: dataPerSec){
            if(d[1] > 0) {

                long y1 = Math.round((350 - (unitY * (d[1] / 1000))));
                int yy1 = (int) y1;


                long x1 = Math.round((holder));
                int xx1 = (int) x1;

                toPlot.add(new DataPlots(xx1 + 60, yy1 +30, 1, 350- yy1));

                holder += unitX;
            }else{
                holder += unitX;
            }
        }
    }

    /**
     *called when a component hidden
     *@param e must be taken as an argument
     *@return returns nothing
     **/

    public void componentHidden(ComponentEvent e) {
        return;
    }
    /**
     *called when a component moved
     *@param e must be taken as an argument
     **/

    public void componentMoved(ComponentEvent e) {
        return;
    }

    /**
     *called when component resized and repaints
     *@param e event on component must be taken
     **/
    public void componentResized(ComponentEvent e) {
        this.repaint();
    }

    /**
     *called when a component shown
     *@param e must be taken as an argument
     *@return returns nothing
     **/

    public void componentShown(ComponentEvent e) {
        return;
    }

    /**
     *called when an item changed
     *@param e must be taken as an argument
     *@return returns nothing
     **/
    public void itemStateChanged(ItemEvent e) {
        return;
    }

}
