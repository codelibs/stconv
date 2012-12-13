package org.codelibs.stconv.hatena.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConvertTableValve extends AbstractHatenaValve {
    private String pattern = "\\|.*\\|$";

    private boolean tagStartFlg;

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        if (!tagStartFlg) {
            writer.write("<table>");
            writer.newLine();
            tagStartFlg = true;
        }
        final String[] values = line.split("\\|");
        writer.write("<tr>");
        for (int i = 1; i < values.length; i++) {
            String s = values[i];
            if (s.startsWith("*")) {
                s = s.substring(1);
                writer.write("<th>" + s + "</th>");
            } else {
                writer.write("<td>" + s + "</td>");
            }
        }
        writer.write("</tr>");
        writer.newLine();
    }

    @Override
    protected void writeUnmatchLine(final BufferedWriter writer,
            final String line) throws IOException {
        if (tagStartFlg) {
            writer.write("</table>");
            writer.newLine();
            writer.write(line);
            writer.newLine();
            tagStartFlg = false;
        } else {
            super.writeUnmatchLine(writer, line);
        }
    }

    @Override
    protected void finish(final BufferedWriter writer) throws IOException {
        if (tagStartFlg) {
            writer.write("</table>");
            writer.newLine();
            tagStartFlg = false;
        } else {
            super.finish(writer);
        }
    }
}
