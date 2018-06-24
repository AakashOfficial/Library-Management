package com.project.bookmanagement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.bookmanagement.entity.BookCopies;
import com.project.bookmanagement.entity.Branch;

public class BranchDAO extends BaseDAO{

	public BranchDAO(Connection conn) {
		super(conn);
	}
	
	public void addBranch(Branch branch) throws SQLException{
		String query = "INSERT INTO tbl_library_branch(branchName, branchAddress) VALUES(?,?)";
		Object[] params = new Object[]{branch.getBranchName(), branch.getBranchAddress()};
		save(query, params);
	}
	
	public Integer addBranchWithId(Branch branch) throws SQLException{
		String query = "INSERT INTO tbl_library_branch(branchName, branchAddress) VALUES(?,?)";
		Object[] params = new Object[]{branch.getBranchName(), branch.getBranchAddress()};
		return saveWithID(query, params);
	}
	
	public void updateBranch(Branch branch) throws SQLException{
		String query = "UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? WHERE branchId = ?";
		Object[] params = new Object[]{branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId()};
		save(query, params);
	}
	
	public void deleteBranch(Branch branch) throws SQLException{
		String query = "DELETE FROM tbl_library_branch WHERE branchId = ?";
		Object[] params = new Object[]{branch.getBranchId()};
		save(query, params);
	}
	public Integer getBranchesCount() throws SQLException{
		String query = "select count(*) as COUNT from tbl_library_branch";
		return (getCount(query, null));
	}
	
	@SuppressWarnings("unchecked")
	public List<Branch> readAllBranches(Integer pageNo,String searchString) throws SQLException{
		setPageNo(pageNo);
		Object[] params = null;
		String query = "SELECT * FROM tbl_library_branch";
		if(searchString != null){
			searchString = "%" + searchString + "%";
			query += " WHERE branchName LIKE ?";
			params = new Object[]{searchString};
		}
		return (List<Branch>) read(query, params);
	}
	
	@SuppressWarnings("unchecked")
	public Branch getBranchByPK(Integer branchId) throws SQLException{
		String query = "SELECT * FROM tbl_library_branch WHERE branchId = ?";
		Object[] params = new Object[]{branchId};
		List<Branch> branches = (List<Branch>) read(query, params);
		if(branches != null){
			return branches.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Branch> getBranchesBySearchString(String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		String query = "SELECT * FROM tbl_library_branch WHERE branchName LIKE";
		Object[] params = new Object[]{searchString};
		return (List<Branch>)read(query, params);
	}


	@Override
	public List<Branch> extractData(ResultSet rs) throws SQLException {
		return extractDataFirstLevel(rs);
	}

	@Override
	public List<Branch> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Branch> branches = new ArrayList<>();
		while(rs.next()){
			Branch br = new Branch();
			br.setBranchId(rs.getInt("branchId"));
			br.setBranchName(rs.getString("branchName"));
			br.setBranchAddress(rs.getString("branchAddress"));
			branches.add(br);
		}
		return branches;
	}

}
