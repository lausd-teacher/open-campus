var tema = {};
var mensaje = {};

tema.cerrar = function(img,idTema){
	var cmd = xGetElementById('estado_'+idTema).value;
	if(cmd == '0'){
		cmd = 'cerrar';
	}else{
		cmd = 'abrir';
	}
	var cadena = "idTema="+idTema+"&destino="+cmd;
	
	var myConn = new XHConn();
	var query = function (oXML) { 
		if(oXML.responseText === 'OK'){
			if(cmd === 'cerrar'){
				xGetElementById('estado_'+idTema).value = 1;
				img.src = xGetContextPath()+"/img/icon_candado.jpeg";
			}else{
				xGetElementById('estado_'+idTema).value = 0;
				img.src = xGetContextPath()+"/img/icon_candado_abierto.jpeg";
			}
		}else{
			alert('Hubo un error al intentar guardar, se sugiere actualizar la página.');
		}
	};
	myConn.connect(xGetContextPath()+"/foro/CambiarEstado.action", "POST", cadena, query);
};

tema.eliminar = function(idTema){
	var flag = window.confirm("¿Esta seguro de eliminar el tema? ");
	if(flag){
		var cadena = "idTema="+idTema;
		
		var myConn = new XHConn();
		var query = function (oXML) { 
			if(oXML.responseText === 'OK'){
				var fila = xGetElementById('tema_fila_'+idTema);
				fila.parentNode.removeChild(fila);
			}else{
				alert('Hubo un error al intentar guardar, se sugiere actualizar la página.');
			}
		};
		myConn.connect(xGetContextPath()+"/foro/EliminarTema.action", "POST", cadena, query);
	}
};

tema.nuevo = function(){
	$('nuevoTema').style.display = 'block';
	$('btnNvoTma').style.display = 'none';
	if(!$('contenidoWY')){
		generate_wysiwyg('cont', '622', '100');
	}
};

function validaNuevoTema(){
	
}

tema.cancelar = function(){
	$('btnNvoTma').style.display = 'block';
	$('nuevoTema').style.display = 'none';	
};

function enviarCorreo(idUsuario,titulo) {
	if(!titulo)titulo='';
	window.open(xGetContextPath() + "/comun/buzon/NuevoMensaje.action?destino=" + idUsuario+"&titulo="+titulo, "Saludos", "height=330,width=510,scrollbars=no");
	return true;
}
mensaje.reenviar = function(eleId){
	$('div_'+eleId).style.display='block';
	$('para_'+eleId).style.display='none';
};

mensaje.cancelar = function(eleId){
	$('div_'+eleId).style.display='none';
	$('para_'+eleId).style.display='none';
};

mensaje.eliminar = function(idMensaje){
	var flag = window.confirm("¿Esta seguro de eliminar el mensaje? ");
	if(flag){
		var cadena = "idMensaje="+idMensaje;
		
		var myConn = new XHConn();
		var query = function (oXML) { 
			if(oXML.responseText === 'OK'){
				var fila = xGetElementById('head_'+idMensaje);
				fila.parentNode.removeChild(fila);
				fila = xGetElementById('pop_'+idMensaje);
				fila.parentNode.removeChild(fila);
				fila = xGetElementById('space_'+idMensaje);
				fila.parentNode.removeChild(fila);
			}else{
				alert('Hubo un error al intentar guardar, se sugiere actualizar la página.');
			}
		};
		myConn.connect(xGetContextPath()+"/foro/EliminarMensaje.action", "POST", cadena, query);
	}
};

mensaje.responder = function(eleId,ctx){
	xChangeDisplay('div_'+eleId);
	if(!$('wysiwygcuerpo'+eleId)){	
		generate_wysiwyg('cuerpo'+eleId, '800', '100');
	}
};

var ctx;

tema.valorar = function(ele){
	var n = ele.id;
	var imgY = ctx+'/img/icon_importante_y.gif';
	var imgN = ctx+'/img/icon_importante_n.gif';
	
	var ajax = nuevoAjax();
	var url = xGetContextPath() + "/foro/Calificar.action";
	ajax.open("POST", url, true);
	
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("calificacion="+n);
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			var txt = ajax.responseText;
			if(txt === '-1'){
				alert("Ud. no puede valorar su propio tema. Lo sentimos mucho.")
			}else{
				for(var i=1 ; i<= n ; i++){
					$('stars').getElementsByTagName('img')[i-1].src = imgY;
				}
				
				try{
					for(var i=n ; i<= 5 ; i++){
						$('stars').getElementsByTagName('img')[i].src = imgN;
					}
				} catch(e){}
			}
		}
	};
	
};

