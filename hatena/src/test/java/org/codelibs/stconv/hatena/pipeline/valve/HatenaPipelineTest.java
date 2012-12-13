package org.codelibs.stconv.hatena.pipeline.valve;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.codelibs.stconv.Constants;
import org.codelibs.stconv.pipeline.PipelineException;
import org.codelibs.stconv.pipeline.StreamConverterPipeline;
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
public class HatenaPipelineTest extends AbstractValveTest {
    private static final String SOURCE_PATH = "C:/development/ework-jdk1.4/streamconverter/convert/source/export.xml";

    private static final String RESULT_PATH = "C:/development/ework-jdk1.4/streamconverter/convert/result/result.xml";

    //	private static final String DICON_PATH = "jp/sf/stconv/hatena/pipeline/valve/StreamConverter.dicon";
    private static final String DICON_PATH = "StreamConverter.dicon";

    /**
     * Logger for this class
     */
    private static final Logger log = LoggerFactory
            .getLogger(HatenaPipelineTest.class);

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public HatenaPipelineTest(final String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(HatenaPipelineTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testPipeline() {

        SingletonS2ContainerFactory.setConfigPath(DICON_PATH);
        SingletonS2ContainerFactory.init();
        final S2Container container = SingletonS2ContainerFactory
                .getContainer();
        //		S2Container container = S2ContainerFactory.create(DICON_PATH);

        final StreamConverterPipeline pipeline = (StreamConverterPipeline) container
                .getComponent(StreamConverterPipeline.class);

        new StringBuffer();
        try {
            final StreamStorage storage = new FileStreamStorageImpl(
                    new FileInputStream(new File(SOURCE_PATH)), "UTF-8");
            pipeline.invoke(storage);

            BufferedReader r = null;
            BufferedWriter bw = null;
            try {
                //				FileWriter fw = new FileWriter(new File(RESULT_PATH));
                final FileOutputStream fos = new FileOutputStream(new File(
                        RESULT_PATH));
                final OutputStreamWriter ow = new OutputStreamWriter(fos,
                        "UTF-8");
                bw = new BufferedWriter(ow);

                r = new BufferedReader(new InputStreamReader(
                        storage.getResultInputStream(), storage.getMetadata()
                                .get(Constants.MKEY_ENCODING).toString()));
                String l = null;
                while ((l = r.readLine()) != null) {
                    bw.write(l);
                    bw.newLine();
                }
                bw.flush();
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
                        bw.close();
                    } catch (final IOException e) {
                    }
                }
            }
            storage.destroy();
        } catch (final StreamStorageException e) {
            log.error("StreamStorage Exception.", e);
        } catch (final PipelineException e) {
            log.error("Pipeline Exception.", e);
        } catch (final FileNotFoundException e) {
            log.error("FileNotFound Exception.", e);
        }

        assertTrue(true);
    }
}
