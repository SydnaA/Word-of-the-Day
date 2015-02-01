/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WED_ZERO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import javax.swing.JPanel;

/**
 *
 * @author SydnaAgnehs
 */
public class Log {
    private String Mac_Option_Loc="/Users/"+System.getProperty("user.name")+
                "/Library/Application Support/ProtoStar Softwares/A Word Each Day/Options/";
    private File Mac_log_sav=new File("/Users/"+System.getProperty("user.name")+"/Library/Application Support/ProtoStar Softwares/A Word Each Day/Options/log.sav");
    private String Mac_log_temp_txt="/Users/"+System.getProperty("user.name")+"/Library/Application Support/ProtoStar Softwares/A Word Each Day/Options/log.txt";
    private int tutStage=0;
    private String lang=null;
    public Log() {
        if(this.logExist()) {
            this.updateLog(null, null);
            WED_ZERO.lang.changeLang(lang);
       } else {
            new File(Mac_Option_Loc).mkdirs();
           lang=WED_ZERO.lang.setLanguage(new JPanel());
           this.initNewLog(true);
       }
    }
    public boolean logExist() {
        return Mac_log_sav.exists()&&Mac_log_sav.canRead()&&Mac_log_sav.canWrite();
    }
    //creates new log with its inital values
    private boolean initNewLog(boolean n) {
        try {
            Mac_log_sav.createNewFile();
            File tempFile=new File(Mac_log_temp_txt);
            FileWriter file=new FileWriter(tempFile, true);
            PrintWriter output=new PrintWriter(file);
                
                output.println(LoadAndSave.currentVersion);
                output.println(System.getProperty("user.name"));
                Calendar cal=Calendar.getInstance();
                SimpleDateFormat dateformatter = new SimpleDateFormat("MM/dd/yyyy");
                output.println(dateformatter.format(cal.getTime()));
                output.println(lang);
                output.println(0);
                String[] names=new FileManaging().getListOfFileInArray();
                output.println(names.length);
            output.close();
            file.close();
            tempFile.renameTo(Mac_log_sav);
            return true;
        } catch (IOException ex) {
            System.out.println("CANNOT CREATE NEW LOG");
            return false;
        }
    }
    //reads the log of the disk
    public String[] getLog() throws IOException {
        File tempFile=new File(Mac_log_temp_txt);
        Mac_log_sav.renameTo(tempFile);
        Scanner scan=new Scanner(tempFile);
        ArrayList<String> arr=new ArrayList();
        while(scan.hasNext()) {
            arr.add(scan.nextLine());
        }
        scan.close();
        tempFile.renameTo(Mac_log_sav);
        String[] a=new String[arr.size()];
        for(int x=0;x<arr.size();x++) {
            a[x]=arr.get(x);
        }
        lang=a[3];
        tutStage=Integer.parseInt(a[4]);
        return a;
    }
    public int getTutStage() {
        return tutStage;
    }
    public String getLang() {
        return lang;
    }
    
    public void setLang(String lang) {
        this.lang=lang;
    }
    //update the log to have the current information on the program
    public void updateLog(String tutStage, String lang) {
        try {
            String[] log=this.getLog();
            String[] names=new FileManaging().getListOfFileInArray();
            if(lang!=null) {
                log[3]=lang;
            } else {
                log[3]=this.lang;
            }
            if(tutStage==null){
                log[4]=this.tutStage+"";
            } else {
                log[4]=tutStage;
            }
            log[5]=names.length+"";
            File f=new File(Mac_log_temp_txt);
            FileWriter file=new FileWriter(f, true);
            PrintWriter output=new PrintWriter(file);
                for(int x=0;x<log.length;x++) {
                    output.println(log[x]);
                }
            file.close();
            f.renameTo(Mac_log_sav);
        } catch (IOException ex) {
            System.out.println("Error: Cannot update log");
            System.exit(0);
        }
    }
  
}
