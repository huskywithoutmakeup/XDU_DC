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
			// 生成包裹engine对象的容器对象，即skeleton对象
            MyInterface skeleton = (MyInterface) UnicastRemoteObject.exportObject(engine, 0);
			// 获取注册中心的引用，示例中，注册中心运行在本地计算机上。
			Registry registry = LocateRegistry.createRegistry(8099);
            System.out.println("Registering BookMangaer System");
            registry.rebind(name, skeleton);
        } catch (Exception e) {
            System.err.println("Exception:" + e);
            e.printStackTrace();
        }
    }
}