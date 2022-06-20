package com.example.chuyendeweb.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chuyendeweb.entity.CartEntity;
import com.example.chuyendeweb.entity.CartItemEntity;
import com.example.chuyendeweb.entity.OrderDetailEntity;
import com.example.chuyendeweb.entity.OrderEntity;
import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.model.response.ChangeToOrderRequest;
import com.example.chuyendeweb.repository.CartItemRepository;
import com.example.chuyendeweb.repository.CartRepository;
import com.example.chuyendeweb.repository.OrderDetailRepository;
import com.example.chuyendeweb.repository.OrderRepository;
import com.example.chuyendeweb.repository.ProductRepository;
import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.service.IOrderService;
import com.example.chuyendeweb.service.IUserService;

@Service
public class OrderImp implements IOrderService {
	@Autowired
	OrderRepository repositoryOrder;
	@Autowired
	IUserService iUserService;

	@Autowired
	ProductRepository productRepo;

	@Autowired
	OrderDetailRepository orderDetailRepo;
	@Autowired
	CartRepository cartRepo;
	@Autowired
	CartItemRepository cartItemRepo;

	@Override
	public void saveToOrder(CustomUserDetails userDetails, ChangeToOrderRequest changeToOrderRequest) {
		OrderEntity order = new OrderEntity();
		order.setTotalPriceOrder(changeToOrderRequest.getTotal());
		order.setShipFee(changeToOrderRequest.getFeeTotal());
		UserEntity userEntity = this.iUserService.findById(userDetails.getId());
		order.setUserEntity(userEntity);
		for (int i = 0; i < changeToOrderRequest.getIdProducts().length; i++) {
			ProductEntity product = productRepo.findById((long) changeToOrderRequest.getIdProducts()[i]).get();
			if (product != null) {
				OrderDetailEntity orderDetail = new OrderDetailEntity();
				orderDetail.setProductEntity(product);

//				xu li quantity & totalOrderDetailPrice
				CartItemEntity cartItemEntity = handleQuantityAndTotalPriceProduct(userEntity, product);
				orderDetail.setTotalOrderDetailPrice(cartItemEntity.getTotalPrice());
				orderDetail.setQuantity(cartItemEntity.getQuantity());

				orderDetailRepo.save(orderDetail);

			

			}

		}
//		xoa từng cartItem theo Id của cartId
		repositoryOrder.save(order);
		CartEntity cart = cartRepo.findByUserEntity(userEntity);
		System.out.println("iddddd" + cart.getId());
		cartItemRepo.deleteAllByCartEntity(cart);

	}

	private CartItemEntity handleQuantityAndTotalPriceProduct(UserEntity userEntity, ProductEntity product) {
		CartEntity cart = cartRepo.findByUserEntity(userEntity);
		CartItemEntity cartItemEntity = cartItemRepo.findByCartEntityAndProductEntities(cart, product);

		return cartItemEntity;

	}

}
