package WED_ZERO;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author SydnaAgnehs
 */
public class Qtoday implements ActionListener {
    
    private JPanel Panel;
    private Map<Integer, JButton> buttons= new HashMap<Integer, JButton>();
    private Map<Integer, JTextField> jtext= new HashMap<Integer, JTextField>();
    private JTextArea jta;
    private TodaySystem TS;
    private String theWord=null;
    private int answerIndex=0;
    private String[] str=null;
    private ScoreTrackingSystem st;
    
    
    
    public Qtoday(ArrayList<String> packetName) throws FileNotFoundException, IOException {
        TS=new TodaySystem(packetName);
        JPanel midPanel=new JPanel();
        midPanel.setPreferredSize(new Dimension(540, 400));
        jta = new JTextArea(WED_ZERO.lang.getInstruct().get(4)[0]+": ", 5, 40);
        jta.setVisible(true);
        jta.setEditable(false);
        this.initLook(midPanel);
        Panel=new JPanel();
        Panel.setVisible(true);
        midPanel.setVisible(true);
        Panel.add(jta);
        Panel.add(midPanel);
        Launch.frame.add(Panel);
        st=new ScoreTrackingSystem(TS.getTotalCount());
        this.gameSystem();
    }
    //initalizes the GUI
    private void initLook(JPanel panel) {
        for(int x=1;x<=4;x++) {
            buttons.put(x, new JButton(x+". "));
            buttons.get(x).setPreferredSize(new Dimension(60, 60));
            buttons.get(x).setActionCommand(x+"");
            buttons.get(x).addActionListener(this);
            buttons.get(x).setVisible(true);
            buttons.get(x).setBackground(Color.yellow);
            panel.add(buttons.get(x));
            jtext.put(x, new JTextField("~~~~~~~~~~~"));
            jtext.get(x).setPreferredSize(new Dimension(200, 60));
            jtext.get(x).setVisible(true);
            jtext.get(x).setEditable(false);
            panel.add(jtext.get(x));            
        }
        buttons.put(0, new JButton(WED_ZERO.lang.getInstruct().get(4)[1]));
        buttons.get(0).setPreferredSize(new Dimension(200, 80));
        buttons.get(0).setVisible(true);
        buttons.get(0).setActionCommand("submit");
        buttons.get(0).addActionListener(this);
        panel.add(buttons.get(0));
    }
    
    private void gameSystem() {
        str=TS.getOrganizerSetup();
        this.setQuestion();
    }
    //prepares the question for the user
    private void setQuestion() {
        if(str==null) {
            this.endin();
            return;
        }
        jta.setText(str[4]);
        for(int x=1;x<=4;x++) {
            jtext.get(x).setText(str[x-1]);
        }
        answerIndex=(int)(1+Math.random()*3);
        jtext.get(answerIndex).setText(str[5]);
        theWord=str[5];
    }
    
    //handles the ending sequence
    private void endin() {
        jta.setText(WED_ZERO.lang.getInstruct().get(4)[0]+": ");
        for(int x=1;x<=4;x++) {
            
            jtext.get(x).setText("~~~~~~~~~~~");
            buttons.get(x).setEnabled(false);
        }
        JOptionPane.showConfirmDialog(Launch.frame, st.getResults(),
        WED_ZERO.lang.getInstruct().get(4)[2], JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE);
        try {
            WED_ZERO.toMM(Panel, Launch.frame);
        } catch (IOException ex) {
            ERROR.ERROR_358(Launch.frame);
        }
    }
    
    private void setAllButton(int index) {
        if(buttons.get(index).isOpaque()) {
            buttons.get(index).setOpaque(false);
            return;
        }
        buttons.get(1).setOpaque(false);
        buttons.get(2).setOpaque(false);
        buttons.get(3).setOpaque(false);
        buttons.get(4).setOpaque(false);
        buttons.get(index).setOpaque(true);
    }
    
    
    private boolean resetButton() {
        for(int x=0;x<buttons.size();x++) {
            buttons.get(x).setOpaque(false);
        }
        return true;
    }
    
    private boolean isInteger(String a) {
        try 
        {
            Integer.parseInt(a);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }


    private void newRound() {
        str=null;
        this.gameSystem();
    }
    

    private void proceed(boolean answer) throws FileNotFoundException, IOException {
        if(answer) {
            JOptionPane.showMessageDialog(Launch.frame, WED_ZERO.lang.getInstruct().get(4)[3]);
            st.incrementCorrect();
            TS.updatePoints(true, str);
        } else {
            JOptionPane.showMessageDialog(Launch.frame, WED_ZERO.lang.getInstruct().get(4)[3]+"\n"+WED_ZERO.lang.getInstruct().get(4)[3]+": "+theWord);
            st.incrementIncorrect();
            TS.updatePoints(false, str);
        }
        this.resetButton();
        this.gameSystem();
    }
    
    private void processResponse(int choice) {
        try {
            this.proceed(choice==answerIndex);
            this.newRound();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Qtoday.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Qtoday.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int getResponse() {
        for(int x=1;x<buttons.size();x++) {
            if(buttons.get(x).isOpaque()) {
                return x;
            }
        }
        return 0;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.isInteger(e.getActionCommand()))
        this.setAllButton(Integer.parseInt(e.getActionCommand()));
        if(e.getActionCommand().equals("submit")) {
            this.processResponse(this.getResponse());
         }
    }

}
