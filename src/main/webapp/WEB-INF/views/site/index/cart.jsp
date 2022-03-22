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

<c:set var="jsFiles" value="/js/Index.js" />

<my:template title="${mensagens.get('NomeDoProjeto').valor}"
	fluido="false" jsFiles="${jsFiles}">
	<div id="container" style="padding-top: 180px;">
		<div class="container">
			<div class="row">
				<!--Middle Part Start-->
				<div id="content" class="col-sm-12">
					<h1 class="title">Shopping Cart</h1>
					<div class="table-responsive">
						<table class="table table-bordered">
							<thead>
								<tr>
									<td class="text-center">Imagem</td>
									<td class="text-left">Produto</td>
									<td class="text-left">Categoria</td>
									<td class="text-left">Quantidade</td>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="text-center"><a href="product.html"><img
											src="image/product/samsung_tab_1-50x75.jpg"
											alt="Aspire Ultrabook Laptop" title="Aspire Ultrabook Laptop"
											class="img-thumbnail" /></a></td>
									<td class="text-left"><a href="product.html">Aspire
											Ultrabook Laptop</a><br /> <small>Reward Points: 1000</small></td>
									<td class="text-left">SAM1</td>
									<td class="text-left"><div
											class="input-group btn-block quantity">
											<input type="text" name="quantity" value="1" size="1"
												class="form-control" /> <span class="input-group-btn">
												<button type="submit" data-toggle="tooltip" title="Update"
													class="btn btn-primary">
													<i class="fa fa-refresh"></i>
												</button>
												<button type="button" data-toggle="tooltip" title="Remove"
													class="btn btn-danger" onClick="">
													<i class="fa fa-times-circle"></i>
												</button>
											</span>
										</div></td>
								</tr>
								<tr>
									<td class="text-center"><a href="product.html"><img
											src="image/product/sony_vaio_1-50x75.jpg"
											alt="Xitefun Causal Wear Fancy Shoes"
											title="Xitefun Causal Wear Fancy Shoes" class="img-thumbnail" /></a></td>
									<td class="text-left"><a href="product.html">Xitefun
											Causal Wear Fancy Shoes</a></td>
									<td class="text-left">Product 114</td>
									<td class="text-left"><div
											class="input-group btn-block quantity">
											<input type="text" name="quantity" value="1" size="1"
												class="form-control" /> <span class="input-group-btn">
												<button type="submit" data-toggle="tooltip" title="Update"
													class="btn btn-primary">
													<i class="fa fa-refresh"></i>
												</button>
												<button type="button" data-toggle="tooltip" title="Remove"
													class="btn btn-danger" onClick="">
													<i class="fa fa-times-circle"></i>
												</button>
											</span>
										</div></td>
								</tr>
							</tbody>
						</table>
					</div>
					<h2 class="subtitle">What would you like to do next?</h2>
					<p>Choose if you have a discount code or reward points you want
						to use or would like to estimate your delivery cost.</p>
					<div class="row">
						<div class="col-sm-6">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">Use Coupon Code</h4>
								</div>
								<div id="collapse-coupon" class="panel-collapse collapse in">
									<div class="panel-body">
										<label class="col-sm-4 control-label" for="input-coupon">Enter
											your coupon here</label>
										<div class="input-group">
											<input type="text" name="coupon" value=""
												placeholder="Enter your coupon here" id="input-coupon"
												class="form-control" /> <span class="input-group-btn">
												<input type="button" value="Apply Coupon" id="button-coupon"
												data-loading-text="Loading..." class="btn btn-primary" />
											</span>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">Use Gift Voucher</h4>
								</div>
								<div id="collapse-voucher" class="panel-collapse collapse in">
									<div class="panel-body">
										<label class="col-sm-4 control-label" for="input-voucher">Enter
											gift voucher code here</label>
										<div class="input-group">
											<input type="text" name="voucher" value=""
												placeholder="Enter gift voucher code here"
												id="input-voucher" class="form-control" /> <span
												class="input-group-btn"> <input type="submit"
												value="Apply Voucher" id="button-voucher"
												data-loading-text="Loading..." class="btn btn-primary" />
											</span>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-4 col-sm-offset-8">
							<table class="table table-bordered">
								<tr>
									<td class="text-right"><strong>Quantidade de Produtos:</strong></td>
									<td class="text-right">$940.00</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="buttons">
						<div class="pull-left">
							<a href="index.html" class="btn btn-default">Continue
								Shopping</a>
						</div>
						<div class="pull-right">
							<a href="checkout.html" class="btn btn-primary">Checkout</a>
						</div>
					</div>
				</div>
				<!--Middle Part End -->
			</div>
		</div>
	</div>
</my:template>