package clothes_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import clothes_shop.model.Product;
import clothes_shop.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	private ProductService ps;

	// Lấy danh sách tất cả các sản phẩm ở phía khách hàng
	@GetMapping("/api/products")
	public String searchAndFilterProducts(@RequestParam String catalog, 
										  @RequestParam(value = "brand1", defaultValue = "") String brand1,
										  @RequestParam(value = "brand2", defaultValue = "") String brand2,
										  @RequestParam(value = "brand3", defaultValue = "") String brand3,
										  @RequestParam(value = "brand4", defaultValue = "") String brand4,
										  @RequestParam(value = "brand5", defaultValue = "") String brand5,
										  @RequestParam(value = "price1", defaultValue = "0") int price1,
										  @RequestParam(value = "price2", defaultValue = "0") int price2,
										  @RequestParam(value = "price3", defaultValue = "0") int price3,
										  @RequestParam(value = "price4", defaultValue = "0") int price4,
										  @RequestParam String keyword, 
										  @RequestParam(value = "page", defaultValue = "1") int page) {
		
		List<Product> products = new ArrayList<>();
		
		// Xử lý phân trang
		int totalPage = 0;
		int totalRecords = 0;
		int start = 0;
		int limit = 8; // 8 sản phẩm trên 1 trang (limit = 8)
		
		// Trường hợp người dùng không lọc sản phẩm cũng như không tìm kiếm sản phẩm thì chúng ta sẽ hiển thị hết
		if(brand1.equals("") && brand2.equals("") && brand3.equals("") && brand4.equals("") && brand5.equals("") &&
		   price1 == 0 && price2 == 0 && price3 == 0 && price4 == 0) {
			if(keyword.equals("")) {
				// Cập nhật lại tổng số trang
				totalRecords = (ps.findByCatalog(catalog, 0, 9999)).size(); // Lấy tổng số record bằng cách bắt nó trả về 1 lượng lớn record 
				
				if(totalRecords != 0) {
					totalPage = (int)(Math.ceil(totalRecords / (limit*1.0))); 
					
					start = getStart(page, totalPage, limit);
					
					products = ps.findByCatalog(catalog, start, limit);
				}
			}
			else {
				// Cập nhật lại tổng số trang
				totalRecords = (ps.searchProductsInCatalog(catalog, keyword, keyword, keyword, 0, 9999)).size(); // Lấy tổng số record bằng cách bắt nó trả về 1 lượng lớn record 
				
				if(totalRecords != 0) {
					totalPage = (int)(Math.ceil(totalRecords / (limit*1.0))); 
					
					start = getStart(page, totalPage, limit);
					
					products = ps.searchProductsInCatalog(catalog, keyword, keyword, keyword, start, limit);
				}
			}
				
		}
		else {
			// Cập nhật lại tổng số trang
			totalRecords = (ps.searchAndFilterProducts(catalog, brand1, brand2, brand3, 
													   brand4, brand5, price1, price2, 
													   price3, price4, keyword, keyword, keyword, 
													   0, 9999)).size(); // Lấy tổng số record bằng cách bắt nó trả về 1 lượng lớn record
			if(totalRecords != 0) {
				totalPage = (int)(Math.ceil(totalRecords / (limit*1.0))); 
				
				start = getStart(page, totalPage, limit);
				
				products = ps.searchAndFilterProducts(catalog, brand1, brand2, brand3, 
						  							  brand4, brand5, price1, price2, 
						  							  price3, price4, keyword, keyword, keyword, 
						  							  start, limit);
			}
		}
		
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			if (products.size() > 0)
				return "{\"code\": 0, \"data\": " + objectMapper.writeValueAsString(products) + ", \"totalPage\": " + totalPage + "}";
			else
				return "{\"code\": 0, \"message\": \"Danh sách sản phẩm trống.\"}";
		} catch (JsonProcessingException e) {
			return "{\"code\": 2, \"message\": \"Lỗi khi truy cập vào cơ sở dữ liệu.\"}";
		}
	}

	// Tìm kiếm 1 sản phẩm
	@GetMapping("/api/products/{id}")
	public String getOne(@PathVariable int id) {
		Product product = ps.getOne(id);
		ObjectMapper objectMapper = new ObjectMapper();

		if (product == null)
			return "{\"code\": 1, \"message\": \"Không tìm thấy sản phẩm.\"}";
		try {
			return "{\"code\": 0, \"data\":" + objectMapper.writeValueAsString(product) + "}";
		} catch (JsonProcessingException e) {
			return "{\"code\": 2, \"message\": \"Lỗi khi truy cập vào cơ sở dữ liệu.\"}";
		}
	}
	
	// Thêm hoặc cập nhật sản phẩm
	@PostMapping("/api/save-product")
	public String save(@ModelAttribute("product") Product product, @RequestParam("filename") MultipartFile file) {
		if (product.getName().isBlank() || product.getColor().isBlank() || product.getDescription().isBlank()
				|| product.getPrice() < 0 || product.getBrand().isBlank() || product.getCatalog().isBlank())
			return "{\"code\": 1, \"message\": \"Vui lòng nhập đầy đủ thông tin.\"}";
		
		// Thêm mới sản phẩm
		if(product.getId() == 0) 
			if(file.getOriginalFilename().equals(""))
				return "{\"code\": 1, \"message\": \"Vui lòng nhập đầy đủ thông tin.\"}";
		
		// Lưu file đã được upload vào thư mục static/img
		String filename = file.getOriginalFilename();
		
		// Cập nhật sản phẩm nhưng không cập nhật ảnh
		// -> Không tải file lên -> Không cần lưu file
		if(!filename.equals("")) {
			String fileLocation = new File("src\\main\\resources\\static\\img").getAbsolutePath() + "\\" + filename;
			File dest = new File(fileLocation);
			
	        try {
	            file.transferTo(dest); // lưu file
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
        // Cập nhật sản phẩm
        if(product.getId() != 0) {
        	Product updatedProduct = ps.getOne(product.getId());
        	
        	updatedProduct.setName(product.getName());
        	updatedProduct.setColor(product.getColor());
        	updatedProduct.setDescription(product.getDescription());
        	updatedProduct.setPrice(product.getPrice());
        	
        	if(filename.equals("")) // Không cập nhật ảnh
        		updatedProduct.setImage(product.getImage());
        	else  // Có cập nhật ảnh
        		updatedProduct.setImage(filename);    		
        	
        	updatedProduct.setBrand(product.getBrand());
        	updatedProduct.setCatalog(product.getCatalog());
        	
        	ps.save(updatedProduct);
        }
        // Thêm mới sản phẩm
        else {
        	product.setImage(filename);
        	ps.save(product);
        }
        	
		return "{\"code\": 0, \"message\": \"Lưu sản phẩm thành công.\"}";
	}

	// Xóa sản phẩm
	// Thay vì xóa sản phẩm sẽ ảnh hưởng đến các ràng buộc giữa nó với các bảng khác thì ta ẩn nó đi
	@DeleteMapping("/api/delete-product/{id}")
	public String delete(@PathVariable int id) {
		if (ps.delete(id))
			return "{\"code\": 0, \"message\": \"Xóa sản phẩm thành công.\"}";

		return "{\"code\": 1, \"message\": \"Không tìm thấy sản phẩm.\"}";
	}
	
	// Lấy số record bị bỏ qua (start) trong chức năng phân trang
	private int getStart(int page, int totalPage, int limit) {
		if(page < 1)
			page = 1;
		
		if(page > totalPage)
			page = totalPage;
		
		int start = (page - 1) * limit;
		return start;
	}
}
