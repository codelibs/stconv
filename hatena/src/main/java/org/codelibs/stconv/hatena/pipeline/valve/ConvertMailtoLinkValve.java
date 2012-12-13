package org.codelibs.stconv.hatena.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConvertMailtoLinkValve extends AbstractHatenaValve {
    String pattern = "^.*?\\[?mailto:.*?";

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {

        final String[] strs = line.split("\\[?mailto:[a-z|A-Z|0-9|\\.|@]*");
        final int i = line.indexOf("mailto:");
        final String url = line.substring(i, line.indexOf(strs[1], i));
        if (strs.length == 2 && strs[1].startsWith("]")) {
            strs[1] = strs[1].substring(1);
        }
        writer.write("<p>" + strs[0] + "<a href=\"" + url + "\">"
                + url.substring("mailto:".length()) + "</a>" + strs[1] + "</p>");
        writer.newLine();
    }
}
