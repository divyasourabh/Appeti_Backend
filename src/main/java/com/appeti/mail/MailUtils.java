package com.appeti.mail;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.appeti.database.table.invoice.Invoice;
import com.appeti.database.table.invoice.InvoiceLine;
import com.appeti.database.table.product.Product;
import com.appeti.database.table.product.Ptitle;
import com.appeti.database.table.product.Tag;
import com.appeti.database.table.user.Address;
import com.appeti.database.table.user.LoginInfo;
import com.appeti.database.table.user.Seller;
import com.appeti.database.table.user.User;
import com.appeti.utils.ExceptionUtils;

public class MailUtils {
	
	private static final String PROMO_MAIL_SENDER = "APPETI.IN<no-reply@appeti.in>";
	
	private static final String VERIFY_MAIL_SENDER = "APPETI.IN<no-reply@appeti.in>";
	private static final String VERIFY_MAIL_SUBJECT = "Account Verification Link";
	private static final String NEW_USER_SUBJECT = "Welcome to Appeti !!";
	
	private static final String FORGET_MAIL_SENDER = "APPETI.IN<no-reply@appeti.in>";
	private static final String FORGET_MAIL_SUBJECT = "Account Password Reset";
	
	private static final String INVOICE_MAIL_SENDER = "APPETI.IN<support@appeti.in>";
	private static final String INVOICE_MAIL_SUBJECT = "Order Confirmation";
	private static final String ORDER_MAIL_SUBJECT = "New Order";
	private static final String ORDER_MAIL_RECEPIENTS = "nitesh@appeti.in,dhiraj@appeti.in,narendra@appeti.in,nikhil@appeti.in,divyasourabh@appeti.in,anisha@appeti.in";
	
	public static final String MAIL_BODY_WRAPPER = "<div marginwidth=\"0\" marginheight=\"0\">"
			+ "<div dir=\"ltr\" style=\"background-color:#f5f5f5;margin:0;padding:70px 0 70px 0;width:100%\">"
			+ "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\">"
			+ "<tbody>"
			+ "<tr>"
			+ "<td align=\"center\" valign=\"top\">"
			+ "<div>"
			+ "</div>"
			+ "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"background-color:#fdfdfd;border:1px solid #dcdcdc;border-radius:3px!important\">"
			+ "<tbody>"
			+ "<tr>"
			+ "<td align=\"center\" valign=\"top\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" "
			+ "style=\"background-color:#5fa323;border-radius:3px 3px 0 0!important;color:#ffffff;border-bottom:0;font-weight:bold;line-height:100%;"
			+ "vertical-align:middle;font-family:&quot;Helvetica Neue&quot;,Helvetica,Roboto,Arial,sans-serif\">"
			+ "<tbody>"
			+ "<tr>"
			+ "<td>"
			+ "<h1 style=\"color:#ffffff;display:block;font-family:&quot;Helvetica Neue&quot;,Helvetica,Roboto,Arial,sans-serif;font-size:30px;font-weight:300;"
			+ "line-height:150%;margin:0;padding:36px 48px;text-align:left\">"
			+ "$heading$</h1>"
			+ "</td>"
			+ "</tr>"
			+ "</tbody>"
			+ "</table>"
			+ "</td>"
			+ "</tr>"
			+ "<tr>"
			+ "<td align=\"center\" valign=\"top\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">"
			+ "<tbody>"
			+ "<tr>"
			+ "<td valign=\"top\" style=\"background-color:#fdfdfd\">"
			+ "<table border=\"0\" cellpadding=\"20\" cellspacing=\"0\" width=\"100%\">"
			+ "<tbody>"
			+ "<tr>"
			+ "<td valign=\"top\" style=\"padding:48px\">"
			+ "<div style=\"color:#737373;font-family:&quot;Helvetica Neue&quot;,Helvetica,Roboto,Arial,sans-serif;font-size:14px;"
			+ "line-height:150%;text-align:left\">"
			+ "&nbsp;$content$"
			+ "<p style=\"margin:0 0 16px\">"
			+ "</p>"
			+ "</div>"
			+ "</td>"
			+ "</tr>"
			+ "</tbody>"
			+ "</table>"
			+ "</td></tr></tbody>"
			+ "</table>"
			+ "</td></tr>"
			+ "<tr><td align=\"center\" valign=\"top\"><table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"600\">"
			+ "<tbody>"
			+ "<tr><td valign=\"top\" style=\"padding:0\"><table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"100%\">"
			+ "<tbody><tr>"
			+ "<td colspan=\"2\" valign=\"middle\" style=\"padding:0 48px 48px 48px;border:0;color:#9fc87b;font-family:Arial;font-size:12px;line-height:125%;text-align:center\">"
			+ "<p>Looking forward to see you soon!</p>"
			+ "</td></tr></tbody></table>"
			+ "</td></tr></tbody></table>"
			+ "</td></tr></tbody></table>"
			+ "</td></tr></tbody></table>"
			+ "<div class=\"yj6qo\">"
			+ "</div>"
			+ "<div class=\"adL\"></div></div><div class=\"adL\"></div></div>";
	
