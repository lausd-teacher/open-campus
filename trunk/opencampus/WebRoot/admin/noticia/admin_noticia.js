function seleccion(TR, valor) {
	if (valor) {
		TR.style.background = "#EAEBED";
	} else {
		TR.style.background = "";
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
/*
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
*/
function agregarRegla(){
	var theSel = xGetElementById('SelectGrupos');
	
	jerarquia = xGetElementById('s_jerarquia').value;
	periodo = xGetElementById('s_periodo').value;
	rol = xGetElementById('s_rol').value;
	var theValue = jerarquia+"-"+periodo+"-"+rol;
	
	if(yaExiste(theValue)){
		alert("Esta regla ya esta incluÌda en el grupo.");
		return false;
	}
	
	jerarquia = xGetElementById('s_jerarquia').options[xGetElementById('s_jerarquia').selectedIndex ].text;
	periodo = xGetElementById('s_periodo').options[xGetElementById('s_periodo').selectedIndex ].text;
	rol = xGetElementById('s_rol').options[xGetElementById('s_rol').selectedIndex ].text;
	jerarquia = jerarquia.substring(0,jerarquia.length-1);
	jerarquia = jerarquia.substring(jerarquia.lastIndexOf("/")+1);
	var theText = "["+jerarquia+"] ["+periodo+"] ["+rol+"]";
	
	addOption(theSel, theText, theValue);
}

function quitarRegla(){
	var theSel = xGetElementById('SelectGrupos');
	var selLength = theSel.length;
	for (i = selLength-1; 0<=i; i = i - 1) {
		if (theSel.options[i].selected) {
			deleteOption(theSel, i);
		}
	}
}

function yaExiste(valor){
	var theSel = xGetElementById('SelectGrupos');
	var selLength = theSel.length;
	for (i = selLength-1; 0<=i; i = i - 1) {
		if (theSel.options[i].value === valor) {
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

//***************************************** NOTICIA *******************************************//

function verNoticia(idnoticia){
	var eName = 'pUp_Noticia_Demo';
	var width = 600;
	var height = 700;
	var x = (xClientWidth()-width)/2;
	var y = (xClientHeight()-height)/2;
	if(y<0){
		y=0;
	}
	var url = xGetContextPath()+'/admin/noticia/VerNoticia.action?idnoticia='+idnoticia;
	var title = 'Detalle de la noticia';
	
	if(xGetElementById(eName)){
		INNERDIV.cargar(eName,url,'',true);
	}else{
		INNERDIV.newInnerDiv(eName,x,y,width,height,url,title);
	}
}

function refrescarReglas(){
	var theSel = xGetElementById('SelectGrupos');
	var selLength = theSel.length;
	for (var i = selLength-1; 0<=i; i = i - 1) {
		theSel.options[i] = null;
	}
}

function mostrarNuevo(){
	xGetElementById('form_mod_noticia').reset();
	refrescarReglas();
	xGetElementById('form_nuevo').appendChild(xGetElementById('divMod'));
	xGetElementById('form_mod_noticia').action = xGetContextPath()+'/admin/noticia/Crear.action';
	
	if(xGetElementById('form_nuevo').style.display == "none"){
		//primero mostramos luego refrescamos
		xChangeDisplay('form_nuevo');
		refrescarEditor();
	}else{
		//primero refrescamos luego ocultamos
		refrescarEditor();
		xChangeDisplay('form_nuevo');
	}
	
	xChangeDisplay('close_form_nuevo');
	if(this.idTmp) xHideD('TR_'+this.idTmp);
}

function cerrarNuevo(){
	xHideD('form_nuevo'); 
	xHideD('close_form_nuevo');
	if(this.idTmp) xHideD('TR_'+this.idTmp);
}

function mostrarModificar(id){
	
	var cadena = "idnoticia="+id;
	
	var myConn = new XHConn();
	var query = function (oXML) { 
			if (oXML.readyState === 4) {
				if(oXML.responseText !== ""){
					var xml = oXML.responseXML;
					var form = xGetElementById('form_mod_noticia');
					//INNERDIV.newInnerDiv('pUp_Mod_Noticia',(xClientWidth()-960)/2,(xClientHeight()-410)/2,960,110,'div:divMod','Modificar noticia');
					if(this.idTmp) xHideD('TR_'+this.idTmp);
					this.idTmp = id;
					xShowD('TR_'+id);
					xGetElementById('DIV_'+id).appendChild(xGetElementById('divMod'));
					refrescarEditor();
					xHideD('form_nuevo'); 
					xHideD('close_form_nuevo');
					rellenarFormulario(form, xml);
				}
			}else if (ajax.readyState==1){
				showLoading();
			}
		};
		
	myConn.connect(xGetContextPath()+"/admin/noticia/ObtenerNoticia.action", "POST", cadena, query);
}
function showLoading(){
	xMoveTo('loading',xScrollLeft()+xClientWidth()-125,xScrollTop());
	xShowD('loading');	
}
function refrescarEditor(){
	var doc = document.getElementById("wysiwyg" + "contenido").contentWindow.document;
	doc.open();
	//doc.write("");
	doc.close();
	doc.body.contentEditable = true;
	doc.designMode = "on";
}

function obtenerTextoporValor(theSel, value){
	var selLength = theSel.length;
	for (i = 0; i<selLength; i = i + 1) {
		if(theSel.options[i].value == value){
			return theSel.options[i].text;
		} 
	}
}

function rellenarFormulario(form, xml){
	// Limpiar Form
	form.reset();
	form.action = xGetContextPath()+'/admin/noticia/Modificar.action';
	
	//Noticia
	var idnoticia = xml.getElementsByTagName("id")[0].childNodes[0].nodeValue;
	var titular = xml.getElementsByTagName("titular")[0].childNodes[0].nodeValue;
	var cuerpo = xml.getElementsByTagName("cuerpo")[0].childNodes[0].nodeValue;
	var sumilla = xml.getElementsByTagName("sumilla")[0].childNodes[0].nodeValue;
	var fecha = xml.getElementsByTagName("fecha")[0].childNodes[0].nodeValue;
	var imagen = xml.getElementsByTagName("imagen")[0].childNodes[0].nodeValue;
	var formato = xml.getElementsByTagName("formato")[0].childNodes[0].nodeValue;
	var idseccion = xml.getElementsByTagName("idseccion")[0].childNodes[0].nodeValue;
	
	//Reglas
	refrescarReglas();
	for (var x = 0; x < xml.getElementsByTagName("regla").length; x = x + 1) {
		//Extraer valiables
		var noticia = xml.getElementsByTagName("regla")[x];
		var idjerarquia = noticia.getElementsByTagName("idjerarquia")[0].childNodes[0].nodeValue;
		var idperiodo = noticia.getElementsByTagName("idperiodo")[0].childNodes[0].nodeValue;
		var idrol = noticia.getElementsByTagName("idrol")[0].childNodes[0].nodeValue;
		
		// Agregar Regla
		var theSel = xGetElementById('SelectGrupos');
	
		var theValue = idjerarquia+"-"+idperiodo+"-"+idrol;
		
		idjerarquia = obtenerTextoporValor(xGetElementById('s_jerarquia'), idjerarquia);
		idperiodo = obtenerTextoporValor(xGetElementById('s_periodo'), idperiodo);
		idrol = obtenerTextoporValor(xGetElementById('s_rol'), idrol);
		idjerarquia = idjerarquia.substring(0,idjerarquia.length-1);
		idjerarquia = idjerarquia.substring(idjerarquia.lastIndexOf("/")+1);
		var theText = "["+idjerarquia+"] ["+idperiodo+"] ["+idrol+"]";
		
		addOption(theSel, theText, theValue);
		
	}
	
	form.idnoticia.value = idnoticia;
	form.titular.value = titular;
	form.sumilla.value = sumilla;
	form.fecha.value = fecha;
	document.getElementById('wysiwyg' + "contenido").contentWindow.document.body.innerHTML = cuerpo;
	form.idseccion.value = idseccion;
	form.formato[formato].checked = true;
}

function crearnoticia(form){
	
	if(form.titular.value.trim() === ''){
		alert("Debe introducir el titular de la noticia.");
		return false;
	}
	
	if(form.fecha.value.trim() === ''){
		alert("Debe introducir la fecha de publicaciÛn.");
		return false;
	}
	
	var con = xGetElementById("wysiwyg" + "contenido").contentWindow.document.body.innerHTML;
	var conClear = con.stripTags().stripScripts().strip().replace(/&nbsp;/g,"").stripNewLine().strip();
	if(conClear.length===0){
		alert("Debe introducir el cuerpo de la Noticia.");
		return false;
	}
	
	if(form.sumilla.value.trim() === ''){
		alert("Debe introducir la sumilla de la noticia.");
		return false;
	}
	
	if(form.idseccion.options[form.idseccion.selectedIndex ].value  === '0'){
		alert("Debe indicar una secciÛn.");
		return false;
	}
	
	if(!validarAdjunto(form.imagen.value)){
		return false;
	}
		
	var reglas = "";
	var theSel = xGetElementById("SelectGrupos");
	var selLength = theSel.length;
	for (i = 0; i<selLength; i = i + 1) {
		reglas += theSel.options[i].value + "/";
	}
	form.reglas.value = reglas;
	
	if(form.reglas.value.trim() === ''){
		alert("Debe introducir alg˙n tipo de regla para la noticia.");
		return false;
	}
	
	if(form.imagen.value.trim() !== '' && !validarRadio(form.formato)){
		alert("Debe seleccionar la posiciÛn de la imagen.");
		return false;
	}
	
	return true;
	
}

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

function validarRadio(radio){
	for (i=0;i<radio.length;i++){ 
       if (radio[i].checked) 
         return true;
    } 
    return false;
}

function eliminarNoticia(idnoticia) {
	var flag = window.confirm("øEsta seguro de eliminar la noticia? Esta acciÛn es irrevocable.");
	if(flag){
		document.location=xGetContextPath()+'/admin/noticia/Eliminar.action?idnoticia='+idnoticia;
	}
}

function cambiarEstado(img,idnoticia){
	//Entrada
		var cmd = xGetElementById('estado_'+idnoticia).value;
		if(cmd == '0'){
			cmd = 'active';
		}else{
			cmd = 'inactive';
		}
		var cadena = "idnoticia="+idnoticia+"&cmd="+cmd;
			
	//Salida
	
	var myConn = new XHConn();
	var query = function (oXML) { 
		if(oXML.responseText === 'OK'){
			if(cmd === 'active'){
				xGetElementById('estado_'+idnoticia).value = 1;
				img.src = xGetContextPath()+"/img/activo.gif";
			}else{
				xGetElementById('estado_'+idnoticia).value = 0;
				img.src = xGetContextPath()+"/img/desactivo.gif";
			}
		}else{
			alert('Hubo un error al intentar guardar, se sugiere actualizar la p·gina.');
		}
	};
	myConn.connect(xGetContextPath()+"/admin/noticia/CambiarEstado.action", "POST", cadena, query);
}

//***************************************** SECCION *******************************************//

function crearSeccion(form) {

	var nombre = form.nombre.value.trim();
	
	if(nombre !== ""){
		if(validarCaracteres(nombre)){
			
		    //Entrada
			var cadena = "nombre="+escape(nombre);
			
			//Salida
			INNERDIV.cargar('pUp_Seccion',xGetContextPath()+"/admin/noticia/seccion/Crear.action",cadena,cargarSeccionesXML,true);
			
		}else{
			alert("Ha ingresado caracteres no v·lidos.");
		}
	}else{
		alert("Ingrese un nombre para la nueva secciÛn.");
	}
}

function eliminarSeccion(idSeccion,nombre) {
	var flag = window.confirm("øEsta seguro de eliminar la secciÛn de \"" + nombre + "\"? ");
	if(flag){
		//Entrada
		var cadena = "idSeccion="+idSeccion;
		
		//Salida
		INNERDIV.cargar('pUp_Seccion',xGetContextPath()+"/admin/noticia/seccion/Eliminar.action",cadena,cargarSeccionesXML,true);
	}
}

function reordenar(cmd,idSeccion){
	//Entrada
	var cadena = "idSeccion="+idSeccion+"&cmd="+cmd;
	
	//Salida
	INNERDIV.cargar('pUp_Seccion',xGetContextPath()+"/admin/noticia/seccion/Reordenar.action",cadena,cargarSeccionesXML,true);
}

function abrirModificarSeccion(fila){
	xHideD('nombre_'+fila);
	xShowD('nombre_mod_'+fila);
	xGetElementById('nombre_mod_'+fila).value = xGetElementById('nombre_'+fila).innerHTML;
	xGetElementById('nombre_mod_'+fila).focus();
}

function modificarSeccion(fila,inputNombre,idSeccion){
	
	var nombre = inputNombre.value.trim();
	
	if(xGetElementById('nombre_'+fila).innerHTML != nombre){
		//Entrada
		var cadena = "idSeccion="+idSeccion+"&nombre="+escape(nombre);
		
		//INNERDIV.cargar('pUp_Seccion',xGetContextPath()+"/admin/noticia/seccion/Renombrar.action",cadena,true);
		var myConn = new XHConn();
		var query = function (oXML) { 
			if(oXML.responseText == 'OK'){
				xGetElementById('nombre_'+fila).innerHTML = nombre;
				cargarSeccionesXML();
			}else{
				alert('Hubo un error al intentar guardar, se sugiere actualizar la p·gina.');
			}
			xShowD('nombre_'+fila);
			xHideD('nombre_mod_'+fila);
		};
		myConn.connect(xGetContextPath()+"/admin/noticia/seccion/Renombrar.action", "POST", cadena, query);
	}else{
		xShowD('nombre_'+fila);
		xHideD('nombre_mod_'+fila);
	}
}

function cargarSeccionesXML(){
	var myConn = new XHConn();
	var query = function (oXML) { 
			if (oXML.readyState === 4) {
				if(oXML.responseText !== ""){
					var xml = oXML.responseXML;
					var theSel = xGetElementById("sc_newSeccion");
					refrescarSelect(theSel,xml);
				}
			}
		};
		
	myConn.connect(xGetContextPath()+"/admin/noticia/seccion/CargarXML.action", "POST", null, query);
}

function refrescarSelect(theSel,xml){
	// Limpiar Select
	var selLength = theSel.length;
	for (var i = selLength-1; 1<=i; i = i - 1) {
		theSel.options[i] = null;
	}
	
	//Recorrer el Arreglo
	for (var x = 0; x < xml.getElementsByTagName("seccion").length; x = x + 1) {
		//Extraer valiables
		var seccion = xml.getElementsByTagName("seccion")[x];
		
		var id = seccion.getElementsByTagName("id")[0].childNodes[0].nodeValue;
		var nombre = seccion.getElementsByTagName("nombre")[0].childNodes[0].nodeValue;
		
		//Crear opciones
		var newOpt = new Option(nombre, id);
		selLength = theSel.length;
		theSel.options[selLength] = newOpt;
	}
}