package org.codelibs.stconv.hatena.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConvertCaptionValve extends AbstractHatenaValve {
    //	String pattern = "^\\*{1}.\\*?[^\\*].\\*?";
    String pattern = "^\\*[^\\*]*";

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        writer.write("<p><span class=\"sanchor\">■</span> <span class=\"h3\">"
                + line.substring(1) + "</span></p>");
        writer.newLine();
    }
}
