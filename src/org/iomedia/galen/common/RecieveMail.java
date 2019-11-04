package org.iomedia.galen.common;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.SearchTerm;

import org.iomedia.common.BaseUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.SkipException;

import com.sun.mail.imap.IMAPMessage;

public class RecieveMail {
	BaseUtil base;

	public RecieveMail(BaseUtil base) {
		this.base = base;
	}

	public Store connect(String emailAddress, String password) throws MessagingException {

		Properties props = new Properties();
		props.put("mail.store.protocol", "imaps");
		props.put("mail.imaps.ssl.trust", "*");
		Session session = Session.getInstance(props, null);
		Store store = session.getStore();
		store.connect("outlook.office365.com", 993, emailAddress, password);
		return store;
	}

	public void close(Store store) throws MessagingException {
		if (store != null)
			store.close();
	}

	public Folder getFolder(Store store, String folderName) throws Exception {
		Folder folder = store.getFolder(folderName);
		folder.open(Folder.READ_WRITE);
		System.out.println("Folder Read Write Permission Given");
		return folder;
	}

	public Message[] getMessages(Folder folder) throws Exception {
		return folder.getMessages();
	}

	public Message[] waitForMessages(Folder folder, int actualCount) throws Exception {
		Folder request = null;
		try {
			request = folder;
			long time = System.currentTimeMillis();
			long end = time + 30000;
			System.out.println(request.getMessages().length);
			while (System.currentTimeMillis() < end) {
				if (request.getMessages().length <= actualCount) {
					Thread.sleep(200);
				} else {
					break;
				}
			}
			if (request.getMessages().length <= actualCount)
				throw new SkipException("No new email found");

			base.Dictionary.put("MAIL", "found");
			return request.getMessages();
		} catch (Exception mex) {
			throw mex;
		}
	}

	public void copyMessages(Folder from, Folder to, Message[] messages) throws MessagingException {
		from.copyMessages(messages, to);
	}

	public void deleteMessage(Message msg) throws MessagingException {
		msg.setFlag(Flags.Flag.DELETED, true);
	}

	public String SearchMail(Message msg) throws Exception {
		String subject = msg.getSubject();
		return subject;
	}

	public String SearchLinkContent(Folder folder, int actualCount) throws Exception {
		return SearchLinkContent(folder, actualCount, "RESETPASSWORD");
	}

