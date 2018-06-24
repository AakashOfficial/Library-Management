package com.project.bookmanagement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.bookmanagement.entity.Book;
import com.project.bookmanagement.entity.Borrower;

public class BorrowerDAO extends BaseDAO{

	public BorrowerDAO(Connection conn) {
		super(conn);
	}
	
	public void addBorrower(Borrower borrower) throws SQLException{
		String query = "INSERT INTO tbl_borrower(name, address, phone) VALUES(?,?,?)";
		Object[] params = new Object[]{borrower.getName(), borrower.getAddress(), borrower.getPhone()};
		save(query, params);
	}
	
	public Integer addBorrowerWithId(Borrower borrower) throws SQLException{
		String query = "INSERT INTO tbl_borrower(name, address, phone) VALUES(?,?,?)";
		Object[] params = new Object[]{borrower.getName(), borrower.getAddress(), borrower.getPhone()};
		return saveWithID(query, params);
	}
	
	public void updateBorrower(Borrower borrower) throws SQLException{
		String query = "UPDATE tbl_borrower SET name = ?, address = ?, phone = ? WHERE cardNo = ?";
		Object[] params = new Object[]{borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo()};
		save(query, params);
	}
	
	public void deleteBorrower(Borrower borrower) throws SQLException{
		String query = "DELETE FROM tbl_borrower WHERE cardNo = ?";
		Object[] params = new Object[]{borrower.getCardNo()};
		save(query, params);
	}
	
	public Integer getBorrowersCount() throws SQLException{
		String query = "select count(*) as COUNT from tbl_borrower";
		return (getCount(query, null));
	}
	
	@SuppressWarnings("unchecked")
	public List<Borrower> readAllBorrowers(Integer pageNo,String searchString) throws SQLException{
		setPageNo(pageNo);
		Object[] params = null;
		String query = "SELECT * FROM tbl_borrower";
		if(searchString != null){
			searchString = "%" + searchString + "%";
			query += " WHERE name LIKE ?";
			params = new Object[]{searchString};
		}
		return (List<Borrower>) read(query, params);
	}
	
	@SuppressWarnings("unchecked")
	public Borrower getBorrowerByPK(Integer cardNo) throws SQLException{
		String query = "SELECT * FROM tbl_borrower WHERE cardNo = ?";
		Object[] params = new Object[]{cardNo};
		List<Borrower> borrowers = (List<Borrower>) read(query, params);
		if(borrowers != null){
			return borrowers.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Borrower> getBorrowersBySearchString(String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		String query = "SELECT * FROM tbl_borrower WHERE name LIKE";
		Object[] params = new Object[]{searchString};
		return (List<Borrower>)read(query, params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		return extractDataFirstLevel(rs);
	}

	@Override
	public List<Borrower> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<>();
		while(rs.next()){
			Borrower b = new Borrower();
			b.setCardNo(rs.getInt("cardNo"));
			b.setName(rs.getString("name"));
			b.setAddress(rs.getString("address"));
			b.setPhone(rs.getString("phone"));
			borrowers.add(b);
		}
		return borrowers;
	}

}
