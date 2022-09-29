
import java.net.*;
import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.*;

 
public class ThreadPoolEchoServer {
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		ServerSocket listenSocket = new ServerSocket(8259);
		Socket socket = null;
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10,200,TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(5));
		
		int count = 0;
		System.out.println("Server listening at 8259");
		
		while(true) {
			socket = listenSocket.accept();
			count++;
			System.out.println("The total number of client is: "+count+" .");
			ServerThread serverThread = new ServerThread(socket);
			executor.execute(serverThread);
			System.out.println("The number of threads in the ThreadPool:"+executor.getPoolSize());
			System.out.println("The number of tasks in the Queue:"+executor.getQueue().size());
			System.out.println("The number of tasks completed:"+executor.getCompletedTaskCount());
		}
	}
}

