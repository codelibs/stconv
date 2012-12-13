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

public class StringReplaceAllValve implements Valve {
    /**
     * Logger for this class
     */
    private static final Logger log = LoggerFactory
            .getLogger(StringReplaceAllValve.class);

    private String regex = null;

    private String replacement = null;

    public StringReplaceAllValve() {
    }

    /**
     * This valve calls String#replaceAll(String, String) method.
     * 
     * @param regex   
     * @param replacement 
     */
    public StringReplaceAllValve(final String regex, final String replacement) {
        setRegex(regex);
        setReplacement(replacement);
    }

    @Override
    public void invoke(final StreamStorage storage, final ValveContext context) {
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
            if (regex != null && replacement != null) {
                while ((line = reader.readLine()) != null) {
                    writer.write(new String(line + "\n").replaceAll(regex,
                            replacement));
                }
            } else {
                // Invalid Argument!!
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.write("\n");
                }
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

        context.invokeNext(storage);

    }

    /**
     * @return Returns the replacement.
     */
    public String getReplacement() {
        return replacement;
    }

    /**
     * @param replacement The replacement to set.
     */
    public void setReplacement(final String newString) {
        replacement = newString;
    }

    /**
     * @return Returns the regex.
     */
    public String getRegex() {
        return regex;
    }

    /**
     * @param regex The regex to set.
     */
    public void setRegex(final String oldString) {
        regex = oldString;
    }
}
