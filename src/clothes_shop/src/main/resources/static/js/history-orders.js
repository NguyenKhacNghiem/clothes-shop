// Chỉnh màu cho từng đơn hàng ứng với trạng thái của nó
let statuses = document.querySelectorAll(".status");

statuses.forEach(status => {
	if(status.innerHTML === "Chờ xác nhận")
		status.style.backgroundColor = "rgb(255, 128, 128)";
	else if(status.innerHTML === "Đang vận chuyển")
		status.style.backgroundColor = "rgb(255, 255, 128)";
	else
		status.style.backgroundColor = "rgb(128, 255, 128)";
})

function getProductsInOrder(tr) {
	let orderId = tr.getAttribute("data-orderId");
	
	fetch("/api/products-in-order?orderId=" + orderId)
	.then(response => response.json())
	.then(json => showProductsInOrder(json))
}

function showProductsInOrder(json) {
	let tbody = document.getElementsByTagName("tbody")[1]; // tbody trong modal
	let trs = "";
	
	json.data.forEach(product => {
		// Format lại định dạng của price. Vd: 1500000 -> 1.500.000
        let formatedPrice = product.price.toLocaleString('en-US');
        formatedPrice = formatedPrice.replaceAll(",", ".");
        
		trs += `
				<tr>
					<td class="image-and-name" style="width: 55%">
						<img src="./img/${product.image}" style="width: 30%">
						<span>${product.name}</span>
					</td>
					<td style="width: 15%">${product.size}</td>
					<td style="width: 15%">${product.quantity}</td>
					<td style="width: 15%">${formatedPrice}đ</td>
				</tr>
				`
	})
	
	tbody.innerHTML = trs;
	$("#modal").modal("show");
}