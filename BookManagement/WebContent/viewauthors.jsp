<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.project.bookmanagement.service.AdminBookService"%>
<%@page import="com.project.bookmanagement.entity.Author"%>
<%@page import="com.project.bookmanagement.entity.Book"%>

<%
	AdminBookService abs = new AdminBookService();
	List<Author> authors = new ArrayList<>();
	Integer authCount = abs.getAuthorsCount();
	int pages = 0;
	pages = (int)Math.ceil(1.0*authCount/10);
	if(request.getAttribute("authors")!=null){
		authors = (List<Author>)(request.getAttribute("authors"));
	}else{
		authors = abs.getAllAuthors(1, null);	
	}
%>

<script>
function searchAuthors(){
	$.ajax({
		url: "searchAuthors",
		data:{
			searchString: $('#searchString').val(),
			pageNo: $('#pageNumber').val()
		}
	}).done(function (data){
		$('#authorsTable').html(data)
	})
}

function pagination(page){
	$('#pageNumber').val(page)
	$.ajax({
		url: "authorPagination",
		data:{
			pageNo: page,
			searchString: $('#searchString').val()
		}
	}).done(function (data){
		$('#authorsTable').html(data)
	})
}

function deleteAuthor(authorId){
	$.ajax({
		url: "deleteAuthor",
		type:'POST',
		data:{
			authorId: authorId,
			pageNo:$('#pageNumber').val(),
			searchString: $('#searchString').val()
		}
	}).success(function(data){
		$('#authorsTable').html(data)
		alert("Author Deleted")
	})
}

</script>


<%@include file="header.html" %>
<div class="container">
	<div class ="box">
		<h1>Authors</h1>
		<div class="row">
			<div class="col-lg-6">
	    		<div class="input-group">
	      			<input type="text" id ="searchString" name="searchString" class="form-control" placeholder="Search for..." oninput="searchAuthors()">
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
			<a href="createauthor.jsp">New Author</a>
		</div>
		
		<table class="table" id ="authorsTable">
			<tr>
				<th>Author ID</th>
				<th>Author Name</th>
				<th>Books By Author</th>
			</tr>
			<%for(Author a:authors){ %>
			<tr>
				<td><%=authors.indexOf(a)+1 %></td>
				<td><%=a.getAuthorName() %></td>
				<td> <% for (Book b:a.getBooks()){
					out.print("|"+ b.getTitle() + "|");
				}%></td>	
				<td><button class="btn btn-primary" type="button" data-toggle="modal" data-target="#editAuthorModal"
					href="editauthor.jsp?authorId=<%=a.getAuthorId()%>">Edit</button></td>
				<td><button id="deleteButton" class="btn btn-danger" type="button" onclick= "deleteAuthor(<%=a.getAuthorId()%>)">Delete</button></td>	
			</tr>
			<%} %>
		</table>
	</div>
</div>
<input type="hidden" id="pageNumber" name="pageNumber" value="1">

<div class="modal fade bs-example-modal-lg" tabindex="-1"
	id="editAuthorModal" role="dialog" aria-labelledby="myLargeModalLabel">
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