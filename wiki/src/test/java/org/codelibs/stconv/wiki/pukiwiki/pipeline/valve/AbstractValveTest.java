package org.codelibs.stconv.wiki.pukiwiki.pipeline.valve;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.codelibs.stconv.Constants;
import org.codelibs.stconv.pipeline.PipelineException;
import org.codelibs.stconv.pipeline.StreamConverterPipeline;
import org.codelibs.stconv.pipeline.valve.Valve;
import org.codelibs.stconv.storage.StreamStorage;
import org.codelibs.stconv.storage.StreamStorageException;
import org.codelibs.stconv.wiki.storage.impl.CallbackStreamStorageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractValveTest extends TestCase {

    /**
     * Logger for this class
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    StreamConverterPipeline pipeline = null;

    List<Valve> valveList = new ArrayList<Valve>();

    public AbstractValveTest() {
        super();
    }

    public AbstractValveTest(final String arg0) {
        super(arg0);
    }

    protected void addCallbacks(final StreamStorage storage) {

    }

    protected String doLineInvoke(final String str) {
        final StringBuffer buffer = new StringBuffer();
        try {
            pipeline = new StreamConverterPipeline("",
                    valveList.toArray(new Valve[valveList.size()]));
            final StreamStorage storage = new CallbackStreamStorageImpl(
                    new ByteArrayInputStream(str.getBytes("UTF-8")), "UTF-8");
            addCallbacks(storage);
            pipeline.invoke(storage);

            BufferedReader r = null;
            try {
                r = new BufferedReader(new InputStreamReader(
                        storage.getResultInputStream(), storage.getMetadata()
                                .get(Constants.MKEY_ENCODING).toString()));
                String l = null;
                while ((l = r.readLine()) != null) {
                    buffer.append(l);
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
        } catch (final Exception e) {
            log.error("Exception.", e);
        }
        return buffer.toString();
    }

}
