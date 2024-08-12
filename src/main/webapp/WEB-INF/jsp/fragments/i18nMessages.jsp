<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:forEach var="key" items='${["common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus","common.confirm"]}'>
  i18n["${key}"] = "<spring:message code="${key}"/>";
</c:forEach>