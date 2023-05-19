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