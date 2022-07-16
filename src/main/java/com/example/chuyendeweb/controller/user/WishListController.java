package com.example.chuyendeweb.controller.user;

import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.entity.WishListEntity;
import com.example.chuyendeweb.entity.WishListItemEntity;
import com.example.chuyendeweb.exception.NotFoundException;
import com.example.chuyendeweb.model.response.ResponseObject;
import com.example.chuyendeweb.model.response.WishListItemResponse;
import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.service.IProductService;
import com.example.chuyendeweb.service.IUserService;
import com.example.chuyendeweb.service.IWishListService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/wishlist")
public class WishListController {
    @Autowired
    IWishListService iWishListService;
    @Autowired
    IUserService iUserService;
    @Autowired
    ModelMapper mapper;
    @Autowired
    IProductService iProductService;

    @GetMapping("/addWishList")
    public ResponseEntity<?> addWishList(@RequestParam(value = "iDProduct") Long id) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
        	System.out.println("UnAuthorwired");
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        System.out.println("userDEtail");
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ProductEntity toCartResponse = iWishListService.addProductWishList(userDetails.getId(), id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK.value(), "successful!", toCartResponse));

    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(@RequestParam String action, @RequestParam(required = false) Long favProductId,
                                     @RequestParam(defaultValue = "0") int pageIndex, @RequestParam(defaultValue = "10") int pageSize) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = this.iUserService.findById(userDetails.getId());
        WishListEntity wishList = this.iWishListService.findByUserEntity(user);
        Specification<WishListItemEntity> spec = new Specification<WishListItemEntity>() {
            @Override
            public Predicate toPredicate(Root<WishListItemEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("wishList"), wishList);
            }
        };
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        List<WishListItemEntity> wishListItemEntityList = null;
        if (action.equals("show")) {
            wishListItemEntityList = this.iWishListService.findAll(spec, pageable);
        } else if (action.equals("remove")) {
            ProductEntity productEntity = this.iProductService.findByIdProduct(favProductId);
            this.iWishListService.delete(productEntity);
            wishListItemEntityList = this.iWishListService.findAll(spec, pageable);
        }
        List<WishListItemResponse> list = new ArrayList<>();
        for (WishListItemEntity wishListItemEntity : wishListItemEntityList) {
            list.add(this.mapper.map(wishListItemEntity, WishListItemResponse.class));

        }
        System.out.println(list);
        return ResponseEntity.ok(list);
    }

}
