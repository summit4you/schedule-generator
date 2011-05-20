<html>
<head>
<script type="text/javascript">
var t;
var xmlHttp;
function getStatus()
{
	if (window.XMLHttpRequest)
	{
		xmlHttp=new XMLHttpRequest();
	}
	else // for older IE 5/6
	{
		xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	var url="{MASTERSERVLET}&form=status";
	xmlHttp.open("POST",url,false);
	xmlHttp.send();
	document.getElementById("status").innerHTML=xmlHttp.responseText;
	t=setTimeout("getStatus()",2000);
}
</script> 
</head>
<body onLoad="getStatus()">
<table border="1" style="border-collapse:collapse">
	<tbody>
		<tr>
			<td>
				<form method="POST" action="{MASTERSERVLET}">
					<input type="hidden" name="form" value="start"></form>
					##Semester_to_solve##
					<select name="semester">
						<option>1</option>
						<option>2</option>
					</select>
					<input type="submit" value="##Start_algorithm##"></input>
				</form>
			</td>
		</tr>
		<tr>
			<td>
				<div id="status">
					
				</div>
			</td>
		</td>
		<tr>
			<td>
				<form method="POST" action="{MASTERSERVLET}">
					<input type="hidden" name="form" value="stop"></form>
					<input type="submit" value="##Stop_algorithm##"></input>
				</form>
			</td>
		</tr>
	</tbody>
</table>
</body>
</html>