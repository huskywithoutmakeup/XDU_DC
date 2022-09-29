package RSsystem.RSproducer;

import javax.jms.*;
import java.util.*;
import RSsystem.*;

//随机信号生成器
public class RSpublisher {
	@SuppressWarnings("static-access")
	public static void publishRandomSignal(Publisher rS,double m,double v) throws JMSException, InterruptedException {
		int delay = 100; // 延迟为100 ms
		int size = 1000;
		double randomSignal;
		Random random = new Random();
		Thread.currentThread().sleep(10000);
		for (int i = 0; i < size; i++) {
			Thread.currentThread().sleep(delay);
			// 产生N(a,b)的数：即均值为a，方差为b的随机数
			randomSignal = Math.sqrt(v)*random.nextGaussian()+m;
			rS.publishMessage(String.valueOf(randomSignal));
			System.out.println("随机信号" + " " + (i+1) + " 已发送 : " +randomSignal);
		}
	}
	
	public static void main(String args[]) throws JMSException, InterruptedException {
		// 发布主题 RandomSignal ”RANDOM“
		Publisher RS = new Publisher("RandomSignal");
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入正态分布的参数 均值 m / 方差 v");
        double m = scanner.nextDouble();
        double v = scanner.nextDouble();
        // 方便与后续验证
        scanner.close();
		publishRandomSignal(RS,m,v);
		RS.close();
	}
}