package WED_ZERO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.*;

/**
 *
 * @author SydnaAgnehs
 * IDE: Netbeans
 * IB Computer Science HL 2011-2013
 * Platform: Mac OS ONLY
 */
public class WED_ZERO implements ActionListener {
    private static JButton b1, b2, b3;
    private static JButton option;
    private LoadAndSave las;
    public static JFrame frame;
    public static JPanel panel;
    private JFrame add;
    private JPanel panel2;
    private JTextField txt1=null, txt2=null, txt3=null;
    private JComboBox jcb;
    private Log log;
    private Tutorial tut;
    public static LangSupport lang=new LangSupport();

    //set ups the GUI and also starts the basic system
    //like if it is the user's first time opening the program
    //it will create the nesssary files onto the user's computer
    public WED_ZERO() throws IOException {
        lang.initDefault();
        frame=new JFrame(lang.getInstruct().get(0)[0]);
        panel=new JPanel();
        panel.setLayout(null);
        las=new LoadAndSave();
        
        log = new Log();
        b1=new JButton(lang.getInstruct().get(0)[1]);
        b1.setBounds(170, 120, 200, 30);
        b1.setVisible(true);
        b1.setActionCommand("todayQ");
        b1.addActionListener(this);
        panel.add(b1);

        b2=new JButton(lang.getInstruct().get(0)[2]);
        b2.setBounds(170, 170, 200, 30);
        b2.setVisible(true);
        b2.setActionCommand("add");
        b2.addActionListener(this);
        panel.add(b2);


        b3=new JButton(lang.getInstruct().get(0)[3]);
        b3.setBounds(170, 220, 200, 30);
        b3.setVisible(true);
        b3.setActionCommand("view");
        b3.addActionListener(this);
        panel.add(b3);

        option=new JButton(lang.getInstruct().get(0)[4]);
        option.setBounds(170, 270, 200, 30);
        option.setVisible(true);
        //option.setEnabled(false);
        option.setActionCommand("option");
        option.addActionListener(this);
        panel.add(option);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(540, 380);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        panel.setVisible(true);
        
        tut=new Tutorial(log);
        tut.getTutMessage(0);
    }
    
    
    //the algorthim of adding a new vocab into the system
    private void addWord(String selection) {
        tut.getTutMessage(2);
        FileManaging fm = new FileManaging();
        String[] list=new String[(fm.getListOfFileInArray().length+1)];
        for(int x=0;x<fm.getListOfFileInArray().length;x++) {
            list[x]=fm.getListOfFileInArray()[x];
        }
        list[list.length-1]=lang.getInstruct().get(0)[6];
        
        jcb = new JComboBox(list);
        if(selection!=null) {
            jcb.setSelectedItem(selection);
        } else {
            jcb.setSelectedItem("normal");
        }
        jcb.setActionCommand("box");
        jcb.addActionListener(this);
        jcb.setBounds(170, 20, 130, 25);
        jcb.setVisible(true);

        add=new JFrame();
        panel2=new JPanel();
        panel2.setLayout(null);
        add.add(panel2);
        panel2.add(jcb);
        txt1=new JTextField(lang.getInstruct().get(0)[5], 100);
        txt1.setBounds(50, 20, 100, 25);
        txt1.setVisible(true);
        panel2.add(txt1);

        txt2=new JTextField(lang.getInstruct().get(0)[7], 100);
        txt2.setBounds(50, 70, 250, 50);
        txt2.setVisible(true);
        panel2.add(txt2);

        txt3=new JTextField(lang.getInstruct().get(0)[8], 250);
        txt3.setBounds(50, 140, 250, 50);
        txt3.setVisible(true);
        panel2.add(txt3);

        JButton b=new JButton(lang.getInstruct().get(0)[9]);
        b.setBounds(50, 210, 100, 25);
        b.setVisible(true);
        b.setActionCommand("confAdd");
        b.addActionListener(this);
        panel2.add(b);


        JButton bb=new JButton(lang.getInstruct().get(0)[10]);
        bb.setBounds(170, 210, 100, 25);
        bb.setVisible(true);
        bb.setActionCommand("canAdd");
        bb.addActionListener(this);
        panel2.add(bb);


        add.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add.setSize(320, 280);
        add.setLocationRelativeTo(null);
        add.setResizable(false);
        add.setVisible(true);
        panel2.setVisible(true);
        
    }
    
