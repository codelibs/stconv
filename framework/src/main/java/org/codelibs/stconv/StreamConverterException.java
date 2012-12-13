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
package org.codelibs.stconv;

/**
 * a base exception for stconv.
 * 
 * @author shinsuke
 *
 */
public class StreamConverterException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StreamConverterException() {
        super();
    }

    public StreamConverterException(final String message) {
        super(message);
    }

    public StreamConverterException(final Throwable nested) {
        super(nested);
    }

    public StreamConverterException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

}
