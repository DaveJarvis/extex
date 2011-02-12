/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter;

import org.extex.backend.BackendDriver;
import org.extex.core.Locator;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.output.OutputRoutine;
import org.extex.typesetter.pageBuilder.PageBuilder;
import org.extex.typesetter.paragraphBuilder.ParagraphBuilder;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.factory.NodeFactory;

/**
 * This interface describes the capabilities of a typesetter. The typesetter is
 * a container for a stack of list makers which perform the task of assembling
 * node lists of the appropriate type and structure. The typesetter acts as
 * proxy. Most requests are simply forwarded to the current list maker.
 * 
 * @see "<logo>TeX</logo> &ndash; The Program [211]"
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public interface Typesetter extends ListMaker {

    /**
     * Clear the internal state about ship-outs. The ship-out mark is reset to
     * <code>false</code>.
     * 
     * @see #isShipoutMark()
     */
    void clearShipoutMark();

    /**
     * Switch to horizontal mode if necessary. If the current mode is a
     * horizontal mode then nothing is done.
     * 
     * @param locator the locator
     * 
     * @return the horizontal list maker
     * 
     * @throws TypesetterException in case of an error
     */
    ListMaker ensureHorizontalMode(Locator locator) throws TypesetterException;

    /**
     * Instructs the typesetter to perform any actions necessary for cleaning up
     * everything at the end of processing. This should involve a ship-out of
     * any material still left unprocessed.
     * 
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of an configuration problem
     */
    void finish() throws TypesetterException, ConfigurationException;

    /**
     * Getter for the back-end driver.
     * 
     * @return the back-end driver
     */
    BackendDriver getBackendDriver();

    /**
     * Getter for the current list maker.
     * 
     * @return the top list maker or <code>null</code> if the stack is empty
     */
    ListMaker getListMaker();

    /**
     * Getter for the manager of the list maker stack.
     * 
     * @return the manager
     */
    ListManager getManager();

    /**
     * Getter for the NodeFactory.
     * 
     * @return the node factory
     */
    NodeFactory getNodeFactory();

    /**
     * Query the ship-out mark. The ship-out mark is an internal state which
     * records whether or not the ship-out method has been called recently. This
     * method can be used to get the current state. The method
     * {@link #clearShipoutMark() clearShipoutMark()} can be used to reset the
     * ship-out mark to <code>false</code>. Initially the ship-out mark is
     * <code>false</code>.
     * 
     * @return <code>true</code> iff there has been an invocation to the
     *         method {@link #shipout(NodeList) shipout()} since the last
     *         clearing
     * @see #clearShipoutMark()
     */
    boolean isShipoutMark();

    /**
     * Open a new list maker and put it in the top of the stack as current box.
     * 
     * @param listMaker the list maker
     * 
     * @throws TypesetterException in case of an error
     */
    void push(ListMaker listMaker) throws TypesetterException;

    /**
     * Setter for the back-end driver. The back-end driver is addressed whenever
     * a complete page has to be shipped out.
     * 
     * @param driver the new back-end driver
     */
    void setBackend(BackendDriver driver);

    /**
     * Setter for the node factory.
     * 
     * @param nodeFactory the node factory
     */
    void setNodeFactory(NodeFactory nodeFactory);

    /**
     * Setter for the typesetter specific options.
     * 
     * @param options the options to use
     */
    void setOptions(TypesetterOptions options);

    /**
     * Setter for the output routine.
     * 
     * @param output the output routine
     */
    void setOutputRoutine(OutputRoutine output);

    /**
     * Setter for the page builder.
     * 
     * @param pageBuilder the page builder to set.
     */
    void setPageBuilder(PageBuilder pageBuilder);

    /**
     * Setter for the paragraph builder.
     * 
     * @param paragraphBuilder the paragraph builder to set.
     */
    void setParagraphBuilder(ParagraphBuilder paragraphBuilder);

    /**
     * Send a list of nodes to the document writer. As a side effect the
     * ship-out mark is set.
     * 
     * @param nodes the nodes to send to the typesetter
     * 
     * @throws TypesetterException in case of an error
     * 
     * @see #clearShipoutMark()
     */
    void shipout(NodeList nodes) throws TypesetterException;

    /**
     * This method produces a diagnostic representation of the current lists in
     * a StringBuffer.
     * 
     * @param sb the target string buffer
     * @param depth the depth for the display
     * @param breadth the breadth of the display
     */
    void showlists(StringBuffer sb, long depth, long breadth);

    /**
     * Push a list maker onto the stack and return the old value.
     * 
     * @param type the type of the list maker
     * @param locator the locator
     * 
     * @return the new list maker
     *
     * @throws UnsupportedOperationException in case that the type is now known
     * @throws TypesetterException in case of an error in the typesetter
     */
    ListMaker pushListMaker(ListMakerType type, Locator locator)
            throws UnsupportedOperationException, TypesetterException;

}
