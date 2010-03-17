/**
 * @author Alexandre Magno
 * @desc Center a element with jQuery
 * @version 1.0
 * @example
 * $("element").center({
 *
 * 		vertical: true,
 *      horizontal: true
 *
 * });
 * @obs With no arguments, the default is above
 * @license free
 * @param bool vertical, bool horizontal
 * @contribution Paulo Radichi and Tales Santos
 *
 */
jQuery.fn.center=function(B){var A={vertical:true,horizontal:true};op=jQuery.extend(A,B);return this.each(function(){var M=jQuery(this);var C=M.width();var N=M.height();var O=parseInt(M.css("padding-top"));var D=parseInt(M.css("padding-bottom"));var E=parseInt(M.css("border-top-width"));var L=parseInt(M.css("border-bottom-width"));var H=(E+L)/2;var J=(O+D)/2;var I=M.parent().css("position");var G=(C/2)*(-1);var K=((N/2)*(-1))-J-H;var F={position:"absolute"};if(op.vertical){F.height=N;F.top="50%";F.marginTop=K}if(op.horizontal){F.width=C;F.left="50%";F.marginLeft=G}if(I=="static"){M.parent().css("position","relative")}M.css(F)})};