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

package org.extex.font;

import org.extex.core.Unicode;
import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.exception.FontException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test for the font factory.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class FontFactoryImplAer12VfTest extends AbstractFontFactoryTester {

  /**
   * The font.
   */
  private static ExtexFont font;

  /**
   * The font key.
   */
  private static FontKey key;

  /**
   * Creates a new object.
   *
   * @throws ConfigurationException from the configuration system.
   * @throws FontException          if a font error occurred.
   */
  public FontFactoryImplAer12VfTest()
      throws ConfigurationException,
      FontException {

    if( key == null ) {
      CoreFontFactory factory = makeFontFactory();

      key = factory.getFontKey( "aer12" );

      font = factory.getInstance( key );
    }
  }

  /**
   * Test for the font key.
   *
   * @throws Exception if an error occurred.
   */
  @Test
  public void test01() throws Exception {

    assertNotNull( font );
    assertNotNull( key );
  }

  // ************************************************************

  /**
   * test aer12 Char 0: Width=384560, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C0() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 0 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 0 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 0 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 0 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 1: Width=384560, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C1() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 1 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 1 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 1 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 1 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 10: Width=213120, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C10() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 10 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 10 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 10 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 10 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 100: Width=427032, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C100() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 100 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 100 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 100 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 100 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 101: Width=342096, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C101() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 101 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 101 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 101 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 101 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 342096 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 102: Width=235136, Height=541452, Depth=0, IC=54255
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C102() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 102 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 102 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 102 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 102 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 235136 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 54255 ).eq( i ) );
  }

  /**
   * test aer12 Char 103: Width=384560, Height=338160, Depth=152559, IC=10215
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C103() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 103 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 103 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 103 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 103 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 10215 ).eq( i ) );
  }

  /**
   * test aer12 Char 104: Width=427032, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C104() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 104 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 104 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 104 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 104 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 105: Width=213120, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C105() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 105 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 105 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 105 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 105 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 106: Width=235136, Height=513927, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C106() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 106 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 106 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 106 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 106 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 235136 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 107: Width=405792, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C107() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 107 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 107 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 107 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 107 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 405792 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 108: Width=213120, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C108() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 108 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 108 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 108 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 108 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 109: Width=640935, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C109() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 109 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 109 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 109 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 109 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 640935 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 11: Width=342096, Height=0, Depth=133688, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C11() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 11 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 11 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 11 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 11 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 342096 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 0 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 133688 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 110: Width=427032, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C110() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 110 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 110 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 110 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 110 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 111: Width=384560, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C111() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 111 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 111 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 111 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 111 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 112: Width=427032, Height=338160, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C112() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 112 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 112 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 112 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 112 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 113: Width=405792, Height=338160, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C113() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 113 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 113 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 113 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 113 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 405792 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 114: Width=298840, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C114() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 114 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 114 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 114 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 114 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 298840 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 115: Width=303560, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C115() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 115 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 115 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 115 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 115 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 303560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 116: Width=298840, Height=488760, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C116() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 116 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 116 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 116 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 116 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 298840 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 488760 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 117: Width=427032, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C117() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 117 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 117 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 117 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 117 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 118: Width=405792, Height=338160, Depth=0, IC=10215
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C118() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 118 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 118 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 118 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 118 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 405792 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 10215 ).eq( i ) );
  }

  /**
   * test aer12 Char 119: Width=556000, Height=338160, Depth=0, IC=10215
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C119() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 119 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 119 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 119 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 119 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 556000 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 10215 ).eq( i ) );
  }

  /**
   * test aer12 Char 12: Width=169863, Height=0, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C12() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 12 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 12 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 12 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 12 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 169863 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 0 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 120: Width=405792, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C120() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 120 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 120 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 120 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 120 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 405792 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 121: Width=405792, Height=338160, Depth=152559, IC=10215
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C121() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 121 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 121 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 121 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 121 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 405792 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 10215 ).eq( i ) );
  }

  /**
   * test aer12 Char 122: Width=342096, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C122() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 122 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 122 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 122 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 122 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 342096 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 123: Width=393216, Height=582351, Depth=196608, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C123() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 123 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 123 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 123 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 123 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 393216 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 582351 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 196608 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 124: Width=217839, Height=582351, Depth=196608, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C124() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 124 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 124 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 124 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 124 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 217839 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 582351 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 196608 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 125: Width=393216, Height=582351, Depth=196608, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C125() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 125 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 125 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 125 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 125 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 393216 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 582351 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 196608 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 126: Width=384560, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C126() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 126 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 126 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 126 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 126 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 127: Width=256376, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C127() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 127 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 127 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 127 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 127 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 256376 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 128: Width=577239, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C128() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 128 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 128 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 128 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 128 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 129: Width=577239, Height=541452, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C129() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 129 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 129 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 129 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 129 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 13: Width=213120, Height=76280, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C13() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 13 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 13 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 13 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 13 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 76280 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 130: Width=556000, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C130() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 130 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 130 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 130 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 130 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 556000 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 131: Width=556000, Height=690876, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C131() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 131 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 131 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 131 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 131 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 556000 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 690876 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 132: Width=587463, Height=690876, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C132() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 132 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 132 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 132 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 132 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 587463 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 690876 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 133: Width=523759, Height=690876, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C133() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 133 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 133 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 133 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 133 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 523759 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 690876 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 134: Width=523759, Height=541452, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C134() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 134 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 134 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 134 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 134 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 523759 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 135: Width=603975, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C135() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 135 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 135 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 135 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 135 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 603975 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 136: Width=480504, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C136() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 136 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 136 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 136 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 136 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 480504 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 137: Width=536343, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C137() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 137 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 137 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 137 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 137 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 536343 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 138: Width=448264, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C138() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 138 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 138 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 138 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 138 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 448264 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 139: Width=577239, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C139() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 139 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 139 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 139 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 139 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 14: Width=393216, Height=393216, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C14() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 14 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 14 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 14 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 14 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 393216 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 393216 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 140: Width=577239, Height=690876, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C140() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 140 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 140 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 140 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 140 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 690876 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 141: Width=393216, Height=393216, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C141() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 141 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 141 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 141 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 141 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 393216 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 393216 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 142: Width=598472, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C142() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 142 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 142 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 142 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 142 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 598472 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 143: Width=566224, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C143() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 143 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 143 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 143 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 143 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 566224 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 144: Width=566224, Height=690876, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C144() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 144 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 144 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 144 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 144 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 566224 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 690876 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 145: Width=427032, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C145() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 145 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 145 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 145 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 145 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 146: Width=427032, Height=690876, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C146() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 146 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 146 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 146 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 146 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 690876 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 147: Width=427032, Height=541452, Depth=133688, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C147() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 147 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 147 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 147 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 147 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 133688 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 148: Width=556000, Height=690876, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C148() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 148 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 148 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 148 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 148 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 556000 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 690876 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 149: Width=556000, Height=541452, Depth=133688, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C149() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 149 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 149 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 149 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 149 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 556000 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 133688 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 15: Width=393216, Height=393216, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C15() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 15 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 15 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 15 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 15 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 393216 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 393216 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 150: Width=577239, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C150() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 150 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 150 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 150 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 150 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 151: Width=577239, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C151() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 151 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 151 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 151 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 151 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 152: Width=577239, Height=690876, Depth=0, IC=18871
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C152() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 152 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 152 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 152 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 152 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 690876 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 18871 ).eq( i ) );
  }

  /**
   * test aer12 Char 153: Width=470280, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C153() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 153 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 153 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 153 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 153 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 470280 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 154: Width=470280, Height=690876, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C154() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 154 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 154 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 154 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 154 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 470280 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 690876 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 155: Width=470280, Height=720367, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C155() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 155 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 155 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 155 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 155 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 470280 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 720367 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 156: Width=673183, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C156() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 156 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 156 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 156 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 156 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 673183 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 157: Width=277608, Height=720367, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C157() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 157 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 157 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 157 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 157 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 277608 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 720367 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 158: Width=427032, Height=541452, Depth=1568, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C158() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 158 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 158 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 158 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 158 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 1568 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 159: Width=349167, Height=541452, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C159() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 159 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 159 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 159 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 159 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 349167 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 16: Width=286256, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C16() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 16 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 16 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 16 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 16 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 286256 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 160: Width=384560, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C160() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 160 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 160 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 160 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 160 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 161: Width=384560, Height=338160, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C161() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 161 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 161 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 161 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 161 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 162: Width=342096, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C162() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 162 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 162 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 162 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 162 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 342096 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 163: Width=342096, Height=488760, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C163() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 163 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 163 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 163 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 163 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 342096 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 488760 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 164: Width=581168, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C164() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 164 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 164 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 164 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 164 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 581168 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 165: Width=342096, Height=488760, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C165() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 165 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 165 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 165 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 165 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 342096 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 488760 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 166: Width=342096, Height=338160, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C166() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 166 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 166 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 166 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 166 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 342096 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 167: Width=384560, Height=541452, Depth=152559, IC=10215
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C167() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 167 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 167 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 167 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 167 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 10215 ).eq( i ) );
  }

  /**
   * test aer12 Char 168: Width=213120, Height=753399, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C168() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 168 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 168 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 168 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 168 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 753399 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 169: Width=347600, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C169() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 169 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 169 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 169 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 169 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 347600 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 17: Width=286256, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C17() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 17 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 17 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 17 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 17 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 286256 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 170: Width=213120, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C170() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 170 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 170 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 170 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 170 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 171: Width=427032, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C171() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 171 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 171 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 171 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 171 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 172: Width=427032, Height=488760, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C172() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 172 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 172 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 172 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 172 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 488760 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 173: Width=393216, Height=393216, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C173() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 173 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 173 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 173 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 173 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 393216 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 393216 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 174: Width=384560, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C174() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 174 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 174 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 174 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 174 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 175: Width=298840, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C175() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 175 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 175 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 175 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 175 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 298840 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 176: Width=298840, Height=488760, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C176() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 176 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 176 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 176 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 176 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 298840 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 488760 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 177: Width=303560, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C177() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 177 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 177 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 177 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 177 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 303560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 178: Width=303560, Height=488760, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C178() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 178 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 178 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 178 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 178 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 303560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 488760 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 179: Width=303560, Height=338160, Depth=133688, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C179() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 179 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 179 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 179 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 179 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 303560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 133688 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 18: Width=325576, Height=76280, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C18() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 18 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 18 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 18 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 18 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 325576 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 76280 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 180: Width=452984, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C180() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 180 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 180 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 180 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 180 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 452984 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 181: Width=298840, Height=488760, Depth=133688, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C181() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 181 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 181 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 181 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 181 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 298840 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 488760 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 133688 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 182: Width=427032, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C182() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 182 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 182 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 182 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 182 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 183: Width=427032, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C183() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 183 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 183 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 183 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 183 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 184: Width=405792, Height=488760, Depth=152559, IC=10215
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C184() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 184 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 184 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 184 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 184 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 405792 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 488760 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 10215 ).eq( i ) );
  }

  /**
   * test aer12 Char 185: Width=342096, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C185() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 185 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 185 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 185 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 185 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 342096 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 186: Width=342096, Height=488760, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C186() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 186 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 186 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 186 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 186 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 342096 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 488760 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 187: Width=342096, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C187() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 187 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 187 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 187 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 187 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 342096 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 188: Width=448264, Height=513927, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C188() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 188 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 188 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 188 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 188 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 448264 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 189: Width=213120, Height=393216, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C189() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 189 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 189 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 189 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 189 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 393216 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 19: Width=393216, Height=393216, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C19() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 19 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 19 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 19 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 19 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 393216 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 393216 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 190: Width=363328, Height=393216, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C190() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 190 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 190 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 190 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 190 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 363328 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 393216 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 191: Width=545775, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C191() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 191 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 191 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 191 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 191 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 545775 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 192: Width=577239, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C192() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 192 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 192 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 192 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 192 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 193: Width=577239, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C193() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 193 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 193 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 193 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 193 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 194: Width=577239, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C194() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 194 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 194 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 194 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 194 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 195: Width=577239, Height=720367, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C195() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 195 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 195 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 195 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 195 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 720367 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 196: Width=577239, Height=690876, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C196() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 196 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 196 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 196 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 196 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 690876 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 197: Width=577239, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C197() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 197 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 197 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 197 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 197 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 198: Width=694416, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C198() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 198 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 198 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 198 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 198 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 694416 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 199: Width=556000, Height=541452, Depth=133688, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C199() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 199 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 199 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 199 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 199 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 556000 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 133688 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 2: Width=384560, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C2() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 2 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 2 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 2 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 2 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 20: Width=393216, Height=393216, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C20() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 20 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 20 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 20 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 20 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 393216 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 393216 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 200: Width=523759, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C200() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 200 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 200 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 200 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 200 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 523759 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 201: Width=523759, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C201() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 201 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 201 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 201 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 201 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 523759 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 202: Width=523759, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C202() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 202 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 202 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 202 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 202 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 523759 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 203: Width=523759, Height=690876, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C203() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 203 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 203 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 203 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 203 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 523759 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 690876 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 204: Width=277608, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C204() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 204 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 204 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 204 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 204 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 277608 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 205: Width=277608, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C205() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 205 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 205 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 205 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 205 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 277608 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 206: Width=277608, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C206() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 206 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 206 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 206 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 206 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 277608 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 207: Width=277608, Height=690876, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C207() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 207 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 207 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 207 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 207 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 277608 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 690876 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 208: Width=393216, Height=393216, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C208() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 208 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 208 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 208 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 208 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 393216 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 393216 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 209: Width=577239, Height=720367, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C209() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 209 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 209 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 209 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 209 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 720367 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 21: Width=384560, Height=338160, Depth=0, IC=21231
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C21() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 21 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 21 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 21 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 21 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 21231 ).eq( i ) );
  }

  /**
   * test aer12 Char 210: Width=598472, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C210() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 210 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 210 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 210 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 210 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 598472 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 211: Width=598472, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C211() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 211 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 211 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 211 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 211 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 598472 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 212: Width=598472, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C212() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 212 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 212 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 212 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 212 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 598472 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 213: Width=598472, Height=720367, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C213() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 213 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 213 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 213 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 213 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 598472 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 720367 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 214: Width=598472, Height=690876, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C214() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 214 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 214 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 214 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 214 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 598472 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 690876 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 215: Width=780135, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C215() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 215 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 215 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 215 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 215 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 780135 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 216: Width=598472, Height=582351, Depth=37744, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C216() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 216 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 216 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 216 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 216 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 598472 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 582351 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 37744 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 217: Width=577239, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C217() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 217 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 217 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 217 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 217 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 218: Width=577239, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C218() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 218 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 218 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 218 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 218 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 219: Width=577239, Height=744744, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C219() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 219 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 219 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 219 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 219 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 22: Width=769911, Height=338160, Depth=0, IC=21231
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C22() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 22 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 22 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 22 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 22 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 769911 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 21231 ).eq( i ) );
  }

  /**
   * test aer12 Char 220: Width=577239, Height=690876, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C220() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 220 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 220 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 220 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 220 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 690876 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 221: Width=577239, Height=744744, Depth=0, IC=18871
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C221() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 221 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 221 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 221 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 221 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 744744 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 18871 ).eq( i ) );
  }

  /**
   * test aer12 Char 222: Width=393216, Height=393216, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C222() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 222 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 222 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 222 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 222 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 393216 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 393216 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 223: Width=854064, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C223() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 223 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 223 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 223 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 223 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 854064 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 224: Width=384560, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C224() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 224 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 224 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 224 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 224 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 225: Width=384560, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C225() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 225 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 225 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 225 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 225 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 226: Width=384560, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C226() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 226 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 226 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 226 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 226 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 227: Width=384560, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C227() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 227 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 227 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 227 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 227 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 228: Width=384560, Height=488760, Depth=31455, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C228() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 228 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 228 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 228 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 228 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 488760 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 31455 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 229: Width=384560, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C229() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 229 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 229 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 229 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 229 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 23: Width=0, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C23() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 23 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 23 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 23 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 23 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 0 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 230: Width=556000, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C230() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 230 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 230 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 230 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 230 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 556000 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 231: Width=342096, Height=338160, Depth=133688, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C231() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 231 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 231 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 231 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 231 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 342096 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 133688 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 232: Width=342096, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C232() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 232 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 232 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 232 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 232 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 342096 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 233: Width=342096, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C233() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 233 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 233 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 233 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 233 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 342096 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 234: Width=342096, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C234() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 234 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 234 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 234 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 234 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 342096 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 235: Width=342096, Height=488760, Depth=31455, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C235() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 235 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 235 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 235 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 235 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 342096 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 488760 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 31455 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 236: Width=213120, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C236() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 236 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 236 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 236 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 236 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 237: Width=213120, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C237() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 237 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 237 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 237 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 237 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 238: Width=213120, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C238() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 238 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 238 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 238 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 238 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 239: Width=213120, Height=488760, Depth=31455, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C239() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 239 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 239 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 239 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 239 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 488760 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 31455 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 24: Width=393216, Height=393216, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C24() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 24 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 24 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 24 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 24 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 393216 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 393216 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 240: Width=393216, Height=393216, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C240() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 240 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 240 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 240 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 240 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 393216 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 393216 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 241: Width=427032, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C241() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 241 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 241 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 241 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 241 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 242: Width=384560, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C242() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 242 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 242 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 242 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 242 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 243: Width=384560, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C243() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 243 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 243 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 243 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 243 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 244: Width=384560, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C244() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 244 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 244 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 244 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 244 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 245: Width=384560, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C245() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 245 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 245 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 245 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 245 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 246: Width=384560, Height=488760, Depth=31455, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C246() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 246 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 246 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 246 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 246 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 488760 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 31455 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 247: Width=598472, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C247() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 247 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 247 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 247 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 247 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 598472 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 248: Width=384560, Height=414051, Depth=76280, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C248() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 248 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 248 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 248 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 248 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 414051 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 76280 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 249: Width=427032, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C249() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 249 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 249 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 249 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 249 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 25: Width=213120, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C25() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 25 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 25 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 25 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 25 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 250: Width=427032, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C250() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 250 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 250 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 250 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 250 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 251: Width=427032, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C251() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 251 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 251 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 251 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 251 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 252: Width=427032, Height=488760, Depth=31455, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C252() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 252 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 252 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 252 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 252 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 488760 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 31455 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 253: Width=405792, Height=541452, Depth=152559, IC=10215
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C253() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 253 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 253 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 253 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 253 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 405792 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 10215 ).eq( i ) );
  }

  /**
   * test aer12 Char 254: Width=393216, Height=393216, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C254() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 254 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 254 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 254 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 254 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 393216 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 393216 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 255: Width=384560, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C255() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 255 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 255 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 255 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 255 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 26: Width=235136, Height=338160, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C26() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 26 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 26 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 26 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 26 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 235136 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 27: Width=449048, Height=541452, Depth=0, IC=54255
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C27() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 27 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 27 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 27 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 27 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 449048 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 54255 ).eq( i ) );
  }

  /**
   * test aer12 Char 28: Width=427032, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C28() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 28 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 28 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 28 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 28 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 29: Width=427032, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C29() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 29 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 29 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 29 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 29 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 3: Width=384560, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C3() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 3 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 3 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 3 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 3 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 30: Width=640935, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C30() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 30 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 30 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 30 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 30 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 640935 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 31: Width=640935, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C31() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 31 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 31 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 31 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 31 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 640935 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 32: Width=456128, Height=0, Depth=157280, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C32() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 32 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 32 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 32 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 32 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 456128 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 0 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 157280 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 33: Width=213120, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C33() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 33 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 33 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 33 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 33 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 34: Width=286256, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C34() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 34 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 34 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 34 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 34 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 286256 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 35: Width=640935, Height=541452, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C35() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 35 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 35 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 35 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 35 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 640935 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 36: Width=384560, Height=582351, Depth=43248, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C36() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 36 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 36 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 36 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 36 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 582351 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 43248 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 37: Width=640935, Height=582351, Depth=43248, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C37() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 37 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 37 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 37 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 37 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 640935 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 582351 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 43248 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 38: Width=598472, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C38() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 38 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 38 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 38 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 38 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 598472 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 39: Width=213120, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C39() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 39 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 39 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 39 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 39 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 4: Width=384560, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C4() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 4 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 4 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 4 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 4 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 40: Width=298840, Height=582351, Depth=196608, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C40() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 40 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 40 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 40 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 40 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 298840 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 582351 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 196608 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 41: Width=298840, Height=582351, Depth=196608, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C41() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 41 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 41 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 41 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 41 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 298840 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 582351 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 196608 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 42: Width=384560, Height=582351, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C42() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 42 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 42 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 42 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 42 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 582351 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 43: Width=598472, Height=448263, Depth=59768, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C43() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 43 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 43 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 43 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 43 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 598472 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 448263 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 59768 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 44: Width=213120, Height=76280, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C44() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 44 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 44 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 44 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 44 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 76280 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 45: Width=256376, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C45() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 45 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 45 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 45 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 45 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 256376 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 46: Width=213120, Height=76280, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C46() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 46 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 46 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 46 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 46 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 76280 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 47: Width=384560, Height=582351, Depth=196608, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C47() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 47 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 47 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 47 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 47 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 582351 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 196608 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 48: Width=384560, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C48() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 48 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 48 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 48 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 48 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 49: Width=384560, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C49() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 49 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 49 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 49 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 49 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 5: Width=384560, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C5() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 5 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 5 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 5 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 5 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 50: Width=384560, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C50() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 50 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 50 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 50 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 50 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 51: Width=384560, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C51() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 51 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 51 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 51 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 51 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 52: Width=384560, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C52() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 52 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 52 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 52 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 52 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 53: Width=384560, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C53() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 53 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 53 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 53 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 53 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 54: Width=384560, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C54() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 54 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 54 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 54 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 54 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 55: Width=384560, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C55() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 55 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 55 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 55 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 55 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 56: Width=384560, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C56() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 56 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 56 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 56 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 56 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 57: Width=384560, Height=513927, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C57() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 57 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 57 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 57 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 57 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 513927 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 58: Width=213120, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C58() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 58 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 58 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 58 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 58 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 59: Width=213120, Height=338160, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C59() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 59 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 59 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 59 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 59 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 6: Width=0, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C6() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 6 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 6 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 6 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 6 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 0 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 60: Width=598472, Height=414051, Depth=20439, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C60() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 60 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 60 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 60 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 60 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 598472 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 414051 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 20439 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 61: Width=598472, Height=283112, Depth=-109312, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C61() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 61 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 61 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 61 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 61 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 598472 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 283112 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( -109312 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 62: Width=598472, Height=414051, Depth=20439, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C62() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 62 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 62 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 62 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 62 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 598472 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 414051 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 20439 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 63: Width=363328, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C63() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 63 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 63 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 63 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 63 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 363328 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 64: Width=598472, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C64() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 64 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 64 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 64 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 64 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 598472 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 65: Width=577239, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C65() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 65 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 65 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 65 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 65 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 66: Width=544992, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C66() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 66 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 66 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 66 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 66 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 544992 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 67: Width=556000, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C67() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 67 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 67 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 67 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 67 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 556000 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 68: Width=587463, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C68() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 68 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 68 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 68 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 68 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 587463 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 69: Width=523759, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C69() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 69 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 69 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 69 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 69 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 523759 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 7: Width=384560, Height=488760, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C7() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 7 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 7 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 7 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 7 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 488760 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 70: Width=501735, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C70() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 70 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 70 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 70 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 70 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 501735 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 71: Width=603975, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C71() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 71 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 71 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 71 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 71 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 603975 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 72: Width=577239, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C72() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 72 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 72 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 72 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 72 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 73: Width=277608, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C73() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 73 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 73 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 73 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 73 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 277608 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 74: Width=395568, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C74() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 74 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 74 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 74 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 74 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 395568 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 75: Width=598472, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C75() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 75 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 75 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 75 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 75 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 598472 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 76: Width=480504, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C76() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 76 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 76 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 76 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 76 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 480504 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 77: Width=705424, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C77() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 77 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 77 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 77 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 77 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 705424 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 78: Width=577239, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C78() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 78 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 78 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 78 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 78 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 79: Width=598472, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C79() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 79 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 79 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 79 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 79 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 598472 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 8: Width=384560, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C8() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 8 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 8 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 8 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 8 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 80: Width=523759, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C80() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 80 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 80 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 80 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 80 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 523759 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 81: Width=598472, Height=541452, Depth=152559, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C81() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 81 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 81 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 81 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 81 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 598472 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 152559 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 82: Width=566224, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C82() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 82 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 82 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 82 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 82 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 566224 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 83: Width=427032, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C83() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 83 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 83 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 83 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 83 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 84: Width=556000, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C84() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 84 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 84 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 84 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 84 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 556000 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 85: Width=577239, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C85() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 85 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 85 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 85 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 85 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 86: Width=577239, Height=541452, Depth=0, IC=10215
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C86() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 86 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 86 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 86 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 86 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 10215 ).eq( i ) );
  }

  /**
   * test aer12 Char 87: Width=791144, Height=541452, Depth=0, IC=10215
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C87() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 87 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 87 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 87 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 87 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 791144 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 10215 ).eq( i ) );
  }

  /**
   * test aer12 Char 88: Width=577239, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C88() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 88 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 88 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 88 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 88 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 89: Width=577239, Height=541452, Depth=0, IC=18871
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C89() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 89 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 89 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 89 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 89 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577239 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 18871 ).eq( i ) );
  }

  /**
   * test aer12 Char 9: Width=384560, Height=448263, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C9() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 9 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 9 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 9 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 9 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 448263 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 90: Width=470280, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C90() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 90 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 90 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 90 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 90 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 470280 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 91: Width=213120, Height=582351, Depth=196608, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C91() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 91 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 91 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 91 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 91 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 582351 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 196608 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 92: Width=393216, Height=582351, Depth=196608, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C92() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 92 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 92 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 92 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 92 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 393216 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 582351 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 196608 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 93: Width=213120, Height=582351, Depth=196608, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C93() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 93 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 93 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 93 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 93 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 582351 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 196608 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 94: Width=384560, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C94() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 94 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 94 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 94 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 94 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 95: Width=314568, Height=31455, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C95() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 95 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 95 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 95 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 95 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 314568 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 31455 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 96: Width=213120, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C96() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 96 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 96 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 96 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 96 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 213120 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 97: Width=384560, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C97() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 97 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 97 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 97 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 97 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 384560 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 98: Width=427032, Height=541452, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C98() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 98 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 98 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 98 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 98 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 427032 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 541452 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * test aer12 Char 99: Width=342096, Height=338160, Depth=0, IC=0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testaer12C99() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedGlue h = font.getHeight( UnicodeChar.get( Unicode.OFFSET + 99 ) );
    FixedGlue w = font.getWidth( UnicodeChar.get( Unicode.OFFSET + 99 ) );
    FixedGlue d = font.getDepth( UnicodeChar.get( Unicode.OFFSET + 99 ) );
    FixedDimen i =
        font.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 99 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 342096 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 338160 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( 0 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
  }

  /**
   * kerning test aer12 Kerning: V,: 0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testKerning1() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedDimen k =
        font.getKerning( UnicodeChar.get( 'V' ), UnicodeChar.get( ',' ) );
    assertNotNull( k );
    assertTrue( Long.toString( k.getValue() ), new Dimen( 0 ).eq( k ) );
  }

  /**
   * kerning test aer12 Kerning: W.: 0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testKerning2() throws Exception {

    assertNotNull( font );
    assertNotNull( key );

    FixedDimen k =
        font.getKerning( UnicodeChar.get( 'W' ), UnicodeChar.get( '.' ) );
    assertNotNull( k );
    assertTrue( Long.toString( k.getValue() ), new Dimen( 0 ).eq( k ) );
  }

}
