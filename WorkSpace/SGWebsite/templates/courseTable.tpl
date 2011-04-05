<html>
<head>
		
	<style type="text/css" title="currentStyle">
		@import "../js/datatables/css/demo_page.css";
		@import "../js/datatables/css/demo_table.css";
		@import "../js/datatables/css/jquery-ui-1.7.2.custom.css";
		.ui-tabs .ui-tabs-panel { padding: 10px }
	</style>
	<script type="text/javascript" language="javascript" src="../js/jquery.js"></script>
	<script type="text/javascript" language="javascript" src="../js/datatables/jquery.dataTables.js"></script>
	<script type="text/javascript" language="javascript" src="../js/datatables/jquery-ui-tabs.js"></script>

</head>
<body>

<div id="demo">
	<div id="tabs">
		<ul>
			{TABS}
		</ul>
{TABLE}
	</div>
</div>

<script>
	function(name)
	{
		var nCloneTh = document.createElement( 'th' );
		var nCloneTd = document.createElement( 'td' );
		nCloneTd.innerHTML = '<img src="../js/datatables/images/details_open.png">';
		nCloneTd.className = "center";
				
		$('#'+name+' thead tr').each( function () {
			this.insertBefore( nCloneTh, this.childNodes[0] );
		} );
				
		$('#'+name+' tbody tr').each( function () {
			this.insertBefore(  nCloneTd.cloneNode( true ), this.childNodes[0] );
		} );
				
		/*
		 * Initialse DataTables, with no sorting on the 'details' column
		 */
		var oTable = $('#'+name).dataTable( {
			"aoColumnDefs": [
				{ "bSortable": false, "aTargets": [ 0 ] }
			],
			"aaSorting": [[1, 'asc']]
		});
				
		/* Add event listener for opening and closing details
		 * Note that the indicator for showing which row is open is not controlled by DataTables,
		 * rather it is done here
		 */
		$('#'+name+' tbody td img').live('click', function () {
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
			}
		} );
	}
	
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
			"aaSorting": [[1, 'asc']]
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
			}
		} );
	} );
</script>

</body>
</html>