package com.rackspace.library.book.dao;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.rackspace.library.book.dto.Book;
import com.rackspace.library.file.service.FileService;

public class BookRepo {
	private FileService fileService;

	/**
	 * instantiate File service
	 * 
	 * @throws IOException
	 */
	public BookRepo() throws IOException {
		fileService = new FileService();

	}

	/**
	 * get list of Books
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<Book> findAll() throws IOException {
		return fileService.readAll();

	}

	/**
	 * Store new book record
	 * 
	 * @param book data of the new book
	 * @return book id
	 * @throws IOException
	 */
	public int save(Book book) throws IOException {
		return fileService.save(book);

	}

	/**
	 * update record into the csv file
	 * 
	 * @param book updated book object
	 * @throws IOException
	 */
	public void update(Book book) throws IOException {
		fileService.update(book);
	}

	/**
	 * get book by id
	 * 
	 * @param bookId id
	 * @return book object
	 * @throws IOException
	 */
	public Book findById(int bookId) throws IOException {
		List<Book> books = fileService.readAll();
		books = books.stream().filter(e -> e.getId() == bookId).collect(Collectors.toList());
		return books.size() == 1 ? books.get(0) : null;
	}

}
