var cuenta=0;

function setDimension(select,ancho,alto){
	if(select.value == "custom"){
		ancho.value = "";
		alto.value = "";
		ancho.readOnly =null;
		alto.readOnly =null;
	}else{
		var XY = select.value.split(" x ");
		ancho.value = XY[0];
		alto.value = XY[1];
		ancho.readOnly ="readOnly";
		alto.readOnly ="readOnly";
	}
}

function validarDimension(valor) {
	   var er_cp = /([0-9]|^)$/  ;
	   if(!er_cp.test(valor)) {
	      return false;
	   }
	   return true;
}

function mostrarNuevo(boton) {
	//cerrarBusqueda();
	var panel = document.getElementById("unidad_nuevo");
	panel.style.display = (panel.style.display != "block") ? "block" : "none";
	boton.value = (boton.value != "Nuevo") ? "Nuevo" : "Ocultar";
}
function cerrarNuevo(){
	var panel = document.getElementById("unidad_nuevo");
	panel.style.display = "none";
	document.getElementById("btNuevo").value = "Nuevo";	
}
function cerrarBusqueda(){
	var panel = document.getElementById("unidad_buscar");
	panel.style.display = "none";
	document.getElementById("btAvanzado").value = "Busqueda Avanzada";	
}
function mostrarBuscar(boton) {
	cerrarNuevo();
	var panel = document.getElementById("unidad_buscar");
	panel.style.display = (panel.style.display != "block") ? "block" : "none";
	boton.value = (boton.value != "Busqueda Avanzada") ? "Busqueda Avanzada" : "Busqueda Simple";
}
function cerrarModificar(){
	var panel = document.getElementById("unidad_modificar");
	panel.style.display = "none";
	id_tmp = -1;
}

