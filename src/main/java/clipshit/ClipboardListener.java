package clipshit;

import java.awt.*;
import java.awt.datatransfer.*;

public class ClipboardListener {
    private static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private FlavorListener listener;

    public ClipboardListener() {
        listener = new FlavorListener() {
            @Override
            public void flavorsChanged(FlavorEvent e) {
                System.out.println("changed!!! " + e.getSource() + " " + e.toString());
                processClipboard();
            }
        };

        enable();
    }

    public void enable() {
        System.out.println("ENABLED.  Listening...");
        clipboard.addFlavorListener(listener);
    }

    private void processClipboard() {
        String tempText;
        Transferable contents = clipboard.getContents(this);

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
        System.out.println("DISABLED.");
        clipboard.removeFlavorListener(listener);
    }
}