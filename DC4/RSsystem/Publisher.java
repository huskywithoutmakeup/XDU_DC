package RSsystem;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class Publisher {

	//定义ActivMQ的连接地址
    private static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    private static ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;
    private Topic topic;

    public Publisher(String topicName) throws JMSException {
    	//创建连接工厂
        factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //创建连接
        connection = factory.createConnection();
        //创建会话
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建主题
        topic = session.createTopic(topicName);
        //创建生产者
        producer = session.createProducer(topic);
        //打开连接
        connection.start();
    }

    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }
    
	public void publishMessage(String text) throws JMSException {
		Message message = null;
		message = this.session.createTextMessage(text);
		this.producer.send(message);
	}

}