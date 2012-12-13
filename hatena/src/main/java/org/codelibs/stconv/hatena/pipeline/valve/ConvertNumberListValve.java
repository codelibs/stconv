package org.codelibs.stconv.hatena.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConvertNumberListValve extends AbstractHatenaValve {

    private String pattern = "^[\\+].*?";

    private boolean tagStartFlg;

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        if (!tagStartFlg) {
            writer.write("<ol>");
            writer.newLine();
            tagStartFlg = true;
        }
        writer.write("<li>" + line.substring(1) + "</li>");
        writer.newLine();
    }

    @Override
    protected void writeUnmatchLine(final BufferedWriter writer,
            final String line) throws IOException {
        if (tagStartFlg) {
            writer.write("</ol>");
            writer.newLine();
            tagStartFlg = false;
            super.writeUnmatchLine(writer, line);
        } else {
            super.writeUnmatchLine(writer, line);
        }
    }

    @Override
    protected void finish(final BufferedWriter writer) throws IOException {
        if (tagStartFlg) {
            writer.write("</ol>");
            writer.newLine();
            tagStartFlg = false;
        } else {
            super.finish(writer);
        }
    }
}
