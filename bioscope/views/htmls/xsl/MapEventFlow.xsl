<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xlink="http://www.w3.org/1999/xlink">
	<xsl:template match="/">
		<html>
			<head>
				<!--<LINK rel="stylesheet" type="text/css" href="/BugTrack/htmls/css/snapshot.css" title="style1"/>-->
				<LINK rel="stylesheet" type="text/css" href="C:/apache/Tomcat 5.0/webapps/BugTrack/htmls/css/snapshot.css" title="style1"/>
				<script>
					window.contextPath="/BugTrack";
					var mapName = "";
					appPath = window.location.protocol + "//" + window.location.host + window.contextPath;
										
					function showMap(mapName) {
						window.top.frames["contentFrame"].location.href=appPath + "/jsps/mapViewer.jsp?mapName="+mapName;
					}
					
					</script>
			</head>
			<body>
				<table border="0">
					<tr>
						<xsl:apply-templates select="map"/>
					</tr>
				</table>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="map">
		<table id="solidBorderTable">
			<tr id="headerRow">
				<td>Name</td>
				<td>
					<xsl:value-of select="@name"/>
				</td>
			</tr>
			<tr id="headerRow">
				<td>Type</td>
				<td>
					<xsl:choose>
						<xsl:when test="token[@class='com.cogencysoft.flowcontrol.map.canvas.CanvasToken']">Visible</xsl:when>
						<xsl:otherwise>Non-Visible</xsl:otherwise>
					</xsl:choose>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<xsl:for-each select="token" xml:space="preserve">
						<xsl:sort select="@name"/>
						<xsl:call-template name="handleToken">
							<xsl:with-param name="token" select="."/>
						</xsl:call-template>
					</xsl:for-each>
				</td>
			</tr>
		</table>
	</xsl:template>
	<xsl:template name="handleToken">
		<xsl:param name="token" select="token"/>
		<xsl:param name="linkDef" select="1"/>
		<xsl:variable name="tokenName" select="$token/@name"/>
		<table id="clearBorderTable" width="100%">
			<tr>
				<xsl:if test="$linkDef=1">
					<a name="{$tokenName}"/>
				</xsl:if>
				<td id="section">
					<xsl:value-of select="$tokenName"/>: <font id="tokenClass">
						<xsl:call-template name="substring-after-last">
							<xsl:with-param name="string" select="$token/@class"/>
							<xsl:with-param name="delimiter" select="'.'"/>
						</xsl:call-template>
					</font>
				</td>
			</tr>
			<xsl:choose>
				<xsl:when test="/map/pipe/sourceName[text()=$tokenName]">
					<tr>
						<td id="subSection">
							Event Flow
						</td>
					</tr>
					<tr>
						<td>
							<table id="solidBorderTable" width="100%">
								<xsl:choose>
									<xsl:when test="$token/eventPipeOrderData2/event">
										<xsl:for-each select="$token/eventPipeOrderData2/event">
											<xsl:sort case-order="lower-first" select="$tokenName"/>
											<tr>
												<td>
													<xsl:call-template name="handleEventFlow">
														<xsl:with-param name="token" select="$token"/>
														<xsl:with-param name="event" select="@name"/>
													</xsl:call-template>
												</td>
											</tr>
										</xsl:for-each>
									</xsl:when>
									<xsl:otherwise>
										<xsl:variable name="asSource" select="/map/pipe/sourceName[text()=$tokenName]"/>
										<xsl:if test="$asSource">
											<xsl:for-each select="$asSource">
												<xsl:variable name="pipe" select=".."/>
												<!--
												<xsl:call-template name="printEventInfo">
													<xsl:with-param name="pipe" select="$pipe"/>
												</xsl:call-template>-->
												<xsl:call-template name="handleArgPipes">
													<xsl:with-param name="pipe" select="$pipe"/>
													<xsl:with-param name="tokenName" select="$tokenName"/>
												</xsl:call-template>
											</xsl:for-each>
										</xsl:if>
									</xsl:otherwise>
								</xsl:choose>
							</table>
						</td>
					</tr>
					<xsl:call-template name="emptyRow"/>
				</xsl:when>
			</xsl:choose>
			<xsl:call-template name="emptyRow"/>
			<xsl:call-template name="emptyRow"/>
		</table>
	</xsl:template>
	<xsl:template name="printEventInfo">
		<xsl:param name="pipe"/>
		<xsl:param name="printEventName" select="1"/>
		<xsl:if test="$printEventName='1'">
			<font color="Gray">On 
				<font id="program">
					<xsl:choose>
						<xsl:when test="starts-with($pipe/sourceEventId, '&lt;d&gt;')">
							<xsl:value-of select="substring-after($pipe/sourceEventId, '&gt;')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$pipe/sourceEventId"/>
						</xsl:otherwise>
					</xsl:choose>
				</font>
				Change
				</font>
		</xsl:if>
	</xsl:template>
	<xsl:template name="handleArgPipes">
		<xsl:param name="pipe"/>
		<xsl:param name="link" select="1"/>
		<xsl:param name="tokenName" select="tokenName"/>
		<font id="program">
			<xsl:choose>
				<xsl:when test="$link=1">
					<xsl:choose>
						<xsl:when test="starts-with($pipe/targetId, '&lt;d&gt;')">
							<xsl:call-template name="rel-link">
								<xsl:with-param name="value" select="substring-after($pipe/targetId, '&gt;')"/>
							</xsl:call-template>
						</xsl:when>
						<xsl:otherwise>
							<xsl:call-template name="rel-link">
								<xsl:with-param name="value" select="$pipe/targetId"/>
							</xsl:call-template>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
					<xsl:choose>
						<xsl:when test="starts-with($pipe/targetId, '&lt;d&gt;')">
							<xsl:value-of select="substring-after($pipe/targetId, '&gt;')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$pipe/targetId"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:otherwise>
			</xsl:choose>
			<b>.</b>
		</font>				<font id="program" color="blue">
			<xsl:choose>
				<xsl:when test="starts-with($pipe/targetMessageId, '&lt;d&gt;')">
					<xsl:value-of select="substring-after($pipe/targetMessageId, '&gt;')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$pipe/targetMessageId"/>
				</xsl:otherwise>
			</xsl:choose>
		</font>
		(
		<!-- If this pipe is of type eventType, if so it has to have a argument pipe using it as source -->
		<xsl:variable name="arguments" select="/map/pipe/sourceName[text()=$pipe/storageID]"/>
		<xsl:for-each select="$arguments">
			<xsl:variable name="source" select=".."/>
			<xsl:if test="$source/pipeTypeName='argumentPipe'">
				<!-- deal with only if the type is of type argumentPipe -->
				<xsl:choose>
					<xsl:when test="$source/targetTypeName='eventDataValue'">
						<!-- Its based on the event value as argument -->
						<font id="eventSection">
						eventValue
						</font>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="handleArgPipes">
							<xsl:with-param name="pipe" select="$source"/>
							<xsl:with-param name="tokenName" select="$tokenName"/>
						</xsl:call-template>
						<!--
						<xsl:call-template name="printPipe">
							<xsl:with-param name="pipe" select="$source"></xsl:with-param>
						</xsl:call-template>
					-->
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
			<!-- Separate the arugments by comma only if there is next element available -->
			<xsl:if test="$arguments[position()+1]">,</xsl:if>
		</xsl:for-each>)

		<!--
		<tr>
			<td>
				<xsl:value-of select="$tokenName"/>
			</td>
		</tr>
		-->
		<xsl:if test="$tokenName!=$pipe/targetId">
			<tr>
				<xsl:variable name="child" select="//token[@name=$pipe/targetId]"/>
				<td>
					<!--
					<xsl:value-of select="$child/@name"/>
					-->
				</td>
				<xsl:if test="/map/pipe/sourceName[text()=$pipe/targetId]">
					<td>
						<xsl:call-template name="handleToken">
							<xsl:with-param name="token" select="$child"/>
							<xsl:with-param name="linkDef" select="0"/>
						</xsl:call-template>
					</td>
				</xsl:if>
			</tr>
		</xsl:if>
	</xsl:template>
	<xsl:template name="printPipe">
		<xsl:param name="pipe"/>
		<table border="1">
			<tr>
				<td>PipeName</td>
				<td>
					<xsl:value-of select="$pipe/pipeName"/>
				</td>
			</tr>
			<tr>
				<td>PipeType</td>
				<td>
					<xsl:value-of select="$pipe/pipeTypeName"/>
				</td>
			</tr>
			<tr>
				<td>Source</td>
				<td>
					<xsl:value-of select="$pipe/sourceName"/>
				</td>
			</tr>
			<tr>
				<td>SourceEvent</td>
				<td>
					<xsl:value-of select="$pipe/sourceEventId"/>
				</td>
			</tr>
			<tr>
				<td>SourceEventSig</td>
				<td>
					<xsl:value-of select="$pipe/sourceEventSignature"/>
				</td>
			</tr>
			<tr>
				<td>SourceEventSetID</td>
				<td>
					<xsl:value-of select="$pipe/sourceEventSetId"/>
				</td>
			</tr>
			<tr>
				<td>TargetType</td>
				<td>
					<xsl:value-of select="$pipe/targetTypeName"/>
				</td>
			</tr>
			<tr>
				<td>Target</td>
				<td>
					<xsl:value-of select="$pipe/targetId"/>
				</td>
			</tr>
			<tr>
				<td>TargetMessage</td>
				<td>
					<xsl:value-of select="$pipe/targetMessageId"/>
				</td>
			</tr>
			<tr>
				<td>TargetMessageSig</td>
				<td>
					<xsl:value-of select="$pipe/targetMessageSignature"/>
				</td>
			</tr>
			<tr>
				<td>Layout</td>
				<td>
					<xsl:value-of select="$pipe/pipeLayout"/>
				</td>
			</tr>
			<tr>
				<td>DynamicMethod</td>
				<td>
					<xsl:value-of select="$pipe/dynamicMethod"/>
				</td>
			</tr>
			<tr>
				<td>DynamicEvent</td>
				<td>
					<xsl:value-of select="$pipe/dynamicEvent"/>
				</td>
			</tr>
			<tr>
				<td/>
				<td/>
			</tr>
		</table>
	</xsl:template>
	<xsl:template name="handleEventFlow">
		<xsl:param name="token"/>
		<xsl:param name="event"/>
		<table>
			<tr>
				<td id="eventSection">
					<font id="program">
						<xsl:value-of select="$event"/>
					</font>
				</td>
			</tr>
			<xsl:choose>
				<xsl:when test="pipeOrder">
					<xsl:for-each select="pipeOrder">
						<xsl:variable name="pipeName" select="pipe-name"/>
						<tr>
							<td id="emptyCell">
								...
							</td>
							<td id="pipe">
								Order <xsl:value-of select="@order"/> : <font color="green">
									<xsl:value-of select="$pipeName"/>
								</font>
