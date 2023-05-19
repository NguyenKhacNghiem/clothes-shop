package clothes_shop.model;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@Setter @Getter
public class Product implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private String color;
	private String description;
	private int price;
	private String image;
	private String brand;
	private String catalog;
	private int deleted; // 0: chưa xóa, 1: đã xóa
}
