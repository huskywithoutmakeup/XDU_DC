package RSsystem.RSanalysis;

import javax.jms.*;
import RSsystem.Publisher;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class AnalysisListener implements MessageListener {
	private static double RS,mean,var,max=Double.NEGATIVE_INFINITY,min=Double.POSITIVE_INFINITY;
	private Queue<Double> RSqueue = new LinkedList<Double>();
	private static int cnt = 0;
	public int N = 10;  // 默认为10
	
	public AnalysisListener(int n) {
		this.N = n;
	}
	
	public void onMessage(Message message) {
		try {
			RS = Double.parseDouble(((TextMessage)message).getText());
			cnt++;
			RSqueue.offer(RS);
			if(RSqueue.size()>N) RSqueue.poll();
			calMean_Var();
			if(RS>max) max=RS;
			if(RS<min) min=RS;
			publishAll(var,mean,max,min);
			System.out.println("第"+cnt+"次分析结果已发布:"+var+' '+mean+' '+max+' '+min);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void publishAll(double var,double mean,double max,double min) throws JMSException {
		Publisher Analysis = new Publisher("Analysis");
		Analysis.publishMessage(String.valueOf(var)+" "+String.valueOf(mean)+" "+String.valueOf(max)+" "+String.valueOf(min));
		// 设置为短链接时必须关闭
		Analysis.close();
	}
	public void calMean_Var() {
		double RSsum=0,temp,varSum=0;
		Iterator<Double> iterate = RSqueue.iterator();
        while(iterate.hasNext()) {
        	RSsum +=iterate.next();
		}
		mean = RSsum / RSqueue.size();
		iterate = RSqueue.iterator();
		while(iterate.hasNext()) {
			temp =iterate.next()- mean;
			varSum += temp * temp;
		}
		var = varSum / RSqueue.size();
	}
}