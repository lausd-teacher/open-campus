function verToolTip(nombre, lugar) {
	try {
		if(document.getElementsByTagName("body").item(0) != null){
		
			var s_div = document.getElementById("tooltip");
			if (null == s_div) {
				s_div = document.createElement("div");
				s_div["id"] = "tooltip";
				var s_body = document.getElementsByTagName("body").item(0);
				s_body.appendChild(s_div);
			}
			if (nombre.length > 1) {
				xInnerHtml("tooltip", nombre);
				xMoveTo("tooltip", xPageX(lugar) + 15, xPageY(lugar) + 15);
				s_div.style["display"] = "block";
				xShow("tooltip");
			}
		
		}
	}
	catch (e) {
		//window.alert(e);
	}
	
}
function ocultarToolTip() {
	try {
		xHide("tooltip");
	}
	catch (e) {
	}
}
