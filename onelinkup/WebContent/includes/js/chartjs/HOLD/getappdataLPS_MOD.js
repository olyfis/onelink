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
        	 
 
        	 var color_arr = ["#3effcd", "#08896d","#dddd9f","#3c0889","#c45850", "#dd5ea2","#fcba9f", "#bb9ccd", "#2effa3","#a00637","#ccc3f9", "#aac3f9",
        		"#b9e0aa", "#92efef", "#92a4ef", "#ef92a3", "#efcd92", "#7a6159", "#022311", "#d4d6c2", "#f7ad85", "#d5d4db"  ];
        	 
        	var data1 = [];
        	var lps25_arr = [];
        	var lps50_arr = [];
        	var lps75_arr = [];
        	var lps100_arr = [];
        	
        	
        	for (var i in data) {
        		console.log( "LPS25=" + data[i].lps25 );
        		console.log( "LPS50=" + data[i].lps50 );       		
        		console.log( "LPS75=" + data[i].lps75 );
        		console.log( "LPS100=" + data[i].lps100 );
        		
        		lps25_arr.push(data[i].lps25)
        		lps50_arr.push(data[i].lps50)
        		lps75_arr.push(data[i].lps75)
        		lps100_arr.push(data[i].lps100)
        		
        		
	
        	}
       	 
        	//percent_arr.sort();
        	var config = {
        			type: 'bar',
            	    
        			
                    data: {
            	    	//labels: ["Pending", "Released"],
            	    	
            	    	datasets: [	
            	            {
            	              label: ">25%",
            	              backgroundColor: "#3e95cd",
            	             // scaleOverride : true,
                              //scaleSteps : 100,
                              //scaleStepWidth : 10,
                              scaleStartValue : 0,
            	              data: [lps25_arr[0] ]
            	            }, {
            	              label: "26% to 50",
            	              backgroundColor: "#8e5ea2",
            	               
            	              data: [lps50_arr[0]]
            	            }, {
            	              label: "51% to 75",
            	              backgroundColor: "#3cba9f",
            	               
            	              data: [lps75_arr[0]]
            	            }, {
            	              label: "76% to 100%",
            	              backgroundColor: "#c45850",
            	               
            	              data: [lps100_arr[0]]
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
                            text: 'Leases Processed - Shipping Status'
                        }
                    }
        	
        	
        	
        	}
        	
        	  var ctx = document.getElementById("mycanvasLPS").getContext("2d");
              new Chart(ctx, config);
        	
     
        	
        }, // end ajax success
        
      
        error:	function(data) {
        	console.log(data);
        },  // end ajax error
        
    }); // end ajax

});