package WED_ZERO;

/**
 *
 * @author SydnaAgnehs
 */
public class RatioHandler {
    private int right;
    private int wrong;
    public RatioHandler(int r, int w) {
        right=r;
        wrong=w;
    }
    public static RatioHandler toRatio(String log) {
        int a=0;
        int b=0;
        for(int x=0;x<log.length();x++) {
            if(log.substring(x, x+1).equals("/")) {
               a=Integer.parseInt(log.substring(0, x));
               b=Integer.parseInt(log.substring(x+1));
            }
        }
        return new RatioHandler(a, b);
    }
    public int getR() {
        return right;
    }
    public int getW() {
        return wrong;
    }
    public static RatioHandler addRatio(RatioHandler oldRatio, RatioHandler newRatio) {
        return new RatioHandler(oldRatio.getR()+newRatio.getR(), oldRatio.getW()+newRatio.getW());
    }
    @Override
    public String toString() {
        return right+"/"+wrong;
    }
    
}
