/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.tex.pageBuilder.impl;

import org.extex.backend.BackendDriver;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.interpreter.context.Context;
import org.extex.typesetter.PageContext;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.output.OutputRoutine;
import org.extex.typesetter.pageBuilder.PageBuilder;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.VerticalListNode;
import org.extex.typesetter.type.page.Page;
import org.extex.typesetter.type.page.PageFactory;

/**
 * This is a first reference implementation of a page builder.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class PageBuilderImpl implements PageBuilder {

  /**
   * The field {@code context} contains the interpreter context.
   */
  private Context context = null;

  /**
   * The field {@code documentWriter} contains the document writer to
   * receive the pages.
   */
  private BackendDriver backendDriver = null;

  /**
   * The field {@code options} contains the options to control the
   * behavior.
   */
  private TypesetterOptions options = null;

  /**
   * The field {@code output} contains the output routine.
   */
  private OutputRoutine outputRoutine = null;

  /**
   * The field {@code pageFactory} contains the page factory to use.
   */
  private PageFactory pageFactory = null;


  public PageBuilderImpl() {

  }

  public void close() throws TypesetterException {

    try {
      backendDriver.close();
    } catch( GeneralException e ) {
      throw new TypesetterException( e );
    }

  }

  /**
   * org.extex.typesetter.type.NodeList, org.extex.typesetter.Typesetter)
   */
  public void flush( NodeList nodes, Typesetter typesetter )
      throws TypesetterException {

    if( nodes.size() <= 0 ) {
      return;
    }

    try {
      Page page = pageFactory.newInstance( nodes, context, typesetter );

      if( page == null ) {
        // fall through
      }
      else if( this.outputRoutine != null ) {
        this.outputRoutine.output( page, backendDriver );
      }
      else {
        backendDriver.shipout( page );
      }
    } catch( GeneralException e ) {
      throw new TypesetterException( e );
    }
  }

  /**
   * org.extex.typesetter.type.node.VerticalListNode,
   * org.extex.typesetter.Typesetter)
   */
  public void inspectAndBuild( VerticalListNode nodes, Typesetter typesetter )
      throws TypesetterException {

    FixedDimen d = nodes.getVerticalSize();
    if( d.ge( options.getDimenOption( "vsize" ) ) ) {

      flush( nodes, typesetter );
      nodes.clear(); // TODO gene: split off the appropriate amount and
      // leave the rest
    }
  }

  /**
   * org.extex.typesetter.PageContext)
   */
  public void setContext( PageContext context ) {

    if( !(context instanceof Context) ) {
      //TODO gene: setContext unimplemented
      throw new RuntimeException( "unimplemented" );
    }
    this.context = (Context) context;
  }

  /**
   * org.extex.backend.BackendDriver)
   */
  public void setBackend( BackendDriver backend ) {

    this.backendDriver = backend;
  }

  /**
   * Setter for options.
   *
   * @param options the options to set
   */
  public void setOptions( TypesetterOptions options ) {

    this.options = options;
  }

  /**
   * org.extex.typesetter.output.OutputRoutine)
   */
  public void setOutputRoutine( OutputRoutine output ) {

    this.outputRoutine = output;
  }

  /**
   * org.extex.typesetter.type.page.PageFactory)
   */
  public void setPageFactory( PageFactory factory ) {

    pageFactory = factory;
  }

  /**
   * org.extex.typesetter.type.NodeList, org.extex.typesetter.Typesetter)
   */
  public void shipout( NodeList nodes, Typesetter typesetter )
      throws TypesetterException {

    if( nodes.size() <= 0 ) {
      return;
    }
    try {
      Page page = pageFactory.newInstance( nodes, context, typesetter );
      if( page != null ) {
        backendDriver.shipout( page );
      }

    } catch( GeneralException e ) {
      throw new TypesetterException( e );
    }
  }

}
