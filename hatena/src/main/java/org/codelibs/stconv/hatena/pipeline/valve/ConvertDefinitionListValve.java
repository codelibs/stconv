package org.codelibs.stconv.hatena.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Definition List
 * 定義リスト(DL)
 * @author takeharu
 *
 */
public class ConvertDefinitionListValve extends AbstractHatenaValve {

    private String pattern = "^[\\:].*?";

    private boolean tagStartFlg;

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        if (!tagStartFlg) {
            writer.write("<dl>");
            writer.newLine();
            tagStartFlg = true;
        }
        final String[] strArray = line.split(":");
        for (int i = 1; i < strArray.length; i++) {
            if (i == 1) {
                writer.write("<dt>" + strArray[i] + "</dt>");
            } else {
                writer.write("<dd>" + strArray[i] + "</dd>");
            }
        }
        writer.newLine();
    }

    @Override
    protected void writeUnmatchLine(final BufferedWriter writer,
            final String line) throws IOException {
        if (tagStartFlg) {
            writer.write("</dl>");
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
            writer.write("</dl>");
            writer.newLine();
            tagStartFlg = false;
        } else {
            super.finish(writer);
        }
    }
}
