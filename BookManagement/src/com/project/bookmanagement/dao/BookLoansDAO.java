package com.project.bookmanagement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.bookmanagement.entity.Book;
import com.project.bookmanagement.entity.BookLoans;
import com.project.bookmanagement.entity.Branch;

public class BookLoansDAO extends BaseDAO{
	
	public BookLoansDAO(Connection conn) {
		super(conn);
	}
	
	public void addBookLoans(BookLoans bookLoans) throws SQLException{
		String query = "INSERT INTO tbl_book_loans VALUES (?, ?, ?, ?, ?, ?)";
		Object[] params = new Object[]{bookLoans.getBook().getBookId(), bookLoans.getBranch().getBranchId(), bookLoans.getBorrower().getCardNo(), bookLoans.getDateOut(), bookLoans.getDueDate(), bookLoans.getDateIn()};
		save(query, params);
	}
	
	public Integer addBookLoansWithId(BookLoans bookLoans) throws SQLException{
		String query = "INSERT INTO tbl_book_loans VALUES (?, ?, ?, ?, ?, ?)";
		Object[] params = new Object[]{bookLoans.getBook().getBookId(), bookLoans.getBranch().getBranchId(), bookLoans.getBorrower().getCardNo(), bookLoans.getDateOut(), bookLoans.getDueDate(), bookLoans.getDateIn()};
		return saveWithID(query, params);
	}
	
	public void updateBookLoans(BookLoans bookLoans) throws SQLException{
		String query = "UPDATE tbl_book_loans SET dueDate = ?, dateIn = ? WHERE bookId = ? AND branchId = ? AND cardNo = ? AND dateOut = ?";
		Object[] params = new Object[]{bookLoans.getDueDate(), bookLoans.getDateIn(), bookLoans.getBook().getBookId(), bookLoans.getBranch().getBranchId(), bookLoans.getBorrower().getCardNo(), bookLoans.getDateOut()};
		save(query, params);
	}
	
	public void deleteBookLoans(BookLoans bookLoans) throws SQLException{
		String query = "DELETE FROM tbl_book_loans WHERE bookId = ? AND branchId = ? AND cardNo = ? AND dateOut = ?";
		Object[] params = new Object[]{bookLoans.getBook().getBookId(), bookLoans.getBranch().getBranchId(), bookLoans.getBorrower().getCardNo(), bookLoans.getDateOut()};
		save(query, params);
	}
	
	public Integer getBookLoansCount() throws SQLException{
		String query = "select count(*) as COUNT from tbl_book_loans";
		return (getCount(query, null));
	}
	
	@SuppressWarnings("unchecked")
	public List<BookLoans> readAllBookLoans(Integer pageNo, String searchString) throws SQLException{
		setPageNo(pageNo);
		Object[] params = null;
		String query = "SELECT * FROM tbl_book_loans";
		if(searchString != null){
			searchString = "%" + searchString + "%";
			query += " WHERE cardNo LIKE ?";
			params = new Object[]{searchString};
		}
		query+= " ORDER BY dateIn";
		return (List<BookLoans>) read(query, params);
	}
	
	@SuppressWarnings("unchecked")
	public BookLoans getBookLoansByPK(Integer bookId, Integer branchId, Integer cardNo, String dateOut) throws SQLException{
		String query = "SELECT * FROM tbl_book_loans WHERE bookId = ? AND branchId = ? AND cardNo = ? AND dateOut = ?";
		Object[] params = new Object[]{bookId, branchId, cardNo, dateOut};
		List<BookLoans> bookLoans = (List<BookLoans>) read(query, params);
		if (bookLoans!=null) {
			return bookLoans.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<BookLoans> getBookLoansByCardNo(Integer cardNo) throws SQLException{
		String query = "SELECT * FROM tbl_book_loans WHERE cardNo = ?";
		Object[] params = new Object[]{cardNo};
		return (List<BookLoans>) read(query, params);
	}
	
	@SuppressWarnings("unchecked")
	public List<BookLoans> getLoansByCardNo(int cardNo) throws SQLException{
		String query = "SELECT * FROM tbl_book_loans WHERE cardNo = ?";
		Object[] params = new Object[]{cardNo};
		return (List<BookLoans>) read(query, params);
	}
	
	@SuppressWarnings("unchecked")
	public List<BookLoans> getLoansByBranchId(int branchId) throws SQLException{
		String query = "SELECT * FROM tbl_book_loans WHERE branchId = ?";
		Object[] params = new Object[]{branchId};
		return (List<BookLoans>) read(query, params);
	}
	

	@Override
	public List<BookLoans> extractData(ResultSet rs) throws SQLException {
		return extractDataFirstLevel(rs);
	}

	@Override
	public List<BookLoans> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<BookLoans> bookLoans = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		BranchDAO brdao = new BranchDAO(conn);
		BorrowerDAO bodao = new BorrowerDAO(conn);
		while(rs.next()){
			BookLoans l = new BookLoans();
			l.setBook(bdao.getBookByPK(rs.getInt("bookId")));
			l.setBranch(brdao.getBranchByPK(rs.getInt("branchId")));
			l.setBorrower(bodao.getBorrowerByPK(rs.getInt("cardNo")));
			l.setDateOut(rs.getString("dateOut"));
			l.setDueDate(rs.getString("dueDate"));
			if (rs.getString("dateIn") != null){
				l.setDateIn(rs.getString("dateIn"));
			}
			bookLoans.add(l);
		}
		return bookLoans;
	}
}
