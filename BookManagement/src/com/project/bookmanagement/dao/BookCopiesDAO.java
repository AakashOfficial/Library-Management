package com.project.bookmanagement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.bookmanagement.entity.Author;
import com.project.bookmanagement.entity.Book;
import com.project.bookmanagement.entity.BookCopies;
import com.project.bookmanagement.entity.Branch;

public class BookCopiesDAO extends BaseDAO {

	public BookCopiesDAO(Connection conn) {
		super(conn);
	}
	
	public void addBookCopies(BookCopies bookCopies) throws SQLException{
		String query = "INSERT INTO tbl_book_copies VALUES (?, ?, ?)";
		Object[] params = new Object[]{bookCopies.getBook().getBookId(), bookCopies.getBranch().getBranchId(), bookCopies.getCopies()};
		save(query, params);
	}
	
	public Integer addBookCopiesWithId(BookCopies bookCopies) throws SQLException{
		String query = "INSERT INTO tbl_book_copies VALUES (?, ?, ?)";
		Object[] params = new Object[]{bookCopies.getBook().getBookId(), bookCopies.getBranch().getBranchId(), bookCopies.getCopies()};
		return saveWithID(query, params);
	}
	
	public void updateBookCopies(BookCopies bookCopies) throws SQLException{
		String query = "UPDATE tbl_book_copies SET noOfCopies = ? WHERE bookId = ? AND branchId = ?";
		Object[] params = new Object[]{bookCopies.getCopies(), bookCopies.getBook().getBookId(), bookCopies.getBranch().getBranchId()};
		save(query, params);
	}
	
	public void deleteBookCopies(BookCopies bookCopies) throws SQLException{
		String query = "DELETE FROM tbl_book_copies WHERE bookId = ? AND branchId = ?";
		Object[] params = new Object[]{bookCopies.getBook().getBookId(), bookCopies.getBranch().getBranchId()};
		save(query, params);
	}
	
	public Integer getCopiesCount() throws SQLException{
		String query = "select count(*) as COUNT from tbl_book_copies";
		return (getCount(query, null));
	}
	
	@SuppressWarnings("unchecked")
	public List<BookCopies> readAllBookCopies(Integer pageNo) throws SQLException{
		setPageNo(pageNo);
		String query = "SELECT * FROM tbl_book_copies";
		return (List<BookCopies>) read(query, null);
	}
	
	@SuppressWarnings("unchecked")
	public BookCopies getBookCopiesByPK(Integer bookId, Integer branchId) throws SQLException{
		String query = "SELECT * FROM tbl_book_copies WHERE bookId = ? AND branchId = ?";
		Object[] params = new Object[]{bookId, branchId};
		List<BookCopies> bookCopies = (List<BookCopies>) read(query, params);
		if (bookCopies!=null) {
			return bookCopies.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<BookCopies> getBookCopiesByBranchId(Integer branchId) throws SQLException{
		String query = "SELECT * FROM tbl_book_copies WHERE branchId = ?";
		Object[] params = new Object[]{branchId};
		return (List<BookCopies>) read(query, params);
	}
	

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {
		return extractDataFirstLevel(rs);
	}

	@Override
	public List<BookCopies> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<BookCopies> bookCopies = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		BranchDAO brdao = new BranchDAO(conn);
		while(rs.next()){
			BookCopies bc = new BookCopies();
			bc.setBook(bdao.getBookByPK(rs.getInt("bookId")));
			bc.setBranch(brdao.getBranchByPK(rs.getInt("branchId")));
			bc.setCopies(rs.getInt("noOfCopies"));
			bookCopies.add(bc);
			
		}
		return bookCopies;
	}

}
