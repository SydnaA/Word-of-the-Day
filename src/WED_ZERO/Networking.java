package WED_ZERO;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author SydnaAgnehs
 */
public class Networking implements ActionListener {
    private ServerSocket srvr=null;
    private final int PORT= 23432;
    private Socket soc=null;
    PrintWriter out= null;
    BufferedReader in=null;
    String input=null, output=null;
    private String IPaddress=null;
    private JDialog d;
    private JProgressBar bar;
    private JButton c;
    public Networking(boolean isClient) {
        this.progress(isClient);
    }
    //sets up the user as the server
    public String initServer(String name) throws IOException {
        try {
            srvr= new ServerSocket(PORT);
        } catch (IOException ex) {
            ERROR.ERROR_364(Launch.frame);
            return "";
        }
        try {
            soc=srvr.accept();
        } catch (IOException ex) {
            ERROR.ERROR_365(Launch.frame);
                return "";
        }
        this.initWriterReader();
        
        out.println(name);
        return in.readLine();
    }
    private void initWriterReader() {
        try {
            out =new PrintWriter(soc.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //sets up the user as the client
    public String initClient() throws IOException, InterruptedException {
        if(IPaddress==null) {
            return "";
        }
        try {
            soc=new Socket(IPaddress, PORT);
        } catch (UnknownHostException ex) {
            ERROR.ERROR_366(Launch.frame);
            this.initClient();
        }
        this.initWriterReader();
        String temp=in.readLine();
        out.println(System.getProperty("user.name")); 
        return temp;
    }
    //sends the selected data to the client
    public boolean transferData_SEND(ArrayList<ArrayList<String>> packet, ArrayList<String> folderName) {
        try {
            out.println(packet.size());
            bar.setMaximum(this.getLength(packet, folderName)+1);
            bar.setValue(1);
            bar.setString("Sending");
            bar.setIndeterminate(false);
            out.println(this.getLength(packet, folderName));
            for(int x=0;x<folderName.size();x++) {
                out.println(folderName.get(x));
                bar.setValue(bar.getValue()+1);
            }
            for(int x=0;x<packet.size();x++) {
                out.println(packet.get(x).size());
                for(int y=0;y<packet.get(x).size();y++) {
                    out.println(packet.get(x).get(y));
                    bar.setValue(bar.getValue()+1);
                }
            }
            out.println("Done");
            bar.setValue(bar.getMaximum());
            if(in.readLine().equals("Done")) {
                this.done(true);
                return true;
            }
            this.done(true);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    //recieves data from the server
    public boolean transferData_RECIEVE(ArrayList<String> folderName, Map<Integer, ArrayList<String>> packet) throws IOException {
        int max=Integer.parseInt(in.readLine());
        bar.setMaximum(Integer.parseInt(in.readLine())+1);
        bar.setValue(1);
        bar.setIndeterminate(false);
        bar.setString("Recieving");
        int length=(max/2);
        for(int y=0;y<length;y++) {
            folderName.add(in.readLine());
            bar.setValue(bar.getValue()+1);
        }
        for(int x=0;x<max;x++) {
            packet.put(x, new ArrayList());
            length=Integer.parseInt(in.readLine());
            for(int y=0;y<length;y++) {
                packet.get(x).add(in.readLine());
                bar.setValue(bar.getValue()+1);
            }
        }
       
        bar.setValue(bar.getMaximum());
        if(in.readLine().equals("Done")) {
            out.println("Done");
            this.done(false);
            return true;
        }
        out.println("Error");
        this.done(false);    
        return false;
    }
    //displays a little loading bar when the data is transfering to show progress
    private JProgressBar progress(boolean isClient) {
        d=new JDialog((JFrame)null, "name");
        d.setPreferredSize(new Dimension(200,150));
        d.setLocationRelativeTo(null);
        d.pack();
        bar=new JProgressBar();
        bar.setString("Waiting for Response");
        bar.setStringPainted(true);
        bar.setIndeterminate(true);
        bar.setVisible(true);
        JPanel p=new JPanel();
        p.setVisible(true);
         p.add(bar);
        if(isClient) {
            c=new JButton("Cancel");
            c.setActionCommand("cancel");
            c.addActionListener(this);
            p.add(c);
        }
        d.add(p);
        d.setVisible(true);
        return bar;
    }
    //closes al lthe server and sockets
    private void done(boolean server) throws IOException {
        d.dispose();
        if(server)
        srvr.close();
        soc.close();
        out.close();
        in.close();
    }
    private int getLength(ArrayList<ArrayList<String>> packet, ArrayList<String> folderName) {
        int total=0;
        for(int x=0;x<packet.size();x++) {
            total+=packet.get(x).size();
        }
        return total;
    }
    public void setIPAddress(String ip) {
        IPaddress=ip;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("cancel")) {
            try {
                this.done(false);
            } catch (IOException ex) {
                ERROR.ERROR_365(Launch.frame);
                return;
            }
       }
        
    }
}
