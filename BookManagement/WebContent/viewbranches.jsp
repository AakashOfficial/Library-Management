<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.project.bookmanagement.service.AdminBranchService"%>
<%@page import="com.project.bookmanagement.entity.Branch"%>

<%
	AdminBranchService adminBranchService = new AdminBranchService();
	List<Branch> branches = new ArrayList<>();
	Integer branchCount = adminBranchService.getBranchesCount();
	int pages = 0;
	pages = (int)Math.ceil(1.0*branchCount/10);
	if(request.getAttribute("branches")!=null){
		branches = (List<Branch>)(request.getAttribute("branches"));
	}else{
		branches = adminBranchService.getAllBranches(1, null);	
	}
%>

<script>
function searchBranches(){
	$.ajax({
		url: "searchBranches",
		data:{
			searchString: $('#searchString').val(),
			pageNo: $('#pageNumber').val()
		}
	}).done(function (data){
		$('#branchesTable').html(data)
	})
}

function pagination(page){
	$('#pageNumber').val(page)
	$.ajax({
		url: "branchPagination",
		data:{
			pageNo: page,
			searchString: $('#searchString').val()
		}
	}).done(function (data){
		$('#branchesTable').html(data)
	})
}

function deleteBranch(branchId){
	$.ajax({
		url: "deleteBranch",
		type:'POST',
		data:{
			branchId: branchId,
			pageNo:$('#pageNumber').val(),
			searchString: $('#searchString').val()
		}
	}).success(function(data){
		$('#branchesTable').html(data)
		alert("Branch Deleted")
	})
}

</script>

<%@include file="header.html" %>
<div class="container">
	<div class ="box">
		<h1>Branches</h1>
		<div class="row">
			<div class="col-lg-6">
	    		<div class="input-group">
	      			<input type="text" id ="searchString" name="searchString" class="form-control" placeholder="Search for..." oninput="searchBranches()">
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
			<a href="createbranch.jsp">New Branch</a>
		</div>
		
		<table id="branchesTable" class ="table">
			<tr>
				<th>Branch ID</th>
				<th>Branch Name</th>
				<th>Branch Address</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
			<%for(Branch b:branches){ %>
			<tr>
				<td><%=branches.indexOf(b)+1 %></td>
				<td><%=b.getBranchName() %></td>	
				<td><%=b.getBranchAddress() %></td>
				<td><button class="btn btn-primary" type="button" data-toggle="modal" data-target="#editBranchModal"
					href="admineditbranch.jsp?branchId=<%=b.getBranchId()%>">Edit</button></td>
				<td><button class="btn btn-danger" type="button" onclick= "deleteBranch(<%=b.getBranchId()%>)">Delete</button></td>	
			</tr>
			<%} %>
		</table>
	</div>
</div>
<input type="hidden" id="pageNumber" name="pageNumber" value="1">

<div class="modal fade bs-example-modal-lg" tabindex="-1"
	id="editBranchModal" role="dialog" aria-labelledby="myLargeModalLabel">
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