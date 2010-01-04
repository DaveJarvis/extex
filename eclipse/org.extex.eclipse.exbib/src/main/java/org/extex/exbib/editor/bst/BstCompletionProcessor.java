/*
 * Copyright (C) 2010 The ExTeX Group and individual authors listed below
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.extex.exbib.editor.bst.model.BstModel;
import org.extex.exbib.editor.bst.model.BstModelNode;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
public class BstCompletionProcessor implements IContentAssistProcessor {

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
     * @param model
     * @param imageManager
     */
    public BstCompletionProcessor(BstModel model, ImageManager imageManager) {

        this.model = model;
        this.imageManager = imageManager;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeCompletionProposals(org.eclipse.jface.text.ITextViewer,
     *      int)
     */
    @Override
    public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
            int offset) {

        String s = getRegion(viewer, offset);
        if (s == null) {
            return null;
        }
        int i = offset;
        IDocument doc = viewer.getDocument();
        int docLen = doc.getLength();
        try {
            for (i++; i < docLen; i++) {
                char c = doc.getChar(i);
                if ("{}#%'\"".indexOf(c) >= 0 || Character.isWhitespace(c)) {
                    break;
                }
            }
        } catch (BadLocationException e) {
            i = offset - 1;
        }

        List<ICompletionProposal> list = new ArrayList<ICompletionProposal>();
        int len = i - offset + 1;
        int start = offset - s.length();

        for (BstModelNode node : model.getNodes(s)) {
            list.add(new CompletionProposal(node.getName(), start, len, len,
                imageManager.getImage("icons/outline/"
                        + node.getClass().getSimpleName() + ".png"), null,
                null, node.getDescription()));
        }

        return list.toArray(new ICompletionProposal[list.size()]);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeContextInformation(org.eclipse.jface.text.ITextViewer,
     *      int)
     */
    @Override
    public IContextInformation[] computeContextInformation(ITextViewer viewer,
            int offset) {

        // TODO gene: computeContextInformation unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getCompletionProposalAutoActivationCharacters()
     */
    @Override
    public char[] getCompletionProposalAutoActivationCharacters() {

        return new char[]{'.'};
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationAutoActivationCharacters()
     */
    @Override
    public char[] getContextInformationAutoActivationCharacters() {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationValidator()
     */
    @Override
    public IContextInformationValidator getContextInformationValidator() {

        // TODO gene: getContextInformationValidator unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getErrorMessage()
     */
    @Override
    public String getErrorMessage() {

        return null;
    }

    public String getRegion(ITextViewer textViewer, int offset) {

        IDocument doc = textViewer.getDocument();

        try {
            char c = doc.getChar(offset);
            if ("{}#%'\"".indexOf(c) >= 0 || Character.isWhitespace(c)) {
                return null;
            }

            boolean stringFlag = false;

            int i;
            for (i = offset - 1; i >= 0; i--) {
                c = doc.getChar(i);
                if (c == '%') {
                    // drop out of comments immediately
                    return null;
                }
                if (c == '"') {
                    // remember the number of string delimiters modulo 2
                    stringFlag = !stringFlag;
                }
                if (c == '\n' || c == '\r') {
                    // we found the beginning of the line
                    break;
                }
            }
            if (stringFlag) {
                // finally we end up in a string
                return null;
            }
            for (i = offset - 1; i >= 0; i--) {
                c = doc.getChar(i);
                if ("{}#%'\"".indexOf(c) >= 0 || Character.isWhitespace(c)) {
                    break;
                }
            }
            return doc.get(i + 1, offset - i - 1);

        } catch (BadLocationException e) {
            return null;
        }
    }

}
