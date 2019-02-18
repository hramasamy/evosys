<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:output omit-xml-declaration="yes"/>
	<xsl:param name="codebase"/>
	<xsl:template match="/">
		<!--
		<html>
			<head>
				<title>Enhancer search result: The codebase is <xsl:value-of select="$codebase"/></title>
			</head>
			<body>
		
				<table border="0" align="left" bgcolor="gainsboro" cellpadding="0" cellspacing="0">
		-->
		<xsl:apply-templates/>
		<!--
				</table>
			
			</body>
		</html>
-->
	</xsl:template>
	<xsl:template match="cluster">
		<table bgcolor="gainsboro">
			<tr>
				<td>
					<font size="5"> Results for 
					<a>
							<xsl:attribute name="href"><xsl:value-of select="@url"/></xsl:attribute>
							<xsl:value-of select="@name"/>
						</a>
					</font>
				</td>
			</tr>
			<tr bgcolor="gainsboro">
				<td>
					<font color="gainsboro">blank</font>
				</td>
			</tr>
			<tr>
				<td>
					<xsl:call-template name="handleMotifs">
						<xsl:with-param name="motifs" select="motifs"/>
					</xsl:call-template>
				</td>
			</tr>
			<tr>
				<td>
					<xsl:call-template name="handleGenes">
						<xsl:with-param name="genes" select="genes"/>
						<xsl:with-param name="image" select="image"/>
					</xsl:call-template>
				</td>
			</tr>
			<tr>
				<td bgcolor="#fffeef">
					<font color="#ffeeee">new row</font>
				</td>
			</tr>
		</table>
	</xsl:template>
	<xsl:template name="handleMotifs">
		<xsl:param name="motifs"/>
		<xsl:if test="$motifs/motifdetail">
			<table cellpadding="0" cellspacing="1">
				<tr bgcolor="darkred">
					<th width="20%" valign="top" align="left">
						<font color="white">Name</font>
					</th>
					<th width="30%" valign="top" align="left">
						<font color="white">Pattern</font>
					</th>
					<th width="10%" valign="top" align="center">
						<font color="white">Orientation</font>
					</th>
					<th width="40%" valign="top" align="right">
						<font color="white">Distance (next site)</font>
					</th>
				</tr>
				<xsl:for-each select="$motifs/motifdetail">
					<tr bgcolor="#c9d8e6">
						<td id="resultCell">
							<xsl:value-of select="username"/>
						</td>
						<td id="resultCell">
							<xsl:value-of select="pattern"/>
						</td>
						<xsl:variable name="orientation" select="normalize-space(orientation)"/>
						<td align="center" id="resultCell">
							<xsl:element name="img">
								<xsl:attribute name="src"><xsl:choose><xsl:when test="$orientation='r'"><xsl:value-of select="concat($codebase,'images/reverse.gif')"/></xsl:when><xsl:otherwise><xsl:value-of select="concat($codebase,'images/forward.gif')"/></xsl:otherwise></xsl:choose></xsl:attribute>
								<xsl:attribute name="alt"><xsl:value-of select="$orientation"/></xsl:attribute>
							</xsl:element>
						</td>
						<td align="right" id="resultCell">
							<xsl:value-of select="distance"/>
						</td>
					</tr>
				</xsl:for-each>

					<xsl:call-template name="blankRow"/>
				</table>
		</xsl:if>
		<xsl:call-template name="handleSequence">
			<xsl:with-param name="sequence" select="$motifs/sequence"/>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="handleSequence">
		<xsl:param name="sequence"/>
		<tr>
			<td>
				<table>
					<tr bgcolor="steelblue">
						<th align="center" width="10%">
							<font color="white">Left</font>
						</th>
						<th align="center" width="10%">
							<font color="white">Sequence</font>
						</th>
						<th align="center" width="10%">
							<font color="white">Right</font>
						</th>
					</tr>
					<tr>
						<td valign="top" align="justify">
							<textarea cols="60" rows="10" style="FONT-SIZE: 8pt;COLOR: black; BORDER-TOP-STYLE: none; FONT-FAMILY: Arial; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: gainsboro; BORDER-BOTTOM-STYLE: none" wrap="soft" disabled="disabled">
								<xsl:value-of select="normalize-space($sequence/motifleft)"/>
							</textarea>
						</td>
						<td valign="top" align="justify">
							<textarea cols="60" rows="10" style="FONT-SIZE: 8pt;COLOR: black; BORDER-TOP-STYLE: none; FONT-FAMILY: Arial; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: gainsboro; BORDER-BOTTOM-STYLE: none">
								<xsl:value-of select="normalize-space($sequence/motifsequence)"/>
							</textarea>
							<!--				<xsl:call-template name="colorcode">
									<xsl:with-param name="seqNode" select="$sequence/motifsequence"/>
									<xsl:with-param name="sequence" select="normalize-space($sequence/motifsequence)"/>
								</xsl:call-template>
