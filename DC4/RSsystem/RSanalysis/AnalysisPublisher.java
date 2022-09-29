package RSsystem.RSanalysis;

import RSsystem.Consumer;
import javax.jms.*;
import java.util.Scanner;

public class AnalysisPublisher {
    public static void main(String[] args) throws JMSException {
    	Scanner scanner = new Scanner(System.in);
        System.out.println("��������Ҫͳ�Ƶ�����ź��� N:");
        int n = scanner.nextInt();
        
        Consumer RSComsuer = null;
        AnalysisListener listener = new AnalysisListener(n);;  
        try {
        	RSComsuer = new Consumer("RandomSignal");
        	RSComsuer.consumer.setMessageListener(listener);
            RSComsuer.start();
            
            System.in.read();  //����
            
        }catch (Exception e) {
			e.printStackTrace();
		} finally {
			RSComsuer.close();
			scanner.close();
		}    
    }
}