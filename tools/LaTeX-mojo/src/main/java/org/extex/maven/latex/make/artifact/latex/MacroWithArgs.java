
package org.extex.maven.latex.make.artifact.latex;

import java.io.File;
import java.io.IOException;

import org.extex.maven.latex.make.DependencyNet;

/**
 * This abstract base class provides argument parsing. one optional argument and
 * one real argument are recognized. The real argument may be a block or or
 * consists of a single letter.
 */
abstract class MacroWithArgs extends Macro {

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.maven.latex.make.artifact.latex.Macro#expand(LatexReader,
     *      org.extex.maven.latex.make.DependencyNet, java.io.File)
     */
    @Override
    public final void expand(LatexReader reader, DependencyNet net, File base)
            throws IOException {

        int c = reader.scanNext();
        String opt = null;
        if (c == '[') {
            reader.unread(c);
            opt = reader.scanOption();
        } else {
            reader.unread(c);
        }
        String arg = reader.scanBlock();
        if (arg == null) {
            return;
        }
        expand(reader, net, base, opt, arg);
    }

    /**
     * Process the macro.
     * 
     * @param reader the reader to consume further input
     * @param net the net
     * @param base the base file
     * @param opt the optional argument or <code>null</code>
     * @param arg the argument
     * 
     * @throws IOException in case of an I/O error
     */
    protected abstract void expand(LatexReader reader, DependencyNet net,
            File base, String opt, String arg) throws IOException;

}
