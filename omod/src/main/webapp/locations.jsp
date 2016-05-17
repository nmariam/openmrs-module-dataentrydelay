<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="/WEB-INF/view/admin/maintenance/localHeader.jsp"%>
<%@ include file="localHeader.jsp"%>

<form action="" method="post">
	<table>
		<!-- <tr>-->
		<td><spring:message code="dataentrydelay.startDate" />
		</td>
		<td><openmrs_tag:dateField formFieldName="startDate"
				startValue="${startDate}" />
		</td>
		<!-- </tr>-->
		<!-- <tr>-->
		<td><spring:message code="dataentrydelay.endDate" />
		</td>
		<td><openmrs_tag:dateField formFieldName="endDate"
				startValue="${endDate}" />
		</td>
		<!-- </tr>-->
		<tr style="float: left; padding-bottom: 25px">
			<td colspan="2" align="center"><input
				type="<spring:message code="dataentrydelay.submit" />"
				value="<spring:message code="dataentrydelay.update" />" />
			</td>
		</tr>
	</table>
</form>
<br />
<b class="boxHeader"><spring:message code="dataentrydelay.resultMsg" />
</b>
<div class="box">
	<table cellpadding="2" cellspacing="0" width="100%" border="1">
		<tr>
			<th><spring:message code="dataentrydelay.location" />
			</th>
			<th><spring:message code="dataentrydelay.form" />
			</th>

			<th colspan="14"><spring:message code="dataentrydelay.between" />
			</th>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
			<c:forEach items="1,2,3,4,5,6,7,8,9,10,11" var="days">
				<th>${days}</th>
			</c:forEach>
			<th>12+</th>
			<th><spring:message code="dataentrydelay.error" />
			</th>
		</tr>
		<!-- for each location -->
		<c:forEach var="location" items="${allLocations}">
			<tr>
				<td><a
					href="${pageContext.request.contextPath}/module/dataentrydelay/users.htm?locationId=${location.key.locationId}&startDate=<openmrs:formatDate date="${startDate}"/>&endDate=<openmrs:formatDate date="${endDate}"/>">${location.key.name}</a>
				</td>
				<td>${location.value.totalDelay}</td>
				<c:forEach var="days" items="1,2,3,4,5,6,7,8,9,10,11,12">
					<td><a
						href="${pageContext.request.contextPath}/module/dataentrydelay/encounters.htm?delay=${days}&locationId=${location.key.locationId}&startDate=<openmrs:formatDate date="${startDate}"/>&endDate=<openmrs:formatDate date="${endDate}"/>">${(location.value.delayStatistics[days]!=0)?location.value.delayStatistics[days]:""}</a>
					</td>
				</c:forEach>
				<td><a
					href="${pageContext.request.contextPath}/module/dataentrydelay/encounters.htm?delay=-1&locationId=${location.key.locationId}&startDate=<openmrs:formatDate date="${startDate}"/>&endDate=<openmrs:formatDate date="${endDate}"/>">${(location.value.delayStatistics[13]!=0)?location.value.delayStatistics[13]:""}</a>
				</td>
			</tr>
		</c:forEach>
		<!--  end for each location -->
	</table>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>