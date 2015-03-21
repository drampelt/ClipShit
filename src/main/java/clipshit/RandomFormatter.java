package clipshit;

import java.util.Random;

/**
 * Created by daniel on 2015-03-21.
 */
public class RandomFormatter {

    private static Random random = new Random();
    private static String[] formats = {
            "font-weight: bold;",
            "text-decoration: underline;",
            "font-style: italic;",
            "font-size: 64px;",
            "letter-spacing: 10px;",
            "text-transform: uppercase",
            "color: #ff0000;",
            "color: #00ff00;",
            "color: #FF40FF;",
            "line-height: 30px;",
            "text-indent: 30px;",
            "color: #4E8F00;",
            "font-size: 128px;",
            "font-size: 32px;",
            "font-size: 8px;",
            "font-weight: 100;",
            "letter-spacing: -3px;",
            "font-family: monospace;",
            "font-family: \"Comic Sans MS\";"
    };

    public static String format(String in) {
        String html = "<meta charset=\"utf-8\">";
        String lastOpened = "";
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
                if(lastOpened != "" && rand > 0.6) {
                    html += "</" + lastOpened + ">";
                    lastOpened = "";
                } else if(rand < 0.6) {
                    html += "<span style=\"" + getStyle() + "\">";
                    lastOpened = "span";
                }
                html += word + " ";
            }
            if(lastOpened != "") html += "</" + lastOpened + ">";
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
