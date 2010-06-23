/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.felix.gogo.runtime;

import org.apache.felix.gogo.runtime.threadio.ThreadIOImpl;
import org.osgi.service.command.CommandSession;

public class Context extends CommandProcessorImpl
{
    public static final String EMPTY = "";
    
    private static final ThreadIOImpl threadio;
    private final CommandSession session;

    static
    {
        threadio = new ThreadIOImpl();
        threadio.start();
    }

    public Context()
    {
        super(threadio, null);
        session = (CommandSessionImpl) createSession(System.in, System.out, System.err);
    }

    public Object execute(CharSequence source) throws Exception
    {
        Object result = new Exception();
        try
        {
            return result = session.execute(source);
        }
        finally
        {
            System.err.println("execute<" + source + "> = ("
                + (null == result ? "Null" : result.getClass().getSimpleName()) + ")("
                + result + ")\n");
        }
    }

    public void addCommand(String function, Object target)
    {
        addCommand("test", target, function);
    }

    public void set(String name, Object value)
    {
        session.put(name, value);
    }

}
