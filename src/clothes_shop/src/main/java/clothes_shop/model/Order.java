package clothes_shop.model;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Setter @Getter
public class Order {
	@Id
	private String id;
	private String username;
	private int totalPrice;
	private String date;
	private String status;
}
