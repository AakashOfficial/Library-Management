<%@include file="header.html" %>

<div class="container">
	<div class="box">
		<h2>Create a New Publisher</h2>
		<form action="createPublisher" method="post">
				Publisher Name: <input type="text" name="publisherName"><br />
				Publisher Address: <input type="text" name="publisherAddress"><br />
				Publisher Phone: <input type="text" name="publisherPhone"><br />
				<button class="btn btn-default" type="submit">Add Publisher</button>
		</form>
	</div>
</div>

<%@include file="footer.html" %>