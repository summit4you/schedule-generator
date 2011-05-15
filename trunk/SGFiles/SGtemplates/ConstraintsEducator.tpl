<html>
<head>

	<link rel="stylesheet" href="../js/slimpicker/pagestyle.css" media="screen, projection" />
	<link rel="stylesheet" href="../js/slimpicker/slimpicker.css" media="screen, projection" />
	<script src="../js/slimpicker/mootools-1.2.4-core-yc.js"></script>
	<script src="../js/slimpicker/mootools-1.2.4.4-more-yc.js"></script>
	<script src="../js/slimpicker/slimpicker.js"></script>
	
	<script type="text/javascript" src="../js/jquery.js"></script>
	
</head>
<body>

<table border="1" style="border-collapse:collapse">
	<tr>
		<td>
			<form method="post" action="{MASTERSERVLET}&change=single">
				<table border="1" style="border-collapse:collapse">
					<tr>
						<td colspan="4">
							##add_single_day##
						</td>
					</tr>
					<tr>
						<td>
							<input class="slimpicker" name="inputDate" id="inputDate" type="text" value="{DATE}" autocomplete="off" alt="{format:'{DATEFORMAT}'}" />
						</td>
						<td>
							##start_hour##<input type="text" name="start" size="4">
						</td>
						<td>
							##stop hour<input type="text" name="stop" size="4">
						</td>
						<td>
							<input type="submit">
						</td>
					</tr>
				</table>
			</form>
		</td>
	</tr>
	<tr>
		<td>
			<form method="post" action="{MASTERSERVLET}&change=week">
				<table border="1" style="border-collapse:collapse">
					<tr>
						<td colspan="4">
							##add_weekly_day##
						</td>
					</tr>
					<tr>
						<td>
							<select name="day">
								<option value="mon">##monday##</option>
								<option value="tue">##tuesday##</option>
								<option value="wed">##wednesday##</option>
								<option value="thu">##thursday##</option>
								<option value="fri">##friday##</option>
								<option value="sat">##saturday##</option>
								<option value="sun">##sunday##</option>
							</select>
						</td>
						<td>
							##start week##<input type="text" name="startweek" size="2">
						</td>
						<td>
							##stop week##<input type="text" name="stopweek" size="2">
						</td>
						<td>
							
						</td>
					</tr>
					<tr>
						<td>
							
						</td>
						<td>
							##start hour##<input type="text" name="starthour" size="2">
						</td>
						<td>
							##stop hour##<input type="text" name="stophour" size="2">
						</td>
						<td>
							<input type="submit">
						</td>
					</tr>
				</table>
			</form>
		</td>
	</tr>
	<tr>
		<td>
			<form method="post" action="{MASTERSERVLET}&change=long">
				<table border="1" style="border-collapse:collapse">
					<tr>
						<td colspan="3">
							##add_long_period##
						</td>
					</tr>
					<tr>
						<td>
							##start date## <input class="slimpicker" name="startDate" id="inputDate" type="text" value="{DATE}" autocomplete="off" alt="{format:'{DATEFORMAT}'}" />
						</td>
						<td>
							##stop_date## <input class="slimpicker" name="stopDate" id="inputDate" type="text" value="{DATE}" autocomplete="off" alt="{format:'{DATEFORMAT}'}" />
						</td>
						<td>
							<input type="submit">
						</td>
					</tr>
				</table>
			</form>
		</td>
	</tr>
	<tr>
		<td>
			<iframe src="{CONSTRAINT}">
			##noiframe##
			</iframe>
		</td>
	</tr>
</table>
</body>
</html>