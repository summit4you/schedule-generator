<script type="text/javascript"> 
	function calcHeight()
	{
	  //find the height of the internal page
	  var the_height=document.getElementById('calendar').contentWindow.document.body.scrollHeight;
 
	  //change the height of the iframe
	  document.getElementById('calendar').height=the_height+20;
	  
	  //find the width of the internal page
	  var the_width=document.getElementById('calendar').contentWindow.document.body.scrollWidth;
 
	  //change the height of the iframe
	  document.getElementById('calendar').width=the_width+20;
	}
	
</script> 
<table align=center>
<tr>
<td width="1024">
<Iframe id="calendar" src="{LINK}" scrolling="yes" onload="calcHeight();changeBackground();" frameborder="0" >
Sorry, your browser doesn't support iFrames. </br>
You need a browser which supports iFrames to view this page. </br> 
Go for Google Chrome!</Iframe> 
</td>
</tr>
</table>