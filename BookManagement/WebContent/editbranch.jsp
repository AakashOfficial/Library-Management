<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.project.bookmanagement.entity.Book"%>
<%@page import="com.project.bookmanagement.entity.Publisher"%>
<%@page import="com.project.bookmanagement.entity.Branch"%>
<%@page import="com.project.bookmanagement.entity.Genre"%>
<%@page import="com.project.bookmanagement.service.LibrarianService"%>
<%
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	LibrarianService librarianService = new LibrarianService();
	Branch selectedBranch = librarianService.getBranchByPK(branchId);
	List<Book> books = librarianService.getAllBooks(null, null);
	List<Book> branchBooks = librarianService.getAllBooksInBranch(selectedBranch);
	books.removeAll(branchBooks);
%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="myModalLabel">Edit Branch Details</h4>
</div> 
<div class="box">
	<form action="editBranch" method="post">
		<input type="hidden" name="selectedBranch" id="selectedBranch" value="<%=selectedBranch.getBranchId()%>">
		<label>Branch Name: </label><input id="branchName" name="branchName" class="input-sm" value="<%=selectedBranch.getBranchName()%>"></br>
		<label>Branch Address: </label><input id="branchAddress" name="branchAddress" class="input-sm" value="<%=selectedBranch.getBranchAddress()%>">
		<br>
		<div>
			<label>Add Books to Branch</label>
			<br>
			<select class="input-lg" name="addBooks[]" multiple="multiple">
				<%for(Book b: books){  %>
					<option value="<%=b.getBookId()%>"><%=b.getTitle() %></option>
				<%} %>
			</select>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Edit Branch</button>
		</div>
	</form>
</div>

