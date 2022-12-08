import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloaderThread extends Thread {

    private URL url;

    public DownloaderThread(String urlString) {
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        InputStream input = null;
        try {
            input = url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String file = url.getFile();
        int pos = file.lastIndexOf("/");
        String fileName = file.substring(pos+ 1);

        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            int data;
            while ( (data = input.read()) != -1 ) {
                outputStream.write(data);
            }
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": " +fileName );
    }
}



