<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.project.bookmanagement.entity.Book"%>
<%@page import="com.project.bookmanagement.entity.Publisher"%>
<%@page import="com.project.bookmanagement.entity.Author"%>
<%@page import="com.project.bookmanagement.entity.Genre"%>
<%@page import="com.project.bookmanagement.service.AdminBookService"%>
<%
	Integer pubId = Integer.parseInt(request.getParameter("pubId"));
	AdminBookService service = new AdminBookService();
	Publisher publisher = service.getPublisherByPK(pubId);
%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="myModalLabel">Edit Publisher Details</h4>
</div> 
<div class="box">
	<form action="editPublisher" method="post">
		<div class="row"> 
			<input type="hidden" name="pubId" value=<%=publisher.getPublisherId()%>>
			<label>Publisher Name </label><input class="input-sm" type="text" name="pubName" value="<%=publisher.getPublisherName()%>">
			<label>Publisher Address </label><input class="input-sm" type="text" name="pubAddress" value="<%=publisher.getPublisherAddress()%>">
			<label>Publisher Phone </label><input class="input-sm" type="text" name="pubPhone" value="<%=publisher.getPublisherPhone()%>">
		</div></br> 
		<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary">Edit Publisher</button>
	</div>
	</form>
</div>