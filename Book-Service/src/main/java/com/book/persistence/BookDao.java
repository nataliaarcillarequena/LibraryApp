package com.book.persistence;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.book.entity.Book;

@Repository
public interface BookDao extends JpaRepository<Book, Integer> {

	//search book by book id is already included in JpaRepository (by naming convention I believe)
	Book searchBookByBookId(int id);
	
	
	//get all books- not in here - service layer using .findall()
	
	
	//updateQuantity using JPQL
	//change in copies = +ve when want to burrow a book, negative when want to return a book
	@Modifying
	@Transactional
	@Query("update Book set numberOfCopies = numberOfCopies - :numb where bookId = :bookId")
	int updateCopies(@Param("bookId") int bookId, @Param("numb") int changeInCopies);
	
//	just trying it - its for the MVC layer 
//	@Modifying
//	@Transactional
//	@Query("update Book set numberOfCopies = numberOfCopies - :numb, issueDate = :date where bookId = :bookId")
//	int updateBorrowedBoook(@Param("numb") int bookId, @Param("bookId") int changeInCopies, LocalDate issueDate);
	
	//@Transactional
	@Query("select b from Book b where b.bookName like %:input% or b.bookAuthor like %:input% or b.bookType like %:input% or b.bookDescription like %:input%")
	List<Book> selectBookWithInput(@Param("input") String input);
}