<?xml version="1.0" encoding="UTF-8"?>
<!--
  ############################################################################
  # Copyright (C) 2006-2007 The ExTeX Group
  #
  # This library is free software; you can redistribute it and/or modify it
  # under the terms of the GNU Lesser General Public License as published by
  # the Free Software Foundation; either version 2.1 of the License, or (at
  # your option) any later version.
  #
  # This library is distributed in the hope that it will be useful, but
  # WITHOUT ANY WARRANTY; without even the implied warranty of
  # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
  # General Public License for more details.
  #
  # You should have received a copy of the GNU Lesser General Public License
  # along with this library; if not, write to the Free Software Foundation,
  # Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
  #
  #===========================================================================
  # @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
  ######################################################################### -->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:xalan="http://xml.apache.org/xslt"
                version="1.0">

<xsl:output method="xml"
            indent="yes"
            xalan:indent-amount="1"
            encoding="UTF-8"/>

 <!-- ===================================================================== -->
<xsl:template match="application">
<project>

  <path id="application.class.path">
    <xsl:apply-templates select="component" mode="classpath"/>
  </path>

  <fileset dir=".." id="application.resource.files">
    <xsl:apply-templates select="component" mode="resource"/>
  </fileset>

  <fileset dir=".." id="application.libraries">
    <xsl:apply-templates select="component" mode="libraries"/>
  </fileset>

  <path id="application.source.path">
    <dirset dir="..">
      <xsl:apply-templates select="component" mode="source"/>
    </dirset>
  </path>

  <fileset dir=".."
           id="application.source.files">
    <xsl:apply-templates select="component" mode="sourceFiles"/>
  </fileset>

  <path id="test.class.path">
    <fileset dir="..">
      <!-- for testing -->
      <include name="ExTeX-Backend-dump/lib/*.jar"/>
      <include name="ExTeX-Backend-dump/lib.test/*.jar"/>
      <xsl:apply-templates select="component" mode="testpath"/>
    </fileset>
  </path>

  <path id="test.source.path">
    <dirset dir="..">
      <xsl:apply-templates select="component" mode="test"/>
    </dirset>
  </path>

  <fileset id="test.source.files" dir="..">
    <xsl:apply-templates select="component" mode="testfiles"/>
  </fileset>

</project>
</xsl:template>


 <!-- ===================================================================== -->
<xsl:template match="component" mode="classpath">
  <fileset dir="../{@name}">
    <include name="lib/*.jar" />
  </fileset>
</xsl:template>

 <!-- ===================================================================== -->
<xsl:template match="component" mode="libraries">
  <include name="{@name}/lib/*.jar"/>
</xsl:template>

 <!-- ===================================================================== -->
<xsl:template match="component" mode="resource">
  <include name="{@name}/src/java/**/*.properties"/>
  <include name="{@name}/src/java/**/*.xml"/>
  <include name="{@name}/src/java/**/*.txt"/>
  <include name="{@name}/src/resources/**/*.properties"/>
  <include name="{@name}/src/resources/**/*.xml"/>
</xsl:template>

 <!-- ===================================================================== -->
<xsl:template match="component" mode="source">
  <include name="{@name}/src/java"/>
</xsl:template>

 <!-- ===================================================================== -->
<xsl:template match="component" mode="sourceFiles">
  <include name="{@name}/src/java/**/*.java"/>
</xsl:template>

 <!-- ===================================================================== -->
<xsl:template match="component" mode="test">
  <include name="{@name}/src/test"/>
</xsl:template>

 <!-- ===================================================================== -->
<xsl:template match="component" mode="testfiles">
  <include name="{@name}/src/test/**/*Test.java"/>
</xsl:template>

 <!-- ===================================================================== -->
<xsl:template match="component" mode="testpath">
  <include name="{@name}/lib/*.jar"/>
  <include name="{@name}/lib.test/*.jar"/>
</xsl:template>

</xsl:stylesheet>
