<!-- *************************** ENCUESTA **************************************** -->

<c:if test="${hayEncuesta}">

	<div id="div_back_encuesta">
	</div>
	<div id="div_encuesta" style="text-align: left;">
	
		<form name="frmEncuesta" method="post" action="<%=request.getContextPath()%>/aulavirtual/Guardar.action"
			onsubmit="return validarEncuesta(this)">
		    <table width="600"  cellpadding="3" cellspacing="0" id="table_encuesta"
					bgcolor="#FFFFFF" class="bor_tabla" border="0">
		      <tr align="left" class="fon_cab">
		        <td colspan="3" class="tit_cab"> Encuesta : <b> </b> </td>
		      </tr>
		      <tr>
		        <td colspan="3"><p>Estimado participante, sírvase responder las siguientes preguntas, que nos  permitirán mejorar los servicios en Tecsup Virtual. Gracias. </p></td>
		      </tr>
		      <tr class="fon_color01">
		        <td width="4%">1.</td>
		        <td width="90%" align="left">Datos generales :</td>
		        <td width="6%">&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td><table width="100%" border="0" class="bor_tabla">
		          <tr>
		            <td><table width="100%" border="0">
		                <tr bgcolor="#FFFFFF" class="bor_tabla">
		                  <td align="left">Utilización preferente de la computadora para Tecsup Virtual :</td>
		                  <td align="left"><select name="p1_p1" size="1" id="p1_p1">
		                    <option value="1" selected="selected">En Tecsup</option>
		                    <option value="2">En casa</option>
		                    <option value="2">En oficina</option>
		                    <option value="3">En cabina p&uacute;blica</option>
		                    </select>                  </td>
		                </tr>
		                <tr bgcolor="#FFFFFF" class="bor_tabla">
		                  <td align="left">Horas promedio por semana dedicadas al estudio del curso :</td>
		                  <td align="left"><input type="text" name="p1_p2" id="p1_p2" maxlength="3" onKeyPress="return soloNumeros(event)"/></td>
		                </tr>
		            </table></td>
		          </tr>
		        </table></td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr >
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr class="fon_color01">
		        <td>2.</td>
		        <td align="left"> Presentación del material de estudio :        </td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="bor_tabla">
		          <tr >
		            <td ><table width="100%" border="0" cellpadding="3" cellspacing="0">
		                <tr bgcolor="#FFFFFF" >
		                  <td colspan="2" class="fon_pregunta" align="left">Los repasos de cada unidad fueron :</td>
		                  </tr>
		                <tr bgcolor="#FFFFFF" >
		                  <td colspan="2"><table width="100%" border="0" cellpadding="0" cellspacing="0">
		                      <tr>
		                        <td width="42%" height="35"><div align="right">Malos</div></td>
		                        <td width="3%"><input type="radio" name="p2_p1" id="radio56" value="1" /></td>
		                        <td width="3%"><input type="radio" name="p2_p1" id="radio57" value="2" /></td>
		                        <td width="3%"><input type="radio" name="p2_p1" id="radio58" value="3" /></td>
		                        <td width="3%"><input type="radio" name="p2_p1" id="radio59" value="4" /></td>
		                        <td width="3%"><input type="radio" name="p2_p1" id="radio60" value="5" /></td>
		                        <td width="43%" align="left">Excelentes</td>
		                      </tr>
		                  </table></td>
		                </tr>
		                
		                <tr bgcolor="#FFFFFF" >
		                  <td colspan="2" class="fon_pregunta2" align="left">El lenguaje utilizado en los materiales de aprendizaje de texto y pantalla fueron : </td>
		                  </tr>
		                <tr bgcolor="#FFFFFF" class="bor_tabla">
		                  <td colspan="2"><table width="100%" border="0" cellpadding="0" cellspacing="0">
		                      <tr >
		                        <td width="42%" height="35"><div align="right">Difícil de entender</div></td>
		                        <td width="3%"><input type="radio" name="p2_p2" id="radio51" value="1" /></td>
		                        <td width="3%"><input type="radio" name="p2_p2" id="radio52" value="2" /></td>
		                        <td width="3%"><input type="radio" name="p2_p2" id="radio53" value="3" /></td>
		                        <td width="3%"><input type="radio" name="p2_p2" id="radio54" value="4" /></td>
		                        <td width="3%"><input type="radio" name="p2_p2" id="radio55" value="5" /></td>
		                        <td width="43%"  align="left">Claro y fácil de entender </td>
		                      </tr>
		                  </table></td>
		                </tr>
		                
		            </table></td>
		          </tr>
		        </table></td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr class="fon_color01">
		        <td>3.</td>
		        <td align="left">Del Docente :</td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="bor_tabla">
		          <tr>
		            <td><table width="100%" border="0" cellpadding="3" cellspacing="0">
		                <tr bgcolor="#FFFFFF" >
		                  <td class="fon_pregunta" align="left">El docente respondió a tiempo las preguntas formuladas : </td>
		                  </tr>
		                <tr bgcolor="#FFFFFF" >
		                  <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
		                      <tr>
		                        <td width="42%" height="35"><div align="right">Nunca</div></td>
		                        <td width="3%"><input name="p3_p1" type="radio" id="radio61" value="1" /></td>
		                        <td width="3%"><input type="radio" name="p3_p1" id="radio62" value="2" /></td>
		                        <td width="3%"><input type="radio" name="p3_p1" id="radio63" value="3" /></td>
		                        <td width="3%"><input type="radio" name="p3_p1" id="radio64" value="4" /></td>
		                        <td width="3%"><input type="radio" name="p3_p1" id="radio65" value="5" /></td>
		                        <td width="43%" align="left">Siempre</td>
		                      </tr>
		                  </table></td>
		                </tr>
		                
		                <tr bgcolor="#FFFFFF" >
		                  <td class="fon_pregunta2" align="left">El docente participa en los di&aacute;logos : </td>
		                  </tr>
		                <tr bgcolor="#FFFFFF" >
		                  <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
		                      <tr>
		                        <td width="42%" height="35"><div align="right">Nunca</div></td>
		                        <td width="3%"><input type="radio" name="p3_p2" id="radio6" value="1" /></td>
		                        <td width="3%"><input type="radio" name="p3_p2" id="radio7" value="2" /></td>
		                        <td width="3%"><input type="radio" name="p3_p2" id="radio8" value="3" /></td>
		                        <td width="3%"><input type="radio" name="p3_p2" id="radio9" value="4" /></td>
		                        <td width="3%"><input type="radio" name="p3_p2" id="radio10" value="5" /></td>
		                        <td width="43%" align="left">Siempre</td>
		                      </tr>
		                  </table></td>
		                </tr>
		                <tr bgcolor="#FFFFFF" >
		                  <td class="fon_pregunta2" align="left">Las comunicaciones del docente : </td>
		                  </tr>
		                <tr bgcolor="#FFFFFF" >
		                  <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
		                      <tr>
		                        <td width="42%" height="35"><div align="right">No   ayudaron </div></td>
		                        <td width="3%"><input type="radio" name="p3_p3" id="radio" value="1" /></td>
		                        <td width="3%"><input type="radio" name="p3_p3" id="radio2" value="2" /></td>
		                        <td width="3%"><input type="radio" name="p3_p3" id="radio3" value="3" /></td>
		                        <td width="3%"><input type="radio" name="p3_p3" id="radio4" value="4" /></td>
		                        <td width="3%"><input type="radio" name="p3_p3" id="radio5" value="5" /></td>
		                        <td width="43%" align="left">Ayudaron mucho </td>
		                      </tr>
		                  </table></td>
		                </tr>
		                
		            </table></td>
		          </tr>
		        </table></td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr class="fon_color01">
		        <td>4.</td>
		        <td align="left">Evaluaciones : </td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="bor_tabla">
		          <tr>
		            <td><table width="100%" border="0" cellpadding="3" cellspacing="0">
		                <tr bgcolor="#FFFFFF" >
		                  <td class="fon_pregunta" align="left">Las evaluaciones están de acuerdo con el grado de dificultad presentado en los materiales de aprendizaje en texto y pantalla : </td>
		                  </tr>
		                <tr bgcolor="#FFFFFF" class="bor_tabla">
		                  <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
		                      <tr>
		                        <td width="42%" height="35"><div align="right">Son muy fáciles o muy difíciles</div></td>
		                        <td width="3%"><input type="radio" name="p4_p1" id="radio66" value="1" /></td>
		                        <td width="3%"><input type="radio" name="p4_p1" id="radio67" value="2" /></td>
		                        <td width="3%"><input type="radio" name="p4_p1" id="radio68" value="3" /></td>
		                        <td width="3%"><input type="radio" name="p4_p1" id="radio69" value="4" /></td>
		                        <td width="3%"><input type="radio" name="p4_p1" id="radio70" value="5" /></td>
		                        <td width="43%" align="left">Mismo grado de dificultad </td>
		                      </tr>
		                  </table></td>
		                </tr>
		                
		
		            </table></td>
		          </tr>
		        </table></td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr class="fon_color01">
		        <td>5.</td>
		        <td align="left"> Facilidad en las comunicaciones :        </td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="bor_tabla">
		          <tr>
		            <td><table width="100%" border="0" cellpadding="3" cellspacing="0">
		                <tr bgcolor="#FFFFFF" >
		                  <td class="fon_pregunta" align="left">El acceso a Tecsup Virtual se realizó sin dificultades : </td>
		                  </tr>
		                <tr bgcolor="#FFFFFF" class="bor_tabla">
		                  <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
		                      <tr>
		                        <td width="42%" height="35"><div align="right">Siempre fue difícil acceder</div></td>
		                        <td width="3%"><input type="radio" name="p5_p1" id="radio16" value="1" /></td>
		                        <td width="3%"><input type="radio" name="p5_p1" id="radio17" value="2" /></td>
		                        <td width="3%"><input type="radio" name="p5_p1" id="radio18" value="3" /></td>
		                        <td width="3%"><input type="radio" name="p5_p1" id="radio19" value="4" /></td>
		                        <td width="3%"><input type="radio" name="p5_p1" id="radio20" value="5" /></td>
		                        <td width="43%" align="left">Nunca tuve dificultades </td>
		                      </tr>
		                  </table></td>
		                </tr>
		                
		            </table></td>
		          </tr>
		        </table></td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr class="fon_color01">
		        <td>6.</td>
		        <td align="left">Conceptos generales sobre el Campus : </td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="bor_tabla">
		          <tr>
		            <td><table width="100%" border="0" cellpadding="3" cellspacing="0">
		                <tr bgcolor="#FFFFFF" >
		                  <td class="fon_pregunta" align="left">La metodología de Tecsup Virtual me ha permitido : </td>
		                  </tr>
		                <tr bgcolor="#FFFFFF" class="bor_tabla">
		                  <td><table width="100%" border="0" cellspacing="0">
		                      <tr>
		                        <td width="42%" height="35"><div align="right" align="left">No he   aprendido nada
		                         
		                        </div></td>
		                        <td width="3%"><input type="radio" name="p6_p1" id="radio76" value="1" /></td>
		                        <td width="3%"><input type="radio" name="p6_p1" id="radio77" value="2" /></td>
		                        <td width="3%"><input type="radio" name="p6_p1" id="radio78" value="3" /></td>
		                        <td width="3%"><input type="radio" name="p6_p1" id="radio79" value="4" /></td>
		                        <td width="3%"><input type="radio" name="p6_p1" id="radio80" value="5" /></td>
		                        <td width="43%" align="left">Aprender mucho </td>
		                      </tr>
		                  </table></td>
		                </tr>
		                <tr bgcolor="#FFFFFF" >
		                  <td class="fon_pregunta2" align="left">Los contenidos del curso son útiles para mi actividad : </td>
		                  </tr>
		                <tr bgcolor="#FFFFFF" >
		                  <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
		                      <tr>
		                        <td width="42%" height="35"><div align="right">Nada   útiles </div></td>
		                        <td width="3%"><input type="radio" name="p6_p2" id="radio71" value="1" /></td>
		                        <td width="3%"><input type="radio" name="p6_p2" id="radio72" value="2" /></td>
		                        <td width="3%"><input type="radio" name="p6_p2" id="radio73" value="3" /></td>
		                        <td width="3%"><input type="radio" name="p6_p2" id="radio74" value="4" /></td>
		                        <td width="3%"><input type="radio" name="p6_p2" id="radio75" value="5" /></td>
		                        <td width="43%" align="left">Bastante útiles </td>
		                      </tr>
		                  </table></td>
		                </tr>
		                <tr bgcolor="#FFFFFF">
		                  <td class="fon_pregunta2" align="left">El aprendizaje mediante el campus ha cubierto mis expectativas : </td>
		                  </tr>
		                <tr bgcolor="#FFFFFF" class="bor_tabla">
		                  <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
		                      <tr>
		                        <td width="42%" height="35"><div align="right">No ha   cubierto </div></td>
		                        <td width="3%"><input type="radio" name="p6_p3" id="radio31" value="1" /></td>
		                        <td width="3%"><input type="radio" name="p6_p3" id="radio32" value="2" /></td>
		                        <td width="3%"><input type="radio" name="p6_p3" id="radio33" value="3" /></td>
		                        <td width="3%"><input type="radio" name="p6_p3" id="radio34" value="4" /></td>
		                        <td width="3%"><input type="radio" name="p6_p3" id="radio35" value="5" /></td>
		                        <td width="43%" align="left">Totalmente</td>
		                      </tr>
		                  </table></td>
		                </tr>
		                
		            </table></td>
		          </tr>
		        </table></td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr class="fon_color01">
		        <td>7.</td>
		        <td align="left">Sugerencias: </td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td><textarea name="p7_p1" style="width:100%;" rows="4"></textarea></td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td>&nbsp;</td>
		        <td><div align="center">
		          <input type="submit" id="btnGuardar" value="Guardar" class="form_button" style="width: 60px;"/>
		          <!-- input type="reset" id="btnReset" value="Omitir" class="form_button" style="width: 60px;" onclick="ocultarEncuesta()"/ -->
		        </div></td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td colspan="3">&nbsp;</td>
		      </tr>
		    </table></form>

	</div>
	<script type="text/javascript">
		encuesta();
	</script>
	
</c:if>

<!-- *************************** FIN ENCUESTA **************************************** -->