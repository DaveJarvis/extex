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

package org.extex.fontinst;

import org.extex.test.ExTeXLauncher;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Test for fontinst.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class FontInst03Test extends ExTeXLauncher {

  /**
   * The field {@code SEP} contains the separator for properties.
   */
  private static final String SEP = System.getProperty( "path.separator", ":" );

  /**
   * The tex output (fxlr8r.pl)
   */
  private static final String TEXT_FXLR8R_PL =
      "(COMMENT raw font fxlr8r created by fontinst v1.929)\n"
          + "\n"
          + "(COMMENT Filename: fxlr8r.pl)\n"
          + "(COMMENT Created by: tex texput)\n"
          + "(COMMENT Created using: \\mtxtopl{fxlr8r}{fxlr8r})\n"
          + "\n"
          + "(COMMENT This file can be turned into a ligless TeX font with)\n"
          + "(COMMENT pltotf fxlr8r.pl fxlr8r.tfm)\n" + "\n"
          + "(COMMENT THIS FILE CAN THEN BE DELETED.)\n" + "\n"
          + "(DESIGNSIZE R 10.0)\n" + "\n"
          + "(DESIGNUNITS R 1.000)\n"
          + "(CHARACTER D 32 (COMMENT space)\n"
          + "   (CHARWD R 0.250)\n" + "   (CHARHT R 0.000)\n"
          + "   (CHARDP R 0.000)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 33 (COMMENT exclam)\n"
          + "   (CHARWD R 0.227)\n" + "   (CHARHT R 0.658)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 34 (COMMENT quotedbl)\n"
          + "   (CHARWD R 0.336)\n" + "   (CHARHT R 0.645)\n"
          + "   (CHARDP R -0.426)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 35 (COMMENT numbersign)\n"
          + "   (CHARWD R 0.438)\n" + "   (CHARHT R 0.560)\n"
          + "   (CHARDP R -0.013)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 36 (COMMENT dollar)\n"
          + "   (CHARWD R 0.438)\n" + "   (CHARHT R 0.627)\n"
          + "   (CHARDP R 0.081)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 37 (COMMENT percent)\n"
          + "   (CHARWD R 0.637)\n" + "   (CHARHT R 0.569)\n"
          + "   (CHARDP R 0.019)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 38 (COMMENT ampersand)\n"
          + "   (CHARWD R 0.705)\n" + "   (CHARHT R 0.644)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 31 (COMMENT quotesingle)\n"
          + "   (CHARWD R 0.190)\n" + "   (CHARHT R 0.644)\n"
          + "   (CHARDP R -0.426)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 40 (COMMENT parenleft)\n"
          + "   (CHARWD R 0.295)\n" + "   (CHARHT R 0.707)\n"
          + "   (CHARDP R 0.204)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 41 (COMMENT parenright)\n"
          + "   (CHARWD R 0.295)\n" + "   (CHARHT R 0.708)\n"
          + "   (CHARDP R 0.203)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 42 (COMMENT asterisk)\n"
          + "   (CHARWD R 0.433)\n" + "   (CHARHT R 0.718)\n"
          + "   (CHARDP R -0.451)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 43 (COMMENT plus)\n"
          + "   (CHARWD R 0.527)\n" + "   (CHARHT R 0.430)\n"
          + "   (CHARDP R -0.034)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 44 (COMMENT comma)\n"
          + "   (CHARWD R 0.219)\n" + "   (CHARHT R 0.095)\n"
          + "   (CHARDP R 0.118)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 45 (COMMENT hyphen)\n"
          + "   (CHARWD R 0.333)\n" + "   (CHARHT R 0.290)\n"
          + "   (CHARDP R -0.236)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 46 (COMMENT period)\n"
          + "   (CHARWD R 0.219)\n" + "   (CHARHT R 0.094)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 47 (COMMENT slash)\n"
          + "   (CHARWD R 0.291)\n" + "   (CHARHT R 0.682)\n"
          + "   (CHARDP R 0.021)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 48 (COMMENT zero)\n"
          + "   (CHARWD R 0.438)\n" + "   (CHARHT R 0.578)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 49 (COMMENT one)\n"
          + "   (CHARWD R 0.438)\n" + "   (CHARHT R 0.578)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 50 (COMMENT two)\n"
          + "   (CHARWD R 0.438)\n" + "   (CHARHT R 0.578)\n"
          + "   (CHARDP R 0.000)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 51 (COMMENT three)\n"
          + "   (CHARWD R 0.438)\n" + "   (CHARHT R 0.578)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 52 (COMMENT four)\n"
          + "   (CHARWD R 0.438)\n" + "   (CHARHT R 0.578)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 53 (COMMENT five)\n"
          + "   (CHARWD R 0.438)\n" + "   (CHARHT R 0.578)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 54 (COMMENT six)\n"
          + "   (CHARWD R 0.438)\n" + "   (CHARHT R 0.578)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 55 (COMMENT seven)\n"
          + "   (CHARWD R 0.438)\n" + "   (CHARHT R 0.578)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 56 (COMMENT eight)\n"
          + "   (CHARWD R 0.438)\n" + "   (CHARHT R 0.578)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 57 (COMMENT nine)\n"
          + "   (CHARWD R 0.438)\n" + "   (CHARHT R 0.578)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 58 (COMMENT colon)\n"
          + "   (CHARWD R 0.219)\n" + "   (CHARHT R 0.406)\n"
          + "   (CHARDP R -0.037)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 59 (COMMENT semicolon)\n"
          + "   (CHARWD R 0.219)\n" + "   (CHARHT R 0.406)\n"
          + "   (CHARDP R 0.118)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 60 (COMMENT less)\n"
          + "   (CHARWD R 0.487)\n" + "   (CHARHT R 0.404)\n"
          + "   (CHARDP R -0.073)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 61 (COMMENT equal)\n"
          + "   (CHARWD R 0.527)\n" + "   (CHARHT R 0.318)\n"
          + "   (CHARDP R -0.144)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 62 (COMMENT greater)\n"
          + "   (CHARWD R 0.487)\n" + "   (CHARHT R 0.402)\n"
          + "   (CHARDP R -0.071)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 63 (COMMENT question)\n"
          + "   (CHARWD R 0.405)\n" + "   (CHARHT R 0.659)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 64 (COMMENT at)\n"
          + "   (CHARWD R 1.004)\n" + "   (CHARHT R 0.685)\n"
          + "   (CHARDP R 0.227)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 65 (COMMENT A)\n"
          + "   (CHARWD R 0.695)\n" + "   (CHARHT R 0.658)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 66 (COMMENT B)\n"
          + "   (CHARWD R 0.592)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 67 (COMMENT C)\n"
          + "   (CHARWD R 0.644)\n" + "   (CHARHT R 0.658)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 68 (COMMENT D)\n"
          + "   (CHARWD R 0.697)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 69 (COMMENT E)\n"
          + "   (CHARWD R 0.569)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 70 (COMMENT F)\n"
          + "   (CHARWD R 0.487)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 71 (COMMENT G)\n"
          + "   (CHARWD R 0.693)\n" + "   (CHARHT R 0.658)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 72 (COMMENT H)\n"
          + "   (CHARWD R 0.700)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 73 (COMMENT I)\n"
          + "   (CHARWD R 0.293)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 74 (COMMENT J)\n"
          + "   (CHARWD R 0.315)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.198)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 75 (COMMENT K)\n"
          + "   (CHARWD R 0.637)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 76 (COMMENT L)\n"
          + "   (CHARWD R 0.526)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 77 (COMMENT M)\n"
          + "   (CHARWD R 0.839)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.006)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 78 (COMMENT N)\n"
          + "   (CHARWD R 0.695)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.008)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 79 (COMMENT O)\n"
          + "   (CHARWD R 0.698)\n" + "   (CHARHT R 0.658)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 80 (COMMENT P)\n"
          + "   (CHARWD R 0.515)\n" + "   (CHARHT R 0.654)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 81 (COMMENT Q)\n"
          + "   (CHARWD R 0.712)\n" + "   (CHARHT R 0.658)\n"
          + "   (CHARDP R 0.201)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 82 (COMMENT R)\n"
          + "   (CHARWD R 0.574)\n" + "   (CHARHT R 0.654)\n"
          + "   (CHARDP R 0.006)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 83 (COMMENT S)\n"
          + "   (CHARWD R 0.475)\n" + "   (CHARHT R 0.658)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 84 (COMMENT T)\n"
          + "   (CHARWD R 0.577)\n" + "   (CHARHT R 0.673)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 85 (COMMENT U)\n"
          + "   (CHARWD R 0.661)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 86 (COMMENT V)\n"
          + "   (CHARWD R 0.617)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 87 (COMMENT W)\n"
          + "   (CHARWD R 0.915)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 88 (COMMENT X)\n"
          + "   (CHARWD R 0.655)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 89 (COMMENT Y)\n"
          + "   (CHARWD R 0.574)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 90 (COMMENT Z)\n"
          + "   (CHARWD R 0.604)\n" + "   (CHARHT R 0.658)\n"
          + "   (CHARDP R 0.000)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 91 (COMMENT bracketleft)\n"
          + "   (CHARWD R 0.334)\n" + "   (CHARHT R 0.689)\n"
          + "   (CHARDP R 0.200)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 92 (COMMENT backslash)\n"
          + "   (CHARWD R 0.287)\n" + "   (CHARHT R 0.698)\n"
          + "   (CHARDP R 0.048)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 93 (COMMENT bracketright)\n"
          + "   (CHARWD R 0.334)\n" + "   (CHARHT R 0.689)\n"
          + "   (CHARDP R 0.200)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 94 (COMMENT asciicircum)\n"
          + "   (CHARWD R 0.518)\n" + "   (CHARHT R 0.650)\n"
          + "   (CHARDP R -0.347)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 95 (COMMENT underscore)\n"
          + "   (CHARWD R 0.486)\n" + "   (CHARHT R -0.110)\n"
          + "   (CHARDP R 0.154)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 30 (COMMENT grave)\n"
          + "   (CHARWD R 0.392)\n" + "   (CHARHT R 0.701)\n"
          + "   (CHARDP R -0.538)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 97 (COMMENT a)\n"
          + "   (CHARWD R 0.451)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 98 (COMMENT b)\n"
          + "   (CHARWD R 0.478)\n" + "   (CHARHT R 0.698)\n"
          + "   (CHARDP R 0.011)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 99 (COMMENT c)\n"
          + "   (CHARWD R 0.403)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 100 (COMMENT d)\n"
          + "   (CHARWD R 0.494)\n" + "   (CHARHT R 0.698)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 101 (COMMENT e)\n"
          + "   (CHARWD R 0.428)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 102 (COMMENT f)\n"
          + "   (CHARWD R 0.304)\n" + "   (CHARHT R 0.698)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 103 (COMMENT g)\n"
          + "   (CHARWD R 0.484)\n" + "   (CHARHT R 0.462)\n"
          + "   (CHARDP R 0.235)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 104 (COMMENT h)\n"
          + "   (CHARWD R 0.518)\n" + "   (CHARHT R 0.698)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 105 (COMMENT i)\n"
          + "   (CHARWD R 0.264)\n" + "   (CHARHT R 0.619)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 106 (COMMENT j)\n"
          + "   (CHARWD R 0.277)\n" + "   (CHARHT R 0.619)\n"
          + "   (CHARDP R 0.213)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 107 (COMMENT k)\n"
          + "   (CHARWD R 0.479)\n" + "   (CHARHT R 0.698)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 108 (COMMENT l)\n"
          + "   (CHARWD R 0.260)\n" + "   (CHARHT R 0.698)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 109 (COMMENT m)\n"
          + "   (CHARWD R 0.760)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 110 (COMMENT n)\n"
          + "   (CHARWD R 0.520)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 111 (COMMENT o)\n"
          + "   (CHARWD R 0.479)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 112 (COMMENT p)\n"
          + "   (CHARWD R 0.479)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.231)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 113 (COMMENT q)\n"
          + "   (CHARWD R 0.493)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.231)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 114 (COMMENT r)\n"
          + "   (CHARWD R 0.347)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 115 (COMMENT s)\n"
          + "   (CHARWD R 0.371)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 116 (COMMENT t)\n"
          + "   (CHARWD R 0.302)\n" + "   (CHARHT R 0.584)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 117 (COMMENT u)\n"
          + "   (CHARWD R 0.517)\n" + "   (CHARHT R 0.431)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 118 (COMMENT v)\n"
          + "   (CHARWD R 0.497)\n" + "   (CHARHT R 0.431)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 119 (COMMENT w)\n"
          + "   (CHARWD R 0.744)\n" + "   (CHARHT R 0.431)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 120 (COMMENT x)\n"
          + "   (CHARWD R 0.497)\n" + "   (CHARHT R 0.431)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 121 (COMMENT y)\n"
          + "   (CHARWD R 0.509)\n" + "   (CHARHT R 0.431)\n"
          + "   (CHARDP R 0.230)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 122 (COMMENT z)\n"
          + "   (CHARWD R 0.411)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.000)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 123 (COMMENT braceleft)\n"
          + "   (CHARWD R 0.273)\n" + "   (CHARHT R 0.711)\n"
          + "   (CHARDP R 0.217)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 124 (COMMENT bar)\n"
          + "   (CHARWD R 0.205)\n" + "   (CHARHT R 0.698)\n"
          + "   (CHARDP R 0.233)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 125 (COMMENT braceright)\n"
          + "   (CHARWD R 0.273)\n" + "   (CHARHT R 0.711)\n"
          + "   (CHARDP R 0.217)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 126 (COMMENT asciitilde)\n"
          + "   (CHARWD R 0.449)\n" + "   (CHARHT R 0.339)\n"
          + "   (CHARDP R -0.206)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 161 (COMMENT exclamdown)\n"
          + "   (CHARWD R 0.227)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.228)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 162 (COMMENT cent)\n"
          + "   (CHARWD R 0.438)\n" + "   (CHARHT R 0.584)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 163 (COMMENT sterling)\n"
          + "   (CHARWD R 0.438)\n" + "   (CHARHT R 0.575)\n"
          + "   (CHARDP R 0.017)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 164 (COMMENT currency)\n"
          + "   (CHARWD R 0.438)\n" + "   (CHARHT R 0.481)\n"
          + "   (CHARDP R -0.096)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 165 (COMMENT yen)\n"
          + "   (CHARWD R 0.438)\n" + "   (CHARHT R 0.575)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 166 (COMMENT brokenbar)\n"
          + "   (CHARWD R 0.205)\n" + "   (CHARHT R 0.674)\n"
          + "   (CHARDP R 0.201)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 167 (COMMENT section)\n"
          + "   (CHARWD R 0.466)\n" + "   (CHARHT R 0.658)\n"
          + "   (CHARDP R 0.169)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 168 (COMMENT dieresis)\n"
          + "   (CHARWD R 0.375)\n" + "   (CHARHT R 0.620)\n"
          + "   (CHARDP R -0.529)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 169 (COMMENT copyright)\n"
          + "   (CHARWD R 0.633)\n" + "   (CHARHT R 0.588)\n"
          + "   (CHARDP R -0.023)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 170 (COMMENT ordfeminine)\n"
          + "   (CHARWD R 0.297)\n" + "   (CHARHT R 0.637)\n"
          + "   (CHARDP R -0.358)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 171 (COMMENT guillemotleft)\n"
          + "   (CHARWD R 0.536)\n" + "   (CHARHT R 0.429)\n"
          + "   (CHARDP R -0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 172 (COMMENT logicalnot)\n"
          + "   (CHARWD R 0.560)\n" + "   (CHARHT R 0.401)\n"
          + "   (CHARDP R -0.133)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 174 (COMMENT registered)\n"
          + "   (CHARWD R 0.633)\n" + "   (CHARHT R 0.588)\n"
          + "   (CHARDP R -0.023)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 175 (COMMENT macron)\n"
          + "   (CHARWD R 0.397)\n" + "   (CHARHT R 0.573)\n"
          + "   (CHARDP R -0.530)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 176 (COMMENT degree)\n"
          + "   (CHARWD R 0.267)\n" + "   (CHARHT R 0.625)\n"
          + "   (CHARDP R -0.426)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 177 (COMMENT plusminus)\n"
          + "   (CHARWD R 0.527)\n" + "   (CHARHT R 0.515)\n"
          + "   (CHARDP R -0.018)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 180 (COMMENT acute)\n"
          + "   (CHARWD R 0.341)\n" + "   (CHARHT R 0.705)\n"
          + "   (CHARDP R -0.542)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 182 (COMMENT paragraph)\n"
          + "   (CHARWD R 0.602)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.234)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 183 (COMMENT periodcentered)\n"
          + "   (CHARWD R 0.250)\n" + "   (CHARHT R 0.313)\n"
          + "   (CHARDP R -0.204)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 184 (COMMENT cedilla)\n"
          + "   (CHARWD R 0.541)\n" + "   (CHARHT R 0.007)\n"
          + "   (CHARDP R 0.181)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 186 (COMMENT ordmasculine)\n"
          + "   (CHARWD R 0.349)\n" + "   (CHARHT R 0.566)\n"
          + "   (CHARDP R -0.303)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 187 (COMMENT guillemotright)\n"
          + "   (CHARWD R 0.536)\n" + "   (CHARHT R 0.429)\n"
          + "   (CHARDP R -0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 188 (COMMENT onequarter)\n"
          + "   (CHARWD R 0.660)\n" + "   (CHARHT R 0.639)\n"
          + "   (CHARDP R 0.082)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 189 (COMMENT onehalf)\n"
          + "   (CHARWD R 0.660)\n" + "   (CHARHT R 0.639)\n"
          + "   (CHARDP R 0.079)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 190 (COMMENT threequarters)\n"
          + "   (CHARWD R 0.660)\n" + "   (CHARHT R 0.637)\n"
          + "   (CHARDP R 0.079)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 191 (COMMENT questiondown)\n"
          + "   (CHARWD R 0.405)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.229)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 192 (COMMENT Agrave)\n"
          + "   (CHARWD R 0.695)\n" + "   (CHARHT R 0.819)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 193 (COMMENT Aacute)\n"
          + "   (CHARWD R 0.695)\n" + "   (CHARHT R 0.821)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 194 (COMMENT Acircumflex)\n"
          + "   (CHARWD R 0.695)\n" + "   (CHARHT R 0.805)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 195 (COMMENT Atilde)\n"
          + "   (CHARWD R 0.695)\n" + "   (CHARHT R 0.797)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 196 (COMMENT Adieresis)\n"
          + "   (CHARWD R 0.695)\n" + "   (CHARHT R 0.794)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 197 (COMMENT Aring)\n"
          + "   (CHARWD R 0.695)\n" + "   (CHARHT R 0.797)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 198 (COMMENT AE)\n"
          + "   (CHARWD R 0.865)\n" + "   (CHARHT R 0.648)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 199 (COMMENT Ccedilla)\n"
          + "   (CHARWD R 0.644)\n" + "   (CHARHT R 0.658)\n"
          + "   (CHARDP R 0.186)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 200 (COMMENT Egrave)\n"
          + "   (CHARWD R 0.569)\n" + "   (CHARHT R 0.818)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 201 (COMMENT Eacute)\n"
          + "   (CHARWD R 0.569)\n" + "   (CHARHT R 0.821)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 202 (COMMENT Ecircumflex)\n"
          + "   (CHARWD R 0.569)\n" + "   (CHARHT R 0.805)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 203 (COMMENT Edieresis)\n"
          + "   (CHARWD R 0.569)\n" + "   (CHARHT R 0.796)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 204 (COMMENT Igrave)\n"
          + "   (CHARWD R 0.293)\n" + "   (CHARHT R 0.818)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 205 (COMMENT Iacute)\n"
          + "   (CHARWD R 0.293)\n" + "   (CHARHT R 0.820)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 206 (COMMENT Icircumflex)\n"
          + "   (CHARWD R 0.293)\n" + "   (CHARHT R 0.805)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 207 (COMMENT Idieresis)\n"
          + "   (CHARWD R 0.293)\n" + "   (CHARHT R 0.794)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 208 (COMMENT Eth)\n"
          + "   (CHARWD R 0.697)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 209 (COMMENT Ntilde)\n"
          + "   (CHARWD R 0.695)\n" + "   (CHARHT R 0.794)\n"
          + "   (CHARDP R 0.008)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 210 (COMMENT Ograve)\n"
          + "   (CHARWD R 0.698)\n" + "   (CHARHT R 0.819)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 211 (COMMENT Oacute)\n"
          + "   (CHARWD R 0.698)\n" + "   (CHARHT R 0.821)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 212 (COMMENT Ocircumflex)\n"
          + "   (CHARWD R 0.698)\n" + "   (CHARHT R 0.805)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 213 (COMMENT Otilde)\n"
          + "   (CHARWD R 0.698)\n" + "   (CHARHT R 0.794)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 214 (COMMENT Odieresis)\n"
          + "   (CHARWD R 0.698)\n" + "   (CHARHT R 0.796)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 215 (COMMENT multiply)\n"
          + "   (CHARWD R 0.527)\n" + "   (CHARHT R 0.388)\n"
          + "   (CHARDP R -0.073)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 216 (COMMENT Oslash)\n"
          + "   (CHARWD R 0.698)\n" + "   (CHARHT R 0.670)\n"
          + "   (CHARDP R 0.040)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 217 (COMMENT Ugrave)\n"
          + "   (CHARWD R 0.661)\n" + "   (CHARHT R 0.819)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 218 (COMMENT Uacute)\n"
          + "   (CHARWD R 0.661)\n" + "   (CHARHT R 0.820)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 219 (COMMENT Ucircumflex)\n"
          + "   (CHARWD R 0.661)\n" + "   (CHARHT R 0.805)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 220 (COMMENT Udieresis)\n"
          + "   (CHARWD R 0.661)\n" + "   (CHARHT R 0.796)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 221 (COMMENT Yacute)\n"
          + "   (CHARWD R 0.574)\n" + "   (CHARHT R 0.821)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 222 (COMMENT Thorn)\n"
          + "   (CHARWD R 0.523)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 223 (COMMENT germandbls)\n"
          + "   (CHARWD R 0.553)\n" + "   (CHARHT R 0.698)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 224 (COMMENT agrave)\n"
          + "   (CHARWD R 0.451)\n" + "   (CHARHT R 0.673)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 225 (COMMENT aacute)\n"
          + "   (CHARWD R 0.451)\n" + "   (CHARHT R 0.673)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 226 (COMMENT acircumflex)\n"
          + "   (CHARWD R 0.451)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 227 (COMMENT atilde)\n"
          + "   (CHARWD R 0.451)\n" + "   (CHARHT R 0.622)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 228 (COMMENT adieresis)\n"
          + "   (CHARWD R 0.451)\n" + "   (CHARHT R 0.618)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 229 (COMMENT aring)\n"
          + "   (CHARWD R 0.451)\n" + "   (CHARHT R 0.666)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 230 (COMMENT ae)\n"
          + "   (CHARWD R 0.687)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 231 (COMMENT ccedilla)\n"
          + "   (CHARWD R 0.403)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.179)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 232 (COMMENT egrave)\n"
          + "   (CHARWD R 0.428)\n" + "   (CHARHT R 0.673)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 233 (COMMENT eacute)\n"
          + "   (CHARWD R 0.428)\n" + "   (CHARHT R 0.673)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 234 (COMMENT ecircumflex)\n"
          + "   (CHARWD R 0.428)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 235 (COMMENT edieresis)\n"
          + "   (CHARWD R 0.428)\n" + "   (CHARHT R 0.618)\n"
          + "   (CHARDP R 0.008)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 236 (COMMENT igrave)\n"
          + "   (CHARWD R 0.264)\n" + "   (CHARHT R 0.673)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 237 (COMMENT iacute)\n"
          + "   (CHARWD R 0.264)\n" + "   (CHARHT R 0.673)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 238 (COMMENT icircumflex)\n"
          + "   (CHARWD R 0.264)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 239 (COMMENT idieresis)\n"
          + "   (CHARWD R 0.264)\n" + "   (CHARHT R 0.618)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 240 (COMMENT eth)\n"
          + "   (CHARWD R 0.478)\n" + "   (CHARHT R 0.704)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 241 (COMMENT ntilde)\n"
          + "   (CHARWD R 0.520)\n" + "   (CHARHT R 0.622)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 242 (COMMENT ograve)\n"
          + "   (CHARWD R 0.479)\n" + "   (CHARHT R 0.673)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 243 (COMMENT oacute)\n"
          + "   (CHARWD R 0.479)\n" + "   (CHARHT R 0.673)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 244 (COMMENT ocircumflex)\n"
          + "   (CHARWD R 0.479)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 245 (COMMENT otilde)\n"
          + "   (CHARWD R 0.479)\n" + "   (CHARHT R 0.622)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 246 (COMMENT odieresis)\n"
          + "   (CHARWD R 0.479)\n" + "   (CHARHT R 0.618)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 247 (COMMENT divide)\n"
          + "   (CHARWD R 0.527)\n" + "   (CHARHT R 0.409)\n"
          + "   (CHARDP R -0.051)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 248 (COMMENT oslash)\n"
          + "   (CHARWD R 0.479)\n" + "   (CHARHT R 0.454)\n"
          + "   (CHARDP R 0.028)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 249 (COMMENT ugrave)\n"
          + "   (CHARWD R 0.517)\n" + "   (CHARHT R 0.673)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 250 (COMMENT uacute)\n"
          + "   (CHARWD R 0.517)\n" + "   (CHARHT R 0.673)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 251 (COMMENT ucircumflex)\n"
          + "   (CHARWD R 0.517)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 252 (COMMENT udieresis)\n"
          + "   (CHARWD R 0.517)\n" + "   (CHARHT R 0.618)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 253 (COMMENT yacute)\n"
          + "   (CHARWD R 0.509)\n" + "   (CHARHT R 0.673)\n"
          + "   (CHARDP R 0.230)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 254 (COMMENT thorn)\n"
          + "   (CHARWD R 0.496)\n" + "   (CHARHT R 0.698)\n"
          + "   (CHARDP R 0.231)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 255 (COMMENT ydieresis)\n"
          + "   (CHARWD R 0.509)\n" + "   (CHARHT R 0.618)\n"
          + "   (CHARDP R 0.230)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 17 (COMMENT dotlessi)\n"
          + "   (CHARWD R 0.264)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 6 (COMMENT Lslash)\n"
          + "   (CHARWD R 0.526)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 7 (COMMENT lslash)\n"
          + "   (CHARWD R 0.260)\n" + "   (CHARHT R 0.698)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 140 (COMMENT OE)\n"
          + "   (CHARWD R 0.849)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.000)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 156 (COMMENT oe)\n"
          + "   (CHARWD R 0.775)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 138 (COMMENT Scaron)\n"
          + "   (CHARWD R 0.475)\n" + "   (CHARHT R 0.812)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 154 (COMMENT scaron)\n"
          + "   (CHARWD R 0.371)\n" + "   (CHARHT R 0.646)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 159 (COMMENT Ydieresis)\n"
          + "   (CHARWD R 0.574)\n" + "   (CHARHT R 0.796)\n"
          + "   (CHARDP R 0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 14 (COMMENT Zcaron)\n"
          + "   (CHARWD R 0.604)\n" + "   (CHARHT R 0.813)\n"
          + "   (CHARDP R 0.000)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 15 (COMMENT zcaron)\n"
          + "   (CHARWD R 0.411)\n" + "   (CHARHT R 0.645)\n"
          + "   (CHARDP R 0.000)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 131 (COMMENT florin)\n"
          + "   (CHARWD R 0.450)\n" + "   (CHARHT R 0.698)\n"
          + "   (CHARDP R 0.172)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 136 (COMMENT circumflex)\n"
          + "   (CHARWD R 0.349)\n" + "   (CHARHT R 0.668)\n"
          + "   (CHARDP R -0.525)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 16 (COMMENT caron)\n"
          + "   (CHARWD R 0.349)\n" + "   (CHARHT R 0.662)\n"
          + "   (CHARDP R -0.519)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 11 (COMMENT breve)\n"
          + "   (CHARWD R 0.317)\n" + "   (CHARHT R 0.678)\n"
          + "   (CHARDP R -0.529)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 1 (COMMENT dotaccent)\n"
          + "   (CHARWD R 0.291)\n" + "   (CHARHT R 0.688)\n"
          + "   (CHARDP R -0.592)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 9 (COMMENT ring)\n"
          + "   (CHARWD R 0.361)\n" + "   (CHARHT R 0.754)\n"
          + "   (CHARDP R -0.588)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 8 (COMMENT ogonek)\n"
          + "   (CHARWD R 0.334)\n" + "   (CHARHT R 0.039)\n"
          + "   (CHARDP R 0.223)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 152 (COMMENT tilde)\n"
          + "   (CHARWD R 0.292)\n" + "   (CHARHT R 0.670)\n"
          + "   (CHARDP R -0.574)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 5 (COMMENT hungarumlaut)\n"
          + "   (CHARWD R 0.350)\n" + "   (CHARHT R 0.733)\n"
          + "   (CHARDP R -0.557)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 157 (COMMENT Delta)\n"
          + "   (CHARWD R 0.636)\n" + "   (CHARHT R 0.651)\n"
          + "   (CHARDP R 0.002)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 141 (COMMENT Omega)\n"
          + "   (CHARWD R 0.693)\n" + "   (CHARHT R 0.658)\n"
          + "   (CHARDP R 0.000)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 181 (COMMENT mu)\n"
          + "   (CHARWD R 0.485)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.234)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 29 (COMMENT pi)\n"
          + "   (CHARWD R 0.574)\n" + "   (CHARHT R 0.440)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 150 (COMMENT endash)\n"
          + "   (CHARWD R 0.534)\n" + "   (CHARHT R 0.287)\n"
          + "   (CHARDP R -0.238)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 151 (COMMENT emdash)\n"
          + "   (CHARWD R 0.742)\n" + "   (CHARHT R 0.287)\n"
          + "   (CHARDP R -0.238)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 96 (COMMENT quoteleft)\n"
          + "   (CHARWD R 0.268)\n" + "   (CHARHT R 0.720)\n"
          + "   (CHARDP R -0.507)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 39 (COMMENT quoteright)\n"
          + "   (CHARWD R 0.268)\n" + "   (CHARHT R 0.698)\n"
          + "   (CHARDP R -0.485)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 130 (COMMENT quotesinglbase)\n"
          + "   (CHARWD R 0.268)\n" + "   (CHARHT R 0.095)\n"
          + "   (CHARDP R 0.118)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 147 (COMMENT quotedblleft)\n"
          + "   (CHARWD R 0.375)\n" + "   (CHARHT R 0.721)\n"
          + "   (CHARDP R -0.508)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 148 (COMMENT quotedblright)\n"
          + "   (CHARWD R 0.375)\n" + "   (CHARHT R 0.698)\n"
          + "   (CHARDP R -0.485)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 132 (COMMENT quotedblbase)\n"
          + "   (CHARWD R 0.375)\n" + "   (CHARHT R 0.094)\n"
          + "   (CHARDP R 0.119)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 134 (COMMENT dagger)\n"
          + "   (CHARWD R 0.588)\n" + "   (CHARHT R 0.697)\n"
          + "   (CHARDP R 0.207)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 135 (COMMENT daggerdbl)\n"
          + "   (CHARWD R 0.588)\n" + "   (CHARHT R 0.697)\n"
          + "   (CHARDP R 0.207)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 149 (COMMENT bullet)\n"
          + "   (CHARWD R 0.351)\n" + "   (CHARHT R 0.359)\n"
          + "   (CHARDP R -0.105)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 133 (COMMENT ellipsis)\n"
          + "   (CHARWD R 0.749)\n" + "   (CHARHT R 0.094)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 137 (COMMENT perthousand)\n"
          + "   (CHARWD R 0.911)\n" + "   (CHARHT R 0.569)\n"
          + "   (CHARDP R 0.019)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 139 (COMMENT guilsinglleft)\n"
          + "   (CHARWD R 0.355)\n" + "   (CHARHT R 0.430)\n"
          + "   (CHARDP R -0.001)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 155 (COMMENT guilsinglright)\n"
          + "   (CHARWD R 0.355)\n" + "   (CHARHT R 0.429)\n"
          + "   (CHARDP R 0.000)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 4 (COMMENT fraction)\n"
          + "   (CHARWD R 0.044)\n" + "   (CHARHT R 0.601)\n"
          + "   (CHARDP R 0.054)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 128 (COMMENT Euro)\n"
          + "   (CHARWD R 0.438)\n" + "   (CHARHT R 0.585)\n"
          + "   (CHARDP R 0.012)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 153 (COMMENT trademark)\n"
          + "   (CHARWD R 0.795)\n" + "   (CHARHT R 0.671)\n"
          + "   (CHARDP R -0.288)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 26 (COMMENT partialdiff)\n"
          + "   (CHARWD R 0.462)\n" + "   (CHARHT R 0.616)\n"
          + "   (CHARDP R 0.011)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 28 (COMMENT product)\n"
          + "   (CHARWD R 0.696)\n" + "   (CHARHT R 0.587)\n"
          + "   (CHARDP R 0.110)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 27 (COMMENT summation)\n"
          + "   (CHARWD R 0.592)\n" + "   (CHARHT R 0.583)\n"
          + "   (CHARDP R 0.062)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 12 (COMMENT minus)\n"
          + "   (CHARWD R 0.527)\n" + "   (CHARHT R 0.256)\n"
          + "   (CHARDP R -0.207)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 142 (COMMENT radical)\n"
          + "   (CHARWD R 0.565)\n" + "   (CHARHT R 0.772)\n"
          + "   (CHARDP R 0.018)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 23 (COMMENT infinity)\n"
          + "   (CHARWD R 0.785)\n" + "   (CHARHT R 0.436)\n"
          + "   (CHARDP R -0.078)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 129 (COMMENT integral)\n"
          + "   (CHARWD R 0.379)\n" + "   (CHARHT R 0.777)\n"
          + "   (CHARDP R 0.173)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 143 (COMMENT approxequal)\n"
          + "   (CHARWD R 0.527)\n" + "   (CHARHT R 0.359)\n"
          + "   (CHARDP R -0.079)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 22 (COMMENT notequal)\n"
          + "   (CHARWD R 0.527)\n" + "   (CHARHT R 0.567)\n"
          + "   (CHARDP R 0.053)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 24 (COMMENT lessequal)\n"
          + "   (CHARWD R 0.527)\n" + "   (CHARHT R 0.417)\n"
          + "   (CHARDP R -0.011)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 25 (COMMENT greaterequal)\n"
          + "   (CHARWD R 0.527)\n" + "   (CHARHT R 0.417)\n"
          + "   (CHARDP R -0.011)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 158 (COMMENT lozenge)\n"
          + "   (CHARWD R 0.667)\n" + "   (CHARHT R 0.554)\n"
          + "   (CHARDP R 0.137)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "(CHARACTER D 18 (COMMENT dotlessj)\n"
          + "   (CHARWD R 0.277)\n" + "   (CHARHT R 0.442)\n"
          + "   (CHARDP R 0.213)\n" + "   (CHARIC R 0.000)\n"
          + "   )\n" + "\n" + "(COMMENT END OF FILE fxlr8r.pl)\n";


  public FontInst03Test() {

    setConfig( "fontinst-test.xml" );

    // delete temp files after the test
    new File( "texput.log" ).deleteOnExit();
    new File( "fxlr.mtx" ).deleteOnExit();
    new File( "fxlr.pl" ).deleteOnExit();
    new File( "fxlr8r.mtx" ).deleteOnExit();
    new File( "fxlr8r.pl" ).deleteOnExit();
  }

  /**
   * Returns the properties for the test case.
   *
   * @return Returns the properties for the test case.
   */
  private Properties getMyProps() {

    Properties props = getProps();
    props.setProperty( "extex.texinputs",
                       "../ExTeX-fontware/src/texmf/tex/fontinst/base" + SEP +
                           "../ExTeX-fontware/src/texmf/tex/fontinst/latinetx"
                           + SEP +
                           "../ExTeX-fontware/src/texmf/tex/fontinst/latinmtx"
                           + SEP +
                           "../ExTeX-fontware/src/texmf/tex/fontinst/mathetx" + SEP +
                           "../ExTeX-fontware/src/texmf/tex/fontinst/mathmtx" + SEP +
                           "../ExTeX-fontware/src/texmf/tex/fontinst/misc" + SEP +
                           "../ExTeX-fontware/src/texmf/tex/fontinst/smbletx" + SEP +
                           "../ExTeX-fontware/src/texmf/tex/fontinst/smblmtx" + SEP +
                           "../texmf/src/texmf/fonts/afm/" + SEP +
                           "../texmf/src/texmf/fonts/enc/" + SEP +
                           "../ExTeX-fontware/src/texmf/tex/misc"
    );
    // props.setProperty("extex.launcher.trace", "true");
    // props.setProperty("extex.launcher.time", "true");
    return props;
  }

  /**
   * Test for fontinst misc.
   *
   * <pre>
   * \input fontinst.sty
   * \transformfont{fxlr8r}{\reencodefont{8r}{\fromafm{fxlr}}}
   * \bye
   * </pre>
   *
   * @throws Exception if an error occurred.
   */
  @Test
  @Ignore("FIXME")
  public void testTransformTestFXLR8RMTX() throws Exception {

    setConfig( "tex" );
    assertOutput(
        getMyProps(), // --- input code ---
        "\\input fontinst.sty "
            + "\\transformfont{fxlr8r}{\\reencodefont{8r}{\\fromafm{fxlr}}} "
            + "\\bye",
        // --- log channel ---
        "No file fontinst.rc.\n"
            + "Metrics written on fxlr.mtx.\n"
            + "Raw font written on fxlr.pl.\n" // +
            + "Transformed metrics written on fxlr8r.mtx.\n"
            + "Raw font written on fxlr8r.pl.\n",
        // --- output channel ---
        TERM );

    // check the output file.
    File mtx = new File( "fxlr8r.mtx" );
    File mtxorg =
        new File(
            "../ExTeX-fontware/src/test/resources/fxlr8r.mtx.tex.org" );

    assertTrue( mtx.canRead() );
    assertTrue( mtxorg.canRead() );

    BufferedReader in = new BufferedReader( new FileReader( mtx ) );
    BufferedReader inorg = new BufferedReader( new FileReader( mtxorg ) );

    String line, lineorg;
    while( (lineorg = inorg.readLine()) != null ) {
      line = in.readLine();

      assertEquals( "generated mtx file", lineorg, line );
    }
    in.close();
    inorg.close();
  }

  /**
   * Test for fontinst misc.
   *
   * <pre>
   * \input fontinst.sty
   * \transformfont{fxlr8r}{\reencodefont{8r}{\fromafm{fxlr}}}
   * \bye
   * </pre>
   *
   * @throws Exception if an error occurred.
   */
  @Test
  @Ignore("FIXME")
  public void testTransformTestFXLR8RPL() throws Exception {

    setConfig( "tex" );
    assertOutput(
        getMyProps(), // --- input code ---
        "\\input fontinst.sty "
            + "\\transformfont{fxlr8r}{\\reencodefont{8r}{\\fromafm{fxlr}}} "
            + "\\bye",
        // --- log channel ---
        "No file fontinst.rc.\n"
            + "Metrics written on fxlr.mtx.\n"
            + "Raw font written on fxlr.pl.\n" // +
            + "Transformed metrics written on fxlr8r.mtx.\n"
            + "Raw font written on fxlr8r.pl.\n",
        // --- output channel ---
        TERM );

    // check the output file.
    File pl = new File( "fxlr8r.pl" );
    FileInputStream stream = new FileInputStream( pl );
    assertNotNull( stream );
    StringBuilder sb = new StringBuilder();
    for( int c = stream.read(); c >= 0; c = stream.read() ) {
      sb.append( (char) c );
    }
    stream.close();
    assertEquals( "generated file", TEXT_FXLR8R_PL, sb.toString() );

  }

  /**
   * Test for fontinst misc.
   *
   * <pre>
   * \input fontinst.sty
   * \transformfont{fxlr8r}{\reencodefont{8r}{\fromafm{fxlr}}}
   * \bye
   * </pre>
   *
   * @throws Exception if an error occurred.
   */
  @Test
  @Ignore("FIXME")
  public void testTransformTestFXLRMTX() throws Exception {

    setConfig( "tex" );
    assertOutput(
        getMyProps(), // --- input code ---
        "\\input fontinst.sty "
            + "\\transformfont{fxlr8r}{\\reencodefont{8r}{\\fromafm{fxlr}}} "
            + "\\bye",
        // --- log channel ---
        "No file fontinst.rc.\n"
            + "Metrics written on fxlr.mtx.\n"
            + "Raw font written on fxlr.pl.\n" // +
            + "Transformed metrics written on fxlr8r.mtx.\n"
            + "Raw font written on fxlr8r.pl.\n",
        // --- output channel ---
        TERM );

    // check the output file.
    File mtx = new File( "fxlr.mtx" );
    File mtxorg =
        new File(
            "../ExTeX-fontware/src/test/resources/fxlr.mtx.tex.org" );

    assertTrue( mtx.canRead() );
    assertTrue( mtxorg.canRead() );

    BufferedReader in = new BufferedReader( new FileReader( mtx ) );
    BufferedReader inorg = new BufferedReader( new FileReader( mtxorg ) );

    String line, lineorg;
    while( (lineorg = inorg.readLine()) != null ) {
      line = in.readLine();

      assertEquals( "generated mtx file", lineorg, line );
    }
    in.close();
    inorg.close();
  }
}
