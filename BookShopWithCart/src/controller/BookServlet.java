package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Book;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet(
		description = "This Servlet serves all book shop requests", 
		urlPatterns = { 
				"/BookServlet", 
				"/BookShop"
		})
public class BookServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;  
	private BookDAO bookDao;
    public BookServlet() {
       bookDao = new BookDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");
		if (action==null)
			action = "viewAll";
		System.out.println("action is " + action );
		
		switch (action) {
		case "insertNewBook":
			insertBook(request, response);
			break;
		case "showInsertForm":
			showInsertForm(request, response);
			break;	
		case "showUpdateForm":
			showUpdateForm(request, response);
			break;
		case "updateBook":
			updateBook(request, response);
			break;
		case "delete":
			deleteBook(request, response);
			break;
		case "showSearchForm":
			showSearchForm(request, response);
			break;
		case "search":
			search(request, response);
			break;
		case "addToCart":
			addToCart(request, response);
			break;
		case "viewCart":
			viewCart(request, response);
			break;
		case "clearCart":
			clearCart(request, response);
			break;
		case "addItemToCart":
			changeQuantityInCart(1, request, response);
			break;
		case "removeItemFromCart":
			changeQuantityInCart(-1, request, response);
			break;
		default:
			getAllBooks(request, response);
			break;
		}
	}
/*	protected void search(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String search = request.getParameter("search");
		System.out.println("You are searching for " + search);
		String searchType = request.getParameter("searchType");
		
		if (searchType.equals("title")) {
			List <Book> listOfBooks = bookDao.searchForTitle(search);
			request.setAttribute("listOfBooks", listOfBooks);
			RequestDispatcher dispatcher = request.getRequestDispatcher("\\WEB-INF\\view\\viewBooks.jsp");
			dispatcher.forward(request, response);
		} else if (searchType.equals("author")) {
			List <Book> listOfBooks = bookDao.searchForAuthor(search);
			request.setAttribute("listOfBooks", listOfBooks);
			RequestDispatcher dispatcher = request.getRequestDispatcher("\\WEB-INF\\view\\viewBooks.jsp");
			dispatcher.forward(request, response);
		} 
	}*/
	
	
	private void changeQuantityInCart(int newQuantity, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		Book book = bookDao.getBookById(bookId);
		System.out.printf("\nBook for id %d is %s\n", bookId, book);
		
		Map<Book, Integer> cart = (Map<Book, Integer>)request.getSession().getAttribute("cart");
		cart.put(book, cart.get(book) + newQuantity);

		if(cart.get(book)==0) 
			cart.remove(book);

		System.out.println("Quantity changed in the cart for " + book);
		response.sendRedirect("BookServlet?action=viewCart");
	}

	private void clearCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.getSession().removeAttribute("cart");
		response.sendRedirect("BookServlet?action=viewAll");
	}
	
	protected void viewCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("\\WEB-INF\\view\\viewCart.jsp");
		dispatcher.forward(request, response);
	}
	
	private void addToCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		Book book = bookDao.getBookById(bookId);
		System.out.printf("\nBook for id %d is %s\n", bookId, book);
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("cart") == null) {
			session.setAttribute("cart", new HashMap<Book, Integer>());
		}
		Map<Book, Integer> cart = (Map<Book, Integer>)session.getAttribute("cart");
		
		int quantity = 1;
		if(cart.containsKey(book)) {
			quantity = cart.get(book) + 1;
		}
		
		cart.put(book, quantity);
		System.out.println("Book added to cart");
		response.sendRedirect("BookServlet?action=viewAll");
	}
	
	protected void search(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String search = request.getParameter("search");
		String searchType = request.getParameter("searchType");
		request.setAttribute("search", search);
		request.setAttribute("searchType", searchType);
		
		System.out.println("You are searching for " + search);
			List <Book> listOfBooks = bookDao.search(search, searchType);
			request.setAttribute("listOfBooks", listOfBooks);
			RequestDispatcher dispatcher = request.getRequestDispatcher("\\WEB-INF\\view\\viewBooks.jsp");
			dispatcher.forward(request, response);
		} 
	
	private void showSearchForm (HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("\\WEB-INF\\view\\searchForm.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void showUpdateForm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		Book book = bookDao.getBookById(bookId);
		request.setAttribute("book", book);
		RequestDispatcher dispatcher = request.getRequestDispatcher("\\WEB-INF\\view\\updateBook.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void deleteBook(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		bookDao.deleteBook(bookId);
		response.sendRedirect("BookServlet?action=viewAll");
	}
	
	private void insertBook(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String description = request.getParameter("description");
		BigDecimal price = new BigDecimal(request.getParameter("price"));
		Book book = new Book(title, author, description, price);
		System.out.println("New book: " + book);
		bookDao.insertBook(book);
		response.sendRedirect("BookServlet?action=viewAll");
	}
	
	private void showInsertForm (HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("\\WEB-INF\\view\\insertBooks.jsp");
		dispatcher.forward(request, response);
	}

	protected void updateBook(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String description = request.getParameter("description");
		BigDecimal price = new BigDecimal(request.getParameter("price"));
		
		// create a updated book out of them
		
		Book bookToUpdate = new Book(bookId, title, author, description, price);
		bookDao.updateBook(bookToUpdate);
		
		//bookDao.updateBook(bookId, title, author, description, price); other way (see BookDao) 
		// request is complete, redirect the response to a 'viewAll'
		response.sendRedirect("BookServlet?action=viewAll");
	}
	
	private void getAllBooks(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		List <Book> listOfBooks = bookDao.getAllBooks();
		System.out.println(listOfBooks);
		request.setAttribute("listOfBooks", listOfBooks);
		RequestDispatcher dispatcher = request.getRequestDispatcher("\\WEB-INF\\view\\viewBooks.jsp");
		dispatcher.forward(request, response);
	}
}
