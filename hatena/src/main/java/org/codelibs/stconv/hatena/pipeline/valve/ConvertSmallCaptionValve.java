package org.codelibs.stconv.hatena.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConvertSmallCaptionValve extends AbstractHatenaValve {

    String pattern = "^\\*\\*[^\\*].*?";

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        writer.write("<p><span class=\"h4\">" + line.substring(2)
                + "</span></p>");
        writer.newLine();
    }

}
