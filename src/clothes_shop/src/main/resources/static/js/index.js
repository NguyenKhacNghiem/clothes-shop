// Xem sản phẩm của từng danh mục
function viewCatalog(catalog) {
	let catalogName = catalog.getAttribute("data-name");
	window.location.href = "products?catalog=" + catalogName;
}

// Tạo loop slider cho các thương hiệu nổi bật
let imageWrapper = document.querySelector('.image-wrapper')
let imageItems = document.querySelectorAll('.image-wrapper > *')
let imageLength = imageItems.length
let perView = 5
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