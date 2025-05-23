
<%
String tipoCliente = (String) session.getAttribute("tipo-cliente");

if (tipoCliente != null) {
	System.out.println("Redireccionando!!!");
	if (tipoCliente.equals("PRODUCTOR")) {
		response.sendRedirect("/productor.jsp");
	} else {
		response.sendRedirect("/consumidor.jsp");
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
					<input type="hidden" name="tipo" value="PRODUCTOR"></input>
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
					<input type="hidden" name="tipo" value="CONSUMIDOR"></input>
					<button type="submit" class="btn btn-primary">&iexcl;Quiero
						ser consumidor&excl;</button>
				</form>
			</div>
		</div>
	</div>
</div>

<div>abcd</div>

<%
System.out.println("Khe");
String asf = "AAA";
%>

<%=session.getAttribute("meow")%>
<%=asf%>



<%@ include file="/footer.jsp"%>

