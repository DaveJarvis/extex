<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- ====================================================================== -->
<!--  $Id: xml2tex.xsl,v 0.00 2007/02/26 16:47:20 gene Exp $                -->
<!-- ====================================================================== -->

<xsl:stylesheet version="1.0"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output encoding="iso-8859-1" method="text"/>

  <xsl:template match="/"><xsl:apply-templates select="*"/></xsl:template>

  <xsl:template match="h3">\section{<xsl:apply-templates select="*"/>}</xsl:template>
  <xsl:template match="h4">\subsection{<xsl:apply-templates select="*"/>}</xsl:template>
  <xsl:template match="strong">\emph{<xsl:apply-templates select="*"/>}</xsl:template>
  <xsl:template match="em">\emph{<xsl:apply-templates select="*"/>}</xsl:template>
  <xsl:template match="b">\emph{<xsl:apply-templates select="*"/>}</xsl:template>
  <xsl:template match="i">\\textit{<xsl:apply-templates select="*"/>}</xsl:template>
  <xsl:template match="p"><xsl:apply-templates select="*"/>\par </xsl:template>
  <xsl:template match="tt">\\texttt{<xsl:apply-templates select="*"/>}</xsl:template>
  <xsl:template match="code">\\texttt{<xsl:apply-templates select="*"/>}</xsl:template>

  <xsl:template match="text()"><xsl:copy-of select="."/></xsl:template>
  <xsl:template match="*"><xsl:copy-of select="."/></xsl:template>

</xsl:stylesheet>

