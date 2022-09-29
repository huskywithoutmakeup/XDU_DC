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
		//�������ӹ���
        factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //��������
        connection = factory.createConnection();
        //�����Ự
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //��������
        topic = session.createTopic(topicName);
        //����������
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