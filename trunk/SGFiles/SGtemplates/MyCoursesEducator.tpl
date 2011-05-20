<html>
<head>

	<script type="text/javascript" src="../js/jquery.js"></script>
	<script type="text/javascript" src="../js/thickbox/thickbox.js"></script>
	<link rel="stylesheet" href="../js/thickbox/thickbox.css" type="text/css" media="screen" />
	<style type="text/css" title="currentStyle">
		@import "../js/datatables/css/demo_page.css";
		@import "../js/datatables/css/demo_table.css";
		@import "../js/datatables/css/jquery-ui-1.8.4.custom.css";
			.ui-tabs .ui-tabs-panel { padding: 10px }
	</style>
	<script type="text/javascript" language="javascript" src="../js/datatables/jquery.dataTables.js"></script>
	<script type="text/javascript" language="javascript" src="../js/datatables/jquery.jeditable.js"></script>
	<script type="text/javascript" language="javascript" src="../js/datatables/jquery-ui-tabs.js"></script>
	

	<script type="text/javascript" language="javascript">
	
	$(document).ready(function() {
	$('#courses').dataTable( {
		"sScrollY": "100px",
		"bPaginate": false,
		"bLengthChange": false,
		"bFilter": false,
		"bSort": false,
		"bInfo": false,
		"bAutoWidth": false } );
	} );


	/* Add a click handler for the delete row */
	$('#edit').click( function() {
		editSubCourse();
	} );

	function editSubCourse()
	{
	var selected = document.getElementById('options').value;
	var url = "{MASTERSERVLET}&edit=true&Sid="+selected+"&KeepThis=true&TB_iframe=true&height=400&width=600";
	tb_show('Edit', url, false);
	}

	</script>
	
</head>
<body>
<table border="0" style="border-collapse:collapse">
	<tr>
		<td>
			{COURSES}
		</td>
	</tr>
	<tr>
		<td>
			<table border="0" style="border-collapse:collapse" width="900">
				<tr>

						<td>
							<select id="options" name="edit">{SUBCOURSEOPTIONS}</select>
						</td>
						<td>
							<input id="edit" type="button" onClick="editSubCourse()" value="##Edit_Subcourse##"></input>
						</td>
					</tr>
			</table>
		</td>
	</tr>
</table>

</body>
</html>