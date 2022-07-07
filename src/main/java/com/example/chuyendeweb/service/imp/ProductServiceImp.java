package com.example.chuyendeweb.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.chuyendeweb.entity.CategoryEntity;
import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.model.response.CategoryResponse;
import com.example.chuyendeweb.model.response.ProductResponse;
import com.example.chuyendeweb.repository.CategoryRepository;
import com.example.chuyendeweb.repository.ProductRepository;
import com.example.chuyendeweb.repository.specification.builder.ProductSpecificationsBuilder;
import com.example.chuyendeweb.service.IProductService;

@Service
public class ProductServiceImp implements IProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper mapper;

    @Override
    public ProductResponse findById(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId).get();
        CategoryEntity categoryEntity = categoryRepository.findByProductEntitys(productEntity);
        CategoryResponse categoryResponse = this.mapper.map(categoryEntity, CategoryResponse.class);
        ProductResponse productResponse = this.mapper.map(productEntity, ProductResponse.class);
        productResponse.setCategoryResponse(categoryResponse);
        return productResponse;
    }

    @Override
    public ProductEntity findByIdProduct(long id) {
        return this.productRepository.findById(id).get();
    }

    @Override
    public Map<String, Object> showAndSearchProduct(String searchValue, Pageable pageable) {

        Page<ProductEntity> pageTuts;
        if (searchValue.equals("all")) {
            pageTuts = this.productRepository.findAll(pageable);
        } else {
            pageTuts = this.productRepository.findByNameContainingIgnoreCase(searchValue, pageable);
        }
        List<ProductEntity> productEntityList = pageTuts.getContent();
        List<ProductResponse> ProductResponse = this.covertProductEntityToResponse(productEntityList);
        Map<String, Object> result = new HashMap<>();
        result.put("products", ProductResponse);
        result.put("curerentPage", pageTuts.getNumber());
        result.put("totalitems", pageTuts.getTotalElements());
        result.put("totalPage", pageTuts.getTotalPages());
        return result;
    }

    @Override
    public Map<String, Object> showProductFilter(Map<String, Object> fiterParams,
            Long genderId,
            List<Long> category,
            List<Long> priceRanges,
            List<String> ordersProduct,
            List<String> brandProducts,
            int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        ProductSpecificationsBuilder specBuilder = new ProductSpecificationsBuilder();
        for (String key : fiterParams.keySet()) {

            if (!StringUtils.isEmpty(key)) {
                switch (key) {
                    case "gendersId":
                        System.out.println("key" + key);
                        specBuilder.with("genders", "in", genderId);
                        break;
                    case "categoryId":
                        // System.out.println("key" + key);
                         System.out.println("categoryNe" + category);
                        specBuilder.with("categoryEntity", "in", category);
                        break;
                    // case "ageIds":
                    // specBuilder.with("ages", "in",fiterParams.get(key));
                    // break;
                    case "priceRange":
                        System.out.println("key" + key);
                        specBuilder.with("price", "between", priceRanges);
                        break;
                    case "page":
                        specBuilder.with("isNew", "=", true);
                        break;
                    case "brand":
                    	System.out.println("branddd");
                    	System.out.println(brandProducts);
                        	 specBuilder.with("sourceOrigin","in", brandProducts);
                    	break;
                    case "order":
                        System.out.println("key" + key);
                        String orderBy = ordersProduct.get(0);
                        String direction = ordersProduct.get(1);

                        List<Order> orders = new ArrayList<Order>();
                        /* Setup orders */
                        if (!StringUtils.isEmpty(orderBy) && !StringUtils.isEmpty(direction)) {

                            if (direction.equals("asc")) {
                                if (orderBy.equals("price")) {
                                    orders.add(new Order(Sort.Direction.ASC, "price"));
                                } else if (orderBy.equals("name")) {
                                    orders.add(new Order(Sort.Direction.ASC, "name"));
                                }
                            } else {
                                if (orderBy.equals("price")) {
                                    orders.add(new Order(Sort.Direction.DESC, "price"));
                                } else if (orderBy.equals("name")) {
                                    orders.add(new Order(Sort.Direction.DESC, "name"));
                                }
                            }
                        }
                        Sort sort = Sort.by(orders);
                        pageable = PageRequest.of(pageIndex, pageSize, sort);
                        break;
                    default:
                        break;
                }
            }
        }
        Page<ProductEntity> pageTuts;
        if (specBuilder.build() != null) {
            pageTuts = this.productRepository.findAll(specBuilder.build(), pageable);
        } else {
            System.out.println("key null");
            pageTuts = this.productRepository.findAll(pageable);
        }
        List<ProductEntity> productEntityList = pageTuts.getContent();
        List<ProductResponse> ProductResponse = this.covertProductEntityToResponse(productEntityList);
        Map<String, Object> result = new HashMap<>();
        result.put("products", ProductResponse);
        result.put("curerentPage", pageTuts.getNumber());
        result.put("totalitems", pageTuts.getTotalElements());
        result.put("totalPage", pageTuts.getTotalPages());
        return result;
    }

    public List<ProductResponse> covertProductEntityToResponse(List<ProductEntity> productDetailEntities) {
        List<ProductResponse> responseList = new ArrayList<>();
        for (ProductEntity productEntity : productDetailEntities) {
            responseList.add(this.mapper.map(productEntity, ProductResponse.class));
            // System.out.println(productDetailEntities);
        }
        return responseList;
    }

    public List<ProductResponse> getRandomProduct(List<ProductEntity> list, int totalItems) {
        Random rand = new Random();
        List<ProductEntity> newList = new ArrayList<>();
        int numberProduct = ((totalItems <= list.size()) ? totalItems : list.size());
        for (int i = 0; i < numberProduct; i++) {
            int randomIndex = rand.nextInt(list.size());
            newList.add(list.get(randomIndex));
        }
        List<ProductResponse> responseList = new ArrayList<>();
        for (ProductEntity productEntity : newList) {
            responseList.add(this.mapper.map(productEntity, ProductResponse.class));
            // System.out.println(productDetailEntities);
        }
        return responseList;
    }
}
