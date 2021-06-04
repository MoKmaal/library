package com.rackspace.library.book.service;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.rackspace.library.book.dao.BookRepo;
import com.rackspace.library.book.dto.Book;

/**
 * Service of the Book object
 * 
 * @author kamal
 *
 */
public class BookService {
	private BookRepo bookRepo;

	/**
	 * Constructor that instantiate BookRepo
	 * 
	 * @throws IOException
	 */
	public BookService() throws IOException {
		bookRepo = new BookRepo();
	}

	/**
	 * Load all books data into console format
	 * 
	 * @throws IOException
	 */
	public void loadAllBooksAsString() throws IOException {
		List<Book> books = bookRepo.findAll();

		for (Book book : books) {
			System.out.printf("[%d] %s\n", book.getId(), book.getTitle());
		}
	}

	/**
	 * Load list of books into console format
	 * 
	 * @param books list of books to be parsed
	 * @throws IOException
	 */
	public void loadBooksAsString(List<Book> books) throws IOException {

		for (Book book : books) {
			System.out.printf("[%d] %s\n", book.getId(), book.getTitle());
		}

	}

	/**
	 * get book details into console format by bookId
	 * 
	 * @param bookId id of the book
	 * @throws IOException
	 */
	public void viewBookDetails(String bookId) throws IOException {

		Book book = bookRepo.findById(Integer.parseInt(bookId));
		System.out.printf("ID: %d \n", book.getId());
		System.out.printf("Title: %s \n", book.getTitle());
		System.out.printf("Author: %s  \n", book.getAuthor());
		System.out.printf("Description: %s  \n", book.getDescription());
	}

	/**
	 * get number of books that stored into csv file
	 * 
	 * @return count of the books
	 * @throws IOException
	 */
	public int getCount() throws IOException {
		return bookRepo.findAll().size();
	}

	/**
	 * Create new Book
	 * 
	 * @param book data of the new book object
	 * @throws IOException
	 */

	public void save(Book book) throws IOException {
		int id = bookRepo.findAll().size() + 1;
		book.setId(id);
		System.out.printf("Book [%d] Saved", bookRepo.save(book));

	}

	/**
	 * update book by id
	 * 
	 * @param id bookId
	 * @throws IOException
	 */

	public void update(String id) throws IOException {
		Book book = bookRepo.findById(Integer.valueOf(id));
		Scanner input = new Scanner(System.in);

		System.out.printf("Title [%s]:", book.getTitle());
		String title = input.nextLine();
		System.out.println();

		System.out.printf("Author [%s]:", book.getAuthor());
		String author = input.nextLine();
		System.out.println();

		System.out.printf("Description [%s]:", book.getDescription());
		String description = input.nextLine();
		System.out.println();

		if (title.trim().length() > 0) {
			book.setTitle(title);
		}
		if (author.trim().length() > 0) {
			book.setAuthor(author);
		}
		if (description.trim().length() > 0) {
			book.setDescription(description);

		}
		bookRepo.update(book);
		System.out.println("Book saved.");
	}

	/**
	 * Search for books by subset of its title
	 * 
	 * @param keyword
	 * @throws IOException
	 */
	public void findByCriteria(String keyword) throws IOException {

		List<Book> books = bookRepo.findAll().stream()
				.filter(e -> e.getTitle().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
		// || e.getAuthor().toLowerCase().contains(keyword.toLowerCase())
		// || e.getDescription().toLowerCase().contains(keyword.toLowerCase())
		loadBooksAsString(books);

	}

}
