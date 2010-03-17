
var id_menu = null;
var enItem = false;
var navegador = false;
var idtime = null;
/*
Para obtener el tipo de navegador que esta utilizando el usuario
*/
if (navigator.userAgent.toLowerCase().indexOf("msie 6.0") != -1 || navigator.userAgent.toLowerCase().indexOf("msie 7.0") != -1) {
	navegador = true;
}
/*
Este metodo se utiliza cuando se halla escojido una opcion
*/
function esconder_menu_inmediato() {
	try {
		enItem = false;
		var panel = document.getElementById(this.id_menu);
		panel.style.visibility = "hidden";
	}
	catch (e) {
	}
}
/*
Para mostrar el menu con sus items
*/
function ver_menu(idmenu) {
	var panel;
	if (null != this.id_menu && idmenu != this.id_menu) {
		try {
			panel = document.getElementById(id_menu);
			panel.style.visibility = "hidden";
		}
		catch (e) {
		}
	}
	enItem = true;
	this.id_menu = idmenu;
	panel = document.getElementById(this.id_menu);
	panel.style.visibility = "visible";
	panel.style.marginLeft = "-80px";
	if (navegador) {
		panel.style.marginTop = "15px";
	} else {
		panel.style.marginTop = "-3px";
	}
}
/*
Cuando el mouse sale de un item del menu
*/
function esconder_itemmenu(item) {
	enItem = false;
	item.className = "menu_subitem_out";
}
/*
Cuando el mouse entra a un item del menu
*/
function ver_itemmenu(item) {
	enItem = true;
	item.className = "menu_subitem_over";
}
/*
El click derecho del mouse
*/
//var message = "";
//function clickIE() {
//	if (document.all) {
//		(message);
//		return false;
//	}
//}
//function clickNS(e) {
//	if (document.layers || (document.getElementById && !document.all)) {
//		if (e.which == 2 || e.which == 3) {
//			(message);
//			return false;
//		} 
//		else {
//			esconder_menu_inmediato();
//			return false;
//		}
//	}
//	if (navegador && event.button == 1) {
//		esconder_menu_inmediato();
//		return false;
//	}
//	return true;
//}
//if (document.layers) {
//	document.captureEvents(Event.MOUSEDOWN);
//	document.onmousedown = clickNS;	
//} else {
//	document.onmouseup = clickNS;
//	document.oncontextmenu = clickIE;
//}
//document.oncontextmenu = new Function("return false");


var message = "";
function clickIE() {
	if (document.all) {
		(message);
		return false;
	}
}
function clickNS(e) {
	if (document.layers || (document.getElementById && !document.all)) {
		if (e.which == 2 || e.which == 3) {
			(message);
			return false;
		} 
		else {
			esconder_menu_inmediato();
			return false;
		}
	}
	if (navegador && event.button == 1) {
		esconder_menu_inmediato();
		return false;
	}
	return true;
}
if (document.layers) {
	document.captureEvents(Event.MOUSEDOWN);
	document.onmousedown = clickNS;	
} else {
	document.onmouseup = clickNS;
	document.oncontextmenu = clickIE;
}
document.oncontextmenu = new Function("return false");




