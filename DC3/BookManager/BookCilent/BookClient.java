package BookManager.BookCilent;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import BookManager.*;

public class BookClient {
	
	public static void menu() {
		System.out.println("----------图书信息管理系统----------\n"
			+ "-------------1.添加书籍-------------\n" 
			+ "---------2.查询书籍(通过ID)---------\n"
			+ "-------3.查询书籍(通过关键字)-------\n"
			+ "-------------4.删除书籍-------------\n"
			+ "-----------5.更新书籍库存-----------\n"
			+ "-----------6.显示所有书籍-----------\n"
			+ "-----------else.退出系统-----------\n"
			+ "-----------------------------------\n");
	}
	
    public static void main(String args[]) {

        try {
            String name = "BookManager";
            int serverPort = 8099;// 注册中心的端口号
			//获取注册中心引用
			Registry registry = LocateRegistry.getRegistry(serverPort);
			MyInterface myInterface = (MyInterface) registry.lookup(name);
			
        	Scanner input = new Scanner(System.in);
    		menu();
			
			while(true) {
				System.out.println("请输入功能选项: ");
	    		int x = input.nextInt();
	    		
				if(x==1) { // 1.添加书籍
					
					System.out.println("请输入书籍信息 (书籍ID 书籍名称 作者姓名 书籍库存):\n");
					int bookID = input.nextInt();
					String bookName = input.next();
					String author = input.next();
					int bookNum = input.nextInt();
					Book newBook = new Book(bookID, bookName,author,bookNum);
					boolean flag = myInterface.add(newBook);
					if(flag) {
						System.out.println("书籍添加成功! ");
					}
					else {
						System.out.println("书籍已存在，添加库存量! ");
					}
				}
				
				else if(x==2) { // 2.查询书籍(通过ID)
					
					System.out.println("请输入需要查询的书籍ID: ");
					int queryBookID = input.nextInt();
					Book queryid = myInterface.queryByID(queryBookID);
					if (queryid != null) {
						System.out.println("书籍信息如下: \n");
						queryid.showInfo();
					} else {
						System.out.println("查询书籍不存在!");
					}
				}
				
				else if(x==3) { // 3.查询书籍(通过关键字)
					
					System.out.println("请输入书籍的名称关键字: ");
					String bookKeyword = input.next();
					BookList bookList = myInterface.queryByName(bookKeyword);
					if (bookList != null) {
						System.out.println("-------------查询书籍列表-------------\n");
						bookList.showInfo();
					} else {
						System.out.println("查询书籍不存在!");
					}
				}
				
				else if(x==4) { // 4.删除书籍
					System.out.println("请输入书籍ID: ");
					int bookID = input.nextInt();
					boolean flag = myInterface.delete(bookID);
					if(flag) {
						System.out.println("书籍删除成功! ");
					}
					else {
						System.out.println("书籍不存在! ");
					}
				}
				
				else if(x==5) { // 5.更新书籍库存
					
					System.out.println("请输入书籍ID 以及更新的库存数量: ");
					int bookID = input.nextInt();
					int bookNum = input.nextInt();
					boolean flag = myInterface.setNum(bookID, bookNum);
					if(flag) {
						System.out.println("书籍删库存量更新成功! ");
					}
					else {
						System.out.println("书籍不存在! ");
					}
				}
				
				else if(x==6) { //6.显示所有书籍
					
					System.out.println("-------------库存书籍列表-------------");
					System.out.println(myInterface.showInfo());
				}
				
				else { // 退出
					break;
				}
			}
			
			input.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}