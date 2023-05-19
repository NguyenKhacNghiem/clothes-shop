package clothes_shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class UpdateProductInCartDto {
	private int productId;
	private int cartId;
	private String size;
	private String operation; // "increase", "decrease"
	
	public UpdateProductInCartDto(int productId, int cartId, String size, String operation) {
		this.productId = productId;
		this.cartId = cartId;
		this.size = size;
		this.operation = operation;
	}
}
