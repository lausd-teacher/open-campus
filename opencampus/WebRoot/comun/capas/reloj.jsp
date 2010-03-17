<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	java.util.Date ahora = new java.util.Date();
	java.util.Calendar actual = java.util.Calendar.getInstance();
	actual.setTime(ahora);
	String tiempo = String.valueOf(actual.get(java.util.Calendar.YEAR)
			+ "," + actual.get(java.util.Calendar.MONTH) + ","
			+ actual.get(java.util.Calendar.DAY_OF_MONTH) + ","
			+ actual.get(java.util.Calendar.HOUR_OF_DAY) + ","
			+ actual.get(java.util.Calendar.MINUTE) + ","
			+ actual.get(java.util.Calendar.SECOND));
%>

<script type="text/javascript">
var ahorita=new Date(<%=tiempo%>);
function mostrarReloj(){
	local=new Date();
	ahorita.setSeconds(ahorita.getSeconds()+1);
	document.getElementById('reloj').innerHTML="Lima,&nbsp;"+setZeroString(ahorita.getDate())+"-"+setZeroString(ahorita.getMonth()+1)+"-"+ahorita.getFullYear()+"&nbsp;"+setZeroString(ahorita.getHours())+":"+setZeroString(ahorita.getMinutes())+":"+setZeroString(ahorita.getSeconds())+"&nbsp;&nbsp;";
	setTimeout('mostrarReloj()',990);
}

function setZeroString(s_valor){
	return (s_valor<10)?'0'+s_valor:s_valor;
}
</script>