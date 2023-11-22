package Connect;

import java.util.HashMap;
import java.util.Map;

public class HTML {
    public static String convertToHtml(String word, String pronounce, String description) {
        StringBuilder html = new StringBuilder();
        html.append("<h1>").append(word).append("</h1>");
        html.append("<h3><i>/").append(pronounce).append("/</i></h3>");

        //Map<String, StringBuilder> map = new HashMap<>();
        description = description.trim().replaceAll("\\s+", " ");
        String[] parts = description.split("\\s*;\\s*");
        for (String string : parts) {
            String[] typeAndMeaning = string.split("\\s*:\\s*");
            if (typeAndMeaning.length >= 1) {
                String type = typeAndMeaning[0];
                //String[] meanings = typeAndMeaning[1].split("\\s*\\.\\s*");
                String[] meanings;

                // Check if type exists or use a default type
                if (typeAndMeaning.length == 1) {
                    meanings = type.split("\\s*\\.\\s*");
                    type = ""; // Set a default type
                } else {
                    meanings = typeAndMeaning[1].split("\\s*\\.\\s*");
                }
                html.append("<h2>").append(type).append("</h2>");
                html.append("<ul>");

                for (String s : meanings) {
                    html.append("<li>").append(s).append("</li>");
                }
                html.append("</ul>");
            }
        }
        return html.toString();
    }
}
