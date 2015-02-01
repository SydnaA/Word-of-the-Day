package WED_ZERO;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.String;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author SydnaAgnehs
 */
public class FileManaging implements ActionListener {
    private File Mac_FolderLoc=new File("/Users/"+
            System.getProperty("user.name")+
            "/Library/Application Support/ProtoStar Softwares/A Word Each Day/");
    private File Mac_Option=new File("/Users/"+
            System.getProperty("user.name")+
            "/Library/Application Support/ProtoStar Softwares/A Word Each Day/Options/");
    public static File Mac_DS_Store=new File("/Users/"+
            System.getProperty("user.name")+
            "/Library/Application Support/ProtoStar Softwares/A Word Each Day/.DS_Store");
    private JPanel panel;
    private JButton enter=new JButton(WED_ZERO.lang.getInstruct().get(1)[1]);
    private JButton send=new JButton(WED_ZERO.lang.getInstruct().get(1)[0]);
    private JButton back=new JButton(WED_ZERO.lang.getInstruct().get(1)[2]);
    private File folder = new File("/Users/"+
            System.getProperty("user.name")+
            "/Library/Application Support/ProtoStar Softwares/A Word Each Day/");
    private File[] listOfFiles = folder.listFiles();
    public ArrayList<File> listOfFile;
    private ArrayList<String> Button = new ArrayList();
    private Map<Integer, JButton> buttons= new HashMap<Integer, JButton>();
    public FileManaging() {
       this.getFile(null);
    }
    //initalization of the GUI
    public void initView() {
        JPanel pane= new JPanel();
        panel = new JPanel();
        this.getFile(pane);
        JScrollPane scp = new JScrollPane(pane);
        scp.setPreferredSize(new Dimension(500, 280));
        scp.setVisible(true);
        enter.setPreferredSize(new Dimension(100, 50));
        enter.setVisible(true);
        enter.setActionCommand("enter");
        enter.addActionListener(this);
        send.setPreferredSize(new Dimension(80, 50));
        send.setVisible(true);
        send.setActionCommand("sendOptions");
        send.addActionListener(this);
        send.setEnabled(true);
        back.setVisible(true);
        back.setActionCommand("back");
        back.addActionListener(this);
        back.setPreferredSize(new Dimension(80, 50));
        
        panel.add(scp);
        panel.add(send);
        panel.add(enter);
        panel.add(back);
        Launch.frame.add(panel);
    }
    //gets the different packets of vocab by reading all the packet folder names
    //then it processes out 
    //ds.store (default mac system file) 
    //mac options (for the options) 
    //the log
    public void getFile(JPanel panel) {
       listOfFile= new ArrayList();
        for(int x=0; x<listOfFiles.length;x++) {
            listOfFile.add(listOfFiles[x]);
        }
        if(FileManaging.isMac()) {
            if(listOfFile.contains(Mac_DS_Store))
            listOfFile.remove(Mac_DS_Store);
            if(listOfFile.contains(Mac_Option))
            listOfFile.remove(Mac_Option);
            //if(listOfFile.contains(Mac_Log))
              //  listOfFile.remove(Mac_Log);
        }
        if(panel!=null)
        for(int x=0; x<listOfFile.size();x++) {
            buttons.put(x, new JButton(listOfFile.get(x).getName()));
            buttons.get(x).setVisible(true);
            buttons.get(x).setActionCommand(x+"");
            buttons.get(x).addActionListener(this);
            buttons.get(x).setBackground(Color.green);
            panel.add(buttons.get(x));
        }
    }
    
    //help pust the list of files into an string array used to display in other classes
    public String[] getListOfFileInArray() {
       String[] items = new String[this.listOfFile.size()];
        for(int x=0;x<this.listOfFile.size();x++) {
            items[x]=this.listOfFile.get(x).getName();
        }
        return items;
   }
    // checks if the system is a mac, 
    //because the saving system is only supported on mac
    public static boolean isMac() {
        String os=System.getProperty("os.name").toLowerCase();
        if("mac".equals(os.substring(0,3)))
        {
         return true;
        }
        return false;
        
    }
    
