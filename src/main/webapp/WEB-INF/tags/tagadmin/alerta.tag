<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty alerta}">
	<div class="col">
		<div class="alert alert-warning w-100" id="alerta">
			${alerta}
		</div>
	</div>
	<br />
</c:if>