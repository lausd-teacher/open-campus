function mostrarBusqueda(btn, idrol){
	var div = $(btn).up(0).previous();
	div.insert($('form_matricula'));
	$('idrol').setValue(idrol);
	$('form_matricula').show();
	$('usuario').focus();
}

function todos(check){
	if(check.checked==true){  		
		$$('input.chkmail').each(function(mail){
			mail.checked=true;
		});
	}else{
		$$('input.chkmail').each(function(mail){
			mail.checked=false;
		});
	}
}

function masivo(){	 
	    		var destino = "";
	    		$$('input.chkmail').each(function(mail){
	    			if(mail.checked){
	    				destino += mail.getValue()+',';
	    			}
	    		});
	    		if(destino.length != 0)
	    			enviarCorreo(destino);
	    	}

function cambiarEstado(img,id){
	var query = function (oXML) { 
		if(oXML.responseText === 'OK'){
			if(img.alt == 'Activar'){
				img.alt = 'Desactivar';
				img.src = xGetContextPath()+"/img/activo.gif";
			}else{
				img.alt = 'Activar';
				img.src = xGetContextPath()+"/img/desactivo.gif";
			}
		}else{
			alert('Hubo un error al intentar guardar, se sugiere actualizar la página.');
		}
	};
	
	var url = xGetContextPath()+"/admin/aulavirtual/CambiarEstadoMatricula.action";
	var params = "idmatricula="+id+"&cmd="+img.alt;
	new Ajax.Request(url, { method: 'post', parameters: params, onSuccess: query }); 
}

function eliminarMatricula(idmatricula, nombre) {
	if (window.confirm("Desea eliminar a " + nombre + "de esta aula virtual ?")) {
		document.location.href = xGetContextPath() + "/admin/aulavirtual/EliminarMatricula.action?idmatricula=" + idmatricula;
	}
}

function cambiarPrincipal(icon, id){
	
	var query = function(http) {
		//alert(http.responseText)
		if(http.responseText === 'OK'){
			
			$$('img.principal').each(function(star) {
				star.src = xGetContextPath() + "/img/icon_importante_n.gif";
			});
			icon.src = xGetContextPath() + "/img/icon_importante_y.gif";
			
		}else{
			alert('Hubo un error al intentar guardar, se sugiere actualizar la página.');
		}
		
	}
	
	var url = xGetContextPath()+"/admin/aulavirtual/CambiarPrincipalMatricula.action";
	var params = "idmatricula="+id;
	new Ajax.Request(url, { method: 'post', parameters: params, onSuccess: query }); 

}


