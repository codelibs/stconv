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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author takeharu
 *
 */
public class ConvertParagraphValveTest extends AbstractValveTest {
    private static final String CONVERT_STRING = "normal string";

    private static final String CONVERT_RESULT = "<p>normal string</p>";

    private static final String CONVERT_STRING2 = "normal \nstring";

    private static final String CONVERT_RESULT2 = "<p>normal string</p>";

    private static final String CONVERT_STRING3 = ">inline element";//quatation

    private static final String CONVERT_RESULT3 = ">inline element";

    private static final String CONVERT_STRING4 = "|aaa|bbb|ccc|";//table

    private static final String CONVERT_RESULT4 = "|aaa|bbb|ccc|";

    private static final String CONVERT_STRING5 = ",aaa,bbb,ccc";//csv

    private static final String CONVERT_RESULT5 = ",aaa,bbb,ccc";

    private static final String CONVERT_STRING6 = "-inline element";//list 

    private static final String CONVERT_RESULT6 = "-inline element";

    private static final String CONVERT_STRING7 = "+inline element";//number list

    private static final String CONVERT_RESULT7 = "+inline element";

    private static final String CONVERT_STRING8 = "*inline element";//heading 

    private static final String CONVERT_RESULT8 = "*inline element";

    private static final String CONVERT_STRING9 = ":inline element";//definition

    private static final String CONVERT_RESULT9 = ":inline element";

    private static final String CONVERT_STRING10 = "#contents";//contents

    private static final String CONVERT_RESULT10 = "#contents";

    private static final String CONVERT_STRING11 = "//comment";//comment

    private static final String CONVERT_RESULT11 = "//comment";

    private static final String CONVERT_STRING12 = " preformatted texts";//preformat texts

    private static final String CONVERT_RESULT12 = " preformatted texts";//

    private static final String CONVERT_STRING13 = "aaa \nbbb\n\nccc";//empty line

    private static final String CONVERT_RESULT13 = "<p>aaa bbb</p><p>ccc</p>";

    private static final String CONVERT_STRING14 = "~text \n~|aaa";//escape block element

    private static final String CONVERT_RESULT14 = "<p>text |aaa</p>";

    /**
     * Logger for this class
     */
    private static final Logger log = LoggerFactory
            .getLogger(ConvertParagraphValveTest.class);

    /**
     * Default constructor
     */
    public ConvertParagraphValveTest() {
        super();
    }

    /**
     * Default constructor
     * @param arg0 
     */
    public ConvertParagraphValveTest(final String arg0) {
        super(arg0);
    }

    /**
     * Setup
     */
    @Override
    protected void setUp() throws Exception {
        super.valveList.add(new ConvertParagraphValve());
    }

    /**
     * Rigourous Test :-)
     */
    public void testInvoke() {
        assertEquals(CONVERT_RESULT, doLineInvoke(CONVERT_STRING));
        assertEquals(CONVERT_RESULT2, doLineInvoke(CONVERT_STRING2));
        assertEquals(CONVERT_RESULT3, doLineInvoke(CONVERT_STRING3));
        assertEquals(CONVERT_RESULT4, doLineInvoke(CONVERT_STRING4));
        assertEquals(CONVERT_RESULT5, doLineInvoke(CONVERT_STRING5));
        assertEquals(CONVERT_RESULT6, doLineInvoke(CONVERT_STRING6));
        assertEquals(CONVERT_RESULT7, doLineInvoke(CONVERT_STRING7));
        assertEquals(CONVERT_RESULT8, doLineInvoke(CONVERT_STRING8));
        assertEquals(CONVERT_RESULT9, doLineInvoke(CONVERT_STRING9));
        assertEquals(CONVERT_RESULT10, doLineInvoke(CONVERT_STRING10));
        assertEquals(CONVERT_RESULT11, doLineInvoke(CONVERT_STRING11));
        assertEquals(CONVERT_RESULT12, doLineInvoke(CONVERT_STRING12));
        assertEquals(CONVERT_RESULT13, doLineInvoke(CONVERT_STRING13));
        assertEquals(CONVERT_RESULT14, doLineInvoke(CONVERT_STRING14));
    }
}
