package WED_ZERO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author SydnaAgnehs
 */
public class AlternativeSav {
    private ArrayList<String> packetNames=new ArrayList();
    private LoadAndSave las;
    private FileManaging fm=new FileManaging();
    private File loc;
    public AlternativeSav(File l, ArrayList<String> packetN) throws IOException {
        loc=new File(l.toString()+"/WED_DATA.xml");
        las=new LoadAndSave();
        packetNames=packetN;
            try {
                this.createFile();
                loc.renameTo(new File(l+"/"+System.getProperty("user.name")+"_DATA.WED"));
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(AlternativeSav.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerConfigurationException ex) {
                Logger.getLogger(AlternativeSav.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(AlternativeSav.class.getName()).log(Level.SEVERE, null, ex);
            }
     }
    //creates a XML file
    private void createFile() throws ParserConfigurationException, TransformerConfigurationException, TransformerException, IOException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc=docBuilder.newDocument();
        this.initContent(doc);
        TransformerFactory tf=TransformerFactory.newInstance();
        Transformer tran=tf.newTransformer();
        DOMSource source=new DOMSource(doc);
        StreamResult re=new StreamResult(loc);
        tran.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        tran.setOutputProperty(OutputKeys.INDENT, "yes");
        tran.transform(source, re);
    }
    
    //creates the content of the file
    private void initContent(Document doc) throws FileNotFoundException {
        Element rootElement=doc.createElement("Local");
        doc.appendChild(rootElement);
        
        for(int x=0;x<packetNames.size();x++) {
            las.load(packetNames.get(x));
            Element packet= doc.createElement("Packets");
            this.processPackets(doc, packet, x);
            rootElement.appendChild(packet);
        } 
    }
    
    //writes out the selected folder and terms
    private void processPackets(Document doc, Element e, int x) throws FileNotFoundException {
        Element packetN=doc.createElement("packetName");
        packetN.appendChild(doc.createTextNode(packetNames.get(x)));
        e.appendChild(packetN);
        for(int y=0;y<las.loader1.size();y++) {
            Element vcb=doc.createElement("term");
            Element word=doc.createElement("word");
            word.appendChild(doc.createTextNode(las.loader1.get(y)));
            vcb.appendChild(word);
            Element def=doc.createElement("def");
            def.appendChild(doc.createTextNode(las.loader2.get(y*3)));
            vcb.appendChild(def);
            Element Key=doc.createElement("key");
            Key.appendChild(doc.createTextNode(las.loader2.get(y*3+1)));
            vcb.appendChild(Key);
            Element rate=doc.createElement("rate");
            rate.appendChild(doc.createTextNode(las.loader2.get(y*3+2)));
            vcb.appendChild(rate);
            e.appendChild(vcb);
        }
        
    }
}
