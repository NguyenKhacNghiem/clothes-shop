package clothes_shop.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class ProductOrderPrimaryKey implements Serializable{
	private int productId;
	private String orderId;
	private String size;
}
