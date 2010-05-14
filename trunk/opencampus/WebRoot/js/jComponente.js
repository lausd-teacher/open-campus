//Evalua (V-F) si un punto (X y Y) estan dentro del area del compunente e
//t,r,b,l : (opcional) margen para excluir al area del componente
function xHasPoint(e, x, y, t, r, b, l) {
	if (!xNum(t)) {
		t = r = b = l = 0;
	} else {
		if (!xNum(r)) {
			r = b = l = t;
		} else {
			if (!xNum(b)) {
				l = r;
				b = t;
			}
		}
	}
	var eX = xPageX(e), eY = xPageY(e);
	return (x >= eX + l && x <= eX + xWidth(e) - r && y >= eY + t && y <= eY + xHeight(e) - b);
}
//Obtiene la posición X del componente con respecto a la página.
function xPageX(e) {
	if (!(e = xGetElementById(e))) {
		return 0;
	}
	var x = 0;
	while (e) {
		if (xDef(e.offsetLeft)) {
			x += e.offsetLeft;
		}
		e = xDef(e.offsetParent) ? e.offsetParent : null;
	}
	return x;
}
//Obtiene la posición Y del componente con respecto a la página.
function xPageY(e) {
	if (!(e = xGetElementById(e))) {
		return 0;
	}
	var y = 0;
	while (e) {
		if (xDef(e.offsetTop)) {
			y += e.offsetTop;
		}
		e = xDef(e.offsetParent) ? e.offsetParent : null;
	}
//  if (xOp7) return y - document.body.offsetTop; // v3.14, temporary hack for opera bug 130324
	return y;
}
//Obtiene la posición X del componente con respecto a offsetParent.
function xOffsetLeft(e) {
	if (!(e = xGetElementById(e))) {
		return 0;
	}
	if (xDef(e.offsetLeft)) {
		return e.offsetLeft;
	} else {
		return 0;
	}
}
//Obtiene la posición Y del componente con respecto a offsetParent.
function xOffsetTop(e) {
	if (!(e = xGetElementById(e))) {
		return 0;
	}
	if (xDef(e.offsetTop)) {
		return e.offsetTop;
	} else {
		return 0;
	}
}
//Setea el contenido de un componente
function xInnerHtml(e, sHtml) {
	if (!(e = xGetElementById(e))) {
		return "";
	}
	if (xStr(e.innerHTML)) {
		if (xStr(sHtml)) {
			e.innerHTML = sHtml;
		} else {
			return e.innerHTML;
		}
	}
}
// Cambia la posición V y H de un componente
function xMoveTo(e, iX, iY) {
	xLeft(e, iX);
	xTop(e, iY);
}
// Cambia la posición Horizontal de un componente
function xLeft(e, iX) {
	if (!(e = xGetElementById(e))) {
		return 0;
	}
	var css = xDef(e.style);
	if (css && xStr(e.style.left)) {
		if (xNum(iX)) {
			e.style.left = iX + "px";
		} else {
			iX = parseInt(e.style.left);
			if (isNaN(iX)) {
				iX = 0;
			}
		}
	} else {
		if (css && xDef(e.style.pixelLeft)) {
			if (xNum(iX)) {
				e.style.pixelLeft = iX;
			} else {
				iX = e.style.pixelLeft;
			}
		}
	}
	return iX;
}
// Cambia la posición Vertical de un componente
function xTop(e, iY) {
	if (!(e = xGetElementById(e))) {
		return 0;
	}
	var css = xDef(e.style);
	if (css && xStr(e.style.top)) {
		if (xNum(iY)) {
			e.style.top = iY + "px";
		} else {
			iY = parseInt(e.style.top);
			if (isNaN(iY)) {
				iY = 0;
			}
		}
	} else {
		if (css && xDef(e.style.pixelTop)) {
			if (xNum(iY)) {
				e.style.pixelTop = iY;
			} else {
				iY = e.style.pixelTop;
			}
		}
	}
	return iY;
}
//Evalua que sea Definido
function xDef() {
	for (var i = 0; i < arguments.length; ++i) {
		if (typeof (arguments[i]) == "undefined") {
			return false;
		}
	}
	return true;
}
//Evalua que sea String
function xStr(s) {
	for (var i = 0; i < arguments.length; ++i) {
		if (typeof (arguments[i]) != "string") {
			return false;
		}
	}
	return true;
}
//Evalua que sea Numerico
function xNum(n) {
	for (var i = 0; i < arguments.length; ++i) {
		if (typeof (arguments[i]) != "number") {
			return false;
		}
	}
	return true;
}
//Muestra un componente
function xShow(e) {
	if (!(e = xGetElementById(e))) {
		return;
	}
	if (e.style && xDef(e.style.visibility)) {
		e.style.visibility = "visible";
	}
}
//Oculta un componente
function xHide(e) {
	if (!(e = xGetElementById(e))) {
		return;
	}
	if (e.style && xDef(e.style.visibility)) {
		e.style.visibility = "hidden";
	}
}
//Muestra un componente
function xShowD(e) {
	if (!(e = xGetElementById(e))) {
		return;
	}
	if (e.style && xDef(e.style.display)) {
		e.style.display = "";
	}
}
//Oculta un componente
function xHideD(e) {
	if (!(e = xGetElementById(e))) {
		return;
	}
	if (e.style && xDef(e.style.display)) {
		e.style.display = "none";
	}
}
//Cambia la visibilidad de un componente
function xChangeDisplay(e) {
	if (!(e = xGetElementById(e))) {
		return;
	}
	if (!e.style || !xDef(e.style.display)) {
		return;
	}
	(e.style.display == "none") ? xShowD(e) : xHideD(e);
}
//Cambia la visibilidad de un componente
function xChangeVisibility(e) {
	if (!(e = xGetElementById(e))) {
		return;
	}
	if (!e.style || !xDef(e.style.visibility)) {
		return;
	}
	(e.style.visibility == "hidden") ? xShow(e) : xHide(e);
}

