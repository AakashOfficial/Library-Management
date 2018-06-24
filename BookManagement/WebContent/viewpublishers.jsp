<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.project.bookmanagement.service.AdminBookService"%>
<%@page import="com.project.bookmanagement.entity.Publisher"%>

<%
	AdminBookService adminBookService = new AdminBookService();
	List<Publisher> publishers = new ArrayList<>();
	Integer pubCount = adminBookService.getPublishersCount();
	int pages = 0;
	pages = (int)Math.ceil(1.0*pubCount/10);
	if(request.getAttribute("publishers")!=null){
		publishers = (List<Publisher>)(request.getAttribute("publishers"));
	}else{
		publishers = adminBookService.getAllPublishers(1, null);	
	}
%>

<script>
function searchPublishers(){
	$.ajax({
		url: "searchPublishers",
		data:{
			searchString: $('#searchString').val(),
			pageNo: $('#pageNumber').val()
		}
	}).done(function (data){
		$('#publishersTable').html(data)
	})
}

function pagination(page){
	$('#pageNumber').val(page)
	$.ajax({
		url: "publisherPagination",
		data:{
			pageNo: page,
			searchString: $('#searchString').val()
		}
	}).done(function (data){
		$('#publishersTable').html(data)
	})
}

function deletePublisher(pubId){
	$.ajax({
		url: "deletePublisher",
		type:'POST',
		data:{
			pubId: pubId,
			pageNo:$('#pageNumber').val(),
			searchString: $('#searchString').val()
		}
	}).success(function(data){
		$('#publishersTable').html(data)
		alert("Publisher Deleted")
	})
}
</script>

<%@include file="header.html" %>
<div class="container">
	<div class ="box">
		<h1>Publishers</h1>
		<div class="row">
			<div class="col-lg-6">
	    		<div class="input-group">
	      			<input type="text" id ="searchString" name="searchString" class="form-control" placeholder="Search for..." oninput="searchPublishers()">
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
			<a href="createpublisher.jsp">New Publisher</a>
		</div>
		
		<table id="publishersTable" class ="table">
			<tr>
				<th>Publisher ID</th>
				<th>Publisher Name</th>
				<th>Publisher Address</th>
				<th>Publisher Phone</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
			<%for(Publisher p:publishers){ %>
			<tr>
				<td><%=publishers.indexOf(p)+1 %></td>
				<td><%=p.getPublisherName() %></td>	
				<td><%=p.getPublisherAddress() %></td>
				<td><%=p.getPublisherPhone() %></td>	
				<td><button class="btn btn-primary" type="button" data-toggle="modal" data-target="#editPublisherModal"
					href="editpublisher.jsp?pubId=<%=p.getPublisherId()%>">Edit</button></td>
				<td><button class="btn btn-danger" type="button" onclick= "deletePublisher(<%=p.getPublisherId()%>)">Delete</button></td>	
			</tr>
			<%} %>
		</table>
	</div>
</div>
<input type="hidden" id="pageNumber" name="pageNumber" value="1">

<div class="modal fade bs-example-modal-lg" tabindex="-1"
	id="editPublisherModal" role="dialog" aria-labelledby="myLargeModalLabel">
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