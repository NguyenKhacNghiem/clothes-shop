package clothes_shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothes_shop.dto.ShowProductInCartDto;
import clothes_shop.model.ProductCart;
import clothes_shop.model.ProductCartPrimaryKey;
import clothes_shop.repository.ProductCartRepository;

@Service
public class ProductCartServiceImplementation implements ProductCartService {
	@Autowired
	private ProductCartRepository pcr;
	
	@Override
	public List<ProductCart> getAll() {
		return pcr.findAll();
	}

	@Override
	public ProductCart getOne(ProductCartPrimaryKey id) {
		Optional<ProductCart> productCart = pcr.findById(id);
		
	    if (productCart.isPresent()) 
	    	return productCart.get();
	    
	    return null;
	}

	@Override
	public void save(ProductCart productCart) {
		pcr.save(productCart);
	}

	@Override
	public void delete(ProductCartPrimaryKey id) {
		if(getOne(id) != null) 
			pcr.deleteById(id);		
	}
}
