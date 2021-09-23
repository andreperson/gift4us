<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty sucesso}">
	<div class="col">
		<div class="alert alert-success w-100" id="sucesso">
			${sucesso}
		</div>
	</div>
	<br />
</c:if>