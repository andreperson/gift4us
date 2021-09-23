<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty erros}">
	<div class="col">
		<div class="alert alert-danger">
			<ul>
				<c:forEach var="mensagem" items="${erros}">
					<li>${mensagem}</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</c:if>