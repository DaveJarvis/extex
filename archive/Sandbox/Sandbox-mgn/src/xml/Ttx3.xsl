<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   
   <!-- output -->
   <xsl:output method="text" indent="no" encoding="ISO8859-1"/>
   
   <!-- root -->
   <xsl:template match="/">
      <xsl:apply-templates select="//glyf"/>
   </xsl:template>
   
   <xsl:template match="hmtx">
      <xsl:apply-templates select="TTGlyph"/>
   </xsl:template>

   <xsl:template match="TTGlyph">
    /**
     * test <xsl:value-of select="@name" />
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyf<xsl:value-of select="@name" />() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("<xsl:value-of select="@name" />", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(<xsl:value-of select="@xMin" />, 
                         <xsl:value-of select="@yMin" />,
                         <xsl:value-of select="@xMax" />,
                         <xsl:value-of select="@yMax" />));
    }
   </xsl:template>
   
</xsl:stylesheet>

