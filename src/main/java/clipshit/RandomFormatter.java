package clipshit;

import java.util.Random;

/**
 * Created by daniel on 2015-03-21.
 */
public class RandomFormatter {

    private static Random random = new Random();
    private static String[] formats = {
            "font-weight: bold;",
            "font-weight: bold;",
            "text-decoration: underline;",
            "text-decoration: underline;",
            "font-style: italic;",
            "font-style: italic;",
            "font-style: italic;",
            "text-transform: uppercase",
            "color: #ff0000;",
            "color: #00ff00;",
            "color: #FF40FF;",
            "color: #4E8F00;",
            "color: #0096FF;",
            "color: #941651;",
            "color: #009192;",
            "color: #FEFC78;",
            "line-height: 20px;",
            "text-indent: 20px;",
            "font-size: 32px;",
            "font-size: 64px;",
            "font-size: 80px;",
            "font-size: 5px;",
            "font-size: 10px;",
            "font-size: 16px;",
            "font-size: 20px;",
            "font-weight: 100;",
            "letter-spacing: 10px;",
            "letter-spacing: 5px;",
            "letter-spacing: -3px;",
            "letter-spacing: -5px;",
            "letter-spacing: -1px;",
            "font-family: monospace;",
            "font-family: 'Papyrus';",
            "font-family: 'Comic Sans MS';",
            "opacity: 0.5;",
            "opacity: 0.7",
    };

    public static String format(String in) {
        String html = "<meta charset=\"utf-8\">";
        int numspans = 0;
        String[] lines = in.split("\n");
        for(String line : lines) {
            float rand = random.nextFloat();
            if(rand > 0.66) {
                html += "<p style=\"text-align: right;\">";
            } else if(rand > 0.33) {
                html += "<p style=\"text-align: center;\">";
            } else {
                html += "<p>";
            }
            String[] words = line.split(" ");
            for(String word : words) {
                rand = random.nextFloat();
                if(numspans > 0 && rand > 0.6) {
                    html += "</span>";
                    numspans--;
                } else if(rand < 0.6) {
                    html += "<span style=\"" + getStyle() + "\">";
                    numspans++;
                }
                html += word + " ";
            }
            while(numspans > 0) {
                html += "</span>";
                numspans--;
            }
            html += "</p>";
        }
        return html;
    }

    private static String getStyle() {
        String ret = formats[random.nextInt(formats.length)];
        if(random.nextFloat() > 0.6) ret += getStyle();
        return ret;
    }

}
