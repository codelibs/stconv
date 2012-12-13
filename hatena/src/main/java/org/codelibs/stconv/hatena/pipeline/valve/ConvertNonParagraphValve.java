package org.codelibs.stconv.hatena.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConvertNonParagraphValve extends AbstractHatenaValve {
    private String pattern = "^\\>\\<.*?|.*?\\>\\<$";

    private boolean tagStartFlg;

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        String s = line;
        if (s.startsWith("><")) {
            s = s.substring(1);
            tagStartFlg = true;
        }
        if (tagStartFlg && s.endsWith("><")) {
            s = s.substring(0, s.length() - 1);
            tagStartFlg = false;
        }
        writer.write(s);
        writer.newLine();
    }

    @Override
    protected void writeUnmatchLine(final BufferedWriter writer,
            final String line) throws IOException {
        if (tagStartFlg) {
            writer.write(line);
            writer.newLine();
        } else {
            super.writeUnmatchLine(writer, line);
        }
    }
}
