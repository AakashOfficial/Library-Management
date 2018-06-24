<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.project.bookmanagement.service.AdminBookService"%>
<%@page import="com.project.bookmanagement.entity.Book"%>

<%@include file="header.html" %>
<script src="js/gcitlms.js"></script>

<%
AdminBookService adminBookService = new AdminBookService();
List<Book> books = adminBookService.getAllBooks(null, null);
%>
<select class ="input-sm" name="genreId">
					<%for(Book b: books){ %>
					<option value="<%=b.getBookId()%>"><%=b.getTitle() %></option>
					<%} %>
				</select>