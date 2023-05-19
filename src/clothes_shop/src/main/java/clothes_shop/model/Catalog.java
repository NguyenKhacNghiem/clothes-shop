package clothes_shop.model;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "catalog")
@Setter @Getter
public class Catalog implements Serializable {
	@Id
	private String name;
	private String image;
}
