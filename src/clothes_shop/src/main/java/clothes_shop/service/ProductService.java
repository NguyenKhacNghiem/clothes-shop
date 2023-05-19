package clothes_shop.service;

import java.util.List;

import clothes_shop.model.Product;

public interface ProductService {
	List<Product> getAll();
	Product getOne(int id);
	boolean save(Product product); // add, update
	boolean delete(int id);
	List<Product> findRelatedProducts(String catalog, int currentId);
	List<Product> searchAndFilterProducts(String catalog, String brand1, String brand2, String brand3, 
										  String brand4, String brand5, int price1, int price2, 
										  int price3, int price4, String nameSearch, String colorSearch, 
										  String brandSearch, int start, int limit);
	List<Product> findByCatalog(String catalog, int start, int limit);
	List<Product> searchProductsInCatalog(String catalog, String nameSearch, String colorSearch, String brandSearch, int start, int limit);
}
