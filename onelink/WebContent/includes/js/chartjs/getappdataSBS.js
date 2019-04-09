// A $( document ).ready() block.
$( document ).ready(function() {
    console.log( "ready!" );
    $.ajax({
    	type: 'Get',
        //url: 'http://cvyhj3a27:8181/webreport/getchart?cType=SBS',
        url: 'http://localhost:8181/webreport/getchart?cType=SBS',
        
        dataType: "json",
        success: function(data) {
        	console.log(data);
        	var pending_arr = [];
        	var released_arr = [];
        	
        	for (var i in data) {
        		console.log("Pending:" + data[i].pending);
        		console.log("Released:" + data[i].release);
        		pending_arr.push(data[i].pending)
        		released_arr.push(data[i].release)
        	}
        	var p = pending_arr[0];
        	var r = released_arr[0];

        	var config = {
        			type: 'bar',
            	    
        			
                    data: {
            	    	//labels: ["Pending", "Released"],
            	    	
            	    	datasets: [	
            	            {
            	              label: "Pending Booking",
            	              backgroundColor: "#3e95cd",
            	              scaleOverride : true,
                              scaleSteps : 1,
                              scaleStepWidth : 1,
                              scaleStartValue : 0,
            	              data: [p ]
            	            }, {
            	              label: "Order Released",
            	              backgroundColor: "#8e5ea2",
            	               
            	              data: [r]
            	            }
            	          ]
            	    },
                    
                    
            	    options: {
                        responsive: true,
                        legend: {
                            position: 'bottom',
                        },
                        hover: {
                            mode: 'label'
                        },
                        scales: {
                            xAxes: [{
                                    display: true,
                                    scaleLabel: {
                                        display: true,
                                        //labelString: 'Month'
                                    }
                                }],
                            yAxes: [{
                                    display: true,
                                    ticks: {
                                        beginAtZero: true,
                                        steps: 10,
                                        stepValue: 5,
                                        max: 75
                                    }
                                }]
                        },
                        title: {
                            display: true,
                            text: 'Shipped By Status - Orders Released > 95% '
                        }
                    }
        	
        	
        	
        	}
        	  var ctx = document.getElementById("mycanvasSBS").getContext("2d");
              new Chart(ctx, config);
        	
        	console.log("************** Pending: " + pending_arr[0] );
        	console.log("************** Released: " + released_arr[0] );
        }, // end ajax success
        error:	function(data) {
        	console.log(data);
        },  // end ajax error
        
    }); // end ajax
  
});