<?xml version="1.0" encoding="ISO-8859-1"?>
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
