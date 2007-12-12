<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   
   <!-- output -->
   <xsl:output method="text" indent="no" encoding="ISO8859-1"/>
   
   <!-- root -->
   <xsl:template match="/">
      <xsl:apply-templates select="//Lookup[@index=16]"/>
   </xsl:template>
   
   <xsl:template match="Lookup[@index=16]">
      <xsl:apply-templates select="SingleSubst/Substitution"/>
   </xsl:template>

   <xsl:template match="Substitution">
    /**
     * test <xsl:value-of select="@in" />.
     */
     @Test
    public void testGSUBSingle_<xsl:value-of select="translate(@in,'.','')" />()  {
        check("zero","<xsl:value-of select="@in" />","<xsl:value-of select="@out" />");
    }

   </xsl:template>
   
</xsl:stylesheet>

