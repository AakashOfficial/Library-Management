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

import com.project.bookmanagement.entity.Branch;
import com.project.bookmanagement.service.AdminBranchService;


/**
 * Servlet implementation class AdminBranchServlet
 */
@WebServlet({"/createBranch", "/editAdminBranch", "/deleteBranch", "/branchPagination", "/searchBranches"})
public class AdminBranchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdminBranchService adminBranchService = new AdminBranchService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminBranchServlet() {
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
		case "/branchPagination":
			if(request.getParameter("pageNo") != null && !request.getParameter("pageNo").isEmpty()) {
				Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
				String searchString = null;
				if(request.getParameter("searchString") != null && !request.getParameter("searchString").isEmpty()){
					searchString = request.getParameter("searchString");
				}
				try {
					List<Branch> branches = adminBranchService.getAllBranches(pageNo, searchString);
					request.setAttribute("branches", branches);
					String branchTable = branchTable(branches);
					response.getWriter().write(branchTable);
					isAjax = Boolean.TRUE;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			break;
		case "/searchBranches" :
			String branchSearchString = request.getParameter("searchString");
			Integer branchPageNo = 1;
			if(branchSearchString.isEmpty() || branchSearchString == null){
				branchPageNo = Integer.parseInt(request.getParameter("pageNo"));
			}
			try{
				List<Branch> branches = adminBranchService.getAllBranches(branchPageNo, branchSearchString);
				String branchTable = branchTable(branches);
				response.getWriter().write(branchTable);
				isAjax = Boolean.TRUE;
			} catch(SQLException e){
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
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		Boolean isAjax = Boolean.FALSE;
		String message = "";
		RequestDispatcher rd = null;
		switch (reqUrl) {
			case "/createBranch":
				Branch branch = new Branch();
				branch.setBranchName(request.getParameter("branchName"));
				branch.setBranchAddress(request.getParameter("branchAddress"));
				try {
					adminBranchService.saveBranch(branch);;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rd = request.getRequestDispatcher("viewbranches.jsp");
				break;
			case "/editAdminBranch":
				Branch editBranch = new Branch();
				editBranch.setBranchId(Integer.parseInt(request.getParameter("branchId")));
				editBranch.setBranchName(request.getParameter("branchName"));
				editBranch.setBranchAddress(request.getParameter("branchAddress"));
				try {
					adminBranchService.saveBranch(editBranch);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				rd = request.getRequestDispatcher("viewbranches.jsp");
				break;
			case "/deleteBranch":
				if(request.getParameter("branchId")!=null && !request.getParameter("branchId").isEmpty()){
					Integer branchId = Integer.parseInt(request.getParameter("branchId"));
					Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
					String searchString = request.getParameter("searchString");
					Branch deleteBranch = new Branch(); deleteBranch.setBranchId(branchId);
					List<Branch> newBranchList = new ArrayList<Branch>();
					try {
						adminBranchService.deleteBranch(deleteBranch);
						newBranchList = adminBranchService.getAllBranches(pageNo, searchString);
						String branchTable = "";
						branchTable = branchTable(newBranchList);	
						response.getWriter().write(branchTable);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					isAjax = Boolean.TRUE;
				}
				break;
			default:
				break;	
		}
		
		if(!isAjax){
			request.setAttribute("message", message);
			rd.forward(request, response);
		}
	}
	
	private String branchTable(List<Branch> branches){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<tr><th>Branch ID</th><th>Branch Name</th><th>Branch Address</th><th>Edit</th><th>Delete</th></tr>");
		for (Branch b:branches){
			strBuffer.append("<tr><td>"+(int)(branches.indexOf(b)+1)+"</td><td>"+b.getBranchName()+"</td><td>"+b.getBranchAddress()+"</td>");
			strBuffer.append("<td><button class='btn btn-primary' type='button' data-toggle='modal' data-target='#editBranchModal' href='admineditbranch.jsp?branchId="+b.getBranchId()+"'>Edit</button></td>");
			strBuffer.append("<td><button class='btn btn-danger' type='button' onclick= 'deleteBranch("+ b.getBranchId() +")'>Delete</button></td></tr>");
		}
		return strBuffer.toString();
	}

}