//------------------------------------------- OLD ------------------------------------------------------------//
var v;
var nuevaBusqueda;
var busqueda;
var ultimaBusquedaNula;
var divLista;
var inputLista;
var ultimoIdentificador;
var elementoSeleccionado;
function asignaVariables() {
	v = 1;
	nuevaBusqueda = 1;
	busqueda = null;
	ultimaBusquedaNula = null;
	divLista = document.getElementById("lista");
	inputLista = document.getElementById("input_2");
	elementoSeleccionado = -1;
	ultimoIdentificador = 0;
}
function formateaLista(valor) {
	try {
	// Funcion encargada de ir colocando en negrita las palabras y asignarle un ID a los elementos
		var x = 0, verificaExpresion = new RegExp("(" + valor + ")", "i");
		while (divLista.childNodes[x] !== null) {
			divLista.childNodes[x].innerHTML = divLista.childNodes[x].innerHTML.replace(verificaExpresion, "<b>$1</b>");
			x = x + 1;
		}
	}
	catch (e) {
	}
}
function limpiaPalabra(palabra) {
	palabra = palabra.replace(/<b>/i, "");
	palabra = palabra.replace(/<\/b>/i, "");
	return palabra;
}
function coincideBusqueda(palabraEntera, primerasLetras) {
	if (primerasLetras === null) {
		return 0;
	}
	var verificaExpresion = new RegExp("(" + primerasLetras + ")", "i");
	if (verificaExpresion.test(palabraEntera)) {
		return 1;
	} else {
		return 0;
	}
}
function nuevaCadenaNula(valor) {
	if (coincideBusqueda(valor, ultimaBusquedaNula) === 0) {
		ultimaBusquedaNula = valor;
	}
}
function busquedaEnBD() {
	var valor = inputLista.value;
	if ((coincideBusqueda(valor, busqueda) === 1 && nuevaBusqueda === 0) || coincideBusqueda(valor, ultimaBusquedaNula) === 1) {
		return 0;
	} else {
		return 1;
	}
}
function filtraLista(valor) {
	try {
		var x = 0;
		while (divLista.childNodes[x] !== null) {
			divLista.childNodes[x].innerHTML = limpiaPalabra(divLista.childNodes[x].innerHTML);
			if (coincideBusqueda(limpiaPalabra(divLista.childNodes[x].innerHTML), valor) === 0) {
				divLista.removeChild(divLista.childNodes[x]);
				x = x - 1;
			}
			x = x + 1;
		}
	}
	catch (e) {
	}
}
function reiniciaSeleccion() {
	mouseFuera();
	elementoSeleccionado = -1;
}
function navegaTeclado(evento) {
	var teclaPresionada = (document.all) ? evento.keyCode : evento.which;
	switch (teclaPresionada) {
	  case 40:
		if (elementoSeleccionado < divLista.childNodes.length - 1 - nuevaBusqueda) {
			mouseDentro(divLista.childNodes[elementoSeleccionado + 1]);
			elementoSeleccionado = elementoSeleccionado + 1;
		}
		return 0;
	  case 38:
		if (elementoSeleccionado > 0) {
			mouseDentro(divLista.childNodes[elementoSeleccionado - 1]);
			elementoSeleccionado = elementoSeleccionado - 1;
		}
		return 0;
	  case 13:
	  case 39:
		if (divLista.style.display == "block" && elementoSeleccionado != -1) {
			clickLista(divLista.childNodes[elementoSeleccionado]);
		}
		return 0;
	  default:
		return 1;
	}
}
function rellenaLista() {
	var valor = inputLista.value;
	var reg = /(^[a-zA-Z0-9.@\xF1\xE1\xE9\xED\xF3\xFA\xD1\xC1\xC9\xCD\xD3\xDA ]{2,40}$)/;
	if (!reg.test(valor)) {
		divLista.style.display = "none";
	} else {
		if (busquedaEnBD() === 0) {
			busqueda = valor;
			filtraLista(valor);
			if (divLista.childNodes[0] === null) {
				divLista.style.display = "none";
				nuevaCadenaNula(valor);
			} else {
				reiniciaSeleccion();
				formateaLista(valor);
			}
		} else {
			busqueda = valor;
			ajax = nuevoAjax();
			ajax.open("POST", xGetContextPath() + "/admin/aulavirtual/matricula/Buscar.action", true);
			ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			ajax.send("nombre=" + toHex(valor));
			ajax.onreadystatechange = function () {
				if (ajax.readyState == 4) {
					if (!ajax.responseText) {
						divLista.style.display = "none";
					} else {
						var respuesta = new Array(2);
						respuesta = ajax.responseText.split("&");
						nuevaBusqueda = respuesta[0];
						if (respuesta[1] != "vacio") {
							divLista.style.display = "block";
							divLista.innerHTML = respuesta[1];
							reiniciaSeleccion();
							formateaLista(valor);
						} else {
							nuevaCadenaNula(valor);
						}
					}
				}
			};
		}
	}
}
function clickLista(elemento) {
	v = 1;
	valor = limpiaPalabra(elemento.innerHTML);
	busqueda = valor;
	inputLista.value = valor;
	divLista.style.display = "none";
	elemento.className = "normal";
	var inputUsuario = document.getElementById("form_usuario");
	inputUsuario.value = elemento.id;
}
function mouseFuera() {
	if (elementoSeleccionado != -1 && divLista.childNodes[elementoSeleccionado]) {
		divLista.childNodes[elementoSeleccionado].className = "normal";
	}
}
function mouseDentro(elemento, nElemento) {
	mouseFuera();
	elemento.className = "resaltado";
	if (nElemento) {
		elementoSeleccionado = nElemento;
	}
}
function nuevoAjax() {
	var xmlhttp = false;
	try {
		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
	}
	catch (e) {
		try {
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
var tempidrol = 0;
function mostrarParaMatricular(botonMas, botonMat, idrol) {
	document.getElementById("form_usuario").value = "";
	if (0 != tempidrol) {
		document.getElementById("botonMat" + tempidrol).style.visibility = "hidden";
		document.getElementById("botonMas" + tempidrol).style.visibility = "visible";
		document.getElementById("seccion_matricula").style.visibility = "hidden";
		try {
			document.getElementById("seccion_formacion_" + tempidrol).style.visibility = "hidden";
		}
		catch (e) {
		}
		document.getElementById("matricular_" + tempidrol).innerHTML = "";
	}
	botonMas.style.visibility = "hidden";
	botonMat.style.visibility = "visible";
	if (ROL_AULAVIRTUAL_ESTUDIANTE == idrol) {
		document.getElementById("seccion_matricula").style.visibility = "visible";
	}
	if (ROL_AULAVIRTUAL_RESPONSABLE != idrol) {
		document.getElementById("seccion_formacion_" + idrol).style.visibility = "visible";
	}
	paraMatricular(idrol);
	tempidrol = idrol;
}
function paraMatricular(idrol) {
	document.getElementById("matricular_" + idrol).innerHTML = "<input type=\"text\" size=\"40\" style=\"font-size: 13px;\" id=\"input_2\" onfocus=\"if(document.getElementById('lista').childNodes[0]!=null && this.value!='') { filtraLista(this.value); formateaLista(this.value);reiniciaSeleccion(); document.getElementById('lista').style.display='block'; }\" onblur=\"if(v==1) document.getElementById('lista').style.display='none';\" onkeyup=\"if(navegaTeclado(event)==1) {clearTimeout(ultimoIdentificador); ultimoIdentificador=setTimeout('rellenaLista()', 1000); }\"> ";
	document.getElementById("matricular_lista_" + idrol).innerHTML = "<div id=\"lista\" onmouseout=\"v=1;\" onmouseover=\"v=0;\">";
	asignaVariables();
}
function matricularUsuario(idrol) {
	var secciontemp;
	var div_selected = document.getElementById("seccion_matricula");
	try {
		for (p = 0; p < div_selected.getElementsByTagName("OPTION").length; p = p + 1) {
			if (div_selected.getElementsByTagName("OPTION")[p].selected) {
				secciontemp = div_selected.getElementsByTagName("OPTION")[p].value;
				break;
			}
		}
	}
	catch (e) {
	}
	div_selected = document.getElementById("form_seccion");
	div_selected.value = secciontemp;
	div_selected = document.getElementById("form_rol");
	div_selected.value = idrol;
	div_selected = document.getElementById("seccion_formacion_" + idrol);
	try {
		for (p = 0; p < div_selected.getElementsByTagName("OPTION").length; p = p + 1) {
			if (div_selected.getElementsByTagName("OPTION")[p].selected) {
				secciontemp = div_selected.getElementsByTagName("OPTION")[p].value;
				break;
			}
		}
	}
	catch (e) {
	}
	if (1 != idrol && secciontemp == "0") {
		window.alert("Especifique Formacion");
		return;
	}
	div_selected = document.getElementById("form_formacion");
	div_selected.value = secciontemp;
	var usuarioform = document.getElementById("form_usuario").value;
	usuarioform = usuarioform.replace(" ", "");
	if (0 !== usuarioform.length) {
		document.from_marticular.submit();
	} else {
		window.alert("Especifique usuario");
	}
}

var imgs = [xGetContextPath() + "/img/desactivo.gif", xGetContextPath() + "/img/activo.gif"];
function estadoMatricula(idmatricula, figura) {
	var estado_temp = document.getElementById("estado_" + idmatricula);
	estado_temp = (estado_temp.value != ESTADO_ACTIVO) ? "1" : "0";
	ajax = nuevoAjax();
	ajax.open("POST", xGetContextPath() + "/admin/aulavirtual/matricula/Modificar.action", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("id=" + idmatricula + "&estado=" + estado_temp);
	ajax.onreadystatechange = function () {
		if (ajax.readyState == 4) {
			var numero = ajax.responseText;
			if ("1" == numero) {
				document.getElementById("estado_" + idmatricula).value = estado_temp;
				figura.src = (estado_temp != ESTADO_ACTIVO) ? imgs[0] : imgs[1];
			} else {
				window.alert("ummm ha surgido un error!!");
			}
		}
	};
}
var imgs2 = [xGetContextPath() + "/img/icon_importante_n.gif", xGetContextPath() + "/img/icon_importante_y.gif"];
function setPrincipalDocente(idMatricula) {
	var preprincipal = document.getElementById("principal_input_" + idMatricula);
	preprincipal = (preprincipal.value != ESTADO_ACTIVO) ? "1" : "0";
	ajax = nuevoAjax();
	ajax.open("POST", xGetContextPath() + "/admin/aulavirtual/matricula/Principal.action", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("id=" + idMatricula + "&estado=" + preprincipal);
	ajax.onreadystatechange = function () {
		if (ajax.readyState == 4) {
			var numero = ajax.responseText;
			if ("1" == numero) {
				var iml = document.getElementById("pop_cuerpo").getElementsByTagName("img");
				for (var i = 0; i < iml.length; i = i + 1) {
					if (iml[i].id.substring(0, 10) == "principal_") {
						iml[i].src = imgs2[0];
					}
				}
				iml = document.getElementById("pop_cuerpo").getElementsByTagName("input");
				for (i = 0; i < iml.length; i = i + 1) {
					if (iml[i].id.substring(0, 16) == "principal_input_") {
						iml[i].value = "0";
					}
				}
				if (preprincipal == "1") {
					iml = document.getElementById("principal_" + idMatricula);
					iml.src = imgs2[1];
					preprincipal = document.getElementById("principal_input_" + idMatricula);
					preprincipal.value = "1";
				}
			} else {
				window.alert("ummm ha surgido un error!!");
			}
		}
	};
}

