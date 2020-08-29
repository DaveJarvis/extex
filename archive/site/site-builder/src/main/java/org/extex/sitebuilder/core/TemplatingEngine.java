/*
 * Copyright (C) 2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package org.extex.sitebuilder.core;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * This class encapsulates the templating engine.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TemplatingEngine implements LibReceiver {

    /**
     * The field <tt>lib</tt> contains the libraries to be loaded.
     */
    private List<String> libraries = new ArrayList<String>();

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.sitebuilder.core.LibReceiver#lib(java.lang.String)
     */
    @Override
    public void lib(String lib) {

        libraries.add(lib);
    }

    /**
     * Create and initialize a new context.
     * 
     * @param engine the engine
     * 
     * @return the new context
     * 
     * @throws Exception in case of an error
     */
    protected VelocityContext makeContext(VelocityEngine engine)
            throws Exception {

        VelocityContext context = new VelocityContext();
        context.setAllowRendering(false);
        for (String s : libraries) {
            engine.getTemplate(s).merge(context, null);
        }
        context.setAllowRendering(true);
        context.put("currentDate", new Date());
        context.put("dateFormat", new SimpleDateFormat("d/m/yyyy",
            Locale.ENGLISH));
        return context;
    }

    /**
     * Create a new engine and initialize it.
     * 
     * @return the Velocity engine
     * 
     * @throws Exception in case of an error
     */
    protected VelocityEngine makeEngine() throws Exception {

        VelocityEngine engine = new VelocityEngine();
        // engine.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM, logChute);
        // engine.setProperty(JdkLogChute.RUNTIME_LOG_JDK_LOGGER, "xxx");

        Properties prop = new Properties();
        String name = getClass().getName().replace('.', '/') + ".properties";
        InputStream stream =
                getClass().getClassLoader().getResourceAsStream(name);
        if (stream == null) {
            throw new FileNotFoundException(name);
        }
        try {
            prop.load(stream);
        } finally {
            stream.close();
        }
        engine.init(prop);
        return engine;
    }

}
