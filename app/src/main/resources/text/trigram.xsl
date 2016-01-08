<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
<html>
  <style type="text/css">
    table
    {
      width: 100%;
      table-layout: fixed;
    }
    table td
    {
      vertical-align: top;
    }
    col
    {
      width: 20em;
      background-color: lightgrey;
      font-weight: bold;
    }

    ul.values
    {
      margin: 0;
      padding: 0;
    }
    ul.values li
    {
      margin-left: 20px;
    }

    hr
    {
      margin-top: 3em;
    }

    .heading
    {
      margin-top: 2em;
      margin-bottom: 1em;
      background-color:teal;
      color:white;
      padding:4px;
      font-weight:bold;
      font-size:1.5em;
    }

    .csv
    {
      font-style:italic;
    }
  </style>
  <body>
    <xsl:for-each select="//trigram">
      <h1>
        <xsl:value-of select="title"/>
        <xsl:if test="name">
          (
          <span class="csv">
            <xsl:for-each select="name">
              <xsl:if test="position() != last()"><xsl:value-of select="normalize-space(.)"/>, </xsl:if>
              <xsl:if test="position()  = last()"><xsl:value-of select="normalize-space(.)"/></xsl:if>
            </xsl:for-each>
          </span>
          )
        </xsl:if>
      </h1>

      <xsl:for-each select="content">
        <div class="heading"><xsl:value-of select="title"/></div>

        <table border="1" cellspacing="0" cellpadding="0">
          <colgroup>
            <col/>
          </colgroup>
          <xsl:for-each select="entry">
            <tr>
              <td><xsl:value-of select="key"/></td>
              <td>
                <xsl:choose>
                  <xsl:when test="count(value) &gt; 1">
                    <ul class="values">
                      <xsl:for-each select="value">
                        <li><xsl:value-of select="."/></li>
                      </xsl:for-each>
                    </ul>
                  </xsl:when>
                  <xsl:otherwise>
                    <xsl:value-of select="value"/>
                  </xsl:otherwise>
                </xsl:choose>
              </td>
            </tr>
          </xsl:for-each>
        </table>
      </xsl:for-each>

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
