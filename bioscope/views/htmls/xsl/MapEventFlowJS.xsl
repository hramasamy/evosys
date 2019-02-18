<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xlink="http://www.w3.org/1999/xlink">
	<xsl:template match="/">
		<html>
			<head>
				<!--<LINK rel="stylesheet" type="text/css" href="/BugTrack/htmls/css/snapshot.css" title="style1"/>-->
				<LINK rel="stylesheet" type="text/css" href="C:/apache/Tomcat 5.0/webapps/BugTrack/htmls/css/snapshot.css" title="style1"/>
				<link rel="stylesheet" id="mainStyle" href="css/mapnav.css" type="text/css"/>
				<script src="js/util/ArrayUtils.js"/>
				<script src="js/util/AbstractCollection.js"/>
				<script src="js/util/BeanUtils.js"/>
				<script src="js/util/ArrayList.js"/>
				<script src="js/util/AbstractMap.js"/>
				<script src="js/util/HashMap.js"/>
				<script src="js/html/TreeNode.js"/>
				<script src="js/html/Menu.js"/>
				<script>
					window.contextPath="/BugTrack";
					var mapName = "";
					appPath = window.location.protocol + "//" + window.location.host + window.contextPath;
					
					function buildTree() {
						var root = new Menu("root", null, null);
						root.uiHolderName = "treeHolder";
						window.menu = root;
						
						<xsl:variable name="mapName" select="/map/@name"/>
						
						var tn = new Menu("<xsl:value-of select="$mapName"/>", root, null);
						root.addChildNode(tn);
						
						<xsl:apply-templates select="map"/>
					<!--
						var cNode = new Menu("ContactManager", tn, null);
						tn.addChildNode(cNode);
						cNode.addStringChildNode("AccountManagement");
						cNode.addStringChildNode("ContactManagement");
						cNode.addStringChildNode("RelationshipManagement");
						-->
						window.document.getElementById("treeHolder").innerHTML = root.toHtmlString();
						window.document.bgColor="lightgrey";
						window.document.fgColor="darkred";
						root.eventHandlerName = "showMap";
						this.showMap = showMap;
					}
					
					function showMap(mapName) {
						window.top.frames["contentFrame"].location.href=appPath + "/jsps/mapViewer.jsp?mapName="+mapName;
					}
					
				</script>
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
				<table border="0"/>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="map">
		<xsl:variable name="nodeName" select="'tn'"/>
		<xsl:for-each select="token" xml:space="preserve">
			<xsl:sort select="@name"/>
			<xsl:call-template name="handleToken">
				<xsl:with-param name="token" select="."/>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="handleToken">
		<xsl:param name="token" select="token"/>
		<xsl:param name="linkDef" select="1"/>
		<xsl:param name="parent" select="'tn'"/>
		<xsl:variable name="tokenName" select="$token/@name"/>
		<xsl:variable name="tmp" select="concat($tokenName, 'Node')"/>
		
		<xsl:variable name="pass-one">
			<xsl:call-template name="global-replace">
				<xsl:with-param name="string" select="$tmp"/>
			</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="nodeName">
			<xsl:call-template name="global-replace">
				<xsl:with-param name="string" select="$pass-one"/>
			</xsl:call-template>
		</xsl:variable>
		
		var <xsl:value-of select="$nodeName"/>  = new Menu("<xsl:value-of select="$tokenName"/>", <xsl:value-of select="$parent"/>, null);
		
		<xsl:value-of select="$parent"/>.addChildNode(<xsl:value-of select="$nodeName"/>);

		<xsl:choose>
			<xsl:when test="/map/pipe/sourceName[text()=$tokenName]">
				<xsl:choose>
					<xsl:when test="$token/eventPipeOrderData2/event">
						<xsl:for-each select="$token/eventPipeOrderData2/event">
							<xsl:sort case-order="lower-first" select="$tokenName"/>
							<xsl:call-template name="handleEventFlow">
								<xsl:with-param name="token" select="$token"/>
								<xsl:with-param name="event" select="@name"/>
								<xsl:with-param name="parent" select="$nodeName"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						<xsl:variable name="asSource" select="/map/pipe/sourceName[text()=$tokenName]"/>
						<xsl:if test="$asSource">
							<xsl:for-each select="$asSource">
								<xsl:variable name="pipe" select=".."/>
								<xsl:call-template name="handleArgPipes">
									<xsl:with-param name="pipe" select="$pipe"/>
									<xsl:with-param name="tokenName" select="$tokenName"/>
									<xsl:with-param name="link" select="0"/>
								</xsl:call-template>
							</xsl:for-each>
						</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="handleArgPipes">
		<xsl:param name="pipe"/>
		<xsl:param name="link" select="1"/>
		<xsl:param name="tokenName" select="tokenName"/>
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
		</xsl:choose>.<xsl:choose>
			<xsl:when test="starts-with($pipe/targetMessageId, '&lt;d&gt;')">
				<xsl:value-of select="substring-after($pipe/targetMessageId, '&gt;')"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$pipe/targetMessageId"/>
			</xsl:otherwise>
		</xsl:choose>(<xsl:variable name="arguments" select="/map/pipe/sourceName[text()=$pipe/storageID]"/>
		<xsl:for-each select="$arguments">
			<xsl:variable name="source" select=".."/>
			<xsl:if test="$source/pipeTypeName='argumentPipe'">
				<xsl:choose>
					<xsl:when test="$source/targetTypeName='eventDataValue'">eventValue);</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="handleArgPipes">
							<xsl:with-param name="pipe" select="$source"/>
							<xsl:with-param name="tokenName" select="$tokenName"/>
							<xsl:with-param name="link" select="0"/>
						</xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
			<xsl:choose>
				<xsl:when test="$arguments[position()+1]">,</xsl:when>
				<xsl:otherwise></xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
		
		<xsl:if test="$tokenName!=$pipe/targetId">
				<xsl:variable name="child" select="//token[@name=$pipe/targetId]"/>
				<xsl:if test="/map/pipe/sourceName[text()=$pipe/targetId]">
						<xsl:call-template name="handleToken">
							<xsl:with-param name="token" select="$child"/>
							<xsl:with-param name="linkDef" select="0"/>
						</xsl:call-template>
				</xsl:if>
		</xsl:if>
	</xsl:template>
	<xsl:template name="close-parenth">
		<xsl:param name="arg-length"/>
		
	</xsl:template>
	<xsl:template name="handleEventFlow">
		<xsl:param name="token"/>
		<xsl:param name="event"/>
		<xsl:param name="parent"/>
		<xsl:variable name="string-dirty" select="concat($event, 'Node')"/>
		
		<xsl:variable name="nodeName">
			<xsl:call-template name="global-replace">
				<xsl:with-param name="string" select="$string-dirty"/>
			</xsl:call-template>
		</xsl:variable>
		
		var <xsl:value-of select="$nodeName"/>  = new Menu("<xsl:value-of select="$event"/>", <xsl:value-of select="$parent"/>, null);
		<xsl:value-of select="$parent"/>.addChildNode(<xsl:value-of select="$nodeName"/>);
		
		<xsl:choose>
			<xsl:when test="pipeOrder">
				<xsl:for-each select="pipeOrder">
					<xsl:variable name="pipeName" select="pipe-name"/>
					<xsl:variable name="tmp" select="concat($token/@name, $event)"/>
					<xsl:variable name="tmp1" select="concat($tmp, @order)"/>
					<xsl:variable name="string" select="concat($nodeName, $tmp1)"/>
					
					<xsl:variable name="cNode">
						<xsl:call-template name="global-replace">
							<xsl:with-param name="string" select="$string"/>
						</xsl:call-template>
					</xsl:variable>
					
					var <xsl:value-of select="$cNode"/>= new Menu("Order<xsl:value-of select="@order"/>:<xsl:value-of select="$pipeName"/>", <xsl:value-of select="$nodeName"/>); 
					<xsl:value-of select="$nodeName"/>.addChildNode(<xsl:value-of select="$nodeName"/>);
					<xsl:variable name="pipe" select="/map/pipe[pipeName=$pipeName]"/>
					<xsl:call-template name="handleArgPipes">
						<xsl:with-param name="pipe" select="$pipe"/>
						<xsl:with-param name="link" select="0"/>
						<xsl:with-param name="tokenName" select="$token/@name"/>
						<xsl:with-param name="parent" select="$cNode"/>
					</xsl:call-template>
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
	</xsl:template>
	<xsl:template name="hasEvent">
		<xsl:param name="pipe"/>
		Order <xsl:value-of select="position()"/>:<xsl:value-of select="$pipe/pipeName"/>
		<xsl:call-template name="handleArgPipes">
			<xsl:with-param name="pipe" select="$pipe"/>
			<xsl:with-param name="link" select="0"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:param name="replace" select="' '"/>
	<xsl:param name="replace1" select="' /'"/>
	<xsl:param name="by" select="'_'"/>
	
	<xsl:template name="clean-name">
		<xsl:param name="string"/>
		<xsl:variable name="first-pass">	
		<xsl:choose>
			<xsl:when test="contains($string, $replace)">
				<xsl:variable name="cNode">
					<xsl:call-template name="global-replace">
						<xsl:with-param name="string" select="$string"/>
					</xsl:call-template>
				</xsl:variable>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$string"/>
			</xsl:otherwise>
		</xsl:choose>
		</xsl:variable>
		
		Value of first pass is <xsl:value-of select="$first-pass"/>
		<xsl:variable name="second-pass">
		<xsl:choose>
			<xsl:when test="contains($first-pass, $replace1)">
				<xsl:variable name="cNode">
					<xsl:call-template name="global-replace-1">
						<xsl:with-param name="string" select="$string"/>
					</xsl:call-template>
				</xsl:variable>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$string"/>
			</xsl:otherwise>
		</xsl:choose>
		</xsl:variable>
				Value of second pass is <xsl:value-of select="$second-pass"/>
				
		<xsl:value-of select="$second-pass"/>
	</xsl:template>
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
	<xsl:template name="global-replace-1">
		<xsl:param name="string"/>
		<xsl:choose>
			<xsl:when test="contains($string, $replace1)">
				<xsl:value-of select="substring-before($string, $replace1)"/>
				<xsl:value-of select="$by"/>
				<xsl:call-template name="global-replace-1">
					<xsl:with-param name="string" select="substring-after($string, $replace1)"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$string"/>
			</xsl:otherwise>
		</xsl:choose>
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
