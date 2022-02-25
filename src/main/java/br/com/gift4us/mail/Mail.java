package br.com.gift4us.mail;
import java.io.File;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	public static boolean EnviarEmail(Properties props, final String emailfrom, final String password,
			String mensagemHtml, String assunto, String to, String attachFile, String[] copias) {

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailfrom, password);
			}
		});

		try {

			Multipart multipart = new MimeMultipart("alternative");

			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(mensagemHtml, CONTENT_TYPE);

			multipart.addBodyPart(htmlPart);

			Message message = new MimeMessage(session);

			if (attachFile != null && new File(attachFile).exists()) {
				MimeBodyPart attachPart = new MimeBodyPart();
				attachPart.attachFile(attachFile);
				multipart.addBodyPart(attachPart);
			}

			message.setContent(multipart);

			message.setFrom(new InternetAddress(emailfrom));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			if (copias != null) {
				for (int i = 0; i < copias.length; i++) {
					message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(copias[i]));
				}
			}
			message.setSubject(assunto);

			Transport.send(message);

			System.out.println("Email enviado com sucesso!");
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
}