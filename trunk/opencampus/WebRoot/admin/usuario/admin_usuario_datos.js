function cambioPassword(s_id) {
	if(window.confirm("¿Está seguro que desea modificar la clave?")){
	ajax = nuevoAjax();
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			if (ajax.responseText === "0") {
				
				showMessage("Intentelo en breves minutos.");
			} else {
				showMessage("Su nueva clave es " + ajax.responseText + "."); 
			}
		}
	};
	ajax.open("POST", xGetContextPath() + "/admin/usuario/RestablecerClave.action", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("id=" + s_id);
	}
}