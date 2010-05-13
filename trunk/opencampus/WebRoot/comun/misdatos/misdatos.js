function validarClave(texto) {
	if(!texto.empty()){
		var password = new PasswordTest(texto);
		//$("password_level").style.width = (password.value * 2.24) + "px";
		var _x = (112 - password.value * 2.24 - 40);
		$("password_level").style.backgroundPosition= _x + 'px';
		if(_x > 0){
			$("password_level").style.backgroundColor= '#ff0000';
		}else{
			$("password_level").style.backgroundColor= '#00ff00';
		}
		$('password_level').update(password.level);
	}else{
		$("password_level").style.backgroundPosition= '200px';
		$("password_level").style.backgroundColor= '#ffffff';
	}
}

function verPanelCambioPassword() {
	DarkPanel.show('form_div_clave',{duration	: 0.0});
}

function ocultarPanelCambioPassword() {
	DarkPanel.hide('form_div_clave');
	$("password_level").style.backgroundPosition= '200px';
	$("password_level").style.backgroundColor= '#ffffff';
}

function verPanelCambioDatos() {
	DarkPanel.show('form_div_datos',{duration	: 0.0});
}

function ocultarPanelCambioDatos() {
	DarkPanel.hide('form_div_datos');
}

function verPanelCambioCorreo() {
	DarkPanel.show('form_div_correo',{duration	: 0.0});
	$("newMail").value = $("theMail").innerHTML.replace(/<BR>/gi, ",")
}
function ocultarPanelCambioCorreo() {
	DarkPanel.hide('form_div_correo');
}

wf.functionName_formValidation = "myCustomValidation";
function myCustomValidation (evt) {
	try{
		if (wf.formValidation(evt) && evt.srcElement.id == 'form_clave'){
			
			var isValidate = true;
				
			//Validar clave
			if(evt.srcElement.claveTemp.value != evt.srcElement.claveTemp2.value ){
				showErrorForm(evt.srcElement.claveTemp2,"Clave no coincide.");
				isValidate =  false;
			}else{
				hideErrorForm(evt.srcElement.claveTemp2);
			}
			if(evt.srcElement.claveTemp.value.length < evt.srcElement.claveTemp.alt){
				showErrorForm(evt.srcElement.claveTemp,"La clave debe tener una longitud no menor a 6.");
				isValidate =  false;
			}else{
				hideErrorForm(evt.srcElement.claveTemp);
			}
			
			if(!isValidate)
				return wf.utilities.XBrowserPreventEventDefault(evt);
			
			//$('form_clave_btn').disable();
			//loading();
			//return wf.utilities.XBrowserPreventEventDefault(evt);
			
		}else if (wf.formValidation(evt) && evt.srcElement.id == 'form_datos'){
			
			var isValidate = true;
				
			//Validar ...
			
			
			if(!isValidate)
				return wf.utilities.XBrowserPreventEventDefault(evt);
				
		}
		
	}catch(e){
		alert('Ocurrió un error. Actualice la página.\nError:'+((e.description)?e.description:e));
		return wf.utilities.XBrowserPreventEventDefault(evt);
	}
}



/*********************************************/

function grabarCorreo() {
	new Ajax.Request(xGetContextPath() +"/CambioEmail.action", 
	{
		method: 'post',
		parameters: 'email=' + $F("newMail"),
		onSuccess: function(transport) {
			if (transport.responseText.strip() == 'OK'){
				$("theMail").update($F("newMail").replace(/,/gi, "<BR>"));
				ocultarPanelCambioCorreo();
			}else{
				alert('Error al intentar actualizar. Inténtelo nuevamente.');
				window.location.reload();
			}
		}
	});	
}
