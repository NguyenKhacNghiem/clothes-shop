package clothes_shop.model;

import java.io.Serializable;
import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart")
@Setter @Getter
public class Cart implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private int totalPrice;
}
