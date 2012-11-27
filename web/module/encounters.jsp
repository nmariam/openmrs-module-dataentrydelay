
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="/WEB-INF/view/admin/maintenance/localHeader.jsp"%>
<%@ include file="localHeader.jsp"%>
<!-- pagination header files -->
<openmrs:htmlInclude file="/moduleResources/dataentrydelay/myjquery.js" />
<!--/openmrs jquery/<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js"/>-->
<openmrs:htmlInclude
	file="/moduleResources/dataentrydelay/jquery.dataTables.js" />
<openmrs:htmlInclude
	file="/moduleResources/dataentrydelay/demo_page.css" />
<openmrs:htmlInclude
	file="/moduleResources/dataentrydelay/demo_table.css" />
<!--  -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#example').dataTable(
				{
			"sPaginationType" : "full_numbers"
		});		 
	});
</script>
<div>
	<form action="" method="get">
		<table>
			<tr>
				<td><spring:message code="dataentrydelay.startDate" />
				</td>
				<td><openmrs_tag:dateField formFieldName="startDate"
						startValue="${startdate}" />
				</td>
				<td><spring:message code="dataentrydelay.endDate" />
				</td>
				<td><openmrs_tag:dateField formFieldName="endDate"
						startValue="${enddate}" />
				</td>
				<!-- 
				<td><spring:message code="dataentrydelay.user" /></td>
				<td><openmrs_tag:userField formFieldName="userId"
						initialValue="${user}" />
				</td>
				
				<div class="boxHeader" ; style="text-align: center;">
		
	</div>
 				-->
				<td style="width: 100px"></td>
				<td><spring:message code="dataentrydelay.location" />
				</td>
				<td><openmrs_tag:locationField formFieldName="locationId"
						initialValue="${param.locationId}" />
				</td>
				<td><spring:message code="dataentrydelay.delay" />
				</td>
				<td><select name="delay">
						<option value="-2" <c:if test="${-2==delay}">selected</c:if>>
							<spring:message code="dataentrydelay.any" />
						</option>
						<c:forEach items="1,2,3,4,5,6,7,8,9,10,11" var="days">
							<option value="${days}"
								<c:if test="${days == delay }">selected</c:if>>${days}</option>
						</c:forEach>
						<option value="12" <c:if test="${12==delay}">selected</c:if>>
							<spring:message code="dataentrydelay.moreThanTwelve" />
						</option>
						<option value="-1" <c:if test="${-1==delay}">selected</c:if>>
							<spring:message code="dataentrydelay.error" />
						</option>
				</select> <spring:message code="dataentrydelay.days" />
				</td>
			</tr>
			<tr style="float: left; padding-bottom: 25px">
				<td colspan="2" align="center"><input type="submit"
					value="<spring:message code="dataentrydelay.update" />" />
				</td>
			</tr>
		</table>
	</form>
</div>
<fieldset>
	<legend>
		<span class="boxHeader"><spring:message
					code="dataentrydelay.resultMsg" />
		|<spring:message code="dataentrydelay.sortTableTitle" /></span>
	</legend>

	<div id="dt_example">
		<div id="container">
			<table cellpadding="0" cellspacing="0" border="0" class="display"
				id="example">
				<thead>
					<tr>
						<th><spring:message code="dataentrydelay.edit" />
						</th>
						<th><spring:message code="dataentrydelay.patient" />
						</th>
						<th><spring:message code="dataentrydelay.encdate" />
						</th>
						<th><spring:message code="dataentrydelay.dateCreated" />
						</th>
						<th><spring:message code="dataentrydelay.type" />
						</th>
						<th><spring:message code="dataentrydelay.location" />
						</th>
						<th><spring:message code="dataentrydelay.creator" />
						</th>
						<th><spring:message code="dataentrydelay.delay" />
						</th>
						<th><spring:message code="dataentrydelay.error" />
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="encount" items="${encounters}">
						<tr>
							<td><a
								href="${pageContext.request.contextPath}/admin/encounters/encounter.form?encounterId=${encount.key.encounterId}">
									<img src="${pageContext.request.contextPath}/images/edit.gif"
									title="Edit" border="0"> </a></td>
							<td>${encount.key.patient.personName}</td>
							  <!-- 
							<td><openmrs:formatDate date="${encount.key.encounterDatetime}"/></td>
							<td><openmrs:formatDate date="${encount.key.dateCreated}"/></td>
							
							 
							<td>${encount.key.encounterDatetime}</td>
							<td>${encount.key.dateCreated}</td>
							 -->
							<td><fmt:formatDate value="${encount.key.encounterDatetime}" pattern="yyyy/MM/dd"/></td>
							<td><fmt:formatDate value="${encount.key.encounterDatetime}" pattern="yyyy/MM/dd"/></td>
							
							
							<td>${encount.key.encounterType.name}</td>
							<td>${encount.key.location.name}</td>
							<td>${encount.key.creator.username}</td>
							<td>${(encount.value >= 0) ? encount.value : -1}</td>
							<td>${(encount.value >= 0) ? "" : "Date created before encounter date "}</td>
							<!--
							<td><c:choose>
									<c:when test="${encount.value>= 0}">
					${(encount.value)}
					</c:when>
									<c:otherwise>
										<spring:message code="dataentrydelay.dateCreatedBefore" />
									</c:otherwise>
								</c:choose>
							</td>
							  -->
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</fieldset>
<%@ include file="/WEB-INF/template/footer.jsp"%>