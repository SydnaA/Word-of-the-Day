/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WED_ZERO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author SydnaAgnehs
 */
public class LangSupport {
    public LangSupport() {
        
    }
    public static ArrayList<String[]> instruct=new ArrayList<String[]>();
    private String curLang="";
    
    //initializes the default instructions in English for the entire program
    public void initDefault() {
        //0
        instruct.clear();
        
        String[] mainClass=new String[12];
        mainClass[0]="A Word Each Day";
        mainClass[1]="Today";
        mainClass[2]="Add a word";
        mainClass[3]="View Words";
        mainClass[4]="Options";
        mainClass[5]="New Vocab";
        mainClass[6]="New Folder";
        mainClass[7]="Definition";
        mainClass[8]="Required keyword for definition";
        mainClass[9]="Add";
        mainClass[10]="Cancel";
        mainClass[11]="Enter desired folder's name";
        instruct.add(mainClass);
        
        //1
        String[] Qtoday=new String[41];
        Qtoday[0]="Sharing";
        Qtoday[1]="ENTER";
        Qtoday[2]="Back";
        Qtoday[3]="Send or receive from friend";
        Qtoday[4]="Send";
        Qtoday[5]="Recieve";
        Qtoday[6]="Lan or File";
        Qtoday[7]="Lan";
        Qtoday[8]="File";
        Qtoday[9]="Message";
        Qtoday[10]="Please do not close this program while sending data";
        Qtoday[11]="Input";
        Qtoday[12]="Enter the IP address of the receiver";
        Qtoday[13]="Confirm";
        Qtoday[14]="Sending packet to";
        Qtoday[15]="Message";
        Qtoday[16]="Transfer Successful! Thank you for your patience!";
        Qtoday[17]="Enter the IP address of the sender";
        Qtoday[18]="Please do not close this program while sending data";
        Qtoday[19]="Receiving packet from";
        Qtoday[20]="Replace";
        Qtoday[21]="Rename";
        Qtoday[22]="Merge";
        Qtoday[23]="Cancel";
        Qtoday[24]="The name";
        Qtoday[25]="already exists.";
        Qtoday[26]="Warning!";
        Qtoday[27]="Cancel? Then new data then will be deleted\nAre you sure?";
        Qtoday[28]="\nReplace?\nThe old data will be deleted\nAre you sure?";
        Qtoday[29]="Merge with";
        Qtoday[30]="Enter desired folder name";
        Qtoday[31]="Error, please try again later and check your proxy settings";
        Qtoday[32]="Must have more than 1 packet selected!";
        Qtoday[33]="Lan- Through wifi \n File- Compiles a special file, \nSo you can send it to them using other methods";
        Qtoday[34]="Open";
        Qtoday[35]="Save to";
        Qtoday[36]="Multiple Choice";
        Qtoday[37]="Short Response";
        Qtoday[38]="Which testing method?";
        Qtoday[39]="Testing Methods Selection";
        Qtoday[40]="files types";
        
        instruct.add(Qtoday);
        
        //2
        String[] View= new String[16];
        View[0]="Back";
        View[1]="Delete folder";
        View[2]="Vocab";
        View[3]="Definition";
        View[4]="Key";
        View[5]="Rate";
        View[6]="Revert";
        View[7]="Delete word";
        View[8]="Apply";
        View[9]="Are you sure?";
        View[10]="Confirm";
        View[11]="The following ";
        View[12]="will be made";
        View[13]="Change successfully saved!";
        View[14]="The changes cannot be made due to some error";
        View[15]="Delete";
        instruct.add(View);
        
        //3
        String[] option= new String[10];
        option[0]="Option";
        option[1]="Language";
        option[2]="Version";
        option[3]="User";
        option[4]="Created";
        option[5]="Numbers of Packet";
        option[6]="RESET";
        option[7]="Back";
        option[8]="There is no way to undo this!";
        option[9]="Confirm";
        instruct.add(option);
        
        //4
        String[] today=new String[5];
        today[0]="QUESTION";
        today[1]="Submit";
        today[2]="Results";
        today[3]="Correct";
        today[4]="Wrong";
        instruct.add(today);
        
        //5
        String[] keyTest=new String[10];
        keyTest[0]="Short Response Question";
        keyTest[1]="Please type your answers below";
        keyTest[2]="Clear";
        keyTest[3]="Next";
        keyTest[4]="What is the definition of";
        keyTest[5]="How did you do?";
        keyTest[6]="You missed out on";
        keyTest[7]="and";
        keyTest[8]="You got";
        keyTest[9]="of the keywords in your short answer response";
        instruct.add(keyTest);
        
        //6
        String[] tut=new String[7];
        tut[0]="Welcome,\n"
                        + "Why not get started?\n"
                        + "First press the \n"
                        + "\"Add a Word\" button!";
        tut[1]="Since it is your first time,\n"
                        + " why dont you create a new packet.";
        tut[2]="Next fill out all the blanks\n"
                        + "then press ADD";
        tut[3]="Well Done!\n"
                        + "Next why don't you press the \"view\" button\n"
                        + "and check out our newly added word!";
        tut[4]="Now, go back and add 5 more vocabs :D";
        tut[5]="If you have more than 4 vocab, you can take a test :)";
        tut[6]="How did your test go? Well, you atleast passed tutorial :)";
        instruct.add(tut);
        
    }
    
