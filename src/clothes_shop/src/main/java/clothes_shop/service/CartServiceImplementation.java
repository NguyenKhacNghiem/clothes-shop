package clothes_shop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothes_shop.model.Cart;
import clothes_shop.repository.CartRepository;

@Service
public class CartServiceImplementation implements CartService {
	@Autowired
	private CartRepository cr;

	@Override
	public void save(Cart cart) {
		cr.save(cart);
	}

	@Override
	public Cart getOne(int id) {
		Optional<Cart> cart = cr.findById(id);

		if (cart.isPresent())
			return cart.get();

		return null;
	}

}
