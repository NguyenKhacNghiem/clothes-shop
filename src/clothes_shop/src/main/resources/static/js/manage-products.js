// Hiện tên của file sau khi upload
$(".custom-file-input").on("change", function() {
	let fileName = $(this).val().split("\\").pop();
	$(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});

// Thêm sản phẩm
let submit = document.getElementById("submit");
let form = document.getElementById("form");

submit.addEventListener("click", function(event) {
	event.preventDefault();

	let formData = new FormData(form);

	fetch("/api/save-product", {
		method: 'POST',
		body: formData
	})
	.then(data => data.json())
	.then(json => handleSave(json))
})

function handleSave(json) {	
	if(json.code === 0) {
		// Tạo hiệu ứng loading
	    let loading = document.getElementsByClassName("loading")[0];
	    loading.classList.remove("d-none");
	    
	    setTimeout(function() {
			window.location.href = "/manage-products";
		}, 1000)
	}
	else {
		// Hiển thị nội dung lỗi cho modal thông báo
		let modalBody = document.getElementsByClassName("modal-body")[0];
		modalBody.innerHTML = json.message;
		
		// Hiện modal lên
		$('#save-modal').modal('show');
	}
}

// Sửa sản phẩm
function updateProduct(button) {
	let btnId = button.id;
	let id = btnId.substring(1);
	
	window.location.href = "/manage-products/" + id;
}

// Xóa sản phẩm
let deletedId;

function showModalDelete(button) {
	let btnId = button.id;
	let id = btnId.substring(1);
	
	// Hiện modal thông báo lên
	document.getElementById("delete-confirm-content").innerHTML = "Xác nhận xóa sản phẩm có <b>" + "ID = " + id + "</b>?";
	$('#delete-modal').modal('show');
	
	deletedId = id; // gán id cho deletedId. Vì deletedId là biến toàn cục nên sẽ sử dụng được cho các hàm bên dưới
}

function deleteProduct() {
	fetch("/api/delete-product/" + deletedId, {
		method: "DELETE"
	})
	
	let waiting = document.getElementById("waiting");
	waiting.classList.remove("d-none");
	
	setTimeout(function() {
		window.location.href = "/manage-products";
	}, 1000);
}

// Sự kiện khi người dùng nhấn nút "Hủy"
function cancel() {
	window.location.href = "/manage-products";
}