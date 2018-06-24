package com.project.bookmanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.bookmanagement.service.BorrowerService;
import com.project.bookmanagement.entity.Author;
import com.project.bookmanagement.entity.Book;
import com.project.bookmanagement.entity.BookCopies;
import com.project.bookmanagement.entity.BookLoans;
import com.project.bookmanagement.entity.Borrower;
import com.project.bookmanagement.entity.Branch;


/**
 * Servlet implementation class BorrowerServlet
 */
@WebServlet({"/borrowerLogin", "/fetchBooks", "/fetchLoans", "/checkOutBook", "/returnBook"})
public class BorrowerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BorrowerService borrowerService = new BorrowerService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		Boolean isAjax = Boolean.FALSE;
		String message = "";
		RequestDispatcher rd = null;
		switch (reqUrl) {
			case "/borrowerLogin":
				Integer cardNo = 0;
				if (cardNo!=null){
					cardNo = Integer.parseInt(request.getParameter("cardNo"));
				}
				Borrower borrower = null;
				String borrowerDetails = "";
				try {
					borrower = borrowerService.getBorrower(cardNo);
					borrowerDetails = borrower.getCardNo() + "//" + borrower.getName();
				} catch (Exception e) {
					borrowerDetails = "failed";
				}	
				finally{
					response.getWriter().write(borrowerDetails);
					isAjax= Boolean.TRUE;
				}
				break;
			case "/fetchBooks":
				Integer branchId = Integer.parseInt(request.getParameter("branchId"));
				List <BookCopies> bookcopies = new ArrayList<BookCopies>();
				if(branchId != 0){
					Branch branch = new Branch();
					branch.setBranchId(branchId);
					try {
						bookcopies = borrowerService.getBookCopiesInBranch(branch);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				String bookTable = bookTable(bookcopies);
				response.getWriter().write(bookTable);
				isAjax = Boolean.TRUE;
				break;
			case "/fetchLoans":
				Integer cardNumber = Integer.parseInt(request.getParameter("cardNo"));
				Borrower br = new Borrower(); br.setCardNo(cardNumber);
				List<BookLoans> bookloans = new ArrayList<BookLoans>();
				try {
					bookloans = borrowerService.getAllBorrowerLoans(br);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String loanTable = loansTable(bookloans);
				response.getWriter().write(loanTable);
				isAjax = Boolean.TRUE;
				
				break;
			case "/checkOutBook":
				Integer checkOutBookId = Integer.parseInt(request.getParameter("bookId"));
				Book checkOutBook = new Book(); checkOutBook.setBookId(checkOutBookId);
				Integer checkOutBranchId = Integer.parseInt(request.getParameter("branchId"));
				Branch checkOutBranch = new Branch(); checkOutBranch.setBranchId(checkOutBranchId);
				Integer checkOutCardNo = Integer.parseInt(request.getParameter("cardNo"));
				Borrower checkOutBorrower = new Borrower(); checkOutBorrower.setCardNo(checkOutCardNo);;
				BookCopies checkOutCopy = new BookCopies();
				checkOutCopy.setBook(checkOutBook); checkOutCopy.setBranch(checkOutBranch);
				List<BookLoans> updatedbookloans = new ArrayList<BookLoans>();
				List<BookCopies> updatedbookcopies = new ArrayList<BookCopies>();
				try {
					borrowerService.checkOutBook(checkOutCopy, checkOutBorrower);
					updatedbookloans = borrowerService.getAllBorrowerLoans(checkOutBorrower);
					updatedbookcopies = borrowerService.getBookCopiesInBranch(checkOutBranch);
					} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				response.getWriter().write(loansTable(updatedbookloans));
				response.getWriter().write("//");
				response.getWriter().write(bookTable(updatedbookcopies));
				isAjax = Boolean.TRUE;
				break;
			case "/returnBook":
				String dateOut = request.getParameter("dateOut");
				Integer returnBookId = Integer.parseInt(request.getParameter("bookId"));
				Book returnBook = new Book(); returnBook.setBookId(returnBookId);
				Integer returnBranchId = Integer.parseInt(request.getParameter("branchId"));
				Branch returnBranch = new Branch(); returnBranch.setBranchId(returnBranchId);
				Integer returnCardNo = Integer.parseInt(request.getParameter("cardNo"));
				Borrower returnBorrower = new Borrower(); returnBorrower.setCardNo(returnCardNo);;
				BookLoans returnCopy = new BookLoans();
				returnCopy.setDateOut(dateOut);
				returnCopy.setBook(returnBook); returnCopy.setBranch(returnBranch); returnCopy.setBorrower(returnBorrower);
				List<BookLoans> updatedBookLoans = new ArrayList<BookLoans>();
				List<BookCopies> updatedBookCopies = new ArrayList<BookCopies>();
				try {
					borrowerService.returnBook(returnCopy);
					updatedBookLoans = borrowerService.getAllBorrowerLoans(returnBorrower);
					updatedBookCopies = borrowerService.getBookCopiesInBranch(returnBranch);
					} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				response.getWriter().write(loansTable(updatedBookLoans));
				response.getWriter().write("//");
				response.getWriter().write(bookTable(updatedBookCopies));
				response.getWriter().write("//");
				response.getWriter().write(request.getParameter("branchId"));
				isAjax = Boolean.TRUE;
				break;
			default:
				break;	
		}
		
		if(!isAjax){
			request.setAttribute("message", message);
			rd.forward(request, response);
		}
	}
	
	private String bookTable(List<BookCopies> bookcopies){
		StringBuffer strbuffer = new StringBuffer();
		strbuffer.append("<tr><th>Book Title</th><th>Author(s)</th><th>Status</th></tr>");
		for (BookCopies bc:bookcopies){
			strbuffer.append("<tr><td>" + bc.getBook().getTitle() + "</td><td>");
			for (Author a:bc.getBook().getAuthors()){
				strbuffer.append("|" + a.getAuthorName() + "|");
			}
			strbuffer.append("</td><td>");
			if (bc.getCopies() > 0){
				strbuffer.append("Available </td>");
				strbuffer.append("<td><button class='btn btn-primary btn-small' onclick='checkOutBook("+bc.getBook().getBookId()+ "," + bc.getBranch().getBranchId() +")'> Check Out </button></td>");
			}
			else{
				strbuffer.append("Out of Stock </td>");
			}
			strbuffer.append("</tr>");
		}
		return strbuffer.toString();
	}
	
	private String loansTable(List<BookLoans> bookLoans){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
		StringBuffer strbuffer = new StringBuffer();
		strbuffer.append("<tr><th>Branch</th><th>Book Title</th><th>Author(s)</th><th>Date Out</th><th>Due Date</th></tr>");
		for (BookLoans bl:bookLoans){
			if (bl.getDateIn() == null){
				strbuffer.append("<tr><td>" + bl.getBranch().getBranchName() + "</td><td>" + bl.getBook().getTitle() + "</td><td>");
				for (Author a:bl.getBook().getAuthors()){
					strbuffer.append("|" + a.getAuthorName() + "|");
				}
				strbuffer.append("</td><td>"+LocalDateTime.parse(bl.getDateOut(), formatter)+"</td><td>"+LocalDateTime.parse(bl.getDueDate(), formatter)+"</td>");
				strbuffer.append("<td><button class='btn btn-primary btn-small' onclick='returnBook(" + bl.getBook().getBookId()+ "," + bl.getBranch().getBranchId() + "," + bl.getBorrower().getCardNo() + ",\"" + bl.getDateOut() +"\")'> Return Book</button></td>");
			}
		}
		return strbuffer.toString();
	}

}
