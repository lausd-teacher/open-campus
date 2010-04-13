var options = { portal 			: 'columns', 
				editorEnabled 	: true, 
				saveurl 		: xGetContextPath() +"/portal/GrabarPortal.action" };
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

/*
 * <!-- 
	http://www.ajaxupdates.com/igoogle-like-drag-drop-portal-v2-0/
	* Exconder y mostrar los divs con scriptaculus (Effect.Apear)
	* puede susar apply_settings para refrescar los bloques cuando hagan click en 
	eliminar y agregar servicios, en lugar de refrezcar toda la pantalla
 -->

* 
que traiga todos los servicios a la vez, menos los minimizados ni cerrados:
Collection<Service> portal = getPortal()
if(foro.visible())
	buscarForos
y asi para cada uno

ya en el portal cuando se maximiza recien se trae con ajax
 
 * 
 * 
 * 
 */





function reiniciarServicio(){
	if(window.confirm('¿Desea reestablecer los servicios por defecto?')){
		document.location = xGetContextPath() +'/ConfiguracionPortalDefault.action';
	}
}
