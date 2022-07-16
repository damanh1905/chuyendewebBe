package com.example.chuyendeweb.controller.admin;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chuyendeweb.entity.CategoryEntity;
import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.model.response.ProductResponse;
import com.example.chuyendeweb.model.response.ResponseObject;
import com.example.chuyendeweb.repository.CategoryRepository;
import com.example.chuyendeweb.repository.ProductRepository;
import com.example.chuyendeweb.service.IProductService;

@RestController
@RequestMapping("api/product/admin")
public class ManageProductAdmin {
	@Autowired
	IProductService iProductService;
	@Autowired
	ModelMapper mapper;
	@Autowired
	CategoryRepository categoryRepo;
	@Autowired
	ProductRepository productRepo;

	@GetMapping("listProducts")
	public ResponseEntity<?> listProductAdmin(@RequestParam(required = true) String searchValue,
			@RequestParam(defaultValue = "0") int pageIndex, @RequestParam(defaultValue = "10") int pageSize) {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("bạn chưa đăng nhập");
		}
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Map<String, Object> result = this.iProductService.showAndSearchProduct(searchValue, pageable);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "successfull", result));
	}

	@GetMapping("/product-detail/{productId}")
	public ResponseEntity<?> showProductDetail(@PathVariable(name = "productId", required = true) Long productId) {
		ProductResponse result = this.iProductService.findById(productId);
		System.out.println(result);
		if (result == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "Not found product", ""));

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "product detail ", result));

	}

	@PostMapping("/editProduct/{productId}")
	public ResponseEntity<?> editProduct(@PathVariable(name = "productId", required = true) Long productId,
			@RequestBody ProductResponse productResponse) {
		ProductEntity product = mapper.map(productResponse, ProductEntity.class);
		CategoryEntity category = categoryRepo.findById(productResponse.getCategoryResponse().getId()).get();
		product.setCategoryEntity(category);
		productRepo.save(product);
		if (product == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "Not found product", ""));
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "update Product successfull", product));

	}

}
