package clothes_shop.model;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productorder")
@Setter @Getter
public class ProductOrder implements Serializable {
	@EmbeddedId
	private ProductOrderPrimaryKey id;
	private int quantity;
}
