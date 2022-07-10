package com.example.chuyendeweb.service.imp;

import com.example.chuyendeweb.entity.*;
import com.example.chuyendeweb.model.response.ChangeToOrderRequest;
import com.example.chuyendeweb.model.response.ChangeToOrderResponseByUser;
import com.example.chuyendeweb.model.response.ListOrderAdminResponse;
import com.example.chuyendeweb.repository.*;
import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.service.IOrderService;
import com.example.chuyendeweb.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		List<OrderEntity> orders = repositoryOrder.findByUserEntityId(user.getId());
//		 System.out.println(user);
		List<ChangeToOrderResponseByUser> result = new ArrayList<>();
		for (OrderEntity order : orders) {
			result.add(this.mapper.map(order, ChangeToOrderResponseByUser.class));
		}
		return result;

	}

	@Override
	public void deleteOrderByOrderId(Long orderId) {
		repositoryOrder.deleteOneById(orderId);

	}

	@Override
	public Map<String, Object> showListOdersAdmin(int pageIndex, int pageSize) {
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Map<String, Object> result = new HashMap<>();
		Page<OrderEntity> pageTuts;
		pageTuts = this.repositoryOrder.findAll(pageable);
		System.out.println(pageTuts);
		List<OrderEntity> listOrderEntity = pageTuts.getContent();
		List<ListOrderAdminResponse> listOrderAdmin = new ArrayList<ListOrderAdminResponse>();
		for (OrderEntity orderEntity : listOrderEntity) {
			listOrderAdmin.add(mapper.map(orderEntity, ListOrderAdminResponse.class));
		}
		result.put("listOrderAdmin", listOrderAdmin);
		result.put("curerentPage", pageTuts.getNumber());
		result.put("totalitems", pageTuts.getTotalElements());
		result.put("totalPage", pageTuts.getTotalPages());
		return result;
	}

	@Override
	public List<ChangeToOrderResponseByUser> showListOrderByUserIdAdmin(Long id) {
		UserEntity user = userRepo.findOnedById(id);
		List<OrderEntity> orders = repositoryOrder.findByUserEntityId(user.getId());
//		 System.out.println(user);
		List<ChangeToOrderResponseByUser> result = new ArrayList<>();
		for (OrderEntity order : orders) {
			result.add(this.mapper.map(order, ChangeToOrderResponseByUser.class));
		}
		return result;
	}

}
