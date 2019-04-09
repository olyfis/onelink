// A $( document ).ready() block.
$( document ).ready(function() {
    console.log( "ready!" );
    $.ajax({
    	type: 'Get',
        //url: 'http://cvyhj3a27:8181/webreport/getchart?cType=LHP',
    	
    	
        url: 'http://localhost:8181/webreport/getchart?cType=LHP',
        
        dataType: "json",
        success: function(data) {
        	console.log(data);
        	var os_arr = [];
        	var mi_arr = [];
        	var dr_arr = [];
        	var ca_arr = [];
        	
        	
        	for (var i in data) {
        		console.log("missinginfo:" + data[i].missinginfo);
        		console.log("ordsubmit:" + data[i].ordsubmit);
        		console.log("docreceived:" + data[i].docreceived);
        		console.log("creditapproval:" + data[i].creditapproval);
        		
        		
        		os_arr.push(data[i].ordsubmit)
        		mi_arr.push(data[i].missinginfo)
        		dr_arr.push(data[i].docreceived)
        		ca_arr.push(data[i].creditapproval)
        		
        		
        	}
        	var os = os_arr[0];
        	var dr = dr_arr[0];

        	var config = {
        			type: 'bar',
            	    
        			
                    data: {
            	    	//labels: ["Pending", "Released"],
            	    	
            	    	datasets: [	
            	            {
            	              label: "Order Submitted",
            	              backgroundColor: "#3e95cd",
            	             // scaleOverride : true,
                              //scaleSteps : 100,
                              //scaleStepWidth : 10,
                              scaleStartValue : 0,
            	              data: [os_arr[0] ]
            	            }, {
            	              label: "Docs Received",
            	              backgroundColor: "#8e5ea2",
            	               
            	              data: [dr_arr[0]]
            	            }, {
            	              label: "Docs Missing Info",
            	              backgroundColor: "#3cba9f",
            	               
            	              data: [mi_arr[0]]
            	            }, {
            	              label: "Need Credit Approval",
            	              backgroundColor: "#c45850",
            	               
            	              data: [ca_arr[0]]
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
                            	title: "Dollars",
                        		titleFontSize: 16,
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
                            text: 'Leases in House - Processing Status'
                        }
                    }
        	
        	
        	
        	}
        	  var ctx = document.getElementById("mycanvasLHP").getContext("2d");
              new Chart(ctx, config);
        	
        	console.log("************** OS: " + os_arr[0] );
        	console.log("************** DR: " + dr_arr[0] );
        }, // end ajax success
        error:	function(data) {
        	console.log(data);
        },  // end ajax error
        
    }); // end ajax
  
});