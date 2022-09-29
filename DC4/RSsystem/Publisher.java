package RSsystem;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class Publisher {

	//����ActivMQ�����ӵ�ַ
    private static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    private static ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;
    private Topic topic;

    public Publisher(String topicName) throws JMSException {
    	//�������ӹ���
        factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //��������
        connection = factory.createConnection();
        //�����Ự
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //��������
        topic = session.createTopic(topicName);
        //����������
        producer = session.createProducer(topic);
        //������
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