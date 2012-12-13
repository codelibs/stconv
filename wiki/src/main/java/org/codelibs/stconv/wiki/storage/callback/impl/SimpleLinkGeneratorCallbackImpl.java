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
package org.codelibs.stconv.wiki.storage.callback.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.codelibs.stconv.wiki.storage.callback.LinkGeneratorCallback;

/**
 * @author ?
 * 
 */
public class SimpleLinkGeneratorCallbackImpl implements LinkGeneratorCallback {

    private Map attributes;

    private String baseUrl;

    private String encoding;

    public SimpleLinkGeneratorCallbackImpl(final String baseUrl) {
        this(baseUrl, "UTF-8");
    }

    public SimpleLinkGeneratorCallbackImpl(final String baseUrl,
            final String encoding) {
        this.baseUrl = baseUrl;
        this.encoding = encoding;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.codelibs.stconv.wiki.storage.callback.LinkGeneratorCallback#addAttribute(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void addAttribute(final String key, final String value) {
        attributes.put(key, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.codelibs.stconv.wiki.storage.callback.LinkGeneratorCallback#getUrl()
     */
    @Override
    public String getUrl() {
        final StringBuffer url = new StringBuffer(baseUrl);
        boolean newParam = baseUrl.endsWith("?");

        final Set entries = attributes.entrySet();
        for (final Iterator iterator = entries.iterator(); iterator.hasNext();) {
            final Map.Entry entry = (Map.Entry) iterator.next();
            if (newParam) {
                url.append("&");
            } else {
                url.append("?");
                newParam = true;
            }
            try {
                url.append(URLEncoder.encode((String) entry.getKey(), encoding));
            } catch (final UnsupportedEncodingException e) {
                url.append((String) entry.getKey());
            }
            url.append("=");
            try {
                url.append(URLEncoder.encode((String) entry.getValue(),
                        encoding));
            } catch (final UnsupportedEncodingException e) {
                url.append((String) entry.getValue());
            }
        }
        return url.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.codelibs.stconv.wiki.storage.callback.LinkGeneratorCallback#init()
     */
    @Override
    public void init() {
        if (attributes != null) {
            attributes.clear();
        } else {
            attributes = new HashMap();
        }
    }
}
