package WED_ZERO;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SydnaAgnehs
 * This class basically works with WordKeyHolder to extract
 * and link up the vocabulary terms and keywords.
 */
public class WordKeyHandler {
    private Map<Integer, WordKeyHolder> wordKey;
    
    public WordKeyHandler(Map<Integer, DataSyn> organizer) {
        wordKey=new HashMap<Integer, WordKeyHolder>();
        for(int x=0;x<organizer.size();x++) {
            for(int y=0;y<organizer.get(x).getDataMap().size();y++) {
            wordKey.put(x, new WordKeyHolder(organizer.get(x).getWord(y), organizer.get(x).getKey(y)));
            }
        }
        return;
    }
    public Map<Integer, WordKeyHolder> getWordKey() {
        return wordKey;
    }
    public int getSize() {
        return wordKey.size();
    }
    public boolean remove(String word) {
        for(int x=0;x<wordKey.size();x++) {
            if(wordKey.get(x).getWord().equals(word)) {
                wordKey.remove(wordKey.get(x));
                return true;
            }
        }
       return false;
    }
}
