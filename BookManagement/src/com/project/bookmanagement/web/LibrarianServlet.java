package com.project.bookmanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.bookmanagement.entity.Book;
import com.project.bookmanagement.entity.BookCopies;
import com.project.bookmanagement.entity.Branch;
import com.project.bookmanagement.service.LibrarianService;

/**
 * Servlet implementation class LibrarianServlet
 */
@WebServlet({"/branchCopies", "/addCopies", "/editBranch"})
public class LibrarianServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LibrarianService librarianService = new LibrarianService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LibrarianServlet() {
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
		case "/branchCopies":
			Integer branchId = Integer.parseInt(request.getParameter("branchId"));
			String bookCopiesTable = "";
			if(branchId != 0){
				Branch branch = new Branch(); branch.setBranchId(branchId);
				try {
					List<BookCopies> bookcopies = librarianService.getBookCopiesInBranch(branch);
					bookCopiesTable = bookCopiesTable(bookcopies);
					response.getWriter().write(bookCopiesTable);
					isAjax = Boolean.TRUE;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "/addCopies" :
			BookCopies bc = new BookCopies();
			Integer noOfCopies = Integer.parseInt(request.getParameter("copies"));
			Integer addBranchId = Integer.parseInt(request.getParameter("branchId"));
			Integer addBookId = Integer.parseInt(request.getParameter("bookId"));
			try {
				Branch addBranch = librarianService.getBranchByPK(addBranchId);
				Book addBook = librarianService.getBookByPK(addBookId);
				bc =librarianService.getBookCopiesByPK(addBranchId, addBookId);
				bc.setCopies(bc.getCopies()+noOfCopies);
				librarianService.updateBookCopies(bc);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.setAttribute("selectedBranch", addBranchId);
			rd = request.getRequestDispatcher("librarianbranch.jsp");
			break;
		case "/editBranch":
			Integer editBranchId = Integer.parseInt(request.getParameter("selectedBranch"));
			String branchName = request.getParameter("branchName");
			String branchAddress = request.getParameter("branchAddress");
			String[] addBookIds = request.getParameterValues("addBooks[]");
			List<Book> addBooks = new ArrayList<Book>();
			Book addBook=null;
			if (addBookIds != null){
				for (String bookId:addBookIds){
					addBook = new Book();
					addBook.setBookId(Integer.parseInt(bookId));
					addBooks.add(addBook);
				}
			}
			
			Branch editBranch = new Branch();
			editBranch.setBranchId(editBranchId);
			editBranch.setBranchName(branchName);
			editBranch.setBranchAddress(branchAddress);
			try {
				librarianService.updateBranch(editBranch);
				if (!addBooks.isEmpty() && addBooks != null){
					librarianService.addBookCopies(addBooks, editBranch);
				}	
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("selectedBranch", editBranchId);
			rd = request.getRequestDispatcher("librarianbranch.jsp");
			break;
		default:
			break;
		}
		if(!isAjax){
			request.setAttribute("message", message);
			rd.forward(request, response);
		}
	}
	
	private String bookCopiesTable(List<BookCopies> bookcopies){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<tr><th>Book Title</th><th>Copies</th></tr>");
		for (BookCopies bc:bookcopies){
			strBuffer.append("<tr><td>" + bc.getBook().getTitle() + "</td><td>" + bc.getCopies() + "</td>");
			strBuffer.append("<td><button data-toggle='modal' data-target='#addCopiesModal' href='addbookcopies?bookId=" + bc.getBook().getBookId()+ "&branchId=" + bc.getBranch().getBranchId() +"'class='btn btn-sm btn-primary' type='button'>Add Copies</button></td></tr>");
		}
		return strBuffer.toString();
	}

}