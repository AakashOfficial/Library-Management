package com.project.bookmanagement.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.project.bookmanagement.dao.AuthorDAO;
import com.project.bookmanagement.dao.BookDAO;
import com.project.bookmanagement.dao.GenreDAO;
import com.project.bookmanagement.dao.PublisherDAO;
import com.project.bookmanagement.entity.Author;
import com.project.bookmanagement.entity.Book;
import com.project.bookmanagement.entity.Genre;
import com.project.bookmanagement.entity.Publisher;

public class AdminBookService {
	ConnectionUtil cUtil = new ConnectionUtil();
	
	
	public void saveAuthor(Author author) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try{
			if (author.getAuthorId() != null){
				adao.updateAuthor(author);
			}
			else{
				adao.addAuthor(author);
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
	
	public void saveBook(Book book) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			if(book.getBookId() != null){
				bdao.updateBook(book);
			}
			else{
				bdao.addBook(book);
			}
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	public Integer saveBookWithId(Book book) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		Integer bookId = null;
		try {
			bookId = bdao.addBookWithId(book);
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return bookId;
	}
	
	public void deleteAuthor(Author author) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			adao.deleteAuthor(author);
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
	
	public void deleteBook(Book book) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			bdao.deleteBook(book);
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
	
	public Author getAuthorByPK(Integer authorId) throws SQLException {
		Connection conn = null;
		
		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			return adao.getAuthorByPK(authorId);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
	
	public Publisher getPublisherByPK(Integer pubId) throws SQLException {
		Connection conn = null;
		
		conn = cUtil.getConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		try {
			return pdao.getPublisherByPK(pubId);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
	
	public Genre getGenreByPK(Integer genreId) throws SQLException {
		Connection conn = null;
		
		conn = cUtil.getConnection();
		GenreDAO gdao = new GenreDAO(conn);
		try {
			return gdao.getGenreByPK(genreId);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
	
	public Integer getAuthorsCount() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
				return adao.getAuthorsCount();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
	
	public Integer getBooksCount() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
				return bdao.getBooksCount();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
	
	public List<Author> getAllAuthors(Integer pageNo, String searchString) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			return adao.readAllAuthors(pageNo, searchString);
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
	
	public List<Book> getAllBooks(Integer pageNo, String searchString) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			return bdao.readAllBooks(pageNo, searchString);
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
	
	public void savePublisher(Publisher publisher) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		try{
			if (publisher.getPublisherId() != null){
				pdao.updatePublisher(publisher);
			}
			else{
				pdao.addPublisher(publisher);
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
	
	
	public void deletePublisher(Publisher publisher) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		try {
			pdao.deletePublisher(publisher);
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
	
	public Integer getPublishersCount() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		try {
				return pdao.getPublishersCount();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
	
	public List<Publisher> getAllPublishers(Integer pageNo, String searchString) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		try {
			return pdao.readAllPublishers(pageNo, searchString);
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
	
	public void saveGenre(Genre genre) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		GenreDAO gdao = new GenreDAO(conn);
		try{
			if (genre.getGenreId() != null){
				gdao.updateGenre(genre);
			}
			else{
				gdao.addGenre(genre);
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
	
	public void deleteGenre(Genre genre) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		GenreDAO gdao = new GenreDAO(conn);
		try {
			gdao.deleteGenre(genre);
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
	
	public Integer getGenresCount() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		GenreDAO gdao = new GenreDAO(conn);
		try {
				return gdao.getGenresCount();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
	
	public List<Genre> getAllGenres(Integer pageNo, String searchString) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		GenreDAO gdao = new GenreDAO(conn);
		try {
			return gdao.readAllGenres(pageNo, searchString);
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
	
	
	public void setBookAuthor(Book book, Author author) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try{
			bdao.addAuthorToBook(book, author);
			conn.commit();
		}catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		} finally{
			if(conn != null){
				conn.close();
			}
		}
	}
	
	public void setBookGenre(Book book, Genre genre) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try{
			bdao.addGenreToBook(book, genre);
			conn.commit();
		}catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		} finally{
			if(conn != null){
				conn.close();
			}
		}
	}
	
	public void removeBookAuthor(Book book, Author author) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try{
			bdao.deleteAuthorFromBook(book, author);
			conn.commit();
		}catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		} finally{
			if(conn != null){
				conn.close();
			}
		}
	}
	
	public void removeBookGenre(Book book, Genre genre) throws SQLException{
		Connection conn = null;
		
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try{
			bdao.deleteGenreFromBook(book, genre);
			conn.commit();
		}catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		} finally{
			if(conn != null){
				conn.close();
			}
		}
	}
	
	public void editBookAuthors(Book book, ArrayList<Author> authors) throws SQLException{
		Connection conn = null;
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		
		List<Author> removeAuthors = new ArrayList<>(book.getAuthors());
		removeAuthors.removeAll(authors);
		
		List<Author> newAuthors = authors;
		newAuthors.removeAll(book.getAuthors());
		
		for(Author removeAuthor:removeAuthors){
			try {
				bdao.deleteAuthorFromBook(book, removeAuthor);
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
			}
		}
		for (Author newAuthor:newAuthors){
			try {
				bdao.addAuthorToBook(book, newAuthor);
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
			}
		}
		conn.commit();		
	}

	public void editBookGenres(Book book, ArrayList<Genre> genres) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		
		List<Genre> removeGenres = new ArrayList<>(book.getGenres());
		removeGenres.removeAll(genres);
		
		List<Genre> newGenres = genres;
		newGenres.removeAll(book.getGenres());
		
		for(Genre removeGenre:removeGenres){
			try {
				bdao.deleteGenreFromBook(book, removeGenre);
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
			}
		}
		for (Genre newGenre:newGenres){
			try {
				bdao.addGenreToBook(book, newGenre);
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
			}
		}
		conn.commit();	
		
	}
	
	
	
}
