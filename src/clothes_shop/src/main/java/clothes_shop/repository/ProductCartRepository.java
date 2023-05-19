package clothes_shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import clothes_shop.model.ProductCart;
import clothes_shop.model.ProductCartPrimaryKey;

@Repository
public interface ProductCartRepository extends JpaRepository<ProductCart, ProductCartPrimaryKey> {
	@Query("select pc from ProductCart pc, Product p where p.id = pc.id.productId and p.deleted = 0")
	List<ProductCart> findAll();
}
