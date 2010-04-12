var options = { 	portal 			: 'columns', 
							editorEnabled 	: true, 
							saveurl 		: 'save.php' };
var data 	= {};				
var portal;		
Event.observe(window, 'load', function() {
	portal = new Portal(settings, options, data);
});

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
		
	
}

function closeService(img,id){
	var block = $(img).up('div').up('div');
	block.hide();
}


/*
 * <!-- 
	http://www.ajaxupdates.com/igoogle-like-drag-drop-portal-v2-0/
	* Exconder y mostrar los divs con scriptaculus (Effect.Apear)
	* puede susar apply_settings para refrescar los bloques cuando hagan click en 
	eliminar y agregar servicios, en lugar de refrezcar toda la pantalla
 -->

que traiga todos los servicios a la vez, menos los minimizados ni cerrados:
Collection<Service> portal = getPortal()

if(foro.visible())
	buscarForos
y asi para cada uno

ya en el portal cuando se maximiza recien se trae con ajax
 
 * 
 * EN cadena = Servicio.doServicesToJson(portal);
no agregar a la cadena si el estado=0
 
 * 
 * 
 */



function anadirServicio(check, id) {
		var valors = (check.checked==true)?'1':'0';
		new Ajax.Request(xGetContextPath() +"/portal/GrabarPortalEliminado.action",{parameters:"stringServicio="+id+"&estadoServicio="+valors});
}

function reiniciarServicio(){
	if(window.confirm('¿Desea reestablecer los servicios por defecto?')){
		document.location = xGetContextPath() +'/ConfiguracionPortalDefault.action';
	}
}
