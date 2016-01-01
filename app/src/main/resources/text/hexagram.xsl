<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
    <head>
      <style>
        li
        {
          margin: 0 0 0 1.25em;
        }

        h1
        {
        }

        h2
        {
          font-size:1.25em;
        }

        hr
        {
          margin-top: 3em;
        }

        .heading
        {
          background-color:teal;
          color:white;
          padding:4px;
          font-weight:bold;
          font-size:1.5em;
        }

        .glossary
        {
          background-color:brown;
          color:white;
          padding:4px;
          margin-bottom:1em;
          font-weight:bold;
          font-size:1.5em;
        }

        .csv
        {
          font-style:italic;
        }

        .subtitle
        {
          font-weight:bold;
          font-style:italic;
        }

        .lines dt
        {
          font-size:1.25em;
          font-weight:bold;
        }
      </style>
    </head>

    <body>
    <xsl:for-each select="//hexagram">
      <h1><xsl:value-of select="@id"/>. <xsl:value-of select="title"/></h1>

      <span class="csv">
        <xsl:for-each select="name">
          <xsl:if test="position() != last()"><xsl:value-of select="normalize-space(.)"/>, </xsl:if>
          <xsl:if test="position()  = last()"><xsl:value-of select="normalize-space(.)"/></xsl:if>
        </xsl:for-each>
      </span>

      <h2><xsl:value-of select="keywords/title"/>:</h2>
      <ul>
        <xsl:for-each select="keywords/keyword">
          <li>
            <xsl:value-of select="."/>
          </li>
        </xsl:for-each>
      </ul>

      <xsl:for-each select="content">
        <div class="heading"><xsl:value-of select="title"/></div>
        <span class="subtitle"><xsl:value-of select="subtitle"/></span>
        <xsl:for-each select="paragraph">
          <p><xsl:value-of select="."/></p>
        </xsl:for-each>
      </xsl:for-each>

      <div class="heading"><xsl:value-of select="lines/title"/></div>
      <span class="subtitle"><xsl:value-of select="lines/subtitle"/></span>
      <xsl:for-each select="lines/paragraph">
        <p><xsl:value-of select="."/></p>
      </xsl:for-each>

      <dl class="lines">
        <xsl:for-each select="lines/line">
          <xsl:sort select="@id" order="ascending"/>
          <xsl:choose>
            <xsl:when test="impact">
              <dt><xsl:value-of select="@id"/>. <xsl:value-of select="title"/> (<xsl:value-of select="impact"/>)</dt>
            </xsl:when>
            <xsl:otherwise>
              <dt><xsl:value-of select="@id"/>. <xsl:value-of select="title"/></dt>
            </xsl:otherwise>
          </xsl:choose>
          <dd>
            <span class="subtitle"><xsl:value-of select="subtitle"/></span>
            <xsl:for-each select="paragraph">
              <p><xsl:value-of select="."/></p>
            </xsl:for-each>
          </dd>
        </xsl:for-each>
      </dl>

      <xsl:if test="glossary">
        <div class="glossary"><xsl:value-of select="glossary/title"/></div>
        <span class="csv">
          <xsl:for-each select="glossary/term">
            <xsl:if test="position() != last()"><xsl:value-of select="normalize-space(.)"/>, </xsl:if>
            <xsl:if test="position()  = last()"><xsl:value-of select="normalize-space(.)"/></xsl:if>
          </xsl:for-each>
        </span>
      </xsl:if>

      <xsl:if test="sources">
        <hr/>
        <h2><xsl:value-of select="sources/title"/>:</h2>
        <ul>
          <xsl:for-each select="sources/cite">
            <li>
              <xsl:if test="author">
                <xsl:value-of select="author"/>&#160;
              </xsl:if>
              <xsl:value-of select="date"/>,
              <xsl:value-of select="name"/>.
              <xsl:if test="url">
                URL: <a href="{url}"><xsl:value-of select="url"/></a>
              </xsl:if>
            </li>
          </xsl:for-each>
        </ul>
      </xsl:if>
    </xsl:for-each>
    </body>
  </html>
</xsl:template>

</xsl:stylesheet>
