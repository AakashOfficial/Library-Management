<%@include file="header.html" %>
<div class="container">
	<div class="box">
		<h2>Create a New Genre</h2>
		<form action="createGenre" method="post">
			<div class="row">Genre Name:<input type="text" name="genreName"></div>	 
			<div class="row"> <button class="btn btn-default" type="submit">Add Genre</button></div>
		</form>
	</div>
</div>
<%@include file="footer.html" %>