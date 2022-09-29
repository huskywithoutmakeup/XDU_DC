package RSsystem.RSdisplay;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import RSsystem.Consumer;
import javax.jms.*;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


class DisplayListener implements MessageListener {
	private static double rs;
	private static int num = 0;
	 
    public void onMessage(Message message) {
        try {
        	num++;
        	rs = Double.parseDouble(((TextMessage)message).getText()); 
        	RealTimeChart.timeSeries.add(new Millisecond(), rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class RealTimeChart extends ChartPanel implements Runnable   
{   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static TimeSeries timeSeries;   
    static double rs;
       
    public RealTimeChart(String chartContent,String title,String yaxisName)   
    {   
        super(createChart(chartContent,title,yaxisName)); 
    }   
       
    private static JFreeChart createChart(String chartContent,String title,String yaxisName){   
        //创建时序图对象   
        timeSeries = new TimeSeries(chartContent,Millisecond.class);   
        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection(timeSeries);   
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title,"time",yaxisName,timeseriescollection,true,true,false);   
        XYPlot xyplot = jfreechart.getXYPlot();   
        //纵坐标设定   
        ValueAxis valueaxis = xyplot.getDomainAxis();   
        //自动设置数据轴数据范围   
        valueaxis.setAutoRange(true);   
        //数据轴固定数据范围 300s   
        valueaxis.setFixedAutoRange(30000D);   
   
        valueaxis = xyplot.getRangeAxis();   
   
        return jfreechart;   
    }   
    
    public void run()   
    {   
          
    }   
}  

public class RSdisplayConsumer {
    @SuppressWarnings("static-access")
	public static void main(String[] args) throws JMSException {
    	Consumer RSComsuer = null;
        DisplayListener listener = new DisplayListener();;  
        try {
        	RSComsuer = new Consumer("RandomSignal");
        	RSComsuer.consumer.setMessageListener(listener);
            RSComsuer.start();

            
            JFrame frame=new JFrame("RandomSignal Chart");
            RealTimeChart rtcp=new RealTimeChart("Random Data","RandomSignal Chart","value");   
            frame.getContentPane().add(rtcp,new BorderLayout().CENTER);   
            frame.pack();   
            frame.setVisible(true);   
            (new Thread(rtcp)).start();   
            frame.addWindowListener(new WindowAdapter(){   
                  public void windowClosing(WindowEvent windowevent)   
                  {   
                      System.exit(0);   
                  }   
              }); 
            
            System.in.read();  //继续
       
              
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	RSComsuer.close();
        }
    }
}