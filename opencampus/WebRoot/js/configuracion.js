var options = { portal 			: 'columns', 
				editorEnabled 	: true, 
				saveurl 		: xGetContextPath() +"/portal/GrabarPortal.action" };
var data 	= {};				
var servicios_cargados = new Object(); 
	
function hideService(img,id,msgMax, msgMin){
	var block = $(img).up('div').next();
	
	new Ajax.Request(xGetContextPath() +"/portal/OcultarServicio.action", 
	{
		method: 'post',
		parameters: "servicio="+id+"&estado="+((block.visible())?'0':'1'),
		onSuccess: function(transport) {
			if (transport.responseText.strip() == 'OK'){
				if(block.visible()){
  					block.hide();
					img.src = xGetContextPath()+'/img/mas.jpg';
					img.alt = msgMax;
				}else{
					block.show();
					img.src = xGetContextPath()+'/img/menos.jpg';
					img.alt = msgMin;
				}
			}
		}
	});	
	
	if(!servicios_cargados[id] && !block.visible())cargar_servicio(id);
	
}

function removeService(id){
		
	new Ajax.Request(xGetContextPath() +"/portal/EliminarServicio.action", 
	{
		method: 'post',
		parameters: "servicio="+id+"&estado=0",
		onSuccess: function(transport) {
			if (transport.responseText.strip() == 'OK'){
  				$('block-'+id).hide();
  				if($('chkbox_'+id))$('chkbox_'+id).checked=false;
			}
		}
	});
	
}

function addService(id){
		
	new Ajax.Request(xGetContextPath() +"/portal/EliminarServicio.action", 
	{
		method: 'post',
		parameters: "servicio="+id+"&estado=1",
		onSuccess: function(transport) {
			if (transport.responseText.strip() == 'OK'){
  				$('block-'+id).show();
  				if($('chkbox_'+id))$('chkbox_'+id).checked=true;
			}
		}
	});
	
}

function configService(chk,id){
	if(chk.checked){
		addService(id);
	}else{
		removeService(id);
	}
}

/* Carga Servicios */
var msg_error = '<strong>Problemas con el servicio</strong>';

function cargar_servicio(id){
	if(!$('box_'+id))return;
	$('box_'+id).childElements().each(function(el, indice){el.remove()}); 
	
	new Ajax.Request(xGetContextPath() +"/portal/CargarServicio.action", 
	{
		method: 'post',
		parameters: "servicio="+id,
		onSuccess: function(transport) {
			if (transport.responseText !== ""){
				servicios_cargados[id] = true;
  				$('box_'+id).update(transport.responseText);
			}else{
				$('box_'+id).update(msg_error);
			}
		},
		onLoading : function(){startLoading('box_'+id)},
    	onComplete : function(){stopLoading('box_'+id)}
	});
	
}

function startLoading(idblock){
	if(idblock && $(idblock)){
		if($(idblock+'_loading'))
			$(idblock+'_loading').show();
		else
			$(idblock).insert(new Element('div', { 'class': 'loading_portal', id: idblock+'_loading' }).insert(new Element('img', { src: xGetContextPath()+'/img/cargando.gif' })));
	}else{
		if($('body_loading'))
			$('body_loading').show();
		else{
			$(document.body).insert(new Element('div', { 'class': 'loading_portal_modal', id: 'body_loading' }).insert(new Element('div').insert(new Element('img', { src: xGetContextPath()+'/img/cargando.gif' }))));
			DarkPanel.show('body_loading',{duration	: 0.0});
		}
	}
}

function stopLoading(idblock){
	if(idblock && $(idblock)){
		if($(idblock+'_loading'))
			$(idblock+'_loading').hide();
	}else{
		if($('body_loading'))
			$('body_loading').hide();
			DarkPanel.hide('body_loading');
	}
}



function cargar_servicio_curso(){
	var myConn = new XHConn();
	var query = function (oXML) {
		if (oXML.readyState === 4) {
			if(oXML.responseText !== ""){
				xInnerHtml('box_servicio_curso',oXML.responseText);
				xInnerHtml('servicio_curso_descripcion',xInnerHtml('servicio_curso_descripcion_origen'));
			}else{
				xInnerHtml('box_servicio_curso',msg_error);
			}
		}
	};
	myConn.connect(xGetContextPath()+"/portal/CargarCursos.action", "POST", null, query);
}

/*
 *
que traiga todos los servicios a la vez, menos los minimizados ni cerrados:
Collection<Service> portal = getPortal()
if(foro.visible())
	buscarForos
y asi para cada uno

ya en el portal cuando se maximiza recien se trae con ajax
* 
* ************ Hacer sitemesh para todos los *.jsp, excepto los que terminen en *_inc.jsp, *_popup.jsp, *_ajax.jsp
 
 * 
 * 
 * *****************************************************************************************************************
 */



function eliminarAula(){
	var myConn = new XHConn();
	myConn.connect(xGetContextPath()+"/aulavirtual/SalirDelAula.action", "POST", null, function (oXML) {});
}

/* Carga Servicios */

