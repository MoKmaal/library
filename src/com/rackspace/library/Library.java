package com.rackspace.library;

import java.io.IOException;
import java.util.Scanner;

import com.rackspace.library.book.dto.Book;
import com.rackspace.library.book.service.BookService;

public class Library {
	/**
	 * Main Method
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		BookService bookService = new BookService();
		System.out.printf("Loaded %d books into the library", bookService.getCount());
		loop: while (true) {
			// load intro
			initInstructions();

			Scanner input = new Scanner(System.in);
			Scanner in = new Scanner(System.in);
			// enter number from 1 to 5
			int choise = input.nextInt();
			switch (choise) {
			case 1:
				System.out.println("==== View Books ====");
				// load all books from csv file and store represent it into console
				bookService.loadAllBooksAsString();
				System.out.println();
				// loop to view books details by its id
				view: while (true) {
					System.out.println("To view details enter the book ID, to return press <Enter>");
					// press enter to exit the loop
					String bookId = in.nextLine();
					if (bookId == null || bookId.trim().length() == 0) {
						break view;
					}
					bookService.viewBookDetails(bookId);
				}
				break;
			case 2:
				// create new object of Book
				Book book = new Book();
				System.out.println("==== Add a Book ====");
				System.out.println("Please Enter the following information:");
				System.out.print("Title: ");
				book.setTitle(in.next());

				System.out.print("Author: ");
				book.setAuthor(in.next());

				System.out.print("Description: ");
				book.setDescription(in.next());
				// save the new record
				bookService.save(book);

				break;
			case 3:
				System.out.println("==== Edit a Book ====");
				// load list of the books
				bookService.loadAllBooksAsString();
				edit: while (true) {
					System.out.println("Enter the book ID of the book you want to edit; to return press <Enter>");
					// enter book id to edit or press enter to exit
					String id = in.nextLine();
					if (id == null || id.trim().length() == 0) {
						break edit;
					}
					// update the record by id
					bookService.update(id);

				}
				break;
			case 4:
				System.out.println("==== Search ====");
				System.out.println("Type in one or more keywords to search for");
				System.out.print("Search: ");
				// loop for searching by like query
				query: while (true) {
					// enter keyword
					String keyword = in.nextLine();
					System.out.println();
					System.out.println(
							"The following books matched your query. Enter the book ID to see more details, or <Enter> to return.");

					bookService.findByCriteria(keyword);

					// enter book id to view details or enter to exit
					String fBookId = in.nextLine();
					if (fBookId == null || fBookId.trim().length() == 0) {
						break query;
					}
					bookService.viewBookDetails(fBookId);
				}
				break;
			case 5:
				// exit the application
				in.close();
				input.close();
				break loop;

			}

		}
	}

	/**
	 * menu list
	 */
	private static void initInstructions() {
		System.out.println();
		System.out.println("==== Book Manager ====");
		System.out.println("1) View all books");
		System.out.println("2) Add a book");
		System.out.println("3) Edit a book");
		System.out.println("4) Search for a book");
		System.out.println("5) Save and exit");

		System.out.println("Choose [1-5]:");
	}

}
