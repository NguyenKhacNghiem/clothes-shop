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

// Đổi thông tin cá nhân
let submit = document.getElementById("submit");

submit.addEventListener("click", function(event) {
	event.preventDefault();
	
	let username = document.getElementById("username");
	let fullname = document.getElementById("fullname");
	let phone = document.getElementById("phone");
	let address = document.getElementById("address");
	
	if(fullname.value === "" || phone.value === "" || address.value === "") 
		showError("Vui lòng nhập đầy đủ thông tin");
	else if(!validatePhone(phone.value)) 
		showError("Số điện thoại không hợp lệ");
	else {
		let loading = document.getElementsByClassName("loading")[0];
		loading.classList.remove("d-none");
		
        setTimeout(function() {
			loading.classList.add("d-none");
			
			document.getElementById("navbardrop-customer").innerHTML = fullname.value; // Cập nhật lại fullname trên nav bar
			
			fetch('/api/change-info-customer', {
	            method: 'POST',
	            body: new URLSearchParams({
		            'username': username.value,
		            'fullname': fullname.value,
		            'phone': phone.value,
		            'address': address.value
	    		})
        	})
		}, 1000)
	}
})

function validatePhone(phone) {
    var phoneno = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;

    if (phone.match(phoneno))
        return true;
    else
        return false;
}

function showError(error) {
	document.getElementsByClassName("modal-body")[0].innerHTML = error;
	$("#modal").modal("show");
}

// Đổi mật khẩu
let oldPassword = document.getElementById("password");
let newPassword = document.getElementById("new-password");
let confirmNewPassword = document.getElementById("confirm-new-password");
		
function showModalChangePassword() {
	$("#modal-change-password").modal("show");
}

function changePassword() {
	let username = document.getElementById("username");
	
	// Hiện hiệu ứng loading
	let loading = document.getElementById("loading");
	loading.classList.remove("d-none");
	
	setTimeout(function() {
		loading.classList.add("d-none");
		
		fetch('/api/change-password', {
	            method: 'POST',
	            body: new URLSearchParams({
					'username': username.value,
		            'oldPassword': oldPassword.value,
		            'newPassword': newPassword.value,
		            'confirmNewPassword': confirmNewPassword.value
	    		})
        	})
        .then(response => response.json())
        .then(json => handleChangePassword(json))
	}, 1000)
}

function handleChangePassword(json) {
	let alert = document.getElementById("alert");
	
	// Đổi mật khẩu thành công
	if(json.code === 0) {
		alert.classList.remove("alert-danger");
		alert.classList.add("alert-success");
	}	
	// Đổi mật khẩu thất bại
	else {
		alert.classList.remove("alert-success");
		alert.classList.add("alert-danger");	
	}
	
	$("#alert").fadeIn(2000);
	$("#alert").fadeOut(2000);
	alert.innerHTML = json.message;
	
	if(json.code === 0) {
		setTimeout(function() {
			$("#modal-change-password").modal("hide");
		}, 2000)
		
		// Xóa hết dữ liệu đã nhập trong modal đổi mật khẩu khi modal này bị đóng
		oldPassword.value = "";
		newPassword.value = "";
		confirmNewPassword.value = "";
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