-->
						</td>
						<td valign="top" align="justify">
							<textarea cols="60" rows="10" style="FONT-SIZE: 8pt;COLOR: black; BORDER-TOP-STYLE: none; FONT-FAMILY: Arial; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: gainsboro; BORDER-BOTTOM-STYLE: none">
								<xsl:value-of select="normalize-space($sequence/motifright)"/>
							</textarea>
						</td>
					</tr>
						<xsl:call-template name="blankRow"/>
					
						<xsl:call-template name="blankRow"/>
				
				</table>
			</td>
		</tr>
	</xsl:template>
	<xsl:template name="drawImage">
		<xsl:param name="image"/>

		<table style="BORDER-RIGHT: maroon solid; BORDER-TOP: maroon solid; BORDER-LEFT: maroon solid; BORDER-BOTTOM: maroon solid; BACKGROUND-COLOR: #ffffff">
			<xsl:for-each select="$image/row">
				<xsl:variable name="blankLength" select="normalize-space(blank)"/>
				<xsl:choose>
					<xsl:when test="gene">
						<xsl:variable name="orientation" select="normalize-space(gene/orientation)"/>
						<xsl:variable name="length" select="normalize-space(gene/length)"/>
						<xsl:variable name="desc" select="gene/description"/>
						<tr>
							<td>
								<xsl:call-template name="drawLine">
									<xsl:with-param name="src" select="'images/clear.gif'"/>
									<xsl:with-param name="width" select="$blankLength"/>
								</xsl:call-template>
								<xsl:choose>
									<xsl:when test="$orientation='forward'">
										<xsl:call-template name="insertImage">
											<xsl:with-param name="src" select="'images/geneForward.gif'"/>
										</xsl:call-template>
										<xsl:call-template name="drawLine">
											<xsl:with-param name="src" select="'images/gene.gif'"/>
											<xsl:with-param name="width" select="$length"/>
										</xsl:call-template>
									</xsl:when>
									<xsl:otherwise>

										<xsl:call-template name="drawLine">
											<xsl:with-param name="src" select="'images/gene.gif'"/>
											<xsl:with-param name="width" select="$length"/>
										</xsl:call-template>
										<xsl:call-template name="insertImage">
											<xsl:with-param name="src" select="'images/geneReverse.gif'"/>
										</xsl:call-template>
									</xsl:otherwise>
								</xsl:choose>
							</td>
						</tr>
						<tr>
							<td id="sbsSubLabel">
								<xsl:call-template name="drawLine">
									<xsl:with-param name="src" select="'images/clear.gif'"/>
									<xsl:with-param name="width" select="$blankLength"/>
								</xsl:call-template>
								<xsl:value-of select="$desc"/>
							</td>
						</tr>
					</xsl:when>
					<xsl:otherwise>
						<xsl:variable name="length" select="normalize-space(cluster/length)"/>
						<xsl:variable name="desc" select="cluster/description"/>
						<tr>
							<td>
								<xsl:call-template name="drawLine">
									<xsl:with-param name="src" select="'images/clear.gif'"/>
									<xsl:with-param name="width" select="$blankLength"/>
								</xsl:call-template>
								<xsl:call-template name="drawLine">
									<xsl:with-param name="src" select="'images/motif.gif'"/>
									<xsl:with-param name="width" select="$length"/>
								</xsl:call-template>
							</td>
						</tr>
						<tr>
							<td id="sbsSubLabel">
								<xsl:call-template name="drawLine">
									<xsl:with-param name="src" select="'images/clear.gif'"/>
									<xsl:with-param name="width" select="$blankLength"/>
								</xsl:call-template>
								<xsl:value-of select="$desc"/>
							</td>
						</tr>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
		</table>
		<table bgcolor="gainsboro">
					<xsl:call-template name="blankRow"/>
			<xsl:call-template name="blankRow"/>
					<xsl:call-template name="blankRow"/>
			<tr><td id="sbsLabel">Tablular View</td></tr>
			<xsl:call-template name="blankRow"/>

		</table>
	</xsl:template>
	<xsl:template name="handleGenes">
		<xsl:param name="genes"/>
		<xsl:param name="image"/>
		<xsl:variable name="west" select="normalize-space($genes/left/gene)"/>
		<xsl:variable name="inside" select="normalize-space($genes/inside/gene)"/>
		<xsl:variable name="east" select="normalize-space($genes/right/gene)"/>
		<xsl:choose>
			<xsl:when test="$west or $inside or $west">
				<table bgcolor="gainsboro" border="0">
					<tr>
						<td id="sbsSection">Gene Information</td>
					</tr>
