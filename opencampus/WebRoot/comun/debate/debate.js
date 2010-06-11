
function validaTema(){	

	var con = $("wysiwygcuerpo").contentWindow.document.body.innerHTML;	
	var conClear = stripNewLine(con.stripTags().stripScripts().strip().replace(/&nbsp;/g,""));

	var tT = trim($('tituloTema').value).length;

	if(tT == 0){
		alert("Debe indicar el asunto.");
		return false;
	}
	if(conClear.length == 0){
		alert("Debe incluir algún comentario.")
		return false;
	}
	
}


function cancelarPadre(){
	xHideD('nuevoTema');
	xShowD('btnNvoTma');
}

function nuevoPadre(){
	xShowD('nuevoTema');
	xHideD('btnNvoTma');
	if(!xGetElementById('wysiwygcuerpo')){
		generate_wysiwyg('cuerpo', '570', '100');
	}
}

function nuevo(idDialogo,asunto){
	var divOrigen = xGetElementById('div_form');
	var divDestino = xGetElementById('nuevoTema');
	divDestino.appendChild(divOrigen);
	var editor = xGetElementById("wysiwygtexto");
	var iframe = xGetElementById("contenidoWY");
	var toolbar1 = xGetElementById("toolbarWY1");
	var toolbar2 = xGetElementById("toolbarWY2");
	iframe.style.width = '100%';
	editor.style.width = '100%';
	toolbar1.style.width = '100%';
	toolbar2.style.width = '100%';
	xShowD('div_form');
	xGetElementById("form_predecesor").value = idDialogo;
	xGetElementById("form_asunto").value = 'RE: '+asunto;
	refrescarEditor()
}

function responder(idDialogo,asunto){
	var divOrigen = xGetElementById('div_form');
	var divDestino = xGetElementById('form_dia_'+idDialogo);
	divDestino.appendChild(divOrigen);
	var editor = xGetElementById("wysiwygtexto");
	var iframe = xGetElementById("contenidoWY");
	var toolbar1 = xGetElementById("toolbarWY1");
	var toolbar2 = xGetElementById("toolbarWY2");
	iframe.style.width = '100%';
	editor.style.width = '100%';
	toolbar1.style.width = '100%';
	toolbar2.style.width = '100%';
	xShowD('div_form');
	xGetElementById("form_predecesor").value = idDialogo;
	xGetElementById("form_asunto").value = 'RE: '+asunto;
	refrescarEditor()
}

function cancelar(){
	xHideD('div_form');
}

function refrescarEditor(){
	var doc = document.getElementById("wysiwyg" + "texto").contentWindow.document;
	doc.open();
	//doc.write("");
	doc.close();
	doc.body.contentEditable = true;
	doc.designMode = "on";
}

function seleccionNoticia(Input, valor) {
	if (valor) {
		Input.style.background = "#FFFBE8";
		Input.style.borderColor = "#EFDA45";
	} else {
		Input.style.background = "#FFFFFF";
		Input.style.borderColor = "#AAABAF";
	}
}

function valida(form){	
	
	if(form.asunto.value.isEmpty()){
		alert("Debe indicar el asunto.");
		return false;
	}
	
	var con = xGetElementById("wysiwygtexto").contentWindow.document.body.innerHTML;
	var conClear = stripNewLine(con.stripTags().stripScripts().strip().replace(/&nbsp;/g,""));
		
	if(conClear.length === 0){
		alert("Debe incluir algún comentario.")
		return false;
	}
	
	/*if(con.length > 4000){
		alert("Su comentario supera el límite de los 4000 caracteres.")
		return false;
	}*/
	
	if(form.predecesor.value.isEmpty()){
		alert("Error, actualice la página.");
		return false;
	}
	
	if(form.predecesor.value === form.owner.value){
		form.submit.disabled = true;
		return true;
	}
	
	return crearDialogoAsync(form);
}

function crearDialogoAsync(form){
	form.submit.disabled = false;
	xHideD('div_form');
	var texto = xGetElementById("wysiwygtexto").contentWindow.document.body.innerHTML;
	var cadena = "asunto="+escape(form.asunto.value)+"&texto="+escape(texto)+"&predecesor="+form.predecesor.value;
			
		var myConn = new XHConn();
		var query = function (oXML) { 
			if(oXML.responseText === 'OK'){
				listarSubTertulias(form.predecesor.value,true);
			}else{
				alert('Hubo un error al intentar crear, se sugiere actualizar la página.');
			}
		};
		myConn.connect(xGetContextPath()+"/aulavirtual/debate/CrearAsync.action", "POST", cadena, query);
	return false;
}

