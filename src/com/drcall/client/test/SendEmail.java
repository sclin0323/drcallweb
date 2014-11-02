package com.drcall.client.test;

import java.io.UnsupportedEncodingException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendEmail
{
   public static void main(String [] args) throws Exception
   {    
  
	   SendMail m = new SendMail("test@gmail.com","sclin0323@gmail.com","subject","contenys");
	   
	   m.send();
	   
      }
}
