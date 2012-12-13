package org.codelibs.stconv.hatena.pipeline.valve;

import org.codelibs.stconv.pipeline.StreamConverterPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvertBlockQuotationValveTest extends AbstractValveTest {
    private static final String CONVERT_STRING = ">>\n引用文\n>>\nさらに引用\n<<\n<<";

    private static final String CONVERT_RESULT = "<blockquote>引用文<blockquote>さらに引用</blockquote></blockquote>";

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
    }

}
