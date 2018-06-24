<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.project.bookmanagement.entity.Book"%>
<%@page import="com.project.bookmanagement.entity.Publisher"%>
<%@page import="com.project.bookmanagement.entity.Author"%>
<%@page import="com.project.bookmanagement.entity.Genre"%>
<%@page import="com.project.bookmanagement.service.AdminBookService"%>
<%
	Integer genreId = Integer.parseInt(request.getParameter("genreId"));
	AdminBookService service = new AdminBookService();
	Genre genre = service.getGenreByPK(genreId);
%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="myModalLabel">Edit Genre Details</h4>
</div> 
<div class="box">
	<form action="editGenre" method="post">
		<div class="row"> 
			<label>Genre Name: </label><input class="input-sm" type="text" name="genreName" value="<%=genre.getGenreName()%>">
			<input type="hidden" name="genreId" value=<%=genre.getGenreId()%>>
		</div></br> 
		<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary">Edit Genre</button>
	</div>
	</form>
</div>