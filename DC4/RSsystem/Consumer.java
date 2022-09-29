package RSsystem;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {
	
    private static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    private ConnectionFactory factory;
    private Connection connection;
    public Session session;
    public MessageConsumer consumer;
    public Topic topic;
	
	public Consumer(String topicName) throws JMSException {
		//创建连接工厂
        factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //创建连接
        connection = factory.createConnection();
        //创建会话
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建主题
        topic = session.createTopic(topicName);
        //创建消费者
		consumer = session.createConsumer(topic);
		
	}
	
	public void close() throws JMSException {
		if (connection != null) {
			connection.close();
		}
	}
	
	public void start() throws JMSException {
		if (connection != null) {
			connection.start();
		}
	}
}