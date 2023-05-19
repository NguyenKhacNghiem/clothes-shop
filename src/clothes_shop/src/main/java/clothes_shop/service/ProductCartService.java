package clothes_shop.service;

import java.util.List;

import clothes_shop.model.ProductCart;
import clothes_shop.model.ProductCartPrimaryKey;

public interface ProductCartService {
	List<ProductCart> getAll();
	ProductCart getOne(ProductCartPrimaryKey id);
	void save(ProductCart productCart);
	void delete(ProductCartPrimaryKey id);
}
