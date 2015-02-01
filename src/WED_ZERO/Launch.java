package WED_ZERO;

import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author SydnaAgnehs
 */
public class Launch {
    public static JFrame frame;
    //JPanel panel;
    public Launch(String name, int which, String packet) throws IOException {
        frame=new JFrame(name);
       // panel=new JPanel();
       // panel.setLayout(null);
        switch(which) {
            case 1:
                this.startToday();
                break;
            case 3:
                break;
            case 4:
                //this.viewWords();
                this.view(packet);
                break;
            case 5:
                this.option();
                break;
        }
        
        //frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(540, 380);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        //panel.setVisible(true);
    }
    public JFrame getFrame() {
        return frame;
    }
    
    public void view(String packet) throws IOException {
        if(packet==null)
        new View(null);
        else {
            new View(packet);
        }
    }
    
    public void option() {
        new Option();
    }
    public void startToday() {
        //new today();
        new FileManaging().initView();
        
    }
    
    public void invis() {
        frame.setVisible(false);
        //panel.setVisible(false);
    }
    
}
