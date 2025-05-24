<%@ include file="/header.jsp"%>



Productor
<form action="kafka/produce" method="get">
	<div class="mb-3">
		<label for="temperature" class="form-label">
			Temperatura (celsius)
		</label>
		
		<input type="number" min="-273" max="1000" class="form-control"
			id="temperature" name="temperature" required>
	</div>
	<button type="submit" class="btn btn-success">&iexcl;Producir evento&excl;</button>
</form>


<form action="main" method="get">
	<button type="submit" class="btn btn-danger">Retirar productor</button>
</form>


<%@ include file="/footer.jsp"%>