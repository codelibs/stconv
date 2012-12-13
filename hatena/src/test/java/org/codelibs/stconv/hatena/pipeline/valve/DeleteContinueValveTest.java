package org.codelibs.stconv.hatena.pipeline.valve;

public class DeleteContinueValveTest extends AbstractValveTest {
    private static final String CONVERT_RESULT = "";

    private static final String CONVERT_RESULT4 = "===";

    private static final String CONVERT_STRING = "====";

    private static final String CONVERT_STRING2 = "=====";

    private static final String CONVERT_STRING3 = "======";

    private static final String CONVERT_STRING4 = "===";

    public DeleteContinueValveTest() {
        super();
    }

    public DeleteContinueValveTest(final String arg0) {
        super(arg0);
    }

    @Override
    protected void setUp() throws Exception {
        valveList.add(new DeleteContinueValve());
    }

    /**
     * Rigourous Test :-)
     */
    public void testInvoke() {
        assertEquals(CONVERT_RESULT, doLineInvoke(CONVERT_STRING));
        assertEquals(CONVERT_RESULT, doLineInvoke(CONVERT_STRING2));
        assertEquals(CONVERT_RESULT, doLineInvoke(CONVERT_STRING3));
        assertEquals(CONVERT_RESULT4, doLineInvoke(CONVERT_STRING4));
    }

}
