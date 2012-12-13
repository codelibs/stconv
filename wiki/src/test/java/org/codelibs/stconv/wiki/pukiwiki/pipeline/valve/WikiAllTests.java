package org.codelibs.stconv.wiki.pukiwiki.pipeline.valve;

import junit.framework.Test;
import junit.framework.TestSuite;

public class WikiAllTests {

    public static Test suite() {
        final TestSuite suite = new TestSuite(
                "Test for sf.streamconverter.pipeline.valve.hatena");
        //$JUnit-BEGIN$
        suite.addTestSuite(ConvertBoldFontValveTest.class);
        suite.addTestSuite(ConvertNumberListValveTest.class);
        suite.addTestSuite(ConvertListValveTest.class);
        suite.addTestSuite(ConvertLineBreakValveTest.class);
        suite.addTestSuite(ConvertBlockQuotationValveTest.class);
        suite.addTestSuite(ConvertPreValveTest.class);
        suite.addTestSuite(ConvertTableValveTest.class);
        suite.addTestSuite(ConvertPageLinkValveTest.class);
        suite.addTestSuite(ConvertItaricFontValveTest.class);
        suite.addTestSuite(ConvertCaptionValveTest.class);
        //$JUnit-END$

        return suite;
    }

}
