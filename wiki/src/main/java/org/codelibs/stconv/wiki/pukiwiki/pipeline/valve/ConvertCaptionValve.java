package org.codelibs.stconv.wiki.pukiwiki.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConvertCaptionValve extends AbstractWikiValve {

    private String pattern = "^\\*";

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        //count '=' charactor
        super.log.debug("HIT");
        int c = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.startsWith("*", i)) {
                c++;
            } else {
                break;
            }
        }

        writer.write("<h" + c + ">" + line.replaceAll("^\\*{" + c + "}", "")
                + "</h" + c + ">");
        writer.newLine();
    }
}
