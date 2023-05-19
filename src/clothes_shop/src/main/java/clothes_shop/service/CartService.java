package clothes_shop.service;

import clothes_shop.model.Cart;

public interface CartService {
	void save(Cart cart);
	Cart getOne(int id);
}
