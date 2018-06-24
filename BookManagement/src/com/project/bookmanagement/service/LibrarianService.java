package com.project.bookmanagement.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.project.bookmanagement.dao.BookCopiesDAO;
import com.project.bookmanagement.dao.BookDAO;
import com.project.bookmanagement.dao.BranchDAO;
import com.project.bookmanagement.entity.Book;
import com.project.bookmanagement.entity.BookCopies;
import com.project.bookmanagement.entity.Branch;


public class LibrarianService {
	ConnectionUtil cUtil = new ConnectionUtil();
	
	
	public Integer getBranchesCount() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BranchDAO brdao = new BranchDAO(conn);
		try {
				return brdao.getBranchesCount();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
	
	public List<Branch> getAllBranches(Integer pageNo, String searchString) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BranchDAO brdao = new BranchDAO(conn);
		try {
			return brdao.readAllBranches(pageNo, searchString);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null){
				conn.close();
			}
		}
		return null;
	}
	
	public List<Book> getAllBooks(Integer pageNo, String searchString) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			return bdao.readAllBooks(pageNo, searchString);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null){
				conn.close();
			}
		}
		return null;
	}
	
	public List<Book> getAllBooksInBranch(Branch br) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			return bdao.getBooksInBranch(br.getBranchId());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null){
				conn.close();
			}
		}
		return null;
	}
	
	public List<BookCopies> getBookCopiesInBranch(Branch br) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		try {
			return bcdao.getBookCopiesByBranchId(br.getBranchId());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null){
				conn.close();
			}
		}
		return null;
	}
	
	public void addBookCopies (List<Book> books, Branch branch) throws SQLException{
		Connection conn = null;
		
		for (Book book:books){
		conn = cUtil.getConnection();
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		BookCopies newCopy = new BookCopies();	
			newCopy.setBook(book);
			newCopy.setBranch(branch);
			newCopy.setCopies(0);
			try {
				bcdao.addBookCopies(newCopy);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
			} finally{
				if(conn !=null){
					conn.close();
				}
			}
		}
		
	}
	
	public void addBookCopies(BookCopies bc) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		try {
			bcdao.addBookCopies(bc);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		} finally{
			if (conn != null){
				conn.close();
			}
		}
	}
	
	public void updateBookCopies(BookCopies bc) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		try {
			bcdao.updateBookCopies(bc);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		} finally{
			if (conn != null){
				conn.close();
			}
		}
	}
	
	public void updateBranch(Branch br) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BranchDAO brdao = new BranchDAO(conn);
		try {
			brdao.updateBranch(br);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		} finally{
			if (conn != null){
				conn.close();
			}
		}
	}
	
	public Book getBookByPK(Integer bookId) throws SQLException {
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			return bdao.getBookByPK(bookId);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
	
	public Branch getBranchByPK(Integer branchId) throws SQLException {
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BranchDAO brdao = new BranchDAO(conn);
		try {
			return brdao.getBranchByPK(branchId);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
	
	public BookCopies getBookCopiesByPK(Integer branchId, Integer bookId) throws SQLException {
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		try {
			return bcdao.getBookCopiesByPK(bookId, branchId);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
}
