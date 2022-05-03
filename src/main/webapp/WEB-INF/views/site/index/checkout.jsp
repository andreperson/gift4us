<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags/tag-site" prefix="my"%>
<%@ page import="br.com.gift4us.urls.ListaDeURLs"%>
<c:url value="${urlRecursos}image" var="urlrecursos" />
<c:url value="${urlpadrao}_produtos" var="urlproduto" />
<c:url value="${urlpadrao}_sliders" var="urlslider" />
<c:url value="${urlpadrao}_banners" var="urlbanner" />
<c:url value="${urlpadrao}_modelos" var="urlmodelo" />
<c:url value="${urlpadrao}_categorias" var="urlcategoria" />
<c:url value="${urlRecursos	}resources-site/image/icons" var="urlicons" />

<my:template title="${mensagens.get('NomeDoProjeto').valor}"
	fluido="false" jsFiles="${jsFiles}">
	<div id="container" style="padding-top: 180px;">
		<div class="container">
			<div class="row">
				<!--Middle Part Start-->
				<div id="content" class="col-sm-12">
					<h1 class="title">Orçamento</h1>
					<div class="table-responsive">
						<table class="table table-bordered">
							<thead>
								<tr>
									<td class="text-center">Imagem</td>
									<td class="text-left">Produto</td>
									<td class="text-left">Preço</td>
									<td class="text-left">Quantidade</td>
									<td class="text-left">Total</td>
								</tr>
							</thead>
							<tbody>
								<c:set var="total" value="0" />
								<c:set var="soma" value="0" />
								<c:forEach items="${lstProdutos}" var="produto">
									<c:set var="urlprodutomontada" scope="application"
										value="${urlmodelo}/modelo-220x330.jpg" />
									<c:if test="${not empty produto.imagem}">
										<c:set var="urlprodutomontada" scope="application"
											value="${urlproduto}/${produto.anunciante.id}/${produto.id}/${produto.imagem}" />
									</c:if>
									<c:set var="desconto" value="0.${produto.desconto}" />
									<c:set var="precocomdesconto"
										value="${produto.preco - (produto.preco * desconto)}" />
									<c:set var="total"
										value="${total + (produto.qtdademin * precocomdesconto)}" />
									<tr>
										<td class="text-center"><a
											href="../../site/produtos/produto/${produto.id}"><img
												src="${urlprodutomontada}" alt="${produto.titulo}"
												title="${produto.titulo}" width="40px;"
												class="img-thumbnail" /></a></td>
										<td class="text-left"><small>${produto.titulo}</small></td>
										<td class="text-left"><fmt:formatNumber
												value="${precocomdesconto}" minFractionDigits="2"
												type="currency" /></td>
										<td class="text-left">
											<div id="quantity_${produto.id}">${produto.qtdademin}</div>
										</td>
										<td class="text-left"><c:set var="soma"
												value="${produto.qtdademin * precocomdesconto }" />
											<div>
												<fmt:formatNumber value="${soma }" minFractionDigits="2"
													type="currency" />
											</div></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
					</div>

					<div class="row">
						<div class="col-sm-4 col-sm-offset-8">
							<table class="table table-bordered">
								<tr>
									<td class="text-right"><strong>Quantidade de
											Produtos:</strong></td>
									<td class="text-right"><fmt:formatNumber value="${total }"
											minFractionDigits="2" type="currency" /></td>
								</tr>
							</table>
						</div>
					</div>
					<c:url var="urlform" value="<%=ListaDeURLs.CHECKOUT%>" />
					<form class="msbc-validator-form" id="formulario-form"
						name="formcheckout" action="${urlform}" method="POST">
						<input type="hidden" id="${_csrf.parameterName}"
							name="${_csrf.parameterName}" value="${_csrf.token}" />
						<div class="row">
							<table class="table table-bordered">
								<tr>
									<td class="text-left">${mensagens.get('OrcamentoFormularioNome').valor}:</td>
									<td class="text-left"><input type="text" id="nome"
										name="nome" class="form-control msbc-validator"
										value="${orcamento.nome}"
										data-msbc-required="Campo obrigatório"
										data-msbc-maxlength="100|O campo deve ter no máximo {value} caracteres" />
										<span id="msgnome" nome="msgnome" style="color:red;"></span>
										</td>
									<td class="text-left">${mensagens.get('OrcamentoFormularioEmail').valor}:</td>
									<td class="text-left"><input type="text" id="email"
										name="email" class="form-control msbc-validator"
										value="${orcamento.email}"
										data-msbc-required="Campo obrigatório"
										data-msbc-maxlength="100|O campo deve ter no máximo {value} caracteres" />
										<span id="msgemail" nome="msgemail" style="color:red;"></span>
										</td>
									<td class="text-left">${mensagens.get('OrcamentoFormularioCelular').valor}:</td>
									<td class="text-left"><input type="text" id="ddd"
										name="ddd" style="width: 40px !important;" maxlength="2"
										class="form-control msbc-validator" value="${orcamento.ddd}"
										data-msbc-required="Campo obrigatório"
										data-msbc-maxlength="2|O campo deve ter no máximo {value} caracteres" />
									</td>
									<td class="text-left"><input type="text" id="celular"
										name="celular" style="width: 150px !important"
										class="form-control msbc-validator"
										value="${orcamento.celular}"
										data-msbc-required="Campo obrigatório"
										data-msbc-maxlength="100|O campo deve ter no máximo {value} caracteres" />
									</td>
								</tr>
							</table>
						</div>

						<div class="buttons">
							<div class="pull-right">
								<input
									class="btn btn-primary w-100 msbc-validator-button-submit"
									type="submit"
									value="${mensagens.get('OrcamentoFormularioBotaoEnviar').valor}" />
							</div>
						</div>

					</form>
					<div class="buttons">
						<div class="pull-left">
							<a href="../index" class="btn btn-default">Continue Comprando</a>
						</div>
					</div>

				</div>
				<!--Middle Part End -->
			</div>
		</div>
	</div>
</my:template>