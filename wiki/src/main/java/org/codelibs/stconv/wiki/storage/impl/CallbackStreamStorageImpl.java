/*
 * Copyright 2003-2006 Stream Converter Project Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.codelibs.stconv.wiki.storage.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.codelibs.stconv.Constants;
import org.codelibs.stconv.storage.StreamStorage;
import org.codelibs.stconv.storage.StreamStorageException;
import org.codelibs.stconv.wiki.storage.callback.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallbackStreamStorageImpl implements StreamStorage {
    /**
     * Logger for this class
     */
    private static final Logger log = LoggerFactory
            .getLogger(CallbackStreamStorageImpl.class);

    protected String DEFAULT_TMPFILE_PREFIX = "STREAMSTORAGE_";

    protected String DEFAULT_TMPFILE_SUFFIX = ".tmp";

    protected InputStream inputStream;

    protected OutputStream outputStream;

    protected String encoding;

    protected File previousFile;

    protected File currentFile;

    protected Map<String, Callback> callbackMap;

    protected Map<String, Object> metadataMap = new HashMap<String, Object>();

    public CallbackStreamStorageImpl(final InputStream in, final String encoding) {
        metadataMap.put(Constants.MKEY_ENCODING, encoding);
        init(in);
    }

    @Override
    public void init(final InputStream in) {

        try {
            // TODO better to wrap BufferredInputStream
            inputStream = in;
            this.encoding = encoding;
            previousFile = null;
            currentFile = File.createTempFile(DEFAULT_TMPFILE_PREFIX,
                    DEFAULT_TMPFILE_SUFFIX);
            outputStream = new FileOutputStream(currentFile);
        } catch (final FileNotFoundException e) {
            log.error("Cannot find the temp file: " + currentFile.getName(), e);
            throw new StreamStorageException(e);
        } catch (final IOException e) {
            log.error("I/O error occurs. ", e);
            throw new StreamStorageException(e);
        }
    }

    @Override
    public void destroy() {
        if (previousFile != null && previousFile.isFile()) {
            previousFile.deleteOnExit();
        }
        if (currentFile != null && currentFile.isFile()) {
            currentFile.deleteOnExit();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.codelibs.stconv.storage.impl.StreamStorage#commit()
     */
    @Override
    public void commit() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }

            if (previousFile != null && previousFile.isFile()) {
                previousFile.deleteOnExit();
            }

            previousFile = currentFile;
            currentFile = File.createTempFile(DEFAULT_TMPFILE_PREFIX,
                    DEFAULT_TMPFILE_SUFFIX);
            inputStream = new FileInputStream(previousFile);
            outputStream = new FileOutputStream(currentFile);
        } catch (final FileNotFoundException e) {
            log.error("Cannot find the temp file. ", e);
            throw new StreamStorageException(e);
        } catch (final IOException e) {
            log.error("I/O error occurs. ", e);
            throw new StreamStorageException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.codelibs.stconv.storage.impl.StreamStorage#getResultInputStream()
     */
    @Override
    public InputStream getResultInputStream() {
        try {
            if (log.isDebugEnabled()) {
                log.debug("getResultInputStream() - previousFile="
                        + previousFile);
            }

            if (previousFile == null) {
                if (inputStream.markSupported()) {
                    inputStream.reset();
                }
                return inputStream;
            }

            return new FileInputStream(previousFile);
        } catch (final FileNotFoundException e) {
            log.error("Cannot find the temp file: " + previousFile.getName(), e);
            throw new StreamStorageException(e);
        } catch (final IOException e) {
            log.error("I/O error occurs. ", e);
            throw new StreamStorageException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.codelibs.stconv.storage.impl.StreamStorage#getInputStream()
     */
    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param inputStream
     *            The inputStream to set.
     */
    public void setInputStream(final InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.codelibs.stconv.storage.impl.StreamStorage#getOutputStream()
     */
    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * @param outputStream
     *            The outputStream to set.
     */
    public void setOutputStream(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /* (non-Javadoc)
     * @see org.codelibs.stconv.storage.StreamStorage#getMetadata()
     */
    @Override
    public Map<String, Object> getMetadata() {
        return metadataMap;
    }

    /* (non-Javadoc)
     * @see org.codelibs.stconv.storage.StreamStorage#setMetadata(java.util.Map)
     */
    @Override
    public void setMetadata(Map<String, Object> metadata) {
        metadataMap = metadata;
    }

    public void addCallback(final String key, final Callback callback) {
        // TODO Auto-generated method stub
        if (callbackMap == null) {
            callbackMap = new HashMap<String, Callback>();
        }
        callbackMap.put(key, callback);
    }

    public Callback getCallback(final String key) {
        if (callbackMap == null) {
            return null;
        }
        return callbackMap.get(key);
    }

    public Callback removeCallback(final String key) {
        if (callbackMap != null) {
            return callbackMap.remove(key);
        }
        return null;
    }
}
