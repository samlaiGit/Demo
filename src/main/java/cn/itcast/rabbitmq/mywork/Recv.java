package cn.itcast.rabbitmq.mywork;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;

import cn.itcast.rabbitmq.util.ConnectionUtil;

public class Recv {

    private final static String QUEUE_NAME = "test_queue";

    public static void main(String[] argv) throws Exception {

        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        
//        Consumer consumer = new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
//                throws IOException {
//              String message = new String(body, "UTF-8");
//              System.out.println(" [x] Received '" + message + "'");
//              try {
//            	  dowork(message);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}finally{
//					 System.out.println(" [x] Done");
//				}
//            }
//          };
        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列
        channel.basicConsume(QUEUE_NAME, false, consumer);
        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            try {
            	dowork(message);
			} finally{
				 System.out.println(" [x] Done");
				 channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			}
        }
    }

	protected static void dowork(String message) throws InterruptedException {
		String[] meschars=message.split(",");
//				.toCharArray();
		for(String meschar:meschars){
			char[] c=meschar.toCharArray();
			for(char strc: c){
				if (strc == '.') Thread.sleep(1000);
			}
		}
	}
}