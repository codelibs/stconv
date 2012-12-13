package org.codelibs.stconv.wiki.pukiwiki.pipeline.valve;

import org.codelibs.stconv.storage.StreamStorage;
import org.codelibs.stconv.wiki.storage.callback.LinkGeneratorCallback;
import org.codelibs.stconv.wiki.storage.callback.impl.SimpleLinkGeneratorCallbackImpl;
import org.codelibs.stconv.wiki.storage.impl.CallbackStreamStorageImpl;

public class ConvertPageLinkValveTest extends AbstractValveTest {
    /** Convert String */
    private static final String CONVERT_STRING = "[[Page1]]";

    /** Result String */
    private static final String CONVERT_RESULT = "<a href=\"http://localhost:8080/index.jsp?pageName=Page1\">Page1</a>";

    /** Convert String */
    private static final String CONVERT_STRING2 = "This is a [[Page2]].";

    /** Result String */
    private static final String CONVERT_RESULT2 = "This is a <a href=\"http://localhost:8080/index.jsp?pageName=Page2\">Page2</a>.";

    /** Convert String */
    private static final String CONVERT_STRING3 = "[[Page3]] is [[Page4]].";

    /** Result String */
    private static final String CONVERT_RESULT3 = "<a href=\"http://localhost:8080/index.jsp?pageName=Page3\">Page3</a> is <a href=\"http://localhost:8080/index.jsp?pageName=Page4\">Page4</a>.";

    /**
     * Default constructor
     */
    public ConvertPageLinkValveTest() {
        super();
    }

    /**
     * Default constructor
     * 
     * @param arg0
     */
    public ConvertPageLinkValveTest(final String arg0) {
        super(arg0);
    }

    /**
     * Setup
     */
    @Override
    protected void setUp() throws Exception {
        valveList.add(new ConvertPageLinkValve());
    }

    @Override
    protected void addCallbacks(final StreamStorage storage) {
        ((CallbackStreamStorageImpl) storage).addCallback(
                LinkGeneratorCallback.LINK_GENERATOR_CALLBACK,
                new SimpleLinkGeneratorCallbackImpl(
                        "http://localhost:8080/index.jsp", "UTF-8"));
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
