package BookManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class BookList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ArrayList<Book> bookList = new ArrayList<Book>();
	
	public BookList(String fileLocation) throws FileNotFoundException {
		File file = new File(fileLocation);
		Scanner input = new Scanner(file);	
		while (input.hasNext()) {
			int bookNum = input.nextInt();
			String bookName = input.next();
			String author = input.next();
			int bookID = input.nextInt();
			bookList.add(new Book(bookID, bookName,author,bookNum));
		}
		input.close();
	}
	
	public String getInfo() {
		String info ="";
		for (int i = 0; i < bookList.size(); i++) {
			info += ("ID: " + bookList.get(i).getID()
			+ " Name:" + bookList.get(i).getBookName() + 
			" Author:" + bookList.get(i).getAuthor() + 
			" Num:" + bookList.get(i).getBookNum() +"\n");
		}
		return info;
	}
	
	public void showInfo() {
		System.out.println(getInfo());
	}
}