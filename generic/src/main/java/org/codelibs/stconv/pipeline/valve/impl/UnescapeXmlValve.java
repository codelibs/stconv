package org.codelibs.stconv.pipeline.valve.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringEscapeUtils;
import org.codelibs.stconv.Constants;
import org.codelibs.stconv.pipeline.PipelineException;
import org.codelibs.stconv.pipeline.valve.Valve;
import org.codelibs.stconv.pipeline.valve.ValveContext;
import org.codelibs.stconv.storage.StreamStorage;
import org.codelibs.stconv.storage.StreamStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnescapeXmlValve implements Valve {
    /**
     * Logger for this class
     */
    private static final Logger log = LoggerFactory
            .getLogger(UnescapeXmlValve.class);

    @Override
    public StreamStorage invoke(final StreamStorage storage,
            final ValveContext context) {
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
                writer.write(StringEscapeUtils.unescapeXml(line));
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

        return context.invokeNext(storage);

    }
}
