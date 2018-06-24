package com.project.bookmanagement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.bookmanagement.entity.Author;
import com.project.bookmanagement.entity.Book;
import com.project.bookmanagement.entity.Genre;

public class BookDAO extends BaseDAO{

	public BookDAO(Connection conn) {
		super(conn);
	}
	
	public void addBook(Book book) throws SQLException{
		String query = "INSERT INTO tbl_book(title, pubId) VALUES (?, ?)";
		Object[] params = new Object[]{book.getTitle(), book.getPublisher().getPublisherId()};
		save(query, params);
	}
	
	public Integer addBookWithId(Book book) throws SQLException{
		String query = "INSERT INTO tbl_book(title, pubId) VALUES (?, ?)";
		Object[] params = new Object[]{book.getTitle(), book.getPublisher().getPublisherId()};
		return saveWithID(query, params);
	}
	
	public void updateBook(Book book) throws SQLException{
		String query = "UPDATE tbl_book SET title = ?, pubId = ? WHERE bookId = ?";
		Object[] params = new Object[]{book.getTitle(), book.getPublisher().getPublisherId(), book.getBookId()};
		save(query, params);
	}
	
	public void deleteBook(Book book) throws SQLException{
		String query = "DELETE FROM tbl_book WHERE bookId = ?";
		Object[] params = new Object[]{book.getBookId()};
		save(query, params);
	}
	
	public Integer getBooksCount() throws SQLException{
		String query = "select count(*) as COUNT from tbl_book";
		return (getCount(query, null));
	}
	
	@SuppressWarnings("unchecked")
	public List<Book> readAllBooks(Integer pageNo,String searchString) throws SQLException{
		setPageNo(pageNo);
		Object[] params = null;
		String query = "SELECT * FROM tbl_book";
		if(searchString != null){
			searchString = "%" + searchString + "%";
			query += " WHERE title LIKE ?";
			params = new Object[]{searchString};
		}
		return (List<Book>) read(query, params);
	}
	
	@SuppressWarnings("unchecked")
	public Book getBookByPK(Integer bookId) throws SQLException{
		String query = "SELECT * FROM tbl_book WHERE bookId = ?";
		Object[] params = new Object[]{bookId};
		List<Book> books = (List<Book>) read(query, params);
		if (books!=null) {
			return books.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Book> getBooksBySearchString(String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		String query = "SELECT * FROM tbl_book WHERE title LIKE ?";
		Object[] params = new Object[]{searchString};
		return (List<Book>) read(query, params);
	}
	
	@SuppressWarnings("unchecked")
	public List<Book> getBooksInBranch(int branchId) throws SQLException{
		String query = "SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_copies WHERE branchId=?)";
		Object[] params = new Object[]{branchId};
		return (List<Book>)read(query, params);
	}
	
	public void addAuthorToBook(Book b, Author a) throws SQLException{
		String query = "INSERT INTO tbl_book_authors VALUES (?, ?)";
		Object[] params = new Object[]{b.getBookId(), a.getAuthorId()};
		save(query, params);
	}
	
	public void addGenreToBook(Book b, Genre g) throws SQLException{
		String query = "INSERT INTO tbl_book_genres VALUES (?, ?)";
		Object[] params = new Object[]{g.getGenreId(),b.getBookId()};
		save(query, params);
	}
	
	public void deleteAuthorFromBook(Book b, Author a) throws SQLException{
		String query = "DELETE FROM tbl_book_authors WHERE bookId = ? AND authorId = ?";
		Object[] params = new Object[]{b.getBookId(), a.getAuthorId()};
		save(query, params);
	}
	
	public void deleteGenreFromBook(Book b, Genre g) throws SQLException{
		String query = "DELETE FROM tbl_book_genres WHERE genre_id = ? AND bookId = ?";
		Object[] params = new Object[]{g.getGenreId(),b.getBookId()};
		save(query, params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		AuthorDAO adao = new AuthorDAO(conn);
		GenreDAO gdao = new GenreDAO(conn);
		PublisherDAO pdao = new PublisherDAO(conn);
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			if (rs.getObject("pubId") != null){
				b.setPublisher(pdao.getPublisherByPK(rs.getInt("pubId")));
			}
			String query = "SELECT * FROM tbl_author WHERE authorId IN (SELECT authorId FROM tbl_book_authors WHERE bookId = ?)";
			Object[] params = new Object[]{b.getBookId()};
			b.setAuthors((List<Author>) adao.readFirstLevel(query, params));
			query = "SELECT * FROM tbl_genre WHERE genre_Id IN (SELECT genre_Id FROM tbl_book_genres WHERE bookId = ?)";
			b.setGenres((List<Genre>) gdao.readFirstLevel(query, params));
			
			books.add(b);
		}
		return books;
	}

	@Override
	public List<Book> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			books.add(b);
		}
		return books;
	}

}
