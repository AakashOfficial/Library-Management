<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.project.bookmanagement.service.BorrowerService"%>
<%@page import="com.project.bookmanagement.entity.Branch"%>

<%
	BorrowerService borrowerService = new BorrowerService();
	List<Branch> branches = new ArrayList<>();
	branches = borrowerService.getAllBranches(null, null);
	Integer selectedBranch = 0;
	if (request.getAttribute("selectedBranch")!=null){
		String branchId = String.valueOf(request.getAttribute("selectedBranch"));
		selectedBranch = Integer.parseInt(branchId);
	}
%>

<script>
	function borrowerLogin(){
		$.ajax({
			url: "borrowerLogin",
			type: "POST",
			data:{
				cardNo: $(cardNo).val()
			}
		}).done(function (data){
			if (data !== 'failed'){
				var borrowerDetails = data.split("//", 2);
				$(userLogin).hide()
				$(selectBranch).show()
				$(welcomeUser).show()
				$("label[for='borrowerName']").text("Welcome " + borrowerDetails[1]);
				$('input[name="savedCardNo"]').val(borrowerDetails[0]);
				fetchLoans(borrowerDetails[0])
			} else{
				alert("Invalid Card Number");
			}
		})	
	}
	
	function fetchBooks(){
		$.ajax({
			url: "fetchBooks",
			type: "POST",
			data:{
				branchId: $(branchList).val()
			}
		}).done(function (data){
			$('#bookTable').html(data)
		})
	}
	
	function fetchLoans(cardNo){
		$.ajax({
			url: "fetchLoans",
			type: "POST",
			data:{
				cardNo: cardNo
			}
		}).done(function (data){
			$('#loansTable').html(data)
			$(bookLoans).show()
		})
	}
	
	function checkOutBook(bookId, branchId){
		$.ajax({
			url: "checkOutBook",
			type: "POST",
			data:{
				bookId: bookId,
				branchId: branchId,
				cardNo: $('#savedCardNo').val()
			}
		}).done(function (data){
			if (data !== null){
				var updatedTables = data.split("//", 2);
				$('#loansTable').html(updatedTables[0])
				$('#bookTable').html(updatedTables[1])
			}
		})
	}
	

	function returnBook(bookId, branchId, cardNo, dateOut){
		$.ajax({
			url: "returnBook",
			type: "POST",
			data:{
				bookId: bookId,
				branchId: branchId,
				cardNo: cardNo,
				dateOut: dateOut
			}
		}).done(function (data){
			if (data !== null){
				var updatedTables = data.split("//", 3);
				$('#loansTable').html(updatedTables[0])
 				$('#bookTable').html(updatedTables[1])
				$(branchList).val(updatedTables[2])
			}
		})
	}
</script>

<%@include file="header.html" %>

<div class="container">
	<div id="userLogin" class ="row">
		<div class="box">
			<label>Enter your Card No: </label> <input class="input-sm" id="cardNo" name="cardNo" type=number min=0 >
			<button class="btn btn-default btn-xs" onclick="borrowerLogin()">Login</button>
		</div> <%--Box--%>
 	</div> <%--Row--%>
	
	<div id="welcomeUser" class ="row" style="display:none;">
		<div class="box">
			<label for="borrowerName"></label>
		</div>
	</div>
 	
 	<div id="bookLoans" class ="row" style="display:none;">
	 	<div class="box">
	 	<h2>Book Loans</h2>
		 	<table class="table" id="loansTable">
			
			</table>
	 	</div> <%--Box--%>
 	</div> <%--Row--%>
 	
 	<div id="selectBranch" class ="row" style="display:none;">
		<div class="box">
			<h2>Find a Book: </h2>
			<label>Select a branch: </label>
			<select onchange="fetchBooks()" name="branchList" id="branchList">
				<option value="0">Select Branch</option>
				<%for(Branch b: branches){  %>
						<option value="<%=b.getBranchId()%>"><%=b.getBranchName() %></option>
				<%}%>
			</select>
			<table class="table" id="bookTable">
			
			</table>
		</div> <%--Box--%>
 	</div> <%--Row--%>
 	
</div> <%--Container--%>

<input type="hidden" id="savedCardNo" name="savedCardNo" >

<%@include file="footer.html" %>