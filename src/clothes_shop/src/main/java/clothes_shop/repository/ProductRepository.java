package clothes_shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import clothes_shop.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query("select p from Product p where p.deleted = 0")
	List<Product> findAll();
	
	@Query("select p from Product p where p.id = ?1 and p.deleted = 0")
	Optional<Product> findById(int id);
	
	// Thay vì xóa sản phẩm sẽ ảnh hưởng đến các ràng buộc giữa nó với các bảng khác thì ta ẩn nó đi
	@Modifying // cho phép thực hiện các câu lệnh != select
	@Query(value = "UPDATE Product SET deleted = 1 WHERE id = ?1", nativeQuery = true) // cho phép dùng ngôn ngữ SQL
	void deleteById(int id);
	
	// Tìm các sản phẩm liên quan
	@Query("select p from Product p where p.deleted = 0 and p.catalog = ?1 and p.id != ?2")
	List<Product> findRelatedProducts(String catalog, int currentId);
	
	// Tìm kiếm và lọc sản phẩm
	@Query(
			value = "select * from Product p where p.deleted = 0 and p.catalog = ?1 and "
			+ "(p.brand = ?2 or p.brand = ?3 or p.brand = ?4 or p.brand = ?5 or p.brand = ?6 or"
			+ " p.price < ?7 or p.price < ?8 or p.price < ?9 or p.price < ?10) and "
			+ "(p.name like %?11% or p.color like %?12% or p.brand like %?13%) "
			+ "limit ?14, ?15", 
			nativeQuery = true) // native query cho phép dùng câu lệnh SQL thuần
	List<Product> searchAndFilterProducts(String catalog, String brand1, String brand2, String brand3, 
										  String brand4, String brand5, int price1, int price2, 
										  int price3, int price4, String nameSearch, String colorSearch, 
										  String brandSearch, int start, int limit);
	
	// Xem danh sách sản phẩm có trong 1 danh mục
	@Query(
			value = "select * from Product p where p.deleted = 0 and p.catalog = ?1 limit ?2, ?3",
			nativeQuery = true) // native query cho phép dùng câu lệnh SQL thuần
	List<Product> findByCatalog(String catalog, int start, int limit);
	
	// Tìm kiếm sản phẩm theo các tiêu chỉ trong 1 danh mục
	@Query(
			value = "select * from Product p where p.deleted = 0 and p.catalog = ?1 and (p.name like %?2% or p.color like %?3% or p.brand like %?4%) limit ?5, ?6",
			nativeQuery = true) // native query cho phép dùng câu lệnh SQL thuần
	List<Product> searchProductsInCatalog(String catalog, String nameSearch, String colorSearch, String brandSearch, int start, int limit);
}
