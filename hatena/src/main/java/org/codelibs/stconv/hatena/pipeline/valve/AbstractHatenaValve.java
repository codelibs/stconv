/**
 * 
 */
package org.codelibs.stconv.hatena.pipeline.valve;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.codelibs.stconv.Constants;
import org.codelibs.stconv.pipeline.PipelineException;
import org.codelibs.stconv.pipeline.valve.Valve;
import org.codelibs.stconv.pipeline.valve.ValveContext;
import org.codelibs.stconv.storage.StreamStorage;
import org.codelibs.stconv.storage.StreamStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author takeharu
 */
public abstract class AbstractHatenaValve implements Valve {

    /**
     * Logger for this class
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /*
     * (非 Javadoc)
     * 
     * @see org.codelibs.stconv.pipeline.valve.AbstractValve#invoke(org.codelibs.stconv.storage.StreamStorage,
     *      org.codelibs.stconv.pipeline.valve.ValveContext)
     */
    @Override
    public void invoke(final StreamStorage storage, final ValveContext context)
            throws PipelineException {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        final Pattern ptn = Pattern.compile(getPattern());

        try {
            reader = new BufferedReader(new InputStreamReader(
                    storage.getInputStream(), storage.getMetadata()
                            .get(Constants.MKEY_ENCODING).toString()));
            writer = new BufferedWriter(new OutputStreamWriter(
                    storage.getOutputStream(), storage.getMetadata()
                            .get(Constants.MKEY_ENCODING).toString()));
        } catch (final UnsupportedEncodingException e) {
            log.warn("Unsupported Encoding. ", e);
            reader = new BufferedReader(new InputStreamReader(
                    storage.getInputStream()));
        }

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                line = StringEscapeUtils.unescapeHtml(line);
                //				log.debug("LINE:"+line);
                if (ptn.matcher(line).find()) {
                    //					log.debug("HIT:"+line);
                    writeMatchLine(storage, writer, line);
                } else {
                    writeUnmatchLine(writer, line);
                }
            }
            finish(writer);
            writer.flush();
            storage.commit();
        } catch (final StreamStorageException e) {
            log.error("Stream Storage Exception. ", e);
            throw new PipelineException(e);
        } catch (final IOException e) {
            log.error("Stream Storage Exception. ", e);
            throw new PipelineException(e);
        }

        //		if(log.isDebugEnabled())
        //		{
        //			log
        //					.debug("invoke(StreamStorage, ValveContext) - getResultInputStream()=\n");
        //			BufferedReader r = null;
        //			try
        //			{
        //				r = new BufferedReader(new InputStreamReader(storage
        //						.getResultInputStream(), storage.getMetadata().get(Constants.MKEY_ENCODING).toString()));
        //				String l = null;
        //				while ((l = r.readLine()) != null)
        //				{
        //					log.debug(l);
        //				}
        //			}
        //			catch (UnsupportedEncodingException e)
        //			{
        //				log.warn("Unsupported Encoding. ", e);
        //			}
        //			catch (StreamStorageException e)
        //			{
        //				log.warn("Stream Storage Exception. ", e);
        //			}
        //			catch (IOException e)
        //			{
        //				log.error("I/O Exception. ", e);
        //			}
        //			finally
        //			{
        //				if(r != null)
        //				{
        //					try
        //					{
        //						r.close();
        //					}
        //					catch (IOException e)
        //					{
        //					}
        //				}
        //			}
        //
        //		}

        context.invokeNext(storage);

    }

    protected void finish(final BufferedWriter writer) throws IOException {

    }

    protected void writeUnmatchLine(final BufferedWriter writer,
            final String line) throws IOException {

        writer.write(line);
        writer.newLine();
    }

    protected void writeMatchLine(final StreamStorage storage,
            final BufferedWriter writer, final String line) throws IOException {
        writeMatchLine(writer, line);
    }

    protected void writeMatchLine(final BufferedWriter writer, final String line)
            throws IOException {
        writer.write(line);
        writer.newLine();
    }

    protected String getPattern() {
        return "";
    }

}
