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

<my:template title="${mensagens.get('NomeDoProjeto').valor}"
	fluido="false">
	
	<c:set var="urlprodutomontada" scope="application" value="${urlmodelo}/modelo-350x525.jpg" />
	<c:if test="${not empty produto.imagem}">
		<c:set var="urlprodutomontada" scope="application"
			value="${urlproduto}/${produto.anunciante.id}/${produto.id}/${produto.imagem}" />
	</c:if>
	
  <div id="container" style="padding-top: 180px;">
    <div class="container">
      <div class="row">
        <!--Middle Part Start-->
        <div id="content" class="col-sm-9">
          <div itemscope itemtype="http://schema.org/Product">
            <h1 class="title" itemprop="name">${produto.titulo}</h1>
            <h6>${produto.categoria.nome}</h6>
            <div class="row product-info">
              <div class="col-sm-6">
                <div class="image"><img class="img-responsive" itemprop="image" id="img_0" src="${urlprodutomontada}" title="${produto.titulo}" alt="${produto.titulo}" data-zoom-image="${urlprodutomontada}" style="width:350px; height:525px;" /> </div>
                <div class="image-additional" id="gallery_01">
                <c:forEach items="${lstImagem}" var="img" varStatus="loop">
                	<c:set var="urlprodutomontada2" scope="application" value="${urlmodelo}/modelo-66x99.jpg" />
					<c:if test="${not empty img.imagem}">
					<c:set var="urlprodutomontada2" scope="application"
						value="${urlproduto}/${img.produto.anunciante.id}/${img.produto.id}/${img.imagem}" />
					</c:if>
                	<a onclick="mudaImagem(${loop.count});" class="thumbnail" data-zoom-image="${urlprodutomontada2}" data-image="${urlprodutomontada2}" title="${produto.titulo}"> 
                		<img id="img_${loop.count}" src="${urlprodutomontada2}" title="${produto.titulo}" alt = "${produto.titulo}" style="width:66px; height:98px;"/>
                	</a> 
                </c:forEach>
                </div>
              </div>
              <div class="col-sm-6">
                <p class="list-unstyled description">
                	${produto.brevedescricao}
                </p>
                
                <c:set var="desconto" value="0.${produto.desconto}" />       
				<c:set var="precocomdesconto" scope="application" value="${produto.preco - (produto.preco * desconto)}" />
                
                <ul class="price-box">
                  <li class="price" itemprop="offers" itemscope itemtype="http://schema.org/Offer">
                  	<c:if test="${produto.desconto > 0}">
                  		<span itemprop="price">$ ${precocomdesconto}<span itemprop="availability" content="In Stock"></span></span>
                  		<span class="price-old">${produto.preco}</span>
                  	</c:if>
                  	<c:if test="${produto.desconto == 0}">
                  		<span itemprop="price">$ ${precocomdesconto}<span itemprop="availability" content="In Stock"></span></span>
                  	</c:if>
                  </li>
                </ul>
                
                  <div class="cart">
                    <div>
                      <div class="qty">
                        <label class="control-label" for="input-quantity">Qtd</label>
                        <input type="text" name="quantity" value="1" size="2" id="input-quantity" class="form-control" />
                        <a class="qtyBtn plus" href="javascript:void(0);">+</a><br />
                        <a class="qtyBtn mines" href="javascript:void(0);">-</a>
                        <div class="clear"></div>
                      </div>
                      <button type="button" id="button-cart" class="btn btn-primary btn-lg"><i class="fa fa-shopping-cart"></i> Orçar</button>
                    </div>
                  </div>
                </div>
               
              </div>
            </div>
            <ul class="nav nav-tabs">
              <li class="active"><a href="#tab-description" data-toggle="tab"><i class="fa fa-arrow-circle-right"></i> Descrição</a></li>
            </ul>
            <div class="tab-content">
              <div itemprop="description" id="tab-description" class="tab-pane active">
                <div>
                  <p>${produto.descricaocompleta }</p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!--Middle Part End -->
      </div>
    </div>
</my:template>