package clothes_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import clothes_shop.model.Customer;
import clothes_shop.service.CustomerService;

@RestController
public class CustomerController {
	@Autowired
	private CustomerService cs;
	
	// Khóa tài khoản khách hàng
	@PutMapping("/api/block-customer/{username}")
	public String blockAndUnblock(@PathVariable String username) {
		Customer selectedCustomer = cs.getOne(username);
		
		if(selectedCustomer.getActive() == 0) {
			selectedCustomer.setActive(2);
			cs.save(selectedCustomer);
			
			return "{\"code\": 0, \"message\": \"Khóa tài khoản khách hàng thành công.\"}";
		}
		
		selectedCustomer.setActive(0);
		cs.save(selectedCustomer);
		
		return "{\"code\": 0, \"message\": \"Mở khóa tài khoản khách hàng thành công.\"}";
	}
	
	// Thay đổi loại (type) tài khoản của khách hàng
	@PutMapping("/api/change-type-customer/{username}")
	public String changeType(@PathVariable String username) {
		Customer selectedCustomer = cs.getOne(username);
		
		if(selectedCustomer.getType().equals("đồng"))
			selectedCustomer.setType("bạc");
		else if(selectedCustomer.getType().equals("bạc"))
			selectedCustomer.setType("vàng");
		else
			selectedCustomer.setType("đồng");
		
		cs.save(selectedCustomer);
		
		return "{\"code\": 0, \"message\": \"Thay đổi loại tài khoản của khách hàng thành công.\"}";
	}
	
	@PostMapping("/api/change-info-customer")
	public String changeInfo(@RequestParam String username, @RequestParam String fullname, 
							 @RequestParam String phone, @RequestParam String address) {
		Customer selectedCustomer = cs.getOne(username);
				
		selectedCustomer.setFullname(fullname);
		selectedCustomer.setPhone(phone);
		selectedCustomer.setAddress(address);
		
		cs.save(selectedCustomer);
		
		return "{\"code\": 0, \"message\": \"Thay đổi thông tin khách hàng thành công.\"}";
	}
	
	@PostMapping("/api/forget-password")
	public String forgetPassword(@RequestParam String username, @RequestParam String phone) {
		Customer selectedCustomer = cs.getOne(username);
				
		if(selectedCustomer == null)
			return "{\"code\": 1, \"message\": \"Không tìm thấy mật khẩu của bạn.\"}";
		
		if(!selectedCustomer.getPhone().equals(phone))
			return "{\"code\": 1, \"message\": \"Không tìm thấy mật khẩu của bạn.\"}";
		
		String password = decryptPassword(selectedCustomer.getPassword());
		
		return "{\"code\": 0, \"data\": " + "\"" + password + "\"" + "}";
	}
	
	@PostMapping("/api/change-password")
	public String changePassword(@RequestParam String username, @RequestParam String oldPassword, 
			 					 @RequestParam String newPassword, @RequestParam String confirmNewPassword) {
		
		Customer selectedCustomer = cs.getOne(username);
		
		if(!decryptPassword(selectedCustomer.getPassword()).equals(oldPassword))
			return "{\"code\": 1, \"message\": \"Mật khẩu cũ không chính xác\"}";
		
		if(!newPassword.equals(confirmNewPassword))
			return "{\"code\": 1, \"message\": \"Mật khẩu mới và xác nhận mật khẩu không khớp\"}";
		
		selectedCustomer.setPassword(encryptPassword(newPassword));
		cs.save(selectedCustomer);
		
		return "{\"code\": 0, \"message\": \"Đổi mật khẩu thành công\"}";
	}
	
	// Hàm mã hóa mật khẩu sử dụng giải thuật Caesar
	private String encryptPassword(String plain) {
	    String cipher = "";

	    for (int i = 0; i < plain.length(); i++)
	        cipher += (char) (plain.charAt(i) + 1); // key = 1
	    
	    return cipher;
	}
	
	// Hàm giải mã mật khẩu đã được mã hóa bằng giải thuật Caesar
	private String decryptPassword(String cipher) {
	    String plain = "";

	    for (int i = 0; i < cipher.length(); i++)
	        plain += (char) (cipher.charAt(i) - 1); // key = 1

	    return plain;
	}
}
