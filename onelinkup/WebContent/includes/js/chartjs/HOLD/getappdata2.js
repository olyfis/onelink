// A $( document ).ready() block.
$( document ).ready(function() {
    console.log( "ready!" );
    $.ajax({
    	type: 'Get',
        url: 'http://localhost:8181/webreport/getchart',
        dataType: "json",
        success: function(data) {
        	console.log(data);
        	var pending = [];
        	var release = [];
        	
        	for (var i in data) {
        		console.log("Pending:" + data[i].pending);
        		console.log("Release:" + data[i].release);
        		pending.push(data[i].pending)
        		release.push(data[i].release)
        	}
 
        	var chartdata = {   		   		 
        		labels: ["Pending", "Released"],
        		data: {
        			type: 'bar', 
        			datasets: [{
        		    	  //labels: ["Pending", "Released"],
        		        backgroundColor: ["#3e95cd", "#8e5ea2"],
        		        //data: [release[0], pending[0]]
        			
        		        data: pending[0]
        		      }]			
        		},
        		options: {
        		      title: {
        		        display: true,
        		        text: '> 95% Shipped By Status'
        		      }
        		    }
        		}
        	var ctx = $("#mycanvas");
        	var barGraph= new Chart(ctx, {  
        		type: 'bar',
        		data: chartdata	
        	})
        	console.log("************** Pending: " + pending[0] );
        	
        },
        error:	function(data) {
        	console.log(data);
        },   
    }); 
});