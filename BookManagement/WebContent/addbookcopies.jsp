<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.project.bookmanagement.entity.BookCopies"%>
<%@page import="com.project.bookmanagement.entity.Book"%>
<%@page import="com.project.bookmanagement.entity.Branch"%>
<%@page import="com.project.bookmanagement.service.LibrarianService"%>
<%
	LibrarianService librarianService = new LibrarianService();
	Integer bookId = Integer.parseInt(request.getParameter("bookId"));
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	Book book = librarianService.getBookByPK(bookId);
	Branch branch = librarianService.getBranchByPK(branchId);
	
%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="myModalLabel">Add Copies of <%= book.getTitle() %> to <%=branch.getBranchName() %></h4>
</div> 
<form action="addCopies" method="post">
<div class="box">
<label>Number of Copies: </label><input type="number" value="0" min="0" max="3" name="copies" id="copies" class="input-sm">
<input type="hidden" id="bookId" name="bookId" value="<%=book.getBookId()%>">
<input type="hidden" id="branchId" name="branchId" value="<%=branch.getBranchId()%>">
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary">Add Copies</button>
	</div>
</form>
