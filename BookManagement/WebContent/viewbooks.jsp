<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.project.bookmanagement.service.AdminBookService"%>
<%@page import="com.project.bookmanagement.entity.Book"%>
<%@page import="com.project.bookmanagement.entity.Author"%>
<%@page import="com.project.bookmanagement.entity.Publisher"%>
<%@page import="com.project.bookmanagement.entity.Genre"%>

<script>
function searchBooks(){
	$.ajax({
		url: "searchBooks",
		data:{
			searchString: $('#searchString').val(),
			pageNo: $('#pageNumber').val()
		}
	}).done(function (data){
		$('#booksTable').html(data)
	})
}
function pagination(page){
	console.log(page)
	$('#pageNumber').val(page)
	$.ajax({
		url: "bookPagination",
		data:{
			pageNo: page,
			searchString: $('#searchString').val()
		}
	}).done(function (data){
		$('#booksTable').html(data)
	})
}
function deleteBook(bookId){
	console.log(bookId)
	$.ajax({
		url: "deleteBook",
		type:'POST',
		data:{
			bookId: bookId,
			pageNo:$('#pageNumber').val(),
			searchString: $('#searchString').val()
		}
	}).success(function(data){
		$('#booksTable').html(data)
		alert("Book Deleted")
	})
}
</script>

<%
AdminBookService adminBookService = new AdminBookService();
List<Book> books = new ArrayList<>();
Integer bookCount = adminBookService.getBooksCount();
int pages = 0;
pages = (int)Math.ceil(1.0*bookCount/10);
if(request.getAttribute("books")!=null){
	books = (List<Book>)(request.getAttribute("books"));
}else{
	books = adminBookService.getAllBooks(1, null);	
}%>

<%@include file="header.html" %>
<div class="container">
	<div class ="box">
		<h1>Books</h1>
		${message}
		<div class="row">
			<div class="col-lg-6">
	    		<div class="input-group">
	      			<input type="text" id ="searchString" name="searchString" class="form-control" placeholder="Search for..." oninput="searchBooks()">
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
		
		<div style="padding:10px">
			<a href="createbook.jsp">New Book</a>
		</div>
		
		<table class="table" id="booksTable">
				<tr>
					<th>Book ID</th>
					<th>Title</th>
					<th>Author(s)</th>
					<th>Publisher</th>
					<th>Genre(s)</th>
				</tr>
				<%for(Book b:books){ %>
				<tr>
					<td><%=books.indexOf(b)+1 %></td>
					<td><%=b.getTitle() %></td>	
					<td> <% for (Author a:b.getAuthors()) {%>
					 <%=a.getAuthorName()%> <%}%></td>
					<td><% if(b.getPublisher() != null){ %>
					<%=b.getPublisher().getPublisherName() %>  <%}%></td>
					<td> <% for (Genre g:b.getGenres()) {%>
					 <%=g.getGenreName()%>, <%}%></td>
					<td><button type="button" class="btn btn-primary"
					data-toggle="modal" data-target="#editBookModal"
					href="editbook.jsp?bookId=<%=b.getBookId()%>">Edit</button></td>
					<td><button class="btn btn-danger" type="button" onclick= "deleteBook(<%=b.getBookId()%>)">Delete</button></td>	
				</tr>
				<%} %>
		</table>
	</div>
</div>
<input type="hidden" id="pageNumber" name="pageNumber" value="1">

<div class="modal fade bs-example-modal-lg" tabindex="-1"
	id="editBookModal" role="dialog" aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content"></div>
	</div>
</div>
<script>
	$(document).ready(function() {
		// codes works on all bootstrap modal windows in application
		$('.modal').on('hidden.bs.modal', function(e) {
			$(this).removeData();
		});
	});
</script>


<%@include file="footer.html" %>