var foco = true;
if (usuario == null) var usuario = {};
var destino_activo = '';
var i = 0;
var top = 0;
var left = 0;
var activo = true;
var interval = null;
var titulo = '';
var contactos = new Array();

function initChat(){
	if(activo){
		dwr.engine.setActiveReverseAjax(true);		
		UsuariosConectados.getConectados();	
		dwr.engine.setHttpMethod('POST');
	}
}

usuario.buscalo = function(){
	word = trim(dwr.util.getValue('buscamelo'));
	word = word.toUpperCase();

	if(word.length>0){
		for(i=0;i<contactos.length;i=i+1){
			contacto = contactos[i];			
			try{
				xHideD('modelo_'+contacto.id);
				xHideD('name_'+contacto.id);
			}catch(e){}
		}
		
		for(i=0;i<contactos.length;i=i+1){
			contacto = contactos[i];
			nombre = contacto.nombre;
			nombre = nombre.toUpperCase();
			try{
				if(nombre.search(word) > -1){
					xShowD('modelo_'+contacto.id);
					xShowD('name_'+contacto.id);					
				}				
			}catch(e){}
		}
	}else{
		for(i=0;i<contactos.length;i=i+1){
			contacto = contactos[i];
			try{
				xShowD('modelo_'+contacto.id);
				xShowD('name_'+contacto.id);
			}catch(e){}				
		}
	}		
};

function zumbido(){
	
	window.focus();
	
	var iframe = xGetElementById("zumbido");
	iframe.contentWindow.document.location.href  = xGetContextPath()+'/comun/chat/zumbido.jsp';
	
	setTimeout("bumble()",400);
	
}

function bumble(){
	var range = 10;
	var tiempo = 50;
	try{
		if (navigator.userAgent.toLowerCase().indexOf("msie 6.0") != -1 || navigator.userAgent.toLowerCase().indexOf("msie 7.0") != -1) {
			for(var index=0; index<tiempo; index++) {
				window.moveBy(range, range);
				window.moveBy(0, -1*range);
				window.moveBy(-1*range, range);
				window.moveBy(0, -1*range);
			}
		}else{
			var xW = (screen.width-window.outerWidth)/2;
			var xH = (screen.height-window.outerHeight)/2;
			window.moveTo(xW, xH);
			for(var index=0; index<tiempo; index++) {
				window.moveTo(xW+range, xH+range);
				window.moveTo(xW, xH-range);
				window.moveTo(xW-range, xH+range);
				window.moveTo(xW, xH-range);
			}
		}
	}catch(e){}
}

function play() {
	xInnerHtml('demo',this.foco+'');
	if(!foco){
		var iframe = xGetElementById("alert");
		iframe.contentWindow.document.location.href  = xGetContextPath()+'/comun/chat/alert.jsp';
		//iframe.contentWindow.document.location.reload();
	}
	//alert(this.foco);
}

if (navigator.userAgent.toLowerCase().indexOf("msie 6.0") != -1 || navigator.userAgent.toLowerCase().indexOf("msie 7.0") != -1) {
	document.onmousedown = setFoco;
	document.onmouseover = setFoco;
	document.onmouseout = lostFoco;
}else{
	window.onfocus = setFoco;
	window.onblur = lostFoco;
	document.onmouseover = setFoco;
	document.onmouseout = lostFoco;
}

