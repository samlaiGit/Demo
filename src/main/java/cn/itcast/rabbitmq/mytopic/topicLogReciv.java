package cn.itcast.rabbitmq.mytopic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import cn.itcast.rabbitmq.util.ConnectionUtil;

public class topicLogReciv {

	private final static String EXCHANGE_NAME = "fanoutExc";

	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName=channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        QueueingConsumer consumer=new QueueingConsumer(channel);
        //返回consumertag
        channel.basicConsume(queueName, false, consumer);
        while(true){
        	  QueueingConsumer.Delivery delivery= consumer.nextDelivery();
        	  String message=new String(delivery.getBody());
        	  Thread tr=Thread.currentThread();
        	  System.out.println("["+tr.getName()+"]rec mes:"+message);
        	  print2File(message);
        	 
        }
	}
	
	private static void print2File(String msg) {
		try {
			String dir = topicLogReciv.class.getClassLoader().getResource("").getPath();
			String logFileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			File file = new File(dir, logFileName + ".log");
			System.out.println(file.getPath());
			FileOutputStream fos = new FileOutputStream(file, true);
			fos.write(((new SimpleDateFormat("HH:mm:ss").format(new Date())+" - "+msg + "\r\n").getBytes()));
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}  
}
