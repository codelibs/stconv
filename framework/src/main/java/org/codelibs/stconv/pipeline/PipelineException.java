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
package org.codelibs.stconv.pipeline;

import org.codelibs.stconv.StreamConverterException;

public class PipelineException extends StreamConverterException {

    private static final long serialVersionUID = 1L;

    public PipelineException() {
        super();
    }

    public PipelineException(final String message) {
        super(message);
    }

    public PipelineException(final Throwable nested) {
        super(nested);
    }

    public PipelineException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

}
