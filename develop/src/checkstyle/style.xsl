<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- ====================================================================== -->
<!--  $Id$                -->
<!-- ====================================================================== -->

<xsl:stylesheet version="1.0"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output encoding="iso-8859-1"
	      method="html"/>

  <xsl:template match="checkstyle">
   <xsl:apply-templates select="*"/>
  </xsl:template>
  <xsl:template match="file">
   <h1><xsl:value-of select="@name"/></h1>
   <xsl:apply-templates select="*"/>
  </xsl:template>
  <xsl:template match="error">
    <xsl:value-of select="@message"/>
  </xsl:template>

</xsl:stylesheet>

