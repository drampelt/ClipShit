package clipshit;

import java.awt.*;
import java.awt.datatransfer.*;

public class ClipboardListener extends Thread implements ClipboardOwner {
    private static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private boolean isRunning = false;

    @Override
    public void run() {
        Transferable contents = clipboard.getContents(this);
        takeOwnership(contents);
        System.out.println("ENABLED.  Listening...");
        isRunning = true;
        while(isRunning) {
            try {
                ClipboardListener.sleep(1000);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("DISABLED");
        this.interrupt();
    }

    @Override
    public void lostOwnership(Clipboard c, Transferable t) {
        try {
            ClipboardListener.sleep(250);
        } catch(Exception e) {
            e.printStackTrace();
        }

        Transferable contents = clipboard.getContents(this);

        try {
            processClipboard(contents, c);
        } catch (Exception e) {
            e.printStackTrace();
        }

        takeOwnership(contents);
    }

    private void takeOwnership(Transferable t) {
        clipboard.setContents(t, this);
    }

    private void processClipboard(Transferable t, Clipboard c) {
        String tempText;
        Transferable contents = t;

        try {
            if(contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                tempText = (String) contents.getTransferData(DataFlavor.stringFlavor);
                System.out.println("TEXT:  " + tempText);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void disable() {
        isRunning = false;
    }
}