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

package org.extex.typesetter.paragraphBuilder.impl;

import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.core.glue.WideGlue;
import org.extex.framework.logger.LogEnabled;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.logging.LogFormatter;
import org.extex.typesetter.Badness;
import org.extex.typesetter.Discardable;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.paragraphBuilder.FixedParagraphShape;
import org.extex.typesetter.paragraphBuilder.HangingParagraphShape;
import org.extex.typesetter.paragraphBuilder.ParagraphBuilder;
import org.extex.typesetter.paragraphBuilder.ParagraphShape;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.*;
import org.extex.typesetter.type.node.factory.NodeFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides a paragraph builder.
 *
 * <p>The Parameter {@code \emergencystretch}</p>
 * <p>
 * TODO missing documentation
 *
 * <p>The Parameter {@code \exhyphenpenalty}</p>
 * <p>
 * TODO missing documentation
 *
 * <p>The Parameter {@code \hangafter}</p>
 * <p>
 * TODO missing documentation
 *
 * <p>The Parameter {@code \hangindent}</p>
 * <p>
 * TODO missing documentation
 *
 * <p>The Parameter {@code \hsize}</p>
 * The parameter {@code \hsize} contains the horizontal size of the paragraph
 * to be build.
 * See also \parshape, \hangindent, and \hangafter.
 *
 * <p>The Parameter {@code \hyphenpenalty}</p>
 * <p>
 * The parameter {@code \hyphenpenalty} contains the penalty inserted whenever
 * a hyphenation is applied. Thus paragraphs with less hyphenations are
 * preferred over those with more hyphenations.
 *
 * <p>The Parameter {@code \leftskip}</p>
 *
 * <p>
 * The parameter {@code \leftskip} contains the glue which is inserted at the
 * left side of each line in the paragraph. The default is 0&nbsp;pt.
 * </p>
 * <p>
 * This parameter can be used to flash the line to the left side or center the
 * line. Those effects can be achieved in combination with the parameter
 * {@code \rightskip}.
 * </p>
 *
 * <p>The Parameter {@code \parfillskip}</p>
 * The parameter {@code \parfillskip} contains the glue which is added at the
 * end of each paragraph.
 *
 * <p>The Parameter {@code \parskip}</p>
 * The parameter {@code \parskip} contains the glue which is added to the
 * vertical list before the beginnng of each paragraph.
 *
 * <p>The Parameter {@code \pretolerance}</p>
 * <p>
 * TODO missing documentation
 *
 * <p>The Parameter {@code \rightskip}</p>
 * <p>
 * The parameter {@code \rightskip} contains the glue which is inserted at the
 * right side of each line in the paragraph. The defult is 0&nbsp;pt.
 * </p>
 * <p>
 * This parameter can be used to flash the line to the right side or center the
 * line. Those effects can be achieved in combination with the parameter
 * {@code \leftskip}.
 * </p>
 *
 * <p>The Parameter {@code \tolerance}</p>
 * <p>
 * TODO missing documentation
 *
 * <p>The Parameter {@code \tracingparagraphs}</p>
 * <p>
 * TODO missing documentation
 *
 *
 * <p>Extension</p>
 * <p>
 * Treat segments of a paragraph separated by forced breaks separately.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ParagraphBuilderImpl implements ParagraphBuilder, LogEnabled {

  /**
   * The constant {@code COMPLETE} contains the indicator that the
   * implementation is complete.
   */
  private static final boolean COMPLETE = true;

  /**
   * The constant {@code DEVELOP} contains a boolean indicating whether the
   * develop traces should be written.
   */
  private static final boolean DEVELOP = true;

  /**
   * The field {@code fixedParshape} contains the data object used to
   * transport the fixed paragraph shape to the appropriate places. The values
   * stored in it will be overwritten whenever this object will be used for
   * the current paragraph.
   */
  private final FixedParagraphShape fixedParshape =
      new FixedParagraphShape( Dimen.ZERO_PT );

  /**
   * The field {@code hangingParshape} contains the data object used to
   * transport the hanging paragraph shape to the appropriate places. The
   * values stored in it will be overwritten whenever this object will be
   * used for the current paragraph.
   */
  private final HangingParagraphShape hangingParshape =
      new HangingParagraphShape( 0, Dimen.ZERO_PT, Dimen.ZERO_PT );

  /**
   * The field {@code logger} contains the logger to be used.
   * This field is initialized from the framework by invoking the appropriate
   * setter.
   */
  private Logger logger = null;

  /**
   * The field {@code nodeFactory} contains the node factory.
   */
  private NodeFactory nodeFactory;

  /**
   * The field {@code options} contains the reference to the options object.
   */
  private TypesetterOptions options = null;

  /**
   * The field {@code parshape} contains the paragraph shape specification.
   * This field is initialized at the beginning of the line breaking if it is
   * {@code null}. At the end of the line breaking it is reset to
   * {@code null}.
   */
  private ParagraphShape parshape = null;

  /**
   * The field {@code tracer} contains the logger used for tracing.
   */
  private Logger tracer = null;


  public ParagraphBuilderImpl() {

    if( DEVELOP ) {
      tracer = Logger.getLogger( "" );
      tracer.setUseParentHandlers( false );
      Handler handler = new ConsoleHandler();
      handler.setLevel( Level.ALL );
      handler.setFormatter( new LogFormatter() );
      tracer.addHandler( handler );
      tracer.setLevel( Level.ALL );
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.typesetter.paragraphBuilder.ParagraphBuilder#build(
   *org.extex.typesetter.type.node.HorizontalListNode)
   */
  public NodeList build( HorizontalListNode nodes )
      throws HyphenationException {

    //split into smaller methods?

    if( !COMPLETE ) {
      return nodes;
    }

    if( options.getCountOption( "tracingparagraphs" ).getValue() > 0 ) {
      tracer = logger;
    }

    int hyphenpenalty =
        (int) options.getCountOption( "hyphenpenalty" ).getValue();
    int exhyphenpenalty =
        (int) options.getCountOption( "exhyphenpenalty" ).getValue();
    FixedGlue leftskip = options.getGlueOption( "leftskip" );
    FixedGlue rightskip = options.getGlueOption( "rightskip" );

    prepareParshape();

    // remove final node if it is glue; [TTB p99--100]
    if( !nodes.isEmpty() && nodes.get( nodes.size() - 1 ) instanceof GlueNode ) {
      nodes.remove( nodes.size() - 1 );
    }

    // [TTB p100]
    nodes.add( new PenaltyNode( Badness.INF_PENALTY ) );
    nodes.add( new GlueNode( options.getGlueOption( "parfillskip" ), true ) );
    nodes.add( new PenaltyNode( Badness.EJECT_PENALTY ) );

    NodeList nl;
    nl = pass1( nodes, hyphenpenalty, exhyphenpenalty, leftskip, rightskip );
    if( nl != null ) {
      return nl;
    }

    hyphenate( nodes );

    if( tracer != null ) {
      tracer.log( Level.FINE, "@secondpass\n" );
      for( int i = 0; i < nodes.size(); i++ ) {
        tracer.log( Level.FINE, i + "\t"
            + nodes.get( i ).toString() + "\n" );
      }
    }
    int tolerance = (int) options.getCountOption( "tolerance" ).getValue();
    BreakPoint[] breakPoints =
        makeBreakPoints( nodes, hyphenpenalty, exhyphenpenalty );

    if( tracer != null ) {
      for( int i = 0; i < breakPoints.length; i++ ) {
        tracer.log( Level.FINE, breakPoints[ i ].toString() + "\n" );
      }
    }

    Breaks breaks =
        findOptimalBreakPoints( breakPoints, 0, tolerance, 0, 0,
                                leftskip, rightskip, Dimen.ZERO_PT );
    if( breaks != null ) {
      options.setParshape( null );
      return splitNodeList( nodes, breaks, leftskip, rightskip );
    }

    FixedDimen emergencystretch =
        options.getDimenOption( "emergencystretch" );
    if( emergencystretch.getValue() > 0 ) {
      if( tracer != null ) {
        tracer.log( Level.FINE, "@thirdpass\n" );
      }
      breaks =
          findOptimalBreakPoints( breakPoints, 0, tolerance, 0, 0,
                                  leftskip, rightskip, emergencystretch );
      if( breaks != null ) {
        options.setParshape( null );
        return splitNodeList( nodes, breaks, leftskip, rightskip );
      }
    }

    options.setParshape( null );
    return nodes;
  }

  /**
   * Collect the active break points.
   *
   * @param breakPoint the list of possible break points
   * @param depth      the number of active break points
   * @param penalty    the accumulated penalty
   * @return a breaks container
   */
  private Breaks collect( BreakPoint[] breakPoint, int depth,
                          int penalty ) {

    int[] a = new int[ depth + 1 ];
    int xi = 0;
    for( int i = 0; i < breakPoint.length; i++ ) {
      if( breakPoint[ i ].isActive() ) {
        a[ xi++ ] = breakPoint[ i ].getPosition();
      }
    }
    return new Breaks( penalty, a );
  }

  /**
   * Determine the demerits.
   *
   * @param breakPoint the list of possible break points
   * @param pi         the index of the end point of the current line
   * @param leftskip   the skip to be included at the left end of each line
   * @param rightskip  the skip to be included at the right end of each line
   * @param lineWidth  the size of the current line
   * @param threshold  the threshold
   * @return the demerits
   * @see "TTP [851]"
   */
  private int computeDemerits( BreakPoint[] breakPoint, int pi,
                               FixedGlue leftskip, FixedGlue rightskip,
                               FixedGlue lineWidth, int threshold ) {

    Glue width = new Glue( breakPoint[ pi ].getWidth() );
    for( int i = pi - 1; i > 0 && !breakPoint[ i ].isActive(); i-- ) {
      width.add( breakPoint[ i ].getWidth() );
      width.add( breakPoint[ i ].getPointWidth() );
    }

    int badness = 0;
    Fitness fit;
    if( width.getStretch().getOrder() != 0 ) {
      fit = Fitness.DECENT;
    }
    else {
      long line = lineWidth.getLength().getValue();
      long shortfall = line - width.getLength().getValue();

      if( shortfall > 7230584 ) {
        // overflow

      }
      else if( shortfall > 0 ) { //TTP [852]
        if( line < 1663497 ) {
          badness = Badness.INF_PENALTY;
          fit = Fitness.VERY_LOOSE;
        }
        else {
          badness = Badness.badness( shortfall, line );
          fit =
              (badness < 12 ? Fitness.DECENT : badness < 99
                  ? Fitness.LOOSE
                  : Fitness.VERY_LOOSE);
        }
      }
      else { //TTP [853]
        long shrink = lineWidth.getShrink().getValue();
        if( -shortfall > shrink ) {
          badness = Badness.INF_PENALTY + 1;
        }
        else {
          badness = Badness.badness( -shortfall, shrink );
          fit = (badness <= 12 ? Fitness.DECENT : Fitness.TIGHT);
        }
      }
    }

    if( badness > threshold ) {
      return Badness.INF_PENALTY + 1;
    }

    return badness;
  }

  /**
   * Skip over any discardable nodes and return the index of the next
   * non-discardable node.
   *
   * @param start the index to start at
   * @param len   the length of the node list
   * @param nodes the node list to take into account
   * @param wd    the  accumulator for the width of the discarded nodes
   * @return the index of the next non-discardable node
   */
  private int discartNodes( int start, int len,
                            NodeList nodes, WideGlue wd ) {

    int i = start;
    while( ++i < len && nodes.get( i ) instanceof Discardable ) {
      wd.add( nodes.get( i ).getWidth() );
    }
    return i - 1;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.framework.logger.LogEnabled#enableLogging(
   *java.util.logging.Logger)
   */
  public void enableLogging( Logger theLogger ) {

    this.logger = theLogger;
  }

  /**
   * Compute a optimal list of break positions.
   *
   * @param breakPoint       the list of possible break points
   * @param line             the starting line number
   * @param threshold        the threshold for the penalties of a single line
   * @param depth            the current depth of recursion. This is 
   *                         identical to the
   *                         length of the list of break points
   * @param pointIndex       the index of the point
   * @param leftskip         the skip for the left side
   * @param rightskip        the skip for the right side
   * @param emergencystretch the emergency stretch to add
   * @return the container with the breaks or {@code null} if none is
   * found.
   */
  private Breaks findOptimalBreakPoints( BreakPoint[] breakPoint,
                                         int line, int threshold, int depth,
                                         int pointIndex, FixedGlue leftskip,
                                         FixedGlue rightskip,
                                         FixedDimen emergencystretch ) {

    if( tracer != null ) {
      tracer.log( Level.FINE,
                  "........................................................."
                      .substring( 0, depth )
                      + " +++ " + pointIndex + "\n" );
      for( int i = 0; i < pointIndex; i++ ) {
        if( breakPoint[ i ].isActive() ) {
          tracer.log( Level.FINE, " " + breakPoint[ i ].getPosition()
              + " [" + breakPoint[ i ].getPenalty() + "]" );
        }
      }
    }
    Breaks b = null;
    int pen = 0;
    Glue lineWidth = new Glue( parshape.getLength( line ) );
    lineWidth.subtract( parshape.getIndent( line ) );

    for( int i = pointIndex; i < breakPoint.length; i++ ) {
      breakPoint[ i ].setActive();
      pen =
          computeDemerits( breakPoint, i, leftskip, rightskip,
                           lineWidth, threshold );
      if( pen <= Badness.EJECT_PENALTY || pen < threshold ) {
        if( i + 1 < breakPoint.length ) {
          Breaks b2 =
              findOptimalBreakPoints( breakPoint, line + 1,
                                      threshold, depth + 1, i + 1, leftskip,
                                      rightskip, emergencystretch );
          if( b2 != null
              && (b == null || b.getPenalty() > b2.getPenalty()) ) {
            b = b2;
          }
        }
        else {
          b = collect( breakPoint, depth, 9999 ); //TODO gene: 
          // provide accumulated penalty
        }
      }
      breakPoint[ i ].setPassive();
    }

    return b;
  }

  /**
   * Hyphenate all words of an hlist.
   *
   * @param nodes the node list to hyphenate
   * @throws HyphenationException in case of an error
   */
  private void hyphenate( HorizontalListNode nodes )
      throws HyphenationException {

    if( tracer != null ) {
      tracer.log( Level.FINE, "@hyphenating\n" );
    }
    int size = nodes.size();
    Node n;

    for( int i = 0; i < size; i++ ) {
      n = nodes.get( i );
      if( n instanceof CharNode ) {
        TypesettingContext tc = ((CharNode) n).getTypesettingContext();
        UnicodeChar hyphen = tc.getFont().getHyphenChar();
        tc.getLanguage().hyphenate( nodes, options, hyphen, i, false,
                                    nodeFactory );
      }
    }
  }

  /**
   * Find all admissible break points.
   *
   * @param nodes           the horizontal node list containing all nodes 
   *                        for the
   *                        paragraph
   * @param hyphenpenalty   penalty for a discretionary node with non-empty
   *                        pre-text
   * @param exhyphenpenalty penalty for a discretionary node with empty
   *                        pre-text
   * @return a complete list of admissible break points
   */
  private BreakPoint[] makeBreakPoints( HorizontalListNode nodes,
                                        int hyphenpenalty,
                                        int exhyphenpenalty ) {

    int len = nodes.size();
    List<BreakPoint> breakList = new ArrayList<BreakPoint>( 1 + len / 5 ); //
    // size is a heuristic
    WideGlue w = new WideGlue();
    WideGlue wd = new WideGlue();
    boolean math = false;

    int i = 0;
    Node node = nodes.get( i );
    if( node instanceof GlueNode ) {
      node.addWidthTo( w );
      i++;
    }

    for( ; i < len; i++ ) {
      node = nodes.get( i );

      if( node instanceof CharNode ) {
        node.addWidthTo( w );

      }
      else if( node instanceof GlueNode
          && !(nodes.get( i - 1 ) instanceof Discardable) ) {

        node.addWidthTo( wd );
        breakList.add( new BreakPoint( i, w, wd, 0 ) );
        i = discartNodes( i, len, nodes, wd );
        w = new WideGlue();
        wd = new WideGlue();
      }
      else if( node instanceof KernNode && !math
          && nodes.get( i + 1 ) instanceof GlueNode ) {

        node.addWidthTo( wd );
        breakList.add( new BreakPoint( i, w, wd, 0 ) );
        i = discartNodes( i, len, nodes, wd );
        w = new WideGlue();
        wd = new WideGlue();
      }
      else if( node instanceof BeforeMathNode ) {
        math = true;
      }
      else if( node instanceof AfterMathNode ) {
        if( nodes.get( i + 1 ) instanceof GlueNode ) {

          node.addWidthTo( wd );
          breakList.add( new BreakPoint( i, w, wd, 0 ) );
          i = discartNodes( i, len, nodes, wd );
          w = new WideGlue();
          wd = new WideGlue();
        }
        math = false;
      }
      else if( node instanceof PenaltyNode ) {
        int penalty = (int) ((PenaltyNode) node).getPenalty();
        if( penalty < Badness.INF_PENALTY ) {

          node.addWidthTo( wd );
          breakList.add( new BreakPoint( i, w, wd, penalty ) );
          i = discartNodes( i, len, nodes, wd );
          w = new WideGlue();
          wd = new WideGlue();
        }
      }
      else if( node instanceof DiscretionaryNode ) {

        node.addWidthTo( wd );
        breakList.add( new BreakPoint( i, w, wd,
                                       (((DiscretionaryNode) node).getPreBreak() != null
                                           ? hyphenpenalty
                                           : exhyphenpenalty) ) );
        i = discartNodes( i, len, nodes, wd );
        w = new WideGlue();
        wd = new WideGlue();
      }
      else {
        node.addWidthTo( w );
      }
    }

    BreakPoint[] breakPoints = new BreakPoint[ breakList.size() ];
    breakList.toArray( breakPoints );
    return breakPoints;
  }

  /**
   * Try paragraph building with the pass 1 algorithm: no hyphenations are
   * taken into account.
   *
   * @param nodes           the node list to work on
   * @param hyphenpenalty   the penalty for hyphenation
   * @param exhyphenpenalty the extra penalty subsequent hyphenation
   * @param leftskip        the glue for the left side
   * @param rightskip       the glue for the right side
   * @return the resulting vertical node list or {@code null} if this
   * attempt has not been successful
   */
  private NodeList pass1( HorizontalListNode nodes,
                          int hyphenpenalty, int exhyphenpenalty,
                          FixedGlue leftskip, FixedGlue rightskip ) {

    int pretolerance =
        (int) options.getCountOption( "pretolerance" ).getValue();
    if( pretolerance > 0 ) {
      if( tracer != null ) {
        tracer.log( Level.FINE, "@firstpass\n" );
      }
      BreakPoint[] breakPoints =
          makeBreakPoints( nodes, hyphenpenalty, exhyphenpenalty );
      Breaks breaks =
          findOptimalBreakPoints( breakPoints, 0, pretolerance, 0, 0,
                                  leftskip, rightskip, Dimen.ZERO_PT );
      if( breaks != null ) {
        options.setParshape( null );
        return splitNodeList( nodes, breaks, leftskip, rightskip );
      }
    }
    return null;
  }

  /**
   * Initializes the field {@code parshape} if not set already.
   * For this purpose the options are considered.
   */
  private void prepareParshape() {

    parshape = options.getParshape();

    if( parshape == null ) {
      int hangafter =
          (int) options.getCountOption( "hangafter" ).getValue();

      if( hangafter != 0 ) {
        hangingParshape.setHangafter( hangafter );
        hangingParshape.setHangindent( options
                                           .getDimenOption( "hangindent" ) );
        hangingParshape.setHsize( options.getDimenOption( "hsize" ) );
        parshape = hangingParshape;
      }
      else {
        fixedParshape.setHsize( options.getDimenOption( "hsize" ) );
        parshape = fixedParshape;
      }
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.typesetter.paragraphBuilder.ParagraphBuilder#setNodefactory(
   *org.extex.typesetter.type.node.factory.NodeFactory)
   */
  public void setNodefactory( NodeFactory factory ) {

    this.nodeFactory = factory;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.typesetter.paragraphBuilder.ParagraphBuilder#setOptions(
   *org.extex.typesetter.TypesetterOptions)
   */
  public void setOptions( TypesetterOptions options ) {

    this.options = options;
  }

  /**
   * Split the given hlist at the computed break points and construct a vlist
   * containing the lines.
   *
   * @param nodes     the hlist to split
   * @param breaks    the list of break positions
   * @param leftskip  the skip for the left side
   * @param rightskip the skip for the right side
   * @return a vlist with the lines
   */
  private NodeList splitNodeList( NodeList nodes, Breaks breaks,
                                  FixedGlue leftskip, FixedGlue rightskip ) {

    VerticalListNode vlist = new VerticalListNode();
    int[] a = breaks.getPoints();
    int hi = 0;
    for( int i = 0; i < a.length; i++ ) {
      HorizontalListNode hlist = new HorizontalListNode();
      while( hi < a[ i ] ) {
        hlist.add( nodes.get( hi++ ) );
      }

      vlist.add( hlist );

      int max = (i + 1 < a.length ? a[ i + 1 ] : nodes.size());
      // skip discardable items at end of line
      while( hi < max && nodes.get( hi ) instanceof Discardable ) {
        hi++;
      }
    }

    return vlist;
  }

}
