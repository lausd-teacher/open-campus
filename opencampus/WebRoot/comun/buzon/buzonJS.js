
var mensajes = new Array(); //contiene los mensajes de la pagina actual
var carpetas = new Array();
var carpeta = "R";
var aviso = document.getElementById("aviso");
var browser = navigator.userAgent;
var mensajeG = null; //es el mensaje actual, el que se encuentra abierto 
var destino = document.getElementById("destino");
var active = "R";
var newWin = null;
var toTo = "";
var toCC = "";
var pag = null;
var abierto = 0; // 1 si un mensaje esta abierto
var pagActual = 1;
var noL = null; //numero de mensajes no leidos
var idFila = null;
var maxPag = 10;

//en Firefox se muestran 14 mensajes al inicio, por temas de diseño
if(browser.indexOf('Firefox')>0){	
	maxPag = 14;
}


function init() {
	recibidos("R", 1);
	setInterval("noLeidos()", 30000); //cada 30'' revisa si hay nuevos mensajes de correo, si no se tiene un mensaje abierto, o redactando uno el nuevo mensaje aparece
}
function activo(menu) {
	xGetElementById(active).style.background = "white";
	xGetElementById(menu).style.background = "#DFF0FF";
	active = menu;
}
function toWhere(onde) {
	var matches = xGetElementById("matches");
	for (i = matches.length - 1; i >= 0; i = i - 1) {
		if (matches[i].selected) {
			var op = xCreate("option");
			op.text = matches[i].text;
			op.value = matches[i].value;
			try {
				onde.add(op, onde.length);
			}
			catch (Exception) {
				onde.add(op, null);
			}
			matches.remove(i);
		}
	}
}
function pasalo() {
	var to = xGetElementById("to");
	var cc = xGetElementById("ccList");
	var destino = opener.document.getElementById("destino");
	var ccIn = opener.document.getElementById("cc");
	try {
		if (to.length > 0) {
			for (i = 0; i < to.length; i = i + 1) {
				toTo += "\"" + to.options[i].text + "\"" + "<" + to.options[i].value + ">,";
			}
			if (trim(destino.value).length > 0) {
				if ((destino.value.substring(trim(destino.value).length - 1, trim(destino.value).length)) != ",") {
					destino.value += ",";
				}
			}
			destino.value += toTo;
		}
		if (cc.length > 0) {
			for (i = 0; i < cc.length; i = i + 1) {
				toCC += "\"" + cc.options[i].text + "\"" + "<" + cc.options[i].value + ">,";
			}
			if (trim(ccIn.value).length > 0) {
				if ((ccIn.value.substring(trim(ccIn.value).length - 1, trim(ccIn.value).length)) != ",") {
					ccIn.value += ",";
				}
			}
			ccIn.value += toCC;
		}
	}
	catch (Exception) {
		window.reload();
	}
	window.close();
}
function eliToWhere() {
	var to = xGetElementById("to");
	var cc = xGetElementById("ccList");
	try {
		for (i = to.length - 1; i >= 0; i = i - 1) {
			if (to[i].selected) {
				to.remove(i);
			}
		}
		for (i = cc.length - 1; i >= 0; i = i - 1) {
			if (cc[i].selected) {
				cc.remove(i);
			}
		}
	}
	catch (Exception) {
	}
}
function buscarContacto() {
	var aBuscar = xGetElementById("aB").value;
	if (trim(aBuscar).length > 0) {
		var div = xGetElementById("matches");
		div.innerHTML = "";
		var ajax = nuevoAjax();
		var url = obtenerContextPath() + "/comun/buzon/BuscarContacto.action";
		ajax.open("POST", url, true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send("aBuscar=" + toHex(aBuscar));
		ajax.onreadystatechange = function () {
			if (ajax.readyState === 4) {
				var xml = ajax.responseXML;
				for (x = 0; x < xml.getElementsByTagName("coincidencia").length; x = x + 1) {
					var id = xml.getElementsByTagName("id")[x].childNodes[0].nodeValue;
					var nombre = xml.getElementsByTagName("nombreLargo")[x].childNodes[0].nodeValue;
					div.options[x] = new Option(nombre + "(" + id + ")", id);
					try {
						div.options[x].setAttribute("onDblClick", "toWhere(to)");
					}
					catch (Exception) {
						div.options[x].onDblClick = "toWhere(to)";
					}
				}
			}
		};
	}
}
function renombrar(id) {
	var nombre = trim(xGetElementById("c" + id).innerHTML);
	var newName = prompt("\xbfQu\xe9 nombre deseas asignar a esta carpeta?", [nombre]);
	if (newName == nombre) {
		alert("Debe renombrar la carpeta con un nombre distinto al existente.");
	} else {
		if (newName === null) {
			newName = nombre;
		} else {
			if (trim(newName).length === 0) {
				alert("Ingrese nombre para la carpeta.");
			} else {
				if (trim(newName).length > 14) {
					alert("N\xfamero de caracteres inv\xe1lido.");
				} else {
					if (trim(newName).length > 0) {
						var ajax = nuevoAjax();
						var url = obtenerContextPath() + "/comun/buzon/CambiarNombreCarpeta.action";
						ajax.open("POST", url, true);
						ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
						ajax.send("idCarpeta=" + id + "&titulo=" + toHex(newName));
						ajax.onreadystatechange = function () {
							if (ajax.readyState === 4) {
								var txt = ajax.responseText;
								if (txt == "1") {
									alert("No puede haber carpetas duplicadas.");
								} else {
									xGetElementById("c" + id).innerHTML = newName;//n el popup				
									var span = opener.document.getElementById("c" + id);
									span.innerHTML = "<img src=\"" + obtenerContextPath() + "/img/carpeta.gif\"/><b class=\"texto\">&nbsp;&nbsp;" + newName + "</b>";
									var x = opener.document.form1.select;
									for (i = 0; i < x.length; i = i + 1) {
										if (x[i].value == id) {
											x[i].text = newName;
										}
									}
									var x = opener.document.getElementsByName("select2")[0];
									for (i = 0; i < x.length; i = i = i + 1) {
										if (x[i].value == id) {
											x[i].text = newName;
										}
									}
								}
							}
						};
					}
				}
			}
		}
	}
}
function elimimarCarpeta(id) {
	if (confirm("\xbfDeseas eliminar esta carpeta?")) {
		var ajax = nuevoAjax();
		var url = obtenerContextPath() + "/comun/buzon/EliminarCarpeta.action";
		ajax.open("POST", url, true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send("idCarpeta=" + id);
		ajax.onreadystatechange = function () {
			if (ajax.readyState === 4) {
				xHideD(xGetElementById(id));
				xHideD(opener.document.getElementById(id));
				removeOp(id);
			}
		};
		return true;
	} else {
		return false;
	}
}
function crearCarpeta(input) {
	var input = xGetElementById(input);
	var carp = trim(input.value);
	if (carp == "") {
		alert("Ingrese un nombre para la carpeta");
		input.focus();
		return false;
	} else {
		if (carp.length > 14) {
			alert("No puede tener mas de 14 caracteres");
			input.focus();
			return false;
		}
	}
	var ajax = nuevoAjax();
	var url = obtenerContextPath() + "/comun/buzon/CrearCarpeta.action";
	ajax.open("POST", url, true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("carpeta=" + toHex(carp));
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			var id = ajax.responseText;
			if (id == 0) {
				alert("No puede haber carpetas duplicadas.");
				input.focus();
			} else {
				if (id == "L") {
					alert("L\xedmite de carpetas alcanzado.");
					input.focus();
				} else {
					try {
						var menu = xGetElementById("nav");
						var div = xCreate("div");
						div.setAttribute("id", id);
						div.className = "nl";
						div.innerHTML = "<span style=\"cursor:pointer;\" id=\"c" + id + "\" class=\"texto\" onClick=\"recibidos('" + id + "')\"><img src=\"" + obtenerContextPath() + "/img/carpeta.gif\"/><b class=\"texto\">&nbsp;&nbsp;" + carp + "</b></span>";
						menu.appendChild(div);
						var x = document.form1.select;
						var y = xCreate("option");
						y.text = carp;
						y.value = id;
						try {
							x.add(y, x.length);
						}
						catch (Exception) {
							x.add(y, null);
						}
						var x = document.getElementsByName("select2")[0];
						var y = document.createElement("option");
						y.text = carp;
						y.value = id;
						try {
							x.add(y, x.length);
						}
						catch (Exception) {
							x.add(y, null);
						}
					}
					catch (Exception) {
						document.location.reload();
						var menu = opener.document.getElementById("nav");
						var div = opener.document.createElement("div");
						div.setAttribute("id", id);
						div.className = "nl";
						div.innerHTML = "<span style=\"cursor:pointer;\" id=\"c" + id + "\" class=\"texto\" onClick=\"recibidos('" + id + "')\"><img src=\"" + obtenerContextPath() + "/img/carpeta.gif\"/><b class=\"texto\">&nbsp;&nbsp;" + carp + "</b></span>";
						menu.appendChild(div);
						var x = opener.document.form1.select;
						var y = opener.document.createElement("option");
						y.text = carp;
						y.value = id;
						try {
							x.add(y, x.length);
						}
						catch (Exception) {
							x.add(y, null);
						}
						var x = opener.document.getElementsByName("select2")[0];
						var y = opener.document.createElement("option");
						y.text = carp;
						y.value = id;
						try {
							x.add(y, x.length);
						}
						catch (Exception) {
							x.add(y, null);
						}
					}
					input.value = "";
				}
			}
		}
	};
}
function todos() {
	for (var i = 0; i < document.getElementsByName("cb").length; i = i + 1) {
		document.getElementsByName("cb")[i].checked = true;
	}
}
function ninguna() {
	for (var i = 0; i < document.getElementsByName("cb").length; i = i + 1) {
		document.getElementsByName("cb")[i].checked = false;
	}
}
function deleteRows(rowObjArray) {
	for (var i = 0; i < rowObjArray.length; i = i + 1) {
		var rIndex = rowObjArray[i].sectionRowIndex;
		rowObjArray[i].parentNode.deleteRow(rIndex);
	}
}
function marcarLeidos() {
	for (var i = 0; i < document.getElementsByName("cb").length; i = i + 1) {
		document.getElementsByName("cb")[i].checked = false;
	}
	for (var i = 0; i < document.getElementsByName("cb").length; i = i + 1) {
		if (document.getElementsByName("cb")[i].id.split("_")[0] == "l") {
			document.getElementsByName("cb")[i].checked = true;
		}
	}
}
function marcarNoLeidos() {
	for (var i = 0; i < document.getElementsByName("cb").length; i = i + 1) {
		document.getElementsByName("cb")[i].checked = false;
	}
	for (var i = 0; i < document.getElementsByName("cb").length; i = i + 1) {
		if (document.getElementsByName("cb")[i].id.split("_")[0] == "n") {
			document.getElementsByName("cb")[i].checked = true;
		}
	}
}
function marcarDestacados() {
	for (var i = 0; i < document.getElementsByName("cb").length; i = i + 1) {
		document.getElementsByName("cb")[i].checked = false;
	}
	for (var i = 0; i < document.getElementsByName("cb").length; i = i + 1) {
		if (document.getElementsByName("cb")[i].id.split("_")[1] == 1) {
			document.getElementsByName("cb")[i].checked = true;
		}
	}
}
function marcarNoDestacados() {
	for (var i = 0; i < document.getElementsByName("cb").length; i = i + 1) {
		document.getElementsByName("cb")[i].checked = false;
	}
	for (var i = 0; i < document.getElementsByName("cb").length; i = i + 1) {
		if (document.getElementsByName("cb")[i].id.split("_")[1] == 0) {
			document.getElementsByName("cb")[i].checked = true;
		}
	}
}
function noLeidos() {
	var ajax = nuevoAjax();
	var url = obtenerContextPath() + "/comun/buzon/NoLeidos.action";
	ajax.open("POST", url, true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			if (noL != ajax.responseText) {
				xGetElementById("reci").innerHTML = ajax.responseText;
			}
			if ((noL != ajax.responseText) && (abierto === 0) && (carpeta == "R") && (pag === 1)) {
				recibidos(carpeta);
			}
			noL = ajax.responseText;
		}
	};
	ajax.send(null);
}
function cambiarMaxPag(maxPagi){
	maxPag = maxPagi;
	recibidos(carpeta,pagActual);
	xGetElementById("mp1")[0].selected = true;
	xGetElementById("mp2")[0].selected = true;
}
function recibidos(car, pagActual) {
	if (abierto === 1 && car == carpeta) {
		xShowD(xGetElementById("tct"));
		xShowD(xGetElementById("fs"));
		xShowD(xGetElementById("tbd"));
		xShowD(xGetElementById("tf"));
		xShowD(xGetElementById("tcb"));
		xHideD(xGetElementById("redac"));
		xHideD(xGetElementById("elMensaje"));
	} else {
		xShowD(xGetElementById("tct"));
		xShowD(xGetElementById("fs"));
		xShowD(xGetElementById("tbd"));
		xShowD(xGetElementById("tf"));
		xShowD(xGetElementById("tcb"));
		xHideD(xGetElementById("redac"));
		xHideD(xGetElementById("elMensaje"));
		if (pagActual == null) {
			pagActual = 1;
		}
		pag = pagActual;
		if (car == null) {
			car = "R";
		}
		activo(car);
		if (car == "F") {
			xGetElementById("opts").disabled = true;
			xGetElementById("bam").disabled = true;
		} else {
			xGetElementById("opts").disabled = false;
			xGetElementById("bam").disabled = false;
		}
		for (i = 0; i < xGetElementById("opts").length; i = i + 1) {
			if (car == xGetElementById("opts")[i].value) {
				xGetElementById("opts")[i].style.color = "gray";
			} else {
				xGetElementById("opts")[i].style.color = "black";
			}
		}
		for (i = 0; i < xGetElementById("bam").length; i = i + 1) {
			if (car == xGetElementById("bam")[i].value) {
				xGetElementById("bam")[i].style.color = "gray";
			} else {
				xGetElementById("bam")[i].style.color = "black";
			}
		}
		/* div q dice Recibiendo con Rojo en la izkina sup de */
		xGetElementById("aviso").innerHTML = "Recibiendo...";
		carpeta = car;
		var ajax = nuevoAjax();
		var url = obtenerContextPath() + "/comun/buzon/MensajesRecibidos.action";
		ajax.open("POST", url, true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send("carpeta=" + car + "&pagActual=" + pagActual + "&cantidadPorPagina=" + maxPag);
		ajax.onreadystatechange = function () {
			if (ajax.readyState === 4) {
				//noLeidos();
				xGetElementById("aviso").innerHTML = "";
				var xml = ajax.responseXML;
				//alert(ajax.responseText);
				try {
					var total = xml.getElementsByTagName("total")[0].childNodes[0].nodeValue;
					var ult = Math.ceil(total / maxPag);
					xGetElementById("all").innerHTML = total + "&nbsp;";
					xGetElementById("all2").innerHTML = total + "&nbsp;";
					if (pagActual != ult && pagActual < ult) {
						xGetElementById("siguiente").innerHTML = "<a href=\"#\" onclick=\"recibidos('" + car + "'," + (pagActual + 1) + ")\">&nbsp;&gt;&nbsp;&nbsp;</a>";
						xGetElementById("siguiente1").innerHTML = "<a href=\"#\" onclick=\"recibidos('" + car + "'," + (pagActual + 1) + ")\">&nbsp;&gt;&nbsp;&nbsp;</a>";
						xGetElementById("ultimo1").innerHTML = "<a href=\"#\" onclick=\"recibidos('" + car + "'," + ult + ")\"> &Uacute;ltimo &gt;&gt;&nbsp;</a>";
						xGetElementById("ultimo").innerHTML = "<a href=\"#\" onclick=\"recibidos('" + car + "'," + ult + ")\"> &Uacute;ltimo &gt;&gt;&nbsp;</a>";
					} else {
						xGetElementById("ultimo").innerHTML = "";
						xGetElementById("ultimo1").innerHTML = "";
						xGetElementById("siguiente").innerHTML = "";
						xGetElementById("siguiente1").innerHTML = "";
					}
					if (pagActual != 1) {
						xGetElementById("anterior").innerHTML = "<a href=\"#\" onclick=\"recibidos('" + car + "'," + (pagActual - 1) + ")\">&nbsp;&nbsp;&lt;&nbsp;</a>";
						xGetElementById("anterior1").innerHTML = "<a href=\"#\" onclick=\"recibidos('" + car + "'," + (pagActual - 1) + ")\">&nbsp;&nbsp;&lt;&nbsp;</a>";
						xGetElementById("primero").innerHTML = "<a href=\"#\" onclick=\"recibidos('" + car + "',1)\">&nbsp; &lt;&lt; Primero </a>";
						xGetElementById("primero1").innerHTML = "<a href=\"#\" onclick=\"recibidos('" + car + "',1)\">&nbsp; &lt;&lt; Primero </a>";
					} else {
						xGetElementById("anterior").innerHTML = "";
						xGetElementById("anterior1").innerHTML = "";
						xGetElementById("primero").innerHTML = "";
						xGetElementById("primero1").innerHTML = "";
					}
					var pA = xml.getElementsByTagName("actual")[0].childNodes[0].nodeValue;
					var start = xml.getElementsByTagName("start")[0].childNodes[0].nodeValue;
					xGetElementById("start").innerHTML = start;
					xGetElementById("start2").innerHTML = start;
					var end = xml.getElementsByTagName("end")[0].childNodes[0].nodeValue;
					xGetElementById("end").innerHTML = end;
					xGetElementById("end2").innerHTML = end;
					
					var tbd = xGetElementById("tbd");
					tbd.innerHTML = '';
					
					var table = xCreate("table");
					table.className = "tablaMensajes";
					table.setAttribute("id", "mensajes");
					var colgroup = xCreate('colgroup');
					var tbody = xCreate("tbody");
										
					var col1 = xCreate('col');
					col1.className = "col1";
					
					var col2 = xCreate('col');
					col2.className = "col2";
					
					var col3 = xCreate('col');
					col3.className = "col3";
					
					var colExtra = xCreate('col');
					colExtra.className = "colExtra";
					
					var col4 = xCreate('col');					
					
					var col5 = xCreate('col');
					col5.className = "col5";
					
					var col6 = xCreate('col');
					col6.className = "col6";
										
					colgroup.appendChild(col1);
					colgroup.appendChild(col2);
					colgroup.appendChild(col3);
					colgroup.appendChild(colExtra);
					colgroup.appendChild(col4);
					colgroup.appendChild(col5);
					colgroup.appendChild(col6);					
					table.appendChild(colgroup);
					
					if (total > 0) {
						mensajes.length = 0;
						var a = 0;
						for (i = 0; i < xml.getElementsByTagName("mensaje").length; i = i + 1) {
							var id = xml.getElementsByTagName("id")[i].childNodes[0].nodeValue;
							var titulo = xml.getElementsByTagName("titulo")[i].childNodes[0].nodeValue;
							var remitente = xml.getElementsByTagName("remitente")[i].childNodes[0].nodeValue;
							var contenido = xml.getElementsByTagName("contenido")[i].childNodes[0].nodeValue;
							var carp = xml.getElementsByTagName("carpeta")[i].childNodes[0].nodeValue;
							var adjunto = xml.getElementsByTagName("adjunto")[i].childNodes[0].nodeValue;
							var fecha_envio = xml.getElementsByTagName("fecha_envio")[i].childNodes[0].nodeValue;
							var favorito = xml.getElementsByTagName("favorito")[i].childNodes[0].nodeValue;
							var estado = xml.getElementsByTagName("estado")[i].childNodes[0].nodeValue;//leido o no
							var idUsuarioE = xml.getElementsByTagName("idUsuarioEnvio")[i].childNodes[0].nodeValue;
							var destinos = xml.getElementsByTagName("destinos")[i].childNodes[0].nodeValue;
							var ccs = xml.getElementsByTagName("ccs")[i].childNodes[0].nodeValue;
							var tipo = xml.getElementsByTagName("tipo")[i].childNodes[0].nodeValue;//remitente, destino, esa vaina
							var adjNom = "";
							var adjId = "";
							var adjSiz = "";
							var adjFec = "";
							if (adjunto == "1") {
								adjNom = xml.getElementsByTagName("adjNom")[a].childNodes[0].nodeValue;
								adjId = xml.getElementsByTagName("adjId")[a].childNodes[0].nodeValue;
								adjSiz = xml.getElementsByTagName("adjSiz")[a].childNodes[0].nodeValue;
								adjFec = xml.getElementsByTagName("adjFe")[a].childNodes[0].nodeValue;
								a = a + 1;
							}
							mensaje = {leidoM:estado, tipo:tipo, adFeM:adjFec, adjIdM:adjId, adjNomM:adjNom, ccsM:ccs, destinosM:destinos, idUE:idUsuarioE, adjSizM:adjSiz, idM:id, tituloM:titulo, remitenteM:remitente, contenidoM:contenido, carpetaM:carp, adjuntoM:adjunto, fecha_envioM:fecha_envio, favoritoM:favorito, estadoM:estado};
							mensajes.push(mensaje);
							
							/*
							var trk = xCreate("tr");
							trk.className = "leido";
							var tdk1 = xCreate("td");
							tdk1.innerHTML = "1";
							var tdk2 = xCreate("td");
							tdk2.innerHTML = "2";
							var tdk3 = xCreate("td");
							tdk3.innerHTML = "3";
							var tdk4 = xCreate("td");
							tdk4.innerHTML = "4";
							var tdk5 = xCreate("td");
							tdk5.innerHTML = "5";
							var tdk6 = xCreate("td");
							tdk6.innerHTML = "6";
							var tdk7 = xCreate("td");
							tdk7.innerHTML = "7";
							*/
							
							
							var tr = xCreate("tr");
							var ln = null;
							if (estado == "0") {
								ln = "n";
								tr.className = "noLeido";
							} else {
								ln = "l";
								tr.className = "leido";
							}
							var idRow = ln + "_" + id;
							tr.setAttribute("id", idRow);
							
							/*checkbox*/
							var td1 = xCreate("td");							
							td1.innerHTML = "<input id='" + ln + "_" + favorito + "' name =\"cb\" type=\"checkbox\" value=\"" + tipo + id + "\"/>";
							
							/*estrellita estrellita*/
							var td2 = xCreate("td");
							td2.width = "10";
							
							/*td2.className = "sc v";*/
							if (carpeta != "P") {
								td2.innerHTML = iconoFavorito(favorito, id, carpeta, tipo, "1");
							} else {
								td2.innerHTML = "&nbsp;";
							}
							
							/*nombre remitente o destino o whatever*/
							/*AQUI VA ATACAR KIKE - PONER EL COLOR SEGUN EL ESTADO DEL MENSAJE - LEIDO O NO LEIDO*/
							var td3 = xCreate("td");
							if (tipo != "R") {
								remitente = remitente.replace(/&lt;/i, "(");
								remitente = remitente.replace(/&gt;/i, ")");
								td3.innerHTML = "<div onclick=\"abrirMensaje('" + id + "','" + tipo + "','" + idRow + "')\">" + remitente + "</div>";
							} else {								
								td3.innerHTML = "<div onclick=\"abrirMensaje('" + id + "','" + tipo + "','" + idRow + "')\">Para: " + destinos + "</div>";
							}
							
							var tdExtra = xCreate("td");
							
							/*titulo del mensaje*/
							var td5 = xCreate("td");							
							//td5.innerHTML = "<div onclick=\"abrirMensaje('" + id + "','" + tipo + "','" + idRow + "')\"><span class=\"ct\"/>" + titulo + "</span></div>";
							td5.innerHTML = "<div onclick=\"abrirMensaje('" + id + "','" + tipo + "','" + idRow + "')\"></div>";
							
							//** Parche XSS **********//
							var divTitle = td5.childNodes[0];
							var spanTitle = xCreate("span");
							spanTitle.className = "ct";
							var textTitle = document.createTextNode(titulo);
							spanTitle.appendChild(textTitle);
							divTitle.appendChild(spanTitle);
							//**********************//
							
							/*clip de adjunto*/
							var td6 = xCreate("td");
							td6.innerHTML = iconoAdjunto(adjunto, adjNom, adjSiz, adjId, adjFec);
							if (td6.innerHTML == "") {
								td6.innerHTML = "&nbsp;";
							}
							
							/*the fecha*/
							var td7 = xCreate("td");							
							td7.innerHTML = "<span onclick=\"abrirMensaje('" + id + "','" + tipo + "','" + idRow + "')\" id=\"\">" + fecha_envio + "</span>";
										
							/*trk.appendChild(tdk1);
							trk.appendChild(tdk2);
							trk.appendChild(tdk3);
							trk.appendChild(tdk4);
							trk.appendChild(tdk5);
							trk.appendChild(tdk6);
							trk.appendChild(tdk7);
							tbody.appendChild(trk);		*/	
										
														
							tr.appendChild(td1);
							tr.appendChild(td2);
							tr.appendChild(td3);
							tr.appendChild(tdExtra);
							tr.appendChild(td5);
							tr.appendChild(td6);
							tr.appendChild(td7);
							tbody.appendChild(tr);							
						}
						table.appendChild(tbody);
						tbd.appendChild(table);
					}else{
						tbd.innerHTML = "<div class=\"tf\" id=\"mensajeInicial\">No tiene mensajes en esta carpeta.</div>";
					}
				}
				catch (e) {
					if(e.description)
						alert(e.description)
					else
						alert(e)
				}
			}
		};
	}
	abierto = 0;
}
function nuevo() {
	activo("redactar");
	carpeta = "redactar";
	xHideD(xGetElementById("tct"));
	xHideD(xGetElementById("fs"));
	xHideD(xGetElementById("tbd"));
	xHideD(xGetElementById("tf"));
	xHideD(xGetElementById("tcb"));
	xHideD(xGetElementById("elMensaje"));
	xShowD(xGetElementById("redac"));
	xGetElementById("destino").value = "";
	xGetElementById("cc").value = "";
	xGetElementById("titulo").value = "";
	xGetElementById("enviar").disabled = false;
	var te = xGetElementById("elTexto");
	te.innerHTML = "<textarea\tname=\"contenido\" id=\"contenido\" type=\"_moz\" style=\"background: white;\">&nbsp;<br>" + "</textarea>";
	generate_wysiwyg("contenido", "542", "150");
	xResizeTo('wysiwygcontenido',xClientWidth()-200,xClientHeight()-330);
	abierto = 1;
}
function enviarMensaje() {
	var con = xGetElementById("wysiwyg" + "contenido").contentWindow.document.body.innerHTML;
	var conClear = stripNewLine(con.stripTags().stripScripts().strip().replace(/&nbsp;/g, ""));
	if (trim(xGetElementById("destino").value) == "") {
		alert("Ingrese destino al mensaje.");
		xGetElementById("destino").focus();
		return false;
	} else {
		if (conClear.length == 0) {
			alert("Ingrese contenido al mensaje.");
			return false;
		} else {
			if (trim(xGetElementById("titulo").value) == "") {
				if (confirm("\xbfDesea enviar el mensaje sin t\xedtulo?")) {
				} else {
					xGetElementById("titulo").focus();
					return false;
				}
			}
		}
	}
	xGetElementById("enviar").disabled = true;
}
/* ---------------------------------------------------------------------- *
  navegación por mensajes, al llegar al último, traer los siguientes mensajes
* ---------------------------------------------------------------------- */
function abrirMensaje(id, tipo, idRow) {
	idFila = idRow;
	abierto = 1;
	xHideD(xGetElementById("tct"));
	xHideD(xGetElementById("fs"));
	xHideD(xGetElementById("tbd"));
	xHideD(xGetElementById("tf"));
	xHideD(xGetElementById("tcb"));
	xShowD(xGetElementById("elMensaje"));
	var tmp;
	var mensaje = null;
	var o = 0;
	for (i = 0; i < mensajes.length; i = i + 1) {
		if ((id == mensajes[i].idM) && (tipo == mensajes[i].tipo)) {
			tmp = mensajes[i].leidoM;
			mensajes[i].leidoM = "1";
			mensajeG = mensajes[i];
			break;
		}
	}
	if (tmp == "0") {
		var ajax = nuevoAjax();
		var url = obtenerContextPath() + "/comun/buzon/MarcarLeido.action";
		ajax.open("POST", url, true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send("id=" + mensajeG.idM + "&tipo=" + tipo);
		ajax.onreadystatechange = function () {
			if (ajax.readyState === 4) {
				noLeidos();
				var trTMP = xGetElementById("n_" + mensajeG.idM);
				xGetElementById("n_" + mensajeG.idM).removeAttribute("id");
				trTMP.setAttribute("id", "l_" + mensajeG.idM);
				trTMP.className = "leido";
				trTMP.getElementsByTagName("input")[0].setAttribute("id", "l_" + mensajeG.favorito);
			}
		};
	}
	var tit = xGetElementById("tit");
	var ant = xGetElementById("ant");
	if (carpeta != "P") {
		ant.innerHTML = iconoFavorito(mensajeG.favoritoM, mensajeG.idM, mensajeG.carpetaM, mensajeG.tipo, "2");
	}
	var det = xGetElementById("det");
	var deta1 = xGetElementById("detallesMensaje");
	var de = xGetElementById("de");
	var para = xGetElementById("to");
	var cc = xGetElementById("tocc");
	var fecha = xGetElementById("fecha");
	var asunto = xGetElementById("asunto");
	var cuerpo = xGetElementById("cuerpo");
	var acciones = xGetElementById("acciones");
	if (carpeta == "P" || carpeta == "E") {
		xHideD(acciones);
	} else {
		xShowD(acciones);
	}
	tit.innerHTML = "<strong>" + mensajeG.tituloM + "</strong>";
	det.innerHTML = mensajeG.remitenteM + ", " + mensajeG.fecha_envioM + "<span class=\"l\" href=\"#\" onClick=\"xChangeDisplay('detallesMensaje')\"> - Mostrar detalles</span>&nbsp;" + iconoAdjunto(mensajeG.adjuntoM, mensajeG.adjNomM, mensajeG.adjSiz, mensajeG.adjIdM, mensajeG.adFeM);
	de.innerHTML = "De: " + mensajeG.remitenteM + "&lt;" + mensajeG.idUE + "&gt;";
	para.innerHTML = "Para: " + mensajeG.destinosM;
	if (mensajeG.ccsM.length > 0) {
		cc.innerHTML = "Cc: " + mensajeG.ccsM;
	}
	fecha.innerHTML = "Fecha: " + mensajeG.fecha_envioM;
	asunto.innerHTML = "Asunto: " + mensajeG.tituloM;
	cuerpo.innerHTML = "<br>" + mensajeG.contenidoM + "<br><br>";
	if (mensajeG.adjuntoM > 0) {
		cuerpo.innerHTML = cuerpo.innerHTML + "Mensajes adjuntos:<strong> " + "<a href=\"" + obtenerContextPath() + "/comun/buzon/DescargarAdjunto.action?idAdjunto=" + mensajeG.adjIdM + "&archivoNombre=" + mensajeG.adjNomM + "&fechaAd=" + mensajeG.adFeM + "\">" + mensajeG.adjNomM + "</a> " + Math.ceil(mensajeG.adjSizM / 1024) + "Kb.</strong>";
	}
	if (mensajeG.ccsM.length === 0) {
		xHideD(xGetElementById("replyAll"));
	} else {
		xShowD(xGetElementById("replyAll"));
	}
}
function reply() {
	xHideD(xGetElementById("tct"));
	xHideD(xGetElementById("fs"));
	xHideD(xGetElementById("tbd"));
	xHideD(xGetElementById("tf"));
	xHideD(xGetElementById("tcb"));
	xHideD(xGetElementById("elMensaje"));
	xShowD(xGetElementById("redac"));
	xGetElementById("destino").value = mensajeG.remitenteM + "<" + mensajeG.idUE + ">";
	xGetElementById("cc").value = "";
	xGetElementById("titulo").value = "Re: " + mensajeG.tituloM;
	xGetElementById("enviar").disabled = false;
	var te = xGetElementById("elTexto");
	te.innerHTML = "<textarea\tname=\"contenido\" id=\"contenido\" type=\"_moz\" style=\"background: white;\"> " + "<br><br>-----------------------------------<br>Mensaje enviado por: " + mensajeG.remitenteM + "&lt;" + mensajeG.idUE + "&gt;" + ", el " + mensajeG.fecha_envioM + "<br>" + mensajeG.contenidoM + " " + "</textarea>";
	generate_wysiwyg("contenido", "542", "150");
	xResizeTo('wysiwygcontenido',xClientWidth()-200,xClientHeight()-330);
}
function replyAll() {
	xHideD(xGetElementById("tct"));
	xHideD(xGetElementById("fs"));
	xHideD(xGetElementById("tbd"));
	xHideD(xGetElementById("tf"));
	xHideD(xGetElementById("tcb"));
	xHideD(xGetElementById("elMensaje"));
	xShowD(xGetElementById("redac"));
	xGetElementById("destino").value = mensajeG.remitenteM + "<" + mensajeG.idUE + ">";
	mensajeG.ccsM = mensajeG.ccsM.replace(/&lt;/g, "<");
	mensajeG.ccsM = mensajeG.ccsM.replace(/&gt;/g, ">");
	xGetElementById("cc").value = mensajeG.ccsM;
	xGetElementById("titulo").value = "Re: " + mensajeG.tituloM;
	xGetElementById("enviar").disabled = false;
	var te = xGetElementById("elTexto");
	te.innerHTML = "<textarea\tname=\"contenido\" id=\"contenido\" type=\"_moz\" style=\"background: white;\">" + "<br><br>-----------------------------------<br>Mensaje enviado por: " + mensajeG.remitenteM + "&lt;" + mensajeG.idUE + "&gt;" + ", el " + mensajeG.fecha_envioM + "<br>" + mensajeG.contenidoM + "</textarea>";
	generate_wysiwyg("contenido", "542", "150");
	xResizeTo('wysiwygcontenido',xClientWidth()-200,xClientHeight()-330);
}
function reenviar() {
	xHideD(xGetElementById("tct"));
	xHideD(xGetElementById("fs"));
	xHideD(xGetElementById("tbd"));
	xHideD(xGetElementById("tf"));
	xHideD(xGetElementById("tcb"));
	xHideD(xGetElementById("elMensaje"));
	xShowD(xGetElementById("redac"));
	xGetElementById("destino").value = "";
	xGetElementById("titulo").value = "Fw: ";
	xGetElementById("enviar").disabled = false;
	var te = xGetElementById("elTexto");
	te.innerHTML = "<textarea\tname=\"contenido\" id=\"contenido\" type=\"_moz\" style=\"background: white;\">" + "<br><br>-----------------------------------<br>Mensaje enviado por: " + mensajeG.remitenteM + "&lt;" + mensajeG.idUE + "&gt;" + ", el " + mensajeG.fecha_envioM + "<br>" + mensajeG.contenidoM + "</textarea>";
	generate_wysiwyg("contenido", "542", "150");
	xResizeTo('wysiwygcontenido',xClientWidth()-200,xClientHeight()-330);
}
var actual;
var estado;
function cambiarNoLeido() {
	var checkedObjArray = new Array();
	var cCount = 0;
	try {
		var tbl = document.getElementById("mensajes");
	}
	catch (Exception) {
		var tbl = document.getElementsByName("mensajes")[0];
	}
	for (var i = 0; i < document.getElementsByName("cb").length; i = i + 1) {
		if (document.getElementsByName("cb")[i].checked) {
			var cb = document.getElementsByName("cb")[i];
			var idMensaje = cb.value.substring(1, cb.value.length);
			var tipo = cb.value.substring(0, 1);
			checkedObjArray[cCount] = tbl.tBodies[0].rows[i];
			var ajax = nuevoAjax();
			var url = obtenerContextPath() + "/comun/buzon/MarcarNoLeido.action";
			ajax.open("POST", url, true);
			ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			ajax.send("id=" + idMensaje + "&tipo=" + tipo);
			ajax.onreadystatechange = function () {
				if (ajax.readyState === 4) {
					noLeidos();
				}
			};
			cb.parentNode.parentNode.className = "noLeido";
			cCount++;
		}
	}
	xGetElementById("opts")[0].selected = true;
	xGetElementById("bam")[0].selected = true;
}
function cambiarLeido() {
	var checkedObjArray = new Array();
	var cCount = 0;
	try {
		var tbl = document.getElementById("mensajes");
	}
	catch (Exception) {
		var tbl = document.getElementsByName("mensajes")[0];
	}
	for (var i = 0; i < document.getElementsByName("cb").length; i = i + 1) {
		if (document.getElementsByName("cb")[i].checked) {
			var cb = document.getElementsByName("cb")[i];
			var idMensaje = cb.value.substring(1, cb.value.length);
			var tipo = cb.value.substring(0, 1);
			checkedObjArray[cCount] = tbl.tBodies[0].rows[i];
			var ajax = nuevoAjax();
			var url = obtenerContextPath() + "/comun/buzon/MarcarLeido.action";
			ajax.open("POST", url, true);
			ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			ajax.send("id=" + idMensaje + "&tipo=" + tipo);
			ajax.onreadystatechange = function () {
				if (ajax.readyState === 4) {
					noLeidos();
				}
			};
			cb.parentNode.parentNode.className = "leido";
			cCount++;
		}
	}
	xGetElementById("opts")[0].selected = true;
	xGetElementById("bam")[0].selected = true;
}
function moverMensajes(idCar) {
	if (idCar == "P") {
		moverPapelera();
	} else {
		if (idCar == "N") {
			cambiarNoLeido();
		} else {
			if (idCar == "L") {
				cambiarLeido();
			} else {
				xGetElementById("aviso").innerHTML = "Moviendo mensajes...";
				if (idCar != carpeta) {
					var checkedObjArray = new Array();
					var cCount = 0;
					try {
						var tbl = document.getElementById("mensajes");
					}
					catch (Exception) {
						var tbl = document.getElementsByName("mensajes")[0];
					}
					for (var i = 0; i < document.getElementsByName("cb").length; i = i + 1) {
						if (document.getElementsByName("cb")[i].checked) {
							var actual = document.getElementsByName("cb")[i].value;
							checkedObjArray[cCount] = tbl.tBodies[0].rows[i];
							cCount++;
							var ajax = nuevoAjax();
							var url = obtenerContextPath() + "/comun/buzon/MoverCarpeta.action";
							ajax.open("POST", url, true);
							ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
							ajax.send("idMensaje=" + actual.substring(1, actual.length) + "&tipo=" + actual.substring(0, 1) + "&idCarpeta=" + idCar + "&carpeta=" + carpeta);
						}
					}
					if (checkedObjArray.length > 0) {
						var rIndex = checkedObjArray[0].sectionRowIndex;
						deleteRows(checkedObjArray);
					}
					recibidos(carpeta);
				} else {
					xGetElementById("aviso").innerHTML = "";
				}
				xGetElementById("opts")[0].selected = true;
				xGetElementById("bam")[0].selected = true;
			}
		}
	}
}
function moveInside(idCar) {
	xGetElementById("aviso").innerHTML = "Moviendo mensajes...";
	if (idCar == "P") {
		eliminarMensajeIn();
	}
	if (idCar != carpeta) {
		var ajax = nuevoAjax();
		var url = obtenerContextPath() + "/comun/buzon/MoverCarpeta.action";
		ajax.open("POST", url, true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send("idMensaje=" + mensajeG.idM + "&tipo=" + mensajeG.tipo + "&idCarpeta=" + idCar + "&carpeta=" + carpeta);
		ajax.onreadystatechange = function () {
			if (ajax.readyState === 4) {
				xHideD(idFila);
				recibidos(carpeta);
				xGetElementById("aviso").innerHTML = "";
			}
		};
	}
	xGetElementById("bam2")[0].selected = true;
}
function moverPapelera() {
	var checkedObjArray = new Array();
	var cCount = 0;
	try {
		var tbl = document.getElementById("mensajes");
	}
	catch (Exception) {
		var tbl = document.getElementsByName("mensajes")[0];
	}
	for (var i = 0; i < document.getElementsByName("cb").length; i++) {
		if (document.getElementsByName("cb")[i].checked) {
			xGetElementById("aviso").innerHTML = "Enviando a papelera...";
			var actual = document.getElementsByName("cb")[i].value;
			checkedObjArray[cCount] = tbl.tBodies[0].rows[i];
			cCount++;
			var ajax = nuevoAjax();
			var url = obtenerContextPath() + "/comun/buzon/EnviarPapelera.action";
			ajax.open("POST", url, true);
			ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			ajax.send("idMensaje=" + actual.substring(1, actual.length) + "&tipo=" + actual.substring(0, 1));
			ajax.onreadystatechange = function () {
				if (ajax.readyState === 4) {
					xGetElementById("aviso").innerHTML = "";
				}
			};
		}
	}
	if (checkedObjArray.length > 0) {
		var rIndex = checkedObjArray[0].sectionRowIndex;
		deleteRows(checkedObjArray);
	}
	recibidos(carpeta);
	xGetElementById("opts")[0].selected = true;
	xGetElementById("bam")[0].selected = true;
}
function eliminar() {
	if (carpeta != "P") {
		moverMensajes("P");
	} else {
		var checkedObjArray = new Array();
		var cCount = 0;
		try {
			var tbl = document.getElementById("mensajes");
		}
		catch (Exception) {
			var tbl = document.getElementsByName("mensajes")[0];
		}
		for (var i = 0; i < document.getElementsByName("cb").length; i++) {
			if (document.getElementsByName("cb")[i].checked) {
				var actual = document.getElementsByName("cb")[i].value;
				checkedObjArray[cCount] = tbl.tBodies[0].rows[i];
				cCount++;
				xGetElementById("aviso").innerHTML = "Eliminando mensajes...";
				var ajax = nuevoAjax();
				var url = obtenerContextPath() + "/comun/buzon/Eliminar.action";
				ajax.open("POST", url, true);
				ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				ajax.send("idMensaje=" + actual.substring(1, actual.length) + "&tipo=" + actual.substring(0, 1));
				ajax.onreadystatechange = function () {
					if (ajax.readyState === 4) {
						xGetElementById("aviso").innerHTML = "";
					}
				};
			}
		}
		if (checkedObjArray.length > 0) {
			var rIndex = checkedObjArray[0].sectionRowIndex;
			deleteRows(checkedObjArray);
		}
		recibidos(carpeta);
	}
}
function eliminarMensajeIn() {
	var ajax = nuevoAjax();
	if (carpeta != "P") {
		var url = obtenerContextPath() + "/comun/buzon/EnviarPapelera.action";
	} else {
		var url = obtenerContextPath() + "/comun/buzon/Eliminar.action";
	}
	ajax.open("POST", url, true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("idMensaje=" + mensajeG.idM + "&tipo=" + mensajeG.tipo);
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			xHideD(idFila);
			recibidos(carpeta);
		}
	};
}
function mostrarDetalles() {
	xChangeDisplay(xGetElementById("det"));
}
function marcarFavorito(id, carpeta, tipo, x) {
	var mensaje = null;
	for (i = 0; i < mensajes.length; i = i + 1) {
		if ((id == mensajes[i].idM) && (tipo == mensajes[i].tipo)) {
			mensaje = mensajes[i];
			mensajeG = mensaje;
			break;
		}
	}
	var img1 = xGetElementById("img1" + tipo + id);
	if (abierto === 1) {
		var img2 = xGetElementById("img2" + tipo + id);
	}
	var ajax = nuevoAjax();
	var url = obtenerContextPath() + "/comun/buzon/MarcarFavorito.action";
	ajax.open("POST", url, true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("id=" + id + "&carpeta=" + toHex(carpeta) + "&tipo=" + tipo);
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			var favorito = ajax.responseText;
			if (favorito == "1") {
				img1.src = obtenerContextPath() + "/img/icon_importante_y.gif";
				img1.alt = "Dejar de ser Favorito";
				if (abierto === 1) {
					img2.src = obtenerContextPath() + "/img/icon_importante_y.gif";
					img2.alt = "Dejar de ser Favorito";
				}
				mensajeG.favoritoM = 1;
				mensaje.favorito = 1;
				var trTMP = null;
				if (browser.indexOf("IE") > 1) {
					trTMP = xGetElementById("n_" + mensajeG.idM);
					if (trTMP == null) {
						trTMP = xGetElementById("l_" + mensajeG.idM);
					}
				} else {
					trTMP = xGetElementById("l_" + mensajeG.idM);
					if (trTMP == null) {
						trTMP = xGetElementById("n_" + mensajeG.idM);
					}
				}
				var cb = trTMP.getElementsByTagName("input")[0];
				var id = cb.getAttribute("id");
				cb.setAttribute("id", id.split("_")[0] + "_" + 1);
			} else {
				if (favorito == "0") {
					img1.src = obtenerContextPath() + "/img/icon_importante_n.gif";
					img1.alt = "Marcar como Favorito";
					if (abierto === 1) {
						img2.src = obtenerContextPath() + "/img/icon_importante_n.gif";
						img2.alt = "Marcar como Favorito";
					}
					mensajeG.favoritoM = 0;
					mensaje.favorito = 0;
					var trTMP = null;
					if (browser.indexOf("IE") > 1) {
						try {
							trTMP = xGetElementById("n_" + mensajeG.idM);
						}
						catch (e) {
							trTMP = xGetElementById("l_" + mensajeG.idM);
						}
					} else {
						try {
							trTMP = xGetElementById("l_" + mensajeG.idM);
						}
						catch (e) {
							trTMP = xGetElementById("n_" + mensajeG.idM);
						}
					}
					var cb = trTMP.getElementsByTagName("input")[0];
					var id = cb.getAttribute("id");
					cb.setAttribute("id", id.split("_")[0] + "_" + 0);
				}
			}
		}
	};
}
function iconoFavorito(favorito, id, carpeta, tipo, x) {
	if (favorito == "1") {
		return "<img id=\"img" + x + tipo + id + "\" src=\"" + obtenerContextPath() + "/img/icon_importante_y.gif\" alt=\"Dejar de ser Favorito\" title=\"Dejar de ser Favorito\" width=\"15\" height=\"15\" onClick=\"marcarFavorito(" + id + ",'" + carpeta + "','" + tipo + "','" + x + "')\"/>";
	} else {
		return "<img id=\"img" + x + tipo + id + "\" src=\"" + obtenerContextPath() + "/img/icon_importante_n.gif\"  title=\"Marcar como Favorito\" alt=\"Marcar como Favorito\" width=\"15\" height=\"15\" onClick=\"marcarFavorito(" + id + ",'" + carpeta + "','" + tipo + "','" + x + "')\"/>";
	}
}
function iconoAdjunto(adjunto, adjNom, idSiz, adjId, adjFec) {
	if (adjunto == "1") {
		return "<a href=\"" + obtenerContextPath() + "/comun/buzon/DescargarAdjunto.action?idAdjunto=" + adjId + "&archivoNombre=" + adjNom + "&fechaAd=" + adjFec + "\"><img id=\"img" + "" + "\" src=\"" + obtenerContextPath() + "/img/paperclip.gif\" title=\"" + adjNom + "\" alt=\"" + adjNom + "\" width=\"15\" height=\"15\" ></a>";
	} else {
		return "";
	}
}
function removeOp(id) {
	var op = opener.document.getElementById("opts");
	for (i = 0; i < op.length; i = i + 1) {
		if (op[i].value == id) {
			op[i] = null;
		}
	}
	var op = opener.document.getElementById("bam");
	for (i = 0; i < op.length; i = i + 1) {
		if (op[i].value == id) {
			op[i] = null;
		}
	}
}
var buzon = {};
buzon.complete = function(ele){
	var aBuscar = ele.value;
	var div = xGetElementById("coincidencias");
	if (trim(aBuscar).length > 2) {
		div.innerHTML = "";
		var ajax = nuevoAjax();
		var url = obtenerContextPath() + "/comun/buzon/BuscarContacto.action";
		ajax.open("POST", url, true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send("aBuscar=" + toHex(aBuscar));
		ajax.onreadystatechange = function () {
			if (ajax.readyState === 4) {
				var xml = ajax.responseXML;
				for (x = 0; x < xml.getElementsByTagName("coincidencia").length; x = x + 1) {
					var id = xml.getElementsByTagName("id")[x].childNodes[0].nodeValue;
					var nombre = xml.getElementsByTagName("nombreLargo")[x].childNodes[0].nodeValue;
					div.innerHTML += nombre+"<br>";
				}
			}
		};
	}else{
		div.innerHTML="";
	}
};
function resize() {
	var co = xGetElementById("co").offsetHeight;
	var nav = xGetElementById("nav").offsetHeight;
	var fB = xGetElementById("footerBlanks");
	fB.innerHTML = "";
	var beres = (nav - co) / 17;
	for (i = 0; i < beres; i = i + 1) {
		fB.innerHTML += "<br>";
	}
	if(xGetElementById('wysiwygcontenido')){
		xResizeTo('wysiwygcontenido',xClientWidth()-200,xClientHeight()-330);
	}
}
function xCreate(element) {
	return document.createElement(element);
}
