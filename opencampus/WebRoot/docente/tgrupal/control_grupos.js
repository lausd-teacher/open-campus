var grupos = new Array();
var estudiantes = new Array();
// estudiante: value y text
// grupo: name y array
var grouplimit = 20;
function cargar(){
	// Cargar estudiantes
	listarEstudiantes();
	
	//Cargar grupos
	var theSel = xGetElementById("SelectGrupos");
	var length = grupos.length;
	for (i = 0; i<length; i = i + 1) {
		addOption(theSel, ""+grupos[i].name, grupos[i].id);
	}
	setInterval("displayIntegrantes()",500);
}

function formarAutomaticamente(){
	
	var theSelGroup = xGetElementById("SelectGrupos");
	if(theSelGroup.length>0){
		if(!confirm('¿Seguro que desea eliminar a todos los grupos actuales y formar otros?')){
			return false;
		}
	}
	
	eliminarGrupo(true);
	
	var theSelStudent = xGetElementById("SelectEstudiantes");
	var totalStudents = theSelStudent.length;
	var totalGroups = '';
  	while(totalGroups.isEmpty() || !totalGroups.isInteger() || totalGroups == 0 || totalGroups>totalStudents){ //usa ==    ó   parseFloat(totalGroups)
		totalGroups = prompt("Indique la cantidad de grupos que desea formar.", "");
		if(!totalGroups){return false;}
		if(totalGroups.isEmpty()){ alert('Debe ingresar un valor numérico entre 1 a '+totalStudents+' en la caja de texto.');}
		if(!totalGroups.isInteger() || totalGroups == 0 || totalGroups>totalStudents) {  alert('Debe ingresar un valor numérico entre 1 a '+totalStudents+' en la caja de texto.');}
  	}
  	
	// Crear Grupos***************************
	
	for(var g=1;g<=totalGroups;g++){
	
		var name = 'Grupo '+g;
		//---de crear Grupo
		if(grupos.length < grouplimit){ //lO CHECKAS DESPUES NO PUEDES CREAR MAS DE 20 GRUPOS , NO SE POR QUE
			
					//*******************************************/
					crearGrupoBD(name);
					//*******************************************/
					
					alert("Se ha creado el grupo: "+name)
					//while(grupos.length<g){ //El while congela por completo ni siquiera se ejecuta el onreadystatechange, busca otra forma de hacer listeners o crea otro crarGrupoBD o haz como asignarGrupoDB(el addoption esta fuera del onreadystatechage)
					//	if(!grupos.length<g){break;}
					//}
					
					//OJO: cuando cargas la interface y tienes grupos creados no se muestra la cantidad al pie del select
				
		}else{
			xAlert("mensaje2","Cantidad límite de grupos","error");	//
		}
		
		//----
	}
	//*************************
	
	// Asignar Estudiantes ***************************
	
	var theSel = xGetElementById("SelectEstudiantes");
	var theSelGroup = xGetElementById("SelectGrupos");
	var theSelMember = xGetElementById("SelectIntegrantes");
	var intg = theSel.length-1;
	
	
			while (0<=intg) {
			
				for(var gt=0; gt<theSelGroup.length;gt++){
					
					if(intg === -1){break;}
					
					//*******************************************/
					//alert("11111")
					 //alert(intg);
					asignarGrupoBD(intg, gt); //agrega a bd
					//*******************************************/
					//alert("2222")
					moveStudent(intg, gt);//mueve de estudiantes a integrantes por grupo
					//alert("3333")
					deleteOption(theSel, intg); // elimina del grupo estudiantes puedes popnerlo dentro de moveStudent()
					intg = intg - 1;
				}
					
			}
			
		
	//listarIntegrantes();
	// *****************************************
	
	
}

function calcularFactores(num){
	var array = new Array();
	if(num && num.isInteger()){
		var num = Math.round(eval(num));
		for (i = 2; i < (num / 2); i++) {
			var chkd = Math.round(num / i);
			var inn = Math.round(num / i);
			var outt = (num / i);
			if (inn == outt && chkd > i) {
				array.push(num/i);
				array.push(i);
	      	}
	   }
	   array.sort();
	}
	
	return array;
}

