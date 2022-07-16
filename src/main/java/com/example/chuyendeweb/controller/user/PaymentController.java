package com.example.chuyendeweb.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chuyendeweb.config.PaypalPaymentIntent;
import com.example.chuyendeweb.config.PaypalPaymentMethod;import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.model.response.ChangeToOrderRequest;
import com.example.chuyendeweb.model.response.ResponseObject;
import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.service.IOrderService;
import com.example.chuyendeweb.service.PaypalService;
import com.example.chuyendeweb.util.Utils;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("api/payment")
public class PaymentController {
	@Autowired
	private PaypalService paypalService;
	@Autowired
	IOrderService orderService;
	public static final String URL_PAYPAL_SUCCESS = "pay/success";
	public static final String URL_PAYPAL_CANCEL = "pay/cancel";
	private Logger log = LoggerFactory.getLogger(getClass());

	public ChangeToOrderRequest changeToOrderRequestNe;
	public CustomUserDetails userDetails;
	@PostMapping("/pay")
	public ResponseEntity<ResponseObject> pay(HttpServletRequest request,@RequestParam("price") double price,@RequestBody ChangeToOrderRequest changeToOrderRequest ){
		String cancelUrl = Utils.getBaseURL(request) + "/" +"api/payment/"+ URL_PAYPAL_CANCEL;
		String successUrl = Utils.getBaseURL(request) + "/" +"api/payment/"+ URL_PAYPAL_SUCCESS;
		changeToOrderRequestNe=changeToOrderRequest;
//System.out.println(changeToOrderRequestNe);
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {
			Payment payment = paypalService.createPayment(
					price,
					"USD",
					PaypalPaymentMethod.paypal,
					PaypalPaymentIntent.sale,
					"payment description",
					cancelUrl,
					successUrl);
			for(Links links : payment.getLinks()){
				if(links.getRel().equals("approval_url")){

					return ResponseEntity.status(HttpStatus.OK)
							.body(new ResponseObject(HttpStatus.OK.value(), "chuyển hướng tới trang paypal", links.getHref()));
				}

			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage()+"vl");
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "Lỗi mất tiêu rồi", new Integer(1)));
	}
	@GetMapping(URL_PAYPAL_CANCEL)
	public String cancelPay(){
		return "";
	}
	@GetMapping(URL_PAYPAL_SUCCESS)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if(payment.getState().equals("approved")){
				orderService.saveToOrder(userDetails,changeToOrderRequestNe);


			return "Thành công rồi hãy ấn nút checkout để thanh toán";
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return "that bai roi";

}
}