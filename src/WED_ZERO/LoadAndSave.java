package WED_ZERO;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author SydnaAgnehs
 */
public class LoadAndSave {
    public File Mac_savLoc_Folder=new File("/Users/"+System.getProperty("user.name")+"/Library/Application Support/ProtoStar Softwares/");
    public File Mac_sacLoc_coFolder=new File("/Users/"+System.getProperty("user.name")+"/Library/Application Support/ProtoStar Softwares/A Word Each Day/");
    public File Mac_savLoc_txt_temp=new File("/Users/"+System.getProperty("user.name")+"/Library/Application Support/ProtoStar Softwares/A Word Each Day/save_tmp.txt");
    public File Mac_savLoc_txt2_temp=new File("/Users/"+System.getProperty("user.name")+"/Library/Application Support/ProtoStar Softwares/A Word Each Day/save2_tmp.txt");
    private String pathy_txt= "/save.txt";
    private String pathy2_txt= "/save2.txt";
    private String pathy2_sav= "/save2.sav";
    private String pathy_sav= "/save.sav";
    private String[] words;
    private DataSyn datasyn;
    public static String currentVersion="0.0.0.1";
    public ArrayList<String> loader1, loader2;
    public LoadAndSave() throws IOException {
        if(this.checkExist()) {
          FileManaging fm = new FileManaging();
          fm.getFile(null);
          if(fm.getListOfFileInArray().length>0) 
          this.load(fm.getListOfFileInArray()[0]);
        }
        datasyn=new DataSyn();
    } 
    //return true if the folders already exist 
    //else return false
    private boolean checkExist() throws IOException {
        if(Mac_sacLoc_coFolder.exists()) {
             if(Mac_sacLoc_coFolder.canRead()&&Mac_sacLoc_coFolder.canWrite())
                return true;
             else {
                 ERROR.ERROR_354(Launch.frame);
                 Mac_sacLoc_coFolder.mkdirs();
                 return true;
             }
        } else {
            Mac_sacLoc_coFolder.mkdirs();
        }
        return false;
    }
    //Changes a word in the program by the index value
    public boolean changeWord(int index, String a) throws IOException {
        if(this.loader1.contains(a)) {
            ERROR.ERROR_351(Launch.frame);
            return false;
        }
        this.loader1.set(index, a);
        return true;
    }
    //Changes the word or any property of the word
    public boolean editWord(int r, int c, String word, String packet) throws IOException {
        if(c==0) {
            this.changeWord(r, word);
        } else {
            this.changeDefinitionNother((r*3)+c-1, word);
        }     
        return this.save(packet); 
    }
    private void changeDefinitionNother (int index, String a) {
        this.loader2.set(index, a);
    }
    //writes the word and the property of the word onto the disk.
    public boolean save(String packet) throws IOException {
        packet="/"+packet;
        FileWriter file;
        PrintWriter output;
        file=new FileWriter(Mac_savLoc_txt_temp, true);
        output=new PrintWriter(file);
        for(int x=0; x<this.loader1.size();x++) {
            output.println(this.loader1.get(x));
        }
        output.close();
        file.close();
        file=new FileWriter(Mac_savLoc_txt2_temp, true);
        output=new PrintWriter(file);
        for(int x=0; x<this.loader2.size();x++) {
            output.println(this.loader2.get(x));
        }
        output.close();
        file.close();
        
        File temp=new File(Mac_sacLoc_coFolder.toString()+packet+pathy_sav);
        File temp2=new File(Mac_sacLoc_coFolder.toString()+packet+pathy2_sav);
        Mac_savLoc_txt_temp.renameTo(temp);
        Mac_savLoc_txt2_temp.renameTo(temp2);
        
        return true;
    }

