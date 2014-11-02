package com.drcall.client.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SendMailUtil implements Runnable{

	private JavaMailSender mailSender;
	private String sendTo;
	private String subject;
	private String content;
	
	public SendMailUtil(JavaMailSender mailSender, String sendTo, String subject){
		this.mailSender = mailSender;
		this.sendTo = sendTo;
		this.subject = subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public void run() {
		
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(sendTo);
			helper.setSubject(subject);
			helper.setText(content);
			
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}

	
}
