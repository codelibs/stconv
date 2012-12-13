package org.codelibs.stconv.hatena.pipeline.valve;

public class ConvertCaptionValveTest extends AbstractValveTest {
    /** Convert String */
    private static final String CONVERT_STRING = "*今日のできごと";

    /** Result String */
    private static final String CONVERT_RESULT = "<p><span class=\"sanchor\">■</span> <span class=\"h3\">今日のできごと</span></p>";

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
    }

}
