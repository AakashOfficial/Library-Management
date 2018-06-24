<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.project.bookmanagement.service.AdminBookService"%>
<%@page import="com.project.bookmanagement.entity.Publisher"%>
<%@page import="com.project.bookmanagement.entity.Author"%>
<%@page import="com.project.bookmanagement.entity.Genre"%>

<%@include file="header.html" %>

<%
AdminBookService adminBookService = new AdminBookService();
List<Publisher> publishers = adminBookService.getAllPublishers(null, null);
List<Author> authors = adminBookService.getAllAuthors(null, null);
List<Genre> genres = adminBookService.getAllGenres(null, null);
%>

<div class='container'>
	<div class='box'>
	<h2>Add a Book</h2>
		<form action="createBook" method="post">
			<div class="row"> <label>Title: </label><input class="input-sm" type="text" name="bookName"></div>
			<div class="row"><label>Pick Book Publisher: </label>
				<select name="publisherId">
					<option value="0">Select a Publisher</option>
					<%for(Publisher p: publishers){ %>
					<option value="<%=p.getPublisherId()%>"><%=p.getPublisherName() %></option>
					<%} %>
				</select>
			</div>
			<div class="row"><label>Pick Book Author(s): </label></br>
				<select class="input-sm" name="authorId[]" multiple="multiple">
					<%for(Author a: authors){ %>
					<option value="<%=a.getAuthorId()%>"><%=a.getAuthorName() %></option>
					<%} %>
				</select>
			</div></br>	
			<div class="row"><label>Pick Book Genre(s): </label></br>
				<select class ="input-sm" name="genreId[]" multiple="multiple">
					<%for(Genre g: genres){ %>
					<option value="<%=g.getGenreId()%>"><%=g.getGenreName() %></option>
					<%} %>
				</select>
			</div></br> 
			<div class="row"> <button class="btn btn-default" type="submit">Add Book</button></div>
		</form>
	</div>
</div>
<%@include file="footer.html" %>