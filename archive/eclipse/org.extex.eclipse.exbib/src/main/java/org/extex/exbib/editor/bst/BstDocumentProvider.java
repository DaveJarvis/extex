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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;
import org.extex.exbib.editor.bst.scanner.BstPartitionScanner;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class BstDocumentProvider extends FileDocumentProvider {

@Override
    protected IDocument createDocument(Object element) throws CoreException {

        IDocument document = super.createDocument(element);
        if (document != null) {
            IDocumentPartitioner partitioner =
                    new FastPartitioner(new BstPartitionScanner(),
                        new String[]{BstPartitionScanner.BST_CODE,
                                BstPartitionScanner.BST_COMMENT});
            partitioner.connect(document);
            document.setDocumentPartitioner(partitioner);
        }
        return document;
    }

}