function abrirTest(url) {
	var ancho = 550;
	var alto = 540;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualTest = window.open(url, "AulaVirtualTest", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualTest.focus();
	return true;
}

function verFichas(fila){

    //Entrada
	var id = xGetElementById('id_'+fila).value;
	//Salida
	var list = xGetElementById('div'+fila);
	
	ajax = nuevoAjax();
	
	ajax.open("POST", xGetContextPath()+"/admin/unidad/ListarFichas.action", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("id="+id);
	
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			if(ajax.responseText !== ""){
				var xml = ajax.responseXML;
				list.innerHTML = '';
				
				var oTable = xCreate('table');
				oTable.className="bor_tabla";
				oTable.style.width="100%";
				oTable.cellSpacing="0";
				oTable.cellPadding="3";
				
				var oTbody = xCreate("tbody");
				
				//Encabezado
				var oCapTR = xCreate('tr');
				oCapTR.className = "fon_cab";
				var oCapTD = xCreate('td');
				oCapTD.className = "tit_cab";
				oCapTD.align = 'center';
				oCapTD.colSpan = 10;
				oCapTD.innerHTML = 'Fichas al cual pertenece';
				oCapTR.appendChild(oCapTD);
				oTbody.appendChild(oCapTR);
				
				//SubEncabezado
				var oTR = xCreate('tr');
					
		        var oTD = xCreate('td');
		        oTD.className = "moduloAbajo";
		        oTD.align = 'center';
		        oTD.innerHTML = '&nbsp;';
		        oTR.appendChild(oTD);
		        
		        oTD = xCreate('td');
		        oTD.className = "moduloAbajo";
		        oTD.align = 'center';
		        oTD.innerHTML = '<b>UDS ID</b>';
		        oTR.appendChild(oTD);
		        
		        oTD = xCreate('td');
		        oTD.className = "moduloAbajo";
		        oTD.align = 'center';
		        oTD.innerHTML = '<b>Ficha ID</b>';
		        oTR.appendChild(oTD);
		        
		        oTD = xCreate('td');
		        oTD.className = "moduloAbajo";
		        oTD.innerHTML = '<b>Nombre</b>';
		        oTR.appendChild(oTD);
		        
		        oTD = xCreate('td');
		        oTD.className = "moduloAbajo";
		        oTD.align = 'center';
		        oTD.innerHTML = '<b>Departamento</b>';
		        oTR.appendChild(oTD);
		        
		        oTD = xCreate('td');
		        oTD.className = "moduloAbajo";
		        oTD.align = 'center';
		        oTD.innerHTML = '<b>Seccion</b>';
		        oTR.appendChild(oTD);
		        
		        oTD = xCreate('td');
		        oTD.className = "moduloAbajo";
		        oTD.align = 'center';
		        oTD.innerHTML = '<b>Familia</b>';
		        oTR.appendChild(oTD);
		        
		        oTD = xCreate('td');
		        oTD.className = "moduloAbajo";
		        oTD.align = 'center';
		        oTD.innerHTML = '<b>Inicio</b>';
		        oTR.appendChild(oTD);
		        
		        oTD = xCreate('td');
		        oTD.className = "moduloAbajo";
		        oTD.align = 'center';
		        oTD.innerHTML = '<b>Fin</b>';
		        oTR.appendChild(oTD);
		        
		        oTD = xCreate('td');
		        oTD.className = "moduloAbajo1";
		        oTD.align = 'center';
		        oTD.innerHTML = '&nbsp;';
		        oTR.appendChild(oTD);
		        
		        oTbody.appendChild(oTR);
				
				
				
				for (x = 0; x < xml.getElementsByTagName("ficha").length; x = x + 1) {
					//Extraer valiables
					var ficha = xml.getElementsByTagName("ficha")[x];
					
					var idUDS = ficha.getElementsByTagName("idUDS")[0].childNodes[0].nodeValue;
					var idFicha = ficha.getElementsByTagName("idFicha")[0].childNodes[0].nodeValue;
					var estado = ficha.getElementsByTagName("estado")[0].childNodes[0].nodeValue;
					var nombre = ficha.getElementsByTagName("nombre")[0].childNodes[0].nodeValue;
					var departamento = ficha.getElementsByTagName("departamento")[0].childNodes[0].nodeValue;
					var seccion = ficha.getElementsByTagName("seccion")[0].childNodes[0].nodeValue;
					var familia = ficha.getElementsByTagName("familia")[0].childNodes[0].nodeValue;
					var fechaInicio = ficha.getElementsByTagName("fechaInicio")[0].childNodes[0].nodeValue;
					var fechaFin = ficha.getElementsByTagName("fechaFin")[0].childNodes[0].nodeValue;
					
					oTR = xCreate('tr');
					
			        oTD = xCreate('td');
			        oTD.className = "bor_der_cur";
			        oTD.align = 'center';
			        oTD.innerHTML = '<img src="'+xGetContextPath()+'/img/icon_blib.gif">';
			        oTR.appendChild(oTD);
			        
			        oTD = xCreate('td');
			        oTD.className = "bor_der_cur";
			        oTD.align = 'center';
			        oTD.innerHTML = idUDS;
			        oTR.appendChild(oTD);
			        
			        oTD = xCreate('td');
			        oTD.className = "bor_der_cur";
			        oTD.align = 'center';
			        oTD.innerHTML = idFicha;
			        oTR.appendChild(oTD);
			        
			        oTD = xCreate('td');
			        oTD.className = "bor_der_cur";
			        oTD.innerHTML = nombre;
			        oTR.appendChild(oTD);
			        
			        oTD = xCreate('td');
			        oTD.className = "bor_der_cur";
			        oTD.align = 'center';
			        oTD.innerHTML = departamento;
			        oTR.appendChild(oTD);
			        
			        oTD = xCreate('td');
			        oTD.className = "bor_der_cur";
			        oTD.align = 'center';
			        oTD.innerHTML = seccion;
			        oTR.appendChild(oTD);
			        
			        oTD = xCreate('td');
			        oTD.className = "bor_der_cur";
			        oTD.align = 'center';
			        oTD.innerHTML = familia;
			        oTR.appendChild(oTD);
			        
			        oTD = xCreate('td');
			        oTD.className = "bor_der_cur";
			        oTD.align = 'center';
			        oTD.innerHTML = fechaInicio;
			        oTR.appendChild(oTD);
			        
			        oTD = xCreate('td');
			         oTD.className = "bor_der_cur";
			        oTD.align = 'center';
			        oTD.innerHTML = fechaFin;
			        oTR.appendChild(oTD);
			        
			        oTD = xCreate('td');
			        oTD.align = 'center';
			        if(estado=='0'){
			        	 oTD.innerHTML = '<img src="'+xGetContextPath()+'/img/desactivo.gif">';
			        }else{
			        	 oTD.innerHTML = '<img src="'+xGetContextPath()+'/img/activo.gif">';
			        }
			        oTR.appendChild(oTD);
			        
			        oTbody.appendChild(oTR);
			        
				}
				
				oTable.appendChild(oTbody);
				list.appendChild(oTable);
				
				xShowD('div'+fila);
				
			}else{
				list.innerHTML = "Esta unidad no está presente en ninguna ficha.";
			}
		}
	};

}

