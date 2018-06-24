<%@include file="header.html" %>

<div class="container">
	<div class="box">
		<h2>Create a New Branch</h2>
		<form action="createBranch" method="post">
			<div class="row"><label>Branch Name: </label><input type="text" name="branchName"></div>
			<div class="row"><label>Branch Address: </label><input type="text" name="branchAddress"></div>			 
			<div class="row"> <button class="btn btn-default" type="submit">Add Branch</button></div>
		</form>
	</div>
</div>

<%@include file="footer.html" %>