package WED_ZERO;

/**
 *
 * @author SydnaAgnehs
 */
public class Data {
    private String word;
    private String def;
    private String[] key;
    private RatioHandler rh;
    public Data() {
        
    }
    public Data(String word, String def, String[] key, RatioHandler rh) {
        this.word=word;
        this.def=def;
        this.key=key;
        this.rh=rh;
    }
    public String getWord() {
        return word;
    }
    public String getDef() {
        return def;
    }
    public String[] getKey() {
        return key;
    }
    public RatioHandler getRatioHandler() {
        return rh;
    }
}
