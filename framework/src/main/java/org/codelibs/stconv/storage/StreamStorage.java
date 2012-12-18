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
package org.codelibs.stconv.storage;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * An interface that contains a processed data.
 * 
 * @author shinsuke
 *
 */
public interface StreamStorage {
    /**
     * Initialize this StreamStorage object.
     * 
     * @param in an input stream that you want to convert
     * @param encoding an encoding for the given input stream
     */
    void init(InputStream in);

    /**
     * Destroys instances, such as caches, in this class
     */
    void destroy();

    /**
     * Commits a written output stream, and then you can get the commited stream
     * from getInputStream().
     * 
     */
    void commit();

    /**
     * @return Returns the converted byte stream.
     */
    InputStream getResultInputStream();

    /**
     * @return Returns the inputStream.
     */
    InputStream getInputStream();

    /**
     * @return Returns the outputStream.
     */
    OutputStream getOutputStream();

    /**
     * @return Returns the metadata map.
     */
    Map<String, Object> getMetadata();

    /**
     * Sets a metadata.
     * 
     * @param metadata
     */
    void setMetadata(Map<String, Object> metadata);
}
