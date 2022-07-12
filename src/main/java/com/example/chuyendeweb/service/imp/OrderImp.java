package com.example.chuyendeweb.service.imp;

import com.example.chuyendeweb.entity.*;
import com.example.chuyendeweb.model.response.*;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public AdminChartCiResponse getChartDayMonth(int month, int year) throws ParseException {
		List<OrderEntity> pageTuts = null;
//		SortedMap<String, Double> map = new TreeMap<>(new Comparator<String>() {
//			@Override
//			public int compare(String s1, String s2) {
//				return s1.compareToIgnoreCase(s2);
//			}
//		});
//		TreeMap<String, Double> map = new TreeMap<>(new AccordingMarks());

//		LinkedHashMap<String, Double> sortmap = new LinkedHashMap<>();
//
//		map.entrySet()
//				.stream()
//				.sorted(Map.Entry.comparingByKey())
//				.forEachOrdered(x -> map.put(x.getKey(), x.getValue()));
//		map.put("1",0.0);
//		map.put("2",0.0);
//		map.put("3",0.0);
//		map.put("4",0.0);
//		map.put("5",0.0);
//		map.put("6",0.0);
//		map.put("7",0.0);
//		map.put("8",0.0);
//		map.put("9",0.0);
//		map.put("10",0.0);
//		map.put("11",0.0);
//		map.put("12",0.0);
//		map.put("13",0.0);
//		map.put("14",0.0);
//		map.put("15",0.0);
//		map.put("16",0.0);
//		map.put("17",0.0);
//		map.put("18",0.0);
//		map.put("19",0.0);
//		map.put("20",0.0);
//		map.put("21",0.0);
//		map.put("22",0.0);
//		map.put("23",0.0);
//		map.put("24",0.0);
//		map.put("25",0.0);
//		map.put("26",0.0);
//		map.put("27",0.0);
//		map.put("28",0.0);
//		map.put("29",0.0);
//		map.put("30",0.0);









		List<String> title = new ArrayList<>();
		List<Double> data = new ArrayList<>();
		title.add("1");
		title.add("2");
		title.add("3");
		title.add("4");
		title.add("5");
		title.add("6");
		title.add("7");
		title.add("8");
		title.add("9");
		title.add("10");
		title.add("11");
		title.add("12");
		title.add("13");
		title.add("14");
		title.add("15");
		title.add("16");
		title.add("17");
		title.add("18");
		title.add("19");
		title.add("20");
		title.add("21");
		title.add("22");
		title.add("23");
		title.add("24");
		title.add("25");
		title.add("26");
		title.add("27");
		title.add("28");
		title.add("29");
		title.add("30");


		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);
		data.add(0.0);

//		List<OrderEntity> orderEntities = repositoryOrder.findAllByDateCreated(month,year);
//		for (OrderEntity orderEntity:orderEntities) {
//			Date date = orderEntity.getDateCreated();
//			int day = date.getDay();
//
//			double totalOderDetail = 0;
//			if(map.containsKey(String.valueOf(day))){
//				totalOderDetail += orderEntity.getTotalPriceOrder()+map.get(String.valueOf(day));
//
//				System.out.println( totalOderDetail);
//			}else {
//				totalOderDetail= 0;
//			}
//			map.put(String.valueOf(day),totalOderDetail);
//		}
		List<OrderEntity> orderEntities = repositoryOrder.findAllByDateCreated(month,year);
		for (OrderEntity orderEntity:orderEntities) {
			Date date = orderEntity.getDateCreated();
			int day = getDayOfMonth(date);
			System.out.println(orderEntity.getDateCreated());
			double totalOderDetail = 0;
			for (int i = 0 ;i<title.size();i++) {
				if (title.get(i).equals(String.valueOf(day))) {
					System.out.println(String.valueOf(day));
					totalOderDetail = orderEntity.getTotalPriceOrder()+data.get(i) ;
					data.set(i,totalOderDetail);
					System.out.println(data.toString());
				}
//				else {
//					totalOderDetail = 0;
////					data.add(i,totalOderDetail);
//				}
			}

		}

//			pageTuts = repositoryOrder.findAllByDateCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-" + month + "-" + day));
//			pageTuts = repositoryOrder.findAllByDateCreated(month,year);
//			System.out.println(pageTuts);


//		List<AdminChartResponse> listOrderAdmin = new ArrayList<AdminChartResponse>();
//		for (OrderEntity orderEntity : pageTuts) {
//			listOrderAdmin.add(mapper.map(orderEntity, AdminChartResponse.class));
//		}
		System.out.println(data.toString());
		AdminChartCiResponse result = new AdminChartCiResponse(title,data);
		return result;
	}
	public static int getDayOfMonth(Date aDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(aDate);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	@Override
	public List<AdminChartResponse> getChartDay(int day,int month) throws ParseException {
		List<OrderEntity> pageTuts = repositoryOrder.findAllByDateCreated(new SimpleDateFormat("yyyy-MM-dd").parse("2022-"+month + "-" + day));
		List<AdminChartResponse> listOrderAdmin = new ArrayList<AdminChartResponse>();
		for (OrderEntity orderEntity : pageTuts) {
			listOrderAdmin.add(mapper.map(orderEntity, AdminChartResponse.class));
		}

		return listOrderAdmin;
	}
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

	@Override
	public Map<String, Double> getChartDayMonthCi(int month, int year) {
		Map<String,Double> map = new HashMap<>();
		List<AdminChartCiResponse> result = new ArrayList<>();
//		List<String> listString = new ArrayList<>();
//		listString.add("accessories");
//		listString.add("outerwear");
//		listString.add("footwear");
//		listString.add("tops");
//		listString.add("bottoms");
		map.put("accessories",0.0);
		map.put("outerwear",0.0);
		map.put("footwear",0.0);
		map.put("tops",0.0);
		map.put("bottoms",0.0);
		List<OrderDetailEntity> orderDetailEntities = orderDetailRepo.findAllByDateCreated(month,year);
		List<OrderEntity> orderEntities = repositoryOrder.findAllByDateCreated(month,year);

		double totalOrder = 0;

		for (OrderEntity orderEntity:orderEntities) {
			totalOrder += orderEntity.getTotalPriceOrder();
		}
		for (OrderDetailEntity orderDetailEntity:orderDetailEntities) {
			long idCategory = orderDetailEntity.getProductEntity().getCategoryEntity().getId();

			String nameCatogery = this.categoryRepository.findById(idCategory).get().getNameCategory();
//			for (Map.Entry<String, Double> entry : map.entrySet()) {
				double totalOderDetail = 0;
				if(map.containsKey(nameCatogery)){
					totalOderDetail += orderDetailEntity.getTotalOrderDetailPrice()*orderDetailEntity.getQuantity()+map.get(nameCatogery);

					System.out.println( totalOderDetail);
				}else {
					totalOderDetail= 0;
				}
				map.put(nameCatogery,totalOderDetail);
//			}

//				if(nameCatogery.equals(title)){
//					totalOderDetail += orderDetailEntity.getTotalOrderDetailPrice()*orderDetailEntity.getQuantity() ;
//				}else{
//					totalOderDetail = 0;
//				}


//
//			for (Map.Entry<String, Double> entry : map.entrySet()) {
////				System.out.println(entry.getKey() + " " + entry.getValue());
//				result.add(new AdminChartCiResponse(entry.getKey(),entry.getValue()));
//			}
		}
//		for (AdminChartCiResponse chartCiResponse: result) {
//
//		}

		return map;


	}

}
