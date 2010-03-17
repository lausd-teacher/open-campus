var s_s_check=null;

function anadirServicio(s_div) {
	if(s_s_check==null){
		s_s_check = s_div;
		var valors = ($('id_'+s_div).checked==true)?'1':'0';
		new Ajax.Request(xGetContextPath() +"/GrabarPortalEliminado.action",{parameters:"stringServicio="+s_div+"&estadoServicio="+valors,onSuccess: resultadoanadirServicio});
	}else{
		window.alert('En estos momentos se está procesando su petición. Por favor, espere un momento.');
	}
}

function resultadoanadirServicio(s_transport){
	var s_check = $('id_'+s_s_check);
	s_s_check =null;
	var valor = s_transport.responseText;	
	if(valor==='1'){		
		if(s_check.checked==true){		
			SERVICIO_FALTA--;
		}else{
			SERVICIO_FALTA++;
		}		
		if(SERVICIO_FALTA<=0){
			document.getElementById('td_mensaje_servicio').innerHTML=TXT_COMPLETO;
		}else{
			document.getElementById('td_mensaje_servicio').innerHTML=TXT_INCOMPLETO;
		}		
	}else{		
		s_check.checked = !s_check.checked;
	}	
}

function reiniciarServicio(){
	if(window.confirm('¿Desea reestablecer los servicios por defecto?')){
		document.location = xGetContextPath() +'/ConfiguracionPortalDefault.action';
	}
}
