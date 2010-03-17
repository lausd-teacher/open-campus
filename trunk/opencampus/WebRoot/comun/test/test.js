function confirmarIngreso(idUnidad){
	if(confirm('¿Está seguro que desea ingresar a la evaluación?')){
		this.exit=true;
		window.document.location =xGetContextPath() +'/aulavirtual/test/Ingresar.action?idUnidad='+idUnidad;
	}
	return false;
}

function seleccion(Input, valor) {
	if (valor) {
		Input.className = "fon_color01";
	} else {
		Input.className = "";
	}
}

function deshabilitarBotonDerecho(){
	var message="";
	function clickIE() {if (document.all) {(message);return false;}}
	function clickNS(e) {if 
	(document.layers||(document.getElementById&&!document.all)) {
	if (e.which==2||e.which==3) {(message);return false;}}}
	if (document.layers) 
	{document.captureEvents(Event.MOUSEDOWN);document.onmousedown=clickNS;}
	else{document.onmouseup=clickNS;document.oncontextmenu=clickIE;}
	document.oncontextmenu=new Function("return false");
	//if("Explorer" === xBrowser.getName()){
		document.onkeydown=function(evt){ 
			var evt = navigator.appName=="Netscape" ? e:event;
			if (evt.ctrlKey) {
				alert("Tecla no permitida");
			} 
		}
	/*}else{
		document.onkeydown=new Function("return false");
	}*/
}

function deshabilitarCopiarPegar(){
	function disableselect(e)
	{
	  return false
	}
	function reEnable()
	{
	  return true
	}
	//if IE4+
	document.onselectstart=new Function ("return false")
	//if NS6
	if (window.sidebar)
	{
	  if("Explorer" === xBrowser.getName()){
	  	document.onmousedown=disableselect
	  }
	  document.onclick=reEnable
	}	
}

function closeForced(){
	window.onbeforeunload = '';
}

function cerrarTest(){
	var myConn = new XHConn();
	myConn.connect(xGetContextPath()+"/aulavirtual/test/SalirDelTest.action", "POST", null, function (oXML) {});
}

// Test **** //
var inicio= false;
var timerOff = false;
var validate = null
function initTest(){
	//Init Timer
	cronometro(new Date());
	keepAlive();
	if(opener.showBlock)opener.showBlock();
	
	//*********
	if(this.curform<=1)xGetElementById('back').disabled=true;
	tr = xGetElementById('tr_1');
	if(tr)tr.style.background = '#E5EFF8';
	validate = setInterval("validar()",1000);
}

var isFirst = true;
function keepAlive(){
	if(!isFirst)message();
	isFirst = false;
	setTimeout("keepAlive()",600000);
}

function message(msg){
	var myConn = new XHConn(); 
	myConn.connect(xGetContextPath()+"/KeepAlive.action", "POST", (msg)?"message="+msg:null, function (oXML) { if (oXML.readyState === 4){if(oXML.status !== 200) xInnerHtml("msg","Error en la conexión al servidor, intente rendir nuevamente la evaluación.") }; });
}

function cronometro(date) {  
	if(date)this.inicio = date;
	var diferencia = (new Date()).getTime() - this.inicio.getTime();
	var ahora = new Date(diferencia+18000000);
    var horas = ahora.getHours();
    var minutos = ahora.getMinutes();
    var segundos = ahora.getSeconds();
    var ValorHora;
  
   if (horas < 10)   
            ValorHora = "0" + horas;
    else  
        ValorHora = "" + horas;
  
    if (minutos < 10)   
        ValorHora += ":0" + minutos;
    else  
        ValorHora += ":" + minutos;
  
     if (segundos < 10)   
        ValorHora += ":0" + segundos;
    else  
        ValorHora += ":" + segundos;
           
    xInnerHtml('timer',ValorHora);
    if(!this.timerOff)setTimeout("cronometro()",1000);
}

// Imagen Previo **** //

