package RSsystem.RSproducer;

import javax.jms.*;
import java.util.*;
import RSsystem.*;

//����ź�������
public class RSpublisher {
	@SuppressWarnings("static-access")
	public static void publishRandomSignal(Publisher rS,double m,double v) throws JMSException, InterruptedException {
		int delay = 100; // �ӳ�Ϊ100 ms
		int size = 1000;
		double randomSignal;
		Random random = new Random();
		Thread.currentThread().sleep(10000);
		for (int i = 0; i < size; i++) {
			Thread.currentThread().sleep(delay);
			// ����N(a,b)����������ֵΪa������Ϊb�������
			randomSignal = Math.sqrt(v)*random.nextGaussian()+m;
			rS.publishMessage(String.valueOf(randomSignal));
			System.out.println("����ź�" + " " + (i+1) + " �ѷ��� : " +randomSignal);
		}
	}
	
	public static void main(String args[]) throws JMSException, InterruptedException {
		// �������� RandomSignal ��RANDOM��
		Publisher RS = new Publisher("RandomSignal");
        Scanner scanner = new Scanner(System.in);
        System.out.println("��������̬�ֲ��Ĳ��� ��ֵ m / ���� v");
        double m = scanner.nextDouble();
        double v = scanner.nextDouble();
        // �����������֤
        scanner.close();
		publishRandomSignal(RS,m,v);
		RS.close();
	}
}