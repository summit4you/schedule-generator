<html>
<head>


</head>
<body>

<form action="{MASTERSERVLET}" method="post">
<table align="center" border="0" style="borde-collapse:collapse">
<tr>
<td>#search_type#</td>
<td>
<select name="type">
<option value="student">#search_student#</option>
<option value="educator">#search_educator#</option>
</select>
</td>
</tr>
<tr>
<td>#first_name#</td><td>#surname#</td>
</tr>
<tr>
<td><input type="text" name="firstname"></td><td><input type="text" name="lastname"></td>
</tr>
<tr>
<td colspan="2" align="center">
<input type="submit">
</td>
</tr>
</table>
</form>

<form action="{MASTERSERVLET}" method="post">
<table align="center" border="0" style="borde-collapse:collapse">
<tr>
<td>#search_room#</td>
</tr>
<tr>
<td><input type="text" name="roomnumber"></td>
</tr>
<tr>
<td align="center">
<input type="submit">
</td>
</tr>
</table>
<input type="hidden" name="type" value="room">
</form>

{CALENDAR}

</body>
</html>