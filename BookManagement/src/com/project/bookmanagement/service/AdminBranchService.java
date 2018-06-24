package com.project.bookmanagement.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.project.bookmanagement.dao.BranchDAO;
import com.project.bookmanagement.entity.Branch;

public class AdminBranchService {
	ConnectionUtil cUtil = new ConnectionUtil();
	
	
	public void saveBranch(Branch branch) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BranchDAO brdao = new BranchDAO(conn);
		try{
			if (branch.getBranchId() != null){
				brdao.updateBranch(branch);
			}
			else{
				brdao.addBranch(branch);
			}
			conn.commit();
		} catch(SQLException e){
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn != null){
				conn.close();
			}
		}
	}
	
	public void deleteBranch(Branch branch) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BranchDAO brdao = new BranchDAO(conn);
		try {
			brdao.deleteBranch(branch);
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.rollback();
		} finally{
			if (conn != null){
				conn.close();
			}
		}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(conn != null){
				conn.close();
			}
		}
		return null;
	}
	
	public Branch getBranchById(Integer branchId) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BranchDAO brdao = new BranchDAO(conn);
		try {
			return brdao.getBranchByPK(branchId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(conn != null){
				conn.close();
			}
		}
		return null;
	}
	
}
