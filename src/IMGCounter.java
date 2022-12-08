import org.xml.sax.helpers.DefaultHandler;
import javax.swing.text.html.HTMLEditorKit;
import javax.xml.parsers.SAXParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;


public class IMGCounter extends DefaultHandler {
    private static final String CLASS_NAME = IMGCounter.class.getName();
    private final static Logger LOG = Logger.getLogger(CLASS_NAME);
    private SAXParser parser = null;
    public static final String TAG = IMGCounter.class.getSimpleName();

    public static final String THE_URL = "https://people.sc.fsu.edu/~jburkardt/data/svg/";

    public static void main(String[] args) {

        URL webPage = null;
        try {
            webPage = new URL(THE_URL);
        } catch (MalformedURLException ex) {
            LOG.severe(ex.getMessage());
        }

        BufferedReader htmlReader = null;
        try {
            htmlReader = new BufferedReader(
                    new InputStreamReader( webPage.openStream() ));
        } catch (IOException ex) {
            LOG.severe(ex.getMessage());
        }

        HTMLParser p  = new HTMLParser();
        HTMLEditorKit.Parser procesador = p.getParser();
        ManejadorEtiquetas contador = new ManejadorEtiquetas();
        try {
            procesador.parse( htmlReader, contador, true);
        } catch (IOException e) {
            LOG.severe("No se puede leer documento HTML");
            System.exit(2);
        }
        System.out.println("Total de imagenes: " +  contador.getImgCounter());

        if (args.length == 0) {
            LOG.severe("No file to process. Usage is:" + "\njava DisplayXML <filename>");
            return;
        }
        File xmlFile = new File( args[0] );
        IMGCounter handler = new IMGCounter();
        handler.process(xmlFile);
    }
    private void process(File file) {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        spf.setValidating(true);

        System.out.println("Parser will " + (spf.isNamespaceAware() ? "" : "not ")
                + "be namespace aware");
        System.out.println("Parser will " + (spf.isValidating() ? "" : "not ") + "validate XML");
        try {
            parser = spf.newSAXParser();
            System.out.println("Parser object is: " + parser);
        } catch (SAXException | ParserConfigurationException e) {
            LOG.severe(e.getMessage());
            System.exit(1);
        }
        System.out.println("\nStarting parsing of " + file + "\n");
        try {
            parser.parse(file, this);
        } catch (IOException | SAXException e) {
            LOG.severe(e.getMessage());
        }
    }
    public void startDocument() {
        System.out.println("START-DOCUMENT");
    }
    public void endDocument() {
        System.out.println("END-DOCUMENT");
    }
    public void startElement(String uri, String localName, String qname, Attributes attr) {
        System.out.printf("START-ELEMENT: local name: '%s' qname: '%s' uri: '%s'\n",localName, qname, uri);
        int n = attr.getLength();
        for (int i=0;i<n; i++) {
            String attName = attr.getLocalName(i);
            String attrType = attr.getType(i);
            String attrValue = attr.getValue(i);
            System.out.printf("\t%s = %s (%s)\n",attName,attrValue,attrType);
        }
    }
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length);
        System.out.printf("CHARACTERS (%d): \"%s\"\n", length, data );
    }
}

