// A $( document ).ready() block.

var host = location.hostname;


$( document ).ready(function() {
    console.log( host + " Host ready!"  );
    $.ajax({
    	
     
    	
    	type: 'Get',
         //url: 'http://cvyhj3a27:8181/webreport/getchart?cType=SBO',
        
    	  url: 'http://localhost:8181/webreport/getchart?cType=SBO',
        dataType: "json",
        success: function(data) {
        	console.log(data);
       
        	 
        	var owner_arr = [];
        	var count_arr = [];	 
        	 var color_arr = ["#3effcd", "#08896d","#dddd9f","#3c0889","#c45850", "#dd5ea2","#fcba9f", "#bb9ccd", "#2effa3","#a00637","#ccc3f9", "#aac3f9" ];
        	 
        	var data1 = [];
        	
        	for (var i in data) {
        		console.log( "Owner=" + data[i].appOwner );
        		console.log( "Count=" + data[i].count );
        		owner_arr.push(data[i].appOwner);
        		count_arr.push(data[i].count);	
	
        	}
         	 
    
        	var config = {
        			 type: 'bar', 
        		    data: {
        		      labels:  owner_arr,
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
                            text: '> 95% Shipped by App Owner'
                        }
                    }
        	
        	
        	
        	}
        	  var ctx = document.getElementById("mycanvasSBO").getContext("2d");
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