<html>
<head>

	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/thickbox/thickbox.js"></script>
	<link rel="stylesheet" href="js/thickbox/thickbox.css" type="text/css" media="screen" />
	<style type="text/css" title="currentStyle">
		@import "js/datatables/css/demo_page.css";
		@import "js/datatables/css/demo_table.css";
		@import "js/datatables/css/jquery-ui-1.8.4.custom.css";
			.ui-tabs .ui-tabs-panel { padding: 10px }
	</style>
	<script type="text/javascript" language="javascript" src="js/datatables/jquery.dataTables.js"></script>
	<script type="text/javascript" language="javascript" src="js/datatables/jquery.jeditable.js"></script>
	<script type="text/javascript" language="javascript" src="js/datatables/jquery-ui-tabs.js"></script>

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
					<form method="get" action="{MASTERSERVLET}">
						<td>
							<select name="edit">{SUBOURCEOPTIONS}</select>
						</td>
						<td>
							<input type="submit" value="##Edit_Subcourse##"/>
						</td>
					</form>
					</tr>
			</table>
		</td>
	</tr>
</table>

</body>
</html>