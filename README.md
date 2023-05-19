# Clothes Shop Website
A clothes shop website using Java Spring Boot.
### Software Development Technologies
1. **Frameworks**
- Spring Boot: is a popular framework for Java application development that simplifies the process of configuring and deploying Java applications.
2. **Libraries**
- Hibernate/ JPA: supports access to database
- Thymeleaf: supports handling html templates
- MySQL Connector: provides JDBC driver to connect and manipulate with MySQL database.
- Lombok: provides utilities that simplify coding, including annotations to automatically generate getter, setter, constructor, and other methods.
- Jackson Core: provides utilities for handling JSON in Java.
- Commons FileUpload: provides a library to handle file uploads in web applications.
### Features
1. **Customer role**
    - Sign up
    - Sign in
    - Sign out
	- Update personal information
	- Change password
	- Forget password
	- Change the website to dark mode
    - Add products to cart
	- Update products in cart: change the quantity, delete products
	- View order history and their status
	- View an order in detail
	- View product catalogs
	- View a product in detail
	- View related products
	- View products
	- Filter products by multiple criteria
	- Search products by multiple criteria
	- Paging product list
2. **Administrator role**
    - Block or unblock customer account
	- Update customer ranking: copper, silver, gold
	- Update order status
	- Manage products: CRUD
### How to run this website?
1. Download or clone this project from GitHub
2. Unzip this project
3. Open XAMPP and accessing PHPMyAdmin
4. Import file `database.sql` in order to have a sample database
5. Open Eclipse IDE -> `File` -> `Open Projects from File System` -> `Directory` -> Choose this project `(clothes_shop)`
6. See the `Package Explorer` window (at the left) -> Right click on the project name `(clothes_shop)` -> `Run As` -> `Spring Boot App`
7. Open your browser -> Access `http://localhost:8080/`
### Sample accounts to sign in
1. **Customer account**
    - username: nknghiem
    - password: nknghiem
2. **Administrator account**
    - username: admin
    - password: admin
> Currently this program only supports **Vietnamese** language.
