<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.project.bookmanagement.service.AdminLoanService"%>
<%@page import="com.project.bookmanagement.entity.Author"%>
<%@page import="com.project.bookmanagement.entity.Book"%>
<%@page import="com.project.bookmanagement.entity.BookLoans"%>


<%
	AdminLoanService adminLoanService = new AdminLoanService();
	List<BookLoans> loans = new ArrayList<>();
	Integer loansCount = adminLoanService.getLoansCount();
	int pages = 0;
	pages = (int)Math.ceil(1.0*loansCount/10);
	if(request.getAttribute("loans")!=null){
		loans = (List<BookLoans>)(request.getAttribute("loans"));
	}else{
		loans = adminLoanService.getAllLoans(1, null);	
	}
%>

<script>
function searchLoans(){
	$.ajax({
		url: "searchLoans",
		data:{
			searchString: $('#searchString').val(),
			pageNo: $('#pageNumber').val()
		}
	}).done(function (data){
		$('#loansTable').html(data)
	})
}

function pagination(page){
	$('#pageNumber').val(page)
	$.ajax({
		url: "loansPagination",
		data:{
			pageNo: page,
			searchString: $('#searchString').val()
		}
	}).done(function (data){
		$('#loansTable').html(data)
	})
}

function extendLoan(borrowerId, bookId, branchId, dateOut){
	$.ajax({
		url: "extendLoan",
		data:{
			borrowerId: borrowerId,
			bookId: bookId,
			branchId: branchId,
			dateOut: dateOut,
			pageNo: $('#pageNumber').val()
		}
	}).done(function (data){
		$('#loansTable').html(data)
	})
}

</script>


<%@include file="header.html" %>
<div class="container">
	<div class ="box">
		<h1>Book Loans</h1>
		<div class="row">
			<div class="col-lg-6">
	    		<div class="input-group">
	      			<input type="text" id ="searchString" name="searchString" class="form-control" placeholder="Card No" oninput="searchLoans()">
	    		</div><!-- /input-group -->
	 		 </div><!-- /.col-lg-6 -->
 		 </div> <!-- /row -->
		<nav aria-label="Page navigation">
			<ul class="pagination">
				<li><a href="#" aria-label="Previous"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
				<%for(int i=1; i<=pages; i++){ %>
					<li><a onclick="pagination(<%=i%>)"><%=i%></a></li>
				<%} %>
				<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
		</nav>
		<table class="table" id ="loansTable">
			<tr>
				<th>Card No</th>
				<th>Borrower Name</th>
				<th>Book Title</th>
				<th>Library Branch</th>
				<th>Date Out</th>
				<th>Date In</th>
				<th>Due Date</th>
			</tr>
			<%for(BookLoans l:loans){ %>
			<tr>
				<td><%=l.getBorrower().getCardNo() %></td>
				<td><%=l.getBorrower().getName() %></td>
				<td><%=l.getBook().getTitle() %></td>
				<td><%=l.getBranch().getBranchName() %></td>	
				<td><%=l.getDateOut() %></td>
				<td>
				<%if (l.getDateIn() != null){ %>
					<%=l.getDateIn() %>
				<%}%>
				</td>
				<td><%=l.getDueDate() %></td>
				<%if (l.getDateIn() == null){ %>
					<td><button class="btn btn-primary" type="button" onclick="extendLoan(<%=l.getBorrower().getCardNo()%>,<%=l.getBook().getBookId()%>,<%=l.getBranch().getBranchId()%>,<%=l.getDateOut()%>)">Add 30 Days</button></td>
				<%}%>
			</tr>
			<%} %>
		</table>
	</div>
</div>
<input type="hidden" id="pageNumber" name="pageNumber" value="1">
<%@include file="footer.html" %>