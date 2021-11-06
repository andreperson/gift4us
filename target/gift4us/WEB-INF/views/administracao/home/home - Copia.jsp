<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>

<my:template title="${mensagens.get('NomeDoProjeto').valor}"  fluido="false">
	<div class="col">
		<div class="card w-100">
			<div class="card-header">
				<h1>${mensagens.get('NomeDoProjeto').valor}</h1>
			</div>
			<div class="card-body">
				<div class="mb-3">
					<br /> <br /> <br />
					<h1 class="text-center">
						${mensagens.get('SaudacaoHome').valor}
						<span><sec:authentication property="principal.nome" /></span>
					</h1>
					<br /> <br /> <br />
				</div>
			</div>
		</div>
	</div>
</my:template>