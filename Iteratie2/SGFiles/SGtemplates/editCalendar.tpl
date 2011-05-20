<html>
<head>
	<style type="text/css" title="currentStyle">
		@import "../js/datatables/css/demo_page.css";
		@import "../js/datatables/css/demo_table.css";
	</style>
	<script type="text/javascript" language="javascript" src="../js/jquery.js"></script>
	<script type="text/javascript" language="javascript" src="../js/datatables/jquery.dataTables.js"></script>

	<script type="text/javascript" src="../js/thickbox/thickbox.js"></script>
	<link rel="stylesheet" href="../js/thickbox/thickbox.css" type="text/css" media="screen" />
	
	<script src="../js/ownCreations/coupledSelects.js"></script>
</head>

<body onload="initCoupledLists('faculties','programs',{TEXTS},{VALUES})">

<label for="faculties">##Faculties##</label>
<select id="faculties">
	{FACULTY_LIST}
</select>

<label for="programs">##Programs##</label>
<select id="programs"></select>

<button onclick="processSelection()">##Add_Program##</button>

<b>##Selected_Programs##</b>
<table id="programTable" class="display" id="example" cellpadding="0" cellspacing="0" border="0">
	<thead>
		<tr>
			<th>##Program##</th>
			<th>##Faculty##</th>
			<th>ID</th>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>

<script type="text/javascript">
	var programs="";
	
	function processSelection()
	{
		var plist=document.getElementById('programs');
		var flist=document.getElementById('faculties');
		if (addProgram(plist.options[plist.selectedIndex].value))
		{
			addRow(plist.options[plist.selectedIndex].text,flist.options[flist.selectedIndex].text,plist.options[plist.selectedIndex].value);
		}
	}
	function removeProgram(id)
	{
		programs=programs.replace("_"+id+";","");
		updateCalendar();
	}
	function removeAllPrograms()
	{
		programs="";
		updateCalendar();
	}
	function addProgram(id)
	{
		if (programs.indexOf("_"+id+";")==-1)
		{
			programs+="_"+id+";";
			updateCalendar();
			return true;
		}
		return false;
	}
	function updateCalendar()
	{
		var frame=document.getElementById('calendarFrame');
		frame.src="{LINK}"+"&programs="+programs;
	}
</script>

<script>
	var oTable;
	var giRedraw = false;
	
	$(document).ready(function() {
		/* Add a click handler to the rows - this could be used as a callback */
		$("#programTable tbody").click(function(event) {
			$(oTable.fnSettings().aoData).each(function (){
				$(this.nTr).removeClass('row_selected');
			});
			$(event.target.parentNode).addClass('row_selected');
			
			setHref(oTable.fnGetData(fnGetSelected(oTable)[0])[2]);
		});
		
		/* Add a click handler for the delete row */
		$('#delete').click( function() {
			var anSelected = fnGetSelected( oTable );
			oTable.fnDeleteRow( anSelected[0] );
			removeProgram(anSelected[0]);
		} );
		
		/* Init the table */
		oTable =$('#programTable').dataTable( {
			"sDom": '<"toolbar">frtip'
			} );
			
		oTable.fnSetColumnVis(2,false);
		
		$("div.toolbar").html('<button onclick="fnClickDeleteRow()">##Delete_Program##</button><button onclick="deleteAll()">##Delete_All##</button>');
	} );
	
	function deleteAll()
	{
		oTable.fnClearTable();
		removeAllPrograms();
	}
	
	function fnClickDeleteRow()
	{
		var anSelected=fnGetSelected(oTable);
		if (anSelected.length==0)
		{
			alert("##Select_Program_In_Table##");
		}
		else
		{
			removeProgram(oTable.fnGetData(anSelected[0])[2]);
			oTable.fnDeleteRow(anSelected[0]);
		}
	}

	/* Get the rows which are currently selected */
	function fnGetSelected( oTableLocal )
	{
		var aReturn = new Array();
		var aTrs = oTableLocal.fnGetNodes();
		
		for ( var i=0 ; i<aTrs.length ; i++ )
		{
			if ( $(aTrs[i]).hasClass('row_selected') )
			{
				aReturn.push( aTrs[i] );
			}
		}
		return aReturn;
	}
	
	function addRow(program,faculty,id) 
	{
		$('#programTable').dataTable().fnAddData( [
		program,
		faculty,
		id ] );
	}
	
	function setHref(programID)
	{
		var button=document.getElementById('addEvent');
		if (programID==null)
		{
			button.disabled=true;
		}
		else
		{
			button.disabled=false;
			button.alt="{CREATELINK}&program="+programID+"&KeepThis=true&TB_iframe=true&height=400&width=600";
		}
	}
</script>

<label for="addEvent" value="##Add_Event_Info##"></label>
<input type="button" disabled="true" id="addEvent" class="thickbox" value="##Create_Event##"></input>

<iframe id='calendarFrame' src='{LINK}&programs=' style="height:720px;width:1000"></iframe>

</body>
</html>