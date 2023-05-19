package clothes_shop.service;

import java.util.List;

import clothes_shop.model.ProductOrder;
import clothes_shop.model.ProductOrderPrimaryKey;

public interface ProductOrderService {
	List<ProductOrder> getAll();
	ProductOrder getOne(ProductOrderPrimaryKey id);
	void save(ProductOrder productOrder);
	void delete(ProductOrderPrimaryKey id);
}