	public static final String TABLE_CONTENT = "<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100.0%;border:solid #eeeeee 1.0pt\">"
			+ "<thead><tr><td style=\"border:solid #eeeeee 1.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt\">"
			+ "<p class=\"MsoNormal\"><b>Product<u></u><u></u></b></p></td>"
			+ "<td style=\"border:solid #eeeeee 1.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt\">"
			+ "<p class=\"MsoNormal\"><b>Quantity<u></u><u></u></b></p></td>"
			+ "<td style=\"border:solid #eeeeee 1.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt\">"
			+ "<p class=\"MsoNormal\"><b>Price<u></u><u></u></b></p></td>"
			+ "</tr></thead>"
			+ "<tbody>"
			
			+ "$invoicelines$"
			
			+ "<tr><td colspan=\"2\" style=\"border:solid #eeeeee 1.0pt;border-top:solid #eeeeee 3.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt\">"
			+ "<p class=\"MsoNormal\"><b>Subtotal:<u></u><u></u></b></p></td>"
			+ "<td style=\"border:solid #eeeeee 1.0pt;border-top:solid #eeeeee 3.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt\">"
			+ "<p class=\"MsoNormal\"><span>Rs.$subtotal$</span><u></u><u></u></p></td></tr>"
			+ "<tr><td colspan=\"2\" style=\"border:solid #eeeeee 1.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt\">"
			+ "<p class=\"MsoNormal\"><b>Discount:<u></u><u></u></b></p></td>"
			+ "<td style=\"border:solid #eeeeee 1.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt\">"
			+ "<p class=\"MsoNormal\"><span>Rs.$discount$</span><u></u><u></u></p></td></tr>"
			+ "<tr><td colspan=\"2\" style=\"border:solid #eeeeee 1.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt\">"
			+ "<p class=\"MsoNormal\"><b>Shipping:<u></u><u></u></b></p></td>"
			+ "<td style=\"border:solid #eeeeee 1.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt\">"
			+ "<p class=\"MsoNormal\"><span>Rs.$shipping$</span>&nbsp;<span style=\"font-size:10.0pt\">via Shipping + Handling + Packaging Charges</span> <u></u><u></u></p></td></tr>"
			+ "<tr><td colspan=\"2\" style=\"border:solid #eeeeee 1.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt\">"
			+ "<p class=\"MsoNormal\"><b>Payment Method:<u></u><u></u></b></p></td><td style=\"border:solid #eeeeee 1.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt\">"
			+ "<p class=\"MsoNormal\">Online Payment<u></u><u></u></p></td></tr>"
			+ "<tr><td colspan=\"2\" style=\"border:solid #eeeeee 1.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt\">"
			+ "<p class=\"MsoNormal\"><b>Total:<u></u><u></u></b></p></td>"
			+ "<td style=\"border:solid #eeeeee 1.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt\">"
			+ "<p class=\"MsoNormal\"><span>Rs.$total$</span><u></u><u></u></p></td></tr>"
			+ "</tbody></table>";
	
