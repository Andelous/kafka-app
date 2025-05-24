
<%
String typeClient = (String) session.getAttribute("type-client");

if (typeClient != null) {
	if (typeClient.equals("PRODUCER")) {
		response.sendRedirect("/producer.jsp");
	} else {
		response.sendRedirect("/consumer.jsp");
	}

	return;
}
%>


<%@ include file="/header.jsp"%>


<div class="mt-3 d-flex justify-content-start align-items-center">
	<img alt="Logo de Kafka" src="favicon.ico" class="img-thumbnail me-3"
		style="width: 50px; height: 50px;">

	<h1>
		Simulador de <strong>Big Data</strong> con <strong
			class="text-secondary">Kafka</strong>
	</h1>
</div>
<hr>

<div class="container-fluid bg-success-subtle" style="height: 600px">
	<div class="row">
		<div class="col-md-3 p-2">
			<div class="bg-info-subtle">
				<h4>Productores</h4>

				<form action="main" method="get">
					<input type="hidden" name="type" value="PRODUCER"></input>
					<button type="submit" class="btn btn-primary">&iexcl;Quiero
						ser productor&excl;</button>
				</form>
			</div>
		</div>

		<div class="col-md-6 p-2">
			<div class="bg-warning-subtle">
				<h4>Eventos</h4>
			</div>

		</div>

		<div class="col-md-3 p-2">
			<div class="bg-info-subtle">
				<h4>Consumidores</h4>

				<form action="main" method="get">
					<input type="hidden" name="type" value="CONSUMER"></input>
					<button type="submit" class="btn btn-primary">&iexcl;Quiero
						ser consumidor&excl;</button>
				</form>
			</div>
		</div>
	</div>
</div>

<div>abcd</div>

<%@ include file="/footer.jsp"%>

