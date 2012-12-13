package org.codelibs.stconv.wiki.pukiwiki.pipeline.valve;

import org.codelibs.stconv.pipeline.StreamConverterPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvertBlockQuotationValveTest extends AbstractValveTest {
    private static final String CONVERT_STRING = ">引用文";

    private static final String CONVERT_RESULT = "<blockquote><p class=\"quotation\">引用文</p></blockquote>";

    private static final String CONVERT_STRING2 = "> 引用文";

    private static final String CONVERT_STRING3 = ">>引用文";

    private static final String CONVERT_STRING4 = ">>>引用文";

    private static final String CONVERT_STRING5 = ">引用文\n>>引用文";

    private static final String CONVERT_RESULT5 = "<blockquote><p class=\"quotation\">引用文</p><blockquote><p class=\"quotation\">引用文</p></blockquote></blockquote>";

    private static final String CONVERT_STRING6 = ">引用文\n>>引用文\n>>>引用文";

    private static final String CONVERT_RESULT6 = "<blockquote><p class=\"quotation\">引用文</p><blockquote><p class=\"quotation\">引用文</p><blockquote><p class=\"quotation\">引用文</p></blockquote></blockquote></blockquote>";

    /**
     * Logger for this class
     */
    private static final Logger log = LoggerFactory
            .getLogger(ConvertBlockQuotationValveTest.class);

    StreamConverterPipeline pipeline = null;

    public ConvertBlockQuotationValveTest() {
        super();
    }

    public ConvertBlockQuotationValveTest(final String arg0) {
        super(arg0);
    }

    @Override
    protected void setUp() throws Exception {
        valveList.add(new ConvertBlockQuotationValve());
    }

    /**
     * Rigourous Test :-)
     */
    public void testInvoke() {
        assertEquals(CONVERT_RESULT, doLineInvoke(CONVERT_STRING));
        assertEquals(CONVERT_RESULT, doLineInvoke(CONVERT_STRING2));
        assertEquals(CONVERT_RESULT, doLineInvoke(CONVERT_STRING3));
        assertEquals(CONVERT_RESULT, doLineInvoke(CONVERT_STRING4));
        assertEquals(CONVERT_RESULT5, doLineInvoke(CONVERT_STRING5));
        assertEquals(CONVERT_RESULT6, doLineInvoke(CONVERT_STRING6));
        // TODO Failed ??
        //assertEquals(CONVERT_RESULT7,doLineInvoke(CONVERT_STRING7));
    }

}
