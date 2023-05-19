package clothes_shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class ShowProductInOrderDto {
	private int productId;
	private String image;
	private String name;
	private String size;
	private int quantity;
	private int price;
	
	public ShowProductInOrderDto(int productId, String image, String name, String size, int quantity, int price) {
		this.productId = productId;
		this.image = image;
		this.name = name;
		this.size = size;
		this.quantity = quantity;
		this.price = price;
	}
}
