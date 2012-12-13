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


/**
 * @author takeharu
 *
 */
public class ConvertItaricFontValveTest extends AbstractValveTest {
    /** Convert String */
    private static final String CONVERT_STRING = "'''Inline Element'''";

    /** Result String */
    private static final String CONVERT_RESULT = "<em>Inline Element</em>";

    /** Convert String */
    private static final String CONVERT_STRING2 = "This is '''Italic Font'''. This is '''Italic Font2'''.";

    /** Result String */
    private static final String CONVERT_RESULT2 = "This is <em>Italic Font</em>. This is <em>Italic Font2</em>.";

    /** Convert String */
    private static final String CONVERT_STRING3 = "'''Inline \nElement'''";

    /** Result String */
    private static final String CONVERT_RESULT3 = "<em>Inline Element</em>";

    /**
     * Default constructor
     */
    public ConvertItaricFontValveTest() {
        super();
    }

    /**
     * Default constructor
     * @param arg0 
     */
    public ConvertItaricFontValveTest(final String arg0) {
        super(arg0);
    }

    /**
     * Setup
     */
    @Override
    protected void setUp() throws Exception {
        valveList.add(new ConvertItaricFontValve());
    }

    /**
     * Rigourous Test :-)
     */
    public void testInvoke() {
        assertEquals(CONVERT_RESULT, doLineInvoke(CONVERT_STRING));
        assertEquals(CONVERT_RESULT2, doLineInvoke(CONVERT_STRING2));
        assertEquals(CONVERT_RESULT3, doLineInvoke(CONVERT_STRING3));
    }

}
