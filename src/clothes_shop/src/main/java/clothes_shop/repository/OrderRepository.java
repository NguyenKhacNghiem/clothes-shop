package clothes_shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import clothes_shop.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
	public List<Order> findAll();
}
