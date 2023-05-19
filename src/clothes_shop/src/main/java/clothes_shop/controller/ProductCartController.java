package clothes_shop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import clothes_shop.dto.*;
import clothes_shop.model.*;
import clothes_shop.service.*;

@RestController
public class ProductCartController {
	@Autowired
	private ProductCartService pcs;
	
	@PutMapping("cart")
	public String updateQuantity(@RequestBody UpdateProductInCartDto updateProductInCartDto) {
		ProductCartPrimaryKey id = new ProductCartPrimaryKey();
		
		id.setProductId(updateProductInCartDto.getProductId());
		id.setCartId(updateProductInCartDto.getCartId());
		id.setSize(updateProductInCartDto.getSize());
		
		ProductCart pc = pcs.getOne(id);
		
		if(updateProductInCartDto.getOperation().equals("increase")) {
			pc.setQuantity(pc.getQuantity() + 1);
			
			pcs.save(pc);
			return "{\"code\": 0, \"message\": \"Tăng số lượng sản phẩm thành công.\"}";
		}
		else {
			pc.setQuantity(pc.getQuantity() - 1);
			
			pcs.save(pc);
			return "{\"code\": 0, \"message\": \"Giảm số lượng sản phẩm thành công.\"}";
		}
	}
	
	@DeleteMapping("cart")
	public String deleteProductInCart(@RequestBody DeleteProductInCartDto deleteProductInCartDto) {
		ProductCartPrimaryKey id = new ProductCartPrimaryKey();
		
		id.setProductId(deleteProductInCartDto.getProductId());
		id.setCartId(deleteProductInCartDto.getCartId());
		id.setSize(deleteProductInCartDto.getSize());
		
		pcs.delete(id);
		return "{\"code\": 0, \"message\": \"Xóa sản phẩm trong giỏ hàng thành công.\"}";
	}
}
