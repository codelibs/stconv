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
public class ConvertPreValveTest extends AbstractValveTest {
    private static final String CONVERT_STRING = " #!/usr/bin/perl -w\n user strict;\n print <<END;\n <html><body>\n <h1>Hello! Workd.</h1></body></html>\n END";

    private static final String CONVERT_RESULT = "<pre>#!/usr/bin/perl -wuser strict;print &lt;&lt;END;&lt;html&gt;&lt;body&gt;&lt;h1&gt;Hello! Workd.&lt;/h1&gt;&lt;/body&gt;&lt;/html&gt;END</pre>";

    /**
     * Logger for this class
     */
    private static final Logger log = LoggerFactory
            .getLogger(ConvertPreValveTest.class);

    /**
     * Default constructor
     */
    public ConvertPreValveTest() {
        super();
    }

    /**
     * Default constructor
     * @param arg0 
     */
    public ConvertPreValveTest(final String arg0) {
        super(arg0);
    }

    /**
     * Setup
     */
    @Override
    protected void setUp() throws Exception {
        super.valveList.add(new ConvertPreValve());
    }

    /**
     * Rigourous Test :-)
     */
    public void testInvoke() {
        assertEquals(CONVERT_RESULT, doLineInvoke(CONVERT_STRING));
        //        assertEquals(CONVERT_RESULT2, doLineInvoke(CONVERT_STRING2));
    }
}
