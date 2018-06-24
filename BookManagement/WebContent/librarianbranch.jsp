<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.project.bookmanagement.service.LibrarianService"%>
<%@page import="com.project.bookmanagement.entity.Branch"%>

<%
	LibrarianService librarianService = new LibrarianService();
	List<Branch> branches = new ArrayList<>();
	branches = librarianService.getAllBranches(null, null);
	Integer selectedBranch = 0;
	if (request.getAttribute("selectedBranch")!=null){
		String branchId = String.valueOf(request.getAttribute("selectedBranch"));
		selectedBranch = Integer.parseInt(branchId);
	}
%>

<script>
function getBranchCopies(){
	changeHref()
	$.ajax({
		url: "branchCopies",
		type: "POST",
		data:{
			branchId: $(branchList).val()
		}
	}).done(function (data){
		$('#bookCopiesTable').html(data)
	})
}

function changeHref(){
	var selectedBranch = $("#branchList").val();
	if (selectedBranch !== "0"){
		$("#editBranchBtn").attr("data-toggle", "modal");
		$("#editBranchBtn").attr("data-target", "#editBranchModal");
		var newHref = "editbranch.jsp?branchId=" + selectedBranch;
		$("#editBranchBtn").attr("href", newHref);
	}	
}
</script>

<%@include file="header.html" %>

<div class="container">
	<div class = "box">
		<h2>Welcome Librarian</h2>
		<label> Select your branch: </label>
		<select onchange="getBranchCopies()" name="branchList" id="branchList">
			<option value="0">Select Branch</option>
			<%for(Branch b: branches){ 
				if (b.getBranchId() == selectedBranch) {%>
					<option value="<%=b.getBranchId()%>" selected><%=b.getBranchName() %></option>
			<%} else { %>
					<option value="<%=b.getBranchId()%>"><%=b.getBranchName() %></option>
			<%}} %>
		</select>
		<button class="btn btn-xs btn-default" id="editBranchBtn" name="editBranchBtn" type="button">Edit Branch</button>

		<table class="table" id="bookCopiesTable">
		
		</table>
	</div>
	
	<div class="modal fade add-copies-modal" id="addCopiesModal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	  	<div class="modal-dialog modal-sm" role="document">
		    <div class="modal-content"></div>
	  	</div>
	</div>
	
	<div class="modal fade edit-branch-modal" id="editBranchModal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	  	<div class="modal-dialog modal-lg" role="document">
		    <div class="modal-content"></div>
	  	</div>
	</div>
</div>
<script>
	$(document).ready(function() {
		// codes works on all bootstrap modal windows in application
		$('.modal').on('hidden.bs.modal', function(e) {
			$(this).removeData();
		});
	});
	$(document).ready(function() { 
		if ($("#branchList").val() !== "0"){
		 getBranchCopies()
		}
	});
	
</script>
	
<%@include file="footer.html" %>