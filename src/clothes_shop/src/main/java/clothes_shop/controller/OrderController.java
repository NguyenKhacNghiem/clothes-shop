package clothes_shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import clothes_shop.model.Order;
import clothes_shop.service.OrderService;

@RestController
public class OrderController {
	@Autowired
	private OrderService os;

	// Lấy danh sách tất cả các đơn hàng
	@GetMapping("/api/orders")
	public String getAll() {
		List<Order> orders = os.getAll();
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			if (orders.size() > 0)
				return objectMapper.writeValueAsString(orders);
			else
				return "{\"code\": 0, \"message\": \"Danh sách đơn hàng rỗng.\"}";
		} catch (JsonProcessingException e) {
			return "{\"code\": 2, \"message\": \"Lỗi khi truy cập vào cơ sở dữ liệu.\"}";
		}
	}

	// Tìm kiếm 1 đơn hàng
	@GetMapping("/api/orders/{id}")
	public String getOne(@PathVariable String id) {
		Order order = os.getOne(id);
		ObjectMapper objectMapper = new ObjectMapper();

		if (order == null)
			return "{\"code\": 1, \"message\": \"Không tìm thấy đơn hàng.\"}";
		try {
			return "{\"code\": 0, \"data\":" + objectMapper.writeValueAsString(order) + "}";
		} catch (JsonProcessingException e) {
			return "{\"code\": 2, \"message\": \"Lỗi khi truy cập vào cơ sở dữ liệu.\"}";
		}
	}

	// Thêm (tạo) mới 1 đơn hàng
	@PostMapping("/api/add-order")
	public String add(@ModelAttribute("order") Order order) {
		// Tạo mã đơn hàng tự động: tài khoản + ngày đặt hàng + giờ đặt hàng
	    LocalDateTime currentDateTime = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	    String formattedDateTime = currentDateTime.format(formatter).replace("/", "").replace(" ", "").replace(":", "");
	   
	    order.setId(order.getUsername() + formattedDateTime);
	    order.setDate(currentDateTime.format(formatter));  // dd/MM/yyyy HH:mm:ss
	    
	    os.save(order);
	    return "{\"code\": 0, \"message\": \"Thêm đơn hàng thành công.\", \"orderId\": " + "\"" + order.getId() + "\"" + "}";
	}

	// Cập nhật đơn hàng
	@PostMapping("/api/update-order/{id}")
	public String update(@PathVariable String id, @ModelAttribute("order") Order order) {
		if (order.getUsername().isBlank() || order.getTotalPrice() < 0 || order.getDate().isBlank())
			return "{\"code\": 1, \"message\": \"Vui lòng nhập đầy đủ thông tin.\"}";

		Order selectedOrder = os.getOne(id);

		if (selectedOrder == null)
			return "{\"code\": 1, \"message\": \"Không tìm thấy đơn hàng.\"}";

		selectedOrder.setUsername(order.getUsername());
		selectedOrder.setTotalPrice(order.getTotalPrice());
		selectedOrder.setDate(order.getDate());
		selectedOrder.setStatus(order.getStatus());

		os.save(selectedOrder);

		return "{\"code\": 0, \"message\": \"Cập nhật đơn hàng thành công.\"}";
	}

	// Xóa đơn hàng
	@GetMapping("/api/delete-order/{id}")
	public String delete(@PathVariable String id) {
		if (os.delete(id))
			return "{\"code\": 0, \"message\": \"Xóa đơn hàng thành công.\"}";

		return "{\"code\": 1, \"message\": \"Không tìm thấy đơn hàng.\"}";
	}
	
	// Cập nhật trạng thái đơn hàng
	@PutMapping("/api/update-status-order/{id}")
	public String update(@PathVariable String id) {
		Order selectedOrder = os.getOne(id);
		
		if(selectedOrder.getStatus().equals("Chờ xác nhận"))
			selectedOrder.setStatus("Đang vận chuyển");
		else if(selectedOrder.getStatus().equals("Đang vận chuyển"))
			selectedOrder.setStatus("Hoàn thành");
		else
			selectedOrder.setStatus("Chờ xác nhận");
		
		os.save(selectedOrder);

		return "{\"code\": 0, \"message\": \"Cập nhật trạng thái đơn hàng thành công.\"}";
	}
}
