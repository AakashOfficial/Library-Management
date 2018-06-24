package com.project.bookmanagement.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.project.bookmanagement.dao.BookCopiesDAO;
import com.project.bookmanagement.dao.BookLoansDAO;
import com.project.bookmanagement.dao.BorrowerDAO;
import com.project.bookmanagement.dao.BranchDAO;
import com.project.bookmanagement.entity.BookCopies;
import com.project.bookmanagement.entity.BookLoans;
import com.project.bookmanagement.entity.Borrower;
import com.project.bookmanagement.entity.Branch;

public class BorrowerService {
	ConnectionUtil cUtil = new ConnectionUtil();
	
	public Borrower getBorrower(int cardNo) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BorrowerDAO brdao = new BorrowerDAO(conn);
		try {
			return brdao.getBorrowerByPK(cardNo);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
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
	
	public List<BookLoans> getAllBorrowerLoans(Borrower br) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookLoansDAO bldao = new BookLoansDAO(conn);
		try {
			return bldao.getBookLoansByCardNo(br.getCardNo());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if (conn != null){
				conn.close();
			}
		}
		return null;
	}
	
	public void checkOutBook(BookCopies bc, Borrower br) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		BookLoansDAO bldao = new BookLoansDAO(conn);
		BookLoans bl = new BookLoans();
		bl.setBook(bc.getBook());
		bl.setBranch(bc.getBranch());
		bl.setBorrower(br);
		bl.setDateOut(LocalDateTime.now().toString());
		bl.setDueDate(LocalDateTime.now().plusDays(30).toString());
		
		try {
			bldao.addBookLoans(bl);
			bc = bcdao.getBookCopiesByPK(bc.getBook().getBookId(), bc.getBranch().getBranchId());
			bc.setCopies(bc.getCopies()-1);
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
	
	public void returnBook(BookLoans bl) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		BookLoansDAO bldao = new BookLoansDAO(conn);
		BookCopies bc = new BookCopies();
		
		try {
			bl = bldao.getBookLoansByPK(bl.getBook().getBookId(), bl.getBranch().getBranchId(), bl.getBorrower().getCardNo(), bl.getDateOut());
			bl.setDateIn(LocalDateTime.now().toString());
			bldao.updateBookLoans(bl);
			bc = bcdao.getBookCopiesByPK(bl.getBook().getBookId(), bl.getBranch().getBranchId());
			bc.setCopies(bc.getCopies()+1);
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
}