var imgComp;
var id = 0;
function verImagen(archivo,size,img){

	if(xStr(archivo)){
		restaurarImagen(img);
		imgComp = img;
		var imagen = new Image();
		imagen.src = archivo+"&nc="+(new Date()) ; 
		//xInnerHtml("txtampliacion"," "+size+" KB ");
		pruebaCarga(imagen,id = id+1);
		xShow("ampliacion");
	}
}

function restaurarImagen(img) {
 	xResizeTo("ampliacion",136,60);
	xResizeTo("loading",130,33);
	xWidth('cerrarampliacion',129); 
	xMoveTo("ampliacion",xPageX(img)-136,xPageY(img));
	xInnerHtml('loading','<img src="' + xGetContextPath() + '/img/cargando.gif" width="' + 130 + '" height="' + 30 + '" border="0">');
}

function pruebaCarga(imagen,id) { 
	
	if(this.id == id){
		
		if (imagen.complete === true) { 
			var ancho =(imagen.width>400)?400:imagen.width;
			var alto =(imagen.height>300)?300:imagen.height;
			xResizeTo("ampliacion",ancho+6,alto+6 + 20);
			xResizeTo("loading",ancho,alto);
			xWidth('cerrarampliacion',ancho); 
			xLeft("ampliacion",xPageX("ampliacion")-xWidth("ampliacion")+136);
			xInnerHtml('loading','<img src="' + imagen.src + '" width="' + ancho + '" height="' + alto + '" border="0">');
			
		
		} else{
			setTimeout(function(){ this.pruebaCarga(imagen,id); },500);
		}
		
	}
	
}

function ocultarImagen(){
	xHide("ampliacion");
}

// Navegador **** //

var curform = 1;
function selectForm(id){
	var tr;
	for(var i=1; i<=10; i++) {
		xHideD('form_'+i);
		tr = xGetElementById('tr_'+i);
		if(tr)tr.style.background = '#FFF';
	}
	xShowD('form_'+id);
	tr = xGetElementById('tr_'+id);
	if(tr)tr.style.background = '#E5EFF8';
	this.curform = id;
	
	xGetElementById('next').disabled=false;
	xGetElementById('back').disabled=false;
	if(this.curform>=10)xGetElementById('next').disabled=true;
	if(this.curform<=1)xGetElementById('back').disabled=true;
	
}

function next(){
	if(this.curform<10){
		selectForm(this.curform+1);
	}
}

function back(){
	if(this.curform>1){
		selectForm(this.curform-1);
	}
}

// Formulario **** //

function check12(input){
	if(!validar12(input.value)){
		input.value = "";
	}else{
		if(input.form.a1.value == input.value && input.name != input.form.a1.name)
			input.value = "";
		if(input.form.a2.value == input.value && input.name != input.form.a2.name)
			input.value = "";
		if(input.form.a3.value == input.value && input.name != input.form.a3.name)
			input.value = "";
		if(input.form.a4.value == input.value && input.name != input.form.a4.name)
			input.value = "";
		
	}
}