var modo = '';
function guardarCambios(){
	if(modo === 'new'){
		crearGrupo(true);
	}else{
		renombrarGrupo(true);
	}
}

function displayIntegrantes(){
	var theSel = xGetElementById("SelectGrupos");
	var tableInt = xGetElementById("table_integrantes");
	if(theSel.selectedIndex != -1){
		//xShow("Integrantes");
		tableInt.className='tabla01';
	}else{
		//xHide("Integrantes");
		tableInt.className='tabla01_out';
		var theSelMember = xGetElementById("SelectIntegrantes");
		var selLength = theSelMember.length;
		for (i = selLength-1; 0<=i; i = i - 1) {
			deleteOption(theSelMember, i);
		}
		xAlert("mensaje3","Seleccione un grupo.");
	}
}

function crearGrupo(save){
	var rename = xGetElementById("name_txt");
	if(!save){
		rename.value = 'Ingrese un nombre';
		xShowD("gForm");
		rename.focus();
		this.modo='new';
	}else{
		var name = rename.value.trim();
		if(grupos.length < grouplimit){
			if(validarCaracteres(name)){
				if(compararNombreGrupo(name)){
					//*******************************************/
					crearGrupoBD(name);
					//*******************************************/
					xAlert("mensaje2","Total: "+grupos.length+" grupo(s)");
					xHideD("gForm");
				}else{
					xAlert("mensaje2","Nombre de grupo en uso","error");
				}
			}
			else{
				xAlert("mensaje2","Caracter no permitido.","error");
			}
		}else{
			xAlert("mensaje2","Cantidad límite de grupos","error");
		}
	}
}

function eliminarGrupo(isAll){
	var theSel = xGetElementById("SelectGrupos");
	if(theSel.selectedIndex != -1 || isAll){
		var selLength = theSel.length;
		for (i = selLength-1; 0<=i; i = i - 1) {
			if (theSel.options[i].selected || isAll) {
				if(hasMemberToDelete(i)){
					//*******************************************/
					eliminarGrupoBD(i);
					//*******************************************/
					deleteGroup(i);
					deleteOption(theSel, i);
				}
			}
		}
		listarEstudiantes();
		xAlert("mensaje2","Total: "+theSel.length+" grupo(s)");
	}else{
		xAlert("mensaje2","Seleccione un grupo.","error");
	}
}

function hasMemberToDelete(index){
	var memberLength = grupos[index].array.length;
	if(memberLength > 0){
		return window.confirm("Realmente quiere eliminar este grupo de "+memberLength+" integrantes\nllamado \""+grupos[index].name+"\"  ?");
	}
	return true;
}

function compararNombreGrupo(name){
	var groupsLength = grupos.length;
	for (i =0; i<groupsLength; i = i + 1) {
		if (grupos[i].name == name) {
			return false;
		}
	}
	return true;
}
var flag=true;
function renombrarGrupo(save){
	var theSelGroup = xGetElementById("SelectGrupos");
	if(theSelGroup.selectedIndex != -1){
		var rename = xGetElementById("name_txt");
		if(!save){
			rename.value = grupos[theSelGroup.selectedIndex].name;
			xShowD("gForm");
			rename.focus();
			rename.select();
			this.modo='update';
		}else{
			var name = rename.value.trim();
			if(validarCaracteres(name)){
				if(compararNombreGrupo(name)){
					//*******************************************/
					renombrarGrupoBD(grupos[theSelGroup.selectedIndex].id, rename.value);
					//*******************************************/
					grupos[theSelGroup.selectedIndex].name = rename.value;
					theSelGroup.options[theSelGroup.selectedIndex].text = ""+rename.value;
					xAlert("mensaje2","Total: "+theSelGroup.length+" grupo(s)");
					xHideD("gForm");
				}else{
					xAlert("mensaje2","Nombre de grupo en uso","error");
				}
			}else{
				xAlert("mensaje2","Caracter no permitido.","error");
			}
		}
	}else{
		xAlert("mensaje2","Seleccione un grupo.","error");
	}
}

function addGroup(name,idGrupo){
	var grupo = new Object();
	grupo.id = idGrupo;
	grupo.name = name;
	grupo.array = new Array();
	grupos.push(grupo);
}

