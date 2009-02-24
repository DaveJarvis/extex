/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.builder.latex;

import java.io.File;

/**
 * This interface describes a parameter container.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * 
 * @version $Revision$
 */
public class Parameters {

    /**
     * The field <tt>bibtexCommand</tt> contains the <span
     * class="sc">Bib</span></span><span class="t">T</span><span
     * class="e">e</span>X command.
     */
    private String bibtexCommand = "bibtex";

    /**
     * The field <tt>texinputs</tt> contains the search path for files.
     */
    private String[] texinputs = new String[]{""};

    /**
     * The field <tt>bibtexExtensions</tt> contains the <span
     * class="sc">Bib</span></span><span class="t">T</span><span
     * class="e">e</span>X extensions.
     */
    private String[] bibtexExtensions =
            new String[]{".bib", ".bibtex", ".BibTeX", ""};

    /**
     * The field <tt>graphicsExtensions</tt> contains the extensions for
     * graphics files.
     */
    private String[] graphicsExtensions =
            new String[]{".png", ".jpg", ".jpeg", ".gif", ".tiff", ".pdf", ""};

    /**
     * The field <tt>latexCommand</tt> contains the L<span
     * class="la">a</span>T<span class="e">e</span>X command.
     */
    private String latexCommand = "pdflatex";

    /**
     * The field <tt>latexExtensions</tt> contains the L<span
     * class="la">a</span>T<span class="e">e</span>X extensions.
     */
    private String[] latexExtensions =
            new String[]{".tex", ".TeX", ".latex", ".LaTeX", ".ltx", ""};

    /**
     * The field <tt>makeindexCommand</tt> contains the makeindex command.
     */
    private String makeindexCommand = "makeindex";

    /**
     * The field <tt>outputDirectory</tt> contains the output directory.
     */
    private File outputDirectory = new File(".");

    /**
     * The field <tt>targetFormat</tt> contains the target format.
     */
    private String targetFormat = FileFormat.PDF.toString();

    /**
     * The field <tt>workingDirectory</tt> contains the working directory.
     */
    private File workingDirectory = new File(".");

    /**
     * The field <tt>limit</tt> contains the artificial limit to break out of
     * infinite make loops.
     */
    private int limit = 3;

    /**
     * Getter for the <span class="sc">Bib</span></span><span
     * class="t">T</span><span class="e">e</span>X command.
     * 
     * @return the <span class="sc">Bib</span></span><span
     *         class="t">T</span><span class="e">e</span>X command
     */
    public String getBibtexCommand() {

        return bibtexCommand;
    }

    /**
     * Getter for the <span class="sc">Bib</span></span><span
     * class="t">T</span><span class="e">e</span>X extensions.
     * 
     * @return the <span class="sc">Bib</span></span><span
     *         class="t">T</span><span class="e">e</span>X extensions
     */
    public String[] getBibtexExtensions() {

        return bibtexExtensions.clone();
    }

    /**
     * Getter for the graphics extensions.
     * 
     * @return the graphics extensions
     */
    public String[] getGraphicsExtensions() {

        return graphicsExtensions.clone();
    }

    /**
     * Getter for the L<span class="la">a</span>T<span class="e">e</span>X
     * command.
     * 
     * @return the L<span class="la">a</span>T<span class="e">e</span>X command
     */
    public String getLatexCommand() {

        return latexCommand;
    }

    /**
     * Getter for the L<span class="la">a</span>T<span class="e">e</span>X
     * extensions.
     * 
     * @return the L<span class="la">a</span>T<span class="e">e</span>X
     *         extensions
     */
    public String[] getLatexExtensions() {

        return latexExtensions.clone();
    }

    /**
     * Getter for the limit.
     * 
     * @return the limit
     */
    public int getLimit() {

        return limit;
    }

    /**
     * Getter for the makeindexCommand.
     * 
     * @return the makeindexCommand
     */
    public String getMakeindexCommand() {

        return makeindexCommand;
    }

    /**
     * Getter for the outputDirectory.
     * 
     * @return the outputDirectory
     */
    public File getOutputDirectory() {

        return outputDirectory;
    }

    /**
     * Getter for the targetFormat.
     * 
     * @return the targetFormat
     */
    public String getTargetFormat() {

        return targetFormat;
    }

    /**
     * Getter for the texinputs.
     * 
     * @return the texinputs
     */
    public String[] getTexinputs() {

        return texinputs.clone();
    }

    /**
     * Getter for the working directory.
     * 
     * @return the working directory
     */
    public File getWorkingDirectory() {

        return workingDirectory;
    }

