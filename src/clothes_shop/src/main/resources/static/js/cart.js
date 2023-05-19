// Giảm số lượng sản phẩm trong giỏ hàng
function decrease(button) {
	let btnId = button.id; 			// Vd: d123
	let index = btnId.substring(1); // Vd: 123 
	let quantity = document.getElementById("q" + index).value;
		
	if(quantity === "1")
		return;
	
	let updateProductInCartDto = {
		productId: button.getAttribute("data-pid"),
		cartId: button.getAttribute("data-cid"),
		size: button.getAttribute("data-size"),
		operation: "decrease"
	};
	
	fetch('/cart', {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(updateProductInCartDto)
	})
	
	refresh();
}

// Tăng số lượng sản phẩm trong giỏ hàng
function increase(button) {
	let updateProductInCartDto = {
		productId: button.getAttribute("data-pid"),
		cartId: button.getAttribute("data-cid"),
		size: button.getAttribute("data-size"),
		operation: "increase"
	};
	
	fetch('/cart', {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(updateProductInCartDto)
	})
	
	refresh();
}

// Xóa sản phẩm trong giỏ hàng
function deleteProductInCart(icon, isDeleteOne) {
	let deleteProductInCartDto = {
		productId: icon.getAttribute("data-pid"),
		cartId: icon.getAttribute("data-cid"),
		size: icon.getAttribute("data-size"),
	};
	
	fetch('/cart', {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(deleteProductInCartDto)
	})
	
	if(isDeleteOne)
		refresh();
}

// Thanh toán các sản phẩm có trong giỏ hàng
function checkout(button) {
	// Tạo 1 đơn hàng
	fetch('/api/add-order', {
		method: 'POST',
		body: new URLSearchParams({ 
            'id': "",
            'username': button.getAttribute("data-username"),
            'totalPrice': button.getAttribute("data-totalPrice"),
            'date': "",
            'status': "Chờ xác nhận"
        })
	})
	.then(response => response.json())
	.then(json => createDetailOrder(json))
}

function createDetailOrder(json) {
	let orderId = json.orderId;
	let productIds = document.querySelectorAll(".image-and-name");
	let quantites = document.querySelectorAll(".quantity");
	let sizes = document.querySelectorAll(".size");
	
	for(let i=0;i<productIds.length;i++) {		
		fetch('/api/add-detail-order', {
			method: 'POST',
			body: new URLSearchParams({ 
	            'productId': productIds[i].getAttribute("data-pid"),
	            'orderId': orderId,
	            'quantity': quantites[i].value,
	            'size': sizes[i].innerHTML
	        })
		})
	}
	
	// Xóa tất cả sản phẩm trong giỏ hàng sau khi thanh toán
	let iconList = document.getElementsByClassName("fa-circle-x");
	
	for(let i=0; i<iconList.length ; i++)
		deleteProductInCart(iconList[i], false);
	
	setTimeout(function() {
		window.location.href = "/history-orders";
	}, 500)
}

function refresh() {
	setTimeout(function() {
		window.location.reload();
	}, 500)
}

// Xem thông tin chi tiết 1 sản phẩm
function viewProductInfo(product) {	
	window.location.href = "/product-info/" + product.getAttribute("data-pid");
}