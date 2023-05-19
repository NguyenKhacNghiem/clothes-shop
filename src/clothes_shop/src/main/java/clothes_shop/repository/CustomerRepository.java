package clothes_shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import clothes_shop.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
	// Lấy ra danh sách khách hàng mà tài khoản của họ chưa bị xóa (đang hoạt động hoặc bị khóa)
	@Query("select c from Customer c where active != 1 and type != 'admin'")
	List<Customer> getAllByAdmin();
}
