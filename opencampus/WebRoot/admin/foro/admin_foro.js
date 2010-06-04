function seleccion(TR, valor) {
	if (valor) {
		TR.style.background = "#EAEBED";
	} else {
		TR.style.background = "";
	}
}

function seleccionOverIcon(TR, valor) {
	if (valor) {
		TR.style.background = "#FFDF00";
	} else {
		TR.style.background = "";
	}
}

function seleccionIcon(TR, valor) {
	if (valor) {
		TR.style.borderColor = "#a0c0dd";
	} else {
		TR.style.borderColor = "#E5EFF8";
	}
}

function validarCaracteres(texto){
	var reg=/(^[a-zA-Z0-9._@<:#ø+$&()°,-=*\/>Ò·ÈÌÛ˙—¡…Õ”⁄ ]{1,100}$)/;
	return reg.test(texto);
}
//***************************************** FAMILIAS *******************************************//

/*
//----- Variables ---
// Sedes
var sede1 = 'L';
var sede2 = 'A';
var sede3 = 'T';
// Familias
var familia1 = 100;
var familia2 = 3002;
var familia3 = 3003;
//Formacion
var formacion1 = 11;
var formacion2 = 12;
var formacion3 = 13;
var formacion4 = 14;
var formacion5 = 15;
var formacion6 = 16;
var formacion7 = 17;
var formacion8 = 100001;
var formacion9 = 100002;
//-------------------

function grupo(name, value) {
    this.name = name;
    this.value = value;
}

var sedes = new Array(3);
sedes[0]=new grupo("LIMA",sede1);
sedes[1]=new grupo("ARQP",sede2);
sedes[2]=new grupo("TRUJ",sede3);

var familias = new Array(3);
familias[0]=new grupo("PFR",familia1);
familias[1]=new grupo("opencampus Virtual",familia2);
familias[2]=new grupo("PCC",familia3);

var formaciones = new Array(9);
formaciones[0]=new grupo("C11",formacion1);
formaciones[1]=new grupo("C12",formacion2);
formaciones[2]=new grupo("C13",formacion3);
formaciones[3]=new grupo("C14",formacion4);
formaciones[4]=new grupo("C15",formacion5);
formaciones[5]=new grupo("C16",formacion6);
formaciones[6]=new grupo("C17",formacion7);
formaciones[7]=new grupo("G1",formacion8);
formaciones[8]=new grupo("G2",formacion9);

function getCiclo(formacion){
	var ciclos = new Array();
	ciclos[0]=new grupo("I",1);
	ciclos[1]=new grupo("II",2);
	if(formacion !== formacion8 && formacion !== formacion9){
		ciclos[2]=new grupo("III",3);
		ciclos[3]=new grupo("IV",4);
		ciclos[4]=new grupo("V",5);
		ciclos[5]=new grupo("VI",6);
		if(formacion === formacion7){
			ciclos[6]=new grupo("VII",7);
			ciclos[7]=new grupo("VIII",8);
		}
	}
	return ciclos;
}

var secciones = new Array(8);
secciones[0]=new grupo("a",1);
secciones[1]=new grupo("b",2);
secciones[2]=new grupo("c",3);
secciones[3]=new grupo("d",4);
secciones[4]=new grupo("e",5);
secciones[5]=new grupo("f",6);
secciones[6]=new grupo("g",7);
secciones[7]=new grupo("h",8);
*/
//* fun *//

function changeSede(select){
	xGetElementById('s_familia').value = 0;
	xGetElementById('s_formacion').value = 0;
	xGetElementById('s_ciclo').value = 0;
	xGetElementById('s_seccion').value = 0;
	
	xHide('s_familia');
	xHide('s_formacion');
	xHide('s_ciclo');
	xHide('s_seccion');
	
	if(select.value !== '0'){
		xShow('s_familia');
	}
}

function changeFamilia(select){
	var f_PFR = '100';
	
	xGetElementById('s_formacion').value = 0;
	xGetElementById('s_ciclo').value = 0;
	xGetElementById('s_seccion').value = 0;
	
	xHide('s_formacion');
	xHide('s_ciclo');
	xHide('s_seccion');
	
	if(select.value !== '0' && select.value === f_PFR && select.value){
		xShow('s_formacion');
	}
}

function changeFormacion(select){
	xGetElementById('s_ciclo').value = 0;
	xGetElementById('s_seccion').value = 0;
	
	xHide('s_ciclo');
	xHide('s_seccion');
	
	if(select.value !== '0'){
		xShow('s_ciclo');
	}
}

function changeCiclo(select){
	xGetElementById('s_seccion').value = 0;
	
	xHide('s_seccion');
	
	if(select.value !== '0'){
		xShow('s_seccion');
	}
}

function agregarRegla(){
	var theSel = xGetElementById('SelectGrupos');
	
	sede = xGetElementById('s_sede').value;
	familia = xGetElementById('s_familia').value;
	formacion = xGetElementById('s_formacion').value;
	ciclo = xGetElementById('s_ciclo').value;
	seccion = xGetElementById('s_seccion').value;
	var theValue = sede+"-"+familia+"-"+formacion+"-"+ciclo+"-"+seccion;
	
	if(yaExisteRegla(theValue)){
		alert("Esta regla ya esta incluÌda en el grupo.");
		return false;
	}
	
	sede = xGetElementById('s_sede').options[xGetElementById('s_sede').selectedIndex ].text;
	familia = xGetElementById('s_familia').options[xGetElementById('s_familia').selectedIndex ].text;
	formacion = xGetElementById('s_formacion').options[xGetElementById('s_formacion').selectedIndex ].text;
	ciclo = xGetElementById('s_ciclo').options[xGetElementById('s_ciclo').selectedIndex ].text;
	seccion = xGetElementById('s_seccion').options[xGetElementById('s_seccion').selectedIndex ].text;
	var theText = sede+" - "+familia+" - "+formacion+" - "+ciclo+" - "+seccion;
	
	addOption(theSel, theText, theValue);
	
	//Update Candidatos
	obtenerCandidatos();
}

function quitarRegla(){
	var theSel = xGetElementById('SelectGrupos');
	var selLength = theSel.length;
	for (i = selLength-1; 0<=i; i = i - 1) {
		if (theSel.options[i].selected) {
			deleteOption(theSel, i);
		}
	}
	
	//Update candidatos
	obtenerCandidatos();
}

function yaExisteRegla(valor){
	var todos = "0-0-0-0-0";
	var theSel = xGetElementById('SelectGrupos');
	
	if(valor === todos){
		limpiarSelect(theSel);
	}
	
	var selLength = theSel.length;
	for (var i = selLength-1; 0<=i; i = i - 1) {
		if (theSel.options[i].value === valor || theSel.options[i].value === todos) {
			return true;
		}
	}
	return false;
}

function addOption(theSel, theText, theValue) {
	var newOpt = new Option(theText, theValue);
	var selLength = theSel.length;
	theSel.options[selLength] = newOpt;
}

function deleteOption(theSel, theIndex) {
	var selLength = theSel.length;
	if (selLength > 0) {
		theSel.options[theIndex] = null;
	}
}

//*************************************** MODERADOR *****************************************//

function obtenerCandidatos(){
	
	// **** Cargando ***//
	// Limpiar Select
	var theSel = xGetElementById('SelectPotenciales');
	limpiarSelect(theSel);
	
	var newOpt = new Option("Cargando, sea paciente por favor ...", 0);
	theSel.options[0] = newOpt;
	//**** Fin Cargando ***//
	
	//Entrada
		var reglas = reglasToString();
		var cadena = "reglas="+reglas;
			
	//Salida
	
	var myConn = new XHConn();
	var query = function (oXML) { 
		var xml = oXML.responseXML;
		
		if(xml.getElementsByTagName("usuarios").length !== 0){
			
			// Limpiar Select
			var theSel = xGetElementById('SelectPotenciales');
			limpiarSelect(theSel);
			
			//Recorrer el Arreglo
			for (var x = 0; x < xml.getElementsByTagName("usuario").length; x = x + 1) {
				//Extraer valiables
				var usuario = xml.getElementsByTagName("usuario")[x];
				
				var id = usuario.getElementsByTagName("id")[0].childNodes[0].nodeValue;
				var nombre = usuario.getElementsByTagName("nombre")[0].childNodes[0].nodeValue;
				
				//Crear opciones
				var newOpt = new Option(nombre, id);
				selLength = theSel.length;
				theSel.options[selLength] = newOpt;
			}
			
			//Comprobar si lista de moderadores es conforme a la lista de usuarios
			updateModeradores();
			
		}else{
			alert('Hubo un error al intentar cargar la lista de potenciales moderadores,\nse sugiere actualizar la p·gina.');
		}
	};
	myConn.connect(xGetContextPath()+"/admin/foro/ListarCandidatos.action", "POST", cadena, query);
}

function updateModeradores(){
	var theSelSource = xGetElementById('SelectPotenciales');
	var theSelDestin = xGetElementById('SelectModeradores');
	
	var selLengthDestin = theSelDestin.length;
	
	for (i = selLengthDestin-1; 0<=i; i = i - 1) {
		if (!yaExiste(theSelSource,theSelDestin.options[i].value)) {
			deleteOption(theSelDestin, i);
		}
	}
}

function agregarModerador(){
	var theSelSource = xGetElementById('SelectPotenciales');
	var theSelDestin = xGetElementById('SelectModeradores');
	
	var selLength = theSelSource.length;
	
	if(theSelSource.selectedIndex != -1){
		for (var i = selLength-1; 0<=i; i = i - 1) {
			if (theSelSource.options[i].selected) {
				if(yaExiste(theSelDestin,theSelSource.options[i].value)){
					alert("El usuario "+theSelSource.options[i].text+" ya est· incluÌdo en el grupo.");
				}else{
					addOption(theSelDestin, theSelSource.options[i].text, theSelSource.options[i].value);
				}
			}
		}
	}else{
		alert("Debe seleccionar un usuario");
	}
}

function quitarModerador(){
	var theSel = xGetElementById('SelectModeradores');
	
	var selLength = theSel.length;
	
	if(theSel.selectedIndex != -1){
		for (i = selLength-1; 0<=i; i = i - 1) {
			if (theSel.options[i].selected) {
				deleteOption(theSel, i);
			}
		}
	}else{
		alert("Debe seleccionar un moderador");
	}
}

function yaExiste(theSel,valor){
	var selLength = theSel.length;
	for (var i = selLength-1; 0<=i; i = i - 1) {
		if (theSel.options[i].value === valor) {
			return true;
		}
	}
	return false;
}

function limpiarSelect(theSel){
	var selLength = theSel.length;
	for (var i = selLength-1; 0<=i; i = i - 1) {
		theSel.options[i] = null;
	}
}

//***************************************** FORO *******************************************//

function mostrarNuevo(){
	limpiarFormulario();
	xGetElementById('form_nuevo').appendChild(xGetElementById('divMod'));
	xGetElementById('formNuevo').action = xGetContextPath()+'/admin/foro/Crear.action';
	xChangeDisplay('form_nuevo');
	xChangeDisplay('close_form_nuevo');
	if(this.idTmp){ 
		xHideD('TR_'+this.idTmp);
	}
}

function cerrarNuevo(){
	xHideD('form_nuevo'); 
	xHideD('close_form_nuevo');
	if(this.idTmp) xHideD('TR_'+this.idTmp);
}

function crearForo(form){
	
	if(form.titular.value.trim() === ''){
		alert("Debe introducir el titular del foro.");
		return false;
	}
	
	if(form.descripcion.value.trim() === ''){
		alert("Debe introducir la descripcion del foro.");
		return false;
	}
		
	if(form.icono.value === ''){
		alert("Debe seleccionar un Ìcono para el foro.");
		return false;
	}
	
	form.reglas.value = reglasToString();
	
	if(form.reglas.value.trim() === ''){
		alert("Debe introducir alg˙n tipo de regla para el foro.");
		return false;
	}
	
	form.moderadores.value = moderadoresToString();
	
	if(form.moderadores.value.trim() === ''){
		if(!window.confirm("øRealmente quiere crear el foro sin ning˙n moderador?")){
			return false;
		}
	}
	
	return true;
	
}

function reglasToString(){
	var reglas = "";
	var theSel = xGetElementById("SelectGrupos");
	var selLength = theSel.length;
	for (i = 0; i<selLength; i = i + 1) {
		reglas += theSel.options[i].value + "/";
	}
	return reglas;
}

function moderadoresToString(){
	var moderadores = "";
	var theSel = xGetElementById("SelectModeradores");
	var selLength = theSel.length;
	for (i = 0; i<selLength; i = i + 1) {
		moderadores += theSel.options[i].value + "/";
	}
	return moderadores;
}

function validarRadio(radio){
	for (i=0;i<radio.length;i++){ 
       if (radio[i].checked) 
         return true;
    } 
    return false;
}

var iconTmp = null;
function selectIcon(td,iconID){
	xGetElementById('foro_icono').value = iconID;
	
	td.style.background = "#FFDF00";
	if(iconTmp != null){
		iconTmp.style.background = "";
	}
	iconTmp = td;
} 

function cambiarEstado(img,idForo){
	//Entrada
		var cmd = xGetElementById('estado_'+idForo).value;
		if(cmd == '0'){
			cmd = 'active';
		}else{
			cmd = 'inactive';
		}
		var cadena = "idForo="+idForo+"&cmd="+cmd;
			
	//Salida
	
	var myConn = new XHConn();
	var query = function (oXML) { 
		if(oXML.responseText === 'OK'){
			if(cmd === 'active'){
				xGetElementById('estado_'+idForo).value = 1;
				img.src = xGetContextPath()+"/img/activo.gif";
			}else{
				xGetElementById('estado_'+idForo).value = 0;
				img.src = xGetContextPath()+"/img/desactivo.gif";
			}
		}else{
			alert('Hubo un error al intentar guardar, se sugiere actualizar la p·gina.');
		}
	};
	myConn.connect(xGetContextPath()+"/admin/foro/CambiarEstado.action", "POST", cadena, query);
}

function cambiarCerrado(img,idForo){
	//Entrada
		var cmd = xGetElementById('cerrado_'+idForo).value;
		if(cmd == '0'){
			cmd = 'active';
		}else{
			cmd = 'inactive';
		}
		var cadena = "idForo="+idForo+"&cmd="+cmd;
			
	//Salida
	
	var myConn = new XHConn();
	var query = function (oXML) { 
		if(oXML.responseText === 'OK'){
			if(cmd === 'active'){
				xGetElementById('cerrado_'+idForo).value = 1;
				img.src = xGetContextPath()+"/img/icon_candado.jpeg";
			}else{
				xGetElementById('cerrado_'+idForo).value = 0;
				img.src = xGetContextPath()+"/img/icon_candado_abierto_out.jpg";
			}
		}else{
			alert('Hubo un error al intentar guardar, se sugiere actualizar la p·gina.');
		}
	};
	myConn.connect(xGetContextPath()+"/admin/foro/CambiarCerrado.action", "POST", cadena, query);
}

function eliminar(idforo) {
	var flag = window.confirm("øEsta seguro de eliminar el foro? Esta acciÛn es irrevocable.");
	if(flag){
		document.location=xGetContextPath()+'/admin/foro/Eliminar.action?idForo='+idforo;
	}
}

//-- Modificar --

function mostrarModificar(id){
	
	var cadena = "idForo="+id;
	
	var myConn = new XHConn();
	var query = function (oXML) { 
			if (oXML.readyState === 4) {
				
				var xml = oXML.responseXML;
				
				if(xml.getElementsByTagName("foro").length !== 0){
					
					if(this.idTmp) xHideD('TR_'+this.idTmp);
					this.idTmp = id;
					xShowD('TR_'+id);
					xGetElementById('DIV_'+id).appendChild(xGetElementById('divMod'));
					xHideD('form_nuevo'); 
					xHideD('close_form_nuevo');
					
					rellenarFormulario(xml);
				}else{
					alert('Hubo un error al intentar cargar los datos del foro,\nse sugiere actualizar la p·gina.');
				}
			}/*else if (ajax.readyState==1){
				showLoading();
			}*/
		};
		
	myConn.connect(xGetContextPath()+"/admin/foro/ObtenerForo.action", "POST", cadena, query);
}

function rellenarFormulario(xml){
	var form = xGetElementById('formNuevo');
	
	// Limpiar Form
	limpiarFormulario();
	form.action = xGetContextPath()+'/admin/foro/Modificar.action';
	
	//Foro
	var idForo = xml.getElementsByTagName("id")[0].childNodes[0].nodeValue;
	var titulo = xml.getElementsByTagName("titulo")[0].childNodes[0].nodeValue;
	var descripcion = xml.getElementsByTagName("descripcion")[0].childNodes[0].nodeValue;
	var icono = xml.getElementsByTagName("icono")[0].childNodes[0].nodeValue;
	
	//Reglas
	for (var x = 0; x < xml.getElementsByTagName("regla").length; x = x + 1) {
		//Extraer valiables
		var noticia = xml.getElementsByTagName("regla")[x];
		var idregla = noticia.getElementsByTagName("idregla")[0].childNodes[0].nodeValue;
		var sede = noticia.getElementsByTagName("sede")[0].childNodes[0].nodeValue;
		var familia = noticia.getElementsByTagName("familia")[0].childNodes[0].nodeValue;
		var formacion = noticia.getElementsByTagName("formacion")[0].childNodes[0].nodeValue;
		var ciclo = noticia.getElementsByTagName("ciclo")[0].childNodes[0].nodeValue;
		var seccion = noticia.getElementsByTagName("seccion")[0].childNodes[0].nodeValue;
		
		// Agregar Regla
		var theSel = xGetElementById('SelectGrupos');
	
		var theValue = sede+"-"+familia+"-"+formacion+"-"+ciclo+"-"+seccion;
		
		sede = obtenerTextoporValor(xGetElementById('s_sede'), sede);
		familia = obtenerTextoporValor(xGetElementById('s_familia'), familia);
		formacion = obtenerTextoporValor(xGetElementById('s_formacion'), formacion);
		ciclo = obtenerTextoporValor(xGetElementById('s_ciclo'), ciclo);
		seccion = obtenerTextoporValor(xGetElementById('s_seccion'), seccion);
		var theText = sede+" - "+familia+" - "+formacion+" - "+ciclo+" - "+seccion;
		
		addOption(theSel, theText, theValue);
		
	}
	
	//Moderadores
	for (var x = 0; x < xml.getElementsByTagName("moderador").length; x = x + 1) {
		//Extraer valiables
		var moderador = xml.getElementsByTagName("moderador")[x];
		var id = moderador.getElementsByTagName("idmoderador")[0].childNodes[0].nodeValue;
		var nombre = moderador.getElementsByTagName("nombre")[0].childNodes[0].nodeValue;
		
		// Agregar Regla
		var theSel = xGetElementById('SelectModeradores');
	
		addOption(theSel, nombre, id);
	}
	
	form.idForo.value = idForo;
	form.titular.value = titulo;
	form.descripcion.value = descripcion;
	form.icono.value = icono;
	var td = xGetElementById('i_'+icono);
	td.style.background = "#FFDF00";
	iconTmp = td;
	
	obtenerCandidatos();
}

function limpiarFormulario(){
	xGetElementById('formNuevo').reset();
	limpiarSelect(xGetElementById('SelectGrupos'));
	limpiarSelect(xGetElementById('SelectPotenciales'));
	limpiarSelect(xGetElementById('SelectModeradores'));
	xHide('s_familia');
	xHide('s_formacion');
	xHide('s_ciclo');
	xHide('s_seccion');
	if(this.iconTmp){
		iconTmp.style.background = "";
	}
}

function obtenerTextoporValor(theSel, value){
	var selLength = theSel.length;
	for (i = 0; i<selLength; i = i + 1) {
		if(theSel.options[i].value == value){
			return theSel.options[i].text;
		} 
	}
}

//***************************************** NOTICIA *******************************************//

function showLoading(){
	xMoveTo('loading',xScrollLeft()+xClientWidth()-125,xScrollTop());
	xShowD('loading');	
}

//deprecated, usar el que esta en util "validarExtAdjunto"
var extArray = new Array(".gif", ".jpg",".png");
function validarAdjunto(file){
	if(file == "") return true;
	while (file.indexOf("\\") != -1)
	file = file.slice(file.indexOf("\\") + 1);
	ext = file.slice(file.indexOf(".")).toLowerCase();
	for (var i = 0; i < extArray.length; i++) {
		if (extArray[i] == ext) {
			return true;
		}
	}
	alert("Se permiten ˙nicamente archivos con la extenciÛn: " 
	+ (extArray.join(" ")) + "\nPor favor, seleccione otro archivo "
	+ "e intente de nuevo.");
	return false;
}

function eliminarNoticia(idnoticia) {
	var flag = window.confirm("øEsta seguro de eliminar la noticia? Esta acciÛn es irrevocable.");
	if(flag){
		document.location=xGetContextPath()+'/admin/noticia/Eliminar.action?idnoticia='+idnoticia;
	}
}
