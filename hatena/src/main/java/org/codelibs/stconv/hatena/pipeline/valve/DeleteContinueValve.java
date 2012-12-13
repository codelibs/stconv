package org.codelibs.stconv.hatena.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * This Valve deletes continue notation ==== and =====
 * when vallve finds the continue notation valve delets
 * the line and not write new line
 * 
 * @author takeharu
 *
 */
public class DeleteContinueValve extends AbstractHatenaValve {
    String pattern = "^={4,5}";

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {

    }
}
