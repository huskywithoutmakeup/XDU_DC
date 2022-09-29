package BookManager;

import java.io.Serializable;

public class Book implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5529019015075055189L;
	private int bookNum;
	private String bookName;
	private String author;
	private int bookID;
   

	public Book(int bookID, String bookName, String author,int bookNum) {
		this.bookNum=bookNum;
		this.bookName=bookName;
		this.author=author;
		this.bookID=bookID;
	}

	public int getID() {
		return bookID;
	}
	public void setID(int bookID) {
		this.bookID = bookID;
	}
	public String getAuthor() {
		return author;
	}
	public String getBookName() {
		return bookName;
	}
	public int getBookNum() {
		return bookNum;
	}
	
	public int addBookNum(int i) {
		this.bookNum = this.bookNum + i;
		return bookNum;
	}
	
	public void setBookNum(int i) {
		this.bookNum =  i;
	}
	
	public void showInfo() {
		System.out.println("ID: " + this.bookID
				+ "  Name:" + this.bookName + 
				"  Author:" + this.author + 
				"  Num:" + this.bookNum +"\n");
	}
}