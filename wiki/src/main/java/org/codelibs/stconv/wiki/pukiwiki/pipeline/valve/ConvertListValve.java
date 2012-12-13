/*
 * Copyright 2003-2006 Stream Converter Project Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.codelibs.stconv.wiki.pukiwiki.pipeline.valve;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * @author takeharu
 *
 */
public class ConvertListValve extends AbstractWikiValve {
    /**
     * Logger for this class
     */
    //  private static final Log log = LogFactory.getLog(ConvertListValve.class);

    private String pattern = "^[\\-].*?";

    private boolean isTagStarted;

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        if (!isTagStarted) {
            writer.write("<ul>");
            writer.newLine();
            isTagStarted = true;
        }
        writer.write("<li>" + line.substring(1) + "</li>");
        writer.newLine();
    }

    @Override
    protected void writeUnmatchLine(final BufferedWriter writer,
            final String line) throws IOException {
        if (isTagStarted) {
            writer.write("</ul>");
            writer.newLine();
            isTagStarted = false;
            super.writeUnmatchLine(writer, line);
        } else {
            super.writeUnmatchLine(writer, line);
        }
    }

    @Override
    protected void finish(final BufferedWriter writer) throws IOException {
        if (isTagStarted) {
            writer.write("</ul>");
            isTagStarted = false;
        } else {
            super.finish(writer);
        }
    }
}
