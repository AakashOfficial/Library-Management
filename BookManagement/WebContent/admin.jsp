<%@include file="header.html" %>

<div class="container">
	<div class ="row">
		<div class = "box">
		<h2 class="text-center">Welcome Admin</h2>
	</div>
	</div>
	   <div class="row">
            <div class="box">
                <div class="col-lg-12">
                    <hr>
                    <h2 class="intro-text text-center">Choose an
                        <strong>Option</strong>
                    </h2>
                    <hr>
                </div>
                <div class="col-sm-4 text-center">
                    <h4><a href="adminbookservices.jsp">Books</a></h4>
                    <img style="display:block;margin:auto" class="img-responsive" src="Content/img/books.png" alt="Books">
                </div>
                <div class="col-sm-4 text-center">
                	<h4><a href="viewbranches.jsp">Library Branches</a></h4>
                    <img style="display:block;margin:auto"  class="img-responsive" src="Content/img/branches.png" alt="Branches">
                </div>
                <div class="col-sm-4 text-center">
                 	<h4><a href="viewloans.jsp">Loans</a></h4>
                    <img style="display:block;margin:auto" class="img-responsive" src="Content/img/loans.png" alt="Loans">
                </div>
                <div class="clearfix"></div>
            </div>
 	</div>
</div>
	

<%@include file="footer.html" %>