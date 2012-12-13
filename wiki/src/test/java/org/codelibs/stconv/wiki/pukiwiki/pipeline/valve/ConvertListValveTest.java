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
public class ConvertListValveTest extends AbstractValveTest {
    /** Convert String */
    private static final String CONVERT_STRING = "-通常リスト";

    /** Result String */
    private static final String CONVERT_RESULT = "<ul><li>通常リスト</li></ul>";

    /** Convert String */
    private static final String CONVERT_STRING2 = "-リスト1\n-リスト2";

    /** Result String */
    private static final String CONVERT_RESULT2 = "<ul><li>リスト1</li><li>リスト2</li></ul>";

    /**
     * Logger for this class
     */
    private static final Logger log = LoggerFactory
            .getLogger(ConvertListValveTest.class);

    /**
     * Default constructor
     */
    public ConvertListValveTest() {
        super();
    }

    /**
     * Default constructor
     * @param arg0 
     */
    public ConvertListValveTest(final String arg0) {
        super(arg0);
    }

    /**
     * Setup
     */
    @Override
    protected void setUp() throws Exception {
        super.valveList.add(new ConvertListValve());
    }

    /**
     * Rigourous Test :-)
     */
    public void testInvoke() {
        assertEquals(CONVERT_RESULT, doLineInvoke(CONVERT_STRING));
        assertEquals(CONVERT_RESULT2, doLineInvoke(CONVERT_STRING2));
    }
}
