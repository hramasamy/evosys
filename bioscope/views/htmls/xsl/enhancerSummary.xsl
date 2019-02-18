<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:output omit-xml-declaration="yes" method="html"/>
	<xsl:param name="codebase"/>
	<xsl:param name="detail"/>
	<xsl:param name="showAllChromosomeRes" select="'F'"/>
	
	<xsl:template match="/">
		<!--
		<html>
			<head>
			<LINK title="hs"  href="C:\projects\sbs\biocis\htmls\css\sbs.css" type="text/css" rel="stylesheet"/>
				<title>Enhancer search result summary</title>
			</head>
			<body>
				<h1 style="color:darkred;font-family:arial">Result Summary</h1>
-->

		<xsl:message>The codebase: <xsl:value-of select="$codebase"/></xsl:message>
		<xsl:message>The constraint: <xsl:value-of select="$constraint"/></xsl:message>
		<xsl:message>The binding sites: <xsl:value-of select="$binding-sites"/></xsl:message>
		<xsl:message>The binding bases: <xsl:value-of select="$binding-bases"/></xsl:message>
		<xsl:apply-templates/>
		<!--<table border="0" align="left" bgcolor="lightgrey" cellpadding="1" cellspacing="1">
					<xsl:apply-templates/>
				</table>
				-->
		<!--				
			</body>
		</html>
-->
	</xsl:template>
	<xsl:template match="summary">
		<table style="border-style:solid;border-width:2;border-color:steelblue;" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top">
					<xsl:call-template name="left-header"/>
				</td>
				<td valign="top">
					<xsl:call-template name="top"/>
				</td>
			</tr>
		</table>
	</xsl:template>
	<xsl:template name="left-header">
		<table border="0" cellpadding="1" cellspacing="1">
			<tr>
				<th valign="top" id="summaryHeader">
					<font id="HeaderBGColor">blank</font>
				</th>
			</tr>
			<xsl:for-each select="$motifs">
				<tr>
					<th valign="top" id="leftHeader" align="left">
						<font color="white">
							<xsl:value-of select="name"/> - <xsl:value-of select="pattern"/>
						</font>
					</th>
				</tr>
			</xsl:for-each>
			<tr>
				<th valign="top" id="leftHeader" align="left">
					<font color="white">No of <xsl:value-of select="$binding-sites"/> sites within <xsl:value-of select="$binding-bases"/> bases</font>
				</th>
			</tr>
			<tr>
				<th valign="top" id="leftHeader" align="left">
					<font color="white">No after satisfying logical constraint <xsl:value-of select="$constraint"/>
					</font>
				</th>
			</tr>
			<tr>
				<th valign="top" id="leftHeader" align="left">
					<font color="white">No after satisfying gene specific constraints </font>
				</th>
			</tr>
			<tr>
				<th valign="top" id="leftHeader" align="left">
					<font color="white">No after satisfying precedence/orientation constraints </font>
				</th>
			</tr>
		</table>
	</xsl:template>
	<xsl:template name="top">
		<table border="0" cellpadding="1" cellspacing="1">
			<tr>
				<xsl:for-each select="$chromosomes">
					<xsl:choose>
						<xsl:when test="$showAllChromosomeRes='F'">
							<!-- Do only if it has results for binding/result-->
							<xsl:if test="binding/result!=0">
								<td id="summaryHeader">
									<xsl:call-template name="do-chromosome"/>
								</td>
							</xsl:if>
						</xsl:when>
						<xsl:otherwise>
							<td id="summaryHeader">
								<xsl:call-template name="do-chromosome"/>
							</td>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:for-each>
			</tr>
		</table>
	</xsl:template>
	<xsl:template name="do-chromosome">
		<table border="0" cellpadding="1" cellspacing="1">
			<tr>
				<th>
					<font color="white">
						<a style="color:white;text-decoration:underline">
							<xsl:attribute name="href"><xsl:value-of select="$detail"/>?chromosome=<xsl:value-of select="@name"/></xsl:attribute>
							<xsl:value-of select="@name"/>
						</a>
					</font>
				</th>
			</tr>
			<xsl:for-each select="motif">
				<tr>
					<xsl:call-template name="insertCell">
						<xsl:with-param name="value" select="total"/>
					</xsl:call-template>
				</tr>
			</xsl:for-each>
			<tr>
				<xsl:call-template name="insertCell">
					<xsl:with-param name="value" select="binding/result"/>
				</xsl:call-template>
			</tr>
			<tr>
				<xsl:call-template name="insertCell">
					<xsl:with-param name="value" select="logic/result"/>
				</xsl:call-template>
			</tr>
			<tr>
				<xsl:call-template name="insertCell">
					<xsl:with-param name="value" select="geneconstraint/result"/>
				</xsl:call-template>
			</tr>
			<tr>
				<xsl:call-template name="insertCell">
					<xsl:with-param name="value" select="precedence/result"/>
				</xsl:call-template>
			</tr>
		</table>
	</xsl:template>
	<xsl:template name="insertCell">
		<xsl:param name="value"/>
		<td id="resultCell" align="right">
			<xsl:choose>
				<xsl:when test="$value">
					<xsl:value-of select="$value"/>
				</xsl:when>
				<xsl:otherwise>0</xsl:otherwise>
			</xsl:choose>
		</td>
	</xsl:template>
<!--
	<xsl:variable name="chromosomes" select="$chList"/>
	<xsl:variable name="motifs" select="$motifDisplayNames"/>
	<xsl:variable name="constraint" select="$logicalConstraints"/>
	<xsl:variable name="binding-sites" select="$noOfSites"/>
	<xsl:variable name="binding-bases" select="$noOfBases"/>
-->
	
	<xsl:variable name="chromosomes" select="//chromosome"/>
	<xsl:variable name="motifs" select="$chromosomes[1]/motif"/>
	<xsl:variable name="search" select="/summary/search"/>
	<xsl:variable name="constraint" select="$search/condition"/>
	<xsl:variable name="binding-sites" select="$search/sites"/>
	<xsl:variable name="binding-bases" select="$search/constraint"/>
	
</xsl:stylesheet>