//Elimina espacios a los costados
function trim(str) {
	return str.replace(/\s+$|^\s+/g, "");
}
//Elimina etiquetas HTML
function stripTags(str) {
	return str.replace(/<\/?[^>]+>/gi, "");
}
//Elimina el salto de linea o retorno de carro
function stripNewLine(str) {
	var entra = new Array("\n", "\r");
	var sale = new Array("", "");
	var oc = new String(str), nt = new String(""), nc;
	for (var q = 0; q < oc.length; q++) {
		nc = oc.charAt(q);
		for (var w = 0; w < entra.length; w++) {
			if (oc.charAt(q) == entra[w]) {
				nc = sale[w];
				break;
			}
		}
		nt += nc;
	}
	delete oc, nt;
	return nt;
}

//Valida contenido del texto enriquecido
String.prototype.hasContenido = function () {
	if(0<this.stripTags().stripScripts().strip().replace(/&nbsp;/g,"").stripNewLine().strip().length){
		return true;
	}
	return  false;
};

//Elimina espacios en blanco a los extremos
String.prototype.trim = function () {
	return this.replace(/\s+$|^\s+/g, "");
};

//Elimina el salto de linea o retorno de carro
String.prototype.stripNewLine = function () {
	var entra = new Array("\n", "\r");
	var sale = new Array("", "");
	var oc = new String(this), nt = new String(""), nc;
	for (var q = 0; q < oc.length; q++) {
		nc = oc.charAt(q);
		for (var w = 0; w < entra.length; w++) {
			if (oc.charAt(q) == entra[w]) {
				nc = sale[w];
				break;
			}
		}
		nt += nc;
	}
	delete oc, nt;
	return nt;
};

//Elimina exceso de espacios entre palabras
String.prototype.stripWithSpace = function () {
	var word=this.trim();
	while(word.indexOf("  ") != -1){
		word = word.replace("  "," ");
	}
	return word;
}

//Valida si esta vacio
String.prototype.isEmpty = function(){
	var _d6=/^\s+$/;
	return ((this==null)||(this.length==0)||_d6.test(this));
}

//Valida letras (a-z A-Z)
String.prototype.isAlpha = function(){
	var _d8=/^[a-zA-Z\s]+$/;
	return this.isEmpty()||_d8.test(this);
}

//Valida caracteres alfanuméricos (a-z 0-9).
String.prototype.isAlphaNum = function(){
	var _da=/^[\w\s]+$/;
	return this.isEmpty()||_da.test(this);
}

//Valida enteros
String.prototype.isInteger = function(s){
	var _e0=/^[+]?\d+$/;
	return this.isEmpty()||_e0.test(this);
}

