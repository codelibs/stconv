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

import java.util.List;

import org.codelibs.stconv.pipeline.valve.Valve;
import org.codelibs.stconv.pipeline.valve.ValveContext;
import org.codelibs.stconv.storage.StreamStorage;

/**
 * StreamConverterPipeline has valves to process the operations.
 * 
 * @author shinsuke
 *
 */
public class StreamConverterPipeline {
    /**
     * Name of this pipeline.
     */
    protected String name;

    /**
     * The set of Valves associated with this Pipeline.
     */
    protected Valve[] valves;

    /**
     * Constructor that provides the descriptor for building the pipeline
     */
    public StreamConverterPipeline(final String name) {
        this(name, new Valve[0]);
    }

    /**
     * Constructor that provides the descriptor for building the pipeline
     */
    public StreamConverterPipeline(final String name, final Valve[] valves) {
        this.name = name;
        this.valves = valves;
    }

    /**
     * Constructor that provides the descriptor for building the pipeline
     */
    public StreamConverterPipeline(final String name,
            final List<Valve> valveList) {
        this(name, valveList.toArray(new Valve[valveList.size()]));
    }

    /**
     * Set the name of this pipeline.
     * 
     * @param name
     *            Name of this pipeline.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get the name of this pipeline.
     * 
     * @return String Name of this pipeline.
     */
    public String getName() {
        return name;
    }

    public synchronized void addValve(final Valve valve) {
        // Add this Valve to the set associated with this Pipeline
        final Valve[] results = new Valve[valves.length + 1];
        System.arraycopy(valves, 0, results, 0, valves.length);
        results[valves.length] = valve;
        valves = results;

    }

    public synchronized Valve[] getValves() {
        final Valve[] results = new Valve[valves.length];
        System.arraycopy(valves, 0, results, 0, valves.length);
        return results;
    }

    public synchronized void removeValve(final Valve valve) {
        // Locate this Valve in our list
        int index = -1;
        for (int i = 0; i < valves.length; i++) {
            if (valve == valves[i]) {
                index = i;
                break;
            }
        }
        if (index < 0) {
            return;
        }

        // Remove this valve from our list
        final Valve[] results = new Valve[valves.length - 1];
        int n = 0;
        for (int i = 0; i < valves.length; i++) {
            if (i == index) {
                continue;
            }
            results[n++] = valves[i];
        }
        valves = results;

    }

    public StreamStorage invoke(final StreamStorage storage) {

        final Invocation invocation = new Invocation(getValves());

        // Invoke the first Valve in this pipeline for this request
        return invocation.invokeNext(storage);
    }

    protected static final class Invocation implements ValveContext {

        protected final Valve[] valves;

        protected int at = 0;

        protected Invocation(final Valve[] valves) {
            this.valves = valves;
        }

        @Override
        public StreamStorage invokeNext(StreamStorage storage) {
            if (at < valves.length) {
                final Valve next = valves[at];
                at++;
                storage = next.invoke(storage, this);
            }
            return storage;
        }
    }

}
