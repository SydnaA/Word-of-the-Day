/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WED_ZERO;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author SydnaAgnehs
 */
public class Tutorial {
    private int stage;
    private JFrame frame;
    private Log log;
    public Tutorial(Log log) {
        stage=0;
        frame=Launch.frame;
        this.log=log;
        stage=log.getTutStage();
    }
    public void getTutMessage(int number) {
        if(stage>5||number!=stage)
            return;
        String msg=null;
        switch(stage) {
            case 0:
                msg=WED_ZERO.lang.getInstruct().get(6)[0];
                this.increTut();
                break;
            case 1:
                msg=WED_ZERO.lang.getInstruct().get(6)[1];
                this.increTut();
                break;
            case 2:
                msg=WED_ZERO.lang.getInstruct().get(6)[2];
                this.increTut();
                break;
            case 3:
                msg=WED_ZERO.lang.getInstruct().get(6)[3];
                this.increTut();
                break;
            case 4:
                msg=WED_ZERO.lang.getInstruct().get(6)[4];
                this.increTut();
                break;
        }
        JOptionPane.showMessageDialog(frame, msg);
        return;
    }
    public void increTut() {
        stage++;
        log.updateLog(stage+"", null);
    }
}
