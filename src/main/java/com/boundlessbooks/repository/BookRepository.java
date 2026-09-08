package com.boundlessbooks.repository;

import com.boundlessbooks.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
<<<<<<< HEAD
	List<Book> findByTitleContainingIgnoreCase(String title);
=======
	List<Book> findByTitleIgnoreCase(String title, String author);
>>>>>>> branch 'main' of https://github.com/Wilkeaustin/boundless-books.git

	List<Book> findByGenreIgnoreCase(String genre);
}
