/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WED_ZERO;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author SydnaAgnehs
 */
public class Option implements ActionListener {
    private Log log=new Log();
    private ArrayList<JLabel> fields=new ArrayList<JLabel>();
    private JPanel panel;
    private File folder = new File("/Users/"+System.getProperty("user.name")+"/Library/Application Support/ProtoStar Softwares/A Word Each Day/");
    private JComboBox jcb;
        
    public Option() {
        this.initlook();
    }
    private void initlook() {
        panel=new JPanel();
        String[] logs=null;
        try {
            logs=log.getLog();
        } catch (IOException ex) {
            System.out.println("Fail");
        }
        JPanel topPanel=new JPanel();
        JLabel title=new JLabel(WED_ZERO.lang.getInstruct().get(3)[0]);
        
        
        title.setVisible(true);
        topPanel.add(title);
        String[] langs=WED_ZERO.lang.getLanguage();
        
        JPanel lowerThanTopPanel=new JPanel();
        
        jcb=new JComboBox(langs);
        jcb.setSelectedItem(WED_ZERO.lang.getCurLang());
        jcb.setActionCommand("jcb");
        jcb.addActionListener(this);
        jcb.setVisible(true);
        lowerThanTopPanel.add(jcb);
        
        
        lowerThanTopPanel.setPreferredSize(new Dimension(380, 50));
        topPanel.setPreferredSize(new Dimension(380, 50));
        topPanel.setVisible(true);
        lowerThanTopPanel.setVisible(true);
        panel.add(topPanel);
        panel.add(lowerThanTopPanel);
        JPanel interpanel=new JPanel(new GridLayout(2,2));
        interpanel.setPreferredSize(new Dimension(380, 100));
        fields.add(new JLabel(WED_ZERO.lang.getInstruct().get(3)[2]+": "+logs[0]));
        fields.add(new JLabel(WED_ZERO.lang.getInstruct().get(3)[2]+": "+logs[1]));
        fields.add(new JLabel(WED_ZERO.lang.getInstruct().get(3)[4]+": "+logs[2]));
        fields.add(new JLabel(WED_ZERO.lang.getInstruct().get(3)[5]+": "+logs[5]));
        for(int x=0;x<4;x++) {
            fields.get(x).setVisible(true);
            interpanel.add(fields.get(x));
        }
        interpanel.setVisible(true);
        panel.add(interpanel);
        
        
        JButton reset=new JButton(WED_ZERO.lang.getInstruct().get(3)[6]);
        reset.setBackground(Color.RED);
        reset.setOpaque(true);
        JButton back=new JButton(WED_ZERO.lang.getInstruct().get(3)[7]);
        back.setActionCommand("back");
        back.addActionListener(this);
        reset.setActionCommand("reset");
        reset.addActionListener(this);
        reset.setVisible(true);
        back.setVisible(true);
        
        JPanel bottomPanel=new JPanel();
        bottomPanel.add(reset);
        bottomPanel.add(back);
        bottomPanel.setVisible(true);
        
        bottomPanel.setPreferredSize(new Dimension(380, 100));
        panel.add(bottomPanel);
        panel.setVisible(true);
        Launch.frame.add(panel);
        
        
        
    }
    //reset the program by deleting everything so the user can
    //basically start over
    public void reset(File folder) {
        File[] listOfFiles = folder.listFiles();
        for(int x=0;x<listOfFiles.length;x++) {
            if(listOfFiles[x].isDirectory()) {
                this.reset(listOfFiles[x]);
                listOfFiles[x].delete();
            }
        }
        folder.delete();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("reset")) {
            int res = JOptionPane.showConfirmDialog(null, WED_ZERO.lang.getInstruct().get(3)[8], WED_ZERO.lang.getInstruct().get(3)[9],
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(res==JOptionPane.YES_OPTION) {
                this.reset(folder);
                try {
                WED_ZERO.toMM(panel, null);
            } catch (IOException ex) {
                System.out.println("Error");
                System.exit(0);
            }
            } else {
               return;
            }
        }else if(e.getActionCommand().equals("back")) {
            try {
                WED_ZERO.toMM(panel, Launch.frame);
            } catch (IOException ex) {
                System.out.println("Error");
                System.exit(0);
            }
        } else if(e.getActionCommand().equals("jcb")) {
            if(!jcb.getSelectedItem().equals(WED_ZERO.lang.getCurLang())) {
                if(jcb.getSelectedItem().equals("Import Language")) {
                    WED_ZERO.lang.processAction(panel);
                    return;
                }
                System.out.println("SET: "+jcb.getSelectedItem()+"");
                log.setLang(jcb.getSelectedItem()+"");
                WED_ZERO.lang.changeLang(jcb.getSelectedItem()+"");
                log.updateLog(null, jcb.getSelectedItem()+"");
                try {
                  WED_ZERO.toMM(panel, Launch.frame);
                } catch (IOException ex) {
                    System.out.println("Error");
                    System.exit(0);
                }
            }
        }
    }
}
