package com.example.chuyendeweb.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chuyendeweb.entity.CartEntity;
import com.example.chuyendeweb.entity.CartItemEntity;
import com.example.chuyendeweb.entity.OrderDetailEntity;
import com.example.chuyendeweb.entity.OrderEntity;
import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.model.response.ChangeToOrderRequest;
import com.example.chuyendeweb.model.response.ChangeToOrderResponseByUser;
import com.example.chuyendeweb.repository.CartItemRepository;
import com.example.chuyendeweb.repository.CartRepository;
import com.example.chuyendeweb.repository.OrderDetailRepository;
import com.example.chuyendeweb.repository.OrderRepository;
import com.example.chuyendeweb.repository.ProductRepository;
import com.example.chuyendeweb.repository.UserRepository;
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
	@Autowired
	ModelMapper mapper;
	@Autowired
	UserRepository userRepo;

	@Override
	public void saveToOrder(CustomUserDetails userDetails, ChangeToOrderRequest changeToOrderRequest) {
		OrderEntity order = new OrderEntity();
		order.setTotalPriceOrder(changeToOrderRequest.getTotal());
		order.setShipFee(changeToOrderRequest.getFeeTotal());
//		
		order.setAddress(changeToOrderRequest.getAddress());
		order.setPhoneNumber(changeToOrderRequest.getPhoneNumber());
//		
		UserEntity userEntity = this.iUserService.findById(userDetails.getId());
		order.setUserEntity(userEntity);
		order.setDateCreated(new Date());
		repositoryOrder.save(order);
		for (int i = 0; i < changeToOrderRequest.getIdProducts().length; i++) {
			ProductEntity product = productRepo.findById((long) changeToOrderRequest.getIdProducts()[i]).get();
			if (product != null) {
				OrderDetailEntity orderDetail = new OrderDetailEntity();
				orderDetail.setProductEntity(product);
				orderDetail.setOrderEntity(order);
				// xu li quantity & totalOrderDetailPrice
				CartItemEntity cartItemEntity = handleQuantityAndTotalPriceProduct(userEntity, product);
				orderDetail.setTotalOrderDetailPrice(cartItemEntity.getTotalPrice());
				orderDetail.setQuantity(cartItemEntity.getQuantity());
				orderDetail.setDateCreated(new Date());

				orderDetailRepo.save(orderDetail);

			}

		}
		// xoa từng cartItem theo Id của cartId
		repositoryOrder.save(order);

		CartEntity cart = cartRepo.findByUserEntity(userEntity);
		// System.out.println("iddddd" + cart.getId());
		cartItemRepo.deleteAllByCartEntity(cart);

	}

	private CartItemEntity handleQuantityAndTotalPriceProduct(UserEntity userEntity, ProductEntity product) {
		CartEntity cart = cartRepo.findByUserEntity(userEntity);
		CartItemEntity cartItemEntity = cartItemRepo.findByCartEntityAndProductEntities(cart, product);

		return cartItemEntity;

	}

	@Override
	public List<ChangeToOrderResponseByUser> showListOrderByUserId(CustomUserDetails userDetails) {
		 UserEntity user = userRepo.findOnedById(userDetails.getId());
		 List<OrderEntity> orders=repositoryOrder.findByUserEntityId(user.getId());
//		 System.out.println(user);
		 List<ChangeToOrderResponseByUser> result = new ArrayList<>();
		 for (OrderEntity order : orders) {
			 result.add(this.mapper.map(order,ChangeToOrderResponseByUser.class));
		}
		 return result;
		
	}

}
