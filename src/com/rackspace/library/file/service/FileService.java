package com.rackspace.library.file.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.rackspace.library.book.dto.Book;

/**
 * This class contains all operation of fetching and create data from and to csv
 * file
 * 
 * @author Kamal
 *
 */
public class FileService {

	File file;
	FileReader propertiesFileReader;
	Properties p;
	private Book book;
	private List<Book> books;
	FileReader booksFileReader;

	public FileService() throws IOException {
		loadProperties();

	}

	/**
	 * get data from property file that contains the path of the csv file
	 * 
	 * @throws IOException unable to access the file
	 */
	private void loadProperties() throws IOException {
		propertiesFileReader = new FileReader("db.properties");

		p = new Properties();
		p.load(propertiesFileReader);

		propertiesFileReader.close();
	}

	/**
	 * read all books info stored into the csv file
	 * 
	 * @return list of books
	 * @throws IOException unable to access the file
	 */
	public List<Book> readAll() throws IOException {
		books = new ArrayList<>();

		String booksFilePath = p.getProperty("booksfile");
		file = new File(booksFilePath);

		BufferedReader csvReader = new BufferedReader(new FileReader(file));
		String row;
		while ((row = csvReader.readLine()) != null) {
			book = new Book();
			String[] data = row.split(",");
			book.setId(Integer.parseInt(data[0]));
			book.setTitle(data[1]);
			book.setAuthor(data[2]);
			book.setDescription(data[3]);

			books.add(book);
		}

		csvReader.close();
		return books;

	}

	/**
	 * create new book record
	 * 
	 * @param book book data
	 * @return book id
	 * @throws IOException unable to access the file
	 */
	public int save(Book book) throws IOException {
		List<String> row = Arrays.asList(String.valueOf(book.getId()), book.getTitle(), book.getAuthor(),
				book.getDescription());
		String booksFilePath = p.getProperty("booksfile");
		file = new File(booksFilePath);

		FileWriter csvWriter = new FileWriter(file, true);
		if (book.getId() != 1) {
			csvWriter.append("\n");
		}
		csvWriter.append(String.join(",", row));

		csvWriter.flush();
		csvWriter.close();

		return book.getId();
	}

	/**
	 * update book by its id if you don't want to update specific value ignore it by
	 * pressing Enter
	 * 
	 * then get the index of the old record from the csv file to replace it with the new one
	 * @param book the updated object
	 * @throws IOException unable to access the file
	 */
	public void update(Book book) throws IOException {

		String booksFilePath = p.getProperty("booksfile");
		Path path = Paths.get(booksFilePath);
		List<Book> books = readAll();
		Book oldBook = books.stream().filter(p -> p.getId() == book.getId()).findFirst().get();
		int lineNumber = books.indexOf(oldBook);
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

		List<String> row = Arrays.asList(String.valueOf(book.getId()), book.getTitle(), book.getAuthor(),
				book.getDescription());

		lines.set(lineNumber, String.join(",", row));
		Files.write(path, lines, StandardCharsets.UTF_8);

	}

}