function deleteGroup(theIndex){
	var memberLength = grupos[theIndex].array.length;
	if(memberLength > 0){
		var theSelMember = xGetElementById("SelectIntegrantes");
		for (a = memberLength-1; 0<=a; a = a - 1) {
			returnStudent(a,theIndex);
			deleteOption(theSelMember, a);
		}
	}
	grupos.splice(theIndex,1);
}

function addOption(theSel, theText, theValue) {
	var newOpt = new Option(theText, theValue);
	var selLength = theSel.length;
	theSel.options[selLength] = newOpt;
}

function deleteOption(theSel, theIndex) {
	var selLength = theSel.length;
	if (selLength > 0) {
		theSel.options[theIndex] = null;
	}
}

//*********************************************//

function  listarIntegrantes(){
	//limpiar SelectIntegrantes
	var theSelMember = xGetElementById("SelectIntegrantes");
	var selLength = theSelMember.length;
	for (i = selLength-1; 0<=i; i = i - 1) {
		deleteOption(theSelMember, i);
	}
	//Listar SelectIntegrantes
	var theSelGroup = xGetElementById("SelectGrupos");
	var grupo = grupos[theSelGroup.selectedIndex];
	for (i = 0; i < grupo.array.length; i = i + 1) {
		addOption(theSelMember, grupo.array[i].text, grupo.array[i].value);
	}
	var theSelStudent = xGetElementById("SelectEstudiantes");
	xAlert("mensaje1","Total: "+theSelStudent.length+" Estudiante(s)");
	xAlert("mensaje3","Total: "+theSelMember.length+" Integrante(s)");
}

function asignar(){
	var theSel = xGetElementById("SelectEstudiantes");
	var theSelGroup = xGetElementById("SelectGrupos");
	var theSelMember = xGetElementById("SelectIntegrantes");
	var selLength = theSel.length;
	if(theSelGroup.selectedIndex != -1){
		if(theSel.selectedIndex != -1){
			for (i = selLength-1; 0<=i; i = i - 1) {
				if (theSel.options[i].selected) {
					//*******************************************/
					asignarGrupoBD(i, theSelGroup.selectedIndex);
					//*******************************************/
					moveStudent(i, theSelGroup.selectedIndex);
					deleteOption(theSel, i);
				}
			}
			listarIntegrantes();
		}else{
			xAlert("mensaje1","Seleccione un estudiante","error");
		}
	}else{
		xAlert("mensaje1","Seleccione un grupo","error");
	}
}

function desasignar(){
	var theSel = xGetElementById("SelectEstudiantes");
	var theSelGroup = xGetElementById("SelectGrupos");
	var theSelMember = xGetElementById("SelectIntegrantes");
	var selLength = theSelMember.length;
	if(theSelGroup.selectedIndex != -1){
		if(theSelMember.selectedIndex != -1){
			for (i = selLength-1; 0<=i; i = i - 1) {
				if (theSelMember.options[i].selected) {
					//*******************************************/
					desasignarGrupoBD(i, theSelGroup.selectedIndex);
					//*******************************************/
					returnStudent(i, theSelGroup.selectedIndex);
					deleteOption(theSelMember, i);
				}
			}
			listarEstudiantes();
		}else{
			xAlert("mensaje3","Seleccione un integrante","error");
		}
	}else{
		xAlert("mensaje3","Seleccione un grupo","error");
	}
}

function moveStudent(theStudentIndex, theGroupIndex){
	var grupo = grupos[theGroupIndex];
	grupo.array.push(estudiantes[theStudentIndex]);
	estudiantes.splice(theStudentIndex,1);
}

function returnStudent(theIntegrantIndex, theGroupIndex){
	var grupo = grupos[theGroupIndex];
	estudiantes.push(grupo.array[theIntegrantIndex]);
	grupo.array.splice(theIntegrantIndex,1);
}

function  listarEstudiantes(){
	//limpiar SelectEstudiantes
	var theSelStudent = xGetElementById("SelectEstudiantes");
	var selLength = theSelStudent.length;
	for (i = selLength-1; 0<=i; i = i - 1) {
		deleteOption(theSelStudent, i);
	}
	//Listar SelectEstudiantes
	for (i = 0; i < estudiantes.length; i = i + 1) {
		addOption(theSelStudent, estudiantes[i].text, estudiantes[i].value);
	}
	var theSelMember = xGetElementById("SelectIntegrantes");
	xAlert("mensaje1","Total: "+theSelStudent.length+" Estudiante(s)");
	xAlert("mensaje3","Total: "+theSelMember.length+" Integrante(s)");
}

