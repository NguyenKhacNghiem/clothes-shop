package clothes_shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class DeleteProductInCartDto {
	private int productId;
	private int cartId;
	private String size;
	
	public DeleteProductInCartDto(int productId, int cartId, String size, String operation) {
		this.productId = productId;
		this.cartId = cartId;
		this.size = size;
	}
}
