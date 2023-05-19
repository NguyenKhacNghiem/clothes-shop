package clothes_shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothes_shop.model.ProductOrder;
import clothes_shop.model.ProductOrderPrimaryKey;
import clothes_shop.repository.ProductOrderRepository;

@Service
public class ProductOrderServiceImplementation implements ProductOrderService {
	@Autowired
	private ProductOrderRepository por;
	
	@Override
	public List<ProductOrder> getAll() {
		return por.findAll();
	}

	@Override
	public ProductOrder getOne(ProductOrderPrimaryKey id) {
		Optional<ProductOrder> productOrder = por.findById(id);
		
	    if (productOrder.isPresent()) 
	    	return productOrder.get();
	    
	    return null;
	}

	@Override
	public void save(ProductOrder productOrder) {
		por.save(productOrder);
	}

	@Override
	public void delete(ProductOrderPrimaryKey id) {
		if(getOne(id) != null) 
			por.deleteById(id);		
	}
}
