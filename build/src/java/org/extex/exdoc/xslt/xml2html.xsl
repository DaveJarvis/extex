<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- ====================================================================== -->
<!--  $Id: xml2html.xsl,v 0.00 2007/02/26 16:47:20 gene Exp $                -->
<!-- ====================================================================== -->

<xsl:stylesheet version="1.0"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output encoding="iso-8859-1" method="html"/>

  <xsl:variable name="title"><xsl:copy-of select="/doc/h3"/></xsl:variable>
  <xsl:variable name="author"><xsl:copy-of select="/doc/@authorName"/></xsl:variable>
  <xsl:variable name="email"><xsl:copy-of select="/doc/@authorEmail"/></xsl:variable>

  <xsl:template match="/">
   <html>
    <head>
     <title><xsl:copy-of select="/doc/h3"/></title>
     <meta name="author" content="$author" />
     <link rel="stylesheet" href="info.css"/>
     <script src="reframe.js" type="text/javascript"/>
    </head>
    <body onload='parent.document.title="$title";'>
     <noscript><a class="noframes" href="frameset.html" target="_top">Frames</a></noscript>
     <div class="unit">From unit ???</div>
     <hr />
     <xsl:copy-of select="."/>
    </body>
   </html>
  </xsl:template>

</xsl:stylesheet>

