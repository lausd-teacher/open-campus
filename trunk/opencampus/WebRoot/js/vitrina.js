var enablepersist="off" //Habilita la grabacion del estado de la estructura del contenido usando los cookie de la sesion (on/off)
var collapseprevious="no" //Cierra la capas previamente abierta (yes/no)

var contractsymbol='- ' //Simbolo de expandido. Se puede colocar imagenes <img src="xxx.gif">
var expandsymbol='+ ' //El simbolo de contraido

if (document.getElementById){
	document.write('<style type="text/css">')
	document.write('.switchcontent{display:none;}')
	document.write('</style>')
}

function getElementbyClass(classname){
	ccollect=new Array()
	var inc=0
	var alltags=document.all? document.all : document.getElementsByTagName("*")
	for (i=0; i<alltags.length; i++){
		if (alltags[i].className==classname)
			ccollect[inc++]=alltags[i]
	}
}

function contractcontent(omit){
	var inc=0
	while (ccollect[inc]){
		if (ccollect[inc].id!=omit)
			ccollect[inc].style.display="none"
		inc++
	}
}

function expandcontent(cid){
	if (typeof ccollect!="undefined"){
		if (collapseprevious=="yes")
			contractcontent(cid)
		document.getElementById(cid).style.display=(document.getElementById(cid).style.display!="block")? "block" : "none"
	}
}

function expandcontent2(cid,cid2){
	if (typeof ccollect!="undefined"){
		document.getElementById(cid).style.display=(document.getElementById(cid).style.display=="none")? "inline" : "none"
		document.getElementById(cid2).style.display=(document.getElementById(cid2).style.display=="inline")? "none" : "inline"
	}
}

function revivecontent(){
	contractcontent("omitnothing")
	selectedItem=getselectedItem()
	selectedComponents=selectedItem.split("|")
	for (i=0; i<selectedComponents.length-1; i++)
		document.getElementById(selectedComponents[i]).style.display="block"
}

function get_cookie(Name) { 
	var search = Name + "="
	var returnvalue = "";
	if (document.cookie.length > 0) {
		offset = document.cookie.indexOf(search)
		if (offset != -1) { 
			offset += search.length
			end = document.cookie.indexOf(";", offset);
			if (end == -1) end = document.cookie.length;
				returnvalue=unescape(document.cookie.substring(offset, end))
		}
	}
	return returnvalue;
}

function getselectedItem(){
	if (get_cookie(window.location.pathname) != ""){
		selectedItem=get_cookie(window.location.pathname)
		return selectedItem
	}
	else
		return ""
}

function saveswitchstate(){
	var inc=0, selectedItem=""
	while (ccollect[inc]){
		if (ccollect[inc].style.display=="block")
			selectedItem+=ccollect[inc].id+"|"
		inc++
	}
	
	document.cookie=window.location.pathname+"="+selectedItem
}

function do_onload(){
	uniqueidn=window.location.pathname+"firsttimeload"
	getElementbyClass("switchcontent")
	if (enablepersist=="on" && typeof ccollect!="undefined"){
		document.cookie=(get_cookie(uniqueidn)=="")? uniqueidn+"=1" : uniqueidn+"=0" 
		firsttimeload=(get_cookie(uniqueidn)==1)? 1 : 0 //check if this is 1st page load
		if (!firsttimeload)
			revivecontent()
	}
}
function confirmar(){
		return confirm("¿Está seguro que desea eliminar el mensaje?");
}

if (window.addEventListener)
window.addEventListener("load", do_onload, false)
else if (window.attachEvent)
window.attachEvent("onload", do_onload)
else if (document.getElementById)
window.onload=do_onload

if (enablepersist=="on" && document.getElementById)
window.onunload=saveswitchstate