// A $( document ).ready() block.
	
	
	
$( document ).ready(function() {
	
	//var host = window.location.hostname;
    //console.log( "Host ready!" + host );
    $.ajax({
    	
    
    	
    	type: 'Get',
         //url: 'http://cvyhj3a27:8181/webreport/getchart?cType=LPS',
    	
    	
        
    	 url: 'http://localhost:8181/webreport/getchart?cType=LPS',
        dataType: "json",
        success: function(data) {
        	console.log(data);
        	//console.log( "Host ready!" + host );
        	 
        	var percent_arr = [];
        	var count_arr = [];	 
        	 var color_arr = ["#3effcd", "#08896d","#dddd9f","#3c0889","#c45850", "#dd5ea2","#fcba9f", "#bb9ccd", "#2effa3","#a00637","#ccc3f9", "#aac3f9",
        		"#b9e0aa", "#92efef", "#92a4ef", "#ef92a3", "#efcd92", "#7a6159", "#022311", "#d4d6c2", "#f7ad85", "#d5d4db"  ];
        	 
        	var data1 = [];
        	
        	for (var i in data) {
        		console.log( "Owner=" + data[i].percent );
        		console.log( "Count=" + data[i].count );
        		percent_arr.push(data[i].percent);
        		count_arr.push(data[i].count);	
	
        	}
         	 
        	//percent_arr.sort();
        	var config = {
        			 type: 'pie', 
        		    data: {
        		      labels:  percent_arr,
        		      datasets: [
        		        {
        		        	backgroundColor:color_arr,
        		           
        		          data: count_arr,
        		        
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
                                        steps: 1,
                                        stepValue: 1,
                                        //max: 35
                                    }
                                }]
                        },
                        title: {
                            display: true,
                            text: 'Leases Processed - Shipping Status'
                        }
                    }
        	
        	
        	
        	}
        	  var ctx = document.getElementById("mycanvasLPS").getContext("2d");
              new Chart(ctx, config);
        	
        	//console.log("************** OS: " + os_arr[0] );
        	//console.log("************** DR: " + dr_arr[0] );
        }, // end ajax success
        error:	function(data) {
        	console.log(data);
        },  // end ajax error
        
    }); // end ajax
 
    function addData() {
  	  Chart.data.datasets[0].data[2] = 12;
  	  Chart.update();
    }
    
    
  
});