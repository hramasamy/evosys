<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:variable name="context">
		<xsl:text disable-output-escaping="yes">search?q=</xsl:text>
	</xsl:variable>
	<xsl:variable name="query" select="/response/query"/>
	<xsl:variable name="base" select="concat($context, $query)"/>
	<xsl:template match="/">
		<html>
			<head>
				<title> Result for the query</title>
			</head>
			<body>
				<xsl:variable name="topic-set" select="//topic"/>
				<xsl:call-template name="handleTopics">
					<xsl:with-param name="topics" select="$topic-set"/>
				</xsl:call-template>
				<br/>
				<xsl:variable name="result-set" select="//result"/>
				<xsl:call-template name="handleResults">
					<xsl:with-param name="results" select="$result-set"/>
				</xsl:call-template>
				<br/>
				<xsl:call-template name="nextPrev"/>
			</body>
		</html>
	</xsl:template>
	<xsl:template name="handleTopics">
		<xsl:param name="topics"/>
		<table border="0" cellpadding="1" width="100%" bgcolor="#d1e1ff">
			<tr>
				<xsl:element name="td">
					<xsl:element name="font">
						<xsl:attribute name="size">2</xsl:attribute>
						<xsl:element name="a">
							<xsl:attribute name="href"><xsl:value-of select="$base"/><xsl:text disable-output-escaping="no"><![CDATA[&]]>category=more</xsl:text></xsl:attribute>
							more...
						</xsl:element>
					</xsl:element>
				</xsl:element>
			</tr>
			<xsl:text disable-output-escaping="yes">&lt;tr&gt;</xsl:text>
			<xsl:for-each select="$topics">
				<xsl:choose>
					<xsl:when test="position() mod 4 !=0">
						<xsl:call-template name="createTD"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="createTD"/>
						<xsl:text disable-output-escaping="yes">&lt;/tr&gt;</xsl:text>
						<xsl:text disable-output-escaping="yes">&lt;tr&gt;</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
			<xsl:text disable-output-escaping="yes">&lt;/tr&gt;</xsl:text>
		</table>
	</xsl:template>
	<xsl:template name="handleResults">
		<xsl:param name="results"/>
		<xsl:for-each select="$results">
			<br/>
			<xsl:choose>
				<xsl:when test="clustered">
					<xsl:element name="blockquote">
						<xsl:call-template name="put-result"/>
					</xsl:element>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="put-result"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="createTD">
		<xsl:element name="td">
			<xsl:element name="font">
				<xsl:attribute name="size">2</xsl:attribute>
				<xsl:value-of select="score"/>% 
				<xsl:element name="a">
					<xsl:attribute name="href"><xsl:value-of select="$base"/>%22
					<xsl:call-template name="global-replace"><xsl:with-param name="string" select="name"/></xsl:call-template>%22</xsl:attribute>
					<xsl:value-of select="name"/>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template name="put-result">
		<u>
			<font size="4" color="blue">
				<xsl:element name="a">
					<xsl:attribute name="href">
						<xsl:value-of select="url"/>
					</xsl:attribute>
					<xsl:value-of select="title" disable-output-escaping="yes"/>				
				</xsl:element>
				
				<br/>
			</font>
		</u>
		<font size="2">
			<xsl:value-of select="sum" disable-output-escaping="yes"/>
			<br/>
		</font>
		<font size="2" color="green">
			<xsl:value-of select="url"/>
		</font> - <font size="2">
			<xsl:value-of select="size"/>k - indexed: <xsl:value-of select="spidered"/> - modified: <xsl:value-of select="lastMod"/>
			<br/>
		</font>
	</xsl:template>
	<xsl:param name="replace" select="' '"/>
	<xsl:param name="by" select="'+'"/>
	<xsl:template name="global-replace">
		<xsl:param name="string"/>
		<xsl:choose>
			<xsl:when test="contains($string, $replace)">
				<xsl:value-of select="substring-before($string, $replace)"/>
				<xsl:value-of select="$by"/>
				<xsl:call-template name="global-replace">
					<xsl:with-param name="string" select="substring-after($string, $replace)"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$string"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="nextPrev">
		<table width="100%">
			<tr>
				<td width="60%">
					<font color="white">...</font>
				</td>
				<td align="right">
					<xsl:element name="font">
						<xsl:attribute name="size">4</xsl:attribute>
						<xsl:element name="a">
							<xsl:attribute name="href"><xsl:value-of select="$base"/><xsl:text disable-output-escaping="no"><![CDATA[&]]>prev=10</xsl:text></xsl:attribute>
									&lt;prev 10&gt;
								</xsl:element>
					</xsl:element>
				</td>
				<td align="center">
					<xsl:element name="font">
						<xsl:attribute name="size">4</xsl:attribute>
						<xsl:element name="a">
							<xsl:attribute name="href"><xsl:value-of select="$base"/><xsl:text disable-output-escaping="no"><![CDATA[&]]>next=10</xsl:text></xsl:attribute>
									&lt;next 10&gt;
								</xsl:element>
					</xsl:element>
				</td>
				<td width="20%">
					<font color="white">...</font>
				</td>
			</tr>
		</table>
	</xsl:template>
</xsl:stylesheet>
