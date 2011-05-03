<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>Edit event</title>
	
	<link rel="stylesheet" type="text/css" href="templates/default.css">
	
	<link rel="stylesheet" href="js/slimpicker/pagestyle.css" media="screen, projection" />
	<link rel="stylesheet" href="js/slimpicker/slimpicker.css" media="screen, projection" />
	<script src="js/slimpicker/mootools-1.2.4-core-yc.js"></script>
	<script src="js/slimpicker/mootools-1.2.4.4-more-yc.js"></script>
	<script src="js/slimpicker/slimpicker.js"></script>
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<style type="text/css">@import "js/timeEntry/jquery.timeentry.css";</style> 
	<script type="text/javascript" src="js/timeEntry/jquery.timeentry.js"></script>
	<script type="text/javascript" src="js/timeEntry/jquery.timeentry-nl.js"></script>
	
</head>
<body onload="setLocation('{BUILDING}','{ROOM}');">

<script type="text/javascript">
	function setLocation(buildingVal,roomVal)
	{
		var building = document.getElementById('buildings');
		building.value = buildingVal;
		
		SelectSubCat();
		
		var room = document.getElementById('rooms');
		room.value = roomVal;
	}
</script>

<script type="text/javascript">
	function SelectSubCat()
	{
		{ROOMSCRIPT}
	}
	function addListOption(selectbox,value) 
	{
		var optn = document.createElement("OPTION");
		optn.text = value;
		optn.value = value;
		selectbox.options.add(optn);
	} 
	function removeAllOptions(selectbox)
	{
		var i;
		for(i=selectbox.options.length-1;i>=0;i--)
		{
			selectbox.remove(i);
		}
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
					<form name="edit" action="http://wilma.vub.ac.be:8080/CalendarEdit/Edit" method="post">
						<table id="login" border=0 style="width:200px" align="center"><tbody>
							<tr><td style="text-align: right">Date</td><td><input class="slimpicker" id="inputDate" type="text" value="{DATE}" autocomplete="off" alt="{format:'dd/MM/yyyy'}" /></td></tr>
							<tr><td style="text-align: right">Start</td><td><input id="startTime" value="{START}"/></td></tr>
							<tr><td style="text-align: right">End</td><td><input id="endTime" value="{END}"/></td></tr>
							<tr><td style="text-align: right">Location</td><td>
									<SELECT id="buildings" NAME="buildings" onChange="SelectSubCat();" >
										{BUILDINGS}
									</SELECT>
									</td>
									<td>
										<select name="rooms" id="rooms"></select>
									</td></tr>
							<tr><td colspan=2 style="text-align: center"><input type="submit" value="send"></input></td></tr>
						</tbody></table>
					</form>		
				</div>
			</td>
		</tr>
	</table>
</center>

<script>
	$(edit.startTime).timeEntry({spinnerImage: '../js/timeEntry/spinnerUpDown.png',spinnerSize: [15, 16, 0], spinnerIncDecOnly: true,timeSteps: [1,5,1]});
	$(edit.endTime).timeEntry({spinnerImage: '../js/timeEntry/spinnerUpDown.png',spinnerSize: [15, 16, 0], spinnerIncDecOnly: true,timeSteps: [1,5,1]});
</script>

<script> 
	jQuery.noConflict();

	$$('input.slimpicker').each( function(el){
	var picker = new SlimPicker(el);
	});
</script>

</body>
</html>