package BookManager.BookServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import BookManager.*;

public class BookMethodImpl implements MyInterface {

    protected BookMethodImpl() throws RemoteException, FileNotFoundException {
		super();
		// TODO Auto-generated constructor stub
	}
    
    String BookLocation = "F:\\Java-Workspace\\Java-Project-Eclipse\\DC_test\\src\\main\\java\\BookManager\\BookManager.txt";
    String QueryLocation = "F:\\Java-Workspace\\Java-Project-Eclipse\\DC_test\\src\\main\\java\\BookManager\\Query.txt";
	
    BookList BookList = new BookList(BookLocation);
	BookList query = new BookList(QueryLocation);

	public void update() throws RemoteException, FileNotFoundException {
		File file = new File(BookLocation);
		PrintWriter output = new PrintWriter(file);
		for (int i = 0; i < BookList.bookList.size(); i++) {
			output.print(BookList.bookList.get(i).getID() + " ");
			output.print(BookList.bookList.get(i).getBookName() + " ");
			output.print(BookList.bookList.get(i).getAuthor() + " ");
			output.println(BookList.bookList.get(i).getBookNum());
		}
		output.close();
	}

	public boolean add(Book b) throws RemoteException{
		for (int i = 0; i < BookList.bookList.size(); i++) {
			if (BookList.bookList.get(i).getID() == b.getID()) {
				BookList.bookList.get(i).addBookNum(b.getBookNum());
				System.out.println("[log] 书籍"+ BookList.bookList.get(i).getBookName() +"库存量更新为： " + BookList.bookList.get(i).getBookNum());
				return false;
			}
		}
		BookList.bookList.add(b);
		try {
			update();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("[log] 用户添加一本书籍!");
		return true;
	}
    
    public Book queryByID(int bookID) throws RemoteException {
    	Book b = null;
    	
    	for (int i = 0; i < BookList.bookList.size(); i++) {
			if (BookList.bookList.get(i).getID() == bookID) {
				b = BookList.bookList.get(i);
				System.out.println("[log] 用户进行一次ID查询!");
				return b;
			}
		}
    	System.out.println("[log] 用户ID查询失败!");
		return null;
    }
    
    public BookList queryByName(String name) throws RemoteException {
    	for (int i = 0; i < BookList.bookList.size(); i++) {
			if(BookList.bookList.get(i).getBookName().indexOf(name)!=-1){
                query.bookList.add(BookList.bookList.get(i));
            }			
		}
    	System.out.println("[log] 用户进行一次关键字查询!");
		return query;
    }
    
    public boolean delete(int bookID) throws RemoteException {
    	for (int i = 0; i < BookList.bookList.size(); i++) {
			if (BookList.bookList.get(i).getID() == bookID) {
				BookList.bookList.remove(BookList.bookList.get(i));
				try {
					update();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("[log] 用户删除一本书籍!");
				return true;
			}
		}
		System.out.println("[log] 用户删除书籍失败!");
		return false;
    }
    
    public boolean setNum(int bookID,int bookNum) throws RemoteException {
    	for (int i = 0; i < BookList.bookList.size(); i++) {
			if (BookList.bookList.get(i).getID() == bookID) {
				BookList.bookList.get(i).setBookNum(bookNum);
				try {
					update();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("[log] 书籍库存量更新成功!");
				return true;
			}
		}
		System.out.println("[log] 书籍库存量更新失败!");
		return false;
    }
    
    public String showInfo() throws RemoteException {
    	String info ="";
		for (int i = 0; i < BookList.bookList.size(); i++) {
			info += ("ID: " + BookList.bookList.get(i).getID()
			+ " Name:" + BookList.bookList.get(i).getBookName() + 
			" Author:" + BookList.bookList.get(i).getAuthor() + 
			" Num:" + BookList.bookList.get(i).getBookNum() +"\n");
		}
		System.out.println("[log] 书籍系统进行一次全显示!");
		return info;
    }
}