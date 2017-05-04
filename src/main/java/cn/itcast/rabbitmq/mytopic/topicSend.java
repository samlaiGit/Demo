package cn.itcast.rabbitmq.mytopic;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import cn.itcast.rabbitmq.util.ConnectionUtil;

public class topicSend {

	private static  String EXCHANGE_NAME="fanoutExc";
	public static void main(String[] args) {
		Connection connection = null;
		Channel channel=null;
		try {
			connection=ConnectionUtil.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			channel=connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			String message="fanoutExc mes";
			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
			System.out.println(" [x1] Sent '" + message + "'");
	        channel.close();
	        connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