//Valida valor decimal
String.prototype.isFloat = function(){
	return this.isEmpty()||!isNaN(parseFloat(this));
}
//Ingresar solo enteros onKeyPress="return soloNumeros(event)"
function soloNumeros(evt){	
	// NOTE: Backspace = 8, Enter = 13, '0' = 48, '9' = 57	
	var key = window.Event ? evt.which : evt.keyCode;	
	return (key <= 13 || (key >= 48 && key <= 57));
}

//Ignora caracteres especiales
function decodeResponse(response) {
	return unescape(response).replace(/\+/gi, " ");
}
//Obtiene un componente por su ID
function xGetElementById(e) {
	if (typeof (e) != "string") {
		return e;
	}
	if (document.getElementById) {
		e = document.getElementById(e);
	} else {
		if (document.all) {
			e = document.all[e];
		} else {
			e = null;
		}
	}
	return e;
}
//Convierte un caracter a Hexadecimal
function toHex(c) {
	var claveHex = "";
	var array = c.split("");
	for (i = 0; i < array.length; i = i + 1) {
		claveHex += "%" + array[i].charCodeAt(0).toString(16);
	}
	return claveHex;
}
// Cambio de ancho y alto a un Componente
function xResizeTo(e, uW, uH) {
	xWidth(e, uW);
	xHeight(e, uH);
}
// Cambia el ancho o lo obtine (si no existe uW) a un Componente
function xWidth(e, uW) {
	if (!(e = xGetElementById(e))) {
		return 0;
	}
	if (xNum(uW)) {
		if (uW < 0) {
			uW = 0;
		} else {
			uW = Math.round(uW);
		}
	} else {
		uW = -1;
	}
	var css = xDef(e.style);
	if (css && xDef(e.offsetWidth) && xStr(e.style.width)) {
		if (uW >= 0) {
			xSetCW(e, uW);
		}
		uW = e.offsetWidth;
	} else {
		if (css && xDef(e.style.pixelWidth)) {
			if (uW >= 0) {
				e.style.pixelWidth = uW;
			}
			uW = e.style.pixelWidth;
		}
	}
	return uW;
}
// Cambia el alto o lo obtine (si no existe uH) a un Componente
function xHeight(e, uH) {
	if (!(e = xGetElementById(e))) {
		return 0;
	}
	if (xNum(uH)) {
		if (uH < 0) {
			uH = 0;
		} else {
			uH = Math.round(uH);
		}
	} else {
		uH = -1;
	}
	var css = xDef(e.style);
	if (css && xDef(e.offsetHeight) && xStr(e.style.height)) {
		if (uH >= 0) {
			xSetCH(e, uH);
		}
		uH = e.offsetHeight;
	} else {
		if (css && xDef(e.style.pixelHeight)) {
			if (uH >= 0) {
				e.style.pixelHeight = uH;
			}
			uH = e.style.pixelHeight;
		}
	}
	return uH;
}
//Obtiene el valor de un estilo requerido para un Componente (Get Current Style)
function xGetCS(ele, sP) {
	return parseInt(document.defaultView.getComputedStyle(ele, "").getPropertyValue(sP));
}
//Cambia de Ancho a un componente
function xSetCW(ele, uW) {
	var pl = 0, pr = 0, bl = 0, br = 0;
	if (xDef(document.defaultView) && xDef(document.defaultView.getComputedStyle)) {
		pl = xGetCS(ele, "padding-left");
		pr = xGetCS(ele, "padding-right");
		bl = xGetCS(ele, "border-left-width");
		br = xGetCS(ele, "border-right-width");
	} else {
		if (xDef(ele.currentStyle, document.compatMode)) {
			if (document.compatMode == "CSS1Compat") {
				pl = parseInt(ele.currentStyle.paddingLeft);
				pr = parseInt(ele.currentStyle.paddingRight);
				bl = parseInt(ele.currentStyle.borderLeftWidth);
				br = parseInt(ele.currentStyle.borderRightWidth);
			}
		} else {
			if (xDef(ele.offsetWidth, ele.style.width)) { // ?
				ele.style.width = uW + "px";
				pl = ele.offsetWidth - uW;
			}
		}
	}
	if (isNaN(pl)) {
		pl = 0;
	}
	if (isNaN(pr)) {
		pr = 0;
	}
	if (isNaN(bl)) {
		bl = 0;
	}
	if (isNaN(br)) {
		br = 0;
	}
	var cssW = uW - (pl + pr + bl + br);
	if (isNaN(cssW) || cssW < 0) {
		return;
	} else {
		ele.style.width = cssW + "px";
	}
}
//Cambia de Alto a un componente
function xSetCH(ele, uH) {
	var pt = 0, pb = 0, bt = 0, bb = 0;
	if (xDef(document.defaultView) && xDef(document.defaultView.getComputedStyle)) {
		pt = xGetCS(ele, "padding-top");
		pb = xGetCS(ele, "padding-bottom");
		bt = xGetCS(ele, "border-top-width");
		bb = xGetCS(ele, "border-bottom-width");
	} else {
		if (xDef(ele.currentStyle, document.compatMode)) {
			if (document.compatMode == "CSS1Compat") {
				pt = parseInt(ele.currentStyle.paddingTop);
				pb = parseInt(ele.currentStyle.paddingBottom);
				bt = parseInt(ele.currentStyle.borderTopWidth);
				bb = parseInt(ele.currentStyle.borderBottomWidth);
			}
		} else {
			if (xDef(ele.offsetHeight, ele.style.height)) { // ?
				ele.style.height = uH + "px";
				pt = ele.offsetHeight - uH;
			}
		}
	}
	if (isNaN(pt)) {
		pt = 0;
	}
	if (isNaN(pb)) {
		pb = 0;
	}
	if (isNaN(bt)) {
		bt = 0;
	}
	if (isNaN(bb)) {
		bb = 0;
	}
	var cssH = uH - (pt + pb + bt + bb);
	if (isNaN(cssH) || cssH < 0) {
		return;
	} else {
		ele.style.height = cssH + "px";
	}
}
// Obtiene el ancho del navegador
function xClientWidth() {
	var w = 0;
	/*if(xOp5or6) w=window.innerWidth;
  else */
	if (!window.opera && document.documentElement && document.documentElement.clientWidth) {
		w = document.documentElement.clientWidth;
	} else {
		if (document.body && document.body.clientWidth) {
			w = document.body.clientWidth;
		} else {
			if (xDef(window.innerWidth, window.innerHeight, document.height)) {
				w = window.innerWidth;
				if (document.height > window.innerHeight) {
					w -= 16;
				}
			} else {
				w = window.innerWidth;
			}
		}
	}
	return w;
}
// Obtiene el alto del navegador
function xClientHeight() {
	var h = 0;
	/*if(xOp5or6) h=window.innerHeight;
  else */
	if (!window.opera && document.documentElement && document.documentElement.clientHeight) {
		h = document.documentElement.clientHeight;
	} else {
		if (document.body && document.body.clientHeight) {
			h = document.body.clientHeight;
		} else {
			if (xDef(window.innerWidth, window.innerHeight, document.width)) {
				h = window.innerHeight;
				if (document.width > window.innerWidth) {
					h -= 16;
				}
			} else {
				w = window.innerHeight;
			}
		}
	}
	return h;
}

