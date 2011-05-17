<html>
<head>


</head>
<body>
<table align="left">
	<tr>
		<td>
			{ACCOUNTNAME}
		</td>
	</tr>
	<tr>
		<td>
			<form method="post" action="{MASTERSERVLET}">
				<table align="left" border="0" style="borde-collapse:collapse">
					<tr>
						<td align="center" colspan="2">
							##change_password##
						</td>
					</tr>
					<tr>
						<td>
							##new_password##
						</td>
						<td>
							<input type="text" name="pass1"></input>
						</td>
					</tr>
					<tr>
						<td>
							##confirm_password##
						</td>
						<td>
							<input type="text" name="pass2"></input>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="left">
							<input type="submit"></input>
						</td>
					</tr>
				</table>
			</form>
		</td>
	</tr>

	<tr height="90">
		<td style="vertical-align:top">
			{PASSRESULT}
		</td>
	</tr>

	<tr>
		<td>
			{CURRENTLANGUAGE}
		<td>
	</tr>

	<tr>
		<td>
			<form method="post" action="{MASTERSERVLET}" >
				<table align="left" border="0" style="borde-collapse:collapse">
					<tr>
						<td align="center">
							##change_language##
						</td>
						<td>
							<select name="lang">
							{LANGUAGEOPTIONS}
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="left">
							<input type="submit"></input>
						</td>
					</tr>
				</table>
			</form>	
		</td>
	</tr>
	<tr height="90">
		<td style="vertical-align:top">
			{LANGRESULT}
		</td>
	</tr>
</table>

</body>
</html>