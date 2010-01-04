/*
 * Copyright (C) 2009-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.editor.bst;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.extex.exbib.editor.bst.model.BstModel;
import org.extex.exbib.editor.bst.properties.BstEditorPreferences;
import org.extex.exbib.editor.bst.scanner.BstCodeScanner;
import org.extex.exbib.editor.bst.scanner.BstPartitionScanner;
import org.extex.exbib.editor.bst.scanner.BstScanner;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
public class BstEditorConfiguration extends SourceViewerConfiguration {

    /**
     * The field <tt>doubleClickStrategy</tt> contains the ...
     */
    private BstDoubleClickStrategy doubleClickStrategy;

    /**
     * The field <tt>scanner</tt> contains the ...
     */
    private BstScanner scanner;

    /**
     * The field <tt>tagScanner</tt> contains the ...
     */
    private BstCodeScanner codeScanner;

    /**
     * The field <tt>colorManager</tt> contains the color manager.
     */
    private ColorManager colorManager;

    /**
     * The field <tt>model</tt> contains the ...
     */
    private final BstModel model;

    /**
     * The field <tt>imageManager</tt> contains the ...
     */
    private final ImageManager imageManager;

    /**
     * Creates a new object.
     * 
     * @param colorManager the color manager
     * @param model
     */
    public BstEditorConfiguration(ColorManager colorManager,
            ImageManager imageManager, BstModel model) {

        this.colorManager = colorManager;
        this.imageManager = imageManager;
        this.model = model;
        this.scanner = new BstScanner(colorManager);
        this.codeScanner = new BstCodeScanner(colorManager, model);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     */
    protected BstCodeScanner getBstCodeScanner() {

        return codeScanner;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     */
    protected BstScanner getBstScanner() {

        return scanner;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getConfiguredContentTypes(org.eclipse.jface.text.source.ISourceViewer)
     */
    @Override
    public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {

        return new String[]{IDocument.DEFAULT_CONTENT_TYPE,
                BstPartitionScanner.BST_COMMENT, //
                BstPartitionScanner.BST_CODE};
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getContentAssistant(org.eclipse.jface.text.source.ISourceViewer)
     */
    @Override
    public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {

        ContentAssistant assistant = new ContentAssistant();
        assistant.setContentAssistProcessor(new BstCompletionProcessor(model,
            imageManager), BstPartitionScanner.BST_CODE);
        return assistant;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getDoubleClickStrategy(org.eclipse.jface.text.source.ISourceViewer,
     *      java.lang.String)
     */
    @Override
    public ITextDoubleClickStrategy getDoubleClickStrategy(
            ISourceViewer sourceViewer, String contentType) {

        if (doubleClickStrategy == null) {
            doubleClickStrategy = new BstDoubleClickStrategy();
        }
        return doubleClickStrategy;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getPresentationReconciler(org.eclipse.jface.text.source.ISourceViewer)
     */
    @Override
    public IPresentationReconciler getPresentationReconciler(
            ISourceViewer sourceViewer) {

        PresentationReconciler reconciler = new PresentationReconciler();

        DefaultDamagerRepairer dr =
                new DefaultDamagerRepairer(getBstCodeScanner());
        reconciler.setDamager(dr, BstPartitionScanner.BST_CODE);
        reconciler.setRepairer(dr, BstPartitionScanner.BST_CODE);

        dr = new DefaultDamagerRepairer(getBstScanner());
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

        NonRuleBasedDamagerRepairer ndr =
                new NonRuleBasedDamagerRepairer(new TextAttribute(colorManager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_COMMENT)));
        reconciler.setDamager(ndr, BstPartitionScanner.BST_COMMENT);
        reconciler.setRepairer(ndr, BstPartitionScanner.BST_COMMENT);

        return reconciler;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getTextHover(org.eclipse.jface.text.source.ISourceViewer,
     *      java.lang.String)
     */
    @Override
    public ITextHover getTextHover(ISourceViewer sourceViewer,
            String contentType) {

        if (contentType.equals(BstPartitionScanner.BST_CODE)) {
            return new BstTextHover(model);
        }

        return super.getTextHover(sourceViewer, contentType);
    }

}
