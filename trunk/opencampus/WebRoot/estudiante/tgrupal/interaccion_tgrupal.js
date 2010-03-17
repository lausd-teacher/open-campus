// ***** Comun *****
function nuevoAjax()
{ 
	/* Crea el objeto AJAX. Esta funcion es generica para cualquier utilidad de este tipo, por
	lo que se puede copiar tal como esta aqui */
	var xmlhttp=false; 
	try 
	{ 
		// Creacion del objeto AJAX para navegadores no IE
		xmlhttp=new ActiveXObject("Msxml2.XMLHTTP"); 
	}
	catch(e)
	{ 
		try
		{ 
			// Creacion del objet AJAX para IE 
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP"); 
		} 
		catch(E) { xmlhttp=false; }
	}
	if (!xmlhttp && typeof XMLHttpRequest!="undefined") { xmlhttp=new XMLHttpRequest(); } 

	return xmlhttp; 
}

var fadeInTmp = null;
function verIntegrantes(){

    //Entrada
	
	//Salida
	
	ajax = nuevoAjax();
	
	ajax.open("POST", xGetContextPath()+"/aulavirtual/tgrupal/VerIntegrantes.action", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send(null);
	
	//************ Transparencia *********************//
	var trans = xGetElementById("transparency");
	trans.setAttribute('style',trans.getAttribute('style')+'-moz-opacity:.0;');
	trans.style.filter = 'alpha(opacity=0)';
	
	var alto = xClientHeight();
	if(alto < xHeight('pop_up')+10){
		alto = xHeight('pop_up')+10;
	}
	xResizeTo('transparency',xClientWidth(),alto);
	
	fadeInTmp = setInterval(fadeIn,10);
	
	//*************************************************//
	
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			if(ajax.responseText !== ""){
				var xml = ajax.responseXML;
				var list = xGetElementById('list_integrantes');
				list.innerHTML = '';
				for (x = 0; x < xml.getElementsByTagName("integrante").length; x = x + 1) {
					var nombre = xml.getElementsByTagName("integrante")[x].childNodes[0].nodeValue;	
					var span = xCreate('span');
					span.innerHTML = '<img src="'+xGetContextPath()+'/img/icon_user.png"> '+nombre;
					var br = xCreate('br');
					list.appendChild(span);
					list.appendChild(br);
				}
				var ancho = (xClientWidth()-xWidth('div_integrantes'))/2;
				var alto = (xClientHeight()-xHeight('div_integrantes'))/2;
				xMoveTo('div_integrantes',ancho,alto);
				xShow('div_integrantes');
				
			}else{
				alert("Error, no fue posible obtener los grupos.");
				window.location.reload();
			}
		}
	};

}
var nameTMP;
function mostrarRenombrarGrupo(){
	xHideD('title'); 
	xShowD('titleForm'); 
	nameTMP=xGetElementById('titleText').value=xInnerHtml('title');
	xGetElementById('titleText').focus(); 
	xGetElementById('titleText').select();
}

function renombrarGrupo(input){
	if(input.value === nameTMP){
		xShowD('title'); 
		xHideD('titleForm');
		 return false;
	}
	/*if(!validarCaracteres(input.value.trim())){
		alert("Solo se permiten los siguientes caracteres: a-zA-Z0-9._@():$?¿¡!\-$ñáéíóúÑÁÉÍÓÚ\ny con un mínimo de 2.")
	}*/
	if(input.value.trim().length<4){
		alert("Nombre de grupo demasiado corto.");
		return false;
	}
    //Entrada
	var cadena = "descripcion="+escape(input.value);
	//Salida
	
	ajax = nuevoAjax();
	
	ajax.open("POST", xGetContextPath()+"/aulavirtual/tgrupal/RenombrarMiGrupo.action", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send(cadena);

	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			if(ajax.responseText === "OK"){
				xShowD('title'); 
				xHideD('titleForm');
				xInnerHtml('title',xGetElementById('titleText').value);
			}else{
				alert("Error, no fue posible renombrar su grupo.");
				window.location.reload();
			}
		}
	};

}
// Sacada de control_grupos.js
function validarCaracteres(texto){
	var reg=/(^[a-zA-Z0-9._@():$?¿¡!\-$ñáéíóúÑÁÉÍÓÚ ]{2,100}$)/;
	if(!reg.test(texto)){
		return false;
	}
	return true;
}

var alphaT = 0;
function fadeIn(){
	var trans = xGetElementById("transparency");
	if(alphaT < 56){
		alphaT = alphaT + 1;
		trans.style.filter = 'alpha(opacity='+alphaT+')';
		var alphaWhitZero = alphaT;
		if(alphaT < 10){
			alphaWhitZero = '0'+alphaT;
		}
		trans.setAttribute('style',trans.getAttribute('style')+'-moz-opacity:.'+alphaWhitZero+';');
	}else{
		alphaT = 0;
		clearInterval(fadeInTmp);
	}
}

function closeDivIntegrantes(){
	var trans = xGetElementById("transparency");
	trans.setAttribute('style',trans.getAttribute('style')+'-moz-opacity:.0;');
	trans.style.filter = 'alpha(opacity=0)';
	xResizeTo('transparency',0,0);
	xHide('div_integrantes');
	
}

function seleccionInput(Input, valor) {
	if (valor) {
		Input.style.background = "#FFFBE8";
		Input.style.borderColor = "#EFDA45";
	} else {
		Input.style.background = "#FFFFFF";
		Input.style.borderColor = "#AAABAF";
	}
}

function seleccionBlue(TR, valor) {
	if (valor) {
		TR.style.background = "#DCE8F2";
	} else {
		TR.style.background = "#FFFFFF";
	}
}

function cuentaCaracteres(formDesc){
	//var formDesc = xGetElementById('form_descripcion');
	if(formDesc.value.length > 500){
		alert("Llegó al límite de número de caracteres.");
		formDesc.value = formDesc.value.substring(0,500);
	}
	xGetElementById('form_numCaracteres').value = formDesc.value.length;
}

function validar(){
	if(trim(xGetElementById('form_descripcion').value)===''){ 
		alert('Debe ingresar una descripción adicional.');
		return false;
	}
	var buttonPublish = xGetElementById("form_button");
	buttonPublish.disabled = true;
	return true;
	
}

function mostrarFormNuevoMensaje(){
	xChangeDisplay("mensaje");
}

function mostrarFormModMensaje(){
	xGetElementById("form_descripcion").value = xGetElementById("descripcionHTML").innerHTML
	xGetElementById('form_numCaracteres').value = xGetElementById("form_descripcion").value.length;
	xChangeDisplay("mensaje");
}
function abrirDebate(url){
	var ancho = 720;
	var alto = 550;
    var X = (screen.availWidth - ancho)/2;
    var Y = (screen.availHeight - alto)/2;
	aulaVirtualDialogo = window.open(url, 'AulaVirtualDebate', 'width='+ancho+',height='+alto+',toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top='+Y+',left='+X+''); 
	aulaVirtualDialogo.focus();
	return true;
}