function cargar_usuarios_offLine(){
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			var xml = oXML.responseXML;
			
			if(xml.getElementsByTagName("usuarios").length !== 0){
				
				xInnerHtml('noconectados','');
				xInnerHtml('offline',xml.getElementsByTagName("usuario").length+' ');
				
				//Recorrer el Arreglo
				for (var x = 0; x < xml.getElementsByTagName("usuario").length; x = x + 1) {
					//Extraer valiables
					var usuario = xml.getElementsByTagName("usuario")[x];
					
					var id = usuario.getElementsByTagName("id")[0].childNodes[0].nodeValue;
					var nombre = usuario.getElementsByTagName("nombre")[0].childNodes[0].nodeValue;
					var nombrecomp = usuario.getElementsByTagName("nombrecomp")[0].childNodes[0].nodeValue;
					var fecha = usuario.getElementsByTagName("fecha")[0].childNodes[0].nodeValue;
					var rol = usuario.getElementsByTagName("rol")[0].childNodes[0].nodeValue;
					
					//Creacion de tablas
					var fuckingDiv = xGetElementById('noconectados');
					
					var table = xCreate('table');
					table.style.width = '100%';
					table.cellPadding="2";
					table.cellSpacing="0";
					table.border=0;
					var tbody = xCreate("tbody");
					var tr = xCreate('tr');
					var td = xCreate('td');
					
					td.innerHTML  = "<span id='modelo_X_"+id+"' class='flecha_offline' onMouseOver='showMe(this);' onMouseOut='hideMe(this);'>&nbsp;</span>";
					td.innerHTML += nombre;
					td.innerHTML += "<span id='rol_X_"+id+"' style='display:none'>"+rol+"</span>";
					td.innerHTML += "<span id='hora_X_"+id+"' style='display:none'>"+fecha+"</span>";
					td.innerHTML += "<span id='full_X_"+id+"' style='display:none'>"+nombrecomp+"</span>";
					
					tr.appendChild(td);
					
					td = xCreate('td');
					td.style.width = '14px';
					td.style.cursor = 'pointer';
					td.innerHTML = "<img src='"+xGetContextPath()+"/img/star_1.jpg' alt='Invitar (Proximamente...)'/>";
					
					tr.appendChild(td);
					
					if("Explorer" === xBrowser.getName()){
						td = xCreate('td');
						td.style.width = '14px';
						td.innerHTML = '&nbsp;'
						tr.appendChild(td);
					}
					
					tbody.appendChild(tr);
					table.appendChild(tbody);
					fuckingDiv.appendChild(table);
					
				}
				
			}else{
				xInnerHtml('noconectados','<strong>Conexión cerrada.</strong>');
				xInnerHtml('offline','0');
			}
			setTimeout("cargar_usuarios_offLine()",10000);
		}
	};
	myConn.connect(xGetContextPath()+"/chat/CargarUsuariosOffLine.action", "POST", null, query);
}

function setFoco(){
	window.foco=true;
	//xInnerHtml('demo',xInnerHtml('demo')+'1');
}

function lostFoco(){
	window.foco=false;
	//xInnerHtml('demo',xInnerHtml('demo')+'0');
}

function showAll(){
	for(i=0;i<contactos.length;i=i+1){
			contacto = contactos[i];
			try{
			xShowD('modelo_'+contacto.id);
			xShowD('name_'+contacto.id);
			}catch(e){}				
		}
}

function mandarZumbido(who){
	var elmtId = who.id;
	elmtId = elmtId.substring(elmtId.indexOf('_')+1,elmtId.length);
	chatActivo('modelo_chat_'+elmtId)
	xGetElementById('text_'+elmtId).value = 'zumbido-->';
	sendMessage();
}

function HideBeginText(){
	word = trim(dwr.util.getValue('buscamelo'));
	if(word=='Nombres y/o Apellidos'){
		dwr.util.setValue('buscamelo','');
	}
}

function ShowBeginText(){
	word = trim(dwr.util.getValue('buscamelo'));
	if(word.length===0){
		dwr.util.setValue('buscamelo','Nombres y/o Apellidos');
	}
}

function newChat(destino){
	try{
		if(destino.split('_').length>1){
			who = destino.substring(destino.indexOf('_')+1,destino.length);
		}else{
			who = destino.split('_')[1];
		}
		if(!$('modelo_chat_'+who)){
			dwr.util.cloneNode('modelo_chat_',{idSuffix:who});
			dwr.util.setValue('x_'+who,dwr.util.getValue('name_'+who));
			$('modelo_chat_'+who).style.top = (Math.random() * 100)+ 200 + "px";
			$('modelo_chat_'+who).style.left = (Math.random() * 100) + 400 + "px";
		}
		//document.getElementById('modelo_chat_'+who).style.display ="block";
		chatActivo('modelo_chat_'+who);
		xShow('modelo_chat_'+who);
		$('text_'+who).focus();
	}catch(e){}
}

var intervarTitle = null;
function setTitle(title){
	clearInterval(intervarTitle);
	this.intervarTitle = setInterval("changeTitle('"+title+"')",1000);
}

var change = true;
function changeTitle(title){
	if(change){
		parent.document.title = title.split('dice:')[0]+'dice:';
	}else{
		parent.document.title = title.substring(title.indexOf('dice:')+5,title.length);
	}
	change=!change;
}

