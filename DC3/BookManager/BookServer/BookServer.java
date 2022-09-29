package BookManager.BookServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import BookManager.MyInterface;

public class BookServer {
    public static void main(String[] args) throws Exception {

        try {
            String name = "BookManager";
            MyInterface engine = new BookMethodImpl();
			// ���ɰ���engine������������󣬼�skeleton����
            MyInterface skeleton = (MyInterface) UnicastRemoteObject.exportObject(engine, 0);
			// ��ȡע�����ĵ����ã�ʾ���У�ע�����������ڱ��ؼ�����ϡ�
			Registry registry = LocateRegistry.createRegistry(8099);
            System.out.println("Registering BookMangaer System");
            registry.rebind(name, skeleton);
        } catch (Exception e) {
            System.err.println("Exception:" + e);
            e.printStackTrace();
        }
    }
}