// Dark theme
let isDarkTheme = false;

if(document.getElementsByTagName("body")[0].style.backgroundColor === "rgb(50, 50, 50)")
	isDarkTheme = true;

let itemWillBeDarkTheme = document.querySelectorAll(".dark-theme");

itemWillBeDarkTheme.forEach(i => {
	if(isDarkTheme)
		i.style.color = "white";
	else
		i.style.color = "black";
})