function checkAB(input){
	input.value = input.value.toUpperCase();
	if(!validarAB(input.value)){
		input.value = "";
	}else{
		if(input.form.a1.value == input.value && input.name != input.form.a1.name)
			input.value = "";
		if(input.form.a2.value == input.value && input.name != input.form.a2.name)
			input.value = "";
		if(input.form.a3.value == input.value && input.name != input.form.a3.name)
			input.value = "";
		if(input.form.a4.value == input.value && input.name != input.form.a4.name)
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

function validarRadio(rpt1){
	for (var i=0;i<rpt1.length;i++){ 
       if (rpt1[i].checked) 
          return true;
	} 
	return false;
}
/*
 * validar(): solo valida
 * validar(cmd): valida y pregunta si desea finalizar
 */
function validar(cmd){
	try{
		var falta = new Array();
		
		for(var i=0; i<10; i++) {
			var form = xGetElementById('form_'+(i+1));
			var tipo = form.tipo.value;
			
			if(tipo==1){
				//Tipo Simple
				
				if(!validarRadio(form.a1)){
					falta.push(i+1);
				}
				
			}else if(tipo==2){
				//Tipo Multiple
				
				if(	!form.a1.checked && !form.a2.checked && !form.a3.checked && !form.a4.checked && !form.a5.checked){
					falta.push(i+1);
				}
				
			}else if(tipo==3){
				//Tipo Verdadero Flaso
				if(!validarRadio(form.a1)){
					falta.push(i+1);
				}
				
			}else if(tipo==4){
				//Tipo Relacionar
				
				if(trim(form.a1.value).length <= 0 || !validarAB(form.a1.value)
					|| trim(form.a2.value).length <= 0 || !validarAB(form.a2.value)
					|| trim(form.a3.value).length <= 0 || !validarAB(form.a3.value)
					|| trim(form.a4.value).length <= 0 || !validarAB(form.a4.value)){
					falta.push(i+1);
				}
				
			}else if(tipo==5){
				//Tipo Completar
				
				var flag = true;
				if(form.a1 && trim(form.a1.value).length <= 0){
					flag = false;
				}
				if(form.a2 && trim(form.a2.value).length <= 0){
					flag = false;	
				}
				if(form.a3 && trim(form.a3.value).length <= 0){
					flag = false;	
				}
				if(form.a4 && trim(form.a4.value).length <= 0){
					flag = false;	
				}
				if(!flag){
					falta.push(i+1);
				}
							
			}else if(tipo==6){
				//Tipo Ordenar
				
				if(trim(form.a1.value).length <= 0 || !validar12(form.a1.value)
					|| trim(form.a2.value).length <= 0 || !validar12(form.a2.value)
					|| trim(form.a3.value).length <= 0 || !validar12(form.a3.value)
					|| trim(form.a4.value).length <= 0 || !validar12(form.a4.value)){
						falta.push(i+1);
				}
				
			}
					
		}
		
		for(var i=1; i<=10; i++) {
			if(hasElement(falta,i)){
				xShow('flag_'+i);
			}else{
				xHide('flag_'+i);
			}
		}
		
		if(cmd){
			var msg = (falta.length !== 0) ? 'Le falta completar las preguntas: '+falta.join(', ')+'.\n' : '';
			if(confirm(msg+'¿Está seguro que desea finalizar la evaluación?')){
				var btn = xGetElementById('btn_finish');
				btn.disabled = true;
				calificar();
			}
		}
		
	}catch(e){
		clearInterval(validate); 
		message("Error Validar - "+((e.description)?e.description:e));
		xInnerHtml("msg","Ha ocurrido un pequeño error con su Browser: "+(e.description)?e.description:e);
		alert("Ha ocurrido un pequeño error con su Browser, \nel detalle se encuentra al pie de esta página, \nanótela y envíesela a soporte para que su nota no se vea afectada")
	}
}

function hasElement(falta,e){
	for(var i=0; i<falta.length; i++) {
		if(e == falta[i]){
			return true;
		}
	}
	return false;
}

/*
 * calificar(): recupera entradas del formulario y envía al servidor
 * calificar(cmd): recupera las entradas y las setea en el review, no envia al servidor
 */

function calificar(cmd){
	try{
		var cadena ='pm=0';
		for(var i=0; i<10; i++) {
			var form = xGetElementById('form_'+(i+1));
			var review = xGetElementById('review_'+(i+1));
			var tipo = form.tipo.value;
			var id = form.id.value
			
			cadena += '&id'+i+'='+id;
			
			if(tipo==1){
				//Tipo Simple
				
				cadena += '&re'+i+'=';
				
				for (var a=0;a<form.a1.length;a++){ 
			       if (form.a1[a].checked){ 
			          cadena += form.a1[a].value;
			          if(cmd)review.a1[a].checked = true;
			       }
			    } 
			    
			}else if(tipo==2){
				//Tipo Multiple
				
				cadena += '&re'+i+'=';
				
				cadena += (form.a1.checked)?'1/':'0/';
				cadena += (form.a2.checked)?'1/':'0/';
				cadena += (form.a3.checked)?'1/':'0/';
				cadena += (form.a4.checked)?'1/':'0/';
				cadena += (form.a5.checked)?'1/':'0/';
				
				if(cmd){
					if(form.a1.checked)review.a1.checked = true;
					if(form.a2.checked)review.a2.checked = true;
					if(form.a3.checked)review.a3.checked = true;
					if(form.a4.checked)review.a4.checked = true;
					if(form.a5.checked)review.a5.checked = true;
				}
								
			}else if(tipo==3){
				//Tipo Verdadero Flaso
				
				cadena += '&re'+i+'=';
				
				for (var a=0;a<form.a1.length;a++){ 
			       if (form.a1[a].checked){
			          cadena += form.a1[a].value;
			          if(cmd)review.a1[a].checked = true;
			       }
			    } 
				
			}else if(tipo==4){
				//Tipo Relacionar
				
				cadena += '&re'+i+'=';
				
				cadena += (form.a1.value.length > 0)?form.a1.value+'/':' /';
				cadena += (form.a2.value.length > 0)?form.a2.value+'/':' /';
				cadena += (form.a3.value.length > 0)?form.a3.value+'/':' /';
				cadena += (form.a4.value.length > 0)?form.a4.value+'/':' /';
				
				if(cmd){
					review.a1.value = form.a1.value;
					review.a2.value = form.a2.value;
					review.a3.value = form.a3.value;
					review.a4.value = form.a4.value;
				}
				
			}else if(tipo==5){
				//Tipo Completar
				
				cadena += '&re'+i+'=';
				
				if(form.a1){
					cadena += (form.a1.value.length > 0)?escape(form.a1.value.trim().toLowerCase())+'/pm/':' /pm/';
					if(cmd)review.a1.value = form.a1.value;
				}
				if(form.a2){
					cadena += (form.a2.value.length > 0)?escape(form.a2.value.trim().toLowerCase())+'/pm/':' /pm/';
					if(cmd)review.a2.value = form.a2.value;
				}
				if(form.a3){
					cadena += (form.a3.value.length > 0)?escape(form.a3.value.trim().toLowerCase())+'/pm/':' /pm/';
					if(cmd)review.a3.value = form.a3.value;
				}
				if(form.a4){
					cadena += (form.a4.value.length > 0)?escape(form.a4.value.trim().toLowerCase())+'/pm/':' /pm/';
					if(cmd)review.a4.value = form.a4.value;
				}
			
			}else if(tipo==6){
				//Tipo Ordenar
				
				cadena += '&re'+i+'=';
				
				cadena += (form.a1.value.length > 0)?form.a1.value+'/':' /';
				cadena += (form.a2.value.length > 0)?form.a2.value+'/':' /';
				cadena += (form.a3.value.length > 0)?form.a3.value+'/':' /';
				cadena += (form.a4.value.length > 0)?form.a4.value+'/':' /';
				
				if(cmd){
					review.a1.value = form.a1.value;
					review.a2.value = form.a2.value;
					review.a3.value = form.a3.value;
					review.a4.value = form.a4.value;
				}
				
			}
			
		}
		
		if(!cmd){
			//alert(cadena);
			var myConn = new XHConn();
			var query = function (oXML) { 
				if (oXML.readyState === 4) {
					var xml = oXML.responseXML;
					if(oXML.responseText !== "" && xml.getElementsByTagName("test").length !== 0){
						
						this.timerOff = true;
						
						var fecha1 = xml.getElementsByTagName("fecha1")[0].childNodes[0].nodeValue;
						var fecha2 = xml.getElementsByTagName("fecha2")[0].childNodes[0].nodeValue;
						var elapsed = xml.getElementsByTagName("elapsed")[0].childNodes[0].nodeValue;
						var nota = xml.getElementsByTagName("nota")[0].childNodes[0].nodeValue;
						
						var p1 = xml.getElementsByTagName("p0")[0].childNodes[0].nodeValue;
						var p2 = xml.getElementsByTagName("p1")[0].childNodes[0].nodeValue;
						var p3 = xml.getElementsByTagName("p2")[0].childNodes[0].nodeValue;
						var p4 = xml.getElementsByTagName("p3")[0].childNodes[0].nodeValue;
						var p5 = xml.getElementsByTagName("p4")[0].childNodes[0].nodeValue;
						var p6 = xml.getElementsByTagName("p5")[0].childNodes[0].nodeValue;
						var p7 = xml.getElementsByTagName("p6")[0].childNodes[0].nodeValue;
						var p8 = xml.getElementsByTagName("p7")[0].childNodes[0].nodeValue;
						var p9 = xml.getElementsByTagName("p8")[0].childNodes[0].nodeValue;
						var p10 = xml.getElementsByTagName("p9")[0].childNodes[0].nodeValue;
						
						xInnerHtml('fecha1',fecha1);
						xInnerHtml('fecha2',fecha2);
						xInnerHtml('elapsed',elapsed);
						xInnerHtml('nota',nota);
						
						xInnerHtml('punto_1','Puntos : '+p1);
						xInnerHtml('punto_2','Puntos : '+p2);
						xInnerHtml('punto_3','Puntos : '+p3);
						xInnerHtml('punto_4','Puntos : '+p4);
						xInnerHtml('punto_5','Puntos : '+p5);
						xInnerHtml('punto_6','Puntos : '+p6);
						xInnerHtml('punto_7','Puntos : '+p7);
						xInnerHtml('punto_8','Puntos : '+p8);
						xInnerHtml('punto_9','Puntos : '+p9);
						xInnerHtml('punto_10','Puntos : '+p10);
						
						if ((e = xGetElementById('punto_1'))&& p1<=0) e.style.color = '#F00';
						if ((e = xGetElementById('punto_2'))&& p2<=0) e.style.color = '#F00';
						if ((e = xGetElementById('punto_3'))&& p3<=0) e.style.color = '#F00';
						if ((e = xGetElementById('punto_4'))&& p4<=0) e.style.color = '#F00';
						if ((e = xGetElementById('punto_5'))&& p5<=0) e.style.color = '#F00';
						if ((e = xGetElementById('punto_6'))&& p6<=0) e.style.color = '#F00';
						if ((e = xGetElementById('punto_7'))&& p7<=0) e.style.color = '#F00';
						if ((e = xGetElementById('punto_8'))&& p8<=0) e.style.color = '#F00';
						if ((e = xGetElementById('punto_9'))&& p9<=0) e.style.color = '#F00';
						if ((e = xGetElementById('punto_10'))&& p10<=0) e.style.color = '#F00';
						
						calificar(true);
						
						xHideD('table_test');
						xShowD('table_review');
						
						window.onbeforeunload = '';
					
					}else{
						message("Error en la respuesta, responseText: "+oXML.statusText+".\n"+escape(oXML.responseText));
						myConn.connect(xGetContextPath()+"/aulavirtual/test/Calificar.action", "POST", cadena, query);
						//xInnerHtml('msg','Hubo un error al intentar guardar la calificación, informe a soporte adjuntando la hora y fecha de la tragedia. <br>Detalles: '+oXML.statusText);
						xInnerHtml('msg','Sus datos se estan guardando, espere un momento por favor, en caso de que no suceda nada, revise su conexión de Internet. <br>Detalles: '+oXML.statusText);
					}
				}
			};
				
			myConn.connect(xGetContextPath()+"/aulavirtual/test/Calificar.action", "POST", cadena, query);
		}
		
	}catch(e){
		message("Error Calificar - "+((e.description)?e.description:e));
		xInnerHtml("msg","Ha ocurrido un pequeño error con su Browser: "+(e.description)?e.description:e);
		alert("Ha ocurrido un pequeño error con su Browser, \nel detalle se encuentra al pie de esta página, \nanótela y envíesela a soporte para que su nota no se vea afectada")
	}
}

