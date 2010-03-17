function cerrarAula(){
	var ajax=nuevoAjax();
	ajax.open("GET", xGetContextPath()+"/admin/SalirDelAula.action", false);
	ajax.setRequestHeader( "If-Modified-Since", "Sat, 1 Jan 2000 00:00:00 GMT" );
	//ajax.send(null);
	if((ajax.readyState == 4))alert(ajax.responseText);
	
	cerrarAulaVirtualVentanas();
}
function abrirTexto(url) {
	var ancho = 770;
	var alto = 500;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualTexto = window.open(url, "AulaVirtualTexto", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualTexto.focus();
	return true;
}
function abrirRepaso(url, ancho, alto) {
	if (!alto || !ancho) {
		ancho = 775;
		alto = 505;
	}
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualRepaso = window.open(' ', "AulaVirtualRepaso", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=0,resizable=0,top=" + Y + ",left=" + X + "");
	aulaVirtualRepaso.focus();
	var html_version = '<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">';
	var css = '<style type="text/css"> <!-- body { margin-left: 0px; margin-top: 0px; margin-right: 0px; margin-bottom: 0px; } --> </style> ';
	var body = '<object classid="" codebase="" width="770" height="500"> <param name="movie" value="'+url+'"> <param name="quality" value="high"> <embed src="'+url+'" quality="high" type="application/x-shockwave-flash" width="770" height="500"></embed> </object> ';
	aulaVirtualRepaso.document.write( html_version+css+body );
	aulaVirtualRepaso.document.close();
	return true;
}
function abrirLaboratorio(url) {
	var ancho = 550;
	var alto = 540;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualLaboratorio = window.open(url, "AulaVirtualLaboratorio", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualLaboratorio.focus();
	return true;
}
function abrirDialogo(url) {
	var ancho = 720;
	var alto = 550;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualDialogo = window.open(url, "AulaVirtualDialogo", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualDialogo.focus();
	return true;
}
function abrirTrabajo(url) {
	var ancho = 550;
	var alto = 540;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualTrabajo = window.open(url, "AulaVirtualTrabajo", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualTrabajo.focus();
	return true;
}
function abrirTest(url) {
	var ancho = 550;
	var alto = 540;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualTest = window.open(url, "AulaVirtualTest", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualTest.focus();
	return true;
}

function configResource(e, idUnidad, idRecurso){
	DarkPanel.show('capa', function(){});
	var vista = $('menu_config');
	vista.show();
	vista.setStyle({	top: ($(e).cumulativeOffset().top - 4)+'px', 
						left: ($(e).cumulativeOffset().left + 20)+'px'}); 
	
	//jalar data con ajax 
}





var DarkPanel = {
	panels :  new Object(),
	show : function (id,hideEvent){
		panel = DarkPanel.panels[id];
		if(!panel){
			panel = new Capa(id);
			DarkPanel.panels[id] = panel;
		}
		if(panel)panel.show(hideEvent);
	},
	hide : function (id){
		panel = DarkPanel.panels[id];
		if(panel)panel.hide();
	}
}

var Capa = Class.create();  
Object.extend(Object.extend(Capa.prototype, Abstract.prototype), {
	initialize: function(id) {
		this.id = id;     
		this.hideEvent = null;
		this.capa = new Element('div', { id:id , className:'select-free'});
		this.capa.setStyle({	display: 'none' ,opacity: 0.4, backgroundColor: '#ffffff'});
		this.capa.setStyle({	top: document.viewport.getScrollOffsets().top+'px', left: document.viewport.getScrollOffsets().left+'px', 
								width: document.viewport.getWidth()+'px', height: document.viewport.getHeight()+'px' }); 
		//capa.setOpacity(0.5);
		if("Explorer" === xBrowser.getName() && xBrowser.getVersion() < 7){
			this.capa.insert(new Element('iframe')); 
		}
		this.capa.observe('click', this.hide.bind(this)); 
		Event.observe(document.onresize ? document : window, 'resize', this.resize.bind(this));
		Event.observe(window, 'scroll', this.scroll.bind(this));
		$(document.body).insert(this.capa); 
	},  
	resize: function() {  
		this.capa.setStyle({width: document.viewport.getWidth()+'px', height: document.viewport.getHeight()+'px' }); 		
	},
	scroll: function() {  
		this.capa.setStyle({top: document.viewport.getScrollOffsets().top+'px', left: document.viewport.getScrollOffsets().left+'px'}); 
	},
	show: function(hideEvent) {  
		this.hideEvent = hideEvent;
		this.capa.show();
	}, 
	hide: function() {  
		this.capa.hide();
		if(this.hideEvent)this.hideEvent();
	}
}); 







//********************************************************************************************//
/*
Imagen de flag
*/
var img_flag = obtenerContextPath() + "/img/flag.gif";
/*
Esta manejado en un array 3 dimensional.
Ejemplo:
imgs[
		[
			[ dibujo_desactivado_noexiste ]
			, 
			[ dibujo_normal_noexiste   ]
		],[
			[ dibujo_desactivado ] 
			, 
			[ dibujo_activado    ] 
		]	  		
    ]
*/
var imgs = 
	[
		[
				[obtenerContextPath() + "/img/icon_candado.jpeg",
				obtenerContextPath() + "/img/nada.gif",
				obtenerContextPath() + "/img/nada.gif",
				 obtenerContextPath() + "/img/nada.gif",
				  obtenerContextPath() + "/img/nada.gif",
				   obtenerContextPath() + "/img/nada.gif",
				    obtenerContextPath() + "/img/nada.gif",
				     obtenerContextPath() + "/img/nada.gif",
				      obtenerContextPath() + "/img/nada.gif"],
				       [obtenerContextPath() + "/img/icon_candado.jpeg",
				        obtenerContextPath() + "/img/icon_libro_no.gif",
				         obtenerContextPath() + "/img/icon_download_no.gif",
				          obtenerContextPath() + "/img/icon_attach_no.gif",
				           obtenerContextPath() + "/img/icon_dialog_no.gif",
				            obtenerContextPath() + "/img/icon_trab_no.gif",
				             obtenerContextPath() + "/img/icon_trab_grup_no.gif",
				              obtenerContextPath() + "/img/icon_test_no.gif",
				               obtenerContextPath() + "/img/icon_pract_no.gif"
				  ]
		], 
		[
				[obtenerContextPath() + "/img/icon_candado_abierto.jpeg",
				 obtenerContextPath() + "/img/icon_libro_inactivo.gif",
				  obtenerContextPath() + "/img/icon_download_inactivo.gif",
				   obtenerContextPath() + "/img/icon_attach_inactivo.gif",
				    obtenerContextPath() + "/img/icon_dialog_inactivo.gif",
				     obtenerContextPath() + "/img/icon_trab_inactivo.gif",
				      obtenerContextPath() + "/img/icon_trab_grup_inactivo.gif",
				       obtenerContextPath() + "/img/icon_test_inactivo.gif", 
				       obtenerContextPath() + "/img/icon_pract_inactivo.gif"],
				        [obtenerContextPath() + "/img/icon_candado_abierto.jpeg",
				         obtenerContextPath() + "/img/icon_libro.gif",
				          obtenerContextPath() + "/img/icon_download.gif",
				           obtenerContextPath() + "/img/icon_attach.gif",
				            obtenerContextPath() + "/img/icon_dialog.gif",
				             obtenerContextPath() + "/img/icon_trab.gif",
				              obtenerContextPath() + "/img/icon_trab_grup.gif",
				               obtenerContextPath() + "/img/icon_test.gif",
				                obtenerContextPath() + "/img/icon_pract.gif"
			]
		]
	];
	
function ver_opcion(idmenu, idunidad) {
	var estado_unidad = document.getElementById("estado_" + idunidad);
	if ("1" == estado_unidad.value) {
		ver_menu(idmenu);
	}
}
/*

Se obtiene la imagen de acuerdo a los parametros
uno ---> Existir
dos----> Habilitado
tres---> Recurso Tipo
img ---> Lugar donde se pondra la imagen
*/
function obtener_imagen(uno, dos, tres, img) {
	var img_src = document.getElementById(img);
	img_src.src = imgs[uno][dos][tres];
}


/*
Se obtiene el contextPath de la aplicacion
*/
function obtenerContextPath() {
	var context = "";
	var loct = document.location;
	var array = loct.href.split("/");
	try {
		context = array[0] + "/" + array[1] + "/" + array[2] + "/" + array[3];
	}
	catch (e) {
	}
	return context;
}
function asignarSilabo(idFicha, idSilabo) {
	var flag = window.confirm("\xbfEst\xe1 seguro que desea asignar el s\xedlabo a la ficha?");
	if (flag) {
		document.location = xGetContextPath() + "/admin/AsignarSilabo.action?idFicha=" + idFicha + "&idSilabo=" + idSilabo;
	}
}
/*
Cuando se desHabilita la unidad por completa
*/
function deshabilitar_unidad(unidad, contexto) {
	var url = contexto + "/admin/aulavirtual/FichaUnidadCambiarEstado.action";
	var ajax = nuevoAjax();
	ajax.open("POST", url, true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("idUnidad=" + unidad);
	var estado_unidad = document.getElementById("estado_" + unidad);
	estado_unidad.value = (estado_unidad.value != "1") ? "1" : "0";
}
function cambiar_estado_existe(unidad, contexto, recurso) {
	var url = contexto + "/admin/aulavirtual/FichaUnidadRecursoCambiarEstado.action";
	var ajax = nuevoAjax();
	ajax.open("POST", url, true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("idUnidad=" + unidad + "&idRecurso=" + recurso);
	var valor_recurso = document.getElementById("hidden_" + unidad + "_" + recurso);
	valor_recurso.value = (valor_recurso.value != "1") ? "1" : "0";
	obtener_imagen(valor_recurso.value, "1", recurso, "img_" + unidad + "_" + recurso);
	
	var display_recurso = document.getElementById("menu_item_" + unidad + "_" + recurso);
	display_recurso.innerHTML = (valor_recurso.value != 1) ? "Activar" : "Desactivar";
	var cb = document.getElementsByName("I_" + recurso);
	var count = 0, estado = 0;
	for (i = 0; i < cb.length; i = i + 1) {
		var activo = (cb[i].src).substr((cb[i].src).lastIndexOf(".") - 2).substr(0, 2);
		if (activo != "no") {
			count = count + 1;
		}
	}
	if (count == 0) {
		document.getElementById(recurso + "I").checked = false;
	} else {
		document.getElementById(recurso + "I").checked = true;
	}
	
	// Cambiar
	/*
	var nombre_variable = unidad + "_" + recurso;
	var estado_noexiste = document.getElementById("hidden_" + nombre_variable);
	itemDIV.innerHTML = (estado_noexiste.value != "1") ? noexiste : existe;
	document.getElementById("input_existe_" + nombre_variable).value = (estado_noexiste.value != "0") ? "0" : "1";
	var panel = document.getElementById("menu_item_" + nombre_variable);
	var estado_unidad = document.getElementById("estado_unidad_" + unidad);
	if (estado_noexiste.value == "1" && estado_unidad.value == "1") {
		panel.style.display = "block";
	} else {
		panel.style.display = "none";
	}
	obtener_imagen(document.getElementById("input_existe_" + nombre_variable).value, document.getElementById("input_deshabilitado_" + nombre_variable).value, tipo, "img_" + nombre_variable);	
	*/
}
function cambiar_estado_califica(unidad, contexto, recurso) {
	var url = contexto + "/admin/aulavirtual/FichaUnidadRecursoCambiarCalifica.action";
	var ajax = nuevoAjax();
	ajax.open("POST", url, true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("idUnidad=" + unidad + "&idRecurso=" + recurso);
	/* */
	var cb = document.getElementsByName("C_" + recurso);
	var count = 0;
	for (i = 0; i < cb.length; i = i + 1) {
		if (cb[i].checked) {
			count = count + 1;
		}
	}
	if (count > 0) {
		document.getElementById(recurso + "C").checked = true;
	} else {
		document.getElementById(recurso + "C").checked = false;
	}
	/* */
}
function cambiar_estado_docente(unidad, contexto, recurso) {
	var url = contexto + "/admin/aulavirtual/FichaUnidadRecursoCambiarEstadoDocente.action";
	var ajax = nuevoAjax();
	ajax.open("POST", url, true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("idUnidad=" + unidad + "&idRecurso=" + recurso);
	/* */
	var cb = document.getElementsByName("D_" + recurso);
	var count = 0;
	for (i = 0; i < cb.length; i = i + 1) {
		if (cb[i].checked) {
			count = count + 1;
		}
	}
	if (count > 0) {
		document.getElementById(recurso + "D").checked = true;
	} else {
		document.getElementById(recurso + "D").checked = false;
	}
	/* */
}
function cambiar_estado_estudiante(unidad, contexto, recurso) {
	var url = contexto + "/admin/aulavirtual/FichaUnidadRecursoCambiarEstadoEstudiante.action";
	var ajax = nuevoAjax();
	ajax.open("POST", url, true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("idUnidad=" + unidad + "&idRecurso=" + recurso);
	/* */
	var cb = document.getElementsByName("E_" + recurso);
	var count = 0;
	for (i = 0; i < cb.length; i = i + 1) {
		if (cb[i].checked) {
			count = count + 1;
		}
	}
	if (count > 0) {
		document.getElementById(recurso + "E").checked = true;
	} else {
		document.getElementById(recurso + "E").checked = false;
	}
	/* */
}

function abrirConfiguracion(url) {
	var ancho = 550;
	var alto = 540;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualConfiguracion = window.open(url, "AulaVirtualConfiguracion", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=0,top=" + Y + ",left=" + X + "");
	aulaVirtualConfiguracion.focus();
	return true;
}
function abrirPlanDocente(url) {
	var ancho = 550;
	var alto = 540;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualPlanDocente = window.open(url, "AulaVirtualPlanDocente", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualPlanDocente.focus();
	return true;
}
function abrirInforme(url) {
	var ancho = 550;
	var alto = 600;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualInforme = window.open(url, "AulaVirtualInforme", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=0,top=" + Y + ",left=" + X + "");
	aulaVirtualInforme.focus();
	return true;
}
function abrirReporte(url) {
	var ancho = screen.availWidth - 100;
	var alto = screen.availHeight - 200;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualReporte = window.open(url, "AulaVirtualReporte", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualReporte.focus();
	return true;
}
function abrirMiClaseAdmin(url) {
	var ancho = 920;
	var alto = 540;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualMiClaseAdmin = window.open(url, "AulaVirtualMiClaseAdmin", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=0,top=" + Y + ",left=" + X + "");
	aulaVirtualMiClaseAdmin.focus();
	return true;
}
function abrirVitrina(url, titulo, ancho, alto) {
	var ancho = 540;
	var alto = 500;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualVitrina = window.open(url, "AulaVirtualVitrina", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=0,top=" + Y + ",left=" + X + "");
	aulaVirtualVitrina.focus();
	return true;
}
function abrirLectura(url, titulo, ancho, alto) {
	var ancho = 540;
	var alto = 500;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualLectura = window.open(url, "AulaVirtualLectura", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=0,top=" + Y + ",left=" + X + "");
	aulaVirtualLectura.focus();
	return true;
}
function procesarTexto(contexto, idUnidad, indice, urlTexto) {
	copiarRecurso(contexto + "/admin/aulavirtual/CopiarTexto.action?idunidad=" + idUnidad + "&indice=" + indice);
	setTimeout("abrirTexto('" + urlTexto + "')", 500);
}
function procesarRepaso(contexto, idUnidad, urlRepaso, ancho, alto) {
	copiarRecurso(contexto + "/admin/aulavirtual/CopiarRepaso.action?idunidad=" + idUnidad);
	setTimeout("abrirRepaso('" + urlRepaso + "','" + ancho + "','" + alto + "')", 500);
}
function procesarLaboratorio(contexto, idUnidad, indice, urlTexto) {
	copiarRecurso(contexto + "/admin/aulavirtual/CopiarLaboratorio.action?idunidad=" + idUnidad + "&indice=" + indice);
	setTimeout("abrirLaboratorioPdf('" + urlTexto + "')", 500);
}

function abrirVerEstudiante(url) {
	var ancho = 860;
	var alto = 470;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualEstudiante = window.open(url, "AulaVirtualEstudiante", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=0,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualEstudiante.focus();
	return true;
}
function abrirLaboratorioPdf(url) {
	var ancho = 770;
	var alto = 500;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualLaboratorioPdf = window.open(url, "AulaVirtualLaboratorioPdf", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualLaboratorioPdf.focus();
	return true;
}
function copiarRecurso(url) {
	var ajax = nuevoAjax();
	ajax.open("POST", url, true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send(null);
}
function procesarLaboratorioPorTipo(idUnidad, contexto, indiceUnidad, ruta) {
	var objeto = document.getElementById("LC_" + idUnidad);
	if (objeto.checked) {
		abrirLaboratorio(contexto + "/aulavirtual/laboratorio/Cargar.action?idUnidad=" + idUnidad);
	} else {
		procesarLaboratorio(contexto, idUnidad, indiceUnidad, ruta);
	}
}
function theBegin() {
	for (i = 1; i <= 9; i = i + 1) {
		if (i == 8) {
			i = 9;
		}
		var fuck = 0;
		for (o = 0; o < document.getElementsByName("D_" + i).length; o = o + 1) {
			if (document.getElementsByName("D_" + i)[o].checked) {
				fuck = fuck + 1;
			}
		}
		try {
			if (fuck > 0) {
				document.getElementById(i + "D").checked = true;
			} else {
				document.getElementById(i + "D").checked = false;
			}
		}
		catch (e) {
		}
	}
	var fuck = 0;
	for (i = 1; i <= 9; i = i + 1) {
		if (i == 8) {
			i = 9;
		}
		var fuck = 0;
		for (o = 0; o < document.getElementsByName("E_" + i).length; o = o + 1) {
			if (document.getElementsByName("E_" + i)[o].checked) {
				fuck = fuck + 1;
			}
		}
		try {
			if (fuck > 0) {
				document.getElementById(i + "E").checked = true;
			} else {
				document.getElementById(i + "E").checked = false;
			}
		}
		catch (e) {
		}
	}
	var fuck = 0;
	for (o = 0; o < document.getElementsByName("C_3").length; o = o + 1) {
		if (document.getElementsByName("C_3")[o].checked) {
			fuck = fuck + 1;
		}
	}
	try {
		if (fuck > 0) {
			document.getElementById("3C").checked = true;
		} else {
			document.getElementById("3C").checked = false;
		}
	}
	catch (e) {
	}
	for (i = 1; i <= 7; i = i + 1) {
		var fuck = 0;
		for (o = 0; o < document.getElementsByName("I_" + i).length; o = o + 1) {
			var activo = (document.getElementsByName("I_" + i)[o].src).substr((document.getElementsByName("I_" + i)[o].src).lastIndexOf(".") - 2).substr(0, 2);
			if (activo != "no") {
				fuck = fuck + 1;
			}
		}
		try {
			if (fuck > 0) {
				document.getElementById(i + "I").checked = true;
			} else {
				document.getElementById(i + "I").checked = false;
			}
		}
		catch (e) {
		}
	}
}
function cambiar_estado_ficha(contexto, recurso, tipo) {
	var cb = document.getElementsByName(tipo + "_" + recurso);
	var count = 0, estado = 0;
	if (tipo == "I") {
		for (i = 0; i < cb.length; i = i + 1) {
			var activo = (cb[i].src).substr((cb[i].src).lastIndexOf(".") - 2).substr(0, 2);
			if (activo != "no") {
				count = count + 1;
			}
		}
		if (count == 0) {
			estado = 1;
			for (i = 0; i < cb.length; i = i + 1) {
				cb[i].src = imgs[1][1][recurso];
			}
		} else {
			for (i = 0; i < cb.length; i = i + 1) {
				cb[i].src = imgs[0][1][recurso];
			}
		}
	} else {
		for (i = 0; i < cb.length; i = i + 1) {
			if (cb[i].checked) {
				count = count + 1;
			}
		}
		if (count == 0) {
			estado = 1;
			for (i = 0; i < cb.length; i = i + 1) {
				cb[i].checked = true;
			}
		} else {
			for (i = 0; i < cb.length; i = i + 1) {
				cb[i].checked = false;
			}
		}
	}
	var url = contexto + "/admin/aulavirtual/FichaRecursoCambiarEstado.action";
	var ajax = nuevoAjax();
	ajax.open("POST", url, true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("idRecurso=" + recurso + "&estado=" + estado + "&tipo=" + tipo);
}

