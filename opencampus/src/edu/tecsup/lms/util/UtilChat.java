package edu.tecsup.lms.util;

import java.util.Collection;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;

public class UtilChat extends org.directwebremoting.proxy.dwr.Util{
	
	public UtilChat(){
		super();
	}
	
	public UtilChat(ScriptSession scriptSession) {
        super(scriptSession);
    }

	public UtilChat(Collection<ScriptSession> scriptSessions) {
        super(scriptSessions);
    }
	
    /**
     * 
     * @param ella
     */
	public void putFocus(String ella) {		
		ScriptBuffer script = new ScriptBuffer();
		script.appendScript("dwr.util.byId(")
			.appendData(ella)
			.appendScript(").focus();");
		addScript(script);
	}
		
	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title){
		ScriptBuffer script = new ScriptBuffer();		
		//script.appendScript("parent.document.title = ").appendData(title);
		script.appendScript("setTitle(").appendData(title).appendScript(");");
		addScript(script);
	}
		
	public void creditos(){
		alert("");
	}
	
	/**
	 * 
	 * @param which
	 */
	public void scrollAtBottom(String which){
		ScriptBuffer script = new ScriptBuffer();
		script.appendScript("var fuckingDiv = dwr.util.byId(")
			.appendData(which)
			.appendScript(");")
			.appendScript("fuckingDiv.scrollTop = fuckingDiv.scrollHeight;");
		addScript(script);
	}

	/**
	 * 
	 * @param string
	 */
	public void alert(String string) {		
		ScriptBuffer script = new ScriptBuffer();
		script.appendScript("alert(").appendData(string).appendScript(");");
		addScript(script);
	}
	
	/**
	 * 
	 * @param elementId
	 * @param idSuffix
	 * @param child
	 * @param value
	 */
    public void smartClone(String elementId, String idSuffix, String child, String value){
    	ScriptBuffer script = new ScriptBuffer();
    	script.appendScript("if(!$(")
    		  .appendData(elementId+idSuffix)
    		  .appendScript(")){dwr.util.cloneNode(")
              .appendData(elementId)
              .appendScript(", { idPrefix:''")              
              .appendScript(", idSuffix:")
              .appendData(idSuffix)
              .appendScript("});")
              .appendScript("dwr.util.setValue(")
              .appendData(child+idSuffix)
              .appendScript(",")
              .appendData(value)
              .appendScript(")}");
    	addScript(script);
    }
    
    /**
     * 
     * @param elementId
     * @param idSuffix
     * @param child
     * @param value
     */
    public void smartCloneRandomPosition(String elementId, String idSuffix, String child, String value){
    	ScriptBuffer script = new ScriptBuffer();
    	script.appendScript("if(!$(")
    		  .appendData(elementId+idSuffix)
    		  .appendScript(")){dwr.util.cloneNode(")
              .appendData(elementId)
              .appendScript(", { idPrefix:''")
              .appendScript(", idSuffix:")
              .appendData(idSuffix)
              .appendScript("});")
              .appendScript("dwr.util.setValue(")
              .appendData(child+idSuffix)
              .appendScript(",")
              .appendData(value)
              .appendScript(");")              
              .appendScript("}");    	
    	addScript(script);
    }
           
    /**
     * 
     * @param elementId
     * @param idSuffix
     * @param child
     * @param value
     * @param child2
     * @param value2
     * @param child3
     * @param value3
     */
    public void smartClone(String elementId, String idSuffix, String child, String value, String child2, String value2, String child3, String value3){
    	ScriptBuffer script = new ScriptBuffer();
    	script.appendScript("if(!$(")
    		  .appendData(elementId+idSuffix)
    		  .appendScript(")){dwr.util.cloneNode(")
              .appendData(elementId)
              .appendScript(", { idPrefix:''")              
              .appendScript(", idSuffix:")
              .appendData(idSuffix)
              .appendScript("});")
              
              .appendScript("dwr.util.setValue(")
              .appendData(child+idSuffix)
              .appendScript(",")
              .appendData(value)
              .appendScript(");")
              
              .appendScript("dwr.util.setValue(")
              .appendData(child2+idSuffix)
              .appendScript(",")
              .appendData(value2)
              .appendScript(");")
              
              .appendScript("dwr.util.setValue(")
              .appendData(child3+idSuffix)
              .appendScript(",")
              .appendData(value3)
              .appendScript(");")
              
              .appendScript("}");
    	addScript(script);
    }
    
    /**
     * 
     * @param elementId
     * @param idSuffix
     * @param child
     * @param value
     * @param child2
     * @param value2
     * @param child3
     * @param value3
     */
    public void cloneNode(String elementId, String idSuffix, String child, String value, String child2, String value2, String child3, String value3){
    	ScriptBuffer script = new ScriptBuffer();
    	script.appendScript("dwr.util.cloneNode(")
              .appendData(elementId)
              .appendScript(", { idPrefix:''")              
              .appendScript(", idSuffix:")
              .appendData(idSuffix)
              .appendScript("});")
              
              .appendScript("dwr.util.setValue(")
              .appendData(child+idSuffix)
              .appendScript(",")
              .appendData(value)
              .appendScript(");")
              
              .appendScript("dwr.util.setValue(")
              .appendData(child2+idSuffix)
              .appendScript(",")
              .appendData(value2)
              .appendScript(");")
              
              .appendScript("dwr.util.setValue(")
              .appendData(child3+idSuffix)
              .appendScript(",")
              .appendData(value3)
              .appendScript(");")
              
              .appendScript("");
    	addScript(script);
    }
    
    /**
     * 
     * @param elementId
     * @param idSuffix
     * @param child
     * @param value
     * @param child2
     * @param value2
     * @param child3
     * @param value3
     * @param child4
     * @param value4
     */
    public void smartClone(String elementId, String idSuffix, String child, String value, String child2, String value2, String child3, String value3, String child4, String value4){
    	ScriptBuffer script = new ScriptBuffer();
    	script.appendScript("try{if(!$(")
    		  .appendData(elementId+idSuffix)
    		  .appendScript(")){dwr.util.cloneNode(")
              .appendData(elementId)
              .appendScript(", { idPrefix:''")              
              .appendScript(", idSuffix:")
              .appendData(idSuffix)
              .appendScript("});")
              
              .appendScript("dwr.util.setValue(")
              .appendData(child+idSuffix)
              .appendScript(",")
              .appendData(value)
              .appendScript(");")
              
              .appendScript("dwr.util.setValue(")
              .appendData(child2+idSuffix)
              .appendScript(",")
              .appendData(value2)
              .appendScript(");")
              
              .appendScript("dwr.util.setValue(")
              .appendData(child3+idSuffix)
              .appendScript(",")
              .appendData(value3)
              .appendScript(");")
              
              .appendScript("dwr.util.setValue(")
              .appendData(child4+idSuffix)
              .appendScript(",")
              .appendData(value4)
              .appendScript(");")              
                            
              .appendScript("contacto = {id:")
              .appendData(idSuffix)
              .appendScript(", nombre:")
              .appendData(value)
              .appendScript("};")
              .appendScript("contactos.push(contacto);")
              
              .appendScript("}}catch(e){}");
    	addScript(script);
    }
    
    /**
     * 
     * @param elementId
     * @param idSuffix
     * @param child
     * @param value
     * @param child2
     * @param value2
     */
    public void smartClone(String elementId, String idSuffix, String child, String value, String child2, String value2){
    	ScriptBuffer script = new ScriptBuffer();
    	script.appendScript("if(!$(")
    		  .appendData(elementId+idSuffix)
    		  .appendScript(")){dwr.util.cloneNode(")
              .appendData(elementId)
              .appendScript(", { idPrefix:''")              
              .appendScript(", idSuffix:")
              .appendData(idSuffix)
              .appendScript("});")
              
              .appendScript("dwr.util.setValue(")
              .appendData(child+idSuffix)
              .appendScript(",")
              .appendData(value)
              .appendScript(");")
              
              .appendScript("dwr.util.setValue(")
              .appendData(child2+idSuffix)
              .appendScript(",")
              .appendData(value2)
              .appendScript(");")
              
              .appendScript("}");
    	addScript(script);
    }
    
    /**
     * 
     * @param elementId
     * @param idSuffix
     */
    public void smartClone(String elementId, String idSuffix){    	
    	ScriptBuffer script = new ScriptBuffer();
    	script.appendScript("if(!$(")
    		  .appendData(elementId+idSuffix)
    		  .appendScript(")){dwr.util.cloneNode(")
              .appendData(elementId)
              .appendScript(", { idPrefix:''")              
              .appendScript(", idSuffix:")
              .appendData(idSuffix)
              .appendScript("});}");
    	addScript(script);
    }
    
    /**
     * 
     * @param elementId
     * @param idSuffix
     * @param child
     * @param value
     */
    public void smartInsert(String elementId, String idSuffix, String child, String value){
    	ScriptBuffer script = new ScriptBuffer();
    	script
    		  .appendScript("try{if(!$(")
    		  .appendData(elementId+idSuffix)
    		  .appendScript(")){dwr.util.cloneNode(")
              .appendData(elementId)
              .appendScript(", { idPrefix:''")
              .appendScript(", idSuffix:")
              .appendData(idSuffix)
              .appendScript("});")
              .appendScript("dwr.util.setValue(")
              .appendData(child+idSuffix)
              .appendScript(",")
              .appendData(value)
              .appendScript(")}}catch(e){}");
    	addScript(script);
    }
    
    /**
     * 
     */
    public void refreshNumeroOnline(String who){
    	ScriptBuffer script = new ScriptBuffer();
    	script.appendScript("var nOnline = ((dwr.util.getValue('online')-1) < 0) ? 0 : (dwr.util.getValue('online')-1);" +
    			"dwr.util.setValue('online',nOnline);")
    		.appendScript("if(nOnline<=1){dwr.util.setValue('s','usuario en l\\xednea');}else{dwr.util.setValue('s','usuarios en l\\xednea');}")
    		.appendScript("if(destino_activo==").appendData(who).appendScript("){destino_activo=null;}");
    	addScript(script);
    }
    
    /**
     * Elimina las filas de la tabla
     * @param elementId The HTML element to update (by id)
     * @param notThisRow1 id de la fila a no eliminar
     * @param notThisRow2 id de la fila a no eliminar
     */
    public void removeAllRows(String elementId,String notThisRow1, String notThisRow2, String notThisRow3)
    {
    	ScriptBuffer script = new ScriptBuffer();
    	script.appendScript("dwr.util.removeAllRows(")
    	.appendData(elementId)
    	.appendScript(", { filter:function(tr) {return (tr.id != ")
    	.appendData(notThisRow1)
    	.appendScript(" && tr.id != ")
    	.appendData(notThisRow2)
    	.appendScript(" && tr.id != ")
    	.appendData(notThisRow3)
    	.appendScript(");}});");
    	addScript(script);
    }
    
    /**
     * 
     * @param elementId The HTML element to update (by id)
     * @param notThisRow1 id de la fila a no eliminar
     */
    public void removeAllRows(String elementId,String notThisRow1)
    {
    	ScriptBuffer script = new ScriptBuffer();
    	script.appendScript("dwr.util.removeAllRows(")
    	.appendData(elementId)
    	.appendScript(", { filter:function(tr) {return (tr.id != ")
    	.appendData(notThisRow1)
    	.appendScript(");}});");
    	addScript(script);   	
    }
    
    /**
     * Metodo encargado de desaparecer el mensaje de inicio de sesion
     */
	public void desaparecemelo(int tiempo) {
		ScriptBuffer script = new ScriptBuffer();
		script.appendScript("setTimeout('dwr.util.desaparecemelo()',"+tiempo+");");			
		addScript(script);
	}
	
	/**
	 * 
	 * @param idUsuario
	 */
	public void seeMe(String idUsuario) {
		ScriptBuffer script = new ScriptBuffer();
		script.appendScript("if(")
			.appendData(idUsuario).appendScript("!=destino_activo){")
			.appendScript("$(").appendData("table_"+idUsuario).appendScript(").className = 'fon_cab_chat2';")
			.appendScript("}");
		addScript(script);		
	}
	
	/**
	 * 
	 * @param idUsuario
	 */
	public void noSeeMe(String idUsuario) {
		ScriptBuffer script = new ScriptBuffer();
		script
			.appendScript("try{")
			.appendScript("$(").appendData("table_"+idUsuario).appendScript(").className = 'fon_cab_chat3';")
			.appendScript("$(").appendData("text_"+idUsuario).appendScript(").style.borderColor = 'gray';")
			.appendScript("}catch(e){}");
		addScript(script);		
	}
	
	/**
	 * 
	 * @param text
	 * @param destino
	 */
	public void mandamelo(String text, String destino){
		String random = Math.random()*Integer.MAX_VALUE+"";
		ScriptBuffer script = new ScriptBuffer();
		script.appendScript("try{if(document.getElementById(").appendData("modelo_chat_"+destino).appendScript(").style.visibility=='visible'){")
			.appendScript("dwr.util.cloneNode(").appendData("mensaje_"+destino).appendScript(",{idPrefix:'',idSuffix:'"+random+"'});")
			.appendScript("dwr.util.setValue(").appendData("mensaje_"+destino+random).appendScript(",'"+text+"');}}catch(e){}")
		;
		addScript(script);
	}
	
	/**
	 * 
	 */
	public void botame() {		
		ScriptBuffer script = new ScriptBuffer();
		script.appendScript("window.location='http://www.tecsup.edu.pe';");
		addScript(script);		
	}

	/**
	 * 
	 * @param idUsuario
	 */
	public void existe(String idUsuario){
		ScriptBuffer script = new ScriptBuffer();
		script.appendScript("try{")
			.appendScript("if(!$(").appendData("modelo_chat_"+idUsuario).appendScript(")){")
			.appendScript("dwr.util.cloneNode('modelo_chat_',{idPreffix:'',idSuffix:").appendData(idUsuario).appendScript("});")
			.appendScript("dwr.util.setValue(").appendData("x_"+idUsuario).appendScript(",dwr.util.getValue(").appendData("name_"+idUsuario).appendScript("));")
			.appendScript("$(").appendData("modelo_chat_"+idUsuario).appendScript(").style.visibility='visible';")
			.appendScript("$(").appendData("modelo_chat_"+idUsuario).appendScript(").style.top = (Math.random() * 100)+ 20 + 'px';")
			.appendScript("$(").appendData("modelo_chat_"+idUsuario).appendScript(").style.left = (Math.random() * 500)+ 20 + 'px';")
			.appendScript("$(").appendData("table_"+idUsuario).appendScript(").className = 'fon_cab_chat2';")
			.appendScript("}}catch(e){}");
		addScript(script);
	}

	/**
	 * 
	 * @param node
	 * @param idSuffix
	 * @param value
	 */
	public void cloneNvalue(String node, String idSuffix, String value) {
		ScriptBuffer script = new ScriptBuffer();
    	script.appendScript("dwr.util.cloneNode(")
              .appendData(node)
              .appendScript(", { idPrefix:''")
              .appendScript(", idSuffix:")
              .appendData(idSuffix)
              .appendScript("});")
              .appendScript("dwr.util.setValue(")
              .appendData(node+idSuffix)
              .appendScript(",")
              .appendData(value)
              .appendScript(");");
    	addScript(script);
	}

	/**
	 * 
	 */
	public void noChat() {
		ScriptBuffer script = new ScriptBuffer();
		script.appendScript("window.onload='';activo=false;");
		addScript(script);
	}
	
	public void alert() {
		ScriptBuffer script = new ScriptBuffer();
		script.appendScript("play();");
		addScript(script);
	}
	
	public void zumbido() {
		ScriptBuffer script = new ScriptBuffer();
		script.appendScript("zumbido();");
		addScript(script);
	}
	
	public void cierrame(){
		ScriptBuffer script = new ScriptBuffer();
		script.appendScript("alert(").appendData("Su sessión ha finalizado. La ventana de chat se cerrará.").appendScript(");");
		script.appendScript("window.onbeforeunload='';");
		script.appendScript("window.onunload='';");
		script.appendScript("window.close();");
		addScript(script);
	}

}