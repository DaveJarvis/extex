/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.vf;

import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.glue.FixedGlue;
import org.extex.font.CoreFontFactory;
import org.extex.font.ExtexFont;
import org.extex.font.FontKey;
import org.extex.font.exception.FontException;
import org.extex.font.format.dvi.DviInterpreter;
import org.extex.font.format.dvi.DviStack;
import org.extex.font.format.dvi.DviValues;
import org.extex.font.format.dvi.command.*;
import org.extex.font.format.tfm.LoadableTfmFont;
import org.extex.font.format.tfm.TfmFixWord;
import org.extex.font.format.vf.command.VfCommandFontDef;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.TypesettingContextFactory;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.tc.font.impl.FontImpl;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.RuleNode;
import org.extex.typesetter.type.node.SpecialNode;
import org.extex.typesetter.type.node.VirtualCharNode;
import org.extex.typesetter.type.node.factory.NodeFactory;
import org.extex.util.file.random.RandomAccessR;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Convert Vf-Dvi to nodes.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class VfDvi2Node implements DviInterpreter, DviExecuteCommand {

  /**
   * The map for the extex fonts.
   */
  private final Map<Integer, ExtexFont> extexfonts =
      new HashMap<Integer, ExtexFont>();

  /**
   * The node factory.
   */
  private final NodeFactory factory;

  /**
   * The font factory.
   */
  private final CoreFontFactory fontfactory;

  /**
   * The field {@code localizer} contains the localizer. It is initiated with
   * a localizer for the name of this class.
   */
  private final Localizer localizer = LocalizerFactory
      .getLocalizer( VfDvi2Node.class );

  /**
   * The dvi stack.
   */
  private final DviStack stack = new DviStack();

  /**
   * The typesetting context.
   */
  private TypesettingContext tc;

  /**
   * The typesetting context factory.
   */
  private final TypesettingContextFactory tcFactory;

  /**
   * The dvi values.
   */
  private final DviValues val = new DviValues();

  /**
   * The virtual char node.
   */
  private final VirtualCharNode vCharNode;

  /**
   * The virtual font.
   */
  private final VfFont vfFont;

  /**
   * The current x point.
   */
  private final Dimen x = new Dimen();

  /**
   * Creates a new object.
   *
   * @param uc          The Unicode char.
   * @param tc          The typesetting context.
   * @param factory     The node factory.
   * @param tcFactory   The typesetting context factory.
   * @param fontfactory The font factory.
   * @param vfFont      The virtual font.
   * @param charpos     The char pos in the font.
   */
  public VfDvi2Node( UnicodeChar uc, TypesettingContext tc,
                     NodeFactory factory, TypesettingContextFactory tcFactory,
                     CoreFontFactory fontfactory, int charpos, VfFont vfFont ) {

    // this.uc = uc;
    this.tc = tc;
    this.factory = factory;
    this.tcFactory = tcFactory;
    this.fontfactory = fontfactory;
    // this.charpos = charpos;
    this.vfFont = vfFont;
    vCharNode = factory.getVirtualCharNode( tc, uc );

    val.clear();
    stack.clear();

  }

  /**
   * Add the node to the virtual char node.
   * <p>
   * If (x,y) differed to (h,l), then a horizontal box is used.
   *
   * @param node The node.
   */
  private void addNode( Node node ) {

    if( isEquals() ) {
      vCharNode.add( node );
      x.add( node.getWidth() );
    }
    else {
      HorizontalListNode hbox = new HorizontalListNode();
      hbox.add( node );
      hbox.setMove( getXDiff() );
      hbox.setShift( getYDiff() );
      x.add( hbox.getWidth() );
      vCharNode.add( hbox );
    }
  }

  /**
   * Check, if the font is changed.
   * <p>
   * If it changed, it create a new typesetting context with the new font.
   *
   * @return Returns the actual extex font.
   */
  @SuppressWarnings("boxing")
  private ExtexFont checkFont() {

    ExtexFont ff = extexfonts.get( val.getF() );
    FontKey ffkey = ff.getActualFontKey();
    Font tcf = tc.getFont();
    FontKey akey = tcf.getActualFontKey();

    if( !ffkey.eq( akey ) ) {
      tc = tcFactory.newInstance( tc, new FontImpl( ff ) );
    }
    return ff;
  }

  @Override
  public void execute( DviBOP command ) throws FontException {

    // ignore

  }

  @Override
  public void execute( DviChar command ) throws FontException {

    ExtexFont f = checkFont();
    UnicodeChar nuc = UnicodeChar.get( command.getCh() );

    if( f instanceof LoadableTfmFont ) {
      // a tfm font maps the position to unicode
      LoadableTfmFont tfmfont = (LoadableTfmFont) f;
      UnicodeChar tmpuc = tfmfont.getUnicodeChar( command.getCh() );
      if( tmpuc != null ) {
        nuc = tmpuc;
      }
    }
    FixedGlue width = f.getWidth( nuc );
    Node node = factory.getNode( tc, nuc );

    addNode( node );

    if( !command.isPut() ) {
      val.addH( (int) TfmFixWord.toValue( vfFont.getDesignSize(),
                                          width.getLength() ) );
    }

  }

  @Override
  public void execute( DviDown command ) throws FontException {

    val.addV( command.getValue() );

  }

  @Override
  public void execute( DviEOP command ) throws FontException {

    // ignore

  }

  @Override
  public void execute( DviFntDef command ) throws FontException {

    // ignore

  }

  @Override
  public void execute( DviFntNum command ) throws FontException {

    val.setF( command.getFont() );
  }

  @Override
  public void execute( DviNOP command ) throws FontException {

    // ignore

  }

  @Override
  public void execute( DviPOP command ) throws FontException {

    DviValues newval = stack.pop();
    val.setValues( newval );

  }

  @Override
  public void execute( DviPost command ) throws FontException {

    // ignore

  }

  @Override
  public void execute( DviPostPost command ) throws FontException {

    // ignore

  }

  @Override
  public void execute( DviPre command ) throws FontException {

    // ignore

  }

  @Override
  public void execute( DviPush command ) throws FontException {

    stack.push( val );

  }

  @Override
  public void execute( DviRight command ) throws FontException {

    val.addH( command.getValue() );

  }

  @Override
  public void execute( DviRule command ) throws FontException {

    RuleNode node = new RuleNode(
        // width
        TfmFixWord.toDimen( vfFont.getDesignSize(), command.getWidth() ),
        // height
        TfmFixWord.toDimen( vfFont.getDesignSize(), command.getHeight() ),
        // depth
        Dimen.ZERO_PT, tc, true );

    addNode( node );

    if( !command.isPut() ) {
      val.addH( command.getWidth() );
    }
  }

  @Override
  public void execute( DviW command ) throws FontException {

    if( !command.isW0() ) {
      val.setW( command.getValue() );
    }
    val.addH( val.getW() );
  }

  @Override
  public void execute( DviX command ) throws FontException {

    if( !command.isX0() ) {
      val.setX( command.getValue() );
    }
    val.addH( val.getX() );

  }

  @Override
  public void execute( DviXXX command ) throws FontException {

    SpecialNode node = new SpecialNode( command.getXXXString() );
    addNode( node );
  }

  @Override
  public void execute( DviY command ) throws FontException {

    if( !command.isY0() ) {
      val.setY( command.getValue() );
    }
    val.addV( val.getY() );

  }

  @Override
  public void execute( DviZ command ) throws FontException {

    if( !command.isZ0() ) {
      val.setZ( command.getValue() );
    }
    val.addV( val.getZ() );

  }

  /**
   * Getter for the virtual char node.
   *
   * @return the virtual char node.
   */
  public VirtualCharNode getVcharNode() {

    return vCharNode;
  }

  /**
   * Returns the x diff to h.
   *
   * @return Returns the x diff to h.
   */
  private Dimen getXDiff() {

    Dimen hd = TfmFixWord.toDimen( vfFont.getDesignSize(), val.getH() );
    hd.subtract( x );
    return hd;
  }

  /**
   * Returns the y diff to v.
   * <p>
   * y is always zero (baseline)!
   *
   * @return Returns the y diff to v.
   */
  private Dimen getYDiff() {

    Dimen vd = TfmFixWord.toDimen( vfFont.getDesignSize(), val.getV() );
    return vd;
  }

  @Override
  public void interpret( RandomAccessR rar ) throws IOException, FontException {

    if( vCharNode == null ) {
      throw new FontException( localizer.format( "DVI.noVCharNode" ) );
    }

    loadFonts();

    while( !rar.isEOF() ) {
      DviCommand command = DviCommand.getNextCommand( rar );

      if( command instanceof DviChar ) {
        DviChar cc = (DviChar) command;
        execute( cc );
        continue;
      }
      else if( command instanceof DviRight ) {
        DviRight cc = (DviRight) command;
        execute( cc );
        continue;
      }
      else if( command instanceof DviDown ) {
        DviDown cc = (DviDown) command;
        execute( cc );
        continue;
      }
      else if( command instanceof DviW ) {
        DviW cc = (DviW) command;
        execute( cc );
        continue;
      }
      else if( command instanceof DviX ) {
        DviX cc = (DviX) command;
        execute( cc );
        continue;
      }
      else if( command instanceof DviY ) {
        DviY cc = (DviY) command;
        execute( cc );
        continue;
      }
      else if( command instanceof DviZ ) {
        DviZ cc = (DviZ) command;
        execute( cc );
        continue;
      }
      else if( command instanceof DviBOP ) {
        DviBOP cc = (DviBOP) command;
        execute( cc );
        continue;
      }
      else if( command instanceof DviEOP ) {
        DviEOP cc = (DviEOP) command;
        execute( cc );
        continue;
      }
      else if( command instanceof DviPOP ) {
        DviPOP cc = (DviPOP) command;
        execute( cc );
        continue;
      }
      else if( command instanceof DviPush ) {
        DviPush cc = (DviPush) command;
        execute( cc );
        continue;
      }
      else if( command instanceof DviRule ) {
        DviRule cc = (DviRule) command;
        execute( cc );
        continue;
      }
      else if( command instanceof DviXXX ) {
        DviXXX cc = (DviXXX) command;
        execute( cc );
        continue;
      }
      else if( command instanceof DviFntDef ) {
        DviFntDef cc = (DviFntDef) command;
        execute( cc );
        continue;
      }
      else if( command instanceof DviFntNum ) {
        DviFntNum cc = (DviFntNum) command;
        execute( cc );
        continue;
      }
      else if( command instanceof DviPost ) {
        DviPost cc = (DviPost) command;
        execute( cc );
        continue;
      }
      else if( command instanceof DviPostPost ) {
        DviPostPost cc = (DviPostPost) command;
        execute( cc );
        continue;
      }
      else if( command instanceof DviPre ) {
        DviPre cc = (DviPre) command;
        execute( cc );
        continue;
      }
      throw new FontException( localizer.format( "DVI.forgetOpcode",
                                                 String.valueOf( command.getOpcode() ) ) );
    }

  }

  /**
   * Check, if (x,y) is equals to (h,v) .
   * <p>
   * y is always zero (baseline)!
   *
   * @return Returns {@code true}, if (x,y) is equals to (h,v).
   */
  private boolean isEquals() {

    Dimen hd = TfmFixWord.toDimen( vfFont.getDesignSize(), val.getH() );
    Dimen vd = TfmFixWord.toDimen( vfFont.getDesignSize(), val.getV() );

    return x.eq( hd ) && vd.isZero();
  }

  /**
   * Load the {@link ExtexFont}s.
   *
   * @throws FontException if a font error occurred.
   */
  private void loadFonts() throws FontException {

    Map<Integer, VfCommandFontDef> fonts = vfFont.getFonts();

    for( Integer number : fonts.keySet() ) {
      VfCommandFontDef fcmd = fonts.get( number );
      FontKey key;
      if( fcmd.getScalefactorAsCount().getValue() != Dimen.ONE ) {
        HashMap<String, FixedCount> m =
            new HashMap<String, FixedCount>();
        m.put( FontKey.SCALE, new Count( fcmd.getScalefactorAsCount()
                                             .getValue() * 1000 / Dimen.ONE ) );
        key =
            fontfactory.getFontKey( fcmd.getFontname(),
                                    fcmd.getDesignsizeAsDimen(), m );
      }
      else {
        key =
            fontfactory.getFontKey( fcmd.getFontname(),
                                    fcmd.getDesignsizeAsDimen() );
      }

      ExtexFont f = fontfactory.getInstance( key );
      extexfonts.put( number, f );
    }

  }
}
