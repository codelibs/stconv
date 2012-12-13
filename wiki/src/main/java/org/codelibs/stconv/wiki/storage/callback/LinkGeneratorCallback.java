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
package org.codelibs.stconv.wiki.storage.callback;

/**
 * @author shinsuke
 * 
 */
public interface LinkGeneratorCallback extends Callback {
    public static final String LINK_GENERATOR_CALLBACK = "org.codelibs.stconv.wiki.storage.callback.LinkGeneratorCallback";

    public static final String PAGE_NAME = "pageName";

    public void init();

    public void addAttribute(String key, String value);

    public String getUrl();

}
