package cn.itcast.rabbitmq.mytopic;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;

import cn.itcast.rabbitmq.util.ConnectionUtil;

public class topciReciv {

	private final static String EXCHANGE_NAME = "fanoutExc";

	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName=channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        QueueingConsumer consumer=new QueueingConsumer(channel);
        channel.basicConsume(queueName, false, consumer);
        while(true){
        	  QueueingConsumer.Delivery delivery= consumer.nextDelivery();
        	  String message=new String(delivery.getBody());
        	  Thread tr=Thread.currentThread();
        	  System.out.println("["+tr.getName()+"]rec mes:"+message);
//              channel.basicAck(delivery.getEnvelope().getDeliveryTag(), true);
        }
	}
	
}
