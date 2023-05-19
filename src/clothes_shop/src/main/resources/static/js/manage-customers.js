function changeActiveOfCustomer(button) {
	let username = button.getAttribute("data-username");
		
	// Thay đổi trạng thái phía server
	fetch("/api/block-customer/" + username, {
		method: 'PUT'
	})
	.then(response => response.json())
	.then(json => showModal(json, button))	
}

function showModal(json, button) {
	document.getElementById("modal-body").innerHTML = json.message;
	$("#modal").modal("show");
	
	// Thay đổi trang thái phía giao diện nếu thành công
	if(json.code === 0) {
		// Khóa tài khoản
		if(button.innerHTML === '<i class="fa-solid fa-lock-open"></i>') {
			button.innerHTML = '<i class="fa-solid fa-lock"></i>';
			button.classList.remove("btn-outline-success");
			button.classList.add("btn-outline-danger");
		}
		// Mở khóa tài khoản
		else {
			button.innerHTML = '<i class="fa-solid fa-lock-open"></i>';
			button.classList.remove("btn-outline-danger");
			button.classList.add("btn-outline-success");
		}
	}
}

function changeTypeOfCustomer(icon) {
	let username = icon.getAttribute("data-username");
	
	// Thay đổi trang thái phía giao diện
	if(icon.style.color === "rgb(184, 115, 51)")
		icon.style.color = "rgb(192, 192, 195)";
	else if(icon.style.color === "rgb(192, 192, 195)")
		icon.style.color = "rgb(255, 215, 0)";
	else
		icon.style.color = "rgb(184, 115, 51)";
		
	// Thay đổi trạng thái phía server
	fetch("/api/change-type-customer/" + username, {
		method: 'PUT'
	})
}