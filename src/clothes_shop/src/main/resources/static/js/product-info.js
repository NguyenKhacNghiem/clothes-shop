// Tạo hiệu ứng mờ dần cho thông báo thêm sản phẩm vào giỏ hàng thành công
setTimeout(function() {
    $("#success").fadeOut();
}, 500);

// Tắt dialog "Confirm Form Resubmission" mặc định của trình duyệt khi người dùng refresh trang sau khi thêm 1 sản phẩm vào giỏ hàng
if (window.history.replaceState) {
  window.history.replaceState(null, null, window.location.href);
}

// Phần loop slider cho các sản phẩm liên quan
let imageWrapper = document.querySelector('.image-wrapper')
let imageItems = document.querySelectorAll('.image-wrapper > *')
let imageLength = imageItems.length
let perView = 4
let totalScroll = 0
let delay = 2000

imageWrapper.style.setProperty('--per-view', perView)
for(let i = 0; i < perView; i++) {
  imageWrapper.insertAdjacentHTML('beforeend', imageItems[i].outerHTML)
}

let autoScroll = setInterval(scrolling, delay)

function scrolling() {
  totalScroll++
  if(totalScroll == imageLength + 1) {
    clearInterval(autoScroll)
    totalScroll = 1
    imageWrapper.style.transition = '0s'
    imageWrapper.style.left = '0'
    autoScroll = setInterval(scrolling, delay)
  }
  let widthEl = document.querySelector('.image-wrapper > :first-child').offsetWidth + 24
  imageWrapper.style.left = `-${totalScroll * widthEl}px`
  imageWrapper.style.transition = '.3s'
}