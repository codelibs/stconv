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
 * Enclosing Inline Element with '' puts emphasis on the element (Bold font).
 * @author takeharu
 *
 */
public class ConvertBoldFontValve extends AbstractWikiValve {
    private String pattern = "'{2}";

    boolean isTagStarted;

    @Override
    protected String getPattern() {
        log.debug("called");
        return pattern;
    }

    @Override
    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        String s = line;
        while (s.indexOf("''") != -1) {
            if (isTagStarted) {
                s = s.replaceFirst("'{2}", "</strong>");
                isTagStarted = false;
            } else {
                s = s.replaceFirst("'{2}", "<strong>");
                isTagStarted = true;
            }
        }
        writer.write(s);
        writer.newLine();
    }
}
