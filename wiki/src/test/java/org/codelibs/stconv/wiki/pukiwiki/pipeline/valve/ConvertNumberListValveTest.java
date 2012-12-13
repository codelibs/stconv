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
public class ConvertNumberListValveTest extends AbstractValveTest {

    /** Convert String */
    private static final String CONVERT_STRING = "+番号リスト";

    /** Result String */
    private static final String CONVERT_RESULT = "<ol><li>番号リスト</li></ol>";

    /** Convert String */
    private static final String CONVERT_STRING2 = "+リスト1\n+リスト2";

    /** Result String */
    private static final String CONVERT_RESULT2 = "<ol><li>リスト1</li><li>リスト2</li></ol>";

    /**
     * Default constructor
     */
    public ConvertNumberListValveTest() {
        super();
    }

    /**
     * Default constructor
     * @param arg0 
     */
    public ConvertNumberListValveTest(final String arg0) {
        super(arg0);
    }

    /**
     * Setup
     */
    @Override
    protected void setUp() throws Exception {
        valveList.add(new ConvertNumberListValve());
    }

    /**
     * Rigourous Test :-)
     */
    public void testInvoke() {
        assertEquals(CONVERT_RESULT, doLineInvoke(CONVERT_STRING));
        assertEquals(CONVERT_RESULT2, doLineInvoke(CONVERT_STRING2));
    }
}
