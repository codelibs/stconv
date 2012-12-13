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
public class ConvertTableValve extends AbstractWikiValve {
    private String pattern = "\\|.*\\|$";

    private boolean isTagStarted;

    @Override
    protected String getPattern() {

        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        if (!isTagStarted) {
            writer.write("<table>");
            writer.newLine();
            isTagStarted = true;
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
        if (isTagStarted) {
            writer.write("</table>");
            writer.newLine();
            writer.write(line);
            writer.newLine();
            isTagStarted = false;
        } else {
            super.writeUnmatchLine(writer, line);
        }
    }

    @Override
    protected void finish(final BufferedWriter writer) throws IOException {
        if (isTagStarted) {
            writer.write("</table>");
            writer.newLine();
            isTagStarted = false;
        } else {
            super.finish(writer);
        }
    }
}
