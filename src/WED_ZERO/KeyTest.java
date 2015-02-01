package WED_ZERO;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author SydnaAgnehs
 */
public class KeyTest implements ActionListener {
    private JTextArea qArea, reArea;
    private JButton clear, submit, next;
    private JPanel panel;
    private TodaySystem ts;
    private int indexSP;
    private String word;
    private String[] keywords;
        public KeyTest(ArrayList<String> packetName) {
            this.initLook();
            ts=new TodaySystem(packetName);   
            ts.setUpShort();
            this.testingSystem();
        }
        //initalization of the GUI
        private void initLook() {
            panel=new JPanel();
            
            JLabel title=new JLabel(WED_ZERO.lang.getInstruct().get(5)[0]);
            title.setVisible(true);
            
            JLabel yourRes=new JLabel(WED_ZERO.lang.getInstruct().get(5)[1]);
            yourRes.setVisible(true);
            
            qArea=new JTextArea("~~~~~~~~~~", 4, 40);
            qArea.setEditable(false);
            qArea.setVisible(true);
            
            reArea=new JTextArea("~~~~~~~~~~", 8, 40);
            reArea.setEditable(true);
            reArea.setVisible(true);
            
            panel.add(title);
            panel.add(qArea);
            panel.add(yourRes);
            panel.add(reArea);

            submit=new JButton(WED_ZERO.lang.getInstruct().get(4)[1]);
            submit.setActionCommand("submit");
            submit.addActionListener(this);
            submit.setVisible(true);
            submit.setEnabled(false);
            
            clear=new JButton(WED_ZERO.lang.getInstruct().get(5)[2]);
            clear.setActionCommand("clear");
            clear.addActionListener(this);
            clear.setVisible(true);
            
            next=new JButton(WED_ZERO.lang.getInstruct().get(5)[3]);
            next.setActionCommand("next");
            next.addActionListener(this);
            next.setVisible(true);
            next.setPreferredSize(new Dimension(400, 60));
            next.setEnabled(false);
            panel.add(clear);
            panel.add(submit);
            panel.add(next);
            panel.setVisible(true);
            Launch.frame.add(panel);
        }
        private void testingSystem() {
            if(ts.hasEnded()) {
                this.endingSequence();
            }
            indexSP=ts.generateIndex();
            word=ts.getWord(indexSP);
            keywords=ts.getKey(indexSP);
            ts.removeWordFromWordKey(word);
            if(WED_ZERO.lang.getCurLang().equals("English")) {
                qArea.setText(WED_ZERO.lang.getInstruct().get(5)[4]+" \""+word+"\"?");
            } else {
                 qArea.setText(" \""+word+"\""+WED_ZERO.lang.getInstruct().get(5)[4]);
            }

            submit.setEnabled(true);
        }
        //the hands the ending of the test
        private void endingSequence() {
            JOptionPane.showMessageDialog(Launch.frame, WED_ZERO.lang.getInstruct().get(5)[5]);
            try {
            WED_ZERO.toMM(panel, Launch.frame);
        } catch (IOException ex) {
            ERROR.ERROR_358(Launch.frame);
        }
        }
        //Takes out the period at the end the answer
        private String removePeriod(String word) {
            if(word.substring(word.length()-1).equals(".")||word.substring(word.length()-1).equals("!")
                    ||word.substring(word.length()-1).equals("?")) {
                return word.substring(0, word.length()-2);
            }
            return word;
        }
        
        //process the response from the user
        private String[] processRes(String[] res) {
            ArrayList<String> tem=new ArrayList<String>();
            for(int x=0;x<res.length;x++) {
                tem.add(this.removePeriod(res[x]));
            }
            String temp="";
            for(int x=0;x<res.length;x++) {
                temp=res[x];
                for(int y=0;y<res.length;y++) {
                    if((x!=y)&&(temp.equals(res[y]))) {
                        tem.remove(x);
                        this.processRes(tem.toArray(new String[tem.size()]));
                    }
                }
            }
            return res;
        }
        //Counts the number of key word the users has got in their response
        private void checkAnswer() {
            String[] response=reArea.getText().split("\\s");
            response=this.processRes(response);
            int count=0;
            for(int x=0;x<response.length;x++) {
                for(int y=0;y<keywords.length;y++) {
                    if(response[x].equals(keywords[y])) {
                        count++;
                    }
                }
            }
            String assm="";
            if(count<keywords.length) {
                assm=WED_ZERO.lang.getInstruct().get(5)[6]+" "+keywords[0];
                for(int x=1;x<keywords.length-count-1;x++) {
                    assm+=", "+keywords[x];
                }
                assm+=WED_ZERO.lang.getInstruct().get(5)[7]+" "+keywords[keywords.length-1];
            }
            JOptionPane.showMessageDialog(Launch.frame, WED_ZERO.lang.getInstruct().get(5)[8]+"\n"
                    + count+"/"+keywords.length+"\n"
                    + WED_ZERO.lang.getInstruct().get(5)[9]+"!\n"
                    + assm);
            next.setEnabled(true);
        }
    @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("clear")) {
                reArea.setText("");
            } else if(e.getActionCommand().equals("submit")) {
                submit.setEnabled(false);
                this.checkAnswer();
            } else if(e.getActionCommand().equals("next")) {
                next.setEnabled(false);
                this.testingSystem();
            }
        }
}
