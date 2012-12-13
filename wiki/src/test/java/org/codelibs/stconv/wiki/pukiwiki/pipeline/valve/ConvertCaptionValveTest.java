package org.codelibs.stconv.wiki.pukiwiki.pipeline.valve;


public class ConvertCaptionValveTest extends AbstractValveTest {
    /** Convert String */
    private static final String CONVERT_STRING = "***今日のできごと";

    /** Result String */
    private static final String CONVERT_RESULT = "<h3>今日のできごと</h3>";

    /** Convert String */
    private static final String CONVERT_STRING2 = "*今日のできごと";

    /** Result String */
    private static final String CONVERT_RESULT2 = "<h1>今日のできごと</h1>";

    /** Convert String */
    private static final String CONVERT_STRING3 = "今日のできごと";

    /** Result String */
    private static final String CONVERT_RESULT3 = "今日のできごと";

    /**
     * Default constructor
     */
    public ConvertCaptionValveTest() {
        super();
    }

    /**
     * Default constructor
     * @param arg0 
     */
    public ConvertCaptionValveTest(final String arg0) {
        super(arg0);
    }

    /**
     * Setup
     */
    @Override
    protected void setUp() throws Exception {
        valveList.add(new ConvertCaptionValve());
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
