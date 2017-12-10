<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<div class="container-fluid" style="margin-top: 1%">
    <c:if test="${sessionScope.message != null}">
        <p class="alert alert-info" role="alert">
	    <c:out value="${sessionScope.message}" />
	    <c:remove var="message" scope="session" />
	</p>
    </c:if>
</div>
