package WED_ZERO;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.JTableHeader;

/**
 *
 * @author SydnaAgnehs
 */
public class View implements ActionListener {
    private LoadAndSave las;
    private String[][] rowData;
    private String[] columnName = new String[] {WED_ZERO.lang.getInstruct().get(2)[2], 
                                                WED_ZERO.lang.getInstruct().get(2)[3],
                                                WED_ZERO.lang.getInstruct().get(2)[4],
                                                WED_ZERO.lang.getInstruct().get(2)[5]};
    private JButton button_aply, button_rvrt, back, delete, delWord;
    private TableCellListener tcl;
    private JTable table;
    private JScrollPane scrolPanel;
    private JPanel panel;
    private JComboBox jcb;
    private String currentSelection;
    private Map<Integer, DataChange> changes=new HashMap<Integer, DataChange>();
    
    //set up the GUI for the view pages
    public View(String packet) throws IOException {
        FileManaging fm = new FileManaging();
         fm.getFile(null);
         
         panel= new JPanel();
         delete=new JButton(WED_ZERO.lang.getInstruct().get(2)[1]);
         if(fm.getListOfFileInArray().length>0) {
            if(packet==null)
                currentSelection=fm.getListOfFileInArray()[0];
            else {
                currentSelection=packet;
            }
            

            las = new LoadAndSave();
            this.loadArray(currentSelection);
            jcb = new JComboBox(fm.getListOfFileInArray());
            jcb.setVisible(true);
            
            if(packet==null){
                jcb.setSelectedItem(fm.getListOfFileInArray()[0]);
            } else {
                outer:
                for(int x=0; x<fm.getListOfFileInArray().length;x++) {
                    if(packet.equals(fm.getListOfFileInArray()[x])) 
                    {
                    jcb.setSelectedItem(fm.getListOfFileInArray()[x]);
                    break outer;  
                    }
                }

            }
            jcb.setActionCommand("box");
            jcb.addActionListener(this);
         } else {
            jcb = new JComboBox(new Object[] {"None"});
            jcb.setVisible(true);
            rowData =new String[0][0];
            delete.setEnabled(false);
         }
         table = new JTable(rowData, columnName);
         JTableHeader header = table.getTableHeader();
         header.setBackground(Color.WHITE);
         
         button_aply = new JButton(WED_ZERO.lang.getInstruct().get(2)[8]);
         button_rvrt = new JButton(WED_ZERO.lang.getInstruct().get(2)[6]);
         button_rvrt.setBounds(330, 320, 100, 25);
         button_aply.setBounds(430, 320, 100, 25);
         button_aply.setActionCommand("apply");
         button_rvrt.setActionCommand("revert");
         button_aply.setVisible(true);
         button_aply.setEnabled(false);
         button_rvrt.setVisible(true);
         button_rvrt.setEnabled(false); 
         button_rvrt.addActionListener(this);
         button_aply.addActionListener(this);
         
         delete.setVisible(true);
         delete.setActionCommand("delete");
         delete.addActionListener(this);
         delWord=new JButton(WED_ZERO.lang.getInstruct().get(2)[7]);
         delWord.setActionCommand("delWord");
         delWord.addActionListener(this);
         delWord.setVisible(true);
         delWord.setEnabled(false);
         
         back=new JButton(WED_ZERO.lang.getInstruct().get(2)[0]);
         back.setActionCommand("back");
         back.addActionListener(this);
         back.setVisible(true);
         
         
         scrolPanel = new JScrollPane(table);
         scrolPanel.setPreferredSize(new Dimension(500, 280));
         scrolPanel.setVisible(true);
        
         
         table.setVisible(true);
         table.setPreferredSize(new Dimension(500, 300));
         table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
         table.getColumnModel().getColumn(0).setMinWidth(100);
         table.getColumnModel().getColumn(1).setMinWidth(150);
         table.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
           if(table.rowAtPoint(e.getPoint())>=0) {
               delWord.setEnabled(true);
           }
         }
         });
         
         panel.add(back);
         panel.add(jcb);
         panel.add(delete);
         panel.add(scrolPanel);
         panel.add(button_rvrt);
         panel.add(delWord);
         panel.add(button_aply);
         
         
         
         Action act = new AbstractAction() {
            @Override
          public void actionPerformed(ActionEvent e) {
              tcl = (TableCellListener)e.getSource();
              button_aply.setEnabled(true);
              button_rvrt.setEnabled(true);
              changes.put(changes.size(), new DataChange(tcl.getNewValue(), tcl.getOldValue(), tcl.getRow(), tcl.getColumn()));
              if(table.getSelectedRow()>=0) {
                delWord.setEnabled(true);
              }
    }};
         new TableCellListener(table, act);
         Launch.frame.add(panel);
    }
    
    
    //the method of loading the two dimensional array for the table used to set up the gui
    public boolean loadArray(String packet) throws FileNotFoundException {
            las.load(packet);

        rowData = new String[las.loader1.size()][4];        
        for(int r=0;r<las.loader1.size();r++) {
            rowData[r][0]=las.loader1.get(r);
            rowData[r][1]=las.loader2.get(r*3);
            rowData[r][2]=las.loader2.get(r*3+1);
            rowData[r][3]=las.loader2.get(r*3+2);
        }
        return true;
        
    }
    
    @Override
    public void actionPerformed (ActionEvent e) {
       if(e.getActionCommand().equals("delWord")) {
           int res = JOptionPane.showConfirmDialog(null, WED_ZERO.lang.getInstruct().get(2)[9], WED_ZERO.lang.getInstruct().get(2)[10],
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
           if(res == JOptionPane.YES_OPTION) {
               las.deleteWord(rowData[table.getSelectedRow()][0], jcb.getSelectedItem().toString(), table.getSelectedRow());
           }   
       }
       if(e.getActionCommand().equals("apply")) {
           String chng="";
           for(int x=0;x<changes.size();x++) {
                chng=chng+changes.get(x).getOldValue().toString()+" --->>> "+changes.get(x).getNewValue().toString()+"\n";
           }
           chng=WED_ZERO.lang.getInstruct().get(2)[11]+changes.size()+" "+WED_ZERO.lang.getInstruct().get(2)[10]+": \n"+chng;
           int res = JOptionPane.showConfirmDialog(null, chng+"\n"+WED_ZERO.lang.getInstruct().get(2)[9], WED_ZERO.lang.getInstruct().get(2)[10],
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);           
       if (res == JOptionPane.YES_OPTION) {
                try {
                    chng=null;
                    //if(las.editWord(tcl.getRow(), tcl.getColumn(), tcl.getNewValue()+"", jcb.getSelectedItem().toString())) {
                         for(int x=0;x<changes.size();x++) {
                            if(!las.editData(changes.get(x), jcb.getSelectedItem().toString())) {
                                chng="ERROR: "+changes.get(x).getOldValue().toString()+" --->>> "+changes.get(x).getNewValue().toString()+"\n";
                            }
                         }
                         if(chng==null)
                            JOptionPane.showMessageDialog(Launch.frame, WED_ZERO.lang.getInstruct().get(2)[13]);
                         else {
                            JOptionPane.showMessageDialog(Launch.frame, chng+" "+WED_ZERO.lang.getInstruct().get(2)[14]);
                        }
                         this.reload(jcb.getSelectedItem().toString());
                } catch (IOException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
       } else if(res == JOptionPane.NO_OPTION || res == JOptionPane.CLOSED_OPTION ){
            return;
       }
           button_aply.setEnabled(false);
           button_rvrt.setEnabled(false);
           
           
       } 
       if(e.getActionCommand().equals("back")) {
            try {
                WED_ZERO.toMM(panel, Launch.frame);
            } catch (IOException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("cant refresh");
            }
           button_aply.setEnabled(false);
           button_rvrt.setEnabled(false);  
       }
       if(e.getActionCommand().equals("revert")) {
            try {
                this.reload(null);
            } catch (IOException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
       
       if(e.getActionCommand().equals("box")) {
           if(!jcb.getSelectedItem().toString().equals(currentSelection)) {
               currentSelection=jcb.getSelectedItem().toString();
                try {
                    this.reload(jcb.getSelectedItem().toString());
                } catch (IOException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
               
           }
       }
       if(e.getActionCommand().equals("delete")) {
            System.out.println(table.getSelectedRow());
            int res = JOptionPane.showConfirmDialog(null, WED_ZERO.lang.getInstruct().get(2)[15]+" "+jcb.getSelectedItem().toString()+"?", WED_ZERO.lang.getInstruct().get(2)[10],
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(res==JOptionPane.YES_OPTION) {
                try {
                    new LoadAndSave().delete(jcb.getSelectedItem().toString());
                    this.reload(null);
                } catch (IOException ex) {
                    System.out.println("Cannot delete");
                }
            }
       }
    }
   
    //refreshes the page with the appriopate content of vocab of the selected packet
    private void reload(String packet) throws IOException {
        this.panel=null;
        Launch.frame.setVisible(false);
        Launch.frame=null;
        new Launch("view", 4, packet);
    }
    

}
