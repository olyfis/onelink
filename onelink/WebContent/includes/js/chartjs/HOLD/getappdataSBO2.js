// A $( document ).ready() block.
$( document ).ready(function() {
    console.log( "ready!" );
    $.ajax({
    	type: 'Get',
        //url: 'http://cvyhj3a27:8181/webreport/getchart?cType=SBO',
        
    	url: 'http://localhost:8181/webreport/getchart?cType=SBO',
        dataType: "json",
        success: function(data) {
        	console.log(data);
       
        	var owner_arr = [];
        	var count_arr = [];	 
        	for (var i in data) {
        		console.log( "Owner=" + data[i].appOwner );
        		console.log( "Count=" + data[i].count );
        		owner_arr.push(data[i].appOwner)
        		count_arr.push(data[i].count)
        		
        	
        		
        	}
         	 
    
        	var config = {
        			type: 'bar',
            	    
        			
                    data: {
            	    	//labels: ["Pending", "Released"],
            	    	
            	    	datasets: [	
            	            {
            	              label: owner_arr[0],
            	              backgroundColor: "#3e95cd",
            	             // scaleOverride : true,
                              //scaleSteps : 100,
                              //scaleStepWidth : 10,
                              scaleStartValue : 0,
            	              data: [count_arr[0] ]
            	            }, {
            	              label: owner_arr[1],
            	              backgroundColor: "#8e5ea2",
            	               
            	              data: [count_arr[1]]
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
                                        //steps: 10,
                                        //stepValue: 5,
                                        //max: 35
                                    }
                                }]
                        },
                        title: {
                            display: true,
                            text: '> 95% Shipped by App Owner'
                        }
                    }
        	
        	
        	
        	}
        	  var ctx = document.getElementById("mycanvas").getContext("2d");
              new Chart(ctx, config);
        	
        	//console.log("************** OS: " + os_arr[0] );
        	//console.log("************** DR: " + dr_arr[0] );
        }, // end ajax success
        error:	function(data) {
        	console.log(data);
        },  // end ajax error
        
    }); // end ajax
  
});