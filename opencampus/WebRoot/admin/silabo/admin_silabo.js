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
function seleccion(TR, valor) {
	if (valor) {
		TR.style.background = "#EAEBED";
	} else {
		TR.style.background = "";
	}
}
var cuenta=0;
function validacion(form) {
	if (cuenta == 0) {
		cuenta++;
		return true;
	} else {
		alert("Su peticiÛn ya est· siendo procesada, por favor aguarde un instante.");
		return false;
	}
}
function validarCaracteres(texto){
	return true;
	//var reg=/(^[a-zA-Z0-9._@<:#ø+$&()°,-=*\/>Ò·ÈÌÛ˙—¡…Õ”⁄ ]{3,100}$)/;
	//return reg.test(texto);
}
//From Ficha
//********************************************************************************************//
function buscarCursoPorID(idCurso,nombreCurso){
	
	// Reset Interface
	primeraVez=false;
	
	//Salida
	var salidaComp = xGetElementById("resultado_curso");
	
	var cadena = "idCurso="+idCurso;
	
	salidaComp.innerHTML = "<DIV align='center' style='padding: 2px; background: rgb(204, 68, 68) none repeat scroll 0%; z-index: 3; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; color: white; font-size: 95%; top: 1px; right: 16px; '> Cargando...</DIV>";

	ajax = nuevoAjax();

	ajax.open("POST", xGetContextPath()+"/admin/curso/BuscarPorID.action", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send(cadena);
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			salidaComp.innerHTML = ajax.responseText;
			mostrarSilabos(xGetContextPath()+"/admin/silabo/BuscarPorIDCurso.action",idCurso,nombreCurso);
		}
	}
	
}

