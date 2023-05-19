package clothes_shop.service;

import java.util.List;

import clothes_shop.model.Order;

public interface OrderService {
	List<Order> getAll();
	Order getOne(String id);
	boolean save(Order order); // add, update
	boolean delete(String id);
}
