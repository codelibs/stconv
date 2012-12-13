package org.codelibs.stconv.pipeline.valve.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.codelibs.stconv.Constants;
import org.codelibs.stconv.pipeline.PipelineException;
import org.codelibs.stconv.pipeline.valve.Valve;
import org.codelibs.stconv.pipeline.valve.ValveContext;
import org.codelibs.stconv.storage.StreamStorage;
import org.codelibs.stconv.storage.StreamStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToLowerCaseValve implements Valve {
    /**
     * Logger for this class
     */
    private static final Logger log = LoggerFactory
            .getLogger(ToLowerCaseValve.class);

    @Override
    public void invoke(final StreamStorage storage, final ValveContext context)
            throws PipelineException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    storage.getInputStream(), storage.getMetadata()
                            .get(Constants.MKEY_ENCODING).toString()));
        } catch (final UnsupportedEncodingException e) {
            log.warn("Unsupported Encoding. ", e);
            reader = new BufferedReader(new InputStreamReader(
                    storage.getInputStream()));
        }
        final BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(storage.getOutputStream()));

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                writer.write(line.toLowerCase());
                writer.write("\n");
            }
            writer.flush();
            storage.commit();
        } catch (final StreamStorageException e) {
            log.error("Stream Storage Exception. ", e);
            throw new PipelineException(e);
        } catch (final IOException e) {
            log.error("Stream Storage Exception. ", e);
            throw new PipelineException(e);
        }

        if (log.isDebugEnabled()) {
            log.debug("invoke(StreamStorage, ValveContext) - getResultInputStream()=\n");
            BufferedReader r = null;
            try {
                r = new BufferedReader(new InputStreamReader(
                        storage.getResultInputStream(), storage.getMetadata()
                                .get(Constants.MKEY_ENCODING).toString()));
                String l = null;
                while ((l = r.readLine()) != null) {
                    log.debug(l);
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

        }

        context.invokeNext(storage);

    }
}
