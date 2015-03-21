package clipshit;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by daniel on 2015-03-21.
 */
public class ImageWatermarker {

    private static Image watermark = null;
    static {
        URL imageURL = Main.class.getResource("/clipshit.png");
        watermark = new ImageIcon(imageURL).getImage();
    }

    public static Image watermark(Image in) {
        Graphics2D g2d = (Graphics2D) in.getGraphics();
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        g2d.setComposite(alphaChannel);

        int topLeftX = (in.getWidth(null) - watermark.getWidth(null)) / 2;
        int topLeftY = (in.getHeight(null) - watermark.getHeight(null)) / 2;

        // paints the image watermark
        g2d.drawImage(watermark, topLeftX, topLeftY, null);
        g2d.dispose();
        return in;
    }

}