<!--							</td>
							<td>-->
								<xsl:variable name="pipe" select="/map/pipe[pipeName=$pipeName]"/>
								<!--
								<xsl:call-template name="printEventInfo">
									<xsl:with-param name="pipe" select="$pipe"/>
									<xsl:with-param name="printEventName" select="0"/>
								</xsl:call-template>
								-->
								<xsl:call-template name="handleArgPipes">
									<xsl:with-param name="pipe" select="$pipe"/>
									<xsl:with-param name="tokenName" select="$token/@name"/>
								</xsl:call-template>
							</td>
						</tr>
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<xsl:variable name="asSource" select="/map/pipe[sourceName=$token/@name]"/>
					<xsl:if test="$asSource">
						<xsl:for-each select="$asSource">
							<xsl:variable name="pipe" select="."/>
							<xsl:choose>
								<xsl:when test="starts-with($pipe/sourceEventId, '&lt;d&gt;')">
									<xsl:if test="substring-after($pipe/sourceEventId, '&gt;')=$event">
										<xsl:call-template name="hasEvent">
											<xsl:with-param name="pipe" select="$pipe"/>
										</xsl:call-template>
									</xsl:if>
								</xsl:when>
								<xsl:otherwise>
									<xsl:if test="$pipe/sourceEventId=$event">
										<xsl:call-template name="hasEvent">
											<xsl:with-param name="pipe" select="$pipe"/>
										</xsl:call-template>
									</xsl:if>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:for-each>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
		</table>
	</xsl:template>
	<xsl:template name="hasEvent">
		<xsl:param name="pipe"/>
		<tr>
			<td id="blankCell">
				.
			</td>
			<td id="pipe">
					Order <xsl:value-of select="position()"/> : <font color="green">
					<xsl:value-of select="$pipe/pipeName"/>
				</font>
			</td>
			<td>
				<xsl:call-template name="handleArgPipes">
					<xsl:with-param name="pipe" select="$pipe"/>
				</xsl:call-template>
			</td>
		</tr>
	</xsl:template>
	<xsl:template name="substring-after-last">
		<xsl:param name="string"/>
		<xsl:param name="delimiter"/>
		<xsl:choose>
			<xsl:when test="contains($string, $delimiter)">
				<xsl:call-template name="substring-after-last">
					<xsl:with-param name="string" select="substring-after($string, $delimiter)"/>
					<xsl:with-param name="delimiter" select="$delimiter"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$string"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- 
	This template captures the flow of the requested event for the token.
		TokenName
			EventName
				Order<pipe>TokenName
					Event 1
						Order <pipe>TokenName 
					Event 2
						Order <pipe>TokenName 

		The above is built recursively.
	-->
	<xsl:template name="captureFlow">
		<xsl:param name="token"/>
		<xsl:param name="event"/>
	</xsl:template>
	<xsl:template name="emptyRow">
		<tr>
			<td id="emptyCell">...</td>
		</tr>
	</xsl:template>
	<xsl:template name="rel-link">
		<xsl:param name="value" select="value"/>
		<xsl:element name="a">
			<xsl:attribute name="href">#<xsl:value-of select="$value"/></xsl:attribute>
			<xsl:value-of select="$value"/>
		</xsl:element>
	</xsl:template>
	<xsl:template name="abs-link">
		<xsl:param name="value" select="value"/>
		<xsl:element name="a">
			<xsl:attribute name="href">#<xsl:value-of select="$value"/></xsl:attribute>
			<xsl:value-of select="$value"/>
		</xsl:element>
	</xsl:template>
	<xsl:template name="colorCode">
		<xsl:param name="position" select="position"/>
		<xsl:choose>
			<xsl:when test="($position mod 2)= 0">
				<xsl:attribute name="id"><xsl:text>colorRow</xsl:text></xsl:attribute>
			</xsl:when>
			<xsl:otherwise>
				<xsl:attribute name="id"><xsl:text>colorRow</xsl:text></xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:if test="($position mod 2)= 0">
			<xsl:attribute name="id"><xsl:text>ordRow</xsl:text></xsl:attribute>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
