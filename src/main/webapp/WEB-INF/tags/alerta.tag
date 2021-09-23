<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${not empty alertademsg}">
	<div class="header">
		<div class="alert alert-warning w-100" id="alerta">
			${alertademsg}
		</div>
	</div>
	<br />
</c:if>