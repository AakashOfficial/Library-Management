package com.project.bookmanagement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.bookmanagement.entity.Author;
import com.project.bookmanagement.entity.Book;

public class AuthorDAO extends BaseDAO {

	public AuthorDAO(Connection conn) {
		super(conn);
	}
	
	public void addAuthor(Author author) throws SQLException{
		String query = "INSERT INTO tbl_author(authorName) VALUES (?)";
		Object[] params = new Object[]{author.getAuthorName()};
		save(query, params);
	}
	
	public Integer addAuthorWithId(Author author) throws SQLException{
		String query = "INSERT INTO tbl_author(authorName) VALUES (?)";
		Object[] params = new Object[]{author.getAuthorName()};
		return saveWithID(query, params);
	}
	
	public void updateAuthor(Author author) throws SQLException{
		String query = "UPDATE tbl_author SET authorName = ? WHERE authorId = ?";
		Object[] params = new Object[]{author.getAuthorName(), author.getAuthorId()};
		save(query, params);
	}
	
	public void deleteAuthor(Author author) throws SQLException{
		String query = "DELETE FROM tbl_author WHERE authorId = ?";
		Object[] params = new Object[]{author.getAuthorId()};
		save(query, params);
	}
	
	public Integer getAuthorsCount() throws SQLException{
		String query = "select count(*) as COUNT from tbl_author";
		return (getCount(query, null));
	}
	
	@SuppressWarnings("unchecked")
	public List<Author> readAllAuthors(Integer pageNo,String searchString) throws SQLException{
		setPageNo(pageNo);
		Object[] params = null;
		String query = "SELECT * FROM tbl_author";
		if(searchString != null){
			searchString = "%" + searchString + "%";
			query += " WHERE authorName LIKE ?";
			params = new Object[]{searchString};
		}
		return (List<Author>) read(query, params);
	}
	
	@SuppressWarnings("unchecked")
	public Author getAuthorByPK(Integer authorId) throws SQLException{
		String query = "SELECT * FROM tbl_author WHERE authorId = ?";
		Object[] params = new Object[]{authorId};
		List<Author> authors = (List<Author>) read(query, params);
		if (authors!=null) {
			return authors.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Author> getAuthorsBySearchString(Integer pageNo, String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		setPageNo(pageNo);
		String query = "SELECT * FROM tbl_author WHERE authorName LIKE ?";
		Object[] params = new Object[]{searchString};
		return (List<Author>) read(query, params);
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while (rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			String query = "select * from tbl_book where bookId IN (Select bookId from tbl_book_authors where authorId = ?)";
			Object[] params = new Object[]{a.getAuthorId()};
			a.setBooks((List<Book>) bdao.readFirstLevel(query, params));
			authors.add(a);
		}
		return authors;
	}

	@Override
	public List<Author> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			authors.add(a);
		}
		return authors;
	}

}
