package com.project.bookmanagement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.bookmanagement.entity.Genre;
import com.project.bookmanagement.entity.Book;

public class GenreDAO extends BaseDAO{

	public GenreDAO(Connection conn) {
		super(conn);
	}
	
	public void addGenre(Genre genre) throws SQLException{
		String query = "INSERT INTO tbl_genre(genre_name) VALUES (?)";
		Object[] params = new Object[]{genre.getGenreName()};
		save(query, params);
	}
	
	public Integer addGenreWithId(Genre genre) throws SQLException{
		String query = "INSERT INTO tbl_genre(genre_name) VALUES (?)";
		Object[] params = new Object[]{genre.getGenreName()};
		return saveWithID(query, params);
	}
	
	public void updateGenre(Genre genre) throws SQLException{
		String query = "UPDATE tbl_genre SET genre_name = ? WHERE genre_id = ?";
		Object[] params = new Object[]{genre.getGenreName(), genre.getGenreId()};
		save(query, params);
	}
	
	public void deleteGenre(Genre genre) throws SQLException{
		String query = "DELETE FROM tbl_genre WHERE genre_id = ?";
		Object[] params = new Object[]{genre.getGenreId()};
		save(query, params);
	}
	
	public Integer getGenresCount() throws SQLException{
		String query = "select count(*) as COUNT from tbl_genre";
		return (getCount(query, null));
	}
	
	@SuppressWarnings("unchecked")
	public List<Genre> readAllGenres(Integer pageNo,String searchString) throws SQLException{
		setPageNo(pageNo);
		Object[] params = null;
		String query = "SELECT * FROM tbl_genre";
		if(searchString != null){
			searchString = "%" + searchString + "%";
			query += " WHERE genre_name LIKE ?";
			params = new Object[]{searchString};
		}
		return (List<Genre>) read(query, params);
	}
	
	@SuppressWarnings("unchecked")
	public Genre getGenreByPK(Integer genre_id) throws SQLException{
		String query = "SELECT * FROM tbl_genre WHERE genre_id = ?";
		Object[] params = new Object[]{genre_id};
		List<Genre> genres = (List<Genre>) read(query, params);
		if (genres!=null) {
			return genres.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Genre> getGenresBySearchString(String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		String query = "SELECT * FROM tbl_genre WHERE genre_name LIKE ?";
		Object[] params = new Object[]{searchString};
		return (List<Genre>) read(query, params);
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while (rs.next()){
			Genre g = new Genre();
			g.setGenreId(rs.getInt("genre_id"));
			g.setGenreName(rs.getString("genre_name"));
			String query = "select * from tbl_book where bookId IN (Select bookId from tbl_book_genres where genre_id = ?)";
			Object[] params = new Object[]{g.getGenreId()};
			g.setBooks((List<Book>) bdao.readFirstLevel(query, params));
			genres.add(g);
		}
		return genres;
	}

	@Override
	public List<Genre> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<>();
		while (rs.next()){
			Genre g = new Genre();
			g.setGenreId(rs.getInt("genre_id"));
			g.setGenreName(rs.getString("genre_name"));
			genres.add(g);
		}
		return genres;
	}
}
