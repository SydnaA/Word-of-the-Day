package WED_ZERO;

/**
 *
 * @author SydnaAgnehs
 */
public class WordKeyHolder {
    private String word=null;
    private String[] keywords=null;
    public WordKeyHolder(String word, String[] keywords) {
        this.word=word;
        this.keywords=keywords;
    }
    public String getWord() {
        return this.word;
    }
    public String[] getKeywords() {
        return this.keywords;
    }

}