    /**
     * Setter for the <span class="sc">Bib</span></span><span
     * class="t">T</span><span class="e">e</span>X command. If the argument is
     * <code>null</code> then it is silently ignored. Thus a <code>null</code>
     * value has no effect.
     * 
     * @param bibtexCommand the <span class="sc">Bib</span></span><span
     *        class="t">T</span><span class="e">e</span>X command to set
     */
    public void setBibtexCommand(String bibtexCommand) {

        if (bibtexCommand != null) {
            this.bibtexCommand = bibtexCommand;
        }
    }

    /**
     * Setter for the <span class="sc">Bib</span></span><span
     * class="t">T</span><span class="e">e</span>X extensions. If the argument
     * is <code>null</code> then it is silently ignored. Thus a
     * <code>null</code> value has no effect.
     * 
     * @param bibtexExtensions the <span class="sc">Bib</span></span><span
     *        class="t">T</span><span class="e">e</span>X extensions to set
     */
    public void setBibtexExtensions(String[] bibtexExtensions) {

        if (bibtexExtensions != null) {
            this.bibtexExtensions = bibtexExtensions.clone();
        }
    }

    /**
     * Setter for the graphics extensions. If the argument is <code>null</code>
     * then it is silently ignored. Thus a <code>null</code> value has no
     * effect.
     * 
     * @param graphicsExtensions the graphics extensions to set
     */
    public void setGraphicsExtensions(String[] graphicsExtensions) {

        if (graphicsExtensions != null) {
            this.graphicsExtensions = graphicsExtensions.clone();
        }
    }

    /**
     * Setter for the L<span class="la">a</span>T<span class="e">e</span>X
     * command. If the argument is <code>null</code> then it is silently
     * ignored. Thus a <code>null</code> value has no effect.
     * 
     * @param latexCommand the L<span class="la">a</span>T<span
     *        class="e">e</span>X command to set
     */
    public void setLatexCommand(String latexCommand) {

        if (latexCommand != null) {
            this.latexCommand = latexCommand;
        }
    }

    /**
     * Setter for the L<span class="la">a</span>T<span class="e">e</span>X
     * extensions. If the argument is <code>null</code> then it is silently
     * ignored. Thus a <code>null</code> value has no effect.
     * 
     * @param latexExtensions the L<span class="la">a</span>T<span
     *        class="e">e</span>X extensions to set
     */
    public void setLatexExtensions(String[] latexExtensions) {

        if (latexExtensions != null) {
            this.latexExtensions = latexExtensions.clone();
        }
    }

    /**
     * Setter for the limit.
     * 
     * @param limit the limit to set
     */
    public void setLimit(int limit) {

        this.limit = limit;
    }

    /**
     * Setter for the makeindex command. If the argument is <code>null</code>
     * then it is silently ignored. Thus a <code>null</code> value has no
     * effect.
     * 
     * @param makeindexCommand the makeindex command to set
     */
    public void setMakeindexCommand(String makeindexCommand) {

        if (makeindexCommand != null) {
            this.makeindexCommand = makeindexCommand;
        }
    }

    /**
     * Setter for the output directory. If the argument is <code>null</code>
     * then it is silently ignored. Thus a <code>null</code> value has no
     * effect.
     * 
     * @param outputDirectory the output directory to set
     */
    public void setOutputDirectory(File outputDirectory) {

        if (outputDirectory != null) {
            this.outputDirectory = outputDirectory;
        }
    }

    /**
     * Setter for the target format. If the argument is <code>null</code> then
     * it is silently ignored. Thus a <code>null</code> value has no effect.
     * 
     * @param targetFormat the target format to set
     */
    public void setTargetFormat(String targetFormat) {

        this.targetFormat = targetFormat;
    }

    /**
     * Setter for the texinputs. If the argument is <code>null</code> then it is
     * silently ignored. Thus a <code>null</code> value has no effect.
     * 
     * @param texinputs the texinputs to set
     */
    public void setTexinputs(String[] texinputs) {

        if (texinputs != null) {
            this.texinputs = texinputs.clone();
        }
    }

    /**
     * Setter for the working directory. If the argument is <code>null</code>
     * then it is silently ignored. Thus a <code>null</code> value has no
     * effect.
     * 
     * @param workingDirectory the working directory to set
     */
    public void setWorkingDirectory(File workingDirectory) {

        if (workingDirectory != null) {
            this.workingDirectory = workingDirectory;
        }
    }

}
