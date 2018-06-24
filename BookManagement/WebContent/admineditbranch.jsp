<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.project.bookmanagement.entity.Branch"%>
<%@page import="com.project.bookmanagement.service.AdminBranchService"%>
<%
	Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	AdminBranchService service = new AdminBranchService();
	Branch branch = service.getBranchById(branchId);
%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="myModalLabel">Edit Branch Details</h4>
</div> 
<div class="box">
	<form action="editAdminBranch" method="post">
		<div class="row"> 
			<input type="hidden" name="branchId" value=<%=branch.getBranchId()%>>
			<label>Branch Name </label><input class="input-sm" type="text" name="branchName" value="<%=branch.getBranchName()%>">
			<br>
			<label>Branch Address </label><input class="input-sm" type="text" name="branchAddress" value="<%=branch.getBranchAddress()%>">
		</div><br> 
		<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary">Edit Branch</button>
	</div>
	</form>
</div>