/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega.ocp.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Code;
import org.extex.ocpware.compiler.exception.AliasDefinedException;
import org.extex.ocpware.compiler.exception.AliasNotDefinedException;
import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.exception.MissingExpressionsException;
import org.extex.ocpware.compiler.exception.StateDefinedException;
import org.extex.ocpware.compiler.exception.StateNotDefinedException;
import org.extex.ocpware.compiler.exception.SyntaxException;
import org.extex.ocpware.compiler.exception.TableDefinedException;
import org.extex.ocpware.compiler.exception.TableNotDefinedException;
import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.type.OcpProgram;
import org.extex.resource.ResourceFinder;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class represents a loaded OCP instance. It encapsulates an &Omega;CP
 * program.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4411 $
 */
public class Ocp implements Code, OcpConvertible, Serializable {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 2006;

    /**
     * This is a factory method for OCPs.
     * 
     * @param resource the name of the resource
     * @param finder the resource finder
     * 
     * @return the OCP encountered or <code>null</code> if none is found
     * 
     * @throws HelpingException in case of an error
     * @throws ConfigurationException in case of an configuration error
     */
    public static Ocp load(String resource, ResourceFinder finder)
            throws HelpingException,
                ConfigurationException {

        InputStream stream = null;
        try {
            stream = finder.findResource(resource, "ocp");
            if (stream != null) {
                return new Ocp(resource, OcpProgram.load(stream));
            }

            stream = finder.findResource(resource, "otp");
            if (stream != null) {
                return new Ocp(resource, new CompilerState(stream).compile());
            }

        } catch (AliasNotDefinedException e) {
            throw new HelpingException(
                LocalizerFactory.getLocalizer(Ocp.class), "alias.not.defined", //
                e.getMessage());
        } catch (StateNotDefinedException e) {
            throw new HelpingException(
                LocalizerFactory.getLocalizer(Ocp.class), "state.not.defined", //
                e.getMessage());
        } catch (TableNotDefinedException e) {
            throw new HelpingException(
                LocalizerFactory.getLocalizer(Ocp.class), "table.not.defined", //
                e.getMessage());
        } catch (StateDefinedException e) {
            throw new HelpingException(
                LocalizerFactory.getLocalizer(Ocp.class), "state.defined", //
                e.getMessage());
        } catch (SyntaxException e) {
            throw new HelpingException(
                LocalizerFactory.getLocalizer(Ocp.class), "syntax.exception", //
                e.getMessage());
        } catch (TableDefinedException e) {
            throw new HelpingException(
                LocalizerFactory.getLocalizer(Ocp.class), "table.defined", //
                e.getMessage());
        } catch (AliasDefinedException e) {
            throw new HelpingException(
                LocalizerFactory.getLocalizer(Ocp.class), "alias.defined", //
                e.getMessage());
        } catch (MissingExpressionsException e) {
            throw new HelpingException(
                LocalizerFactory.getLocalizer(Ocp.class), "missing expression", //
                e.getMessage());
        } catch (ArgmentTooBigException e) {
            throw new HelpingException(
                LocalizerFactory.getLocalizer(Ocp.class), "argument.too.big", //
                e.getMessage());
        } catch (IOException e) {
            throw new HelpingException(
                LocalizerFactory.getLocalizer(Ocp.class), "io.exception", //
                e.getMessage());
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // ignored on purpose
                }
            }
        }
        return null;
    }

    /**
     * The field <tt>name</tt> contains the name of the resource.
     */
    private String name;

    /**
     * The field <tt>program</tt> contains the program contained in this
     * instance.
     */
    private OcpProgram program;

    /**
     * Creates a new object.
     * 
     * @param resource the name of the resource
     * @param program the program
     * 
     * @throws HelpingException in case of an error
     */
    public Ocp(String resource, OcpProgram program) throws HelpingException {

        super();
        this.name = resource;
        this.program = program;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.unit.omega.ocp.util.OcpConvertible#convertOcp(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Ocp convertOcp(Context context, TokenSource source,
            Typesetter typesetter) {

        return this;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        throw new HelpingException(LocalizerFactory.getLocalizer(Ocp.class),
            "message");
    }

    /**
     * Getter for the name.
     * 
     * @return the name
     * 
     * @see org.extex.interpreter.type.Code#getName()
     */
    public String getName() {

        return name;
    }

    /**
     * Getter for program.
     * 
     * @return the program
     */
    public OcpProgram getProgram() {

        return program;
    }

    /**
     * This simple little method distinguishes the conditionals from the other
     * primitives. This is necessary for the processing of all \if* primitives.
     * 
     * @return <code>true</code> iff this is some sort if <tt>\if</tt>.
     * 
     * @see org.extex.interpreter.type.Code#isIf()
     */
    public boolean isIf() {

        return false;
    }

    /**
     * Getter for the outer flag.
     * 
     * @return <code>true</code> iff the code is defined outer.
     * 
     * @see org.extex.interpreter.type.Code#isOuter()
     */
    public boolean isOuter() {

        return false;
    }

    /**
     * Setter for the name.
     * 
     * @param name the name to set
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * Setter for program.
     * 
     * @param program the program to set
     */
    public void setProgram(OcpProgram program) {

        this.program = program;
    }

}
