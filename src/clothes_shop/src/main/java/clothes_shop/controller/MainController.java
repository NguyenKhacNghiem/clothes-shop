package clothes_shop.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import clothes_shop.model.*;
import clothes_shop.dto.ShowProductInCartDto;
import clothes_shop.service.*;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCartService productCartService; 
	@Autowired
	private OrderService orderService; 
	@Autowired
	private CatalogService catalogService; 
	private String username = ""; // kiểm tra xem người dùng có đăng nhập chưa
	private boolean darkTheme = false;

	// Admin views
	@GetMapping("manage-products")
	public String showManageProducts(RedirectAttributes redirect, Model model) {		
		if(!this.username.equals("admin")) {
			redirect.addFlashAttribute("customer", new Customer());
			return "redirect:/sign-in";
		}
		
		List<Product> products = productService.getAll();
		model.addAttribute("products", products);
		model.addAttribute("product", new Product());
		
		model.addAttribute("catalogs", catalogService.getAll()); // load danh mục sản phẩm lên nav bar
		return "manage-products";
	}
	
	@GetMapping("manage-products/{id}")
	public String showUpdatedProduct(@PathVariable int id, Model model, RedirectAttributes redirect) {
		if(!this.username.equals("admin")) {
			redirect.addFlashAttribute("customer", new Customer());
			return "redirect:/sign-in";
		}
		
		List<Product> products = productService.getAll();
		Product selectedProduct = productService.getOne(id);
		
		model.addAttribute("product", selectedProduct);
		model.addAttribute("products", products);
		
		model.addAttribute("catalogs", catalogService.getAll()); // load danh mục sản phẩm lên nav bar
		return "manage-products";
	}
	
	@GetMapping("manage-customers")
	public String showManageCustomers(Model model, RedirectAttributes redirect) {
		if(!this.username.equals("admin")) {
			redirect.addFlashAttribute("customer", new Customer());
			return "redirect:/sign-in";
		}
		
		List<Customer> customers = customerService.getAllByAdmin();
		model.addAttribute("customers", customers);
		
		return "manage-customers";
	}
	
	@GetMapping("manage-orders")
	public String showManageOrders(Model model, RedirectAttributes redirect) {
		if(!this.username.equals("admin")) {
			redirect.addFlashAttribute("customer", new Customer());
			return "redirect:/sign-in";
		}
		
		List<Order> orders = orderService.getAll();
		model.addAttribute("orders", orders);
		
		return "manage-orders";
	}
	// End of Admin views
	
	// ------------------------------- //
	
	// Customer views
	@GetMapping({ "/", "/index" })
	public String showIndex(Model model) {
		if(!this.username.equals("")) {
			model.addAttribute("customer", customerService.getOne(this.username));
			model.addAttribute("numberProductInCart", countProductInCart());
		}
		
		model.addAttribute("catalogs", catalogService.getAll()); // load danh mục sản phẩm lên nav bar
		model.addAttribute("darkTheme", darkTheme);
		return "index";
	}
	
	@GetMapping("products")
	public String showProducts(Model model, @RequestParam Map<String, String> parameters) {
		if(!this.username.equals("")) {
			model.addAttribute("customer", customerService.getOne(this.username));
			model.addAttribute("numberProductInCart", countProductInCart());
		}
		
		model.addAttribute("catalogs", catalogService.getAll()); // load danh mục sản phẩm lên nav bar
		model.addAttribute("darkTheme", darkTheme);
		return "products";
	}
	
	@GetMapping("cart")
	public String showCart(Model model, RedirectAttributes redirect) {
		if(this.username.equals("")) {
			redirect.addFlashAttribute("customer", new Customer());
			return "redirect:/sign-in";
		}
		
		Customer customer = customerService.getOne(this.username);
		model.addAttribute("customer", customer);
		
		// Hiển thị sản phẩm trong giỏ hàng
		int cartId = customer.getCartId();
		List<ShowProductInCartDto> showProductInCartDtos = new ArrayList<>() ;
		int totalPrice = 0;
		
		for(ProductCart pc : productCartService.getAll())
			if(pc.getId().getCartId() == cartId) {
				Product p = productService.getOne(pc.getId().getProductId());
				ShowProductInCartDto pic = new ShowProductInCartDto(p.getId(), p.getImage(), p.getName(), pc.getId().getSize(), pc.getQuantity(), p.getPrice() * pc.getQuantity());
				totalPrice += p.getPrice() * pc.getQuantity();
				
				showProductInCartDtos.add(pic);
			}
		
		// Giảm giá cho khách hàng dựa vào loại của họ (vàng, bạc, đồng)
		if(customer.getType().equals("vàng"))
			totalPrice = totalPrice - ((int)(totalPrice*0.15));
		
		if(customer.getType().equals("bạc"))
			totalPrice = totalPrice - ((int)(totalPrice*0.1));
		
		model.addAttribute("showProductInCartDtos", showProductInCartDtos);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("numberProductInCart", countProductInCart());
		
		Cart cart = cartService.getOne(cartId);
		cart.setTotalPrice(totalPrice);
		cartService.save(cart);
		
		model.addAttribute("catalogs", catalogService.getAll()); // load danh mục sản phẩm lên nav bar
		model.addAttribute("darkTheme", darkTheme);
		return "cart";	
	}
	

	@GetMapping("product-info/{id}")
	public String showProductInfo(@PathVariable int id, Model model) {
		if(!this.username.equals("")) {
			model.addAttribute("customer", customerService.getOne(this.username));
			model.addAttribute("numberProductInCart", countProductInCart());
		}
		
		// Lấy thông tin chi tiết của sản phẩm
		Product product = productService.getOne(id);
		model.addAttribute("product", product);
		
		// Tìm các sản phẩm liên quan
		List<Product> relatedProducts = productService.findRelatedProducts(product.getCatalog(), product.getId());
		model.addAttribute("relatedProducts", relatedProducts);
		
		model.addAttribute("catalogs", catalogService.getAll()); // load danh mục sản phẩm lên nav bar
		model.addAttribute("darkTheme", darkTheme);
		return "product-info";
	}
	
	
	@PostMapping("product-info/{id}")
	public String addToCart(@PathVariable int id, RedirectAttributes redirect, @RequestParam String size) {
		if(this.username.equals("")) {
			redirect.addFlashAttribute("customer", new Customer());
			return "redirect:/sign-in";
		}
		else {
			Product product = productService.getOne(id);
			redirect.addFlashAttribute("product", product);
			
			Customer customer = customerService.getOne(this.username);
			redirect.addFlashAttribute("customer", customer);
			int cartId = customer.getCartId();
			
			ProductCartPrimaryKey productCartId = new ProductCartPrimaryKey();
			productCartId.setCartId(cartId);
			productCartId.setProductId(id);
			productCartId.setSize(size);
			
			ProductCart selectedProduct = new ProductCart();
			boolean exists = false;
			
			for(ProductCart pc : productCartService.getAll())
				// Nếu sản phẩm đã tồn tại trong giỏ hàng rồi thì ta sẽ tăng số lượng lên 1
				if(pc.getId().getProductId() == productCartId.getProductId() && 
				   pc.getId().getCartId() == productCartId.getCartId() &&
				   pc.getId().getSize().equals(productCartId.getSize())) 
				{
					selectedProduct = productCartService.getOne(productCartId);
					selectedProduct.setQuantity(selectedProduct.getQuantity() + 1);
					exists = true;
					break;
				}
			
			if(!exists) {
				selectedProduct.setId(productCartId);
				selectedProduct.setQuantity(1);
			}
			
			productCartService.save(selectedProduct);
			redirect.addFlashAttribute("success", true); // Hiển thị thông báo thành công
			return "redirect:/product-info/" + id;
		}
	}

	
	@GetMapping("sign-in")
	public String showSignIn(Model model) {
		model.addAttribute("customer", new Customer());
		this.username = "";
		
		model.addAttribute("catalogs", catalogService.getAll()); // load danh mục sản phẩm lên nav bar
		return "sign-in";
	}
	
	@PostMapping("sign-in")
	public String handleSignIn(@ModelAttribute("customer") Customer customer, RedirectAttributes redirect, Model model) {
		List<Customer> customers = customerService.getAll();
				
		for(Customer c : customers)
			if(c.getUsername().equals(customer.getUsername()) && decryptPassword(c.getPassword()).equals(customer.getPassword())) {
				// Nếu admin đăng nhập
				if(c.getType().equals("admin")) {
					this.username = c.getUsername();
					return "redirect:/manage-products";
				}
					
				// Nếu khách hàng đăng nhập
				else {
					// Kiểm tra xem tài khoản của khách hàng có bị xóa không
					if(c.getActive() == 1) {
						model.addAttribute("error", "Tài khoản hoặc mật khẩu không chính xác.");
						return "sign-in";
					}
					
					// Kiểm tra xem tài khoản của khách hàng có bị khóa không
					if(c.getActive() == 2) {
						model.addAttribute("error", "Tài khoản của bạn đã bị khóa. Vui lòng liên hệ shop để biết thêm thông tin.");
						return "sign-in";
					}
					
					this.username = c.getUsername();
					redirect.addFlashAttribute(c);
					return "redirect:/index";
				}
			}
		
		model.addAttribute("error", "Tài khoản hoặc mật khẩu không chính xác.");
		return "sign-in";
	}

	
	@GetMapping("sign-up")
	public String showSignUp(Model model) {
		model.addAttribute("customer", new Customer());
		
		model.addAttribute("catalogs", catalogService.getAll()); // load danh mục sản phẩm lên nav bar
		return "sign-up";
	}

	
	@PostMapping("sign-up")
	public String handleSignUp(@ModelAttribute("customer") Customer customer, Model model, RedirectAttributes redirect, @RequestParam String confirmPassword) {
		// Kiểm tra xem người dùng có nhập đủ thông tin hay chưa
		if (customer.getUsername().isBlank() || customer.getPassword().isBlank() || customer.getFullname().isBlank()
				|| customer.getPhone().isBlank() || customer.getAddress().isBlank() || confirmPassword.isBlank()) {
			model.addAttribute("error", "Vui lòng nhập đầy đủ thông tin.");
			return "sign-up";
		}
		
		// Kiểm tra xem tài khoản này đã tồn tại hay chưa
		for(Customer c : customerService.getAll())
			if(c.getUsername().equals(customer.getUsername())) {
				model.addAttribute("error", "Tài khoản này đã tồn tại.");
				return "sign-up";
			}
		
		// Kiểm tra xem số điện thoại có đúng format không
		if(!isPhoneNumber(customer.getPhone())) {
			model.addAttribute("error", "Số điện thoại không đúng định dạng.");
			return "sign-up";
		}
		
		// Kiểm tra xem mật khẩu và xác nhận mật khẩu có khớp không
		if(!customer.getPassword().equals(confirmPassword)) {
			model.addAttribute("error", "Mật khẩu và xác nhận mật khẩu không khớp.");
			return "sign-up";
		}
		
		Cart cart = new Cart();
		cartService.save(cart);

		customer.setCartId(cart.getId());
		customer.setType("đồng");
		// Mã hóa mật khẩu
		customer.setPassword(encryptPassword(customer.getPassword()));
		
		customerService.save(customer);
		
		redirect.addFlashAttribute("message", "Đăng ký tài khoản thành công.");
		return "redirect:/sign-up";
	}
	
	private boolean isPhoneNumber(String phone) {
		String regex = "^\\d{10}$";
	    return phone.matches(regex);
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

	private int countProductInCart() {
		int cartId = customerService.getOne(this.username).getCartId();
		int count = 0;
		
		for(ProductCart pc : productCartService.getAll())
			if(pc.getId().getCartId() == cartId)
				count++;
				
		return count;
	}
	
	@GetMapping("history-orders")
	public String showHistoryOrders(Model model, RedirectAttributes redirect) {
		if(!this.username.equals("")) {
			model.addAttribute("customer", customerService.getOne(this.username));
			model.addAttribute("numberProductInCart", countProductInCart());
		}
		else {
			redirect.addFlashAttribute("customer", new Customer());
			return "redirect:/sign-in";
		}
		
		List<Order> orders = new ArrayList<>();
		for(Order o : orderService.getAll())
			if(o.getUsername().equals(this.username))
				orders.add(o);
		
		model.addAttribute("orders", orders);
		model.addAttribute("catalogs", catalogService.getAll()); // load danh mục sản phẩm lên nav bar
		model.addAttribute("darkTheme", darkTheme);
		return "history-orders";
	}
	
	@GetMapping("profile")
	public String showProfile(Model model, RedirectAttributes redirect) {
		if(!this.username.equals("")) {
			model.addAttribute("customer", customerService.getOne(this.username));
			model.addAttribute("numberProductInCart", countProductInCart());
		}
		else {
			redirect.addFlashAttribute("customer", new Customer());
			return "redirect:/sign-in";
		}
		
		model.addAttribute("catalogs", catalogService.getAll()); // load danh mục sản phẩm lên nav bar
		model.addAttribute("darkTheme", darkTheme);
		return "profile";
	}
	
	@GetMapping("error")
	public String showErrorPage() {
		return "error";
	}
	
	@GetMapping("dark-theme")
	public String darkTheme(HttpServletRequest request, RedirectAttributes redirect) {
		// Lấy đường dẫn của trang gọi đến
	    String currentUrl = request.getHeader("Referer"); // Vd: http://localhost:8080/index
	    int thirdFlash = currentUrl.indexOf('/', currentUrl.indexOf('/') + 2);
	    String currentPage = currentUrl.substring(thirdFlash + 1); // index

		if(darkTheme)
			darkTheme = false;
		else
			darkTheme = true;
		
		return "redirect:" + currentPage;
	}
	// End of Customer views
}
