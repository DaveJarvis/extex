<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- 
 Copyright (C) 2008 The ExTeX Group and individual authors listed below

 This library is free software; you can redistribute it and/or modify it
 under the terms of the GNU Lesser General Public License as published by the
 Free Software Foundation; either version 2.1 of the License, or (at your
 option) any later version.

 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation,
 Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

*****************************************************************************
 Author: Gerd Neugebauer
 -->
<!-- ====================================================================== -->
<xsl:stylesheet version="1.0"
                xmlns:pom="http://maven.apache.org/POM/4.0.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output encoding="iso-8859-1" method="html"/>

  <!-- ===================================================================== -->
  <xsl:template match="/configs">%%
\begin{configurations}<xsl:apply-templates select="config"/>\end{configurations}
%%
</xsl:template>

  <!-- ===================================================================== -->
  <xsl:template match="config">
  \begin{configuration}{<xsl:value-of select="@name"
   />}{<xsl:value-of select="ExTeX/banner"
   />}<xsl:apply-templates select="ExTeX/*/unit"/>
  \end{configuration}</xsl:template>

  <!-- ===================================================================== -->
  <xsl:template match="unit">
    \unit{<xsl:value-of select="@src"/>}</xsl:template>

</xsl:stylesheet>
