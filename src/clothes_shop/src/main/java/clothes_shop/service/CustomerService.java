package clothes_shop.service;

import java.util.List;

import clothes_shop.model.Customer;

public interface CustomerService {
	List<Customer> getAll();
	void save(Customer customer);
	Customer getOne(String username);
	// Lấy ra danh sách khách hàng mà tài khoản của họ chưa bị xóa (đang hoạt động hoặc bị khóa)
	List<Customer> getAllByAdmin();
}
