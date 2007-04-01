<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   
   <!-- output -->
   <xsl:output method="text" indent="no" encoding="ISO8859-1"/>
   
   <!-- root -->
   <xsl:template match="/">
      <xsl:apply-templates select="//cmap_format_4[@platformID='3' and @platEncID='1']"/>
   </xsl:template>
   
   <!-- plattformid = 3 (Windows) , encodingid = 1 (Unicode) -->
   <xsl:template match="cmap_format_4">
      <xsl:apply-templates select="map"/>
   </xsl:template>

   <xsl:template match="map">
    /**
     * test <xsl:value-of select="@code" />
     *
     * @throws Exception if an error occurred.
     */
    public void test<xsl:value-of select="@code" />() throws Exception {

        assertEquals("<xsl:value-of select="@name" />", 
        reader.mapCharCodeToGlyphname(<xsl:value-of select="@code" />, (short) 3,
                (short) 1));
    }
   </xsl:template>
   
</xsl:stylesheet>

