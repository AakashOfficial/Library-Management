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

import com.project.bookmanagement.entity.Author;
import com.project.bookmanagement.entity.Book;
import com.project.bookmanagement.entity.Genre;
import com.project.bookmanagement.entity.Publisher;
import com.project.bookmanagement.service.AdminBookService;


/**
 * Servlet implementation class AdminBookServlet
 */
@WebServlet({"/createPublisher", "/createAuthor", "/createGenre", "/createBook", "/editBook", "/editAuthor", "/editPublisher","/deletePublisher", "/editGenre", "/searchAuthors", "/authorPagination", "/deleteAuthor", "/deleteBook", "/searchBooks", "/bookPagination", "/searchPublishers", "/publisherPagination", "/searchGenres", "/genrePagination", "/deleteGenre"})
public class AdminBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdminBookService adminBookService = new AdminBookService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminBookServlet() {
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
		
		case "/authorPagination":
			if(request.getParameter("pageNo") != null && !request.getParameter("pageNo").isEmpty()) {
				Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
				String searchString = null;
				if(request.getParameter("searchString") != null && !request.getParameter("searchString").isEmpty()){
					searchString = request.getParameter("searchString");
				}
				try {
					List<Author> authors = adminBookService.getAllAuthors(pageNo, searchString);
					request.setAttribute("authors", authors);
					String authorTable = authorTable(authors);
					response.getWriter().write(authorTable);
					isAjax = Boolean.TRUE;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			break;
		case "/bookPagination":
			if(request.getParameter("pageNo") != null && !request.getParameter("pageNo").isEmpty()) {
				Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
				String searchString = null;
				if(request.getParameter("searchString") != null && !request.getParameter("searchString").isEmpty()){
					searchString = request.getParameter("searchString");
				}
				try {
					List<Book> books = adminBookService.getAllBooks(pageNo, searchString);
					request.setAttribute("books", books);
					String bookTable = bookTable(books);
					response.getWriter().write(bookTable);
					isAjax = Boolean.TRUE;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			break;		
		case "/publisherPagination":
			if(request.getParameter("pageNo") != null && !request.getParameter("pageNo").isEmpty()) {
				Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
				String searchString = null;
				if(request.getParameter("searchString") != null && !request.getParameter("searchString").isEmpty()){
					searchString = request.getParameter("searchString");
				}
				try {
					List<Publisher> publishers = adminBookService.getAllPublishers(pageNo, searchString);
					request.setAttribute("publishers", publishers);
					String publisherTable = publisherTable(publishers);
					response.getWriter().write(publisherTable);
					isAjax = Boolean.TRUE;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			break;
		case "/genrePagination":
			if(request.getParameter("pageNo") != null && !request.getParameter("pageNo").isEmpty()) {
				Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
				String searchString = null;
				if(request.getParameter("searchString") != null && !request.getParameter("searchString").isEmpty()){
					searchString = request.getParameter("searchString");
				}
				try {
					List<Genre> genres = adminBookService.getAllGenres(pageNo, searchString);
					request.setAttribute("genres", genres);
					String genreTable = genreTable(genres);
					response.getWriter().write(genreTable);
					isAjax = Boolean.TRUE;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			break;
			
		case "/searchAuthors":
			String authorSearchString = request.getParameter("searchString");
			Integer authorPageNo = 1;
			if(authorSearchString.isEmpty() || authorSearchString == null){
				authorPageNo = Integer.parseInt(request.getParameter("pageNo"));
			}
			try {
				List<Author> authors = adminBookService.getAllAuthors(authorPageNo, authorSearchString);
				String authorTable = authorTable(authors);
				response.getWriter().write(authorTable);
				isAjax = Boolean.TRUE;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/searchBooks" :
			String bookSearchString = request.getParameter("searchString");
			Integer bookPageNo = 1;
			if(bookSearchString.isEmpty() || bookSearchString == null){
				bookPageNo = Integer.parseInt(request.getParameter("pageNo"));
			}
			try{
				List<Book> books = adminBookService.getAllBooks(bookPageNo, bookSearchString);
				String bookTable = bookTable(books);
				response.getWriter().write(bookTable);
				isAjax = Boolean.TRUE;
			} catch(SQLException e){
				e.printStackTrace();
			}
			break;
		case "/searchPublishers" :
			String pubSearchString = request.getParameter("searchString");
			Integer pubPageNo = 1;
			if(pubSearchString.isEmpty() || pubSearchString == null){
				pubPageNo = Integer.parseInt(request.getParameter("pageNo"));
			}
			try{
				List<Publisher> publishers = adminBookService.getAllPublishers(pubPageNo, pubSearchString);
				String pubTable = publisherTable(publishers);
				response.getWriter().write(pubTable);
				isAjax = Boolean.TRUE;
			} catch(SQLException e){
				e.printStackTrace();
			}
			break;
		case "/searchGenres" :
			String genreSearchString = request.getParameter("searchString");
			Integer genrePageNo = 1;
			if(genreSearchString.isEmpty() || genreSearchString == null){
				genrePageNo = Integer.parseInt(request.getParameter("pageNo"));
			}
			try{
				List<Genre> genres = adminBookService.getAllGenres(genrePageNo, genreSearchString);
				String genreTable = genreTable(genres);
				response.getWriter().write(genreTable);
				isAjax = Boolean.TRUE;
			} catch(SQLException e){
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		
		if(!isAjax){
			request.setAttribute("message", message);
			RequestDispatcher rd = request.getRequestDispatcher("/viewauthors.jsp");
			rd.forward(request, response);
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
		case "/createPublisher":
			Publisher publisher = new Publisher();
			publisher.setPublisherName(request.getParameter("publisherName"));
			publisher.setPublisherAddress(request.getParameter("publisherAddress"));
			publisher.setPublisherPhone(request.getParameter("publisherPhone"));
			try {
				adminBookService.savePublisher(publisher);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rd = request.getRequestDispatcher("viewpublishers.jsp");
			break;
		case "/createAuthor":
			Author author = new Author();
			author.setAuthorName(request.getParameter("authorName"));
			try {
				adminBookService.saveAuthor(author);;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rd = request.getRequestDispatcher("viewauthors.jsp");
			break;
		case "/createGenre":
			Genre genre = new Genre();
			genre.setGenreName(request.getParameter("genreName"));
			try {
				adminBookService.saveGenre(genre);;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rd = request.getRequestDispatcher("viewgenres.jsp");
			break;
		case "/createBook":
			Book addBook = new Book();
			addBook.setTitle(request.getParameter("bookName"));
			String bookPublisher = request.getParameter("publisherId");
			Publisher myPublisher = new Publisher(); 
			if (!bookPublisher.equals("0")){
				myPublisher.setPublisherId(Integer.parseInt(bookPublisher));
			}
			addBook.setPublisher(myPublisher);
			String[] authorIds = request.getParameterValues("authorId[]");
			String[] genreIds = request.getParameterValues("genreId[]");
			ArrayList<Author> bookAuthors = new ArrayList<>();
			ArrayList<Genre> bookGenres = new ArrayList<>();
			Author bookAuthor = null;
			Genre bookGenre = null;
			for(String authorId:authorIds){
				bookAuthor = new Author(); bookAuthor.setAuthorId(Integer.parseInt(authorId));
				bookAuthors.add(bookAuthor);
			}
			for(String genreId:genreIds){
				bookGenre = new Genre(); bookGenre.setGenreId(Integer.parseInt(genreId));
				bookGenres.add(bookGenre);
			}
			try {
				addBook.setBookId(adminBookService.saveBookWithId(addBook));
				for (Author myAuthor: bookAuthors){
					adminBookService.setBookAuthor(addBook, myAuthor);
				}
				for(Genre myGenre: bookGenres){
					adminBookService.setBookGenre(addBook, myGenre);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			rd = request.getRequestDispatcher("viewbooks.jsp");
			break;
		case "/editAuthor":
			Author editAuthor = new Author();
			editAuthor.setAuthorId(Integer.parseInt(request.getParameter("authorId")));
			editAuthor.setAuthorName(request.getParameter("authorName"));
			try {
				adminBookService.saveAuthor(editAuthor);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			rd = request.getRequestDispatcher("viewauthors.jsp");
			break;
		case "/editPublisher":
			Publisher myEditPublisher = new Publisher();
			myEditPublisher.setPublisherId(Integer.parseInt(request.getParameter("pubId")));
			myEditPublisher.setPublisherName(request.getParameter("pubName"));
			myEditPublisher.setPublisherAddress(request.getParameter("pubAddress"));
			myEditPublisher.setPublisherPhone(request.getParameter("pubPhone"));
			try {
				adminBookService.savePublisher(myEditPublisher);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			rd = request.getRequestDispatcher("viewpublishers.jsp");
			break;
		case "/editGenre":
			Genre editGenre = new Genre();
			editGenre.setGenreId(Integer.parseInt(request.getParameter("genreId")));
			editGenre.setGenreName(request.getParameter("genreName"));
			try {
				adminBookService.saveGenre(editGenre);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			rd = request.getRequestDispatcher("viewgenres.jsp");
			break;
		case "/editBook":
			Book editBook = new Book();
			editBook.setBookId(Integer.parseInt(request.getParameter("bookId")));
			editBook.setTitle(request.getParameter("bookTitle"));
			Integer pubId = Integer.parseInt(request.getParameter("publisherId"));
			Publisher editPublisher = new Publisher(); 
			if (pubId != 0){
				editPublisher.setPublisherId(pubId);
			}
			editBook.setPublisher(editPublisher);
			String[] editAuthorIds = request.getParameterValues("authorId[]");
			String[] editGenreIds = request.getParameterValues("genreId[]");
			ArrayList<Author> editBookAuthors = new ArrayList<>();
			ArrayList<Genre> editBookGenres = new ArrayList<>();
			try {
				adminBookService.saveBook(editBook);
				Book book = adminBookService.getBookByPK(editBook.getBookId());
				for(String editAuthorId:editAuthorIds){
					editBookAuthors.add(adminBookService.getAuthorByPK(Integer.parseInt(editAuthorId)));
				}
				for(String editGenreId:editGenreIds){
					editBookGenres.add(adminBookService.getGenreByPK(Integer.parseInt(editGenreId)));
				}
				adminBookService.editBookAuthors(book, editBookAuthors);
				adminBookService.editBookGenres(book, editBookGenres);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			rd = request.getRequestDispatcher("viewbooks.jsp");
			break;
		case "/deleteBook":
			if(request.getParameter("bookId")!=null && !request.getParameter("bookId").isEmpty()){
				Integer bookId = Integer.parseInt(request.getParameter("bookId"));
				Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
				String searchString = request.getParameter("searchString");
				Book book = new Book(); book.setBookId(bookId);
				List<Book> newBookList = new ArrayList<Book>();
				try {
					adminBookService.deleteBook(book);
					newBookList = adminBookService.getAllBooks(pageNo, searchString);
					String bookTable = "";
					bookTable = bookTable(newBookList);	
					response.getWriter().write(bookTable);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isAjax = Boolean.TRUE;
			}
			break;
		case "/deleteAuthor":
			if(request.getParameter("authorId")!=null && !request.getParameter("authorId").isEmpty()){
				Integer authorId = Integer.parseInt(request.getParameter("authorId"));
				Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
				String searchString = request.getParameter("searchString");
				Author deleteAuthor = new Author(); deleteAuthor.setAuthorId(authorId);
				List<Author> newAuthorList = new ArrayList<Author>();
				try {
					adminBookService.deleteAuthor(deleteAuthor);
					newAuthorList = adminBookService.getAllAuthors(pageNo, searchString);
					String authorTable = "";
					authorTable = authorTable(newAuthorList);	
					response.getWriter().write(authorTable);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isAjax = Boolean.TRUE;
			}
			break;
		case "/deleteGenre":
			if(request.getParameter("genreId")!=null && !request.getParameter("genreId").isEmpty()){
				Integer genreId = Integer.parseInt(request.getParameter("genreId"));
				Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
				String searchString = request.getParameter("searchString");
				Genre deleteGenre = new Genre(); deleteGenre.setGenreId(genreId);
				List<Genre> newGenreList = new ArrayList<Genre>();
				try {
					adminBookService.deleteGenre(deleteGenre);
					newGenreList = adminBookService.getAllGenres(pageNo, searchString);
					String genreTable = "";
					genreTable = genreTable(newGenreList);
					response.getWriter().write(genreTable);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isAjax = Boolean.TRUE;
			}
			break;
		case "/deletePublisher":
			if(request.getParameter("pubId")!=null && !request.getParameter("pubId").isEmpty()){
				Integer publisherId = Integer.parseInt(request.getParameter("pubId"));
				Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
				String searchString = request.getParameter("searchString");
				Publisher deletePublisher = new Publisher(); deletePublisher.setPublisherId(publisherId);
				List<Publisher> newPublisherList = new ArrayList<Publisher>();
				try {
					adminBookService.deletePublisher(deletePublisher);
					newPublisherList = adminBookService.getAllPublishers(pageNo, searchString);
					String publisherTable = "";
					publisherTable = publisherTable(newPublisherList);
					response.getWriter().write(publisherTable);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
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
	
	private String bookTable(List<Book> books){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<tr><th>Book ID</th><th>Title</th><th>Author(s)</th><th>Publisher</th><th>Genre(s)</th></tr>");
		for (Book b:books){
			strBuffer.append("<tr><td>" + (int)(books.indexOf(b)+1) + "</td><td>" + b.getTitle() + "</td><td>");
			for (Author a:b.getAuthors()) {
				strBuffer.append("|" + a.getAuthorName() + "|");
			}
			strBuffer.append("</td><td>");
			if(b.getPublisher() != null){
				strBuffer.append(b.getPublisher().getPublisherName());
			}
			strBuffer.append("</td><td>");
			for (Genre g:b.getGenres()) {
				strBuffer.append("|" + g.getGenreName() + "|");
			}
			strBuffer.append("<td><button type='button' class='btn btn-primary' data-toggle='modal' data-target='#editBookModal' href='editbook.jsp?bookId="+b.getBookId()+"'>Edit</button></td>");
			strBuffer.append("<td><button class='btn btn-danger' type='button' onClick='deleteBook("+ b.getBookId() +")'>Delete</button></td></tr>");
		}
		return strBuffer.toString();
	}
	
	private String authorTable(List<Author> authors){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<tr><th>Author ID</th><th>Author Name</th><th>Books by Author</th><th>Edit</th><th>Delete</th></tr>");
		for(Author a: authors){
			strBuffer.append("<tr><td>"+(int)(authors.indexOf(a)+1) +"</td><td>"+a.getAuthorName()+"</td><td>");
			for(Book b: a.getBooks()){
				strBuffer.append('|' + b.getTitle() + '|');
			}
			strBuffer.append("</td><td><button type='button' class='btn btn-sm btn-primary' data-toggle='modal' data-target='#editAuthorModal' href='editauthor.jsp?authorId="+a.getAuthorId()+"'>Edit</button></td>");
			strBuffer.append("<td><button type='button' class='btn btn-sm btn-danger' onClick='deleteAuthor("+ a.getAuthorId() +")'>Delete</button></td></tr>");
		}
		return strBuffer.toString();
	}
	
	private String publisherTable(List<Publisher> publishers){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<tr><th>Publisher ID</th><th>Publisher Name</th><th>Publisher Address</th><th>Publisher Phone</th><th>Edit</th><th>Delete</th></tr>");
		for (Publisher p:publishers){
			strBuffer.append("<tr><td>"+(int)(publishers.indexOf(p)+1)+"</td><td>"+p.getPublisherName()+"</td><td>"+p.getPublisherAddress()+"</td><td>"+p.getPublisherPhone()+"</td>");
			strBuffer.append("<td><button class='btn btn-primary' type='button' data-toggle='modal' data-target='#editPublisherModal' href='editpublisher.jsp?pubId="+p.getPublisherId()+"'>Edit</button></td>");
			strBuffer.append("<td><button class='btn btn-danger' type='button' onclick= 'deletePublisher("+ p.getPublisherId() +")'>Delete</button></td></tr>");
		}
		return strBuffer.toString();
	}
	
	private String genreTable(List<Genre> genres){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<tr><th>Genre ID</th><th>Genre Name</th><th>Edit</th><th>Delete</th></tr>");
		for (Genre g:genres){
			strBuffer.append("<tr><td>"+(int)(genres.indexOf(g)+1)+"</td><td>"+g.getGenreName()+"</td>");
			strBuffer.append("<td><button class='btn btn-primary' type='button' data-toggle='modal' data-target='#editGenreModal' href='editgenre.jsp?genreId="+g.getGenreId()+"'>Edit</button></td>");
			strBuffer.append("<td><button class='btn btn-danger' type='button' onclick= 'deleteGenre("+ g.getGenreId() +")'>Delete</button></td></tr>");
		}
		return strBuffer.toString();
	}

}