//*********************************************//

function xAlert(div,msg,type){
	var mensaje = xGetElementById(div);
	if(type=="error")
		mensaje.style.color =  "#ff3333";
	else
		mensaje.style.color =  "#000000";
	if(mensaje)
		mensaje.innerHTML=msg;
}

function validarCaracteres(texto){
	var reg=/(^[a-zA-Z0-9._@():$?¿¡!\-$ñáéíóúÑÁÉÍÓÚ ]{2,100}$)/;
	if(!reg.test(texto)){
		return false;
	}
	return true;
}

//******************** AJAX *********************//

function crearGrupoBD(nombre){

    //Entrada
	var cadena = "descripcion="+escape(nombre);	
	//Salida
	
	ajax = nuevoAjax();
	
	ajax.open("POST", xGetContextPath()+"/aulavirtual/tgrupal/CrearGrupo.action", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send(cadena);
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			if(ajax.responseText != ""){
				var idGrupo = parseInt(ajax.responseText);
				
				var theSel = xGetElementById("SelectGrupos");
				addGroup(nombre,idGrupo);
				addOption(theSel, ""+nombre, idGrupo);
				xAlert("mensaje2","Total: "+theSel.length+" grupo(s)");
				
			}else{
				alert("Error, no fue posible crear el grupo.");
				window.location.reload();
			}
		}
	}

}

function asignarGrupoBD(studentIndex, groupIndex){
    //Entrada
    var idGrupo = grupos[groupIndex].id;
    var idEstudiante = estudiantes[studentIndex].value;
    //alert(idGrupo +"-" +idEstudiante);
	var cadena = "idGrupo="+idGrupo+"&idMatricula="+idEstudiante;	
	//Salida
	
	ajax = nuevoAjax();
	
	ajax.open("POST", xGetContextPath()+"/aulavirtual/tgrupal/AsignarGrupo.action", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send(cadena);
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			if(ajax.responseText == "OK"){
				
			}else{
				alert("Error, no fue posible asignar al grupo.");
				window.location.reload();
			}
		}
	}
}

function desasignarGrupoBD(studentIndex, groupIndex){

    //Entrada
    var grupo = grupos[groupIndex];
    var idGrupo = grupo.id;
    var idIntegrante = grupo.array[studentIndex].value;
    //alert(idGrupo +"-" +idEstudiante);
	var cadena = "idGrupo="+idGrupo+"&idMatricula="+idIntegrante;	
	//Salida
	
	ajax = nuevoAjax();
	
	ajax.open("POST", xGetContextPath()+"/aulavirtual/tgrupal/DesasignarGrupo.action", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send(cadena);
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			if(ajax.responseText == "OK"){
				
			}else{
				alert("Error, no fue posible desasignar al grupo.");
				window.location.reload();
			}
		}
	}
}

function renombrarGrupoBD(idGrupo, nombre){

    //Entrada
    var cadena = "idGrupo="+idGrupo+"&descripcion="+nombre;	
	//Salida
	
	ajax = nuevoAjax();
	
	ajax.open("POST", xGetContextPath()+"/aulavirtual/tgrupal/RenombrarGrupo.action", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send(cadena);
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			if(ajax.responseText == "OK"){
				
			}else{
				alert("Error, no fue posible renombrar el grupo.");
				window.location.reload();
			}
		}
	}
}

function eliminarGrupoBD(groupIndex){

    //Entrada
    var idGrupo = grupos[groupIndex].id;
    var cadena = "idGrupo="+idGrupo;	
	//Salida
	
	ajax = nuevoAjax();
	
	ajax.open("POST", xGetContextPath()+"/aulavirtual/tgrupal/EliminarGrupo.action", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send(cadena);
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			if(ajax.responseText == "OK"){
				
			}else{
				alert("Error, no fue posible eliminar el grupo.");
				window.location.reload();
			}
		}
	}
}