function cargar_servicio_buzon(){
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			if(oXML.responseText !== ""){
				xInnerHtml('servicio_buzon_td',oXML.responseText);
				portal._updateColumnsHeight();
				xInnerHtml('servicio_buzon_descripcion',xInnerHtml('servicio_buzon_descripcion_origen'));
			}else{
				xInnerHtml('servicio_buzon_td',msg_error)
			}
		}
	};
	myConn.connect(xGetContextPath()+"/comun/buzon/CargarPortada.action", "POST", null, query);
}
function cargar_servicio_noticia(){
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			if(oXML.responseText !== ""){
				xInnerHtml('servicio_noticia_td',oXML.responseText);
				portal._updateColumnsHeight();
				xInnerHtml('servicio_noticia_descripcion',xInnerHtml('servicio_noticia_descripcion_origen'));
			}else{
				xInnerHtml('servicio_noticia_td',msg_error)
			}
		}
	};
	myConn.connect(xGetContextPath()+"/noticia/CargarPortada.action", "POST", null, query);
}
function cargar_servicio_foros(){
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			if(oXML.responseText !== ""){
				xInnerHtml('servicio_foros_td',oXML.responseText);
				portal._updateColumnsHeight();
				xInnerHtml('servicio_foros_descripcion',xInnerHtml('servicio_foros_descripcion_origen'));
			}else{
				xInnerHtml('servicio_foros_td',msg_error)
			}
		}
	};
	//myConn.connect(xGetContextPath()+"/foro/CargarPortada.action", "POST", null, query);
}
function cargar_servicio_chat(){
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			if(oXML.responseText !== "" && oXML.responseText.indexOf("<!--Chat-->") != -1){
				xInnerHtml('servicio_chat_td',oXML.responseText);
				portal._updateColumnsHeight();
				xInnerHtml('servicio_chat_descripcion',xInnerHtml('servicio_chat_descripcion_origen'));
			}else{
				xInnerHtml('servicio_chat_td',msg_error)
			}
			setTimeout("cargar_servicio_chat()",5000);
		}
	};
	myConn.connect(xGetContextPath()+"/usuario/CargarConectadosEnChat.action", "POST", null, query);
}
function cargar_servicio_enlaces(){
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			if(oXML.responseText !== ""){
				xInnerHtml('servicio_enlaces_td',oXML.responseText);
				portal._updateColumnsHeight();
				xInnerHtml('servicio_enlaces_descripcion',xInnerHtml('servicio_enlaces_descripcion_origen'));
			}else{
				xInnerHtml('servicio_enlaces_td',msg_error)
			}
		}
	};
	myConn.connect(xGetContextPath()+"/comun/portal/servicio_enlaces.jsp", "POST", null, query);
}
function cargar_servicio_glosario(){
	xInnerHtml('servicio_glosario_td','');
}
function cargar_servicio_biblioteca(){
	xInnerHtml('servicio_biblioteca_td','');
}
function cargar_servicio_apuntes(){
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			if(oXML.responseText !== ""){
				xInnerHtml('servicio_apuntes_td',oXML.responseText);
				portal._updateColumnsHeight();
				xInnerHtml('servicio_apuntes_descripcion',xInnerHtml('servicio_apuntes_descripcion_origen'));
			}else{
				xInnerHtml('servicio_apuntes_td',msg_error)
			}
		}
	};
	myConn.connect(xGetContextPath()+"/anotacion/CargarPortada.action", "POST", null, query);
}
function cargar_servicio_cumpleanos(){
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			if(oXML.responseText !== ""){
				xInnerHtml('servicio_cumpleanos_td',oXML.responseText);
				portal._updateColumnsHeight();
				xInnerHtml('servicio_cumpleanos_descripcion',xInnerHtml('servicio_cumpleanos_descripcion_origen'));
			}else{
				xInnerHtml('servicio_cumpleanos_td',msg_error)
			}
		}
	};
	myConn.connect(xGetContextPath()+"/usuario/CargarPortada.action", "POST", null, query);
}
var intervalCalendar = null;
function cargar_servicio_agenda(){
	importarCSS(xGetContextPath()+'/js/jscalendar/campus.css');
	importar(xGetContextPath()+'/js/jscalendar/calendar.js');
	//importar(xGetContextPath()+'/js/jscalendar/calendar-es.js');
	//importar(xGetContextPath()+'/js/jscalendar/calendar-setup.js');
				
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			
				this.intervalCalendar = setInterval("startCalendar('"+oXML.responseText+"')",100);
				xInnerHtml('servicio_agenda_descripcion','<strong><span class="text_rojo">'+ (oXML.responseText.split('/').length-1) +'</span> evento(s)<strong>');
				
		}
	};
	myConn.connect(xGetContextPath()+"/agenda/CargarPortada.action", "POST", null, query);
}
var startCalendar = function(days){
	try{
		if(Calendar){
			importar(xGetContextPath()+'/js/jscalendar/calendar-setup.js');
			importar(xGetContextPath()+'/js/jscalendar/calendar-es.js');
			xInnerHtml('servicio_agenda_td','<div id="cv_agenda" style="width:99%;"></div>');
			Calendar.setup(
			    {
			      flat         : "cv_agenda",
			      weekNumbers  : false,
			      showOthers   : true,
			      date		   : ahorita,
			      step		   : 1,
			      //showsTime	   : true,
			      showRowHead  : true,
			      showRowNavegator : false,
			      showToolTips : false, 
			      range        : [2000, 2020],
			      changeFirstDay  : false,
			      disabled	   : true,
			      specialDay   : days
			    }
			);
			portal._updateColumnsHeight();
			clearInterval(this.intervalCalendar);
		}
	}catch(e){
		
	}
}

function cambiarPagina(url) {
	window.document.location = url;
}

function importar(rutaJS) { 
    var scr=document.createElement("SCRIPT"); 
    scr.setAttribute("src", rutaJS); 
    scr.setAttribute("type", "text/javascript"); 
    document.body.appendChild(scr); 
}

function importarCSS(rutaCSS) { 
    var scr=document.createElement("LINK"); 
    scr.setAttribute("href", rutaCSS); 
    scr.setAttribute("rel", "stylesheet"); 
    scr.setAttribute("type", "text/css"); 
    document.body.appendChild(scr); 
}  

function reiniciarServicio(){
	if(window.confirm('¿Desea reestablecer los servicios por defecto?')){
		document.location = xGetContextPath() +'/ConfiguracionPortalDefault.action';
	}
}
