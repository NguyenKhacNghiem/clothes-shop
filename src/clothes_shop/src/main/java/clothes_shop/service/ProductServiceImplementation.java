package clothes_shop.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothes_shop.model.Product;
import clothes_shop.repository.ProductRepository;

@Service
public class ProductServiceImplementation implements ProductService {
	@Autowired
	private ProductRepository pr;
	
	@Override
	public List<Product> getAll() {
		return pr.findAll();
	}

	@Override
	public Product getOne(int id) {
		Optional<Product> product = pr.findById(id);
		
	    if (product.isPresent()) 
	    	return product.get();
	    
	    return null;
	}

	@Override
	public boolean save(Product product) {
		pr.save(product);
		return true;
	}

	@Override
	public boolean delete(int id) {
		if(getOne(id) != null) {
			pr.deleteById(id);
	        return true;
		}
		
	    return false;
	}

	@Override
	public List<Product> findRelatedProducts(String catalog, int currentId) {
		return pr.findRelatedProducts(catalog, currentId);
	}

	@Override
	public List<Product> searchAndFilterProducts(String catalog, String brand1, String brand2, String brand3,
												 String brand4, String brand5, int price1, int price2, 
												 int price3, int price4, String nameSearch, String colorSearch, 
												  String brandSearch, int start, int limit) {
		
		return pr.searchAndFilterProducts(catalog, brand1, brand2, brand3, 
										  brand4, brand5, price1, price2, 
										  price3, price4, nameSearch, colorSearch, 
										  brandSearch, start, limit);
	}

	@Override
	public List<Product> findByCatalog(String catalog, int start, int limit) {
		return pr.findByCatalog(catalog, start, limit);
	}

	@Override
	public List<Product> searchProductsInCatalog(String catalog, String nameSearch, String colorSearch, String brandSearch, int start, int limit) {
		return pr.searchProductsInCatalog(catalog, nameSearch, colorSearch, brandSearch, start, limit);
	}
}
