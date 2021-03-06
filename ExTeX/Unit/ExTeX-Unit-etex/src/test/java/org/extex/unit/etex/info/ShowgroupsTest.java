/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.info;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;

import java.util.Properties;

/**
 * This is a test suite for the primitive {@code \showgroups}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ShowgroupsTest extends NoFlagsPrimitiveTester {


  public ShowgroupsTest() {

    setPrimitive( "showgroups" );
    setArguments( "" );
    setPrepare( "" );
    setOut( "### bottom level group\n" );
    setConfig( "etex-test" );
  }

  /**
   * Prepare the properties to use a fine log level and suppress the banner.
   *
   * @return the properties to use
   */
  private Properties prepare() {

    Properties p = getProps();
    p.setProperty( "extex.launcher.loglevel", "info" );
    p.setProperty( "extex.nobanner", "true" );
    return p;
  }

  /**
   * <testcase primitive="\showgroups">
   * Test case checking that {@code \showgroups} produces the correct
   * message for the top-level group.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertOutput( prepare(),
                  //--- input code ---
                  "\\showgroups\\end ",
                  //--- error channel ---
                  "### bottom level group\n", "" );
  }

  /**
   * <testcase primitive="\showgroups">
   * Test case checking that {@code \showgroups} ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertOutput( prepare(),
                  //--- input code ---
                  "\\begingroup\\showgroups\\endgroup\\end ",
                  //--- error channel ---
                  "### semi simple group (level 1) entered at line 1 " +
                      "(\\begingroup)\n"
                      + "### bottom level group\n",
                  "" );
  }

  /**
   * <testcase primitive="\showgroups">
   * Test case checking that {@code \showgroups} ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test3() throws Exception {

    assertOutput( prepare(),
                  //--- input code ---
                  DEFINE_BRACES
                      + "{\\showgroups}\\end ",
                  //--- error channel ---
                  "### simple group (level 1) entered at line 1 ({)\n"
                      + "### bottom level group\n", "" );
  }

  /**
   * <testcase primitive="\showgroups">
   * Test case checking that {@code \showgroups} ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test4() throws Exception {

    assertOutput( prepare(),
                  //--- input code ---
                  DEFINE_BRACES
                      + "\\hbox{\\showgroups}\\end ",
                  //--- error channel ---
                  "### hbox group (level 1) entered at line 1 (\\hbox)\n"
                      + "### bottom level group\n",
                  null );
  }

  /**
   * <testcase primitive="\showgroups">
   * Test case checking that {@code \showgroups} ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test5() throws Exception {

    assertOutput( prepare(),
                  //--- input code ---
                  DEFINE_BRACES
                      + "\\hbox to 12pt{\\showgroups}\\end ",
                  //--- error channel ---
                  "### adjusted hbox group (level 1) entered at line 1 " +
                      "(\\hbox)\n"
                      + "### bottom level group\n",
                  null );
  }

  /**
   * <testcase primitive="\showgroups">
   * Test case checking that {@code \showgroups} ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test6() throws Exception {

    assertOutput( prepare(),
                  //--- input code ---
                  DEFINE_BRACES
                      + "\\vbox{\\showgroups}\\end ",
                  //--- error channel ---
                  "### vbox group (level 1) entered at line 1 (\\vbox)\n"
                      + "### bottom level group\n",
                  null );
  }

  /**
   * <testcase primitive="\showgroups">
   * Test case checking that {@code \showgroups} ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test7() throws Exception {

    assertOutput( prepare(),
                  //--- input code ---
                  DEFINE_BRACES
                      + "\\vtop{\\showgroups}\\end ",
                  //--- error channel ---
                  "### vtop group (level 1) entered at line 1 (\\vtop)\n"
                      + "### bottom level group\n",
                  null );
  }

  /**
   * <testcase primitive="\showgroups">
   * Test case checking that {@code \showgroups} ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMulti1() throws Exception {

    assertOutput( prepare(),
                  //--- input code ---
                  DEFINE_BRACES
                      + "\\begingroup{\\showgroups}\\endgroup\\end ",
                  //--- error channel ---
                  "### simple group (level 2) entered at line 1 ({)\n"
                      + "### semi simple group (level 1) entered at line 1 " +
                      "(\\begingroup)\n"
                      + "### bottom level group\n", "" );
  }

  //TODO implement the primitive specific test cases

}
