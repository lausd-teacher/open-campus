<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><s:text name="titulo.campus.virtual" /></title>

<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />		
<script type="text/javascript" src="<%=request.getContextPath()%>/js/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/scriptaculous/scriptaculous.js?load=builder,effects,dragdrop"></script>
	
<% if(true){ %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/topup/top_up-min.js?libs=core&fast_mode=1"></script>
<script type="text/javascript">
	jQuery.noConflict();
  	TopUp.images_path = '<%=request.getContextPath()%>/js/topup/images/';
  	TopUp.players_path = '<%=request.getContextPath()%>/js/topup/players/';
</script>
<c:set var="modal_config1" scope="application" value="type = iframe, effect = show, layout = quicklook, modal = 1, shaded = 1"/>
<% } %>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/wforms.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wforms-localization-es.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/fastinit.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/tablesort.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/date.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/anatips.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/open_modal.js"></script>	
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jComponente.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/util.js"></script>