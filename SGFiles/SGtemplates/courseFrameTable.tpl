<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		
		<style type="text/css" title="currentStyle">
		@import "js/datatables/css/demo_page.css";
		@import "js/datatables/css/demo_table.css";
		</style>
		<script type="text/javascript" language="javascript" src="js/jquery.js"></script>
		<script type="text/javascript" language="javascript" src="js/datatables/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8">
			/* Formating function for row details */
			function fnFormatDetails ( oTable, nTr )
			{
				{EXPAND_SCRIPT}
			}
			
			$(document).ready(function() {
		/*
		 * Insert a 'details' column to the table
		 */
		var nCloneTh = document.createElement( 'th' );
		var nCloneTd = document.createElement( 'td' );
		nCloneTd.innerHTML = '<img src="../js/datatables/images/details_open.png">';
		nCloneTd.className = "center";
				
		$('#maintable thead tr').each( function () {
			this.insertBefore( nCloneTh, this.childNodes[0] );
		} );
				
		$('#maintable tbody tr').each( function () {
			this.insertBefore(  nCloneTd.cloneNode( true ), this.childNodes[0] );
		} );
				
		/*
		 * Initialse DataTables, with no sorting on the 'details' column
		 */
		var oTable = $('#maintable').dataTable( {
			"aoColumnDefs": [
				{ "bSortable": false, "aTargets": [ 0 ] }
			],
			"aaSorting": [[1, 'asc']],
			
			"fnDrawCallback": function () { 
				 resizeParentFrame();
			}
		});
				
		/* Add event listener for opening and closing details
		 * Note that the indicator for showing which row is open is not controlled by DataTables,
		 * rather it is done here
		 */
		$('#maintable tbody td img').live('click', function () {
			var nTr = this.parentNode.parentNode;
			if ( this.src.match('details_close') )
			{
				/* This row is already open - close it */
				this.src = "../js/datatables/images/details_open.png";
				oTable.fnClose( nTr );
			}
			else
			{
				/* Open this row */
				this.src = "../js/datatables/images/details_close.png";
				oTable.fnOpen( nTr, fnFormatDetails(oTable, nTr), 'details' );
				{INIT_DATATABLE}
			}
			resizeParentFrame();
		} );
	} );
		</script>
	</head>
<body id="dt_example">

<div id="container">			
<div id="demo">
	{TABLE}
</div>
</div>

<script>
	function resizeParentFrame()
	{
		parent.setIFrameSize();
	}
</script>
		
</body>
</html>