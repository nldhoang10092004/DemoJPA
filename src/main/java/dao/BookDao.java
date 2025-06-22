package dao;

import entity.Book;
import util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class BookDao {

    public void saveBook(Book book) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(book);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Book> getBooks() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM Book", Book.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Book getBook(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Book.class, id);
        } finally {
            em.close();
        }
    }

    public void updateBook(Book book) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(book);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void deleteBook(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            Book b = em.find(Book.class, id);
            if (b != null) {
                tx.begin();
                em.remove(b);
                tx.commit();
            }
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    public static void main(String[] args) {
        BookDao b = new BookDao();
        List<Book> bl = b.getBooks();
        for (Book book: bl){
                    System.out.println(book.toString());

        }
    }
}
