<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   
   <!-- output -->
   <xsl:output method="text" indent="no" encoding="ISO8859-1"/>
   
   <!-- root -->
   <xsl:template match="/">
      <xsl:apply-templates select="//Lookup[@index=17]"/>
   </xsl:template>
   
   <xsl:template match="Lookup[@index=17]">
      <xsl:apply-templates select="AlternateSubst/AlternateSet"/>
   </xsl:template>

   <xsl:template match="AlternateSet">
    /**
     * test <xsl:value-of select="@glyph" />.
     */
     @Test
    public void testGSUBAlternate_aalt_<xsl:value-of select="translate(@glyph,'.','')" />()  {
        check("aalt","<xsl:value-of select="@glyph" />", new String[]{ <xsl:apply-templates select="Alternate"/> null  });
    }

   </xsl:template>

   <xsl:template match="Alternate">
      "<xsl:value-of select="@glyph"/>",
   </xsl:template>
   
</xsl:stylesheet>