    private String Mac_Option_Loc="/Users/"+System.getProperty("user.name")+
                "/Library/Application Support/ProtoStar Softwares/A Word Each Day/Options/";
    
    
    
    public String[] getLanguage() {
        new File(Mac_Option_Loc+"/Lang/").mkdirs();
        File[] listOfFiles = new File(Mac_Option_Loc+"/Lang/").listFiles();
        ArrayList<File> listOfFile= new ArrayList();
        for(int x=0; x<listOfFiles.length;x++) {
            listOfFile.add(listOfFiles[x]);
        }
         if(FileManaging.isMac()) {
            if(listOfFile.contains(FileManaging.Mac_DS_Store))
            listOfFile.remove(FileManaging.Mac_DS_Store);
            
         }
         String[] langs=new String[listOfFile.size()+2];
         langs[0]="English";
         langs[langs.length-1]="Import Language";
         for(int x=1;x<langs.length-1;x++) {
             langs[x]=this.getLangName(listOfFiles[x-1].getAbsolutePath());
         }
        return langs;
    }
    
    public String setLanguage(JPanel panel) {
       String[] langs=this.getLanguage();
        Object selValue = JOptionPane.showInputDialog(null,
        "", "Language", JOptionPane.INFORMATION_MESSAGE, null, langs, langs[0]);
       
        
        if(selValue.equals("Import Language")) {
            this.processAction(panel);
            
        } else if(selValue.equals("English")) {
            this.initDefault();
            curLang="English";
        } else {
            this.loadLanguage(selValue+"");
            curLang=selValue+"";
        }
       return curLang;
    }
    
    //lets the user find the language pack they want to add
    public void processAction(JPanel panel) {
        JFileChooser jfc=new JFileChooser();
        jfc.setCurrentDirectory(new File("Users/"+System.getProperty("user.name")+"/Desktop"));
        jfc.setDialogTitle("Import Language");
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.setFileFilter(new LangSupport.FileType());
        jfc.setVisible(true);
        if (jfc.showOpenDialog(panel)==JFileChooser.APPROVE_OPTION) {
           this.moveLoc(FileManaging.processLoc(jfc.getCurrentDirectory(), jfc.getSelectedFile()));
           curLang=this.setLanguage(panel);
        }
    }
  
    //moves the language support file into the program designated ata's folder
    public void moveLoc(File path) {
        path.renameTo(new File("/Users/"+System.getProperty("user.name")+
                "/Library/Application Support/ProtoStar Softwares/A Word Each Day/Options/Lang/"+
                this.getLangName(path.getAbsolutePath()+"")+".WED_LANG"));
    }
    
    //Reformat the file name into a readable string
    private String getLangName(String file) {
        for(int x=file.length()-9;x>0;x--) {
            if(file.substring(x-1, x).equals("/")) {
                return file.substring(x, file.length()-9);
            }
        }
        return null;
    }
    
    //loads the language file into the program
    public void loadLanguage(String target) {
        try {
            File tempFile=new File(Mac_Option_Loc+"/Lang/"+target+".txt");
            File tar=new File(Mac_Option_Loc+"/Lang/"+target+".WED_LANG");
            tar.renameTo(tempFile);
            Scanner scan=new Scanner(tempFile);
            for(int x=0;x<instruct.size();x++) {
                for(int y=0;y<instruct.get(x).length;y++) {
                    instruct.get(x)[y]=scan.nextLine();
                }
            }
            scan.close();
            tempFile.renameTo(tar);
        } catch (FileNotFoundException ex) {
            System.out.println("Cannot read file");
        }
        return;
    }
    
    public ArrayList<String []> getInstruct() {
        return instruct;
    }
    
    public String getCurLang() {
        return curLang;
    }
    //changes the language to the input
    public boolean changeLang(String newLang) {
        curLang=newLang;
        if(newLang.equals("English")) {
            this.initDefault();
        } else {
            this.loadLanguage(newLang);
        }
        
        
        return true;
    }
    
    //special filetype to help the users find the language pack
    class FileType extends FileFilter {
        @Override
        public boolean accept(File f) {
            return f.isDirectory()||f.getName().endsWith(".WED_LANG");
        }
        @Override
        public String getDescription() {
            return ".WED_LANG files";
        }
    }
   
    
}
