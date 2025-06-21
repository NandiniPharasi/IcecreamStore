package com.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//import com.demo.configuration.JwtRequestFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.demo.dao.ProductDao;
import com.demo.entity.Product;
import com.dto.entity.*;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductDao productdao; 
	
	@Autowired
	private AuthServiceFeignClient authservicefeignclient;
	
	@Autowired
	private CartServiceFeignClient cartservicefeignclient;
	
	
	public Product addNewProduct(Product product) {
		return productdao.save(product);
	}
	
	public List<Product> getAllProducts(int pageNumber, String searchKey) {
        Pageable pageable = PageRequest.of(pageNumber,9);
        List<Product> plist = null;

        if(searchKey.equals("")) {
            plist = productdao.findAll(pageable);
        } else {
            plist = productdao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
                    searchKey, searchKey, pageable);
        }
        System.out.println(plist);
        return plist;
    }
	
	public void deleteProductDetails(Integer productId) {
		productdao.deleteById(productId);
	}
	
	public Product updateProductDetails(Integer productId, Product pobj) {
		Optional<Product> p = productdao.findById(productId);
		if(p.isPresent()) {
			Product pr = p.get();
			pr.setProductActualPrice(pobj.getProductActualPrice());
			pr.setProductDescription(pobj.getProductDescription());
			pr.setProductDiscountedPrice(pobj.getProductDiscountedPrice());
			pr.setProductName(pobj.getProductName());
			pr.setProductImages(pobj.getProductImages());
			Product prr = productdao.save(pr);
			return prr;
		}
		else {
			return null;
		}
	}
	
	
	
	public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId) {
	        if(isSingleProductCheckout && productId != 0) {
	            // we are going to buy a single product	 
	            List<Product> list = new ArrayList<>();
	            Product product = productdao.findById(productId).get();
	            list.add(product);
	            return list;
	        } else {
	            // we are going to checkout entire cart
//	        	List<CartDto> cartDtoList = cartInterface.getCartDetails((String) authentication.getCredentials());
//	            String username = JwtRequestFilter.CURRENT_USER;	 
	        	Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
	        	String username = (String) authentication.getCredentials();
	            List<CartDto> carts = cartservicefeignclient.getCartbyusername(username); 
	            List<Product> cplist = new ArrayList<>();
	            for(CartDto i: carts) {
	            	Product pobj = productdao.findById(i.getProductId()).get();
	            	cplist.add(pobj);
	            }
	            return cplist;
	        }
	    }
	
	
	
	//for feign 
    public Product getProductDetailsById(Integer productId) {
		Optional<Product> p = productdao.findById(productId);
		Product pobj = null;
		if(p.isPresent()) {
			pobj = p.get();			
		}
		System.out.println("============"+pobj+"===========");
		return pobj;
	}
}
