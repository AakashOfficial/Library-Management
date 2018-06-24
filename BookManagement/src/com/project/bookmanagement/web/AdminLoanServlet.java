package com.project.bookmanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.bookmanagement.entity.BookLoans;
import com.project.bookmanagement.service.AdminLoanService;


/**
 * Servlet implementation class AdminLoanServlet
 */
@WebServlet({"/searchLoans", "/loansPagination", "/extendLoan"})
public class AdminLoanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    AdminLoanService adminLoanService = new AdminLoanService();  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLoanServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		Boolean isAjax = Boolean.FALSE;
		String message = "";
		switch (reqUrl) {
		case "/loansPagination":
			if(request.getParameter("pageNo") != null && !request.getParameter("pageNo").isEmpty()) {
				Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
				String searchString = null;
				if(request.getParameter("searchString") != null && !request.getParameter("searchString").isEmpty()){
					searchString = request.getParameter("searchString");
				}
				try {
					List<BookLoans> loans = adminLoanService.getAllLoans(pageNo, searchString);
					request.setAttribute("loans", loans);
					String loansTable = loansTable(loans);
					response.getWriter().write(loansTable);
					isAjax = Boolean.TRUE;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			break;
		case "/searchLoans" :
			String loansSearchString = request.getParameter("searchString");
			Integer loansPageNo = 1;
			if(loansSearchString.isEmpty() || loansSearchString == null){
				loansPageNo = Integer.parseInt(request.getParameter("pageNo"));
			}
			try{
				List<BookLoans> loans = adminLoanService.getAllLoans(loansPageNo, loansSearchString);
				String loansTable = loansTable(loans);
				response.getWriter().write(loansTable);
				isAjax = Boolean.TRUE;
			} catch(SQLException e){
				e.printStackTrace();
			}
			break;
		case "/extendLoan" :
			Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
			Integer cardNo = Integer.parseInt(request.getParameter("borrowerId"));
			Integer bookId = Integer.parseInt(request.getParameter("bookId"));
			Integer branchId = Integer.parseInt(request.getParameter("branchId"));
			String dateOut = request.getParameter("dateOut");
			BookLoans loan = new BookLoans();
			try {
				loan = adminLoanService.getLoanByPK(cardNo, bookId, branchId, dateOut);
				adminLoanService.overwriteLoan(loan);
				List<BookLoans> loans = adminLoanService.getAllLoans(pageNo, null);
				String loansTable = loansTable(loans);
				response.getWriter().write(loansTable);
				isAjax = Boolean.TRUE;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		default:
			break; 
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private String loansTable(List<BookLoans> loans){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<tr><th>Card No</th><th>Borrower Name</th><th>Book Title</th><th>Library Branch</th><th>Date Out</th><th>Date In</th><th>Due Date</th></tr>");
		for (BookLoans l:loans){
			strBuffer.append("<tr><td>"+ l.getBorrower().getCardNo() +"</td>");
			strBuffer.append("<td>"+ l.getBorrower().getName() +"</td>");
			strBuffer.append("<td>"+ l.getBook().getTitle() +"</td>");
			strBuffer.append("<td>"+ l.getBranch().getBranchName() +"</td>");
			strBuffer.append("<td>"+ l.getDateOut() +"</td><td>");
			if(l.getDateIn() != null){
				strBuffer.append(l.getDateIn());
			}
			strBuffer.append("</td><td>"+ l.getDueDate() +"</td>");
			if (l.getDateIn() == null){
				strBuffer.append("<td><button onclick='extendLoan("+l.getBorrower().getCardNo()+","+ l.getBook().getBookId()+","+ l.getBranch().getBranchId()+ ",\"" +l.getDateOut()+"\")' class='btn btn-primary' type='button'>Add 30 Days</button></td></tr>");
			}
		}
		return strBuffer.toString();
	}
}
