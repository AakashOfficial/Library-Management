package com.project.bookmanagement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.bookmanagement.entity.Author;
import com.project.bookmanagement.entity.Book;
import com.project.bookmanagement.entity.Publisher;

public class PublisherDAO extends BaseDAO{

	public PublisherDAO(Connection conn) {
		super(conn);
	}
	
	public void addPublisher(Publisher publisher) throws SQLException{
		String query = "INSERT INTO tbl_publisher(publisherName, publisherAddress, publisherPhone) VALUES(?,?,?)";
		Object[] params = new Object[]{publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone()};
		save(query, params);
	}
	
	public Integer addPublisherWithId(Publisher publisher) throws SQLException{
		String query = "INSERT INTO tbl_publisher(publisherName, publisherAddress, publisherPhone) VALUES(?,?,?)";
		Object[] params = new Object[]{publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone()};
		return saveWithID(query, params);
	}
	
	public void updatePublisher(Publisher publisher) throws SQLException{
		String query = "UPDATE tbl_publisher SET publisherName = ?, publisherAddress = ?, publisherPhone = ? WHERE publisherId = ?";
		Object[] params = new Object[]{publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone(), publisher.getPublisherId()};
		save(query, params);
	}
	
	public void deletePublisher(Publisher publisher) throws SQLException{
		String query = "DELETE FROM tbl_publisher WHERE publisherId = ?";
		Object[] params = new Object[]{publisher.getPublisherId()};
		save(query, params);
	}
	public Integer getPublishersCount() throws SQLException{
		String query = "select count(*) as COUNT from tbl_publisher";
		return (getCount(query, null));
	}

	@SuppressWarnings("unchecked")
	public List<Publisher> readAllPublishers(Integer pageNo,String searchString) throws SQLException{
		setPageNo(pageNo);
		Object[] params = null;
		String query = "SELECT * FROM tbl_publisher";
		if(searchString != null){
			searchString = "%" + searchString + "%";
			query += " WHERE publisherName LIKE ?";
			params = new Object[]{searchString};
		}
		return (List<Publisher>) read(query, params);
	}
	
	@SuppressWarnings("unchecked")
	public Publisher getPublisherByPK(Integer pubId) throws SQLException{
		String query = "SELECT * FROM tbl_publisher WHERE publisherId = ?";
		Object[] params = new Object[]{pubId};
		List<Publisher> publishers = (List<Publisher>) read(query, params);
		if(publishers != null){
			return publishers.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Publisher> getPublishersBySearchString(String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		String query = "SELECT * FROM tbl_publisher WHERE publisherName LIKE";
		Object[] params = new Object[]{searchString};
		return (List<Publisher>)read(query, params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while(rs.next()){
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherPhone(rs.getString("publisherPhone"));
			String query = "SELECT * FROM tbl_book WHERE pubId = ?";
			Object[] params = new Object[]{p.getPublisherId()};
			p.setBooks((List<Book>)bdao.readFirstLevel(query, params));
			publishers.add(p);
		}
		return publishers;
	}

	@Override
	public List<Publisher> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while(rs.next()){
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherPhone(rs.getString("publisherPhone"));
			publishers.add(p);
		}
		return publishers;
	}

}
