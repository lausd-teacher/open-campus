hexadecimal = new Array("0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F") ;
	function convierteHexadecimal(num){ 
	    var hexaDec = Math.floor(num/16) 
	    var hexaUni = num - (hexaDec * 16) 
	    return hexadecimal[hexaDec] + hexadecimal[hexaUni] 
	} 
	
	color_dec_rojo = 0 ;
	color_dec_verde = 0 ;
	color_dec_azul = 0 ;
	factor = 1;
	
	function degradado(){ 
		try{
			if (color_dec_verde == 180)
		    	factor = -1 
		    else if(color_dec_verde == 0)
		    	factor = +1
		
		    color_dec_rojo = 255
		    color_dec_verde += factor 
			color_dec_azul = 0 
		
		    color_hex_rojo = convierteHexadecimal(color_dec_rojo)
		    color_hex_verde = convierteHexadecimal(color_dec_verde) 
		    color_hex_azul = convierteHexadecimal(color_dec_azul);
		    var avisoback = document.getElementById('avisoback');
		    avisoback.style.backgroundColor = color_hex_rojo + color_hex_verde + color_hex_azul;
		    avisoback.setAttribute('style','background-color: #'+color_hex_rojo + color_hex_verde + color_hex_azul+';');
		    setTimeout("degradado()",10) 
	    }catch(e){alert(e)}
	} 
	
	function cargarAviso(){
		var myConn = new XHConn();
		var query = function (oXML) { 
				if (oXML.readyState === 4 && oXML.status == 200) {
					var xml = oXML.responseXML;
					try{
						xHideD('avisoback');
						if(oXML.responseText !== "" && xml.getElementsByTagName("aviso").length !== 0){
							
							var titular = xml.getElementsByTagName("titular")[0].childNodes[0].nodeValue;
							var tipo = xml.getElementsByTagName("tipo")[0].childNodes[0].nodeValue;
							
							if(tipo == 0){			
								xShowD('avisoback');			
								xInnerHtml('aviso',titular);
							}else if(tipo == 1){
								alert(titular);
							}
												
						}else{
						}
					}catch(e){
						xInnerHtml('avisoback','');
						xHideD('avisoback');
						var avisoback = document.getElementById('avisoback');
						avisoback.className = '';
					}
				}
			};
		myConn.connect(xGetContextPath()+"/aviso/CargarAviso.action", "POST", null, query);
		//setTimeout("cargarAviso()",60000); 
	}
	
	function initAviso(){
		degradado() 
		cargarAviso();
	}