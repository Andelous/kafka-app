<%@ include file="/header.jsp"%>



Consumidor

<div id="kafka-events"></div>

<form action="main" method="get">
	<button type="submit" class="btn btn-danger">Retirar
		consumidor</button>
</form>


<script>
	async function retrieveEvents() {
		var response = await fetch("/kafka/events", {
			method : "GET"
		});

		var json = await response.json();
		console.log(json);
		
		var contents = "";
		
		for (val in json) {
			contents += "<pre><code class='language-json'>" + json[val] + "</code></pre>";
		}

		var myDiv = document.getElementById("kafka-events");
		
		myDiv.innerHTML = contents;
		
		hljs.highlightAll();

		setTimeout(retrieveEvents, 2000);
	}

	retrieveEvents();
</script>

<%@ include file="/footer.jsp"%>