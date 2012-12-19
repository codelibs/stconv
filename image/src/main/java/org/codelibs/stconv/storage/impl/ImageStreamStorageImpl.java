/*
 * Copyright 2004-2012 the CodeLibs Project and the Others.
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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.codelibs.stconv.Constants;
import org.codelibs.stconv.storage.StreamStorage;
import org.codelibs.stconv.storage.StreamStorageException;

/**
 * @author shinsuke
 *
 */
public class ImageStreamStorageImpl implements StreamStorage {

    public static final String IMAGE_FORMAT_NAME = "ImageFormatName";

    protected BufferedImage baseImage;

    protected Map<String, Object> metadataMap = new HashMap<String, Object>();

    protected File resultFile;

    protected InputStream resultInputStream;

    protected File inputFile;

    protected InputStream inputStream;

    protected File outputFile;

    protected OutputStream outputStream;

    /* (non-Javadoc)
     * @see org.codelibs.stconv.storage.StreamStorage#init(java.io.InputStream, java.lang.String)
     */
    @Override
    public void init(InputStream in) {
        try {
            baseImage = ImageIO.read(in);
        } catch (IOException e) {
            throw new StreamStorageException("Could not read the stream.", e);
        }

    }

    /* (non-Javadoc)
     * @see org.codelibs.stconv.storage.StreamStorage#destroy()
     */
    @Override
    public void destroy() {
        if (inputStream != null) {
            IOUtils.closeQuietly(inputStream);
        }
        if (inputFile != null) {
            inputFile.delete();
        }

        if (outputStream != null) {
            IOUtils.closeQuietly(outputStream);
        }
        if (outputFile != null) {
            outputFile.delete();
        }

        if (resultInputStream != null) {
            IOUtils.closeQuietly(resultInputStream);
        }
        if (resultFile != null) {
            resultFile.delete();
        }
    }

    /* (non-Javadoc)
     * @see org.codelibs.stconv.storage.StreamStorage#commit()
     */
    @Override
    public void commit() {
        if (inputStream != null) {
            IOUtils.closeQuietly(inputStream);
            inputStream = null;
            inputFile.delete();
            inputFile = null;
            metadataMap.remove(IMAGE_FORMAT_NAME);
        }

        if (outputStream != null) {
            IOUtils.closeQuietly(outputStream);
            outputStream = null;

            if (resultInputStream != null) {
                IOUtils.closeQuietly(resultInputStream);
                resultInputStream = null;
            }
            if (resultFile != null) {
                resultFile.delete();
            }
            resultFile = outputFile;
            outputFile = null;

            FileInputStream fos = null;
            try {
                fos = new FileInputStream(resultFile);
                baseImage = ImageIO.read(fos);
            } catch (FileNotFoundException e) {
                throw new StreamStorageException("Cannot find the temp file: "
                        + resultFile.getAbsolutePath(), e);
            } catch (IOException e) {
                throw new StreamStorageException("I/O error occurs. ", e);
            } finally {
                IOUtils.closeQuietly(fos);
            }
        }
    }

    /* (non-Javadoc)
     * @see org.codelibs.stconv.storage.StreamStorage#getResultInputStream()
     */
    @Override
    public InputStream getResultInputStream() {
        if (resultFile == null) {
            return null;
        }

        if (resultInputStream != null) {
            return resultInputStream;
        }

        try {
            resultFile = File.createTempFile(Constants.DEFAULT_TMPFILE_PREFIX,
                    Constants.DEFAULT_TMPFILE_SUFFIX);
            resultInputStream = new FileInputStream(resultFile);
            return resultInputStream;
        } catch (FileNotFoundException e) {
            throw new StreamStorageException("Cannot find the temp file: "
                    + resultFile.getAbsolutePath(), e);
        } catch (IOException e) {
            throw new StreamStorageException("I/O error occurs. ", e);
        }
    }

    /* (non-Javadoc)
     * @see org.codelibs.stconv.storage.StreamStorage#getInputStream()
     */
    @Override
    public InputStream getInputStream() {
        String formatName = (String) metadataMap.get(IMAGE_FORMAT_NAME);
        if (StringUtils.isBlank(formatName)) {
            throw new StreamStorageException("Image format name is empty.");
        }
        FileOutputStream fos = null;
        try {
            inputFile = File.createTempFile(Constants.DEFAULT_TMPFILE_PREFIX,
                    Constants.DEFAULT_TMPFILE_SUFFIX);
            fos = new FileOutputStream(inputFile);
            ImageIO.write(baseImage, formatName, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            throw new StreamStorageException("Cannot find the temp file: "
                    + inputFile.getAbsolutePath(), e);
        } catch (IOException e) {
            throw new StreamStorageException("I/O error occurs. ", e);
        } finally {
            IOUtils.closeQuietly(fos);
        }
        try {
            inputStream = new FileInputStream(inputFile);
            return inputStream;
        } catch (FileNotFoundException e) {
            throw new StreamStorageException("Cannot find the temp file: "
                    + inputFile.getAbsolutePath(), e);
        }
    }

    /* (non-Javadoc)
     * @see org.codelibs.stconv.storage.StreamStorage#getOutputStream()
     */
    @Override
    public OutputStream getOutputStream() {
        try {
            outputFile = File.createTempFile(Constants.DEFAULT_TMPFILE_PREFIX,
                    Constants.DEFAULT_TMPFILE_SUFFIX);
            outputStream = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            throw new StreamStorageException("Cannot find the temp file: "
                    + outputFile.getAbsolutePath(), e);
        } catch (IOException e) {
            throw new StreamStorageException("I/O error occurs. ", e);
        }
        return outputStream;
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
        this.metadataMap = metadata;
    }

    public BufferedImage getImage() {
        return baseImage;
    }

}
