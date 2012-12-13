package org.codelibs.stconv.hatena.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * BR - Line Break
 * 
 * 改行を2回続けて入れると<br>タグを挿入します。
 * 
 * @author takeharu
 *
 */
public class ConvertLineBreakValve extends AbstractHatenaValve {

    private String pattern = "^$";

    private boolean breakFlg;

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        if (breakFlg) {
            writer.write("<br />");
            writer.newLine();
            breakFlg = false;
        } else {
            breakFlg = true;
        }
    }

    @Override
    protected void writeUnmatchLine(final BufferedWriter writer,
            final String line) throws IOException {
        if (breakFlg) {
            breakFlg = false;
        }
        writer.write(line);
        writer.newLine();
    }
}