// Posicion Horizontal del Scroll (e:elemento (default->document) , bWin:ventana (default->explorador))
function xScrollLeft(e,bWin){
	var offset=0;
	if(!xDef(e)||bWin||e==document||e.tagName.toLowerCase()=='html'||e.tagName.toLowerCase()=='body'){
		var w=window;
		if(bWin&&e)w=e;
		if(w.document.documentElement&&w.document.documentElement.scrollLeft)offset=w.document.documentElement.scrollLeft;
		else if(w.document.body&&xDef(w.document.body.scrollLeft))offset=w.document.body.scrollLeft;
	}else{
		e=xGetElementById(e);
		if(e&&xNum(e.scrollLeft))offset=e.scrollLeft;
	}
	return offset;
}
// Posicion Vertical del Scroll (e:elemento (default->document) , bWin:ventana (default->explorador))
function xScrollTop(e,bWin){
	var offset=0;
		if(!xDef(e)||bWin||e==document||e.tagName.toLowerCase()=='html'||e.tagName.toLowerCase()=='body'){
		var w=window;
		if(bWin&&e)w=e;
		if(w.document.documentElement&&w.document.documentElement.scrollTop)offset=w.document.documentElement.scrollTop;
		else if(w.document.body&&xDef(w.document.body.scrollTop))offset=w.document.body.scrollTop;
	}else{
		e=xGetElementById(e);
		if(e&&xNum(e.scrollTop))offset=e.scrollTop;
	}
	return offset;
}