function marcarLeido(input,idDialogo){
	var cmd = 1;
	if(input.value === 'Marcar no leído'){
		cmd = 0;
	}
	
	var cadena = "idDebate="+idDialogo+"&owner="+cmd;
		
	var myConn = new XHConn();
	var query = function (oXML) { 
		if(oXML.responseText === 'OK'){
			if(input.value === 'Marcar no leído'){
				input.value = 'Marcar leído';
				xGetElementById('flag_'+idDialogo).src = xGetContextPath()+"/img/flag.gif";
			}else{
				input.value = 'Marcar no leído';
				xGetElementById('flag_'+idDialogo).src = xGetContextPath()+"/img/cal.gif";
			}
		}else{
			alert('Hubo un error al intentar guardar, se sugiere actualizar la página.');
		}
	};
	myConn.connect(xGetContextPath()+"/aulavirtual/debate/MarcarLeido.action", "POST", cadena, query);
}

function eliminar(idDialogo){
	if(confirm('¿Está seguro que desea eliminar el diálogo?')){
		var cadena = "idDebate="+idDialogo;
			
		var myConn = new XHConn();
		var query = function (oXML) { 
			if(oXML.responseText === 'OK'){
				var table = xGetElementById('dialogo_'+idDialogo);
				table.parentNode.removeChild(table);
			}else{
				alert('Hubo un error al intentar eliminar, se sugiere actualizar la página.');
			}
		};
		myConn.connect(xGetContextPath()+"/aulavirtual/debate/EliminarAsync.action", "POST", cadena, query);
	}
}

function listarSubTertulias(idDialogo,reload){
	
	if(reload || xGetElementById('fila_cuerpo_'+idDialogo).style.display == 'none'){
	
		var cadena = "idDebate="+idDialogo;//+"&destino="+cmd;
		
		var myConn = new XHConn();
		var query = function (oXML) { 
			if(oXML.responseText !== ''){
				var xml = oXML.responseXML;
				if(xml.getElementsByTagName("debates").length !== 0){
					showEvents(idDialogo,xml);
				}
				
			}else{
				alert('Hubo un error al intentar cargar, se sugiere actualizar la página.');
			}
		};
		myConn.connect(xGetContextPath()+"/aulavirtual/debate/CargarTertulia.action", "POST", cadena, query);
	
	}else{
		
		var tdPri = xGetElementById('fila_sub_'+idDialogo).cells[0];
		xInnerHtml(tdPri,'');
		xHideD('fila_sub_'+idDialogo);
		
		tdPri = xGetElementById('fila_cuerpo_'+idDialogo).cells[1];
		xInnerHtml(tdPri,'');
		xHideD('fila_cuerpo_'+idDialogo);
		
		xHideD('fila_form_'+idDialogo);
		
		var imgMas = xGetElementById('mas_'+idDialogo);
		if(imgMas) imgMas.src = xGetContextPath()+"/img/mas.jpg";
		
	}
	
}

