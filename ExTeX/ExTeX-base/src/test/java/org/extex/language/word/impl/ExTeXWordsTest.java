/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.language.word.impl;

import org.extex.base.font.CMR10;
import org.extex.core.UnicodeChar;
import org.extex.language.hyphenation.base.BaseHyphenationTable;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.language.ligature.impl.LigatureBuilderImpl;
import org.extex.language.word.WordTokenizer;
import org.extex.typesetter.tc.ModifiableTypesettingContext;
import org.extex.typesetter.tc.TypesettingContextImpl;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.DiscretionaryNode;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.LigatureNode;
import org.extex.typesetter.type.node.factory.NodeFactory;
import org.extex.typesetter.type.node.factory.SimpleNodeFactory;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This is a test suite for ExTeXWords.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ExTeXWordsTest {

  /**
   * The field {@code TOKENIZER} contains the word tokenizer to test. The
   * word tokenizer is stateless. Thus a single instance suffices.
   */
  private static final WordTokenizer TOKENIZER = new ExTeXWords();

  /**
   * The field {@code NODE_FACTORY} contains the node factory to use
   * throughout the test cases.
   */
  private static final NodeFactory NODE_FACTORY = new SimpleNodeFactory();

  /**
   * The field {@code TC} contains the typesetting context.
   */
  private static final ModifiableTypesettingContext TC =
      new TypesettingContextImpl( new CMR10() );

  {
    BaseHyphenationTable lan = new BaseHyphenationTable();
    lan.setLigatureBuilder( new LigatureBuilderImpl() );
    lan.setWordTokenizer( TOKENIZER );
    try {
      lan.setLeftHyphenMin( 1L );
      lan.setRightHyphenMin( 1L );
    } catch( HyphenationException e ) {
      e.printStackTrace();
    }
    TC.setLanguage( lan );
  }

  /**
   * The field {@code HYPHEN_NODE} contains the hyphen node.
   */
  private static final CharNode HYPHEN_NODE = (CharNode) NODE_FACTORY
      .getNode( TC, UnicodeChar.get( '-' ) );

  /**
   * The field {@code UC_F} contains the character f.
   */
  private static final UnicodeChar UC_F = UnicodeChar.get( 'f' );

  /**
   * The field {@code UC_I} contains the character i.
   */
  private static final UnicodeChar UC_I = UnicodeChar.get( 'i' );

  /**
   * The field {@code UC_L} contains the character l.
   */
  private static final UnicodeChar UC_L = UnicodeChar.get( 'l' );

  /**
   * The field {@code UC_FF} contains the ff ligature.
   */
  private static final UnicodeChar UC_FF = UnicodeChar.get( '\013' );

  /**
   * The field {@code UC_FI} contains the fi ligature.
   */
  private static final UnicodeChar UC_FI = UnicodeChar.get( '\014' );

  /**
   * The field {@code UC_FL} contains the fl ligature.
   */
  private static final UnicodeChar UC_FL = UnicodeChar.get( '\015' );

  /**
   * The field {@code UC_FFI} contains the ffi ligature.
   */
  private static final UnicodeChar UC_FFI = UnicodeChar.get( '\016' );

  /**
   * The field {@code UC_FFL} contains the ffl ligature.
   */
  private static final UnicodeChar UC_FFL = UnicodeChar.get( '\017' );

  /**
   * Translate a string into a list of nodes.
   *
   * @param s the characters to insert into the list
   * @return a node list made of the characters
   */
  private static NodeList makeList( CharSequence s ) {

    NodeList nodes = new HorizontalListNode();

    for( int i = 0; i < s.length(); i++ ) {
      char c = s.charAt( i );
      switch( c ) {
        case '\013':
          nodes.add( new LigatureNode( TC, UC_FF,
                                       (CharNode) NODE_FACTORY.getNode( TC,
                                                                        UC_F ),
                                       (CharNode) NODE_FACTORY.getNode( TC,
                                                                        UC_F ) ) );
          break;
        case '\014':
          nodes.add( new LigatureNode( TC, UC_FI,
                                       (CharNode) NODE_FACTORY.getNode( TC,
                                                                        UC_F ),
                                       (CharNode) NODE_FACTORY.getNode( TC,
                                                                        UC_I ) ) );
          break;
        case '\015':
          nodes.add( new LigatureNode( TC, UC_FL,
                                       (CharNode) NODE_FACTORY.getNode( TC,
                                                                        UC_F ),
                                       (CharNode) NODE_FACTORY.getNode( TC,
                                                                        UC_L ) ) );
          break;
        case '\016':
          nodes.add( new LigatureNode( TC, UC_FFI,
                                       new LigatureNode( TC, UC_FF,
                                                         (CharNode) NODE_FACTORY
                                                             .getNode( TC,
                                                                       UC_F ),
                                                         (CharNode) NODE_FACTORY
                                                             .getNode( TC,
                                                                       UC_F ) ),
                                       (CharNode) NODE_FACTORY.getNode( TC,
                                                                        UC_I ) ) );
          break;
        case '\017':
          nodes.add( new LigatureNode( TC, UC_FFL,
                                       new LigatureNode( TC, UC_FF,
                                                         (CharNode) NODE_FACTORY
                                                             .getNode( TC,
                                                                       UC_F ),
                                                         (CharNode) NODE_FACTORY
                                                             .getNode( TC,
                                                                       UC_F ) ),
                                       (CharNode) NODE_FACTORY.getNode( TC,
                                                                        UC_L ) ) );
          break;
        default:
          nodes.add( NODE_FACTORY.getNode( TC, UnicodeChar.get( c ) ) );
      }
    }
    return nodes;
  }

  /**
   * Test that the empty spec is accepted.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testInsertShy1() throws Exception {

    NodeList nodes = new HorizontalListNode();
    TOKENIZER.insertShy( nodes, 0, new boolean[ 0 ], HYPHEN_NODE );
    assertEquals( 0, nodes.size() );
  }

  /**
   * Test that the one element spec is accepted.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testInsertShy10() throws Exception {

    NodeList nodes = makeList( "ab" );
    TOKENIZER.insertShy( nodes, 0, new boolean[]{true}, HYPHEN_NODE );
    assertEquals( 3, nodes.size() );
    assertTrue( nodes.get( 0 ) instanceof DiscretionaryNode );
    assertTrue( nodes.get( 1 ) instanceof CharNode );
    assertTrue( nodes.get( 2 ) instanceof CharNode );
  }

  /**
   * Test that the one element spec is accepted.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testInsertShy11() throws Exception {

    NodeList nodes = makeList( "ab" );
    TOKENIZER.insertShy( nodes, 0, new boolean[]{false}, HYPHEN_NODE );
    assertEquals( 2, nodes.size() );
    assertTrue( nodes.get( 0 ) instanceof CharNode );
    assertTrue( nodes.get( 1 ) instanceof CharNode );
  }

  /**
   * Test that the empty spec is accepted.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testInsertShy2() throws Exception {

    NodeList nodes = makeList( "a" );
    TOKENIZER.insertShy( nodes, 0, new boolean[ 0 ], HYPHEN_NODE );
    assertEquals( 1, nodes.size() );
  }

  /**
   * Test that ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testInsertShy20() throws Exception {

    NodeList nodes = makeList( "ab" );
    TOKENIZER.insertShy( nodes, 0, new boolean[]{false, true}, HYPHEN_NODE );
    assertEquals( 3, nodes.size() );
    assertTrue( nodes.get( 0 ) instanceof CharNode );
    assertTrue( nodes.get( 1 ) instanceof DiscretionaryNode );
    assertTrue( nodes.get( 2 ) instanceof CharNode );
  }

  /**
   * Test that ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testInsertShy21() throws Exception {

    NodeList nodes = makeList( "abc" );
    TOKENIZER.insertShy( nodes, 1, new boolean[]{false, true}, HYPHEN_NODE );
    assertEquals( 4, nodes.size() );
    assertTrue( nodes.get( 0 ) instanceof CharNode );
    assertTrue( nodes.get( 1 ) instanceof CharNode );
    assertTrue( nodes.get( 2 ) instanceof DiscretionaryNode );
    assertTrue( nodes.get( 3 ) instanceof CharNode );
  }

  /**
   * Test that the empty spec is accepted.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testInsertShy3() throws Exception {

    NodeList nodes = makeList( "a" );
    TOKENIZER.insertShy( nodes, 1, new boolean[ 0 ], HYPHEN_NODE );
    assertEquals( 1, nodes.size() );
  }

  /**
   * Test that ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testInsertShy31() throws Exception {

    NodeList nodes = makeList( "a\13b" );
    TOKENIZER.insertShy( nodes, 0, new boolean[]{false, true, false},
                         HYPHEN_NODE );
    assertEquals( 4, nodes.size() );
    assertTrue( nodes.get( 0 ) instanceof CharNode );
    assertTrue( nodes.get( 1 ) instanceof DiscretionaryNode );
    assertTrue( nodes.get( 2 ) instanceof LigatureNode );
    assertTrue( nodes.get( 3 ) instanceof CharNode );
  }

  /**
   * Test that ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testInsertShy32() throws Exception {

    NodeList nodes = makeList( "a\13b" );
    TOKENIZER.insertShy( nodes, 1, new boolean[]{false, true}, HYPHEN_NODE );
    assertEquals( 3, nodes.size() );
    assertTrue( nodes.get( 0 ) instanceof CharNode );
    assertTrue( nodes.get( 1 ) instanceof DiscretionaryNode );
    assertTrue( nodes.get( 2 ) instanceof CharNode );
  }

  /**
   * Test that ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testInsertShy33() throws Exception {

    NodeList nodes = makeList( "a\13b" );
    TOKENIZER.insertShy( nodes, 1, new boolean[]{false, false, true},
                         HYPHEN_NODE );
    assertEquals( 4, nodes.size() );
    assertTrue( nodes.get( 0 ) instanceof CharNode );
    assertTrue( nodes.get( 1 ) instanceof LigatureNode );
    assertTrue( nodes.get( 2 ) instanceof DiscretionaryNode );
    assertTrue( nodes.get( 3 ) instanceof CharNode );
  }

  /**
   * Test that ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testInsertShy41() throws Exception {

    NodeList nodes = makeList( "a\17b" );
    TOKENIZER.insertShy( nodes,
                         1,
                         new boolean[]{false, false, true, false},
                         HYPHEN_NODE );
    assertEquals( 3, nodes.size() );
    assertTrue( nodes.get( 0 ) instanceof CharNode );
    assertTrue( nodes.get( 1 ) instanceof DiscretionaryNode );
    DiscretionaryNode d = (DiscretionaryNode) nodes.get( 1 );
    assertTrue( d.getPreBreak().get( 0 ) instanceof LigatureNode );
    assertTrue( d.getPostBreak().get( 0 ) instanceof CharNode );
    assertTrue( nodes.get( 2 ) instanceof CharNode );
  }

  /**
   * Test that ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testInsertShy42() throws Exception {

    NodeList nodes = makeList( "a\17b" );
    TOKENIZER.insertShy( nodes, 1,
                         new boolean[]{false, true, false}, HYPHEN_NODE );
    assertEquals( 3, nodes.size() );
    assertTrue( nodes.get( 0 ) instanceof CharNode );
    assertTrue( nodes.get( 1 ) instanceof DiscretionaryNode );
    DiscretionaryNode d = (DiscretionaryNode) nodes.get( 1 );
    assertNotNull( d );
    // assertTrue(d.getPreBreak().get(0) instanceof CharNode);
    // assertTrue(d.getPostBreak().get(0) instanceof LigatureNode);
    // assertTrue(nodes.get(2) instanceof CharNode);
  }

  // TODO gene: add test cases for handling of implicit kerns
}
