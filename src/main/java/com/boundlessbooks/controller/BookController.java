package com.boundlessbooks.controller;

import com.boundlessbooks.model.Review;
import com.boundlessbooks.service.BookService;
import com.boundlessbooks.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

	private final BookService books;
	private final ReviewService reviews;

	public BookController(BookService books, ReviewService reviews) {
		this.books = books;
		this.reviews = reviews;
	}

	@GetMapping("/books/{id}")
	public String details(@PathVariable Long id, Model model) {
		model.addAttribute("book", books.findById(id));
		model.addAttribute("review", new Review());
		return "book-details";
	}

	@PostMapping("/books/{bookId}/reviews")
	public String review(
			@PathVariable Long bookId,
			@Valid @ModelAttribute Review review,
			BindingResult result,
			Authentication auth,
			Model model) {

		if (result.hasErrors()) {
			model.addAttribute("book", books.findById(bookId));
			return "book-details";
		}

		reviews.create(bookId, review, auth.getName());
		return "redirect:/books/" + bookId + "?reviewed";
	}
}
