package org.codelibs.stconv.pipeline;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.codelibs.stconv.AbstractTestCase;
import org.codelibs.stconv.Constants;
import org.codelibs.stconv.storage.StreamStorage;
import org.codelibs.stconv.storage.StreamStorageException;
import org.codelibs.stconv.storage.impl.FileStreamStorageImpl;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for simple App.
 *
 * @author <a href="mailto:jason@zenplex.com">Jason van Zyl</a>
 */
public class StreamConverterPipelineTest extends AbstractTestCase {
    /**
     * Logger for this class
     */
    private static final Logger log = LoggerFactory
            .getLogger(StreamConverterPipelineTest.class);

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public StreamConverterPipelineTest(final String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(StreamConverterPipelineTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testPipeline() {
        SingletonS2ContainerFactory.setConfigPath("StreamConverter.dicon");
        SingletonS2ContainerFactory.init();
        final S2Container container = SingletonS2ContainerFactory
                .getContainer();

        final StreamConverterPipeline pipeline = (StreamConverterPipeline) container
                .getComponent(StreamConverterPipeline.class);

        final StringBuffer buffer = new StringBuffer();
        try {
            final StreamStorage storage = new FileStreamStorageImpl(
                    new ByteArrayInputStream("abc\nABC\ndef\nDEF\n".getBytes()),
                    "UTF-8");
            pipeline.invoke(storage);

            BufferedReader r = null;
            try {
                r = new BufferedReader(new InputStreamReader(
                        storage.getResultInputStream(), storage.getMetadata()
                                .get(Constants.MKEY_ENCODING).toString()));
                String l = null;
                while ((l = r.readLine()) != null) {
                    buffer.append(l);
                    buffer.append("\n");
                }
            } catch (final UnsupportedEncodingException e) {
                log.warn("Unsupported Encoding. ", e);
            } catch (final StreamStorageException e) {
                log.warn("Stream Storage Exception. ", e);
            } catch (final IOException e) {
                log.error("I/O Exception. ", e);
            } finally {
                if (r != null) {
                    try {
                        r.close();
                    } catch (final IOException e) {
                    }
                }
            }
            storage.destroy();
        } catch (final StreamStorageException e) {
            log.error("StreamStorage Exception.", e);
        } catch (final PipelineException e) {
            log.error("Pipeline Exception.", e);
        }

        assertEquals(buffer.toString(), "ABC\nABC\nDEF\nDEF\n");
    }
}
