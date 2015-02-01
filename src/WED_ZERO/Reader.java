package WED_ZERO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author SydnaAgnehs
 */
public class Reader {
    private File file;
    private Map<Integer, ArrayList<String>> data=new HashMap<Integer, ArrayList<String>>();
    private ArrayList<String> folderName=new ArrayList();
    
    //reads the XML file
    public Reader(File f) {
        try {
            file=f;
            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            DocumentBuilder db=dbf.newDocumentBuilder();
            Document doc=db.parse(file);
            NodeList list=doc.getElementsByTagName("Packets");
            for(int x=0;x<list.getLength();x++) {
                Node n=list.item(x);
                data.put(x*2, new ArrayList());
                data.put(x*2+1, new ArrayList());
                if(n.getNodeType()==Node.ELEMENT_NODE) {
                    Element e=(Element)n;
                    folderName.add(this.getString("packetName", e));
                    //NodeList list2=doc.getElementsByTagName("term");
                    NodeList list2=e.getElementsByTagName("term");
                    for(int y=0;y<list2.getLength();y++) {
                        Node n2=list2.item(y);
                        if(n2.getNodeType()==Node.ELEMENT_NODE) {
                            Element e2=(Element)n2;
                            data.get(x*2).add(this.getString("word", e2));
                            data.get(x*2+1).add(this.getString("def", e2));
                            data.get(x*2+1).add(this.getString("key", e2));
                            data.get(x*2+1).add("0/0");
                        }
                    }
                }
            }
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private String getString(String t, Element e){
	return ((Node) e.getElementsByTagName(t).item(0).getChildNodes().item(0)).getNodeValue();
    }
    public Map<Integer, ArrayList<String>> getMap() {
        return data;
    }
    public ArrayList<String> getPacketName() {
        return folderName;
    }
}
