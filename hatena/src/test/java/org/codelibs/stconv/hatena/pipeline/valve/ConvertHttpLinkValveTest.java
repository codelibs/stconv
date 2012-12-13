package org.codelibs.stconv.hatena.pipeline.valve;

public class ConvertHttpLinkValveTest extends AbstractValveTest {
    /** Convert String1 */
    private static final String CONVERT_STRING = "http://www.yahoo.co.jp/test/abc/abc.html";

    /** Result String1 */
    private static final String CONVERT_RESULT = "<p><a href=\"http://www.yahoo.co.jp/test/abc/abc.html\">http://www.yahoo.co.jp/test/abc/abc.html</a></p>";

    /** Convert String6 */
    private static final String CONVERT_STRING6 = "[http://www.yahoo.co.jp/test/abc/abc.html]";

    /** Result String6 */
    private static final String CONVERT_RESULT6 = "<p><a href=\"http://www.yahoo.co.jp/test/abc/abc.html\">http://www.yahoo.co.jp/test/abc/abc.html</a></p>";

    /** Convert String2 */
    private static final String CONVERT_STRING2 = "ここ[http://www.yahoo.co.jp]を参照";

    /** Result String2 */
    private static final String CONVERT_RESULT2 = "ここ<a href=\"http://www.yahoo.co.jp\">http://www.yahoo.co.jp</a>を参照";

    /** Convert String3 */
    private static final String CONVERT_STRING3 = "ここ[http://www.yahoo.co.jp]を参照ここ[http://www.yahoo.co.jp]も参照";

    /** Result String3 */
    private static final String CONVERT_RESULT3 = "ここ<a href=\"http://www.yahoo.co.jp\">http://www.yahoo.co.jp</a>を参照ここ<a href=\"http://www.yahoo.co.jp\">http://www.yahoo.co.jp</a>も参照";

    /** Convert String4 */
    private static final String CONVERT_STRING4 = "ここ[http://www.yahoo.co.jp:title=はてな]を参照";

    /** Result String4 */
    private static final String CONVERT_RESULT4 = "ここ<a href=\"http://www.yahoo.co.jp\">はてな</a>を参照";

    /** Convert String5 */
    private static final String CONVERT_STRING5 = "ここ[http://www.yahoo.co.jp:title=はてな]を参照ここ[http://www.yahoo.co.jp:title=streamconverter]も参照";

    /** Result String5 */
    private static final String CONVERT_RESULT5 = "ここ<a href=\"http://www.yahoo.co.jp\">はてな</a>を参照ここ<a href=\"http://www.yahoo.co.jp\">streamconverter</a>も参照";

    public ConvertHttpLinkValveTest() {
        super();
    }

    public ConvertHttpLinkValveTest(final String arg0) {
        super(arg0);
    }

    @Override
    protected void setUp() throws Exception {
        super.valveList.add(new ConvertHttpLinkValve());
    }

    /**
     * Rigourous Test :-)
     */
    public void testInvoke() {
        assertEquals(CONVERT_RESULT, doLineInvoke(CONVERT_STRING));
        assertEquals(CONVERT_RESULT2, doLineInvoke(CONVERT_STRING2));
        assertEquals(CONVERT_RESULT3, doLineInvoke(CONVERT_STRING3));
        assertEquals(CONVERT_RESULT4, doLineInvoke(CONVERT_STRING4));
        assertEquals(CONVERT_RESULT5, doLineInvoke(CONVERT_STRING5));
        assertEquals(CONVERT_RESULT6, doLineInvoke(CONVERT_STRING6));
    }

}
