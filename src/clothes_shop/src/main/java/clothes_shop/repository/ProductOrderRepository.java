package clothes_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clothes_shop.model.ProductOrder;
import clothes_shop.model.ProductOrderPrimaryKey;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, ProductOrderPrimaryKey> {
}