//Detecta la version del Browser (xBrowser.getName(), xBrowser.getVersion() y xBrowser.getOS())
//valor devuelto: de los atributos identity
var xBrowser = {
	getName: function () {
		return this.searchString(this.dataBrowser) || "An unknown browser";
	},
	getVersion: function () {
		this.searchString(this.dataBrowser);
		return this.searchVersion(navigator.userAgent)
			|| this.searchVersion(navigator.appVersion)
			|| "an unknown version";
	},
	getOS: function () {
		return this.searchString(this.dataOS) || "an unknown OS";
	},
	searchString: function (data) {
		for (var i=0;i<data.length;i++)	{
			var dataString = data[i].string;
			var dataProp = data[i].prop;
			this.versionSearchString = data[i].versionSearch || data[i].identity;
			if (dataString) {
				if (dataString.indexOf(data[i].subString) != -1)
					return data[i].identity;
			}
			else if (dataProp)
				return data[i].identity;
		}
	},
	searchVersion: function (dataString) {
		var index = dataString.indexOf(this.versionSearchString);
		if (index == -1) return;
		return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
	},
	dataBrowser: [
		{ 	string: navigator.userAgent,
			subString: "OmniWeb",
			versionSearch: "OmniWeb/",
			identity: "OmniWeb"
		},
		{
			string: navigator.vendor,
			subString: "Apple",
			identity: "Safari"
		},
		{
			prop: window.opera,
			identity: "Opera"
		},
		{
			string: navigator.vendor,
			subString: "iCab",
			identity: "iCab"
		},
		{
			string: navigator.vendor,
			subString: "KDE",
			identity: "Konqueror"
		},
		{
			string: navigator.userAgent,
			subString: "Firefox",
			identity: "Firefox"
		},
		{
			string: navigator.vendor,
			subString: "Camino",
			identity: "Camino"
		},
		{		// for newer Netscapes (6+)
			string: navigator.userAgent,
			subString: "Netscape",
			identity: "Netscape"
		},
		{
			string: navigator.userAgent,
			subString: "MSIE",
			identity: "Explorer",
			versionSearch: "MSIE"
		},
		{
			string: navigator.userAgent,
			subString: "Gecko",
			identity: "Mozilla",
			versionSearch: "rv"
		},
		{ 		// for older Netscapes (4-)
			string: navigator.userAgent,
			subString: "Mozilla",
			identity: "Netscape",
			versionSearch: "Mozilla"
		}
	],
	dataOS : [
		{
			string: navigator.platform,
			subString: "Win",
			identity: "Windows"
		},
		{
			string: navigator.platform,
			subString: "Mac",
			identity: "Mac"
		},
		{
			string: navigator.platform,
			subString: "Linux",
			identity: "Linux"
		}
	]

};

/*
 * DEPRECATED: REEMPLAZADO POR date.js
 * the date format prototype
 */
/* // a global month names array
var gsMonthNames = new Array(
	'January',
	'February',
	'March',
	'April',
	'May',
	'June',
	'July',
	'August',
	'September',
	'October',
	'November',
'December'
);
// a global day names array
var gsDayNames = new Array(
	'Sunday',
	'Monday',
	'Tuesday',
	'Wednesday',
	'Thursday',
	'Friday',
	'Saturday'
);
Date.prototype.format = function(f)
{
    if (!this.valueOf())
        return ' ';

    var d = this;

    return f.replace(/(yyyy|mmmm|mmm|mm|dddd|ddd|dd|hh|nn|ss|a\/p)/gi,
        function($1)
        {
            switch ($1.toLowerCase())
            {
            case 'yyyy': return d.getFullYear();
            case 'mmmm': return gsMonthNames[d.getMonth()];
            case 'mmm':  return gsMonthNames[d.getMonth()].substr(0, 3);
            case 'mm':   return d.getMonth().zf(2);
            case 'dddd': return gsDayNames[d.getDay()];
            case 'ddd':  return gsDayNames[d.getDay()].substr(0, 3);
            case 'dd':   return d.getDate().zf(2);
            case 'hh':   return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case 'nn':   return d.getMinutes().zf(2);
            case 'ss':   return d.getSeconds().zf(2);
            case 'a/p':  return d.getHours() < 12 ? 'am' : 'pm';
            }
        }
    );
}

Number.prototype.zf = function(f)
{
    if (!this.valueOf())
        return ' ';

    var len = (this+'').length;
    var string = this;
    
    for(var index=len; index<f; index++) {
    	string = '0' + string; 
    }

    return string;
}*/
//Deshabilita la tecla ctrl + algun boton
function disableCtrlKeyCombination(s_input, e) {
	var key;
	var isCtrl;
	if (window.event) {
		key = window.event.keyCode;     //IE
		if (window.event.ctrlKey) {
			isCtrl = true;
		} else {
			isCtrl = false;
		}
	} else {
		key = e.which;     //firefox
		if (e.ctrlKey) {
			isCtrl = true;
		} else {
			isCtrl = false;
		}
	}
	if (isCtrl) {
		if ("c" == String.fromCharCode(key).toLowerCase()) {
			return false;
		}
		if ("v" == String.fromCharCode(key).toLowerCase()) {
			s_input.value = "";
			return false;
		}
	}
	return true;
}

