package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProductController {

	List<ProductInfo> productList = new ArrayList<ProductInfo>();
	
	@Autowired
	private RestTemplate restTemplate;

	
	/**
	 * @param productid
	 * @return
	 */
	@GetMapping("/product/details/{productid}")
	public Product getProductDetails(@PathVariable Long productid) {
		//Get name and desc from product-service
		ProductInfo productInfo = getProductInfo(productid);
		
		//Get price from pricing service
		Price price = restTemplate.getForObject("http://localhost:8002/price/"+productid, Price.class);
		
		//Get stock avail from inventory service
		Inventory inventory = restTemplate.getForObject("http://localhost:8003/inventory/"+productid, Inventory.class);
		
		return new Product(productInfo.getProductID(), productInfo.getProductName(), productInfo.getProductDesc(), price.getDiscountedPrice(), inventory.getInStock());
	}

	private ProductInfo getProductInfo(Long productid) {
		populateProductList();
		
		for(ProductInfo p: productList) {
			if(productid.equals(p.getProductID())) {
				return p;
			}
		}
		
		return null;
	}

	private void populateProductList() {
		productList.add(new ProductInfo(101L, "iPhone", "iPhone is expensive!"));
		productList.add(new ProductInfo(102L, "Book", "Lord of the rings"));
		productList.add(new ProductInfo(103L, "Macbook Pro", "Macbook is necessary!"));
	}
	
}
