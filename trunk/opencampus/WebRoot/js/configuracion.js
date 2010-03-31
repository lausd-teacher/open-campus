
var options = { portal 			: 'columns', 
				editorEnabled 	: true, 
				saveurl 		: 'save.php' };
var data 	= {};				
var portal;		
Event.observe(window, 'load', function() {
	portal = new Portal(settings, options, data);
});


function anadirServicio(check, id) {
		var valors = (check.checked==true)?'1':'0';
		new Ajax.Request(xGetContextPath() +"/portal/GrabarPortalEliminado.action",{parameters:"stringServicio="+id+"&estadoServicio="+valors});
}

function reiniciarServicio(){
	if(window.confirm('¿Desea reestablecer los servicios por defecto?')){
		document.location = xGetContextPath() +'/ConfiguracionPortalDefault.action';
	}
}
