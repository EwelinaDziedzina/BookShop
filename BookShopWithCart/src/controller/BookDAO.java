package controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.Book;

public class BookDAO {



	public List <Book> getAllBooks(){
		List <Book> listOfBooks = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<Book> query = session.createQuery("FROM Book");
			listOfBooks = query.list();
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return listOfBooks;
	}

	public void insertBook (Book book) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Integer id = (Integer)session.save(book);
			System.out.println("Book added, id " + id);
			tx.commit();
		}catch(HibernateException e) {
			if (tx!= null)
				tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}

	public void deleteBook (int bookId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Book book = session.get(Book.class, bookId);
			session.delete(book);
			tx.commit();

		}catch(HibernateException e){
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
	}

	public Book getBookById(int bookId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Book book = session.get(Book.class, bookId);
		session.close();
		return book;
	}

	public void updateBook(Book bookToUpdate) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(bookToUpdate);
			tx.commit();
		}catch(HibernateException e) {
			if (tx!= null)
				tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}

	public List <Book> search (String search, String searchType) {

		List <Book> listOfBooks = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		String qry ="";
		try {
			if(searchType.equals("title")) {
				qry = "FROM Book b WHERE b.title LIKE '%' + :search + '%'" ;	
				//query.setParameter("name", name);
			}else if(searchType.equals("author")) {
				qry = "FROM Book b WHERE b.author LIKE '%' + :search + '%'" ;	
			}
			Query<Book> query = session.createQuery(qry);
			query.setParameter("search", search);
			listOfBooks = query.list();
		}catch(HibernateException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return listOfBooks;
	}

	/*try { (you need to have all parameters in the method to do this (title, author, des...))
			tx = session.beginTransaction();
			Book book = session.get(Book.class, bookId);
			book.setTitle(title);
			book.setAuthor(author);
			book.setDescription(description);
			book.setPrice(price);
			session.update(book);
			tx.commit();
		}catch(HibernateException e) {
			if (tx!= null)
			tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}*/
}

