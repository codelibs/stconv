package org.codelibs.stconv.hatena.pipeline.valve;

import junit.framework.Test;
import junit.framework.TestSuite;

public class HatenaAllTests {

    public static Test suite() {
        final TestSuite suite = new TestSuite(
                "Test for sf.streamconverter.pipeline.valve.hatena");
        //$JUnit-BEGIN$
        suite.addTestSuite(ConvertDefinitionListValveTest.class);
        suite.addTestSuite(ConvertMoreSmallCaptionValveTest.class);
        suite.addTestSuite(ConvertSmallCaptionValveTest.class);
        suite.addTestSuite(ConvertHttpLinkValveTest.class);
        suite.addTestSuite(ConvertHttpsLinkValveTest.class);
        suite.addTestSuite(ConvertCaptionValveTest.class);
        suite.addTestSuite(ConvertTableValveTest.class);
        suite.addTestSuite(ConvertPreValveTest.class);
        suite.addTestSuite(ConvertNumberListValveTest.class);
        suite.addTestSuite(ConvertTimeCaptionValveTest.class);
        suite.addTestSuite(ConvertMailtoLinkValveTest.class);
        suite.addTestSuite(ConvertListValveTest.class);
        suite.addTestSuite(ConvertBlockQuotationValveTest.class);
        suite.addTestSuite(ConvertSuperPreValveTest.class);
        suite.addTestSuite(ConvertLineBreakValveTest.class);
        suite.addTestSuite(ConvertNonParagraphValveTest.class);
        //$JUnit-END$
        return suite;
    }

}
