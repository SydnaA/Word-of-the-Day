package WED_ZERO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SydnaAgnehs
 */
public class TodaySystem {
    private String[] packetN;
    private LoadAndSave las;
    private DataSyn dataSyn;
    private Map<Integer, DataSyn> organizer;
    private ArrayList<String> wordPool;
    private int totalCount;
    private WordKeyHandler wkh;
    
    public TodaySystem(ArrayList<String> packetName) {
        packetN=new String[packetName.size()];
        for(int x=0;x<packetN.length;x++)
            packetN[x]=packetName.get(x);
        try {
            las= new LoadAndSave();
            //this.load(packetName);
            this.organizerLoad(packetName);
        } catch (IOException ex) {
            System.out.println("Cannot loadFile");
        }
    }
    
    private void organizerLoad(ArrayList<String> packetNames) throws FileNotFoundException {
        //ArrayList<String> wordPool=new ArrayList();
        organizer=new HashMap<Integer, DataSyn>();
        dataSyn=new DataSyn();
        ArrayList<String> tLoader1= new ArrayList();
        ArrayList<String> tLoader2= new ArrayList();
        for(int x=0;x<packetNames.size();x++) {
            las.load(packetNames.get(x));
            for(int y=0; y<las.loader1.size();y++) {
                tLoader1.add(las.loader1.get(y));
                totalCount+=1;
                //wordPool.add(las.loader1.get(y));
            }
            for(int z=0; z<las.loader2.size();z++) {
                tLoader2.add(las.loader2.get(z));
            }
            organizer.put(x, new DataSyn(tLoader1, tLoader2));
            
            tLoader1.clear();
            tLoader2.clear();
            
        }
}
    
    public void setUpShort() {
        wkh=new WordKeyHandler(organizer);
        for(int x=0;x<wkh.getSize();x++) {
            
        }
        return;
    }
    public int generateIndex() {
        return (int)(Math.random())*wkh.getSize();
    }
    public String getWord(int index) {        
        return wkh.getWordKey().get(index).getWord();
    }
    public String[] getKey(int index) {
        return wkh.getWordKey().get(index).getKeywords();
    }
    public boolean removeWordFromWordKey(String word) {
        return wkh.remove(word);
    }
    public boolean hasEnded() {
        if(wkh.getSize()==0) {
            return true;
        }
        return false;
    }

    public String[] getOrganizerSetup() {
        if(wordPool!=null&&this.checkEnd()) {
            return null;
        }        
        String[] str=new String[8];
        str[7]=""+(int)(Math.random()*(organizer.size()));
        //took out -1 on getCount()
        str[6]=""+(int)(Math.random()*(organizer.get(Integer.parseInt(str[7])).getCount()));
        str[5]=organizer.get(Integer.parseInt(str[7])).getWord(Integer.parseInt(str[6]));
        str[4]=organizer.get(Integer.parseInt(str[7])).getDefinition(Integer.parseInt(str[6]));
        this.initWordPool(str);
        organizer.get(Integer.parseInt(str[7])).removeWord(Integer.parseInt(str[6]));
        for(int x=0;x<4;x++) {
            str[x]=this.pickRanWord(str);
        }
        totalCount-=1;
        //new
        for(int x=0;x<organizer.size();x++) {
            if(organizer.get(x).getCount()==0) {
                organizer.remove(x);
            }
        }
        return str;
    }
    
    public int getTotalCount() {
        return totalCount;
    }
    
    private void initWordPool(String[] str) {
        wordPool=new ArrayList();
        for(int x=0;x<organizer.size();x++) {
            for(int y=0;y<organizer.get(x).getCount();y++) {
                    if(!organizer.get(x).getWord(y).equals(str[5])) {
                        wordPool.add(organizer.get(x).getWord(y));
                }
            }
        }
    }
    
    private String pickRanWord(String[] str) {
        ArrayList<String> arr=new ArrayList();
        arr=wordPool;
        if(arr.contains(str[5])) {
            arr.remove(str[5]);
        }
        String word=arr.get((int)(Math.random()*(wordPool.size()-1)));
        return word;
        
    }
    
    private boolean checkEnd() {
        if(totalCount<=4) {
            return true;
        }
        return false;
    }
    
    public String[] getPackets() {
        return packetN;
    }
    
    public boolean updatePoints(boolean correct, String[] str) {
        try {
            las.load(packetN[Integer.parseInt(str[7])]);
            
            if(correct) {
                this.EditRatio(1, las.loader2, Integer.parseInt(str[6]));
            } else {
                this.EditRatio(-1, las.loader2, Integer.parseInt(str[6]));
            }
            las.save(packetN[Integer.parseInt(str[7])]);
            return true;
        } catch (IOException ex) {
            ERROR.ERROR_360(Launch.frame);
            return false;
        }
    }

    private void EditRatio(int increasePoint, ArrayList<String> loader, int index) {
        for(int x=0; x<loader.get((index*3)+2).length();x++) {
            if(loader.get((index*3)+2).substring(x, x+1).equals("/")) {
                if(index<0) {
                   loader.set(index*3+2, (loader.get((index*3)+2).substring(0, x+1)+(increasePoint+Integer.parseInt(loader.get((index*3)+2).substring(x+1)))+""));
                } else {
                   loader.set(index*3+2, (loader.get((index*3)+2).substring(0, x-1)+(Integer.parseInt(loader.get((index*3)+2).substring(0, 1))+increasePoint)+""+loader.get((index*3)+2).substring(x)));
                }
            }
        }
        return;
    }
    
}