function showEvents(id,xml){
	//alert(oXML.responseText);
	var imgMas = xGetElementById('mas_'+id);
	if(imgMas) imgMas.src = xGetContextPath()+"/img/menos.jpg";
	
	//Sub
	tdPri = xGetElementById('fila_sub_'+id).cells[0];
	xInnerHtml(tdPri,'');
	
	//Cuerpo
	var tdPri = xGetElementById('fila_cuerpo_'+id).cells[1];
	var cuerpo = xml.getElementsByTagName("cuerpo")[0].childNodes[0].nodeValue;
	xInnerHtml(tdPri,cuerpo);
	xShowD('fila_cuerpo_'+id);
	
	imgMas = xGetElementById('flag_'+id);
	if(imgMas) imgMas.src = xGetContextPath()+"/img/cal.gif";
	
	xShowD('fila_form_'+id);
	
	//Recorrer el Arreglo
	for (var x = 0; x < xml.getElementsByTagName("debate").length; x = x + 1) {
		//Extraer valiables
		var dialogo = xml.getElementsByTagName("debate")[x];
		
		var idDialogo = dialogo.getElementsByTagName("idDebate")[0].childNodes[0].nodeValue;
		var asunto = dialogo.getElementsByTagName("asunto")[0].childNodes[0].nodeValue;
		var fecha = dialogo.getElementsByTagName("fecha")[0].childNodes[0].nodeValue;
		var nombreUsuario = dialogo.getElementsByTagName("nombreUsuario")[0].childNodes[0].nodeValue;
		var leido = dialogo.getElementsByTagName("leido")[0].childNodes[0].nodeValue;
		var owner = dialogo.getElementsByTagName("owner")[0].childNodes[0].nodeValue;
		var predecesor = dialogo.getElementsByTagName("predecesor")[0].childNodes[0].nodeValue;
		var cantidadSub = dialogo.getElementsByTagName("cantidadSubPlactica")[0].childNodes[0].nodeValue;
		
		
		tdPri = xGetElementById('fila_sub_'+id).cells[0];
		
		
		//Creacion de Tabla para un dialogo	
		var table = xCreate('table');
		//table.className = 'tabla_sin_layout';
		table.style.width = '100%';
		table.id = 'dialogo_'+idDialogo;
		table.cellPadding="4";
		table.cellSpacing="0";
		//table.className = 'bor_tabla tabla_sin_layout';
		var tbody = xCreate("tbody");
		//*****
		
		var tr = xCreate('tr');
		tr.id = 'title_' + idDialogo;
		tr.onclick = function(){
			//alert(this.id.split('_')[1]);
			listarSubTertulias(this.id.split('_')[1]);
		};
		
		tr.onmouseover = function(){
			this.style.background = "#FFFBE8";
			this.style.borderColor = "#EFDA45";
		};
		
		tr.onmouseout = function(){
			this.style.background = "#FFFFFF";
			this.style.borderColor = "#AAABAF";
		};
		tr.style.cursor = "pointer";
				
		var td = xCreate('td');
		td.style.width = '14px';
		if(cantidadSub>0)
			td.innerHTML = '<img id="mas_'+idDialogo+'" src="'+xGetContextPath()+'/img/mas.jpg" alt="Desplegar" />';
		else
			td.innerHTML = '<img id="nada_'+idDialogo+'" src="'+xGetContextPath()+'/img/nada.jpg" />';
		tr.appendChild(td);
		
		td = xCreate('td');
		td.style.width = '10px';
		if(leido == 0)
			td.innerHTML = '<img id="flag_'+idDialogo+'" src="'+xGetContextPath()+'/img/flag.gif" width="8"/>';
		else
			td.innerHTML = '<img id="flag_'+idDialogo+'" src="'+xGetContextPath()+'/img/cal.gif" width="8"/>';
		tr.appendChild(td);
		
		var text = document.createTextNode(asunto);
		
		var strong = xCreate('strong');
		strong.appendChild(text);
		
		td = xCreate('td');
		td.appendChild(strong);
		tr.appendChild(td);
		
		td = xCreate('td');
		td.style.width = '160px';
		td.innerHTML = nombreUsuario;
		tr.appendChild(td);
		
		td = xCreate('td');
		td.style.width = '100px';
		td.align = 'center';
		td.innerHTML = fecha;
		tr.appendChild(td);
		
		td = xCreate('td');
		td.style.width = '50px';
		td.align = 'center';
		td.innerHTML = cantidadSub;
		tr.appendChild(td);
		
		//*****
		tbody.appendChild(tr);
		
		//** TR para el Cuerpo
		tr = xCreate('tr');
		tr.style.display = "none";
		tr.id = 'fila_cuerpo_'+idDialogo;
		
		td = xCreate('td');
		td.className = 'dialogoPuntoV';
		td.innerHTML = '&nbsp;';
		tr.appendChild(td);
		
		td = xCreate('td');
		td.colSpan = '5';
		td.className = 'paddingCuerpo';
		tr.appendChild(td);
		
		tbody.appendChild(tr);
		//*****
		
		//** TR para el Form
		tr = xCreate('tr');
		tr.style.display = "none";
		tr.id = 'fila_form_'+idDialogo;
		
		td = xCreate('td');
		td.className = 'dialogoPuntoV';
		td.innerHTML = '&nbsp;';
		tr.appendChild(td);
		
		td = xCreate('td');
		td.colSpan = '5';
		td.align = 'right';
		td.className = 'paddingForm';
		td.innerHTML = '';
		//**
		if(xGetElementById('admin').value == 'true'){
			td.innerHTML += '<input type="button" value="Eliminar" class="form_button" onclick="eliminar(\''+idDialogo+'\')" style="width: 110px;">';
		}
		td.innerHTML += '<input type="button" value="Marcar no leído" class="form_button" onclick="marcarLeido(this,\''+idDialogo+'\')" style="width: 110px;">';
		if(xGetElementById('admin').value == 'true' || xGetElementById('invitado').value != 'true'){
			td.innerHTML += '&nbsp;<input type="button" value="Responder" class="form_button" onclick="responder(\''+idDialogo+'\',\''+asunto+'\')" style="width: 110px;"><br>';
		}
		var div = xCreate('div');
		div.id='form_dia_'+idDialogo;
		td.appendChild(div);
		tr.appendChild(td);
		
		tbody.appendChild(tr);
		//*****
		
		//** TR para los Subs
		tr = xCreate('tr');
		tr.style.display = "none";
		tr.id = 'fila_sub_'+idDialogo;
		
		td = xCreate('td');
		td.colSpan = '6';
		tr.appendChild(td);
		
		tbody.appendChild(tr);
		//*****
	
		table.appendChild(tbody);
		
		// Fin de creacion de tabla 
		tdPri.appendChild(table);
		
		/*var img = new Image();
		img.src = xGetContextPath()+'/img/cal.gif';
		img.height = 2;
		tdPri.appendChild(img);*/
		
		xShowD('fila_sub_'+id);
		
	}

}

function enviarCorreo(idUsuario,titulo) {
	if(!titulo)titulo='';
	window.open(xGetContextPath() + "/comun/buzon/NuevoMensaje.action?destino=" + idUsuario+"&titulo="+titulo, "Saludos", "height=330,width=510,scrollbars=no");
	return true;
}

