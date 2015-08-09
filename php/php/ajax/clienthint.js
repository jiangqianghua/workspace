var xmlHttp ;
function GetXmlHttpObject()
{
	var xmlHttp = null ;
	try
	{
		xmlHttp = new XMLHttpRequest();
	}
	catch(e)
	{
		try
		{
			xmlHttp = new ActiveObject("Msxml2.XMLHTTP");
		}
		catch(e)
		{
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	return xmlHttp ;
}	

function showHint(str)
{
//	alert(str);
//	document.getElementById("txtHint").innerHTML = str ;
	if(str == null || str.lentgh == 0)
	{
		document.getElementById("txtHint").innerHTML = "123" ;
		return ;
	}
	xmlHttp = GetXmlHttpObject();
	if(xmlHttp == null)
	{
		alert("Browser does not support Http Request");
		return ;
	}
	var url = "gethint.php" ;
	url = url+"?q="+str ;
	url = url+"&sid="+Math.random();
	xmlHttp.onreadystatechange = stateChanged ;
	xmlHttp.open("GET" , url , true );
	xmlHttp.send(null);
}
function stateChanged()
{
	if (xmlHttp.readyState==4 || xmlHttp.readyState=="complete")
	{ 

		document.getElementById("txtHint").innerHTML=xmlHttp.responseText 
	}	 
}