tema.starties = function(ctxt){
	ctx = ctxt;
	var img = ctx+'/img/icon_importante_n.gif';
	var estrellas = $('stars').getElementsByTagName('img').length;
	var tmp = 5 - estrellas;
	for(var i=0 ; i<tmp ; i++){
		$('stars').innerHTML += '<img style="cursor:pointer" onclick="tema.valorar(this)" src="'+img+'" width="12" height="12" id="'+(estrellas+1)+'">';
		estrellas++;
	}
};


function valida(ele){	
	var con = $("wysiwygcuerpo" + ele.id.split('_')[1]).contentWindow.document.body.innerHTML;
	var conClear = stripNewLine(con.stripTags().stripScripts().strip().replace(/&nbsp;/g,""));
		
	if(conClear.length == 0){
		return false;
	}else{
		return true;
	}
}

function validaTema(){	
	var con = $("wysiwygcont").contentWindow.document.body.innerHTML;	
	var conClear = stripNewLine(con.stripTags().stripScripts().strip().replace(/&nbsp;/g,""));
	
	var tT = trim($('tituloTema').value).length;
	
	if(tT == 0){
		return false;
	}
	if(conClear.length == 0){
		return false;
	}
	
}

/** PM **/

function verReglamento(){
	xMoveTo('blocker',xScrollLeft(),xScrollTop()-10);
	xResizeTo('blocker',xClientWidth(),xClientHeight()+10);
	xShowD('blocker');
	xMoveTo('help',(xClientWidth()-500)/2,xScrollTop()+(xClientHeight()-400)/2);
	xShowD('help');
}

function resize() {
	xResizeTo('blocker',xClientWidth(),xClientHeight()+10);
}

function scrolling() {
	xMoveTo('blocker', xScrollLeft(),xScrollTop()-10);
}


function setTipoDeBusqueda(inp) {
	var bloqueUsuario = xGetElementById("inpUsuario");
	var bloqueTema = xGetElementById("inpTema");
 	if(inp.name === 'claveTema'){
 		xGetElementById("txtUsuario").value = "";
 		xGetElementById("hdnUsuario").value = "";
 		bloqueUsuario.setAttribute('style',bloqueUsuario.getAttribute('style')+'-moz-opacity:.3;');
		bloqueUsuario.style.filter = 'alpha(opacity=30)';
		bloqueTema.setAttribute('style',bloqueTema.getAttribute('style')+'-moz-opacity:1.0;');
		bloqueTema.style.filter = 'alpha(opacity=100)';
 	}else{
 		xGetElementById("txtTema").value = "";
 		bloqueUsuario.setAttribute('style',bloqueUsuario.getAttribute('style')+'-moz-opacity:1.0;');
		bloqueUsuario.style.filter = 'alpha(opacity=100)';
		bloqueTema.setAttribute('style',bloqueTema.getAttribute('style')+'-moz-opacity:.3;');
		bloqueTema.style.filter = 'alpha(opacity=30)';
 	}
}

function validarBusqueda(form){
	
	form.claveTema.value = form.claveTema.value.stripWithSpace();
	if(form.claveTema.value === '' && form.usuario.value === ''){
		alert("Debe usar algún criterio de búsqueda.");
		return false;
	}
	
	var query = "?claveTema="+form.claveTema.value
				+"&usuario="+form.usuario.value
				+"&fechaLimite="+form.fechaLimite.options[form.fechaLimite.selectedIndex].value
				+"&enforos="+form.enforos.options[form.enforos.selectedIndex].value
				+"&titulo="+form.titulo.value;
		
	if(form.clavecompleta.checked){
		query += "&clavecompleta="+form.clavecompleta.value;
	}
	if(form.solotema.checked){
		query += "&solotema="+form.solotema.value;
	}
	
	form.reset();
	
	document.location=form.action+query;
	
	return false;
}

//*********************************** LIST BUSCAR *************************************//
function asignaVariables()
{
	// Funcion que asigna variables que se usan a lo largo de las funciones	
	v=1; nuevaBusqueda=1; busqueda=null; ultimaBusquedaNula=null;
	divLista=document.getElementById("lista");
	inputLista=document.getElementById("txtUsuario");
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
	
	// Valido con una expresion regular el contenido de lo que el usuario ingresa
	var reg=/(^[a-zA-Z0-9.@ñáéíóúÑÁÉÍÓÚ ]{2,40}$)/;
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
			ajax.open("POST", xGetContextPath()+"/foro/BuscarUsuarios.action", true);
			ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			//valor = toHex(valor.toUpperCase().replace('Á','A').replace('É','E').replace('Í','I').replace('Ó','O').replace('Ú','U'));
			ajax.send("usuario="+toHex(valor));

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
	document.getElementById("hdnUsuario").value = elemento.id;
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


function toHex(chars){
	var claveHex ="";
	var array = chars.split("");
	for(i=0;i<array.length;i++){
		claveHex += "%"+array[i].charCodeAt(0).toString(16);
	}
	return claveHex;
}
