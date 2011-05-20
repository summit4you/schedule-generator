<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>Edit event</title>
	
	<link rel="stylesheet" type="text/css" href="../templates/default.css">
	
	<link rel="stylesheet" href="../js/slimpicker/pagestyle.css" media="screen, projection" />
	<link rel="stylesheet" href="../js/slimpicker/slimpicker.css" media="screen, projection" />
	<script src="../js/slimpicker/mootools-1.2.4-core-yc.js"></script>
	<script src="../js/slimpicker/mootools-1.2.4.4-more-yc.js"></script>
	<script src="../js/slimpicker/slimpicker.js"></script>
	
	<script type="text/javascript" src="../js/jquery.js"></script>
	<style type="text/css">@import "../js/timeEntry/jquery.timeentry.css";</style> 
	<script type="text/javascript" src="../js/timeEntry/jquery.timeentry.js"></script>
	<script type="text/javascript" src="../js/timeEntry/jquery.timeentry-nl.js"></script>
	
	<script src="../js/ownCreations/coupledSelects.js"></script>
</head>
<body onload="initLocation();{INITLISTS};installDateSelector()">

<script type="text/javascript"> 
	function getRooms()
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
		var sel=document.getElementById('building');
		var date=document.getElementById('inputDate').value;
		var start=document.getElementById('startTime').value;
		var end=document.getElementById('endTime').value;
		var url="{REQUEST_LINK}"+"&building="+sel.options[sel.selectedIndex].value+"&date="+date+"&start="+start+"&end="+end;
		xmlHttp.open("GET",url,false);
		xmlHttp.send();
		document.getElementById("divRooms").innerHTML=xmlHttp.responseText;
	}
	function initLocation()
	{
		var bsel=document.getElementById('building');
		bsel.value="{INIT_BUILDING}";
		getRooms();
		var rsel=document.getElementById('room');
		rsel.value="{INIT_ROOM}";
	}
</script> 

<center>
	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="calborder">
		<tr>
			<td align="center" class="sideback"><div style="height: 17px; margin-top: 3px;" class="G10BOLD">{INFO}</div></td>
		</tr>
		<tr>
			<td align="left" class="V12">
				<div style="margin-left: 10px; margin-right: 10px; margin-bottom: 10px;">
					<form name="edit" action="{SUBMIT_LINK}" method="post">
						<table id="login" border=0 style="width:200px" align="center"><tbody>
							<tr><td style="text-align: right" {HIDDEN}>##Course##</td><td>
									<SELECT id="course" {HIDDEN} name="course">
										{COURSES}
									</SELECT>
									</td>
									<td>
											<select name="subcourse" id="subcourse" {HIDDEN}></select>
									</td></tr>
							<tr><td style="text-align: right">##Date##</td><td><input class="slimpicker" name="inputDate" id="inputDate" onchange="getRooms()" type="text" value="{DATE}" autocomplete="off" alt="{format:'{DATEFORMAT}'}" /></td></tr>
							<tr><td style="text-align: right">##Start##</td><td><input name="startTime" id="startTime" onchange="checkInput();getRooms();" value="{START}"/></td></tr>
							<tr><td style="text-align: right">##End##</td><td><input name="endTime" id="endTime" onchange="checkInput();getRooms();" value="{END}"/></td></tr>
							<tr><td style="text-align: right">##Location##</td><td>
									<SELECT id="building" NAME="building" onChange="getRooms();" >
										{BUILDINGS}
									</SELECT>
									</td>
									<td>
										<div id="divRooms">
											<select name="room" id="room"></select>
										</div>
									</td></tr>
							<tr><td colspan=2 style="text-align: center">
							<label for="submit" id="error" style="color:red"></label>
							<input type="submit" id="submit" value="##Send##"></input></td></tr>
						</tbody></table>
						<input type="hidden" name="uid" value="{UID}"></input>
						<input type="hidden" name="cal" value="{CAL}"></input>
						<input type="hidden" name="dateFormat" value="{DATEFORMAT}"></input>
					</form>		
				</div>
			</td>
		</tr>
	</table>
</center>

<script>
	function checkInput()
	{
		var start=document.getElementById('startTime').value.replace(':','');
		var end=document.getElementById('endTime').value.replace(':','');
		var submit=document.getElementById('submit');
		if (parseInt(start,10)>=parseInt(end,10))
		{
			submit.disabled=true;
			document.getElementById('error').innerHTML="##Start_Hour_Larger_Than_End_Hour##";
		}
		else
		{
			submit.disabled=false;
			document.getElementById('error').innerHTML="";
		}
	}
</script>

<script>
	$(edit.startTime).timeEntry({spinnerImage: '../js/timeEntry/spinnerUpDown.png',spinnerSize: [15, 16, 0], spinnerIncDecOnly: true,timeSteps: [1,5,1]});
	$(edit.endTime).timeEntry({spinnerImage: '../js/timeEntry/spinnerUpDown.png',spinnerSize: [15, 16, 0], spinnerIncDecOnly: true,timeSteps: [1,5,1]});
</script>

<script>
	function installDateSelector()
	{
		jQuery.noConflict();
		$$('input.slimpicker').each( function(el){
		var picker = new SlimPicker(el);
		});
	}
</script>

</body>
</html>