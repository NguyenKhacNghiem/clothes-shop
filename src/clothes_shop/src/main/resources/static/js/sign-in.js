// Tắt dialog "Confirm Form Resubmission" mặc định của trình duyệt khi người dùng refresh trang sau khi thêm 1 sản phẩm vào giỏ hàng
if (window.history.replaceState) {
  window.history.replaceState(null, null, window.location.href);
}

// Tạo hiệu ứng focus cho các thẻ input
let inputs = document.querySelectorAll(".form-control");
let icons = document.querySelectorAll(".input-group-text");

for(let i=0;i<inputs.length;i++) {
	inputs[i].addEventListener("focus", function() {
		inputs[i].style.border = "3px solid rgb(255, 193, 7)";
		inputs[i].style.borderLeft = "none";
		icons[i].style.border = "3px solid rgb(255, 193, 7)";
		icons[i].style.borderRight = "none"
	})
	
	inputs[i].addEventListener("focusout", function() {
		inputs[i].style.border = "";
		icons[i].style.border = "";
	})
}

// Quên mật khẩu
function showModalForgetPassword() {
	$("#modal").modal("show");
}

function forgetPassword() {
	// Hiện hiệu ứng loading
	let loading = document.getElementById("loading");
	loading.classList.remove("d-none");
	
	setTimeout(function() {
		loading.classList.add("d-none");
		
		let username = document.getElementById("username-in-forget-password");
		let phone = document.getElementById("phone-in-forget-password");
		
		fetch('/api/forget-password', {
	            method: 'POST',
	            body: new URLSearchParams({
		            'username': username.value,
		            'phone': phone.value
	    		})
        	})
        .then(response => response.json())
        .then(json => handleForgetPassword(json))
	}, 1000)
}

function handleForgetPassword(json) {
	let alert = document.getElementById("alert");
	
	// Tìm thấy mật khẩu
	if(json.code === 0) {
		alert.classList.remove("alert-danger");
		alert.classList.add("alert-success");
		
		alert.innerHTML = "Mật khẩu của bạn là: <b>" + json.data + "</b>";
		$("#alert").fadeIn(2000);
	}	
	// Không tìm thấy mật khẩu
	else {
		alert.classList.remove("alert-success");
		alert.classList.add("alert-danger");
		
		alert.innerHTML = json.message;
		
		$("#alert").fadeIn(2000);
		$("#alert").fadeOut(2000);
	}
}

// Xử lý sự kiện ẩn/ hiện mật khẩu khi nhấn vào icon con mắt
function clickedEye(eye) {
	let input = eye.parentNode.parentNode.nextSibling.nextSibling; // phải 2 lần nextSibling tại vì giữa div và input là ký tự xuống dòng
	
	// Hiện mật khẩu
	if(eye.getAttribute("class") === "eye fa-solid fa-eye-slash") {
		eye.classList.remove("fa-eye-slash");
		eye.classList.add("fa-eye");
		input.setAttribute("type", "text");
	}
	// Ẩn mật khẩu
	else {
		eye.classList.remove("fa-eye");
		eye.classList.add("fa-eye-slash");
		input.setAttribute("type", "password");
	}
}