<html>
<head>
<script type="text/javascript">
function getPrograms()
 {
  var xmlHttp;

  if (window.XMLHttpRequest)
  {
   xmlHttp=new XMLHttpRequest();
  }
  else // for older IE 5/6
  {
   xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
  }

  var fac=document.getElementById('faculty').value;
  var url="{MASTERSERVLET}"+"&faculty="+fac;
  xmlHttp.open("POST",url,false);
  xmlHttp.send();
  document.getElementById("program").innerHTML=xmlHttp.responseText;
 }
 
 function getCourses()
  {
  var xmlHttp;

  if (window.XMLHttpRequest)
  {
   xmlHttp=new XMLHttpRequest();
  }
  else // for older IE 5/6
  {
   xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
  var pro=document.getElementById('program').value;
  var url="{MASTERSERVLET}"+"&program="+pro;
  xmlHttp.open("POST",url,false);
  xmlHttp.send();
  document.getElementById("course").innerHTML=xmlHttp.responseText;
 }
</script>
</head>
<body>
<form method="post" action="{MASTERSERVLET}">
	<table>
		<tr>
			<td>
				##faculty##
			</td>
			<td>
				<select id="faculty" name="faculty" onChange="getPrograms()">
					{FACULTIES}
				</select>
			</td>
		</tr>
		<tr>
			<td>
				##program##
			</td>
			<td>
				<div id="program">
					<select name="program" onChange="getCourses()"></select>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				##course##
			</td>
			<td>
				<div id="course">
					<select name="course"></select>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<input type="submit"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>