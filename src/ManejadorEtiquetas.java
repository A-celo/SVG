import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import java.util.Enumeration;

public class ManejadorEtiquetas extends HTMLEditorKit.ParserCallback  {
    private int imgCounter ;

    public ManejadorEtiquetas() {
        super();
        imgCounter = 0;
    }
    @Override
    public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attributes, int pos) {

        if( tag != HTML.Tag.IMG) {
            return;
        }

        imgCounter++ ;

        String tagName = tag.toString().toUpperCase();
        int n = attributes.getAttributeCount();
        String src = (String) attributes.getAttribute(HTML.Attribute.SRC);
        System.out.printf("%d = %s: %s%n",imgCounter, tagName, src );
        DownloaderThread downloaderThread = new DownloaderThread(src);
        downloaderThread.start();
    }
    public int getImgCounter() {
        return imgCounter;
    }
}
