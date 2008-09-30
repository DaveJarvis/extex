/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.bsf;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Logger;

import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;
import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.BibliographyCore;
import org.extex.exbib.core.bst.exception.ExBibBstNotFoundException;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibFileNotFoundException;
import org.extex.exbib.core.exceptions.ExBibIoException;
import org.extex.exbib.core.io.Writer;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;

/**
 * This class provides a plug-in replacement for a bibliography processor. It is
 * based on the Bean Scripting Framework (BSF).
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BsfProcessor extends BibliographyCore
        implements
            Processor,
            ResourceAware {

    /**
     * The field <tt>outWriter</tt> contains the output writer.
     */
    private Writer outWriter;

    /**
     * The field <tt>warnings</tt> contains the number of warnings.
     */
    private long warnings = 0;

    /**
     * The field <tt>script</tt> contains the name of the scripting language.
     */
    private String script;

    /**
     * The field <tt>finder</tt> contains the resource finder.
     */
    private ResourceFinder finder;

    /**
     * Creates a new object without database and logger. Thoise have to be
     * provided via setters.
     */
    public BsfProcessor() {

        super(null, null);
    }

    /**
     * Creates a new object with database and logger.
     * 
     * @param db the database
     * @param log the logger
     */
    public BsfProcessor(DB db, Logger log) {

        super(db, log);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.BibliographyCore#configure(org.extex.framework.configuration.Configuration)
     */
    @Override
    public void configure(Configuration config) throws ConfigurationException {

        super.configure(config);
        script = config.getAttribute("script");
        BSFManager.registerScriptingEngine(script, //
            config.getAttribute("engine"), //
            config.getAttribute("extensions").split(":"));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.Processor#getMacroNames()
     */
    public List<String> getMacroNames() {

        return getDB().getMacroNames();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.Processor#getNumberOfWarnings()
     */
    public long getNumberOfWarnings() {

        return warnings;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.Processor#getOutWriter()
     */
    public Writer getOutWriter() {

        return outWriter;
    }

    /**
     * Any Entry type is treated as known.
     * 
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.Processor#isKnown(java.lang.String)
     */
    public boolean isKnown(String type) {

        return true;
    }

    /**
     * Load a script into memory.
     * 
     * @param sty the script
     * 
     * @return the string representation of the resource
     * 
     * @throws IOException in case of an I/O error
     * @throws ExBibBstNotFoundException
     */
    private String load(String sty)
            throws IOException,
                ExBibBstNotFoundException {

        NamedInputStream is = finder.findResource(sty, script);
        if (is == null) {
            throw new ExBibBstNotFoundException(sty, null);
        }
        InputStreamReader r =
                new InputStreamReader(new BufferedInputStream(is), "UTF-8");
        StringBuilder sb = new StringBuilder();
        try {
            for (int c = r.read(); c >= 0; c = r.read()) {
                sb.append((char) c);
            }
        } finally {
            is.close();
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.Processor#process(org.extex.exbib.core.io.Writer)
     */
    public long process(Writer outWriter) throws ExBibException {

        this.outWriter = outWriter;

        try {
            loadDatabases();
        } catch (FileNotFoundException e) {
            throw new ExBibFileNotFoundException(e.getMessage(), null);
        }

        try {
            BSFManager manager = new BSFManager();
            manager.declareBean("bibDB", getDB(), DB.class);
            manager.declareBean("bibWriter", outWriter, Writer.class);
            manager.declareBean("bibProcessor", this, Processor.class);

            manager.eval(script, "Test", 0, 0, "mainxx()");
            for (String sty : getBibliographyStyles()) {
                manager.exec(script, sty, 0, 0, load(sty));
            }

        } catch (BSFException e) {
            throw new ExBibException(e.getLocalizedMessage().replaceFirst(
                "^exception from Groovy: ", ""));
        } catch (IOException e) {
            throw new ExBibIoException(e);
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.resource.ResourceAware#setResourceFinder(org.extex.resource.ResourceFinder)
     */
    public void setResourceFinder(ResourceFinder finder) {

        this.finder = finder;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.Processor#warning(java.lang.String)
     */
    public void warning(String message) {

        Logger logger = getLogger();
        if (logger != null) {
            logger.warning(message);
        }
        warnings++;
    }

}
