package clipshit;

import java.awt.*;
import java.awt.datatransfer.*;
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
                    System.out.println((String) clipboard.getContents(this).getTransferData(DataFlavor.stringFlavor));
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
}