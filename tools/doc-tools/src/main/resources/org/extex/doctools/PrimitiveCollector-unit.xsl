<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- ====================================================================== -->
<xsl:stylesheet version="1.0"
                xmlns:pom="http://maven.apache.org/POM/4.0.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output encoding="iso-8859-1" method="html"/>

  <!-- ===================================================================== -->
  <xsl:template match="/units">%%
\begin{units}<xsl:apply-templates select="unit/unit"/>\end{units}
%%
</xsl:template>

  <!-- ===================================================================== -->
  <xsl:template match="unit">
  \begin{unit}{<xsl:value-of select="@name"
   />}<xsl:apply-templates select="primitives/define"/>
  \end{unit}
</xsl:template>

  <!-- ===================================================================== -->
  <xsl:template match="define">
    \define{<xsl:value-of select="@name"
   />}{<xsl:value-of select="@class"
   />}</xsl:template>

</xsl:stylesheet>
