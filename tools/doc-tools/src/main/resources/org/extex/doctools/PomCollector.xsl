<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- ====================================================================== -->
<xsl:stylesheet version="1.0"
                xmlns:pom="http://maven.apache.org/POM/4.0.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output encoding="iso-8859-1" method="html"/>

  <!-- ===================================================================== -->
  <xsl:template match="/top">%%
\begin{PomList}<xsl:apply-templates select="pom:project"/>\end{PomList}
%%
</xsl:template>

  <!-- ===================================================================== -->
  <xsl:template match="pom:project">
  \begin{Pom}{<xsl:value-of select="pom:groupId"/>}{<xsl:value-of select="pom:artifactId"/>}{<xsl:value-of select="pom:name"/>}<xsl:call-template name="translate"
   ><xsl:with-param name="in"><xsl:value-of select="pom:description"/></xsl:with-param
   ></xsl:call-template>\end{Pom}
</xsl:template>

  <!-- ===================================================================== -->
  <xsl:template name="translate">
    <xsl:param name="in"/>
    <xsl:choose>
     <xsl:when test="string-length($in)=0"></xsl:when>
     <xsl:when test="starts-with($in,'ExTeX')">\ExTeX{}<xsl:call-template name="translate"
        ><xsl:with-param name="in"><xsl:value-of select="substring($in,6)"
        /></xsl:with-param></xsl:call-template></xsl:when>
     <xsl:when test="starts-with($in,'ExBib')">\ExBib{}<xsl:call-template name="translate"
        ><xsl:with-param name="in"><xsl:value-of select="substring($in,6)"
        /></xsl:with-param></xsl:call-template></xsl:when>
     <xsl:when test="starts-with($in,'ExIndex')">\ExIndex{}<xsl:call-template name="translate"
        ><xsl:with-param name="in"><xsl:value-of select="substring($in,8)"
        /></xsl:with-param></xsl:call-template></xsl:when>
     <xsl:when test="starts-with($in,'BibTeX')">\BibTeX{}<xsl:call-template name="translate"
        ><xsl:with-param name="in"><xsl:value-of select="substring($in,7)"
        /></xsl:with-param></xsl:call-template></xsl:when>
     <xsl:when test="starts-with($in,'LaTeX')">\LaTeX{}<xsl:call-template name="translate"
        ><xsl:with-param name="in"><xsl:value-of select="substring($in,6)"
        /></xsl:with-param></xsl:call-template></xsl:when>
     <xsl:when test="starts-with($in,'TeX')">\TeX{}<xsl:call-template name="translate"
        ><xsl:with-param name="in"><xsl:value-of select="substring($in,4)"
        /></xsl:with-param></xsl:call-template></xsl:when>
     <xsl:otherwise><xsl:value-of select="substring($in,1,1)"
       /><xsl:call-template name="translate"
        ><xsl:with-param name="in"><xsl:value-of select="substring($in,2)"
        /></xsl:with-param></xsl:call-template>
</xsl:otherwise>
    </xsl:choose>
  </xsl:template>

</xsl:stylesheet>
