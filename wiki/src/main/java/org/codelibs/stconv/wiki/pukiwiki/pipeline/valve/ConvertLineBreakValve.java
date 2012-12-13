package org.codelibs.stconv.wiki.pukiwiki.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * BR - Line Break
 * 
 * 
 * 
 * @author takeharu
 *
 */
public class ConvertLineBreakValve extends AbstractWikiValve {

    private String pattern = "&br;";

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        final String s = line.replaceAll(pattern, "<br />");
        writer.write(s);
        writer.newLine();
    }
}
