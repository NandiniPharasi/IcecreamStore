package com.demo.controller;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.entity.ImageModel;
import com.demo.entity.Product;
import com.demo.service.ProductServiceImpl;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductServiceImpl productservice;
	
	@PreAuthorize("hasRole('Admin')")
	@PostMapping(value={"/addNewProduct"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Product addNewProduct(@RequestPart("product") Product product,
			                  @RequestPart("imageFile") MultipartFile[] file) {
		try {
			Set<ImageModel> images = uploadImage(file);
			product.setProductImages(images);
			return productservice.addNewProduct(product);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	
	public  Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
		Set<ImageModel> imageModels = new HashSet<>();
		for(MultipartFile file: multipartFiles) {
			ImageModel imageModel = new ImageModel(file.getOriginalFilename(),
					file.getContentType(), file.getBytes());
			imageModels.add(imageModel);
		}
		return imageModels;
	}
	
	@GetMapping({"/getAllProducts"})
	 public List<Product> getAllProducts(@RequestParam(defaultValue = "0") int pageNumber,
                                      @RequestParam(defaultValue = "") String searchKey) {
	       List<Product> result = productservice.getAllProducts(pageNumber, searchKey);
	       System.out.println("Result size is "+ result.size());
	       return result;
	    }
	
	@PreAuthorize("hasRole('Admin')")
	@DeleteMapping("/deleteProductDetails/{productId}")
	public void deleteProductDetails(@PathVariable Integer productId) {
		productservice.deleteProductDetails(productId);
	}
	
	@PutMapping("/updateProductDetails/{productId}")
	public Product updateProductDetails(@PathVariable Integer productId,@RequestPart("product") Product pobj,
			@RequestPart("imageFile") MultipartFile[] file) {
		try {
//			if(!pobj.getProductImages().isEmpty()) {
			Set<ImageModel> images = uploadImage(file);
			pobj.setProductImages(images);
//			}
			return productservice.updateProductDetails(productId, pobj);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PreAuthorize("hasRole('User')")
    @GetMapping({"/getProductDetails/{isSingleProductCheckout}/{productId}"})
    public List<Product> getProductDetails(@PathVariable(name = "isSingleProductCheckout" ) boolean isSingleProductCheckout,
                                           @PathVariable(name = "productId")  Integer productId) {
        return productservice.getProductDetails(isSingleProductCheckout, productId);
    }
	
	
	//for feign
	@GetMapping("/getProductDetailsById/{productId}")
	public Product getProductDetailsById(@PathVariable Integer productId) {
		return productservice.getProductDetailsById(productId);
	}
}
