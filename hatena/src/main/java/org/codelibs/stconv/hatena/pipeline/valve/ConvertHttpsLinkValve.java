package org.codelibs.stconv.hatena.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * https://wwww.hatena.ne.jp/login is converted to
 * <p>
 * <a>https://www.haneta.ne.jp/login</a>https://www.haneta.ne.jp/login
 * </p>
 * 
 * @author takeharu
 */
public class ConvertHttpsLinkValve extends AbstractHatenaValve {
    private static final String KEYWORD_TITLE = ":title=";

    private static final String URI_CHARACTERS = "\\[?https://[a-zA-Z0-9\\;\\/\\?\\:\\@\\&\\=\\+\\$\\,\\-\\_\\.\\!\\~\\*\\'\\(\\)\\#]*";

    private static final String pattern = ".*https:.*";

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        String ln = line.trim();
        ln = ln.replaceAll("\\[|\\]", "");

        int sp = ln.indexOf("https:");
        if (sp < 0) {
            return;
        }
        final boolean addParagraph = ln.startsWith("https:");
        /*
         * Reserved Characters ";" | "/" | "?" | ":" | "@" | "&" | "=" | "+" |
         * "$" | "," Unreserved Characters "-" | "_" | "." | "!" | "~" | "*" |
         * "'" | "(" | ")" URI References "#"
         */
        final String[] strs = ln.split(URI_CHARACTERS);

        final StringBuffer sb = new StringBuffer();
        if (addParagraph) {
            sb.append("<p>");
        }

        if (strs.length == 0) {
            ln = "<a href=\"" + ln + "\">" + ln + "</a>";
            sb.append(ln);
        } else {
            String url = "";
            String value = "";
            for (int i = 0; i < strs.length; i++) {
                int ep = 0;// end index

                if (i + 1 < strs.length) {
                    ep = ln.indexOf(strs[i + 1], sp);
                } else {
                    ep = -1;
                }
                sb.append(strs[i]);
                if (ep > 0) {
                    sb.append("<a href=\"");
                    url = ln.substring(sp, ep);
                    value = url;
                    if (url.indexOf("https:") > 0) {
                        url = url.substring(url.indexOf("https:"));
                        value = url;
                    }
                    if (url.indexOf(KEYWORD_TITLE) != -1) {
                        url = url.substring(0, url.indexOf(KEYWORD_TITLE));
                        value = line.substring(
                                line.indexOf(KEYWORD_TITLE, sp)
                                        + KEYWORD_TITLE.length(),
                                line.indexOf("]",
                                        line.indexOf(KEYWORD_TITLE, sp)));
                        strs[i + 1] = strs[i + 1].replaceAll(value, "");
                        log.debug(url + " : " + value);
                    }
                    sb.append(url);
                    sb.append("\">");
                    sb.append(value);
                    sb.append("</a>");
                }
                sp = ep;
            }
        }
        if (addParagraph) {
            sb.append("</p>");
        }
        writer.write(sb.toString());
        writer.newLine();
    }
}