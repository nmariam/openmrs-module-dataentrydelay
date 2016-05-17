<h2>
	<spring:message code="dataentrydelay.title" />
</h2>
<ul id="menu">
	<li
		class="first<c:if test='<%= request.getRequestURI().contains("locations") %>'> active</c:if>"><a
		href="${pageContext.request.contextPath}/module/dataentrydelay/locations.htm"><spring:message
				code="dataentrydelay.LocationRange" />
	</a>
	</li>
	<li
		<c:if test='<%= request.getRequestURI().contains("users") %>'> class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/dataentrydelay/users.htm"><spring:message
				code="dataentrydelay.ProviderRange" />
	</a>
	</li>

	<li
		<c:if test='<%= request.getRequestURI().contains("encounters") %>'> class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/dataentrydelay/encounters.htm"><spring:message
				code="dataentrydelay.Encounters" />
	</a>
	</li>
</ul>

