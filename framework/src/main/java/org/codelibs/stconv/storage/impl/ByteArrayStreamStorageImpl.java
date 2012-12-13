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
package org.codelibs.stconv.storage.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.codelibs.stconv.Constants;
import org.codelibs.stconv.storage.StreamStorage;
import org.codelibs.stconv.storage.StreamStorageException;

public class ByteArrayStreamStorageImpl implements StreamStorage {

    protected int DEFAULT_ARRAY_SIZE = 512;

    protected InputStream inputStream;

    protected ByteArrayOutputStream outputStream;

    protected byte[] buffer = null;

    protected Map<String, Object> metadataMap = new HashMap<String, Object>();

    public ByteArrayStreamStorageImpl(final InputStream in,
            final String encoding) {
        init(in, encoding);
    }

    @Override
    public void init(final InputStream in, final String encoding) {
        metadataMap.put(Constants.MKEY_ENCODING, encoding);

        // TODO better to wrap BufferredInputStream
        inputStream = in;
        outputStream = new ByteArrayOutputStream(DEFAULT_ARRAY_SIZE);
    }

    @Override
    public void destroy() {
        buffer = null;
        outputStream = null;
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

            buffer = outputStream.toByteArray();
            inputStream = new ByteArrayInputStream(buffer);
            outputStream = new ByteArrayOutputStream(DEFAULT_ARRAY_SIZE);
        }

        catch (final IOException e) {
            throw new StreamStorageException("I/O error occurs. ", e);
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

            if (buffer == null) {
                if (inputStream.markSupported()) {
                    inputStream.reset();
                }
                return inputStream;
            }

            return new ByteArrayInputStream(buffer);
        }

        catch (final IOException e) {
            throw new StreamStorageException("I/O error occurs. ", e);
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
     * @param inputStream The inputStream to set.
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
     * @param outputStream The outputStream to set.
     */
    public void setOutputStream(final OutputStream outputStream) {
        if (outputStream instanceof ByteArrayOutputStream) {
            this.outputStream = (ByteArrayOutputStream) outputStream;
        } else {
            throw new StreamStorageException(
                    "The output stream is not ByteArrayOutputStream.");
        }
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
}
