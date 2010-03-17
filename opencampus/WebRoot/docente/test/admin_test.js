//**************** AJAX ******************//
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
//***************** Comun ****************//
function verAuditoria(userC,fechaC,userM,fechaM,img){
	xInnerHtml("auditoria","<b>Creado:</b><br> "+fechaC+" por "+userC+"<br><b>Modificado:</b><br> "+fechaM+" por "+userM);
	xMoveTo("auditoria",xPageX(img)-150-20,xPageY(img));
	xShow("auditoria");
}
function ocultarAuditoria(){
	xHide("auditoria");
}


window.onresize=Resize;
var imgComp;
var id = 0;
function verImagen(archivo,size,img){

	if(xStr(archivo)){
		restaurarImagen(img);
		imgComp = img;
		var imagen = new Image();
		imagen.src = archivo; 
		//xInnerHtml("txtampliacion"," "+size+" KB ");
		pruebaCarga(imagen,id = id+1);
		xShow("ampliacion");
	}
}

function restaurarImagen(img) {
 	xResizeTo("ampliacion",136,60);
	xResizeTo("c1",130,33);
	xWidth('cerrarampliacion',129); 
	xMoveTo("ampliacion",xPageX(img)-136,xPageY(img));
	xInnerHtml('c1','<img src="' + xGetContextPath() + '/img/cargando.gif" width="' + 130 + '" height="' + 30 + '" border="0">');
}

function pruebaCarga(imagen,id) { 
	
	if(this.id == id){
		
		if (imagen.complete === true) { 
			var ancho =(imagen.width>400)?400:imagen.width;
			var alto =(imagen.height>300)?300:imagen.height;
			xResizeTo("ampliacion",ancho+6,alto+6 + 20);
			xResizeTo("c1",ancho,alto);
			xWidth('cerrarampliacion',ancho); 
			xLeft("ampliacion",xPageX("ampliacion")-xWidth("ampliacion")+136);
			xInnerHtml('c1','<img src="' + imagen.src + '" width="' + ancho + '" height="' + alto + '" border="0">');
			
		
		} else{
			setTimeout(function(){ this.pruebaCarga(imagen,id); },500);
		}
		
	}
	
}
function Resize(){
	xMoveTo("ampliacion",xPageX(imgComp)-xWidth("ampliacion"),xPageY(imgComp));
}
function ocultarImagen(){
	xHide("ampliacion");
}

function seleccion(TR, valor) {
	if (valor) {
		TR.style.background = "#EAEBED";
	} else {
		TR.style.background = "";
	}
}
function seleccionBlue(TR, valor) {
	if (valor) {
		TR.style.background = "#E5EFF8";
	} else {
		TR.style.background = "#FFFFFF";
	}
}