	public String SearchLinkContent(Folder folder, int actualCount, String type) throws Exception {
		Message[] msg = waitForMessages(folder, actualCount);
		// Flags seen = new Flags(Flags.Flag.SEEN);
		// FlagTerm unseenFlagTerm = new FlagTerm(seen, true);
		// msg = folder.search(unseenFlagTerm);
		SearchTerm term = null;
		if (type.toUpperCase().equals("RESETPASSWORD")) {
			term = new SearchTerm() {
				private static final long serialVersionUID = 1L;

				public boolean match(Message message) {
					try {
						if (message.getSubject().contains("Password Reset Link") && message.getSubject().toLowerCase()
								.contains(base.Environment.get("x-client").trim().toLowerCase())) {
							return true;
						}
					} catch (MessagingException ex) {
						ex.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return false;
				}
			};
		} else if (type.toUpperCase().equals("ACCEPTTRANSFER")) {
			term = new SearchTerm() {
				private static final long serialVersionUID = 1L;

				public boolean match(Message message) {
					try {
						if (message.getSubject().contains("Lucky you! You have an offer from")) {
							return true;
						}
					} catch (MessagingException ex) {
						ex.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return false;
				}
			};
		} else if (type.toUpperCase().equals("FETCHINVOICE")) {
			term = new SearchTerm() {
				private static final long serialVersionUID = 1L;

				public boolean match(Message message) {
					try {
						if (message.getSubject().contains("Your Order Confirmation")) {
							return true;
						}
					} catch (MessagingException ex) {
						ex.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return false;
				}
			};
		} else if (type.toUpperCase().equals("EVENTS")) {
			term = new SearchTerm() {
				private static final long serialVersionUID = 1L;

				public boolean match(Message message) {
					try {
						System.out.println(message.getSubject());
						if (message.getSubject().contains("Your Invoice Details")) {
							return true;
						}
					} catch (MessagingException ex) {
						ex.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return false;
				}
			};
		}

		Message[] messages = null;
		if (msg.length >= 10) {
			messages = Arrays.copyOfRange(msg, msg.length - 10, msg.length);
		} else {
			messages = msg;
		}

		Message[] foundMessages = folder.search(term, messages);
		if (foundMessages.length == 0)
			throw new SkipException("No email found");
		String link = null;
		Message message = null;
		System.out.println(message);
		for (int i = foundMessages.length - 1; i >= 0; i--) {
			message = foundMessages[i];
			System.out.println(message.getContentType());
			System.out.println(message.getContent().getClass());
			Object content = message.getContent();
			if (type.toUpperCase().equals("RESETPASSWORD")) {
				link = getResetPasswordLink(content, message);
			} else if (type.toUpperCase().equals("ACCEPTTRANSFER")) {
				link = getAcceptTransferLink(content, message);
			} else if (type.toUpperCase().equals("FETCHINVOICE") || type.toUpperCase().equals("EVENTS")) {
				link = getFetchInvoiceHtml(content, message);
			}
			if (link != null && !link.trim().equalsIgnoreCase("")) {
				break;
			}
		}

		if (link == null || link.trim().equalsIgnoreCase("")) {
			if (type.toUpperCase().equals("RESETPASSWORD")) {
				throw new SkipException("Forgot password link not found");
			} else if (type.toUpperCase().equals("ACCEPTTRANSFER")) {
				throw new SkipException("Accept Transfer link not found");
			} else if (type.toUpperCase().equals("FETCHINVOICE")) {
				throw new SkipException("No Order Confirmation found");
			} else if (type.toUpperCase().equals("EVENTS")) {
				throw new SkipException("No Invoice Details found");
			}
		}

		if (message != null)
			deleteMessage(message);
		folder.close(true);
		return link;
	}

	public String SearchLinkContentForSend(Folder folder, int actualCount, String linkToFetch) throws Exception {
		Message[] msg = waitForMessages(folder, actualCount);
		// Flags seen = new Flags(Flags.Flag.SEEN);
		// FlagTerm unseenFlagTerm = new FlagTerm(seen, true);
		// msg = folder.search(unseenFlagTerm);
		@SuppressWarnings("serial")
		SearchTerm term = new SearchTerm() {
			public boolean match(Message message) {
				try {
					if (!base.Dictionary.get("CustomerName").trim().equals("") && message.getSubject()
							.contains(base.Dictionary.get("CustomerName") + " " + "sent you tickets")) {
						return true;
					}
				} catch (MessagingException ex) {
					ex.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
		};

		Message[] foundMessages = folder.search(term, msg);
		if (foundMessages.length == 0)
			throw new SkipException("No email found");
		String htmlcontent = "";
		String link = "";
		Message message = null;
		for (int i = foundMessages.length - 1; i >= 0; i--) {
			htmlcontent = "";
			message = foundMessages[i];
			htmlcontent = getTextFromMessage(message);
			if (!htmlcontent.trim().equalsIgnoreCase("")) {
				link = getTicketLink(htmlcontent, linkToFetch);
				break;
			}
		}
		if (link.trim().equalsIgnoreCase(""))
			throw new SkipException("Ticket not found");
		folder.close(true);
		base.SoftAssert.assertTrue(htmlcontent.contains(base.Dictionary.get("OptionalMessage")));
		return link;
	}

	private String getTextFromMessage(Message message) throws MessagingException, IOException {
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}

	private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break; // without break same text appears twice in my tests
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}
	
	private String getHTMLFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + "\n" + html;
				break;
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getHTMLFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

	public String getResetPasswordlink(String htmlcontent) throws Exception {
		System.out.println(htmlcontent);
		// Pattern p = Pattern.compile("href=\"(.*?)\"", Pattern.DOTALL);
		Pattern p = Pattern.compile("http://(.*?)]", Pattern.DOTALL);
		Matcher m = p.matcher(htmlcontent);

		String link = "";
		while (m.find()) {
			link = m.group(1);
		}

		return link;
	}

	public String getAcceptTransferLink(String htmlcontent) throws Exception {
		String link = null;
		Document source = Jsoup.parse(htmlcontent);
		Elements elements = source.getElementsByTag("a");
		for (Element element : elements) {
			System.out.println(element);
			if (element.child(0).text().equals("Accept Transfer")) {
				link = element.attr("href");
			}
		}
		return link;
	}

	public String getTicketLink(String htmlcontent, String linkType) throws Exception {
		Pattern p = Pattern.compile("http:(.*?)]", Pattern.DOTALL);
		Matcher m = p.matcher(htmlcontent);
		int i = 0;
		String[] links = new String[5];
		while (m.find()) {
			links[i] = m.group(0);
			i++;
		}

		if (linkType.equalsIgnoreCase("Accept")) {
			return links[0].replaceAll("]", "");
		} else if (linkType.equalsIgnoreCase("Decline"))

			return links[1].replaceAll("]", "");

		else
			throw new SkipException("Pass correct link type to fetch in feature");
	}

	private String getResetPasswordLink(Object content, Message message) throws Exception {
		String link = null;
		if (content instanceof MimeMultipart) {
			Multipart mp = (Multipart) content;
			String htmlcontent = null;
			for (int j = 0; j < mp.getCount(); j++) {
				BodyPart bp = mp.getBodyPart(j);
				// String htmlcontent1 = getTextFromMimeMultipart((MimeMultipart)
				// message.getContent());
				// System.out.println(htmlcontent1);

				if (Pattern.compile(Pattern.quote("text/html"), Pattern.CASE_INSENSITIVE).matcher(bp.getContentType())
						.find()) {
					// found html part
					// htmlcontent = (String) bp.getContent();
					htmlcontent = getTextFromMimeMultipart((MimeMultipart) message.getContent());
					break;

				} else {

					htmlcontent = getTextFromMimeMultipart((MimeMultipart) message.getContent());
				}
			}
			System.out.println(htmlcontent);
			if (!htmlcontent.trim().equalsIgnoreCase("")) {
				String link1 = getResetPasswordlink(htmlcontent);
				String link2 = "http://";
				link = link2 + link1;
				// link = getResetPasswordlink(htmlcontent);
				// System.out.println(link);
			}
		}
		return link;
	}

	private String getAcceptTransferLink(Object content, Message message) throws Exception {
		String link = null;
		if (content instanceof String) {
			System.out.println(message.toString());
			IMAPMessage imapMessage = (IMAPMessage) message;
			String htmlcontent = imapMessage.getContent().toString();
			if (!htmlcontent.trim().equalsIgnoreCase("")) {
				link = getAcceptTransferLink(htmlcontent);
			}
		}
		return link;
	}

	private String getFetchInvoiceHtml(Object content, Message message) throws Exception {
		String htmlcontent = null;
		
		if (content instanceof MimeMultipart) {
			Multipart mp = (Multipart) content;
			for (int j = 0; j < mp.getCount(); j++) {
				BodyPart bp = mp.getBodyPart(j);

				if (Pattern.compile(Pattern.quote("text/html"), Pattern.CASE_INSENSITIVE).matcher(bp.getContentType())
						.find()) {
					htmlcontent = getHTMLFromMimeMultipart((MimeMultipart) message.getContent());
					break;

				} else {

					htmlcontent = getHTMLFromMimeMultipart((MimeMultipart) message.getContent());
				}
			}
		} else if (content instanceof String) {
			System.out.println(message.toString());
			IMAPMessage imapMessage = (IMAPMessage) message;
			htmlcontent = imapMessage.getContent().toString();
			
		}
		return htmlcontent;
	}

}