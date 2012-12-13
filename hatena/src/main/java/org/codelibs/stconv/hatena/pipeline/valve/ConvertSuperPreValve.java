package org.codelibs.stconv.hatena.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;

import org.apache.commons.lang.StringEscapeUtils;

public class ConvertSuperPreValve extends AbstractHatenaValve {

    private String pattern = "^\\>\\|\\||\\|\\|\\<"; //start with >|| or ||<

    private boolean tagStartFlg;

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {

        if (tagStartFlg && line.startsWith("||<")) {
            writer.write("</pre>");
            tagStartFlg = false;
        } else if (!tagStartFlg && line.startsWith(">||")) {
            writer.write("<pre>");
            tagStartFlg = true;
        } else {
            writer.write(line);
        }
        writer.newLine();
    }

    /**
     * 不一致の行に対しては何も行いません。
     * TODO 内部テキストのタグはエスケープする必要があるかも。
     */
    @Override
    protected void writeUnmatchLine(final BufferedWriter writer,
            final String line) throws IOException {
        if (tagStartFlg) {
            writer.write(StringEscapeUtils.escapeHtml(line));
            writer.newLine();
        } else {
            super.writeUnmatchLine(writer, line);
        }
    }

    @Override
    protected void finish(final BufferedWriter writer) throws IOException {
        if (tagStartFlg) {
            writer.write("</pre>");
            tagStartFlg = false;
        } else {
            super.finish(writer);
        }
    }
}