var id_tmp = -1;
function mostrarModificar(id,restore) {
	
	var panel = document.getElementById("unidad_modificar");
	var tr = document.getElementById("modificar"+id);
	var panelFinal=document.getElementById("unidad_mod_"+id);
	panelFinal.appendChild(panel);
	
	if(id_tmp == id && restore != true){
		panel.style.display = "none";
		tr.style.display = "none";
		id_tmp = -1;
	}else{
		panel.style.display = "block";
		tr.style.display = "";
		id_tmp = id;
	}
	document.modificar.id.value = document.getElementById("id_"+id).value;
	document.modificar.unidad.value = document.getElementById("nCompleto_"+id).value;
	document.modificar.ruta.value = document.getElementById("jerarquia_"+id).value;
		
	document.getElementById("unidad_title").innerHTML=document.getElementById("nCompleto_"+id).value;
}
function eliminar(nombre) {
	return window.confirm("¿Está seguro que desea eliminar la unidad \"" + nombre + "\"?");
}

function cambiarEstado(img,id){
	var myConn = new XHConn();
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
	var cadena = "id="+id+"&exacto="+img.alt;
	myConn.connect(xGetContextPath()+"/admin/unidad/CambiarEstado.action", "POST", cadena, query);
}

function seleccion(TR, valor) {
	if (valor) {
		TR.style.background = "#EAEBED";
	} else {
		TR.style.background = "";
	}
}
/*function mostrar(id) {
	var numFilas = document.getElementById("numFilas").value;
	for (i = 1; i <= numFilas; i++) {
		var panel_tmp = document.getElementById("unidad_detalle_" + i);
		if (i != id) {
			panel_tmp.style.display = "none";
		}
	}
	var panel = document.getElementById("unidad_detalle_" + id);
	panel.style.display = (panel.style.display != "block") ? "block" : "none";
}*/
function mostrar(id) {
  if (!document.getElementById) return false;
  fila = document.getElementById(id);
  if (fila.style.display != "none") {
    fila.style.display = "none";
  } else {
    fila.style.display = "";
  }
}
function validacion(form) {
	if (cuenta == 0) {
		if(!validarCaracteres(form.unidad.value)){
			alert("Ingrese un nombre válido.");
			return false;
		}
		form.unidad.value = trim(form.unidad.value);
		cuenta++;
		return true;
	} else {
		alert("Su petición ya está siendo procesada, por favor aguarde un instante.");
		return false;
	}
}
function validarBuscar(form){
	form.unidad.value = trim(form.unidad.value);
	/*if(form.unidad.value == '' && form.ruta.selectedIndex == 0) {
		alert('Debe seleccionar una jerarquía o escribir en el nombre completo de la unidad.');
		return false;
	}
	return true;*/
	var flag = true; //validarCaracteres(form.unidad.value);
	if(!flag){
		alert("El criterio de búsqueda incluye caracteres no válidos o ha ingresado menos de 2 carácteres.")
	}
	return flag;
}

