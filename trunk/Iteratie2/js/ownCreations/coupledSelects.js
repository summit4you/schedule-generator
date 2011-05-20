var indep;
var dep;
var texts;
var values;

function initCoupledLists(indepID,depID,dependantTexts,dependantValues)
{
	texts=dependantTexts;
	values=dependantValues;
	indep=document.getElementById(indepID);
	dep=document.getElementById(depID);
	
	$('#'+indepID).change(function() {
		setDep();
	});

	setDep();
}

function setDep()
{	
	removeAllOptions();
	var index=indep.selectedIndex;
	for (var i=0;i<texts[index].length;i++)
	{
		var optn = document.createElement("OPTION");
		optn.text = texts[index][i];
		optn.value = values[index][i];
		dep.options.add(optn); 
	}
}

function removeAllOptions()
{
	for(var i=dep.options.length-1;i>=0;i--)
	{
		dep.remove(i);
	}
}