function validar(form,tipo){
	if(viewTextMode==1){
		alert("Estas en modo \"Ver Código HTML\" y no se permite guardar en dicho modo.");
		return false;
	}
	//Validar Pregunta
	var con = xGetElementById("wysiwyg" + "pregunta").contentWindow.document.body.innerHTML;
	var conClear = con.stripTags().stripScripts().strip().replace(/&nbsp;/g,"").stripNewLine().strip();
	if(conClear.length==0){
		alert("Debe ingresar una pregunta.");
		return false;
	}
	if(con.length>2048){
		alert("Ha excedido el límite de número de caracteres, solo puede ingresar hasta 2048 caracteres.");
		return false;
	}
	//alert("p"+con+"m"+con.length);
	
	//Validar tipo de archivo adjunto
	if(!validarAdjunto(form.file.value)){
		return false;
	}
	//Validar explicacion
	if(!cuentaCaracteres(form.explicacion)){
		return false;
	}
	
	//Validar COntenido por tipo de Pregunta
	if(tipo==1){
		//Tipo Simple
		if(trim(form.alt1.value).length <= 0){
			alert("No ha introducido la primera alternativa.");
			return false;
		}
		if(trim(form.alt2.value).length <= 0){
			alert("No ha introducido la segunda alternativa.");
			return false;
		}
		if(trim(form.alt3.value).length <= 0){
			alert("No ha introducido la tercera alternativa.");
			return false;
		}
		if(trim(form.alt4.value).length <= 0){
			alert("No ha introducido la cuarta alternativa.");
			return false;
		}
		if(trim(form.alt5.value).length <= 0){
			alert("No ha introducido la quinta alternativa.");
			return false;
		}
		if(!validarRadio(form.rpt1)){
			alert("Debe indicar la respuesta.");
			return false;
		}
		
	}else if(tipo==2){
		//Tipo Multiple
		if(trim(form.aux1.value).length <= 0){
			alert("No ha introducido la primera alternativa.");
			return false;
		}
		if(trim(form.aux2.value).length <= 0){
			alert("No ha introducido la segunda alternativa.");
			return false;
		}
		if(trim(form.aux3.value).length <= 0){
			alert("No ha introducido la tercera alternativa.");
			return false;
		}
		if(trim(form.aux4.value).length <= 0){
			alert("No ha introducido la cuarta alternativa.");
			return false;
		}
		if(trim(form.aux5.value).length <= 0){
			alert("No ha introducido la quinta alternativa.");
			return false;
		}
		if(	!form.rpt1.checked && !form.rpt2.checked && !form.rpt3.checked && 
			!form.rpt4.checked && !form.rpt5.checked){
			if(!window.confirm("¿Esta seguro que no existe ninguna respuesta en esta pregunta?"));
				return false;
		}
		
	}else if(tipo==3){
		//Tipo Verdadero Flaso
		if(!validarRadio(form.rpt1)){
			alert("Debe indicar la respuesta.");
			return false;
		}
		
	}else if(tipo==4){
		//Tipo Relacionar
		if(trim(form.aux1.value).length <= 0){
			alert("No ha introducido la primera alternativa.");
			return false;
		}
		if(trim(form.aux2.value).length <= 0){
			alert("No ha introducido la segunda alternativa.");
			return false;
		}
		if(trim(form.aux3.value).length <= 0){
			alert("No ha introducido la tercera alternativa.");
			return false;
		}
		if(trim(form.aux4.value).length <= 0){
			alert("No ha introducido la cuarta alternativa.");
			return false;
		}
		/**/
		if(trim(form.aux1.value).length <= 0){
			alert("No ha introducido el primer complemento.");
			return false;
		}
		if(trim(form.aux2.value).length <= 0){
			alert("No ha introducido el segundo complemento.");
			return false;
		}
		if(trim(form.aux3.value).length <= 0){
			alert("No ha introducido el tercero complemento.");
			return false;
		}
		if(trim(form.aux4.value).length <= 0){
			alert("No ha introducido el cuarto complemento.");
			return false;
		}
		/**/
		if(trim(form.rpt1.value).length <= 0 || !validarAB(form.rpt1.value)){
			alert("No ha introducido la primera respuesta.");
			return false;
		}
		if(trim(form.rpt2.value).length <= 0 || !validarAB(form.rpt2.value)){
			alert("No ha introducido la segunda respuesta.");
			return false;
		}
		if(trim(form.rpt3.value).length <= 0 || !validarAB(form.rpt3.value)){
			alert("No ha introducido la tercera respuesta.");
			return false;
		}
		if(trim(form.rpt4.value).length <= 0 || !validarAB(form.rpt4.value)){
			alert("No ha introducido la cuarta respuesta.");
			return false;
		}
		
	}else if(tipo==5){
		//Tipo Completar
		conWithInput = con;
		for(var i=0;i<4;i++){
			conWithInput = conWithInput.replace(/<INPUT\/?[^>]+>|<INPUT>/i,"");
		}
		if(conWithInput.length != conWithInput.replace(/<INPUT\/?[^>]+>|<INPUT>/i,"").length){
			alert("Se ha introducido más de 4 cajas de texto.");
			return false;
		}
		
	}else if(tipo==6){
		//Tipo Ordenar
		if(trim(form.aux1.value).length <= 0){
			alert("No ha introducido la primera alternativa.");
			return false;
		}
		if(trim(form.aux2.value).length <= 0){
			alert("No ha introducido la segunda alternativa.");
			return false;
		}
		if(trim(form.aux3.value).length <= 0){
			alert("No ha introducido la tercera alternativa.");
			return false;
		}
		if(trim(form.aux4.value).length <= 0){
			alert("No ha introducido la cuarta alternativa.");
			return false;
		}
		/* */
		if(trim(form.rpt1.value).length <= 0 || !validar12(form.rpt1.value)){
			alert("No ha introducido la primera respuesta.");
			return false;
		}
		if(trim(form.rpt2.value).length <= 0 || !validar12(form.rpt2.value)){
			alert("No ha introducido la segunda respuesta.");
			return false;
		}
		if(trim(form.rpt3.value).length <= 0 || !validar12(form.rpt3.value)){
			alert("No ha introducido la tercera respuesta.");
			return false;
		}
		if(trim(form.rpt4.value).length <= 0 || !validar12(form.rpt4.value)){
			alert("No ha introducido la cuarta respuesta.");
			return false;
		}
		
	}else{
		window.close();
	}
	
	return true;
}

function check12(input){
	if(!validar12(input.value)){
		input.value = "";
	}else{
		if(input.form.rpt1.value == input.value && input.name != input.form.rpt1.name)
			input.value = "";
		if(input.form.rpt2.value == input.value && input.name != input.form.rpt2.name)
			input.value = "";
		if(input.form.rpt3.value == input.value && input.name != input.form.rpt3.name)
			input.value = "";
		if(input.form.rpt4.value == input.value && input.name != input.form.rpt4.name)
			input.value = "";
		
	}
}

function checkAB(input){
	input.value = input.value.toUpperCase();
	if(!validarAB(input.value)){
		input.value = "";
	}else{
		if(input.form.rpt1.value == input.value && input.name != input.form.rpt1.name)
			input.value = "";
		if(input.form.rpt2.value == input.value && input.name != input.form.rpt2.name)
			input.value = "";
		if(input.form.rpt3.value == input.value && input.name != input.form.rpt3.name)
			input.value = "";
		if(input.form.rpt4.value == input.value && input.name != input.form.rpt4.name)
			input.value = "";
		
	}
}

function validar12(texto){
	var reg=/(^[1-4]$)/;
	if(!reg.test(texto)){
		return false;
	}
	return true;
}

function validarAB(texto){
	var reg=/(^[A-D]$)/;
	if(!reg.test(texto)){
		return false;
	}
	return true;
}

extArray = new Array(".gif", ".jpg",".png");
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
	alert("Se permiten únicamente archivos con la extención: " 
	+ (extArray.join(" ")) + "\nPor favor, seleccione otro archivo "
	+ "e intente de nuevo.");
	return false;
}

function cuentaCaracteres(textArea){
	if(textArea.value.length > 1024){
		alert("Llegó al límite de número de caracteres (1024).");
		textArea.value = textArea.value.substring(0,1024);
		return false;
	}
	return true;
}

function validarRadio(rpt1){
	for (var i=0;i<rpt1.length;i++){ 
       if (rpt1[i].checked) 
          return true;
	} 
	return false;
}