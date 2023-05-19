package clothes_shop.model;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer")
@Setter @Getter
public class Customer implements Serializable {
	@Id
	private String username;
	private int cartId;
	private String password;
	private String fullname;
	private String phone;
	private String address;
	private String type; // đồng, bạc, vàng
	private int active; // 0: đang hoạt động, 1: đã xóa tài khoản, 2: đã bị khóa tài khoản
}
