function seleccionNoticia(Input, valor) {
	if (valor) {
		Input.style.background = "#FFFBE8";
		Input.style.borderColor = "#EFDA45";
	} else {
		Input.style.background = "#FFFFFF";
		Input.style.borderColor = "#AAABAF";
	}
}

function verNoticiaRSS(url){
	var eName = 'pUp_Noticia_Demo';
	var width = 770;
	var height = screen.height-200;
	var x = (screen.width-width)/2;
	var y = (screen.height-height)/2;
	
	var pUpNoticiaRSS = window.open(url, "Detalle", "width=" + width + ",height=" + height + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + y + ",left=" + x + "");
	pUpNoticiaRSS.focus();
}

function verNoticia(idnoticia){
	var url = xGetContextPath()+'/noticia/VerNoticia.action?idnoticia='+idnoticia;
	
	
	//InnerDiv ***
	/*var title = 'Noticia';
	var eName = 'pUp_Noticia_Demo';
	var width = xClientWidth()-20;
	var height = xClientHeight()-20;
	width = (width>700)?700:width;
	height = (height>600)?600:height;
	var x = (xClientWidth()-width)/2;
	var y = (xClientHeight()-height)/2 + xScrollTop();
	if(y<0){
		y=0;
	}
	
	//Efecto ***
	xMoveTo('blocker', xScrollLeft(),xScrollTop()-10);
	xResizeTo('blocker',xClientWidth(),xClientHeight()+10);
	Effect.Appear('blocker', {from: 0.0, to: 0.2 });
	
	if(xGetElementById(eName)){
		INNERDIV.cargar(eName,url,'',true,false);
	}else{
		INNERDIV.newInnerDiv(eName,x,y,width,height,url,title,null,true,null,enCerrarNoticia);
	}*/
	// ***** DIV ********************
	/*var myConn = new XHConn();
	var query = function (oXML) { 
			if (oXML.readyState === 4) {
				xInnerHtml('noticia',oXML.responseText);
				xShowD('noticia');
			}
		};
		
	myConn.connect(url, "POST", null, query);
	xHideD('portada');
	xShowD('back');
	xHideD('back2_1');
	xShowD('back2_2');*/
	// ***** POPUP  ****************
	
	/*var width = 750;
	var height = 600;
	x = (screen.availWidth - width) / 2;
	y = (screen.availHeight - height) / 2;
	var pUpNoticiaDemo = window.open(url, "Detalle", "width=" + width + ",height=" + height + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + y + ",left=" + x + "");
	pUpNoticiaDemo.focus();*/
	
	//******** LOCATION ******************
	window.location = url;
	
}

function enCerrarNoticia(){
	xHideD('blocker');
}

function regresarPortada(){
	xHideD('noticia');
	xHideD('back');
	xShowD('portada');
	xShowD('back2_1');
	xHideD('back2_2');
}

function verSeccion(id, total) {
 	for(var index=1; index<=total; index++) {
 		xHideD('divSeccion_'+index);
 		xGetElementById('tab_'+index).className = 'etiqueta_inact_centro';
 		if(index==1){
 			xGetElementById('tabl_'+index).className = 'etiqueta_inact_iz_ult';
 		}else{
 			xGetElementById('tabl_'+index).className = 'etiqueta_inact_de';
 		}
 	}
 	xGetElementById('tabd_'+total).className = 'etiqueta_inact_de_ult';
 	
 	if(id==1){
 		xGetElementById('tabl_'+id).className = 'etiqueta_act_iz_afuera';
 	}else{
 		xGetElementById('tabl_'+id).className = 'etiqueta_act_iz';
 	}
 	
 	if(id==total){
 		xGetElementById('tabd_'+id).className = 'etiqueta_act_de_afuera';
 	}else{
 		xGetElementById('tabl_'+ (parseInt(id)+1)).className = 'etiqueta_act_de';
 	}
 	
 	xGetElementById('tab_'+id).className = 'etiqueta_act_centro';
 	xShowD('divSeccion_'+id);
}

function resize() {
	xResizeTo('blocker',xClientWidth(),xClientHeight()+10);
	xHeight('noticia',xClientHeight()-100);
}

function scrolling() {
	xMoveTo('blocker', xScrollLeft(),xScrollTop()-10);
}