	public static final String getInvoiceRow(String product, String ptitle, String unit, long quantity, long amount){
		return "<tr><td style=\"border:solid #eeeeee 1.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt;word-wrap:break-word\">"
				+ "<p class=\"MsoNormal\">"+product+"<br>"
				+ "<span style=\"font-size:10.0pt\">Weight: "+unit
				+ "<br>Variety: "+ptitle+"</span> <u></u><u></u></p></td>"
				+ "<td style=\"border:solid #eeeeee 1.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt\">"
				+ "<p class=\"MsoNormal\">"+quantity+"<u></u><u></u></p></td>"
				+ "<td style=\"border:solid #eeeeee 1.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt\">"
				+ "<p class=\"MsoNormal\"><span>Rs."+amount+"</span><u></u><u></u></p></td></tr>";
	}
	
	public static final String getTeamInvoiceRow(String product, String ptitle, String seller, String unit, long quantity, long amount){
		return "<tr><td style=\"border:solid #eeeeee 1.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt;word-wrap:break-word\">"
				+ "<p class=\"MsoNormal\">"+product+"<br>"
				+ "<span style=\"font-size:10.0pt\">Weight: "+unit
				+ "<br>Variety: "+ptitle+" <br>Seller: " + seller + "</span> <u></u><u></u></p></td>"
				+ "<td style=\"border:solid #eeeeee 1.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt\">"
				+ "<p class=\"MsoNormal\">"+quantity+"<u></u><u></u></p></td>"
				+ "<td style=\"border:solid #eeeeee 1.0pt;padding:9.0pt 9.0pt 9.0pt 9.0pt\">"
				+ "<p class=\"MsoNormal\"><span>Rs."+amount+"</span><u></u><u></u></p></td></tr>";
	}
	
	public static String getContentLine(String str){
		return "<p style=\"margin:0 0 16px\">" + str + "</p>";
	}
	
	public static String getNextLine(String str){
		return "<br>" + str;
	}
	
	public static String getColoredContentLine(String str){
		return "<p style=\"margin:0 0 16px\"><font style=\"color:#5fa323;font-weight:normal;\">" + str + "</font></p>";
	}
	
	public static String getBoldContentLine(String str){
		return "<p style=\"margin:0 0 16px\"><b>" + str + "</b></p>";
	}
	
	public static String getLinkLine(String link, String innerHtml){
		return "<p style=\"margin:0 0 16px\">"
				+ "<a href=\""+ StringEscapeUtils.escapeHtml(link) + "\" "
				+ "style=\"color:#5fa323;font-weight:normal;text-decoration:underline\" target=\"_blank\">"
				+ innerHtml + "</a>"
				+ "</p>";
	}
	
	public static String getHeaderLine(String str){
		return "<h2 style=\"margin-right:0cm;margin-bottom:6.0pt;margin-left:0cm;line-height:130%\">"
				+ "<span style=\"font-size:13.5pt;line-height:130%;font-family:&quot;Helvetica&quot;,&quot;sans-serif&quot;;color:#5fa323\">"
				+ "<span style=\"color:#5fa323;font-weight:normal\">"+str+"</span>"
				+ "<u></u><u></u></span></h2>";
	}
	
