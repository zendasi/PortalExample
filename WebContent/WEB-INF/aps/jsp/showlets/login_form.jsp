<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib prefix="c" uri="c.tld" %>

<wp:headInfo type="CSS" info="showlets/login_form.css" />

<div class="login_form">
<h2 class="title"><wp:i18n key="RESERVED_AREA" /></h2>
<c:choose>
	<c:when test="${sessionScope.currentUser != 'guest'}">
		<p><wp:i18n key="WELCOME" />, <em><c:out value="${sessionScope.currentUser}"/></em>!</p>
		
		<c:if test="${sessionScope.currentUser.japsUser}">
			<c:if test="${!sessionScope.currentUser.credentialsNotExpired}">
				<p><wp:i18n key="USER_STATUS_EXPIRED_PASSWORD" />: <a href="<wp:info key="systemParam" paramName="applicationBaseURL" />do/editPassword.action"><wp:i18n key="USER_STATUS_EXPIRED_PASSWORD_CHANGE" /></a></p>
			</c:if>
		</c:if>
		<wp:ifauthorized permission="enterBackend">
		
		<h3><wp:i18n key="ADMINISTRATION" />:</h3>
		<p>
		<a href="<wp:info key="systemParam" paramName="applicationBaseURL" />do/main.action?request_locale=<wp:info key="currentLang" />&amp;backend_client_gui=normal" title="<wp:i18n key="ADMINISTRATION_BASIC_GOTO" />"><wp:i18n key="ADMINISTRATION_BASIC" /></a> | 
		<a href="<wp:info key="systemParam" paramName="applicationBaseURL" />do/main.action?request_locale=<wp:info key="currentLang" />&amp;backend_client_gui=advanced" title="<wp:i18n key="ADMINISTRATION_MINT_GOTO" />"><wp:i18n key="ADMINISTRATION_MINT" /></a>
		</p>
		</wp:ifauthorized>
		<p><a href="<wp:info key="systemParam" paramName="applicationBaseURL" />do/logout.action"><wp:i18n key="LOGOUT" /></a></p>
	</c:when>
	<c:otherwise>
	
	<c:if test="${accountExpired}">
		<p><wp:i18n key="USER_STATUS_EXPIRED" /></p>
	</c:if>
	<c:if test="${wrongAccountCredential}">
		<p><wp:i18n key="USER_STATUS_CREDENTIALS_INVALID" /></p>
	</c:if>
	
	
	<form action="<wp:url/>" method="post">
		<p>
			<label for="username"><wp:i18n key="USERNAME" />:</label><br />
			<input id="username" type="text" name="username" class="text" />
		</p>
		<p>
			<label for="password"><wp:i18n key="PASSWORD" />:</label><br />
			<input id="password" type="password" name="password" class="text" />
		</p>
		<p><input type="submit" value="Ok" class="button" /></p>
	</form>
	</c:otherwise>
</c:choose>

</div>