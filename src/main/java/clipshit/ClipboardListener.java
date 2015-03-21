package clipshit;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ClipboardListener extends Thread {
    private Clipboard clipboard;
    private ScheduledFuture<?> cancelObject;
    private Runnable process;
    private ScheduledExecutorService exec;

    @Override
    public void run() {
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        process = new Runnable() {
            @Override
            public void run() {
                try {
                    String contents = (String) clipboard.getContents(this).getTransferData(DataFlavor.stringFlavor);
                    System.out.println("Original:  " + contents);
//                    contents = RandomFormatter.format(contents);

                    // set clipboard's contents with new formatted stuff
                    Transferable t = new HtmlSelection(contents);
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(t, null);

                    System.out.println("Formatted:  " + contents);
                    System.out.println();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        exec = Executors.newSingleThreadScheduledExecutor();
        enable();
    }

    public void enable() {
        System.out.println("ENABLED. Listening...");
        cancelObject = exec.scheduleAtFixedRate(process, 0, 1, TimeUnit.SECONDS);
    }

    public void disable() {
        System.out.println("DISABLED.");
        cancelObject.cancel(false);
    }

    private static class HtmlSelection implements Transferable {
        private static ArrayList htmlFlavors = new ArrayList();
        private String html;

        static {
            try {
                htmlFlavors.add(new DataFlavor("text/html;class=java.lang.String"));
                htmlFlavors.add(new DataFlavor("text/html;class=java.io.Reader"));
                htmlFlavors.add(new DataFlavor("text/html;charset=unicode;class=java.io.InputStream"));
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }

        public HtmlSelection(String html) {
            this.html = html;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return (DataFlavor[]) htmlFlavors.toArray(new DataFlavor[htmlFlavors.size()]);
        }


        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return htmlFlavors.contains(flavor);
        }


        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (String.class.equals(flavor.getRepresentationClass())) {
                return html;
            } else if (Reader.class.equals(flavor.getRepresentationClass())) {
                return new StringReader(html);
            } else if (InputStream.class.equals(flavor.getRepresentationClass())) {
                return new StringBufferInputStream(html);
            }

            throw new UnsupportedFlavorException(flavor);
        }
    }
}