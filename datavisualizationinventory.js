google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {

	var jsonData = JSON.parse(obj); 
	
	// Create the data table.
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'ProductName');
	data.addColumn('number', 'TotalAvailability');
	
	for (var key in jsonData) {
	   var value = jsonData[key];
	   data.addRow([key, value]);
	}	

	// Set chart options
	var options = {
			'title':'Inventory Report : Product Name vs Availability',
			'width':1000,
			'height':1000
	};

	// Instantiate and draw our chart, passing in some options.
	var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
	chart.draw(data, options);
}