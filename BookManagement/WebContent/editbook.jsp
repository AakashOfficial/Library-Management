<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.project.bookmanagement.entity.Book"%>
<%@page import="com.project.bookmanagement.entity.Publisher"%>
<%@page import="com.project.bookmanagement.entity.Author"%>
<%@page import="com.project.bookmanagement.entity.Genre"%>
<%@page import="com.project.bookmanagement.service.AdminBookService"%>
<%
	Integer bookId = Integer.parseInt(request.getParameter("bookId"));
	AdminBookService service = new AdminBookService();
	List<Publisher> publishers = service.getAllPublishers(null, null);
	List<Author> authors = service.getAllAuthors(null, null);
	List<Genre> genres = service.getAllGenres(null, null);
	Book book = service.getBookByPK(bookId);
	Publisher bookPub = book.getPublisher();
	Integer pubId=0;
	if (bookPub !=null){
		pubId = bookPub.getPublisherId();
	}
%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="myModalLabel">Edit Book Details</h4>
</div> 
<div class="box">
	<form action="editBook" method="post">
		<div class="row"> <label>Title: </label><input class="input-sm" type="text" name="bookTitle" value="<%=book.getTitle()%>">
		<input type="hidden" name="bookId" value=<%=book.getBookId()%>></div></br>
		<div class="row"><label>Book Publisher: </label>
			<select name="publisherId">
				<option value="0">Select a Publisher</option>
				<%for(Publisher p: publishers){ 
				if (p.getPublisherId() == pubId){%>
					<option value="<%=p.getPublisherId()%>" selected><%=p.getPublisherName() %></option>
				<%} else{ %>
					<option value="<%=p.getPublisherId()%>"><%=p.getPublisherName() %></option>
				<%}} %>
			</select>
		</div>
		<div class="row"><label>Book Author(s): </label></br>
				<select class="input-sm" name="authorId[]" multiple="multiple">
					<%for(Author a: authors){ 
						if (book.getAuthors().contains(a)){%>
						<option value="<%=a.getAuthorId()%>" selected><%=a.getAuthorName() %></option>
					<%} else{ %>
						<option value="<%=a.getAuthorId()%>"><%=a.getAuthorName() %></option>
					<%}} %>
				</select>
		</div></br>	
		<div class="row"><label>Book Genre(s): </label></br>
				<select class ="input-sm" name="genreId[]" multiple="multiple">
					<%for(Genre g: genres){ 
						if (book.getGenres().contains(g)) {%>
							<option value="<%=g.getGenreId()%>" selected><%=g.getGenreName() %></option>
					<%} else{ %>
							<option value="<%=g.getGenreId()%>"><%=g.getGenreName() %></option>
					<%} }%>
				</select>
		</div></br> 
		<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary">Edit Book</button>
	</div>
	</form>
</div>