    //creates new folder
    public void createNewFolder(String name) throws IOException {
        name=Mac_sacLoc_coFolder+"/"+name;
        new File(name).mkdir();
        this.createSave(name);
    }
    //loads the selected packet of words
    public boolean load(String packet) throws FileNotFoundException {
        packet="/"+packet;
        File temp=new File(Mac_sacLoc_coFolder+packet+pathy_sav);
        if(!temp.exists()) {
            temp.delete();
            return false;
        }
        File temp_txt=new File(Mac_sacLoc_coFolder+packet+pathy_txt);
        temp.renameTo(temp_txt);
        Scanner scanner=new Scanner(temp_txt);
        int maxIndx=-1;
        loader1=new ArrayList();
        while(scanner.hasNext())
        {
            maxIndx++;
            loader1.add(scanner.nextLine());
        }
        scanner.close();
        temp_txt.renameTo(temp);
        temp=new File(Mac_sacLoc_coFolder.toString()+packet+pathy2_sav);
        temp_txt=new File(Mac_sacLoc_coFolder.toString()+packet+pathy2_txt);
        temp.renameTo(temp_txt);
        scanner=new Scanner(temp_txt);
        maxIndx=-1;
        loader2=new ArrayList();
        while(scanner.hasNext())
        {
            maxIndx++;
            loader2.add(scanner.nextLine());
        }
        scanner.close();
        temp_txt.renameTo(temp);
        int c=0;
        words=new String[loader1.size()+loader2.size()];
        for(int x=0; x<loader1.size();x++) {
            if(x==0||x%4==0) {
             words[x]=loader1.get(x);
            } else {
            words[c]=loader2.get(x);
            c++;
            }
        }
        return true;
    }
    
    
    
    
    //add the word and its property into the program
    public boolean addWords(String word, String def, String c, String loc) throws FileNotFoundException, IOException {
        this.load(loc);
        loader1.add(word);
        loader2.add(def);
        loader2.add(c);
        loader2.add("0/0");
        
        String path=Mac_sacLoc_coFolder+"/"+loc;
        File tempFile= new File(path+pathy_sav);
        File tempFile2= new File(path+pathy2_sav);
        File tempFilea2= new File(path+pathy_txt);
        
        tempFile.renameTo(tempFilea2);

        FileWriter Sfile=new FileWriter(tempFilea2);
        PrintWriter output=new PrintWriter(Sfile);
        for(int x=0;x<loader1.size();x++) {
                output.println(loader1.get(x));
        }
        output.close();
        Sfile.close();
        tempFilea2.renameTo(tempFile);
        
        tempFilea2=new File(path+pathy2_txt);
        tempFile2.renameTo(tempFilea2);
        
        Sfile=new FileWriter(tempFilea2);
        output=new PrintWriter(Sfile);
        for(int x=0;x<loader2.size();x++) {
                output.println(loader2.get(x));
        }
        output.close();
        Sfile.close();
        tempFilea2.renameTo(tempFile2);
        
        return true;
    }
    //for editing the word or its property
    public boolean editData(DataChange data, String packet) throws IOException {
        if(data.getCol()==0) {
            this.changeWord(data.getRow(), data.getNewValue().toString());
        } else {
            this.changeDefinitionNother((data.getRow()*3)+data.getCol()-1, data.getNewValue().toString());
        }     
        return this.save(packet); 
    }
    private void createSave(String location) throws IOException {
        new File(location+pathy_sav).createNewFile();
        new File(location+pathy2_sav).createNewFile();
        
    }
    
    //To delete a word from the selected packet
    public boolean deleteWord(String word, String packet, int row) {
        try {
            this.changeWord(row, "");
            this.changeDefinitionNother((row*3), "");
            this.changeDefinitionNother((row*3)+1, "");
            this.changeDefinitionNother((row*3)+2, "");
            this.changeDefinitionNother((row*3)+4, "");
            return true;
        } catch (IOException ex) {
            System.out.println("Unable to delete word");
            return false;
        }
    }
    //To delete the selected packet
    public boolean delete(String packet) {
        packet=Mac_sacLoc_coFolder+"/"+packet;
        new File(packet+pathy_sav).delete();
        new File(packet+pathy2_sav).delete();
        return new File(packet).delete();
    }
}
