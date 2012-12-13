package org.codelibs.stconv.hatena.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConvertBlockQuotationValve extends AbstractHatenaValve {

    private String pattern = ">>|<<";

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        if (line.equals(">>")) {
            writer.write("<blockquote>");
        } else {
            writer.write("</blockquote>");
        }
        writer.newLine();
    }

    @Override
    protected void writeUnmatchLine(final BufferedWriter writer,
            final String line) throws IOException {
        writer.write(line);
        writer.newLine();
    }
}