function chatActivo(id){
	try{ 
		clearInterval(intervarTitle);
		parent.document.title = 'Campus Virtual de Tecsup';
		interval = 's';
		if(id.split('_').length>1){
			destino_activo = id.substring(id.indexOf('_')+6,id.length);			
		}else{
			destino_activo = id.split('_')[2];
		}
		if($('modelo'+destino_activo)){
			$('table_'+destino_activo).className = 'fon_cab_chat';
		}
		
		$('text_'+destino_activo).focus();
		
		i=i+1;
		$(id).style.zIndex = i;	
	}catch(e){}
}

function escapalo(){
	xHide('modelo_chat_'+destino_activo);
	//document.getElementById('modelo_chat_'+destino_activo).style.display ="none";
	destino_activo = '';
}

function sendMessage(){
	var text = trim(dwr.util.getValue('text_'+destino_activo));
	var prefijo = 'Yo: ';
	
	if(text.length>0 && destino_activo !== ''){
		if(text.length>255){
			text = text.substring(0,255);
		}
		UsuariosConectados.sendMessage(text,destino_activo,function(){
			dwr.util.setValue('text_'+destino_activo,'');
			var random = '-'+Math.random()*154;
			dwr.util.cloneNode('mensaje_'+destino_activo,{idPreffix:'',idSuffix:random});
			$('mensaje_'+destino_activo+random).style.color='black';
			dwr.util.setValue('mensaje_'+destino_activo+random,prefijo+text);
			$('chat_'+destino_activo).scrollTop = $('chat_'+destino_activo).scrollHeight;	
		});
		
	}
}

function showMe(who){
	var elmtId = who.id;
	if(elmtId.split('_').length>1){
		id = elmtId.substring(elmtId.indexOf('_')+1,elmtId.length);
	}else{
		id = elmtId.split('_')[1];
	}
	try{
		if (elmtId.split('_')[0]=='x7'){			
			if(!$('masInfo2_'+id)){
				dwr.util.cloneNode('masInfo2_',{idPreffix:'',idSuffix:id});
				if(dwr.util.getValue('rol_'+id).length === 0){
					xHideD('roles2_'+id);
					xHideD('rolBr2_'+id);
				}else{
					dwr.util.setValue('roles2_'+id,'Grupo: '+dwr.util.getValue('rol_'+id));
				}
				dwr.util.setValue('hor2_'+id,'Hora de Ingreso: '+dwr.util.getValue('hora_'+id));
				dwr.util.setValue('fullName2_'+id,dwr.util.getValue('full_'+id));
			}
			$('masInfo2_'+id).style.top=(top-90)+'px';
			$('masInfo2_'+id).style.left=(left-50)+'px';
			xShow('masInfo2_'+id);
			document.getElementById('masInfo2_'+id).style.display ="block";
		}else {			
			if(!$('masInfo_'+id)){
				dwr.util.cloneNode('masInfo_',{idPreffix:'',idSuffix:id});				
				if(dwr.util.getValue('rol_'+id).length === 0){
					xHideD('roles_'+id);
					xHideD('rolBr_'+id);					
				}else{
					dwr.util.setValue('roles_'+id,'Grupo: '+dwr.util.getValue('rol_'+id));
				}
				dwr.util.setValue('hor_'+id,'Hora de Ingreso: '+dwr.util.getValue('hora_'+id));
				dwr.util.setValue('fullName_'+id,dwr.util.getValue('full_'+id));
			}
			$('masInfo_'+id).style.top=(top-0)+'px';
			$('masInfo_'+id).style.left=(left-50)+'px';
			xShow('masInfo_'+id);
			document.getElementById('masInfo_'+id).style.display ="block";
		}
	}catch(e){}
}

function hideMe(who){
	var elmtId = who.id;
	
	if(elmtId.split('_').length>1){
		id = elmtId.substring(elmtId.indexOf('_')+1,elmtId.length);	
	}else{
		id = elmtId.split('_')[1];
	}	
	
	try{
		if (elmtId.split('_')[0]=='x7'){
			$('masInfo2_'+id).style.visibility='hidden';
			$('masInfo2_'+id).style.display='none';
		}else {
			$('masInfo_'+id).style.visibility='hidden';
			$('masInfo_'+id).style.display='none';
		}
	}catch(e){}
}

/******************mouse position****************/
document.onmousemove = mouseMove;

function mouseMove(ev){
	if (document.layers||document.getElementById&&!document.all) {		
		left = ev.pageX;
		top = ev.pageY;
	}else if (document.all) {
		try{	
			top = ev.clientY;
			left = ev.clientX;
		}catch(e){}		
	}	
}