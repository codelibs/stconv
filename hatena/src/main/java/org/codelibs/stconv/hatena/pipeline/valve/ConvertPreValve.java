package org.codelibs.stconv.hatena.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConvertPreValve extends AbstractHatenaValve {

    private String pattern = "^\\>\\|";

    private boolean tagStartFlg;

    @Override
    protected String getPattern() {
        return pattern;
    }

    /**
     * 整形済みテキストブロックの開始行、終了行に対して<pre>タグを挿入します。
     * 
     */
    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        if (!tagStartFlg && !line.startsWith(">||")) {
            writer.write("<pre>");
            tagStartFlg = true;
        }
        writer.newLine();
    }

    @Override
    protected void writeUnmatchLine(final BufferedWriter writer,
            final String line) throws IOException {
        if (tagStartFlg && line.endsWith("|<") && !line.endsWith("||<")) {
            writer.write(line.substring(0, line.length() - 2) + "</pre>");
            writer.newLine();
            tagStartFlg = false;
        } else {
            writer.write(line);
            writer.newLine();

        }
    }

}
