<%@include file="header.html" %>

<div class="container">
	<div class="box">
		<h2>Create a New Author</h2>
		<form action="createAuthor" method="post">
			<div class="row"> Author Name:<input type="text" name="authorName"></div>	 
			<div class="row"> <button class="btn btn-default" type="submit">Add Author</button></div>
		</form>
	</div>
</div>

<%@include file="footer.html" %>