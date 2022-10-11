package com.emailReport;

import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import org.jsoup.Jsoup;

public class GmailInbox {

	@SuppressWarnings("unused")
	public String read(String subject) {

		String plainText = null;
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.port", "465");
		try {
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("UserID", "Password");
				}
			});
			Store store = session.getStore("imaps");
			store.connect("smtp.gmail.com", "UserID", "Password");
			System.out.println("connection is established with the Mail ID");
			Folder inbox = store.getFolder("inbox");
			inbox.open(Folder.READ_WRITE);
			int messageCount = inbox.getMessageCount();
			FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
			Message[] message = inbox.search(ft);
			boolean emailFound = false;
			for (int i = 0; i < message.length; i++) {
				if (emailFound == false) {
					if (message[i].getSubject().contains(subject)) {
						MimeMessage msg = (MimeMessage) message[i];
						String s = (String) msg.getContent();
						plainText = Jsoup.parse(s).text();
						FlagTerm ft1 = new FlagTerm(new Flags(Flags.Flag.SEEN), true);
						Message[] message1 = inbox.search(ft1);
						emailFound = true;
						break;
					}
				}
			}
			if (emailFound == false) {
				System.out.println("User is not received the mail or the mail content is read");
			}
			inbox.close(true);
			store.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plainText;
	}
}
