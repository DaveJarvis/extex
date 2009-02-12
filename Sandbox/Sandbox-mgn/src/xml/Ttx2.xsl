<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   
   <!-- output -->
   <xsl:output method="text" indent="no" encoding="ISO8859-1"/>
   
   <!-- root -->
   <xsl:template match="/">
      <xsl:apply-templates select="//hmtx"/>
   </xsl:template>
   
   <xsl:template match="hmtx">
      <xsl:apply-templates select="mtx"/>
   </xsl:template>

   <xsl:template match="mtx">
    /**
     * test <xsl:value-of select="@name" />
     *
     * @throws Exception if an error occurred.
     */
    public void testMtx<xsl:value-of select="@name" />() throws Exception {

        assertEquals(<xsl:value-of select="@width" />, 
        reader.mapCharCodeToWidth("<xsl:value-of select="@name" />", (short) 3,
                (short) 1));
    }
   </xsl:template>
   
</xsl:stylesheet>

