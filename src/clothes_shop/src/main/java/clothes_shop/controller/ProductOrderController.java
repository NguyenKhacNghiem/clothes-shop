package clothes_shop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import clothes_shop.dto.ShowProductInCartDto;
import clothes_shop.dto.ShowProductInOrderDto;
import clothes_shop.model.*;
import clothes_shop.service.*;

@RestController
public class ProductOrderController {
	@Autowired
	private ProductOrderService pos;
	@Autowired
	private ProductService ps;
	
	@PostMapping("/api/add-detail-order")
	public String addDetailOrder(@RequestParam int productId, @RequestParam String orderId, 
								 @RequestParam int quantity, @RequestParam String size) {
		
		ProductOrderPrimaryKey id = new ProductOrderPrimaryKey();
		id.setOrderId(orderId);
		id.setProductId(productId);
		id.setSize(size);
		
		ProductOrder productOrder = new ProductOrder();
		productOrder.setId(id);
		productOrder.setQuantity(quantity);
		
		pos.save(productOrder);
		
		return "{\"code\": 0, \"message\": \"Thêm chi tiết hóa đơn thành công.\"}"; 
	}
	
	@GetMapping("/api/products-in-order")
	public String getProductsInOrder(@RequestParam String orderId) {
		List<ProductOrder> productOrders = pos.getAll();
		List<ShowProductInOrderDto> showProductInOrderDtos = new ArrayList<>();
		
		for(ProductOrder po : productOrders) 
			if(po.getId().getOrderId().equals(orderId)) {
				int productId = po.getId().getProductId();
				int quantity = po.getQuantity();
				String size = po.getId().getSize();
				
				Product product = ps.getOne(productId);
				String name = product.getName();
				String image = product.getImage();
				
				int price = product.getPrice() * quantity;
				
				showProductInOrderDtos.add(new ShowProductInOrderDto(productId, image, name, size, quantity, price));
			}
		
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			return "{\"code\": 0, \"data\": " + objectMapper.writeValueAsString(showProductInOrderDtos) + "}";
		} catch (JsonProcessingException e) {
			return "{\"code\": 2, \"message\": \"Lỗi khi truy cập vào cơ sở dữ liệu.\"}";
		}		
	}
}
