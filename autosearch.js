var req;
var isIE;
var completeField;
var completeTable;
var autoRow;

function init()
{
	completeField = document.getElementById("complete-field");
    completeTable = document.getElementById("complete-table");
    autoRow = document.getElementById("auto-row");
}

// Initiate request
function doCompletion()
{
	var url = "AutoCompleteServlet?action=complete&id="+escape(completeField.value)
	req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = callback;
    req.send(null);
}

// Define a request object
function initRequest()
{
	if (window.XMLHttpRequest) 
	{
        if (navigator.userAgent.indexOf('MSIE') != -1) 
        {
            isIE = true;
        }
        return new XMLHttpRequest();
    } 
	else if (window.ActiveXObject) 
	{
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

// Handle response
function callback(request)
{
	clearTable();
	
	// 4 means response from server is complete
	// Handler gets invoked multiple times, ignore the first one
	if (req.readyState == 4) 
	{
        if (req.status == 200) 
        {
            parseMessages(req.responseXML);
        }
    }
}

function clearTable() 
{
    if (completeTable.getElementsByTagName("tr").length > 0) 
    {
        completeTable.style.display = 'none';
        for (loop = completeTable.childNodes.length -1; loop >= 0 ; loop--) 
        {
            completeTable.removeChild(completeTable.childNodes[loop]);
        }
    }
}

function parseMessages(responseXML) {
    
    // no matches returned
    if (responseXML == null) 
    {
        return false;
    } 
    else 
    {
        var composers = responseXML.getElementsByTagName("composers")[0];

        if (composers.childNodes.length > 0) 
        {
            completeTable.setAttribute("bordercolor", "black");
            completeTable.setAttribute("border", "1");
    
            for (loop = 0; loop < composers.childNodes.length; loop++) 
            {
                var composer = composers.childNodes[loop];
                var composerName = composer.getElementsByTagName("name")[0];
                var composerId = composer.getElementsByTagName("id")[0];
                appendComposer(composerName.childNodes[0].nodeValue, composerId.childNodes[0].nodeValue);
            }
        }
    }
}

function appendComposer(composerName, composerId) {

    var row;
    var cell;
    var linkElement;
    
    if (isIE) 
    {
        completeTable.style.display = 'block';
        row = completeTable.insertRow(completeTable.rows.length);
        cell = row.insertCell(0);
    } 
    else 
    {
        completeTable.style.display = 'table';
        row = document.createElement("tr");
        cell = document.createElement("td");
        row.appendChild(cell);
        completeTable.appendChild(row);
    }

    cell.className = "popupCell";

    linkElement = document.createElement("a");
    linkElement.className = "popupItem";
    linkElement.setAttribute("href", "AutoCompleteServlet?action=lookup&id=" + composerId);
    linkElement.appendChild(document.createTextNode(composerName));
    cell.appendChild(linkElement);
}



