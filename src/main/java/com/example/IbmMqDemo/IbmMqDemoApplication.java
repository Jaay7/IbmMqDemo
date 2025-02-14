package com.example.IbmMqDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableJms
public class IbmMqDemoApplication {

	@Autowired
	private JmsTemplate jmsTemplate;

	public static void main(String[] args) {
		SpringApplication.run(IbmMqDemoApplication.class, args);
	}

	@GetMapping("send")
	String send(@RequestBody MessageBody messageBody){
		try{
			jmsTemplate.convertAndSend("DEV.QUEUE.1", messageBody.getBody());
			return "OK";
		}catch(JmsException ex){
			ex.printStackTrace();
			return "FAIL";
		}
	}

	@GetMapping("recv")
	String recv(){
		try{
			return jmsTemplate.receiveAndConvert("DEV.QUEUE.1").toString();
		}catch(JmsException ex){
			ex.printStackTrace();
			return "FAIL";
		}
	}
}

class MessageBody {
	String body;

	public MessageBody(String body) {
		this.body = body;
	}

	public MessageBody() {
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}