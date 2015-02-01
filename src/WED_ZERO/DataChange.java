package WED_ZERO;

/**
 *
 * @author SydnaAgnehs
 */
public class DataChange {
    private int row;
    private int col;
    private Object oldValue;
    private Object newValue;
    //hold the value of the changes
    //just in case the user wants to revert their actions
    public DataChange(Object newV, Object oldV, int r, int c) {
        row=r;
        col=c;
        oldValue=oldV;
        newValue=newV;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public Object getOldValue() {
        return oldValue;
    }
    public Object getNewValue() {
        return newValue;
    }
}