	public static void sendEmailVerification(LoginInfo login){
		if(login == null)
			return;
		
		String to = login.getEmailAddress();
		String url = "http://www.appeti.in/verifyEmail?hash="+login.getHashCode()+"&userid="+login.getUserId();
		String content = getContentLine("Please click the link below to complete registration.");
		content += getLinkLine(url, "Click here");
		content = MAIL_BODY_WRAPPER.replace("$heading$", "Account Verification").replace("$content$", content);
		MailOutputStream mail = new MailOutputStream(VERIFY_MAIL_SENDER, to, VERIFY_MAIL_SUBJECT, content);
		try{
			mail.send();
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
	}
	
	public static void sendForgetPasswordMail(LoginInfo login, String newPassword){
		if(login == null)
			return;
		String to = login.getEmailAddress();
		String content = getContentLine("Your password has been reset.");
		content += getColoredContentLine("New password : " + newPassword);
		content += getContentLine("Please change the password at your next login.");
		content = MAIL_BODY_WRAPPER.replace("$heading$", "Password Reset").replace("$content$", content);
		MailOutputStream mail = new MailOutputStream(FORGET_MAIL_SENDER, to, FORGET_MAIL_SUBJECT, content);
		try{
			mail.send();
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
	}
	
	public static boolean sendInvoice(Invoice inv, String email, String phn, Address shippingAddr){
		sendInvoiceToUser(inv, email, phn, shippingAddr);
		sendInvoiceToTeam(inv, email, phn, shippingAddr);
		return true;
	}
	
	public static boolean sendInvoiceToTeam(Invoice inv, String email, String phn, Address shippingAddr){
		if(inv == null)
			return false;
		try{
			String content = "";
			content += getBoldContentLine("Hi Team,");
			content += getContentLine("There is a new order!");
			content += getContentLine("Please find below, the summary of the order.");
			content += getHeaderLine("ORDER#"+inv.getOrderId());

			String tableRow = "";
			for(InvoiceLine line : InvoiceLine.getInvoiceLines(inv.getInvoiceId())){
				Tag tag = Tag.getTagById(line.getTagId());
				Product prod = Product.getProductById(tag.getProductId());
				Ptitle ptitle = Ptitle.getPtitleById(tag.getPtitleId());
				tableRow += getTeamInvoiceRow(prod.getName(),ptitle.getName() ,Seller.getById(tag.getSellerId()).getBrandName(),tag.getUnitString(),line.getQuantity(), line.getTotalPrice());
			}
			content += TABLE_CONTENT.replace("$invoicelines$", tableRow).replace("$subtotal$", String.valueOf(inv.getTotalAmount()))
					.replace("$discount$", String.valueOf(inv.getDiscount()))
					.replace("$shipping$", String.valueOf(inv.getDeliveryCharge()))
					.replace("$total$", String.valueOf(inv.getInvoiceAmount()));
			
			content += getHeaderLine("Customer Details");
			content += getContentLine("<b>Email:&nbsp;</b>" + email + getNextLine("<b>Tel:&nbsp;</b>"+phn));
			
			if(shippingAddr != null){
				String shipContent = "";
				shipContent += getHeaderLine("Shipping Address");
				shipContent += (shippingAddr.getFirstName()+"&nbsp;"+shippingAddr.getLastName());
				if(StringUtils.isNotBlank(shippingAddr.getName1()))
					shipContent += getNextLine(shippingAddr.getName1());
				if(StringUtils.isNotBlank(shippingAddr.getAddr1()))
					shipContent += getNextLine(shippingAddr.getAddr1());
				if(StringUtils.isNotBlank(shippingAddr.getAddr2()))
					shipContent += getNextLine(shippingAddr.getAddr2());
				shipContent += getNextLine(shippingAddr.getCity()+"&nbsp;-&nbsp;"+shippingAddr.getZipCode());
				shipContent += getNextLine(shippingAddr.getState());
				shipContent += getNextLine(shippingAddr.getCountry());
				content += getContentLine(shipContent);
			}
			content = MAIL_BODY_WRAPPER.replace("$heading$", "Order Summary").replace("$content$", content);
			MailOutputStream mail = new MailOutputStream(INVOICE_MAIL_SENDER, ORDER_MAIL_RECEPIENTS, ORDER_MAIL_SUBJECT, content);

			mail.send();
			return true;
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return false;
	}
	
	public static boolean sendInvoiceToUser(Invoice inv, String email, String phn, Address shippingAddr){
		if(inv == null)
			return false;
		try{
			String content = "";
			content += getBoldContentLine("Hi Customer,");
			content += getContentLine("Thank you for your order!");
			content += getContentLine("Please find below, the summary of your order.");
			content += getHeaderLine("ORDER#"+inv.getOrderId());

			String tableRow = "";
			for(InvoiceLine line : InvoiceLine.getInvoiceLines(inv.getInvoiceId())){
				Tag tag = Tag.getTagById(line.getTagId());
				Product prod = Product.getProductById(tag.getProductId());
				Ptitle ptitle = Ptitle.getPtitleById(tag.getPtitleId());
				tableRow += getInvoiceRow(prod.getName(),ptitle.getName(),tag.getUnitString(),line.getQuantity(), line.getTotalPrice());
			}
			content += TABLE_CONTENT.replace("$invoicelines$", tableRow).replace("$subtotal$", String.valueOf(inv.getTotalAmount()))
					.replace("$discount$", String.valueOf(inv.getDiscount()))
					.replace("$shipping$", String.valueOf(inv.getDeliveryCharge()))
					.replace("$total$", String.valueOf(inv.getInvoiceAmount()));
			
			content += getHeaderLine("Customer Details");
			content += getContentLine("<b>Email:&nbsp;</b>" + email + getNextLine("<b>Tel:&nbsp;</b>"+phn));
			
			if(shippingAddr != null){
				String shipContent = "";
				shipContent += getHeaderLine("Shipping Address");
				shipContent += (shippingAddr.getFirstName()+"&nbsp;"+shippingAddr.getLastName());
				if(StringUtils.isNotBlank(shippingAddr.getName1()))
					shipContent += getNextLine(shippingAddr.getName1());
				if(StringUtils.isNotBlank(shippingAddr.getAddr1()))
					shipContent += getNextLine(shippingAddr.getAddr1());
				if(StringUtils.isNotBlank(shippingAddr.getAddr2()))
					shipContent += getNextLine(shippingAddr.getAddr2());
				shipContent += getNextLine(shippingAddr.getCity()+"&nbsp;-&nbsp;"+shippingAddr.getZipCode());
				shipContent += getNextLine(shippingAddr.getState());
				shipContent += getNextLine(shippingAddr.getCountry());
				content += getContentLine(shipContent);
			}
			content = MAIL_BODY_WRAPPER.replace("$heading$", "Order Summary").replace("$content$", content);
			MailOutputStream mail = new MailOutputStream(INVOICE_MAIL_SENDER, email, INVOICE_MAIL_SUBJECT, content);

			mail.send();
			return true;
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return false;
	}
	
	public static boolean sendWelcomeUserMail(User user, String couponCode){
		if(user == null)
			return false;
		try{
			String content = "";
			String name = StringUtils.isNotBlank(user.getFirstName()) ? " " + user.getFirstName() : "";
			content += getBoldContentLine("Hi"+name+",");
			content += getContentLine("Welcome to Appet-i !!");
			content += getContentLine("Taste popular and authentic regional delicacies brought to your door step right from the origin by Appet-i team!");
			content += getContentLine("To avail your special first time discount use the coupon code below (valid for 1 month)");
			content += getColoredContentLine(couponCode);

			content = MAIL_BODY_WRAPPER.replace("$heading$", "Welcome" + name).replace("$content$", content);
			MailOutputStream mail = new MailOutputStream(VERIFY_MAIL_SENDER, user.getEmailAddress(), NEW_USER_SUBJECT, content);
			mail.send();
			return true;
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return false;
	}
	public static boolean sendPromotionalMail(final String subject,final String heading,final String message, final String emailIds){
		Runnable postProcess = new Runnable() {
			@Override
			public void run() {
				try{
					String[] ids = emailIds.split(",");
					for(String id: ids){
						MailOutputStream mail = new MailOutputStream(PROMO_MAIL_SENDER, id, subject, MAIL_BODY_WRAPPER.replace("$heading$", heading).replace("$content$", message).replace("padding:48px", "").replace("cellpadding=\"20\"", "cellpadding=\"0\""));
						mail.send();
					}
				}catch(Exception e){
					ExceptionUtils.logException(e);
				}
			}
		};
		Thread thread = new Thread(postProcess);
		thread.start();
		
		return true;
	}
}
