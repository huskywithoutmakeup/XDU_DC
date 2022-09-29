package RSsystem.RSanalysis;

import RSsystem.Consumer;
import javax.jms.*;
import java.util.Scanner;

public class AnalysisPublisher {
    public static void main(String[] args) throws JMSException {
    	Scanner scanner = new Scanner(System.in);
        System.out.println("请输入需要统计的随机信号数 N:");
        int n = scanner.nextInt();
        
        Consumer RSComsuer = null;
        AnalysisListener listener = new AnalysisListener(n);;  
        try {
        	RSComsuer = new Consumer("RandomSignal");
        	RSComsuer.consumer.setMessageListener(listener);
            RSComsuer.start();
            
            System.in.read();  //继续
            
        }catch (Exception e) {
			e.printStackTrace();
		} finally {
			RSComsuer.close();
			scanner.close();
		}    
    }
}