<xsl:call-template name="blankRow"/>

					<tr>
						<td id="sbsLabel">Graphical View</td>
					</tr>
					
					<xsl:call-template name="blankRow"/>
				</table>
				<xsl:call-template name="drawImage">
						<xsl:with-param name="image" select="$image"/>
				</xsl:call-template>
				
				<table bgcolor="gainsboro" cellpadding="0" border="0" cellspacing="1">
					<tr bgcolor="steelblue">
						<th align="center">
							<font color="steelblue">NoHeader</font>
						</th>
						<xsl:if test="$west">
							<th align="center">
								<font color="white">West</font>
							</th>
						</xsl:if>
						<xsl:if test="$inside">
							<th align="center">
								<font color="white">Inside</font>
							</th>
						</xsl:if>
						<xsl:if test="$east">
							<th align="center">
								<font color="white">East</font>
							</th>
						</xsl:if>
					</tr>
					<tr>
						<td>
							<xsl:call-template name="fillLeftTitles"/>
						</td>
						<xsl:if test="$west">
							<td valign="top">
								<xsl:call-template name="handleGeneSeg">
									<xsl:with-param name="seg" select="$genes/left"/>
								</xsl:call-template>
							</td>
						</xsl:if>
						<xsl:if test="$inside">
							<td valign="top">
								<xsl:call-template name="handleGeneSeg">
									<xsl:with-param name="seg" select="$genes/inside"/>
								</xsl:call-template>
							</td>
						</xsl:if>
						<xsl:if test="$east">
							<td valign="top">
								<xsl:call-template name="handleGeneSeg">
									<xsl:with-param name="seg" select="$genes/right"/>
								</xsl:call-template>
							</td>
						</xsl:if>
					</tr>
				</table>
			</xsl:when>
			<xsl:otherwise>
				<table bgcolor="gainsboro" border="0">
					<tr>
						<td id="sbsSection">Gene Information: No Gene found.</td>
					</tr>
				</table>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="handleGeneSeg">
		<xsl:param name="seg"/>
		<table border="0" bgcolor="white" cellpadding="0" cellspacing="1">
			<tr>
				<xsl:for-each select="$seg/gene">
					<td valign="top">
						<table bgcolor="white" cellpadding="0" cellspacing="1" style="BORDER-RIGHT: steelblue thin solid; BORDER-TOP: steelblue thin solid; BORDER-LEFT: steelblue thin solid; BORDER-BOTTOM: steelblue thin solid;">
							<tr>
								<td valign="top" id="resultCell" align="right">
									<xsl:value-of select="name"/>
								</td>
							</tr>
							<xsl:call-template name="inside_blank"/>
							<tr>
								<td valign="top" id="resultCell" align="center">
									<xsl:variable name="orientation" select="normalize-space(strand)"/>

									<xsl:choose>
										<xsl:when test="$orientation='forward'">
											<xsl:call-template name="insertImage">
												<xsl:with-param name="src" select="'images/geneForward.gif'"/>
											</xsl:call-template>
										</xsl:when>
										<xsl:otherwise>
											<xsl:call-template name="insertImage">
												<xsl:with-param name="src" select="'images/geneReverse.gif'"/>
											</xsl:call-template>
										</xsl:otherwise>
									</xsl:choose>
								</td>
							</tr>
							<xsl:call-template name="inside_blank"/>
							<tr>
								<td valign="top" id="resultCell" align="right">
									<xsl:value-of select="distance"/>
								</td>
							</tr>
							<xsl:call-template name="inside_blank"/>
							<tr>
								<td valign="top" id="resultCell" align="right">
									<xsl:value-of select="start"/>
								</td>
							</tr>
							<xsl:call-template name="inside_blank"/>
							<tr>
								<td valign="top" id="resultCell" align="right">
									<xsl:choose>
										<xsl:when test="end">
											<xsl:value-of select="end"/>
										</xsl:when>
										<xsl:otherwise>...</xsl:otherwise>
									</xsl:choose>
								</td>
							</tr>
							<xsl:call-template name="inside_blank"/>
							<tr>
								<td valign="top" id="resultCell" align="right">
									<xsl:choose>
										<xsl:when test="geneModel">
											<xsl:value-of select="geneModel"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="name"/>
										</xsl:otherwise>
									</xsl:choose>
								</td>
							</tr>
						</table>
					</td>
				</xsl:for-each>
			</tr>
		</table>
	</xsl:template>
	<xsl:template name="fillLeftTitles">
		<table border="0" bgcolor="steelblue" cellpadding="0" cellspacing="1" >
			<tr>
				<th align="left" bgcolor="steelblue" valign="middle">
					<font color="white">Name</font>
				</th>
			</tr>
			<xsl:call-template name="blankRow"/>
			<tr>
				<th align="left" bgcolor="steelblue">
					<font color="white">Orientation</font>
				</th>
			</tr>
			<xsl:call-template name="blankRow"/>
			<tr>
				<th align="left" bgcolor="steelblue">
					<font color="white">Distance</font>
				</th>
			</tr>
			<xsl:call-template name="blankRow"/>
			<tr>
				<th align="left" bgcolor="steelblue">
					<font color="white">Start</font>
				</th>
			</tr>
			<xsl:call-template name="blankRow"/>
			<tr>
				<th align="left" bgcolor="steelblue">
					<font color="white">End</font>
				</th>
			</tr>
			<xsl:call-template name="blankRow"/>
			<tr>
				<th align="left" bgcolor="steelblue">
					<font color="white">Gene Model</font>
				</th>
			</tr>
		</table>
	</xsl:template>
	<xsl:template name="insertImage">
		<xsl:param name="src"/>
		<xsl:element name="img">
			<xsl:attribute name="src"><xsl:value-of select="concat($codebase,$src)"/></xsl:attribute>
		</xsl:element>
	</xsl:template>
	<xsl:template name="drawLine">
		<xsl:param name="src"/>
		<xsl:param name="width"/>
		<xsl:param name="height" select="4"/>
		<xsl:if test="$width!=0">
			<xsl:element name="img">
				<xsl:attribute name="src"><xsl:value-of select="concat($codebase,$src)"/></xsl:attribute>
				<xsl:attribute name="width"><xsl:value-of select="$width"/></xsl:attribute>
				<xsl:attribute name="height"><xsl:value-of select="$height"/></xsl:attribute>
			</xsl:element>
		</xsl:if>
	</xsl:template>

	<xsl:template name="blankRow">
		<tr>
			<td bgcolor="gainsboro"/>
		</tr>
	</xsl:template>
	<xsl:template name="inside_blank">
		<tr>
			<td bgcolor="darkblue"/>
		</tr>
	</xsl:template>
	<xsl:template name="colorcode">
		<xsl:param name="seqNode"/>
		<xsl:param name="sequence"/>
		<xsl:param name="counter" select="1"/>
		<xsl:choose>
			<xsl:when test="1=$counter">
				<xsl:choose>
					<xsl:when test="$seqNode/A">
						<xsl:variable name="newSeq">
							<xsl:call-template name="global-replace">
								<xsl:with-param name="string" select="$sequence"/>
								<xsl:with-param name="replace" select="$seqNode/A"/>
								<xsl:with-param name="by">
									<font color="green">
										<xsl:value-of select="$seqNode/A"/>
									</font>
								</xsl:with-param>
							</xsl:call-template>
						</xsl:variable>
					</xsl:when>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$sequence"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="global-replace">
		<xsl:param name="string"/>
		<xsl:param name="replace" select="' '"/>
		<xsl:param name="by" select="'+'"/>
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
	<xsl:template name="getCode">
		<xsl:param name="counter"/>
		<xsl:choose>
			<xsl:when test="$counter=1">A</xsl:when>
			<xsl:when test="$counter=2">B</xsl:when>
			<xsl:when test="$counter=3">C</xsl:when>
			<xsl:when test="$counter=4">D</xsl:when>
			<xsl:when test="$counter=5">E</xsl:when>
			<xsl:when test="$counter=6">F</xsl:when>
			<xsl:when test="$counter=7">G</xsl:when>
			<xsl:when test="$counter=8">H</xsl:when>
			<xsl:when test="$counter=9">I</xsl:when>
			<xsl:when test="$counter=10">J</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!--
	<xsl:template name="color_code>">
		<xsl:param name="code"/>
		<xsl:param name="text"/>
		<xsl:choose>
			<xsl:when test="$code='A'"><font color="green"><xsl:value-of select="$code"/></font></xsl:when>
			<xsl:when test="$code='B'"><font color="red"><xsl:value-of select="$code"/></font></xsl:when>
			<xsl:when test="$code='C'"><font color="blue"><xsl:value-of select="$code"/></font></xsl:when>
			<xsl:when test="$code='D'"><font color="yellow"><xsl:value-of select="$code"/></font></xsl:when>
			<xsl:when test="$code='E'"><font color="magenta"><xsl:value-of select="$code"/></font></xsl:when>
			<xsl:when test="$code='F'"><font color="darkred"><xsl:value-of select="$code"/></font></xsl:when>
			<xsl:when test="$code='G'"><font color="darkred"><xsl:value-of select="$code"/></font></xsl:when>
			<xsl:when test="$code='H'"><font color="darkred"><xsl:value-of select="$code"/></font></xsl:when>
			<xsl:when test="$code='I'"><font color="darkred"><xsl:value-of select="$code"/></font></xsl:when>
			<xsl:when test="$code='J'"><font color="darkred"><xsl:value-of select="$code"/></font></xsl:when>
		</xsl:choose>		
	</xsl:template>-->
</xsl:stylesheet>
