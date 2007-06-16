/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.pdftex;

import org.extex.backend.documentWriter.DocumentWriter;
import org.extex.backend.documentWriter.PdftexSupport;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.core.count.Count;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.pdf.api.exception.InterpreterPdftexException;
import org.extex.typesetter.Typesetter;

/**
 * This class provides a base class for pdf<logo>TeX</logo> primitives.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4791 $
 */
public abstract class AbstractPdftexCode extends AbstractCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param name the name for tracing and debugging
     */
    public AbstractPdftexCode(String name) {

        super(name);
    }

    /**
     * Check that pdfTeX is active.
     * 
     * @param context the interpreter context
     * @param typesetter the typesetter
     * 
     * @return the casted document writer with pdf support
     * 
     * @throws InterpreterPdftexException in case that pdfTeX is not active
     * @throws NoHelpException in case of an exception in the back-end
     */
    protected PdftexSupport ensurePdftex(Context context, Typesetter typesetter)
            throws InterpreterPdftexException,
                NoHelpException {

        DocumentWriter documentWriter;
        try {
            documentWriter = typesetter.getBackendDriver().getDocumentWriter();
        } catch (DocumentWriterException e) {
            throw new NoHelpException(e);
        }

        if (documentWriter instanceof PdftexSupport
                && context.getCount("pdfoutput").gt(Count.ZERO)) {
            return (PdftexSupport) documentWriter;
        }
        throw new InterpreterPdftexException(printableControlSequence(context));
    }

}
