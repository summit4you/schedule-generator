<html>
<head>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/thickbox/thickbox.js"></script>
	<link rel="stylesheet" href="js/thickbox/thickbox.css" type="text/css" media="screen" />
	<script type="text/javascript" language="javascript" src="js/datatables/jquery.dataTables.js"></script>
	<script type="text/javascript" language="javascript" src="js/datatables/jquery-ui-tabs.js"></script>

	<script type="text/javascript" charset="utf-8">
	var oTable;
	var giRedraw = false;

	$(document).ready(function() {
		/* Init the table */
		$('#hardware').dataTable({"bSort": false,"bFilter": false});
		
		/* Add a click handler to the rows - this could be used as a callback */
		$('#hardware tbody').click(function(event) {
			$(oTable.fnSettings().aoData).each(function (){
				$(this.nTr).removeClass('row_selected');
			});
			$(event.target.parentNode).addClass('row_selected');
		});
		
		/* Add a click handler for the edit row */
		$('#addhardware').click( function() {
			fnClickAddRow();
		} );
		
		/* Add a click handler for the delete row */
		$('#delete').click( function() {
			var anSelected = fnGetSelected( oTable );
			oTable.fnDeleteRow( anSelected[0] );
		} );
	} );

	function fnClickAddRow() {
		var newdata =  [ '<input type=\"hidden\" name=\"' + document.getElementById("selectedhardware").value + '\">' + document.getElementById("selectedhardware").value ];
		$('#hardware').dataTable().fnAddData(newdata,true);
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
	</script>
</head>
<body>

<form method="post" action="{MASTERSERVLET}">
	<table border="1" style="border-collapse:collapse">
		<tr>
			<td style="vertical-align:top;text-align:right">
				##Subcourse_name##
			</td>
			<td colspan="3">
				<input type="text" name="name" value="{NAME}"></input>
			</td>
		</tr>
		<tr>
			<td style="vertical-align:top;text-align:right">
				##Subcourse_properties##
			</td>
			<td colspan="3">
				<textarea  name="properties" rows="10" cols="40">{PROPERTIES}</textarea>
			</td>
		</tr>
		re
		<tr>
			<td style="vertical-align:top;text-align:right">
				##Subcourse_totalNumberHours##
			</td>
			<td>
				<input type="text" name="totalNumberHours" value="{TOTALNUMBERHOURS}" size="2"></input>
			</td>
			<td style="vertical-align:top;text-align:right">
				##Subcourse_blockHours##
			</td>
			<td>
				<input type="text" name="blockHours" value="{BLOCKHOURS}" size="2"></input>
			</td>	
		</tr>
		<tr>
			<td colspan="2">
				{HARDWARETABLE}
			</td>
			<td colspan="2">
				<table border=0 style="border-collapse:collapse">
					<tr>
						<td>
							<select id="selectedhardware">
								{HARDWAREOPTIONS}
							</select>
						</td>
						<td>
							<input type="button" id="addhardware" value="##Add_to_list##"></input>
						</td>
					</tr>
					<tr>
						<td>
						</td>
						<td>
							<input type="button" id="delete" value="##Remove_Selected##"></input>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<input type="submit"/><input type="hidden" name="Sid" value="{SUBCOURSEID}"/>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				{NOTICE}
			</td>
		</tr>
	</table>
</form>

</body>
</html>