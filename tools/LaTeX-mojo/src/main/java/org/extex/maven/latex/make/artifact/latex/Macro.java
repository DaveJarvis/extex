package org.extex.maven.latex.make.artifact.latex;

import java.io.File;
import java.io.IOException;

import org.extex.maven.latex.make.DependencyNet;

/**
 * This is a function object. It is necessary in absence of closures.
 */
abstract class Macro {

    /**
     * Process the macro.
     * 
     * @param reader the reader
     * @param net the dependency net
     * @param base the base file being processed
     * 
     * @throws IOException in case of an I/O error
     */
    public abstract void expand(LatexReader reader, DependencyNet net,
            File base) throws IOException;
}