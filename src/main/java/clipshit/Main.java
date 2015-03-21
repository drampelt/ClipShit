package clipshit;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

/**
 * Created by daniel on 2015-03-21.
 */
public class Main extends Thread implements ClipboardOwner {
    private static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    @Override
    public void lostOwnership(Clipboard c, Transferable c) {
        System.out.println("Lost ownership of clipboard.");
    }

    @Override
    public void run() {
        Transferable contents = clipboard.getContents(this);
        regainOwnership(contents);
        System.out.println("Listening to clipboard");
        while(true) {}
    }

    public static void sivasMain(String[] args) {
        Main listener = new Main();
        listener.start();
    }

    public static void main(String[] args) {
        String result = null;

        try {
            result = (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            result = (String) clipboard.getData(DataFlavor.allHtmlFlavor);
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}