    //set up the combobox content for adding new words
    private void initSelectFolderBox() {
        if(jcb.getSelectedItem().equals(lang.getInstruct().get(0)[6])) {
                String inputValue= JOptionPane.showInputDialog(lang.getInstruct().get(0)[11]);
                 tut.getTutMessage(1);
                if(Arrays.asList(new FileManaging().getListOfFileInArray()).contains(inputValue)) {
                    ERROR.ERROR_356(frame);
                } else if(inputValue==null||inputValue.length()<1) {
                    ERROR.ERROR_357(frame);
                } else {
                    try {
                        las.createNewFolder(inputValue);
                    } catch (IOException ex) {
                        ERROR.ERROR_361(frame);
                        return;
                    }
                    panel2.setVisible(false);
                    add.setVisible(false);
                    panel2=null;
                    add=null;
                    this.addWord(inputValue);
                }
            }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if("box".equals(e.getActionCommand())){
            this.initSelectFolderBox();
        }
        if("add".equals(e.getActionCommand())) {
            this.addWord(null);
        }
        if("confAdd".equals(e.getActionCommand())) {
            this.initSelectFolderBox();
            boolean nWord=true;
            try {
                las.load(jcb.getSelectedItem().toString());
            } catch (FileNotFoundException ex) {
                ERROR.ERROR_352(frame);
                return;
            }
            outer:
            for(int x=0; x<las.loader1.size();x++) {
                if(txt1.getText().equalsIgnoreCase(las.loader1.get(x))) {
                    nWord=false;
                    break outer;
                }
            }
            if(nWord==true) {
                try {
                    las.addWords(txt1.getText(), txt2.getText(), txt3.getText(), jcb.getSelectedItem().toString());
                } catch (FileNotFoundException ex) {
                    ERROR.ERROR_352(frame);
                    return;
                } catch (IOException ex) {
                    ERROR.ERROR_360(frame);
                    return;
                }
                add.setVisible(false);
                panel2.setVisible(false);
                tut.getTutMessage(3);
                }
            else {
                ERROR.ERROR_351(Launch.frame);
            }
            
        }
        if("canAdd".equals(e.getActionCommand())) {
            add.setVisible(false);
            panel2.setVisible(false);
            add=null;
            panel2=null;
        }
        if("view".equals(e.getActionCommand())) {
            FileManaging fm = new FileManaging();
            fm.getFile(null);
            if(fm.getListOfFileInArray()==null) {
                JOptionPane.showMessageDialog(frame, "Before naviagin");
            }
            try {
                frame.setVisible(false);
                panel.setVisible(false);
                new Launch("View", 4, null);

            } catch (IOException ex) {
                ERROR.ERROR_360(frame);
                return;
            }
        }
        if("todayQ".equals(e.getActionCommand())) {
            try {
                frame.setVisible(false);
                panel.setVisible(false);
                new Launch("TodayQ", 1, null);

            } catch (IOException ex) {
                ERROR.ERROR_360(frame);
                return;
            }
        }
        if(("option").equals(e.getActionCommand())) {
            try {
                frame.setVisible(false);
                panel.setVisible(false);
                new Launch("Option", 5, null);
            } catch (IOException ex) {
                ERROR.ERROR_360(frame);
            }
        }

    }
    //To navigate back to the this main page
     public static void toMM(JPanel p, JFrame f) throws IOException {
        b1.setText(lang.getInstruct().get(0)[1]);
        b2.setText(lang.getInstruct().get(0)[2]);
        b3.setText(lang.getInstruct().get(0)[3]);
        option.setText(lang.getInstruct().get(0)[4]);
        
        WED_ZERO.frame.setVisible(true);
        WED_ZERO.panel.setVisible(true);
        if(f!=null) {
            f.setVisible(false);
            f=null;
        }
        p.setVisible(false);
        p=null;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        new WED_ZERO();
    }
}
