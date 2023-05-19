function updateStatusOrder(button) {
	let id = button.getAttribute("data-id");
		
	// Thay đổi trạng thái phía server
	fetch("/api/update-status-order/" + id, {
		method: 'PUT'
	})
	
	setTimeout(function() {
		window.location.reload();
	}, 500)
}