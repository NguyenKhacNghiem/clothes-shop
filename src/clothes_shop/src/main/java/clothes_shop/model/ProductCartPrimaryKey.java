package clothes_shop.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class ProductCartPrimaryKey implements Serializable{
	private int productId;
	private int cartId;
	private String size;
}
