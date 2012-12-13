package org.codelibs.stconv.wiki.pukiwiki.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConvertBlockQuotationValve extends AbstractWikiValve {

    private String pattern = "^>{1,3}?.*";

    private boolean tagStarted;

    private int blockCount;

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        log.debug("HIT");
        final String newLine = line.replaceFirst(">{1,3}\\s*", "");
        if (tagStarted) {
            writer.write("</p><blockquote><p class=\"quotation\">" + newLine);
        } else {
            writer.write("<blockquote><p class=\"quotation\">" + newLine);
        }
        tagStarted = true;
        blockCount++;
        writer.newLine();
    }

    @Override
    protected void writeUnmatchLine(final BufferedWriter writer,
            final String line) throws IOException {
        log.debug("NOT HIT");
        if (tagStarted) {
            if (line.length() == 0) {
                writer.write("</p>");
                for (int i = blockCount; i > 0; i--) {
                    writer.write("</blockquote>");
                }
                blockCount = 0;
                tagStarted = false;
            }
        }
        writer.write(line);
        writer.newLine();
    }

    @Override
    protected void finish(final BufferedWriter writer) throws IOException {
        if (tagStarted) {
            writer.write("</p>");
            for (int i = blockCount; i > 0; i--) {
                writer.write("</blockquote>");
            }
            blockCount = 0;
            writer.newLine();
            tagStarted = false;
        } else {
            super.finish(writer);
        }
    }
}
