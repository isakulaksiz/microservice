package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

	List<Inventory> inventoryList = new ArrayList<Inventory>();
	
	@GetMapping("/inventory/{productid}")
	public Inventory getInventoryDetails(@PathVariable Long productid) {
		Inventory price = getInventoryInfo(productid);
		
		
		return price;
	}

	private Inventory getInventoryInfo(Long productid) {
		populateInventoryList();
		
		for(Inventory i: inventoryList) {
			if(productid.equals(i.getProductID())) {
				return i;
			}
		}
		
		return null;
	}

	private void populateInventoryList() {
		inventoryList.add(new Inventory(301L, 101l, true));
		inventoryList.add(new Inventory(302L, 102l, true));
		inventoryList.add(new Inventory(303L, 103l, false));
	}
	
}
