package clothes_shop.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothes_shop.model.Order;
import clothes_shop.repository.OrderRepository;

@Service
public class OrderServiceImplementation implements OrderService {
	@Autowired
	private OrderRepository or;
	
	@Override
	public List<Order> getAll() {
		return or.findAll();
	}

	@Override
	public Order getOne(String id) {
		Optional<Order> order = or.findById(id);
		
	    if (order.isPresent()) 
	    	return order.get();
	    
	    return null;
	}

	@Override
	public boolean save(Order order) {
		or.save(order);
		return true;
	}

	@Override
	public boolean delete(String id) {
		if(getOne(id) != null) {
			or.deleteById(id);
	        return true;
		}
		
	    return false;
	}
}
