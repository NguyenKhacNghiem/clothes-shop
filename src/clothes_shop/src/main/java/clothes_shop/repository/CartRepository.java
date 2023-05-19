package clothes_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clothes_shop.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

}
