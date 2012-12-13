package org.codelibs.stconv.wiki.pukiwiki.pipeline.valve;


public class ConvertLineBreakValveTest extends AbstractValveTest {
    private static final String CONVERT_STRING = "以下は&br;改行されます。";

    private static final String CONVERT_RESULT = "以下は<br />改行されます。";

    /**
     * Default constructor
     */
    public ConvertLineBreakValveTest() {
        super();
    }

    /**
     * Default constructor
     * @param arg0 
     */
    public ConvertLineBreakValveTest(final String arg0) {
        super(arg0);
    }

    /**
     * Setup
     */
    @Override
    protected void setUp() throws Exception {
        super.valveList.add(new ConvertLineBreakValve());
    }

    /**
     * Rigourous Test :-)
     */
    public void testInvoke() {
        assertEquals(CONVERT_RESULT, doLineInvoke(CONVERT_STRING));
    }

}
