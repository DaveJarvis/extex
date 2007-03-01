<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- ====================================================================== -->
<!--  $Id: xml2tex.xsl,v 0.00 2007/02/26 16:47:20 gene Exp $                -->
<!-- ====================================================================== -->

<xsl:stylesheet version="1.0"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output encoding="iso-8859-1" method="text"/>

  <xsl:template match="h3">\section{<xsl:apply-templates/>}</xsl:template>
  <xsl:template match="h4">\subsection{<xsl:apply-templates/>}</xsl:template>
  <xsl:template match="h5">\subsubsection{<xsl:apply-templates/>}</xsl:template>
  <xsl:template match="h6">\paragraph{<xsl:apply-templates/>}</xsl:template>
  <xsl:template match="h7">\subparagraph{<xsl:apply-templates/>}</xsl:template>
  <xsl:template match="strong">\emph{<xsl:apply-templates/>}</xsl:template>
  <xsl:template match="em">\emph{<xsl:apply-templates/>}</xsl:template>
  <xsl:template match="b">\emph{<xsl:apply-templates/>}</xsl:template>
  <xsl:template match="i">\textit{<xsl:apply-templates/>}</xsl:template>
  <xsl:template match="p"><xsl:apply-templates/>\par </xsl:template>
  <xsl:template match="br">\\\relax </xsl:template>
  <xsl:template match="tt">\texttt{<xsl:apply-templates/>}</xsl:template>
  <xsl:template match="code">\texttt{<xsl:apply-templates/>}</xsl:template>
  <xsl:template match="kbd">\textsf{<xsl:apply-templates/>}</xsl:template>

  <xsl:template match="dl">\begin{description}<xsl:apply-templates/>\end{description}</xsl:template>
  <xsl:template match="dt">\item[<xsl:apply-templates/>] </xsl:template>
  <xsl:template match="dd"><xsl:apply-templates/> </xsl:template>
  <xsl:template match="ol">\begin{enumerate}<xsl:apply-templates/>\end{enumerate}</xsl:template>
  <xsl:template match="ul">\begin{itemize}<xsl:apply-templates/>\end{itemize}</xsl:template>
  <xsl:template match="li">\item <xsl:apply-templates/></xsl:template>

  <xsl:template match="pre[@class='JavaSample']">\begin{listing}{language=Java}<xsl:apply-templates/>\end{listing}</xsl:template>
  <xsl:template match="pre[@class='TeXSample']">\begin{listing}{language=TeX}<xsl:apply-templates/>\end{listing}</xsl:template>
  <xsl:template match="pre[@class='Configuration']">\begin{listing}{language=XML}<xsl:apply-templates/>\end{listing}</xsl:template>
  <xsl:template match="pre[@class='syntax']">\begin{listing}{language=syntax}<xsl:apply-templates/>\end{listing}</xsl:template>
  <xsl:template match="pre">\begin{verbatim}<xsl:apply-templates/>\end{verbatim}</xsl:template>

  <xsl:template match="table">\begin{table}{<xsl:copy-of select="@format"
                            />}<xsl:apply-templates/>\end{table}</xsl:template>
  <xsl:template match="tr"><xsl:apply-templates/>\\</xsl:template>
  <xsl:template match="td"><xsl:apply-templates/>&amp;</xsl:template>

  <xsl:template match="sub">\ensuremath{_{<xsl:apply-templates/>}}</xsl:template>
  <xsl:template match="sup">\ensuremath{^{<xsl:apply-templates/>}}</xsl:template>


  <xsl:template match="a"><xsl:apply-templates/></xsl:template>
  <xsl:template match="img">\\includegraphics{}{<xsl:value-of select="@href"/>}</xsl:template>


  <xsl:template match="logo">\<xsl:copy-of select="."/>{}</xsl:template>
  
  <xsl:template match="/"><xsl:apply-templates/></xsl:template>
  <xsl:template match="text()|@*"><xsl:copy-of select="."/></xsl:template>

</xsl:stylesheet>

