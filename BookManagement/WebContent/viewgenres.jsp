<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.project.bookmanagement.service.AdminBookService"%>
<%@page import="com.project.bookmanagement.entity.Genre"%>

<%
	AdminBookService adminBookService = new AdminBookService();
	List<Genre> genres = new ArrayList<>();
	Integer genreCount = adminBookService.getGenresCount();
	int pages = 0;
	pages = (int)Math.ceil(1.0*genreCount/10);
	if(request.getAttribute("genres")!=null){
		genres = (List<Genre>)(request.getAttribute("genres"));
	}else{
		genres = adminBookService.getAllGenres(1, null);	
	}
%>

<script>
function searchGenres(){
	$.ajax({
		url: "searchGenres",
		data:{
			searchString: $('#searchString').val(),
			pageNo: $('#pageNumber').val()
		}
	}).done(function (data){
		$('#genresTable').html(data)
	})
}

function pagination(page){
	$('#pageNumber').val(page)
	$.ajax({
		url: "genrePagination",
		data:{
			pageNo: page,
			searchString: $('#searchString').val()
		}
	}).done(function (data){
		$('#genresTable').html(data)
	})
}

function deleteGenre(genreId){
	$.ajax({
		url: "deleteGenre",
		type:'POST',
		data:{
			genreId: genreId,
			pageNo:$('#pageNumber').val(),
			searchString: $('#searchString').val()
		}
	}).success(function(data){
		$('#genresTable').html(data)
		alert("Genre Deleted")
	})
}

</script>

<%@include file="header.html" %>
<div class="container">
	<div class ="box">
		<h1>Genres</h1>
		${message}
		<div class="row">
			<div class="col-lg-6">
	    		<div class="input-group">
	      			<input type="text" id ="searchString" name="searchString" class="form-control" placeholder="Search for..." oninput="searchGenres()">
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
			<a href="creategenre.jsp">New Genre</a>
		</div>
		
		<table id="genresTable" class ="table">
				<tr>
					<th>Genre ID</th>
					<th>Genre Name</th>
				</tr>
				<%for(Genre g:genres){ %>
				<tr>
					<td><%=genres.indexOf(g)+1 %></td>
					<td><%=g.getGenreName() %></td>	
					<td><button class="btn btn-primary" type="button" data-toggle="modal" data-target="#editGenreModal"
					href="editgenre.jsp?genreId=<%=g.getGenreId()%>">Edit</button></td>
					<td><button class="btn btn-danger" type="button" onclick= "deleteGenre(<%=g.getGenreId()%>)">Delete</button></td>	
				</tr>
				<%} %>
		</table>
	</div>
</div>
<input type="hidden" id="pageNumber" name="pageNumber" value="1">

<div class="modal fade bs-example-modal-lg" tabindex="-1"
	id="editGenreModal" role="dialog" aria-labelledby="myLargeModalLabel">
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