//Curso
//********************************************************************************************//
function buscarCursos(){
	
	// Entrada
	var jerarquiComp = xGetElementById("idJerarquia");
	var nombreComp = xGetElementById("nombre");

	//Salida
	var salidaComp = xGetElementById("resultado_curso");
	
	if(true || trim(nombreComp.value) != ""){
		if(true || validarCaracteres(nombreComp.value)){
			// Reset Interface
			primeraVez=true;
			xGetElementById("form_Silabo_idCurso").value = "";
			xHideD("form_Silabo");
			xHideD("resultado_silabo");
		
			var cadena = "nombre="+escape(nombreComp.value)+"&idJerarquia="+jerarquiComp.value;
			
			salidaComp.innerHTML = "<DIV align='center' style='padding: 2px; background: rgb(204, 68, 68) none repeat scroll 0%; z-index: 3; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; color: white; font-size: 95%; top: 1px; right: 16px; '> Cargando...</DIV>";
		
			ajax = nuevoAjax();
		
			ajax.open("POST", xGetContextPath()+"/admin/curso/Buscar.action", true);
			ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			ajax.send(cadena);
			ajax.onreadystatechange = function () {
				if (ajax.readyState === 4) {
					salidaComp.innerHTML = ajax.responseText;
					SortableTable.load();
				}
			}
		}else{
			alert("Ha ingresado caracteres no v·lidos o la longitud de la frase es menor a 2.");
		}
	}else{
		alert("Ingrese un nombre a buscar.");
	}
}
function mostrarNuevo(boton) {
	var panel = document.getElementById("unidad_nuevo");
	panel.style.display = (panel.style.display != "block") ? "block" : "none";
	boton.value = (boton.value != "Nuevo") ? "Nuevo" : "Ocultar";
}
var cuenta=0;
function crearCurso(form){
	if (cuenta == 0) {
		form.nombre.value = form.nombre.value.trim();
		if(false && !validarCaracteres(form.nombre.value)){
			alert("Ingrese un nombre v·lido.");
			return false;
		}
		cuenta++;
		ajax = nuevoAjax();
		
		ajax.open("POST", xGetContextPath()+"/admin/curso/Crear.action", true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send("nombre="+escape(form.nombre.value)+"&idJerarquia="+form.idJerarquia.value);
		ajax.onreadystatechange = function () {
			if (ajax.readyState === 4) {
				if(ajax.responseText.trim() == 'OK'){
					showMessage("El curso "+form.nombre.value+" ha sido creado de manera satisfactoria.");
					cuenta = 0;
					
					var jerarquiComp = xGetElementById("idJerarquia");
					jerarquiComp.value = form.idJerarquia.value;
					var nombreComp = xGetElementById("nombre");
					nombreComp.value = form.nombre.value;
					
					form.reset();
					mostrarNuevo(xGetElementById("btNuevo"));
					
					buscarCursos();
				}else{
					alert("Hubo un error al intentar crear el curso.")
				}
			}
		}
		return false;
	} else {
		alert("Su peticiÛn ya est· siendo procesada, por favor aguarde un instante.");
		return false;
	}
}

function eliminarCurso(id){ 
	if(window.confirm('øEsta seguro de eliminar el elemento? ')){
		
		ajax = nuevoAjax();
	
		ajax.open("POST", xGetContextPath()+"/admin/curso/Eliminar.action", true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send("idCurso="+id);
		ajax.onreadystatechange = function () {
			if (ajax.readyState === 4) {
				if(ajax.responseText.trim() == 'OK'){
					showMessage("El curso ha sido eliminado de manera satisfactoria.");
					buscarCursos();
				}else{
					alert("Hubo un error al intentar eliminar el curso.")
				}
			}
		}
	}
}

//Silabo
//********************************************************************************************//
var primeraVez=true;

function mostrarSilabos(numFila,idCurso,nombreCurso){
	
	recortarFila(numFila);
	xShowD("form_Silabo");
	xShowD("resultado_silabo");
	
	if(!nombreCurso || nombreCurso == '')nombreCurso = xInnerHtml('curso_nombre');
	
	xGetElementById("form_Silabo_idCurso").value = idCurso;
	xGetElementById('form_Silabo_nombre').value = nombreCurso;
	// Entrada
	
	//Salida
	var salidaComp = xGetElementById("resultado_silabo");
	
	var cadena = "idCurso="+idCurso;
		
	salidaComp.innerHTML = "<DIV align='center' style='padding: 2px; background: rgb(204, 68, 68) none repeat scroll 0%; z-index: 3; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; color: white; font-size: 95%; top: 1px; right: 16px; '> Cargando...</DIV>";
	
	ajax = nuevoAjax();
	
	ajax.open("POST", xGetContextPath()+"/admin/silabo/BuscarPorIDCurso.action", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send(cadena);
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			salidaComp.innerHTML = ajax.responseText;
		}
	}
}

function recortarFila(numFila){
	if(primeraVez){
		var numFilas = xGetElementById("numFilas").value;
		for(var i=1; i<=numFilas; i++){
			if(i != numFila){
				var filaTR = xGetElementById("curso_"+i);
				if(filaTR != null)
					filaTR.parentNode.removeChild(filaTR);
				else
					alert("null");
			}
		}
		primeraVez=false;
	}
}

function verUnidadesSilabo(idSilabo){
	var URL = xGetContextPath()+"/admin/silabo/VerUnidades.action?idSilabo="+idSilabo;
	abrirPopUp(URL);
}
function abrirPopUp(url){
	var ancho = 800;
	var alto = 550;
    var X = (screen.availWidth - ancho)/2;
    var Y = (screen.availHeight - alto)/2;
	texto = window.open(url, 'UnidadesSilabo', 'width='+ancho+',height='+alto+',toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=0,top='+Y+',left='+X+''); 
	texto.focus();
}

function crearSilabo() {

	var nombre = xGetElementById('form_Silabo_nombre').value;
	
	if(trim(nombre) != ""){
		if(validarCaracteres(nombre)){
		    //Entrada
			var idCurso = xGetElementById("form_Silabo_idCurso").value;
			var cadena = "idCurso="+idCurso+"&nombre="+escape(nombre);
			//Salida
			var salidaComp = xGetElementById("resultado_silabo");
			
			ajax = nuevoAjax();
			
			ajax.open("POST", xGetContextPath()+"/admin/silabo/Crear.action", true);
			ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			ajax.send(cadena);
			ajax.onreadystatechange = function () {
				if (ajax.readyState === 4) {
					salidaComp.innerHTML = ajax.responseText;
					xGetElementById('form_Silabo_nombre').value = "";
				}
			}
		}else{
			alert("Ha ingresado caracteres no v·lidos o la longitud de la frase es menor a 2.");
		}
	}else{
		alert("Ingrese un nombre para el nuevo sÌlabo.");
	}
}
var formModificarSilabo = "";
function mostrarModificarSilabo(idSilabo){

	xGetElementById("silabo_formMod_nombre_"+idSilabo).value = xGetElementById("silabo_nombre_"+idSilabo).innerHTML;

	if(formModificarSilabo != ""){
		xHideD("silabo_formMod_"+formModificarSilabo);
	}

	formModificarSilabo = idSilabo;

	xShowD("silabo_formMod_"+idSilabo);
}
function ocultarModificarSilabo(idSilabo){
	xHideD("silabo_formMod_"+idSilabo);
}

function seleccionBlue(TR, valor) {
	if (valor) {
		TR.style.background = "#64A5F4";
	} else {
		TR.style.background = "#9EC8F5";
	}
}

function modificarSilabo(idSilabo) {

	var nuevoNombre = xGetElementById("silabo_formMod_nombre_"+idSilabo).value;
	var anteriorNombre = xGetElementById("silabo_nombre_"+idSilabo).innerHTML;
	if(trim(nuevoNombre) != anteriorNombre){
		if (trim(nuevoNombre) != "") {
			if(validarCaracteres(nuevoNombre)){
			    //Entrada
			    var cadena = "idSilabo="+idSilabo+"&nombre="+escape(nuevoNombre);
				//Salida
				
				ajax = nuevoAjax();
				
				ajax.open("POST", xGetContextPath()+"/admin/silabo/Modificar.action", true);
				ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				ajax.send(cadena);
				ajax.onreadystatechange = function () {
					if (ajax.readyState === 4) {
						var resultado = ajax.responseText;
						
						if(resultado == '1'){
							xGetElementById("silabo_nombre_"+idSilabo).innerHTML = xGetElementById("silabo_formMod_nombre_"+idSilabo).value;
							ocultarModificarSilabo(idSilabo);
						}else{
							alert("Error al intentar cambiar el nombre.");
						}
					}
				}
			}else{
				alert("Ha ingresado caracteres no v·lidos o la longitud de la frase es menor a 2.");
			}	
		}else{
			alert("Ingrese un nombre para el sÌlabo.");
		}
	}
}

function eliminarSilabo(idSilabo,nombre) {

	var flag = window.confirm("Esta seguro de eliminar la asignaciÛn \"" + nombre + "\" del presente curso ? ");
	
	if(flag){
		
		//Entrada
		var idCurso = xGetElementById("form_Silabo_idCurso").value;
				
		//Salida
		var salidaComp = xGetElementById("resultado_silabo");
		
		ajax = nuevoAjax();
		
		ajax.open("POST", xGetContextPath()+"/admin/silabo/Eliminar.action", true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send("idSilabo="+idSilabo+"&idCurso="+idCurso);
		ajax.onreadystatechange = function () {
			if (ajax.readyState === 4) {
				salidaComp.innerHTML = ajax.responseText;
				formModificarSilabo = "";
			}
		}
	}
	
}
//Silabo Unidad
//********************************************************************************************//

function mostrar(id) {
	var numFilas = xGetElementById("numFilas").value;
	for (i = 1; i <= numFilas; i++) {
		var panel_tmp = xGetElementById("unidad_detalle_" + i);
		if (i != id) {
			panel_tmp.style.display = "none";
		}
	}
	var panel = xGetElementById("unidad_detalle_" + id);
	panel.style.display = (panel.style.display != "block") ? "block" : "none";
}
function agregarUnidad(){
	//Entrada
	var idUnidad = xGetElementById("idUnidad").value;
	var idSilabo = xGetElementById("idSilabo").value;
	var cadena = "idUnidad="+idUnidad+"&idSilabo="+idSilabo;
	
	//Salida
	var divSalida = xGetElementById("Unidades_Silabo");
	
	if(idUnidad != ""){
		ajax = nuevoAjax();
		
		ajax.open("POST", xGetContextPath()+"/admin/silabo/AgregarUnidad.action", true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send(cadena);
		ajax.onreadystatechange = function () {
			if (ajax.readyState === 4) {
				divSalida.innerHTML = ajax.responseText;
			}
		}
		xGetElementById("input_2").value="";
		xGetElementById("idUnidad").value="";
	}else{
		alert("La unidad indicada no existe.");
	}
}

function eliminarUnidad(idSilabo,idUnidad,nombre){
	var flag = window.confirm("Esta seguro de eliminar \"" + nombre + "\" del presente grupo ? ");
	//alert(URL);
	
	if(flag){
		//Entrada
		
		//Salida
		var divSalida = xGetElementById("Unidades_Silabo");
		
		ajax = nuevoAjax();
		
		ajax.open("POST", xGetContextPath()+"/admin/silabo/EliminarUnidad.action", true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send("idSilabo="+idSilabo+"&idUnidad="+idUnidad);
		ajax.onreadystatechange = function () {
			if (ajax.readyState === 4) {
				divSalida.innerHTML = ajax.responseText;
			}
		}
	}
	return flag;
}
function cambiarIndice(cmd,idSilabo,idUnidad){
	//Entrada
		
	//Salida
	var divSalida = xGetElementById("Unidades_Silabo");
	
	ajax = nuevoAjax();
	
	ajax.open("POST", xGetContextPath()+"/admin/silabo/ModificarIndice.action", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("cmd="+cmd+"&idSilabo="+idSilabo+"&idUnidad="+idUnidad);
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			divSalida.innerHTML = ajax.responseText;
		}
	}
}
function decodeResponse(response){
	return unescape(response).replace(/\+/gi," ");
}
function toHex(schar){
	var claveHex ="";
	var array = schar.split("");
	for(i=0;i<array.length;i++){
		claveHex += "%"+array[i].charCodeAt(0).toString(16);
	}
	return claveHex;
}
//********************************************************************************************//
function asignaVariables()
{
	// Funcion que asigna variables que se usan a lo largo de las funciones	
	v=1; nuevaBusqueda=1; busqueda=null; ultimaBusquedaNula=null;
	divLista=document.getElementById("lista");
	inputLista=document.getElementById("input_2");
	elementoSeleccionado=-1;
	ultimoIdentificador=0;
}

function formateaLista(valor)
{
	// Funcion encargada de ir colocando en negrita las palabras y asignarle un ID a los elementos
	var x=0, verificaExpresion=new RegExp("("+valor+")","i");
	while(divLista.childNodes[x]!=null)
	{
		// Asigo el ID para reconocerlo cuando se navega con el teclado
		//divLista.childNodes[x].id=x+1;
		// Coloco en cada elemento de la lista en negrita lo que se haya ingresado en el input
		divLista.childNodes[x].innerHTML=divLista.childNodes[x].innerHTML.replace(verificaExpresion, "<b>$1</b>");
		x++;
		//alert(divLista.childNodes[x].innerHTML);
	}
}

function limpiaPalabra(palabra)
{
	// Funcion encargada de sacarle el codigo HTML de la negrita a las palabras
	palabra=palabra.replace(/<b>/i, "");
	palabra=palabra.replace(/<\/b>/i, "");
	return palabra;
}

function coincideBusqueda(palabraEntera, primerasLetras)
{
	/* Funcion para verificar que las primeras letras de busquedaActual sean iguales al
	contenido de busquedaAnterior. Se devuelve 1 si la verificacion es afirmativa */
	if(primerasLetras==null) return 0;
	var verificaExpresion=new RegExp("("+primerasLetras+")", "i");
	if(verificaExpresion.test(palabraEntera)) return 1;
	else return 0;
}

function nuevaCadenaNula(valor)
{
	/* Seteo cual fue la ultima busqueda que no arrojo resultados siempre y cuando la cadena
	nueva no comience con las letras de la ultima cadena que no arrojo resultados */
	if(coincideBusqueda(valor, ultimaBusquedaNula)==0) ultimaBusquedaNula=valor;
}

function busquedaEnBD()
{
	/* Funcion encargada de verificar si hay que buscar el nuevo valor ingresado en la base
	de datos en funcion de los resultados obtenidos en la ultima busqueda y en base a que
	la cadena bsucada anteriormente este dentro de la nueva cadena */
	var valor=inputLista.value;
	
	if((coincideBusqueda(valor, busqueda)==1 && nuevaBusqueda==0) || coincideBusqueda(valor, ultimaBusquedaNula)==1) return 0;
	else return 1;
}

function filtraLista(valor)
{
	// Funcion encargada de modificar la lista de nombres en base a la nueva busqueda
	var x=0;

	while(divLista.childNodes[x]!=null)
	{
		// Saco la negrita a los elementos del listado
		divLista.childNodes[x].innerHTML=limpiaPalabra(divLista.childNodes[x].innerHTML);
		if(coincideBusqueda(limpiaPalabra(divLista.childNodes[x].innerHTML), valor)==0)
		{
			/* Si remuevo el elemento x, el elemento posterior pasa a ocupar la posicion de
			x, entonces quedaria sin revisar. Por eso disminuyo 1 valor a x */
			divLista.removeChild(divLista.childNodes[x]);
			x--;
		}
		x++;
	}
}

function reiniciaSeleccion()
{
	mouseFuera(); 
	elementoSeleccionado=-1;
}

function navegaTeclado(evento)
{

	var teclaPresionada=(document.all) ? evento.keyCode : evento.which;
	
	switch(teclaPresionada)
	{
		case 40: //Abajo
		if(elementoSeleccionado<divLista.childNodes.length-1-nuevaBusqueda)
		{	
			mouseDentro(divLista.childNodes[elementoSeleccionado+1]);
			elementoSeleccionado++;
		}
		return 0;
		
		case 38: //Arriba
		if(elementoSeleccionado>0)
		{
			mouseDentro(divLista.childNodes[elementoSeleccionado-1]);
			elementoSeleccionado--;
		}
		return 0;
		
		case 39: //Derecha
		if(divLista.style.display=="block" && elementoSeleccionado!=-1)
		{
			clickLista(divLista.childNodes[elementoSeleccionado])
		}
		return 0;
		
		default: return 1;
	}
}		

function rellenaLista()
{
	var valor=inputLista.value;
	var inputIDSilabo=document.getElementById("idSilabo");

	// Valido con una expresion regular el contenido de lo que el usuario ingresa
	var reg=/(^[a-zA-Z0-9.@Ò·ÈÌÛ˙—¡…Õ”⁄ ]{2,40}$)/;
	if(false/*!reg.test(valor)*/) divLista.style.display="none";
	else
	{
		if(busquedaEnBD()==0)
		{	
			// Si no hay que buscar el valor en la BD
			busqueda=valor;
	
			// Hago el filtrado de la nueva cadena ingresada
			filtraLista(valor);
			// Si no quedan elementos para mostrar en la lista
			if(divLista.childNodes[0]==null) { divLista.style.display="none"; nuevaCadenaNula(valor); }
			else { reiniciaSeleccion(); formateaLista(valor); }
		}
		else
		{	
			/* Si se necesita verificar la base de datos, guardo el patron de busqueda con el que se
			busco y luego recibo en una variable si existen mas resultados de los que se van a mostrar */
			busqueda=valor;
			ajax=nuevoAjax();
			ajax.open("POST", xGetContextPath()+"/admin/silabo/GetUnidades.action", true);
			ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			//valor = toHex(valor.toUpperCase().replace('¡','A').replace('…','E').replace('Õ','I').replace('”','O').replace('⁄','U'));
			ajax.send("nombre="+toHex(valor)+"&idSilabo="+inputIDSilabo.value);

			ajax.onreadystatechange=function()
			{	
				if (ajax.readyState==4)
				{
				
					if(!ajax.responseText) { divLista.style.display="none"; }
					else 
					{
						var respuesta=new Array(2);
						respuesta=ajax.responseText.split("&");
						/* Obtengo un valor que representa si tengo que ir a BD en las proximas 
						busquedas con cadena similar */
						nuevaBusqueda=respuesta[0];
				
						// Si se obtuvieron datos los muestro
						if(respuesta[1]!="vacio") 
						{ 
							divLista.style.display="block";
							divLista.innerHTML=respuesta[1]; 
							// Coloco en negrita las palabras
							reiniciaSeleccion();
							formateaLista(valor); 
						}
						// En caso contrario seteo la busqueda actual como una busqueda sin resultados
						else nuevaCadenaNula(valor);
					}
				}
			}
		}
	}
}

function clickLista(elemento)

{
	/* Se ejecuta cuando se hace clic en algun elemento de la lista. Se coloca en el input el
	valor del elemento clickeado */
	v=1;
	valor=limpiaPalabra(elemento.innerHTML); 
	busqueda=valor; inputLista.value=valor;
	divLista.style.display="none"; elemento.className="normal";
	document.getElementById("idUnidad").value = elemento.id;
}

function mouseFuera()
{
	// Des-selecciono el elemento actualmente seleccionado, si es que hay alguno
	if(elementoSeleccionado!=-1 && divLista.childNodes[elementoSeleccionado]){
		divLista.childNodes[elementoSeleccionado].className="normal";
	}
	
}

function mouseDentro(elemento,nElemento)
{	
	mouseFuera();
	elemento.className="resaltado";
	if(nElemento)
		elementoSeleccionado=nElemento;
}