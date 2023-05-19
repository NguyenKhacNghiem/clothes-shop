package clothes_shop.model;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productcart")
@Setter @Getter
public class ProductCart implements Serializable {
	@EmbeddedId
	private ProductCartPrimaryKey id;
	private int quantity;
}
