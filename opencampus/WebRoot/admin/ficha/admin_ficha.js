function clearDateInputs(){
	$('fechaInicio').value = '';
	$('fechaFin').value = '';
}

function clearPhaseInput(){
	$('idperiodo').selectedIndex = 0;
}

function loadSylabus(id){
	$('idSilabo').value =  0;
	var query = function(transport) {
		//alert(transport.responseText)
		
		var xml = transport.responseXML;
		
		$('sylabus').childElements().each(function(s){ s.parentNode.removeChild(s); });
		
		if(xml.getElementsByTagName("silabo").length > 0){ 
		
			$('idSilabo').value =  xml.getElementsByTagName("idsilabo")[0].childNodes[0].nodeValue;
		
			var oTable = xCreate('table');
			oTable.className="bor_tabla tabla_sin_layout";
			oTable.style.width="100%";
			oTable.cellSpacing="0";
			oTable.cellPadding="3";
			
			var oTbody = xCreate("tbody");
			
			//Encabezado
			var oCap = xCreate('caption');
			oCap.className = "fon_cab tit_cab";
			oCap.innerHTML = 'S&iacute;labo: '+xml.getElementsByTagName("descripcion")[0].childNodes[0].nodeValue;
			oTbody.appendChild(oCap);
			
			//SubEncabezado
			var oTR = xCreate('tr');
				
	        var oTD = xCreate('th');
	        oTD.className = "moduloAbajo";
	        oTD.width = '25px';
	        oTD.innerHTML = '&nbsp;';
	        oTR.appendChild(oTD);
	        
	        oTD = xCreate('th');
	        oTD.className = "moduloAbajo";
	        oTD.align = 'center';
	        oTD.width = '60px';
	        oTD.innerHTML = '<b>ID</b>';
	        oTR.appendChild(oTD);
	        
	        oTD = xCreate('th');
	        oTD.className = "moduloAbajo";
	        oTD.align = 'center';
	        oTD.innerHTML = '<b>Nombre</b>';
	        oTR.appendChild(oTD);
	        
	        oTD = xCreate('th');
	        oTD.className = "moduloAbajo";
	        oTD.align = 'center';
	        oTD.width = '130px';
	        oTD.innerHTML = '<b>Jerarquia</b>';
	        oTR.appendChild(oTD);
	        
	        oTD = xCreate('th');
	        oTD.className = "moduloAbajo";
	        oTD.width = '40px';
	        oTD.colSpan = '2';
	        oTD.innerHTML = '&nbsp;';
	        oTR.appendChild(oTD);
	        
	        oTD = xCreate('th');
	        oTD.className = "moduloAbajo";
	        oTD.width = '25px';
	        oTD.innerHTML = '&nbsp;';
	        oTR.appendChild(oTD);
	        
	        oTD = xCreate('th');
	        oTD.className = "moduloAbajo";
	        oTD.width = '25px';
	        oTD.innerHTML = '&nbsp;';
	        oTR.appendChild(oTD);
	        
	        oTD = xCreate('th');
	        oTD.className = "moduloAbajo1";
	        oTD.width = '25px';
	        oTD.innerHTML = '&nbsp;';
	        oTR.appendChild(oTD);
	        
	        oTbody.appendChild(oTR);
		
			for (x = 0; x < xml.getElementsByTagName("unidad").length; x = x + 1) {
				//Extraer valiables
				var unidad = xml.getElementsByTagName("unidad")[x];
				
				var codigo = unidad.getElementsByTagName("codigo")[0].childNodes[0].nodeValue;
				var nombre = unidad.getElementsByTagName("nombre")[0].childNodes[0].nodeValue;
				var jerarquia = unidad.getElementsByTagName("jerarquia")[0].childNodes[0].nodeValue;
				var test = unidad.getElementsByTagName("test")[0].childNodes[0].nodeValue;
				
				oTR = xCreate('tr');
				oTR.id = codigo;
				if(x%2==0)oTR.className = 'fon_color01';
				
		        oTD = xCreate('td');
		        oTD.className = "bor_der_cur";
		        oTD.align = 'center';
		        oTD.innerHTML = x+1;
		        oTR.appendChild(oTD);
		        
		        oTD = xCreate('td');
		        oTD.className = "bor_der_cur";
		        oTD.align = 'center';
		        oTD.innerHTML = codigo;
		        oTR.appendChild(oTD);
		        
		        oTD = xCreate('td');
		        oTD.className = "bor_der_cur";
		        oTD.innerHTML = nombre;
		        oTR.appendChild(oTD);
		        
		        oTD = xCreate('td');
		        oTD.className = "bor_der_cur";
		        oTD.align = 'center';
		        oTD.innerHTML = jerarquia;
		        oTR.appendChild(oTD);
		        
		        oTD = xCreate('td');
		        oTD.align = 'center';
		        oTD.innerHTML = '<img src="'+xGetContextPath()+'/img/icon_test.gif" alt="Test" />';
		        oTD.style.cursor = 'pointer';
		        oTD.onclick = function(){
		        	abrirTest(xGetContextPath()+'/aulavirtual/test/Listar.action?idUnidad='+this.parentNode.id +'&unidad='+this.parentNode.childNodes[2].innerHTML);
		    	};
		        oTR.appendChild(oTD);
		        
		        oTD = xCreate('td');
		        oTD.className = "bor_der_cur";
		        oTD.align = 'center';
		        oTD.innerHTML = '('+test+')';
		        oTR.appendChild(oTD);
		        
		        oTD = xCreate('td');
		        oTD.className = "bor_der_cur";
		        oTD.align = 'center';
		        oTD.innerHTML = '<img src="'+xGetContextPath()+'/img/icon_libro.gif" alt="Texto" />';
		        oTD.style.cursor = 'pointer';
				oTD.onclick = function(){
		        	abrirTexto(xGetContextPath()+'/admin/unidad/VerRecurso.action?ruta=1&id='+this.parentNode.id);
		    	};
		        oTR.appendChild(oTD);
		        
		        oTD = xCreate('td');
		        oTD.className = "bor_der_cur";
		        oTD.align = 'center';
		        oTD.innerHTML = '<img src="'+xGetContextPath()+'/img/icon_curso.gif" alt="Repaso" />';
		        oTD.style.cursor = 'pointer';
				oTD.onclick = function(){
		        	abrirRepaso(xGetContextPath()+'/admin/unidad/VerRecurso.action?ruta=3&id='+this.parentNode.id);
		    	};
		        oTR.appendChild(oTD);
		        
		        oTD = xCreate('td');
		        oTD.align = 'center';
		        oTD.innerHTML = '<img src="'+xGetContextPath()+'/img/icon_lab.gif" alt="Laboratorio" />';
		        oTD.style.cursor = 'pointer';
				oTD.onclick = function(){
		        	abrirLaboratorioPdf(xGetContextPath()+'/admin/unidad/VerRecurso.action?ruta=2&id='+this.parentNode.id);
		    	};
		        oTR.appendChild(oTD);
		        
		        oTbody.appendChild(oTR);
		        
			}
			oTable.appendChild(oTbody);
			$('sylabus').appendChild(oTable);
			
			var oButton = xCreate('button');
			oButton.value = 'Modificar Silabo';
			oButton.className = 'form_button';
			oButton.onclick = function(){
		        if(window.confirm("¿Desea cambiar la ventana actual?\nSe perderán los datos no guardados.")) {
					document.location = xGetContextPath() + "/admin/curso/Cargar.action?idCurso=" + id;
				}
		    };
			$('sylabus').appendChild(oButton);
			
		}else{
			$('sylabus').update('<center><b>No existe s&iacute;labo para el curso seleccionado.</b></center>');
		}
		
	}
	var url = xGetContextPath()+"/admin/silabo/VerSilaboXML.action";
	var params = "idCurso="+id;
	new Ajax.Request(url, { method: 'post', parameters: params, onSuccess: query }); 
}

function validar(form){
	if(form.idCurso == 0){
		alert('Aún no ha seleccionado un curso válido.');
		return false;
	}
	if(form.idSilabo == 0){
		alert('El curso seleccionado no presenta sílabo.');
		return false;
	}
	if(form.idPeriodo.selectedIndex == 0 && (form.fechaInicio.value == '' || form.fechaInicio.value == '')){
		alert('Debe indicar el rango de fechas para la ejecución del curso.');
		return false;
	}
	return true;
}