function trim(str){
	return str.replace(/\s+$|^\s+/g,"");
}
//*************************************************************************************//

var consTipo1 = "opencampus";
var consTipo2 = "scrom";
var tipoUnidad = "opencampus";
var controlUnidad = "mixto";

function controlUnidadSelecionar(control) {
	controlUnidad = control;
}
function mostrarScrom(check) {
	var panel = document.getElementById("unidad_scrom");
	if (check.value == consTipo1) {
		panel.style.display = "none";
		tipoUnidad = consTipo1;
	} else {
		panel.style.display = "block";
		tipoUnidad = consTipo2;
	}
}
function validarBusqueda() {
	var texto = document.getElementById("unidad_nombre");
	if (texto.value === "") {
		return false;
	}
	var panel = document.getElementById("cargando");
	panel.style.display = "block";
	return true;
}
function teclear(text) {
	var td = document.getElementById(text);
	td.style.background = "none";
}
function enviandoPaquete() {
	var nuevo_corto = document.getElementById("nuevo_corto");
	var nuevo_completo = document.getElementById("nuevo_completo");
	var nuevo_archivo = document.getElementById("nuevo_archivo");
	var temp = null;
	if (nuevo_corto.value === "") {
		temp = document.getElementById("tdnuevo_corto");
		temp.style.background = "red";
	}
	if (nuevo_completo.value === "") {
		temp = document.getElementById("tdnuevo_completo");
		temp.style.background = "red";
	}
	if (nuevo_archivo.value === "") {
		temp = document.getElementById("tdnuevo_archivo");
		temp.style.background = "red";
	}
	if (temp !== null) {
		return;
	}
	var panelCargando = document.getElementById("cargando");
	panelCargando.style.display = "block";
	var panelConfigurar = document.getElementById("unidad_nuevo");
	panelConfigurar.style.display = "none";
	var panelResultado = document.getElementById("unidad_resultado");
	panelResultado.style.display = "block";
	var txt_corto = document.getElementById("resultado_corto");
	var txt_completo = document.getElementById("resultado_completo");
	var txt_paquete = document.getElementById("resultado_paquete");
	var txt_tipo = document.getElementById("resultado_tipo");
	var txt_control = document.getElementById("resultado_control");
	txt_corto.innerHTML = nuevo_corto.value;
	txt_completo.innerHTML = nuevo_completo.value;
	if (nuevo_archivo.value.length >= 60) {
		txt_paquete.innerHTML = "..." + nuevo_archivo.value.substr(nuevo_archivo.value.length - 58, nuevo_archivo.value.length);
	} else {
		txt_paquete.innerHTML = txt_paquete.value;
	}
	txt_tipo.innerHTML = tipoUnidad.toUpperCase();
	if (tipoUnidad != consTipo1) {
		txt_control.innerHTML = controlUnidad.toUpperCase();
	} else {
		txt_control.innerHTML = "";
	}
	var boton1 = document.getElementById("unidad_buscar");
	boton1.disabled = true;
	var boton2 = document.getElementById("unidad_boton_nuevo");
	boton2.disabled = true;
	document.nuevo_unidad_todo.submit();
	//document.nuevo_unidad_todo.submit();
//	ajax.open("GET", URL+'?nuevo_corto='+nuevo_corto.value+'&nuevo_completo='+nuevo_completo.value);
//	//ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
//	ajax.send(nuevo_archivo);
//	ajax.onreadystatechange = function () {
//		if (ajax.readyState == 4) {
//			if (ajax.responseText === "") {
//				alert("nada2222");
//			} else {
//				alert("mal");
//			}
//		}
//	};
}
function respuestaAjax() {
	if (ajax.readyState == 4) {
		if (ajax.responseText === "") {
			alert("nada2222");
		} else {
			alert(ajax.responseText);
		}
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

