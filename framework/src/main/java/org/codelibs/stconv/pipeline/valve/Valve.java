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
package org.codelibs.stconv.pipeline.valve;

import org.codelibs.stconv.storage.StreamStorage;

/**
 * An interface for Valve implementation.
 * 
 * @author shinsuke
 *
 */
public interface Valve {

    /**
     * Invoke an operation.
     * 
     * @param storage
     * @param context
     * @return processed storage
     */
    StreamStorage invoke(StreamStorage storage, ValveContext context);

}
