package clothes_shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothes_shop.model.Customer;
import clothes_shop.repository.CustomerRepository;

@Service
public class CustomerServiceImplementation implements CustomerService {
	@Autowired
	private CustomerRepository cr;
	
	@Override
	public void save(Customer customer) {
		cr.save(customer);
	}

	@Override
	public Customer getOne(String username) {
		Optional<Customer> customer = cr.findById(username);

		if (customer.isPresent())
			return customer.get();

		return null;
	}

	@Override
	public List<Customer> getAll() {
		return cr.findAll();
	}
	
	// Lấy ra danh sách khách hàng mà tài khoản của họ chưa bị xóa (đang hoạt động hoặc bị khóa)
	@Override
	public List<Customer> getAllByAdmin() {
		return cr.getAllByAdmin();
	}
}
