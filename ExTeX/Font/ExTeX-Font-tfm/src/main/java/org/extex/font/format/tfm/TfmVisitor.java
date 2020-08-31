/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.tfm;

import java.io.IOException;

/**
 * Interface for a tfm visitor.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public interface TfmVisitor {

  /**
   * Visit the {@link TfmReader}
   *
   * @param tfmReader The TfmReader.
   * @throws IOException if an io error occurred.
   */
  void visitTfmReader( TfmReader tfmReader ) throws IOException;

  /**
   * Visit the {@link TfmHeaderLengths}.
   *
   * @param lengths The TfmHeaderLengths.
   * @throws IOException if an io error occurred.
   */
  void visitTfmHeaderLengths( TfmHeaderLengths lengths ) throws IOException;

  /**
   * Visit the {@link TfmHeaderArray}.
   *
   * @param header The TfmHeaderArray.
   * @throws IOException if an io error occurred.
   */
  void visitTfmHeaderArray( TfmHeaderArray header ) throws IOException;

  /**
   * Visit the {@link TfmCharInfoArray}.
   *
   * @param charinfo The TfmCharInfoArray.
   * @throws IOException if an io error occurred.
   */
  void visitTfmCharInfoArray( TfmCharInfoArray charinfo ) throws IOException;

  /**
   * Visit the {@link TfmWidthArray}.
   *
   * @param width The TfmWidthArray.
   * @throws IOException if an io error occurred.
   */
  void visitTfmWidthArray( TfmWidthArray width ) throws IOException;

  /**
   * Visit the {@link TfmHeaderArray}.
   *
   * @param height The TfmHeightArray.
   * @throws IOException if an io error occurred.
   */
  void visitTfmHeightArray( TfmHeightArray height ) throws IOException;

  /**
   * Visit the {@link TfmDepthArray}.
   *
   * @param depth The TfmDepthArray.
   * @throws IOException if an io error occurred.
   */
  void visitTfmDepthArray( TfmDepthArray depth ) throws IOException;

  /**
   * Visit the {@link TfmItalicArray}.
   *
   * @param italic The TfmItalicArray.
   * @throws IOException if an io error occurred.
   */
  void visitTfmItalicArray( TfmItalicArray italic ) throws IOException;

  /**
   * Visit the {@link TfmLigKernArray}.
   *
   * @param ligkern The TfmLigKernArray.
   * @throws IOException if an io error occurred.
   */
  void visitTfmLigKernArray( TfmLigKernArray ligkern ) throws IOException;

  /**
   * Visit the {@link TfmKernArray}.
   *
   * @param kern The TfmKernArray.
   * @throws IOException if an io error occurred.
   */
  void visitTfmKernArray( TfmKernArray kern ) throws IOException;

  /**
   * Visit the {@link TfmExtenArray}.
   *
   * @param exten The TfmExtenArray.
   * @throws IOException if an io error occurred.
   */
  void visitTfmExtenArray( TfmExtenArray exten ) throws IOException;

  /**
   * Visit the {@link TfmParamArray}.
   *
   * @param param The TfmParamArray.
   * @throws IOException if an io error occurred.
   */
  void visitTfmParamArray( TfmParamArray param ) throws IOException;

  /**
   * Start.
   *
   * @throws IOException if an io error occurred.
   */
  void start() throws IOException;

  /**
   * Stop.
   *
   * @throws IOException if an io error occurred.
   */
  void end() throws IOException;
}
