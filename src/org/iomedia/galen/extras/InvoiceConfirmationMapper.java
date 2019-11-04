package org.iomedia.galen.extras;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class InvoiceConfirmationMapper {
	enum MessageType {
		CamInvoice, InvoiceDetails
	}

	public static MessageType getMessageType(String type) {
		if (type.toUpperCase().equals(MessageType.CamInvoice.toString().toUpperCase())) {
			return MessageType.CamInvoice;
		} else if (type.toUpperCase().equals(MessageType.InvoiceDetails.toString().toUpperCase())) {
			return MessageType.InvoiceDetails;
		}
		return null;
	}

	private int accountNumber;
	private int orderNumber;
	private String section;
	private String row;
	private String seat;
	private String ticketDetail;
	private String totalPrice;
	private ArrayList<InvoiceDetail> invoiceDetail = new ArrayList<InvoiceConfirmationMapper.InvoiceDetail>();
	private String itemTotal;
	private String serviceCharges;
	private String deliveryCharges;
	private String paidToday;
	private String balances;
	private String eventName;
	private MessageType messageType;
	private String name;

	public InvoiceConfirmationMapper(MessageType messageType) {
		this.messageType = messageType;
	}

	public InvoiceConfirmationMapper(String source, MessageType messageType) {
		this.messageType = messageType;
		Document html = Jsoup.parse(source);
		if (messageType == MessageType.CamInvoice) {
			parseCAMInvoiceHtml(html);

		} else if (messageType == MessageType.InvoiceDetails) {
			parseInvoiceDetailsHtml(html);

		}
		double amount = 0.0;
		for (InvoiceDetail invoiceDetail2 : invoiceDetail) {
			System.out.println("$" + invoiceDetail2.getDueAmount() + " " + invoiceDetail2.getCardNumber() + " "
					+ invoiceDetail2.getDueDate());
			amount = amount + Double.parseDouble(invoiceDetail2.getDueAmount());
		}
		System.out.println((int) amount);
	}

	private void parseInvoiceDetailsHtml(Document html) {
		Elements p = html.getElementsByTag("p");
		for (Element element : p) {
			String text = element.text();
			if (orderNumber == 0 && text.contains("Order")) {
				orderNumber = Integer.parseInt(text.split(" ")[1].trim());
				System.out.println("Order Number :: " + orderNumber);

			} else if (name == null && text.contains("Hi")) {
				name = text.split("Hi")[1].replace(",", "").trim();
				System.out.println("User Name :: " + name);

			} else if (eventName == null && element.parent().attr("style").equals(
					"font-family: 'Roboto',sans-serif,Arial; font-size:12px; color:#000000;font-weight:300;padding: 10px 0 0;")
					&& element.lastElementSibling().hasText()) {
				eventName = element.text();
				System.out.println("Event Name :: " + eventName);
				ticketDetail = element.lastElementSibling().text();
				System.out.println("TICKET DETAIL :: " + ticketDetail);
				String[] ticketDetails = ticketDetail.split("\\|");
				section = ticketDetails[0].replaceAll("Sec", "Section").trim();
				System.out.println("Section :: " + section);
				row = ticketDetails[1].trim();
				System.out.println("Row :: " + row);
				seat = ticketDetails[2].trim();
				System.out.println("Seat :: " + seat);
				itemTotal = element.parent().lastElementSibling().child(0).text().split("\\$")[1].trim();
				System.out.println("Item Total :: " + itemTotal);

			} else if (element.parent().attr("style").equals(
					"font-family: 'Roboto',sans-serif,Arial; font-size:12px; color:#000000;font-weight:300;padding: 10px 0 0;")
					&& !element.lastElementSibling().hasText()) {
				deliveryCharges = element.parent().lastElementSibling().child(0).text().split("\\$")[1].trim();
				System.out.println("Delivery Charges :: " + deliveryCharges);
			}
		}
		Elements td = html.getElementsByTag("td");
		int paymentCounter = 1;
		for (Element element : td) {
			String text = element.text();
			if (totalPrice == null && text.equals("Subtotal:")) {
				totalPrice = element.lastElementSibling().text().split("\\$")[1];
				System.out.println("Total Price :: " + totalPrice);
			} else if (paidToday == null && text.equals("Less Payments:")) {
				paidToday = element.lastElementSibling().text().split("\\$")[1].split("\\)")[0];
				System.out.println("Paid Today :: " + paidToday);
			} else if (text.startsWith("" + paymentCounter + ".")) {
				String myDate = text.split("\\.")[1].trim();
				invoiceDetail.add(new InvoiceDetail(element.lastElementSibling().text().split("\\$")[1], "NA",
						changeDateFormat(myDate)));
				paymentCounter++;
			} else if (text.contains("[Account #") && text.endsWith("]")) {
				accountNumber = Integer.parseInt(text.split("#")[1].replace("]", "").trim());
				System.out.println("Account Number :: " + accountNumber);
			}
		}
	}

	private void parseCAMInvoiceHtml(Document html) {
		String nbsp = SessionContext.get().nbsp;
		System.out.println(messageType);
		Elements elements = html.getElementsByTag("b");
		for (Element element : elements) {
			if (accountNumber == 0 && element.text().equals("Account Number:")) {
				accountNumber = Integer.parseInt(element.parent().text().split(": ")[1].split("\\\\")[0]);
				System.out.println("Account Number :: " + accountNumber);

			} else if (orderNumber == 0 && element.parent().text().contains("Your order number is")) {
				orderNumber = Integer.parseInt(element.parent().child(1).text());
				System.out.println("Order Number :: " + orderNumber);

			} else if (ticketDetail == null && element.parent().text().contains("Adult")) {
				ticketDetail = element.parent().text().replaceAll("\\\\", "").trim();
				String[] ticketDetails = ticketDetail.split(",");
				section = ticketDetails[0].trim();
				row = ticketDetails[1].trim();
				seat = ticketDetails[2].split("Adult")[0].trim();
				totalPrice = ticketDetails[2].split("\\$")[1].trim();
				System.out.println("Section :: " + section);
				System.out.println("Row :: " + row);
				System.out.println("Seat :: " + seat);
				System.out.println("Total Price :: " + totalPrice);

			} else if (element.text().startsWith("$")) {
				String[] invoiceDetails = element.text().split(" ");
				invoiceDetail.add(new InvoiceDetail(invoiceDetails[0].replaceAll(nbsp, "").trim().split("\\$")[1],
						invoiceDetails[1].replaceAll(nbsp, "").trim(), invoiceDetails[2].replaceAll(nbsp, "").trim()));

			} else if (itemTotal == null && element.text().contains("Item Total")) {
				String total = element.parent().text();
				itemTotal = total.split("\\$")[1].replaceAll("\\\\", "").trim();
				System.out.println("Item Total :: " + itemTotal);

			} else if (serviceCharges == null && element.text().contains("Service Charges")) {
				String total = element.parent().text();
				serviceCharges = total.split("\\$")[1].replaceAll("\\\\", "").trim();
				System.out.println("Service Charges :: " + serviceCharges);

			} else if (deliveryCharges == null && element.text().contains("Delivery Charges")) {
				String total = element.parent().text();
				deliveryCharges = total.split("\\$")[1].replaceAll("\\\\", "").trim();
				System.out.println("Delivery Charges :: " + deliveryCharges);

			} else if (paidToday == null && element.text().contains("Paid Today")) {
				String total = element.parent().text();
				paidToday = total.split("\\$")[1].replaceAll("\\\\", "").trim();
				System.out.println("Paid Today :: " + paidToday);

			} else if (balances == null && element.text().contains("Balances")) {
				String total = element.parent().text();
				balances = total.split("\\$")[1].replaceAll("\\\\", "").trim();
				System.out.println("Balances :: " + balances);

			}
		}
		Elements td = html.getElementsByTag("td");
		for (Element element : td) {
			if (element.attr("style").equals(
					"color:#000000;font-family:Helvetica,Arial,sans-serif;font-size:12px;font-weight:bold;font-style:normal;text-decoration:none;text-align:left;vertical-align:top;max-width:175px;word-wrap:break-word;-ms-word-wrap:break-word;")) {
				eventName = element.text().replaceAll("\\\\", "").trim();
				System.out.println("Event Name :: " + eventName);
				if (eventName != "") {
					break;
				}
			}
		}

	}

	public static void main(String[] args) throws IOException {
		// File file = new File("/Users/syed/Desktop/Invoice.rtf");
		File file = new File("/Users/syed/Desktop/InvoiceDetails.rtf");
		new InvoiceConfirmationMapper(Jsoup.parse(file, null).toString(),
				InvoiceConfirmationMapper.getMessageType("InvoiceDetails"));
	}

	private String changeDateFormat(String myDate) {
		Date date = new Date(myDate);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		return formatter.format(date);
	}

	public class InvoiceDetail {
		private String dueAmount;
		private String cardNumber;
		private String dueDate;

		public InvoiceDetail(String dueAmount, String cardNumber, String dueDate) {
			this.dueAmount = dueAmount;
			this.cardNumber = cardNumber;
			this.dueDate = dueDate;
		}

		public String getDueAmount() {
			return dueAmount;
		}

		public String getCardNumber() {
			return cardNumber;
		}

		public String getDueDate() {
			return dueDate;
		}
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public String getSection() {
		return section;
	}

	public String getRow() {
		return row;
	}

	public String getSeat() {
		return seat;
	}

	public String getTicketDetail() {
		return ticketDetail;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public ArrayList<InvoiceDetail> getInvoiceDetail() {
		return invoiceDetail;
	}

	public String getItemTotal() {
		return itemTotal;
	}

	public String getServiceCharges() {
		return serviceCharges;
	}

	public String getDeliveryCharges() {
		return deliveryCharges;
	}

	public String getPaidToday() {
		return paidToday;
	}

	public String getBalances() {
		return balances;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public void setTicketDetail(String ticketDetail) {
		this.ticketDetail = ticketDetail;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setInvoiceDetail(ArrayList<InvoiceDetail> invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
	}

	public void setItemTotal(String itemTotal) {
		this.itemTotal = itemTotal;
	}

	public void setServiceCharges(String serviceCharges) {
		this.serviceCharges = serviceCharges;
	}

	public void setDeliveryCharges(String deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}

	public void setPaidToday(String paidToday) {
		this.paidToday = paidToday;
	}

	public void setBalances(String balances) {
		this.balances = balances;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
