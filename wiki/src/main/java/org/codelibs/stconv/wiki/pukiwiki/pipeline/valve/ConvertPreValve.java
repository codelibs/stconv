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

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author takeharu
 *
 */
public class ConvertPreValve extends AbstractWikiValve {
    private String pattern = "^\\s.*";

    private boolean isTagStarted;

    @Override
    protected String getPattern() {
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        if (!isTagStarted) {
            writer.write("<pre>");
            isTagStarted = true;
        }
        writer.write(StringEscapeUtils.escapeHtml(line.substring(1)));
        writer.newLine();
    }

    /**
     * 不一致の行に対しては何も行いません。
     * TODO 内部テキストのタグはエスケープする必要があるかも。
     */
    @Override
    protected void writeUnmatchLine(final BufferedWriter writer,
            final String line) throws IOException {
        if (isTagStarted) {
            writer.write("</pre>");
            isTagStarted = false;
        } else {
            super.writeUnmatchLine(writer, line);
        }
    }

    @Override
    protected void finish(final BufferedWriter writer) throws IOException {
        if (isTagStarted) {
            writer.write("</pre>");
            isTagStarted = false;
        } else {
            super.finish(writer);
        }
    }
}
