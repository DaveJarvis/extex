/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.engine.typesetter.page;

import java.util.logging.Logger;

import org.extex.core.count.FixedCount;
import org.extex.core.count.CountConstant;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.typesetter.PageContext;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.page.Page;
import org.extex.typesetter.type.page.PageFactory;

/**
 * This class provides a factory for page instances.
 * 
 * <p>
 * The separation of the page into a logical page and a physical page is
 * depicted in the figure below.
 * </p>
 * <div class="figure">
 *   <img src="doc-files/page-1.png" alt="Page Dimensions">
 *   <div class="caption">Page Dimensions</div>
 * </div>
 * <p>
 * The physical page denotes the real paper. DVI has no notion of the physical
 * page but PDF knows of those bounds. The logical page is placed somewhere on
 * the physical page. The physical page has the width {@code \mediawidth} and
 * the height {@code \mediaheight}.
 * </p>
 * <p>
 * The logical page is the area used by TeX to place material on. It has a reference point which is in
 * its upper left corner. This reference point is 1&nbsp;in right and 1&nbsp;in
 * down from the corner of the physical page. The reference point can be shifted
 * further by using the dimen registers {@code \hoffset} and {@code \voffset}.
 * </p>
 * 
 * 
 * <h2>Parameters</h2>
 * 
 * <p>The Dimen Parameter {@code \mediawidth}</p>
 * <p>
 * The dimen parameter {@code \mediawidth} contains the physical width of the
 * page. The logical page is usually smaller.
 * </p>
 * <p>
 * The value of this parameter is used when a page is shipped out and attached
 * to the page. Any modifications of the parameter have no effect to the value
 * stored.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;mediawidth&rang;
 *      &rarr; &lang;optional prefix&rang; {@code \mediawidth} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@link
 *        org.extex.base.parser.ConstantDimenParser#parse(Context,TokenSource,Typesetter)
 *        &lang;dimen value&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  {@code \global} &lang;optional prefix&rang; </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *   \mediawidth=210mm </pre>
 * 
 * 
 * 
 * <p>The Dimen Parameter {@code \mediaheight}</p>
 * <p>
 * The dimen parameter {@code \mediaheight} contains the physical height of the
 * page. The logical page is usually smaller.
 * </p>
 * <p>
 * The value of this parameter is used when a page is shipped out and attached
 * to the page. Any modifications of the parameter have no effect to the value
 * stored.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;mediaheight&rang;
 *      &rarr; &lang;optional prefix&rang; {@code \mediaheight} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@link
 *        org.extex.base.parser.ConstantDimenParser#parse(Context,TokenSource,Typesetter)
 *        &lang;dimen value&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  {@code \global} &lang;optional prefix&rang; </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *   \mediaheight=297mm </pre>
 * 
 * 
 * 
 * <p>The Dimen Parameter {@code \hoffset}</p>
 * <p>
 * The logical page is placed on the physical page such that the upper left
 * corner of the logical page is 1&nbsp;in down and 1&nbsp;in to the right of
 * the physical page. This placement can be influence by the dimen parameter
 * {@code \hoffset}. The dimen parameter {@code \hoffset} contains the
 * horizontal offset added to the reference point when placing the logical page.
 * The default value is 0&nbsp;pt. Thus the reference point is 1&nbsp;in to the
 * right. A positive value shifts the reference point rightwards.
 * </p>
 * <p>
 * The value of this parameter is used when a page is shipped out and attached
 * to the page. Any modifications of the parameter have no effect to the value
 * stored.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;hoffset&rang;
 *      &rarr; &lang;optional prefix&rang; {@code \hoffset} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@link
 *        org.extex.base.parser.ConstantDimenParser#parse(Context,TokenSource,Typesetter)
 *        &lang;dimen value&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  {@code \global} &lang;optional prefix&rang; </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *   \hoffset=-.5in </pre>
 * 
 * 
 * 
 * <p>The Dimen Parameter {@code \voffset}</p>
 * <p>
 * The logical page is placed on the physical page such that the upper left
 * corner of the logical page is 1&nbsp;in down and 1&nbsp;in to the right of
 * the physical page. This placement can be influence by the dimen parameter
 * {@code \voffset}. The dimen parameter {@code \voffset} contains the
 * vertical offset added to the reference point when placing the logical page.
 * The default value is 0&nbsp;pt. Thus the reference point is 1&nbsp;in down. A
 * positive value shifts the reference point downwards.
 * </p>
 * <p>
 * The value of this parameter is used when a page is shipped out and attached
 * to the page. Any modifications of the parameter have no effect to the value
 * stored.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;voffset&rang;
 *      &rarr; &lang;optional prefix&rang; {@code \voffset} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@link
 *        org.extex.base.parser.ConstantDimenParser#parse(Context,TokenSource,Typesetter)
 *        &lang;dimen value&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  {@code \global} &lang;optional prefix&rang; </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *   \voffset=1in </pre>
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class PageFactoryImpl implements PageFactory, LogEnabled {

    /**
     * The field {@code x} contains the static mapping from integers to the
     * corresponding index of the count register for the page number.
     */
    private static final String[] NO = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9"};

    /**
     * The field {@code visitor} contains the node visitor to determine which
     * nodes to keep and to post-process the nodes.
     */
    private final PageFactoryNodeVisitor visitor = new PageFactoryNodeVisitor();


    public PageFactoryImpl() {

    }

public void enableLogging(Logger log) {

        visitor.enableLogging(log);
    }

    /**
     * Get a new instance of a page.
     * 
     * @param nodes the nodes contained
     * @param pageContext the interpreter context
     * @param typesetter the typesetter
     * 
     * @return the new instance or {@code null} if the page would be empty
     * 
     * @throws GeneralException in case of an error
     */
    public Page newInstance(NodeList nodes, PageContext pageContext,
            Typesetter typesetter) throws GeneralException {

        // TODO gene: beware of ClassCastException
        Context context = (Context) pageContext;
        FixedCount[] pageNo = new FixedCount[10];
        for (int i = 0; i < 10; i++) {
            pageNo[i] = new CountConstant(context.getCount(NO[i]));
        }
        PageImpl page = new PageImpl(nodes, pageNo);

        page.setMediaWidth(context.getDimen("mediawidth"));
        page.setMediaHeight(context.getDimen("mediaheight"));
        Dimen off = new Dimen(Dimen.ONE_INCH);
        off.add(context.getDimen("hoffset"));
        page.setMediaHOffset(off);
        off.set(Dimen.ONE_INCH);
        off.add(context.getDimen("voffset"));
        page.setMediaVOffset(off);

        visitor.reset(page, context, typesetter);

        if (nodes.visit(visitor, Boolean.FALSE) == null) {
            return null;
        }
        return page;
    }

}
