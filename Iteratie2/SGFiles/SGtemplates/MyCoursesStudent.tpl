<html>
<head>
	<style type="text/css" title="currentStyle">
		@import "../js/datatables/css/demo_page.css";
		@import "../js/datatables/css/demo_table.css";
	</style>
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/thickbox/thickbox.js"></script>
<link rel="stylesheet" href="../js/thickbox/thickbox.css" type="text/css" media="screen" />
<script type="text/javascript" language="javascript" src="../js/datatables/jquery.dataTables.js"></script>
<script type="text/javascript" language="javascript" src="../js/datatables/jquery-ui-tabs.js"></script>


<script type="text/javascript" charset="utf-8">
	var oTable;
	var giRedraw = false;

	$(document).ready(function() {
		/* Init the table */
		oTable = $('#freecourses').dataTable({"bSort": false,"bFilter": false});
	
		/* Add a click handler to the rows - this could be used as a callback */
		$('#freecourses tbody').click(function(event) {
			$(oTable.fnSettings().aoData).each(function (){
				$(this.nTr).removeClass('row_selected');
			});
			$(event.target.parentNode).addClass('row_selected');
		});
		
		try
		{
			/* Add a click handler for the delete row */
			$('#delete').click( function() {
				var anSelected = fnGetSelected( oTable );
				
				var xmlHttp;
				if (window.XMLHttpRequest)
				{
					xmlHttp=new XMLHttpRequest();
				}
				else // for older IE 5/6
				{
					xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
				}
				var course=oTable.fnGetData(anSelected[0])[2];
				oTable.fnDeleteRow( anSelected[0] );
				var url="{MASTERSERVLET}"+"&courseid="+course;
				xmlHttp.open("GET",url,false);
				xmlHttp.send();
			} );
		}
		catch(err)
		{
		//Handle errors here
		}
		
		
	} );


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
<table border="0" style="border-collapse:collapse">
	<tr>
		<td>
			{PROGRAMCOURSES}
		</td>
	</tr>
	<tr>
		<td>
			{FREE COURSES}
		</td>
	</tr>
	<tr>
		<td>
			<table border="0" style="border-collapse:collapse" width="900">
				<tr>
					<form>
						<td>
							<input type="button" value="##delete_selected_course##" id="delete">
						</td>
					</form>
						<td>
							<a class="thickbox" href="{MASTERSERVLET}&add=true&KeepThis=true&TB_iframe=true&height=400&width=600">##add_subcourse##</a>
						</td>
					</tr>
			</table>
		</td>
	</tr>
</table>

</body>
</html>