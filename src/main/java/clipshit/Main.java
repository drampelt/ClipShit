package clipshit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * Created by daniel on 2015-03-21.
 */
public class Main {

    public static void main2(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void main(String[] args) {
        ClipboardListener listener = new ClipboardListener();
    }

    private static boolean enabled = false;

    private static void createAndShowGUI() {
        if(!SystemTray.isSupported()) {
            System.out.println("Sorry we need your system tray bro");
            System.exit(1);
        }

        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(createImage("clipshit.png", "Tray Icon"));

        final SystemTray tray = SystemTray.getSystemTray();

        MenuItem exit = new MenuItem("Exit");
        MenuItem status = new MenuItem("Status: Disabled");
        MenuItem toggle = new MenuItem("Enable");
        status.setEnabled(false);

        popup.add(status);
        popup.add(toggle);
        popup.addSeparator();
        popup.add(exit);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
            System.exit(1);
        }

        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "ayy lmao");
            }
        });

        toggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(enabled) {
                    toggle.setLabel("Enable");
                    status.setLabel("Status: Disabled");
                } else {
                    toggle.setLabel("Disable");
                    status.setLabel("Status: Enabled");
                }
                enabled = !enabled;
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }

    protected static Image createImage(String path, String description) {
        System.out.println( Main.class.getResource(Main.class.getSimpleName() + ".class") );
        URL imageURL = Main.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return new ImageIcon(path, description).getImage();
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

}