// Obtiene la dirección del context: http://127.0.0.1:8080/campus
function xGetContextPath() {
	var context = "";
	var loct = document.location;
	var array = loct.href.split("/");
	try {
		context = array[0] + "/" + array[1] + "/" + array[2] + "/" + array[3];
	}
	catch (e) {
	}
	return context;
}
//Abre un popUp con el contenido de otro elemento solo para imprimir
function xPrint(nombre)
{
  var ficha = document.getElementById(nombre);
  var ventimp = window.open(' ', 'popimpr', 'width=500,height=400,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=0,resizable=0,top=0,left=0');
  var html_version = '<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">';
  var css = '<link href="'+xGetContextPath()+'/estilos/estilos.css" rel="stylesheet" type="text/css" />';
  ventimp.document.write( html_version+css+ficha.innerHTML );
  ventimp.document.close();
  ventimp.print( );
  ventimp.close();
}

//Abre un popUp con el contenido de otro elemento solo para imprimir
function xPrintURL(url)
{
  var ventimp = window.open(url, 'popimpr', 'width=550,height=400,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,top=0,left=0');
    ventimp.document.close();
  ventimp.print( );
  //ventimp.close();
}

// Crer Elementos Wipe
function xCreate(element) {
	return document.createElement(element);
}
// new Ajax
function nuevoAjax() {
	/* Crea el objeto AJAX. Esta funcion es generica para cualquier utilidad de este tipo, por
	lo que se puede copiar tal como esta aqui */
	var xmlhttp = false;
	try { 
		// Creacion del objeto AJAX para navegadores no IE
		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
	}
	catch (e) {
		try { 
			// Creacion del objet AJAX para IE 
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		catch (E) {
			xmlhttp = false;
		}
	}
	if (!xmlhttp && typeof XMLHttpRequest != "undefined") {
		xmlhttp = new XMLHttpRequest();
	}
	return xmlhttp;
}

/* Ajax */
function XHConn()
{
  var xmlhttp, bComplete = false;
  try { xmlhttp = new ActiveXObject("Msxml2.XMLHTTP"); }
  catch (e) { try { xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); }
  catch (e) { try { xmlhttp = new XMLHttpRequest(); }
  catch (e) { xmlhttp = false; }}}
  if (!xmlhttp) return null;
  this.connect = function(sURL, sMethod, sVars, fnDone)
  {
    if (!xmlhttp) return false;
    bComplete = false;
    sMethod = sMethod.toUpperCase();

    try {
     if (sMethod == "GET")
      {
        xmlhttp.open(sMethod, sURL+"?"+sVars, true);
        sVars = "";
      }
      else
      {
        xmlhttp.open(sMethod, sURL, true);
        xmlhttp.setRequestHeader("Method", "POST "+sURL+" HTTP/1.1");
        xmlhttp.setRequestHeader("Content-Type",
          "application/x-www-form-urlencoded");
      }
      xmlhttp.onreadystatechange = function(){
        if (xmlhttp.readyState == 4 && !bComplete)
        {
          bComplete = true;
          fnDone(xmlhttp);
        }};
      xmlhttp.send(sVars);
    }
    catch(z) { return false; }
    return true;
  };
  return this;
}