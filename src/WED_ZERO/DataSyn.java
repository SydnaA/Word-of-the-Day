package WED_ZERO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SydnaAgnehs
 * 
 * This class basically help reconstruct the vocabulary word
 * with its definition, keyword and ratio
 */
public class DataSyn {
    
    private Map<Integer, Data> dataM;
    private int size;
    
    public DataSyn() {
        dataM=new HashMap<Integer, Data>();
        
    }
    public DataSyn(ArrayList l1, ArrayList l2) {
        dataM=new HashMap<Integer, Data>();
        for(int x=0;x<l1.size();x++) {
            dataM.put(x, new Data(l1.get(x)+"", ""+l2.get(x*3), this.intoKey(l2.get(x*3+1)+""), RatioHandler.toRatio(l2.get(x*3+2)+"")));
        }
        size=dataM.size();
    }
    private String[] intoKey(String a) {
        return a.split("\\s");
    }
    public int getCount() {
        return dataM.size();
    }
    public Map getDataMap() {
        return dataM;
    }
    public String getWord(int index) {
        return dataM.get(index).getWord();
    }
    public String getDefinition(int index) {
        return dataM.get(index).getDef();
    }
    public String[] getKey(int index) {
        return dataM.get(index).getKey();
    }
    public boolean removeWord(int index) {
        dataM.remove(dataM.get(index));
        //size--;
        //maybe need to redo key
        return true;
    }

}
