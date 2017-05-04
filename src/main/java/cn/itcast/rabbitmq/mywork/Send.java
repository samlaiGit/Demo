package cn.itcast.rabbitmq.mywork;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import  com.rabbitmq.client.Connection;

import cn.itcast.rabbitmq.util.ConnectionUtil;

public class Send {

    private final static String QUEUE_NAME = "test_queue";
	
	public static void main(String[] args) throws IOException {
		Connection connection=null;
		try {
			connection=ConnectionUtil.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 Channel channel = connection.createChannel();
		 channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		 int prefetchCount=1;
		 channel.basicQos(prefetchCount);
		 String mes="task......";
		 for(int i=0;i<50;i++){
			 String message=getMessage(i,mes);
			 channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			 System.out.println(" [x] Sent '" + message + "'");
	       
		 }
		  //关闭通道和连接
         channel.close();
         connection.close();
	}

	private static String getMessage(int i,String mes) {
		return i+mes;
	}

}