    //resets the button to unclicked
    private void resetButton() {
        for(int x=0;x<buttons.size();x++) {
            if(buttons.get(x).isOpaque())
            buttons.get(x).setOpaque(false);
        }
    }
    
    //checks if there are enough vocab in the packet inorder to perform the tests
    private boolean checkOkay(ArrayList<String> packetName) throws IOException {
      LoadAndSave las =new LoadAndSave();
      int total=0;
      for(int x=0; x<packetName.size();x++) {
          las.load(packetName.get(x));
          total+=las.loader1.size();
      }
      if(total>=4)
      return true;
      ERROR.ERROR_355(Launch.frame);
      return false;
  }
    //If the users selects another button, the rest of the button should 
    //unselect itself.
    private void setAllButton(int index) {
        if(buttons.get(index).isOpaque()) {
            buttons.get(index).setOpaque(false);
        } else {
            buttons.get(index).setOpaque(true);
        }
    }
    
    //checks if the input is an integer
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
    //ables the program to recieve packets from another program through LAN
    private void recieveData() throws IOException, InterruptedException {
        String senderIP= JOptionPane.showInputDialog(WED_ZERO.lang.getInstruct().get(1)[17]);
        if(senderIP==null) {
            ERROR.ERROR_359(Launch.frame);
            return;
        }
        JOptionPane.showMessageDialog(Launch.frame, WED_ZERO.lang.getInstruct().get(1)[18]);
        Networking net=new Networking(true);
        net.setIPAddress(senderIP);
        String to=net.initClient();
        int res = JOptionPane.showConfirmDialog(null, WED_ZERO.lang.getInstruct().get(1)[19]+" "
                +to+".", WED_ZERO.lang.getInstruct().get(1)[12],
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE); 
        if(res==JOptionPane.NO_OPTION||res==JOptionPane.CANCEL_OPTION) {
            return;
        }
        Map<Integer, ArrayList<String>> data=new HashMap<Integer, ArrayList<String>>();
        ArrayList<String> folderName=new ArrayList();
        net.transferData_RECIEVE(folderName, data);
        this.processNewData(data, folderName);
        
    }
    //process the new data obtained through WIFI or LAN
    private void processNewData(Map<Integer, ArrayList<String>> data, ArrayList<String> folderName) throws IOException {
        for(int x=0; x<folderName.size();x++) {
            if(Arrays.asList(this.getListOfFileInArray()).contains(folderName.get(x))) {
                this.problemSolve(folderName.get(x), data.get(x*2), data.get(x*2+1)); 
            } else {
                this.addData(folderName.get(x), data.get(x*2), data.get(x*2+1));
            }
        }
    }
    //if there is an conflict between
    private void problemSolve (String folderName, ArrayList<String> loader1, ArrayList<String> loader2) throws IOException {
        Object[] pValue = { WED_ZERO.lang.getInstruct().get(1)[20], WED_ZERO.lang.getInstruct().get(1)[21],WED_ZERO.lang.getInstruct().get(1)[22], WED_ZERO.lang.getInstruct().get(1)[23] };
        Object selectedValue = JOptionPane.showInputDialog(null,
        WED_ZERO.lang.getInstruct().get(1)[24]+" "+"\""+folderName+"\" "+WED_ZERO.lang.getInstruct().get(1)[25], WED_ZERO.lang.getInstruct().get(1)[26], JOptionPane.INFORMATION_MESSAGE, null, pValue, pValue[0]);
        if(selectedValue==null||(selectedValue.equals(pValue[3]))) {
                int res = JOptionPane.showConfirmDialog(null, WED_ZERO.lang.getInstruct().get(1)[27], WED_ZERO.lang.getInstruct().get(1)[12],
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(res==JOptionPane.YES_OPTION) {
                  return;
                } else {
                   this.problemSolve(folderName, loader1, loader2);
                }
         } else if(selectedValue.equals(pValue[0])) {
                int res = JOptionPane.showConfirmDialog(null, WED_ZERO.lang.getInstruct().get(1)[28], WED_ZERO.lang.getInstruct().get(1)[12],
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(res==JOptionPane.YES_OPTION) {
                    this.replaceData(folderName, loader1, loader2);
                    this.refresh();
                } else {
                    this.problemSolve(folderName, loader1, loader2);
                }
         } else if(selectedValue.equals(pValue[1])) {
                    this.renameFolder(folderName, loader1, loader2);
                    this.refresh();
         } else if(selectedValue.equals(pValue[2])) {
                this.getFile(null);
                selectedValue = JOptionPane.showInputDialog(null,
                WED_ZERO.lang.getInstruct().get(1)[29]+": ", WED_ZERO.lang.getInstruct().get(1)[25], JOptionPane.INFORMATION_MESSAGE, null, this.getListOfFileInArray(), Arrays.asList(this.getListOfFileInArray()).indexOf(folderName));
                if(selectedValue!=null) {
                    this.merge(selectedValue.toString(), loader1, loader2);
                } else {
                   this.problemSolve(folderName, loader1, loader2);
                }
         }
    }
    
    //Renames the desired folder
    private void renameFolder(String folderName, ArrayList<String> loader1, ArrayList<String> loader2) throws IOException {
        String inputValue= JOptionPane.showInputDialog(WED_ZERO.lang.getInstruct().get(1)[30]);
         if(Arrays.asList(new FileManaging().getListOfFileInArray()).contains(inputValue)) {
                 ERROR.ERROR_356(Launch.frame);
                 this.renameFolder(inputValue, loader1, loader2);
         } else if(inputValue==null||inputValue.length()<1) {
                ERROR.ERROR_357(Launch.frame);
                this.problemSolve(folderName, loader1, loader2);
         } else {
                this.addData(inputValue, loader1, loader2);
         }
    }
    
    //replaces the old data with the new data
    private void replaceData(String folderName, ArrayList<String> loader1, ArrayList<String> loader2) throws IOException {
        LoadAndSave las =new LoadAndSave();
        las.load(folderName);
        las.loader1=loader1;
        las.loader2=loader2;
        las.save(folderName);
        
    }
    //Adds the new data to the end of the old data.
    private void merge(String folderName, ArrayList<String> loader1, ArrayList<String> loader2) throws IOException {
        LoadAndSave las =new LoadAndSave();
        las.load(folderName);
        for(int x=0;x<loader1.size();x++) {
            las.loader1.add(loader1.get(x));
            las.loader2.add(loader2.get(x*3));
            las.loader2.add(loader2.get(x*3+1));
            las.loader2.add("0/0");
        }
        las.save(folderName);
    }
    //creates a new folder and new files for the new vocabulary packet
    private void addData(String folderName, ArrayList<String> loader1, ArrayList<String> loader2) throws IOException {
        LoadAndSave las =new LoadAndSave();
        las.createNewFolder(folderName);
        for(int x=0;x<loader1.size();x++) {
            las.addWords(loader1.get(x), loader2.get(x*3), loader2.get(x*3+1), folderName);
        }
    }
    //ables the program to send packet over LAN
    private boolean sendData(ArrayList<String> packetName) {
        try {
            String recieverIP= JOptionPane.showInputDialog(WED_ZERO.lang.getInstruct().get(1)[12]);
            if(recieverIP==null||recieverIP.equals("")) {
                ERROR.ERROR_359(Launch.frame);
                return false;
            }
            Networking net=new Networking(false);
            net.setIPAddress(recieverIP);
            String to=net.initServer(System.getProperty("user.name"));
            int res = JOptionPane.showConfirmDialog(null, WED_ZERO.lang.getInstruct().get(1)[14]+" "+to+".", WED_ZERO.lang.getInstruct().get(1)[13],
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE); 
            if(res==JOptionPane.NO_OPTION||res==JOptionPane.CANCEL_OPTION) {
                return true;
            }
            if(net.transferData_SEND(this.initArrArrList(packetName), packetName)) {
                JOptionPane.showMessageDialog(Launch.frame, WED_ZERO.lang.getInstruct().get(1)[16]);
            } else {
                JOptionPane.showMessageDialog(Launch.frame, WED_ZERO.lang.getInstruct().get(1)[31]);
            }
            this.resetButton();
            return true;
        } catch (IOException ex) {
             ERROR.ERROR_364(Launch.frame);
            return false;
        }
        }
    //puts the packet names into a special arraylist of arraylists.
    private ArrayList<ArrayList<String>> initArrArrList(ArrayList<String> packetNames) throws FileNotFoundException, IOException {
        ArrayList<ArrayList<String>> arrList=new ArrayList();
        LoadAndSave las=new LoadAndSave();
        for(int x=0;x<packetNames.size();x++) {
            las.load(packetNames.get(x));
            arrList.add(las.loader1);
            arrList.add(las.loader2);
        }
        return arrList;
        
    }
    //
    private void processButtonState() {
        Button.clear();
        for(int x=0; x<buttons.size();x++) {
              if(buttons.get(x).isOpaque()) {
                Button.add(this.getListOfFileInArray()[x]);
              }
          }
    }
    //displays the options for send to the user
    private void sendOption() throws IOException, InterruptedException {
        Object[] pValue = { WED_ZERO.lang.getInstruct().get(1)[4], WED_ZERO.lang.getInstruct().get(1)[5]};
        Object selectedValue = JOptionPane.showInputDialog(null,
            WED_ZERO.lang.getInstruct().get(1)[3], WED_ZERO.lang.getInstruct().get(1)[0], JOptionPane.INFORMATION_MESSAGE, null, pValue, pValue[0]);
        if(selectedValue==null)
            return;
        if(selectedValue.equals(pValue[0])) {
            this.processButtonState();
            if(Button.size()==0) {
                JOptionPane.showMessageDialog(Launch.frame, WED_ZERO.lang.getInstruct().get(1)[32]);
                return;
            }
            Object[] o2={WED_ZERO.lang.getInstruct().get(1)[7], WED_ZERO.lang.getInstruct().get(1)[8]};
            selectedValue = JOptionPane.showInputDialog(null,
            WED_ZERO.lang.getInstruct().get(1)[33], WED_ZERO.lang.getInstruct().get(1)[0], JOptionPane.INFORMATION_MESSAGE, null, o2, o2[0]);
            
            if(selectedValue==null) {
                this.sendOption();
            } else if(selectedValue.equals(o2[0])) {
                this.sendLAN();
                this.resetButton();
            } else if(selectedValue.equals(o2[1])) {
                this.compileFile();
                this.resetButton();
            }
        } else if(selectedValue.equals(pValue[1])) {
            Object[] o2={WED_ZERO.lang.getInstruct().get(1)[7], WED_ZERO.lang.getInstruct().get(1)[8]};
            selectedValue = JOptionPane.showInputDialog(null,
            WED_ZERO.lang.getInstruct().get(1)[6], WED_ZERO.lang.getInstruct().get(1)[0], JOptionPane.INFORMATION_MESSAGE, null, o2, o2[0]);
            if(selectedValue==null) {
                this.sendOption();
            } else if(selectedValue.equals(o2[0])) {
                this.recieveData();
            } else if(selectedValue.equals(o2[1])) {
                this.readFile();
            }
        }
    }
    //reads the new files selected by the user.
    private boolean readFile() {
        JFileChooser jfc=new JFileChooser();
        jfc.setCurrentDirectory(new File("Users/"+System.getProperty("user.name")+"/Desktop"));
        jfc.setDialogTitle(WED_ZERO.lang.getInstruct().get(1)[34]);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.setFileFilter(new FileType());
        jfc.setVisible(true);
        if (jfc.showOpenDialog(panel)==JFileChooser.APPROVE_OPTION) {
            Reader read=new Reader(FileManaging.processLoc(jfc.getCurrentDirectory(), jfc.getSelectedFile()));
            try {
                this.processNewData(read.getMap(), read.getPacketName());
                return true;
            } catch (IOException ex) {
                ERROR.ERROR_362(Launch.frame);
                return false;
            }
        }
        return true;
    }
    //compiles the new files from the vocabulary packet selected by the user.
    private boolean compileFile() {
        
        JFileChooser jfc=new JFileChooser();
        jfc.setCurrentDirectory(new File("Users/"+System.getProperty("user.name")+"/Desktop"));
        jfc.setDialogTitle(WED_ZERO.lang.getInstruct().get(1)[35]);
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.setVisible(true);
        if (jfc.showSaveDialog(panel)==JFileChooser.APPROVE_OPTION) {
            try {
                new AlternativeSav(FileManaging.processLoc(jfc.getCurrentDirectory(), jfc.getSelectedFile()), Button);
                return true;
            } catch (IOException ex) {
                ERROR.ERROR_363(Launch.frame);
                return false;
            }
        }
        return false;
  
    }
    public static File processLoc(File curDir, File selFile) {
        StringTokenizer token=new StringTokenizer(selFile.toString(), "/");
        if(token.countTokens()<1) {
            return selFile;
        }
        String[] temp=new String[token.countTokens()];
        for(int x=0;x<temp.length;x++) 
            temp[x]=token.nextToken();
        if(temp[temp.length-1].equals(temp[temp.length-2])) {
            return curDir;
        }   
        return selFile;
        
    }
    private boolean sendLAN() {
        JOptionPane.showMessageDialog(Launch.frame, WED_ZERO.lang.getInstruct().get(1)[10]);
        return this.sendData(Button);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.isInteger(e.getActionCommand()))
        this.setAllButton(Integer.parseInt(e.getActionCommand()));
       if(e.getActionCommand().equals("enter")) {
           this.processButtonState();
            try {
                if(this.checkOkay(Button)) {
                   Object[] pValue = { WED_ZERO.lang.getInstruct().get(1)[36], WED_ZERO.lang.getInstruct().get(1)[37]};
                   Object selectedValue = JOptionPane.showInputDialog(null,
                             WED_ZERO.lang.getInstruct().get(1)[38], WED_ZERO.lang.getInstruct().get(1)[39], JOptionPane.INFORMATION_MESSAGE, null, pValue, pValue[0]);
                   if(selectedValue==null) {
                         return;
                   }
                   if(selectedValue.equals(pValue[0])) {
                       this.panel.setVisible(false);
                        new Qtoday(Button);
                   } else if(selectedValue.equals(pValue[1])) {
                       this.panel.setVisible(false);
                        new KeyTest(Button);
                   }
                }
            } catch (IOException ex) {
                 ERROR.ERROR_362(Launch.frame);
                 return;
            }
          
       }
       if(e.getActionCommand().equals("sendOptions")) {
            try {
                this.sendOption();
            } catch (IOException ex) {
                ERROR.ERROR_362(Launch.frame);
                return;
            } catch (InterruptedException ex) {
                ERROR.ERROR_365(Launch.frame);
                return;}
       }
       if(e.getActionCommand().equals("back")) {
            try {
                WED_ZERO.toMM(panel, Launch.frame);
            } catch (IOException ex) {
                ERROR.ERROR_362(Launch.frame);
                return;
            }
       }
   }
    //refreshes the GUI to include the latest files
    private void refresh() throws IOException {
        Launch.frame.setVisible(false);
        Launch.frame=null;
        new Launch("TodayQ", 1, null);
    }
    //creates a special file extension, so it is easier to the user to 
    //find the file
    class FileType extends FileFilter {
        @Override
        public boolean accept(File f) {
            return f.isDirectory()||f.getName().endsWith(".WED");
        }
        @Override
        public String getDescription() {
            return ".WED "+WED_ZERO.lang.getInstruct().get(1)[40];
        }
    }
   
   
}
