package BookManager.BookCilent;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import BookManager.*;

public class BookClient {
	
	public static void menu() {
		System.out.println("----------ͼ����Ϣ����ϵͳ----------\n"
			+ "-------------1.�����鼮-------------\n" 
			+ "---------2.��ѯ�鼮(ͨ��ID)---------\n"
			+ "-------3.��ѯ�鼮(ͨ���ؼ���)-------\n"
			+ "-------------4.ɾ���鼮-------------\n"
			+ "-----------5.�����鼮���-----------\n"
			+ "-----------6.��ʾ�����鼮-----------\n"
			+ "-----------else.�˳�ϵͳ-----------\n"
			+ "-----------------------------------\n");
	}
	
    public static void main(String args[]) {

        try {
            String name = "BookManager";
            int serverPort = 8099;// ע�����ĵĶ˿ں�
			//��ȡע����������
			Registry registry = LocateRegistry.getRegistry(serverPort);
			MyInterface myInterface = (MyInterface) registry.lookup(name);
			
        	Scanner input = new Scanner(System.in);
    		menu();
			
			while(true) {
				System.out.println("�����빦��ѡ��: ");
	    		int x = input.nextInt();
	    		
				if(x==1) { // 1.�����鼮
					
					System.out.println("�������鼮��Ϣ (�鼮ID �鼮���� �������� �鼮���):\n");
					int bookID = input.nextInt();
					String bookName = input.next();
					String author = input.next();
					int bookNum = input.nextInt();
					Book newBook = new Book(bookID, bookName,author,bookNum);
					boolean flag = myInterface.add(newBook);
					if(flag) {
						System.out.println("�鼮���ӳɹ�! ");
					}
					else {
						System.out.println("�鼮�Ѵ��ڣ����ӿ����! ");
					}
				}
				
				else if(x==2) { // 2.��ѯ�鼮(ͨ��ID)
					
					System.out.println("��������Ҫ��ѯ���鼮ID: ");
					int queryBookID = input.nextInt();
					Book queryid = myInterface.queryByID(queryBookID);
					if (queryid != null) {
						System.out.println("�鼮��Ϣ����: \n");
						queryid.showInfo();
					} else {
						System.out.println("��ѯ�鼮������!");
					}
				}
				
				else if(x==3) { // 3.��ѯ�鼮(ͨ���ؼ���)
					
					System.out.println("�������鼮�����ƹؼ���: ");
					String bookKeyword = input.next();
					BookList bookList = myInterface.queryByName(bookKeyword);
					if (bookList != null) {
						System.out.println("-------------��ѯ�鼮�б�-------------\n");
						bookList.showInfo();
					} else {
						System.out.println("��ѯ�鼮������!");
					}
				}
				
				else if(x==4) { // 4.ɾ���鼮
					System.out.println("�������鼮ID: ");
					int bookID = input.nextInt();
					boolean flag = myInterface.delete(bookID);
					if(flag) {
						System.out.println("�鼮ɾ���ɹ�! ");
					}
					else {
						System.out.println("�鼮������! ");
					}
				}
				
				else if(x==5) { // 5.�����鼮���
					
					System.out.println("�������鼮ID �Լ����µĿ������: ");
					int bookID = input.nextInt();
					int bookNum = input.nextInt();
					boolean flag = myInterface.setNum(bookID, bookNum);
					if(flag) {
						System.out.println("�鼮ɾ��������³ɹ�! ");
					}
					else {
						System.out.println("�鼮������! ");
					}
				}
				
				else if(x==6) { //6.��ʾ�����鼮
					
					System.out.println("-------------����鼮�б�-------------");
					System.out.println(myInterface.showInfo());
				}
				
				else { // �˳�
					break;
				}
			}
			
			input.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}