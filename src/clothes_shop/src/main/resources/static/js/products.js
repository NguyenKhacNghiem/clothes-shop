let checkboxs = document.querySelectorAll("input[type=checkbox]")

checkboxs.forEach(checkbox => {
	checkbox.addEventListener("change", function() {
		getProducts(page);
	})
})

// Lấy danh sách sản phẩm khi trang được load lần đầu
let page = 1;
let totalPage = document.getElementById("total-page");
getProducts(page);

function getProducts(page) {
	// Lấy ra danh mục (catalog) hiện tại 
	let queryString = window.location.search;
	let urlParams = new URLSearchParams(queryString);
	let queryParams = "catalog=" + urlParams.get('catalog');
	
	// Lấy nội dung tìm kiếm
	let keyword = document.getElementById("search-content").value;
	queryParams += "&keyword=" + keyword;
			
	// Lọc theo thương hiệu (có 5 bộ lọc)
	for(let i=0;i<5;i++) 
		if(checkboxs[i].checked)
			queryParams += "&brand" + (i+1) + "=" + checkboxs[i].value;
		
	// Lọc theo giá tiền (có 4 bộ lọc)
	for(let i=5;i<9;i++) 
		if(checkboxs[i].checked)
			queryParams += "&price" + (i-5+1) + "=" + checkboxs[i].value;
			
	queryParams += "&page=" + page;
	
	fetch("/api/products?" + queryParams)
	.then(response => response.json())
	.then(json => showProducts(json)) 
}

function showProducts(json) {
	let pageNavigation = document.getElementById("page-navigation");
	let productList = document.getElementById("product-list");
	let products = "";
	
	// Danh sách sản phẩm được trả về bị trống
	if(json.data == null) {
		productList.innerHTML = "";
		pageNavigation.classList.add("d-none"); // ẩn khu vực lùi/ chuyển trang
		return;
	}
		
	json.data.forEach(p => {
		// Kiểm tra xem tên sản phẩm có quá dài không, nếu có chúng ta thay thế phần còn lại bằng dấu "..."
        let productName = p["name"];
        if(productName.length > 16)
            productName = productName.substring(0, 16) + "...";
            
		// Format lại định dạng của giá tiền. Vd: 1500000 -> 1.500.000
        let formatedPrice = (p["price"]).toLocaleString('en-US');
        formatedPrice = formatedPrice.replaceAll(",", ".");
        
		products += `
					<div class="col-3 mb-3" id="${p['id']}" onclick="viewProductInfo(this)">
							<div class="card px-0">
								<img class="card-img-top" src="./img/${p['image']}">
								<div class="card-body">
									<h5 class="card-title">${productName}</h5>
	 								<p class="card-text">${formatedPrice}đ</p>
									<button class="btn btn-warning w-100"><i class="fa-solid fa-circle-info"></i></button>
								</div>
							</div>
					</div>
					`
	})
	
	productList.innerHTML = products;
	
	// Hiện khu vực lùi/ chuyển trang + trang hiện tại + tổng số trang
    pageNavigation.classList.remove("d-none"); 
	totalPage.innerHTML = json.totalPage;
	
	if(page > totalPage.innerHTML - 0)
		page = totalPage.innerHTML - 0;
	
	if(page < 1)
		page = 1;
	
	document.getElementById("page").innerHTML = page;
}

function previousPage() {
    page--;

    if(page < 1)
        page = 1;

    getProducts(page);
    document.body.scrollTop = document.documentElement.scrollTop = 0;
}

function nextPage() {
    page++;

    if(page > totalPage.innerHTML - 0)
        page = totalPage.innerHTML - 0; // chuyển string sang int

    getProducts(page);
    document.body.scrollTop = document.documentElement.scrollTop = 0;
}

function searchProducts() {
	// Tạo hiệu ứng loading
    let loading = document.getElementsByClassName("loading")[0];
    
    loading.classList.remove("d-none");
    
    setTimeout(function() {
		loading.classList.add("d-none");
		getProducts(page);
	}, 1000)
}

// Xem thông tin chi tiết 1 sản phẩm
function viewProductInfo(product) {	
	window.location.href = "/product-info/" + product.id;
}
