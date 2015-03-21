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

        int width = (int) (in.getWidth(null) * 0.75);
        int height = (int) (in.getHeight(null) * 0.75);
        int topLeftX = (in.getWidth(null) - width) / 2;
        int topLeftY = (in.getHeight(null) - height) / 2;

        // paints the image watermark
        g2d.drawImage(watermark, topLeftX, topLeftY, width, height, null);
        g2d.dispose();
        return in;
    }

}
