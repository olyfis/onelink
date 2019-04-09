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
        	
        	var ca_val = 0;
        	var os_val = 0;
        	var dr_val = 0;
        	var mi_val = 0;
        	
        	for (var i in data) {
        		/*
        		console.log("missinginfo:" + data[i].missinginfo);
        		console.log("ordsubmit:" + data[i].ordsubmit);
        		console.log("docreceived:" + data[i].docreceived);
        		console.log("creditapproval:" + data[i].creditapproval);
        		*/
        		
        		os_arr.push(data[i].ordsubmit)
        		mi_arr.push(data[i].missinginfo)
        		dr_arr.push(data[i].docreceived)
        		ca_arr.push(data[i].creditapproval)
        		
        		
        		os_val = data[i].Order_Submitted;
        		dr_val = data[i].Docs_Received;
        		mi_val = data[i].Doc_Missing_Info;
        		ca_val = data[i].Need_Credit_Approval;
        		console.log("^^^^^ os:" + os_val);
        		console.log("^^^^^ dr:" + dr_val);
        		console.log("^^^^^ mi:" + mi_val);
        		//console.log("********** DR:" + data[i].Docs_Received);
        		//console.log("***********OS:" + data[i].Order_Submitted);
        		
        	}
        	var os = os_arr[0];
        	var dr = dr_arr[0];
        	 var color_arr = ["#08896d", "#ba1ece","#3c0889","#ad3432", "#3effcd","#fcba9f", "#bb9ccd", "#2effa3","#a00637","#ccc3f9", "#aac3f9",
         		"#b9e0aa", "#92efef", "#92a4ef", "#ef92a3", "#efcd92", "#7a6159", "#022311", "#d4d6c2", "#f7ad85", "#d5d4db"  ];
        	 var color_arr2 = ["#3effcd", "#e8aeef","#aa74e8","#e87d74", "#aac3f9",
          		"#b9e0aa", "#92efef", "#92a4ef", "#ef92a3", "#efcd92", "#7a6159", "#022311", "#d4d6c2", "#f7ad85", "#d5d4db"  ];
        	 
        	 
/******************************************************************************************************************************************************************/
     var config = {
      	  type: 'bar',
    	  data: {
    	    labels: ['Submitted', 'Docs Received', 'Docs Missing Info', 'Need Credit Approval'],
    	    
    	    /*
    	    datasets: [{
    	      label: 'Pending',
    	      yAxisID: 'A',
    	      data: [10, 96, 84, 76, 69]
    	    }, {
    	      label: 'Released',
    	      yAxisID: 'B',
    	      data: [2, 3, 5, 2, 3]
    	    }
    	    
    	    ]
    	    */
    	    
    	    
    	    datasets: [	
    	    	/*
	            {
	              label: "Order Submitted",
	              backgroundColor: "#3e95cd",
	             // scaleOverride : true,
                  //scaleSteps : 100,
                  //scaleStepWidth : 10,
                  scaleStartValue : 0,
                  yAxisID: 'A',
	              data: [os_arr[0] ]
	            }, {
	              label: "Docs Received",
	              backgroundColor: "#8e5ea2",
	             //yAxisID: 'B',
	              data: [dr_arr[0]]
	            }, {
	              label: "Docs Missing Info",
	              backgroundColor: "#3cba9f",
	               
	              data: [mi_arr[0]]
	            }, {
	              label: "Need Credit Approval",
	              backgroundColor: "#c45850",
	               
	              data: [ca_arr[0]]
	            },
	            
	            */
	            {
	            	backgroundColor:color_arr,
	            	label: 'Dollars',
		      	      yAxisID: 'A',
		      	      
		      	      
		      	      
		      	      
		      	      data: [os_arr[0], dr_arr[0], mi_arr[0], ca_arr[0]]	
	            	
	            	
	            },
	            
	            
	            
	            {
	            	backgroundColor:color_arr2,	
	      	      label: 'Count',
	      	      yAxisID: 'B',
	      	      data: [os_val, dr_val, mi_val, ca_val]
	      	    }
	            
	            
	            
	          ]
    	    
    	    
    	  },
    	  
    	
    	  options: {
    		  title: {
                  display: true,
                  text: 'Leases in House - Processing Status'
              },
    	    scales: {
    	      yAxes: [
    	    	  {
    	    	
    	    		  id: 'A',
    	    			title: "Dollars",
                		titleFontSize: 16,
                            display: true,
                            ticks: {
                                beginAtZero: true,
                           
                                callback: function(value, index, values) {
                                    if(parseInt(value) >= 1000){
                                      return '$' + value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                                    } else {
                                      return '$' + value;
                                    }
                                },
                                //steps: 10,
                                //stepValue: 5,
                                //max: 35
                            }
    	    	  
    	      }, {
    	        id: 'B',
    	        title: "Count",
    	        type: 'linear',
    	        position: 'right',
    					ticks : {
    	        	max: 60,
    	          min: 0,
    	          stepSize: 20,
    	          
    	          
    	          
    	        }
    	      }]
    	  
    	    }
    	  
    	  }
        	
     }      	
/******************************************************************************************************************************************************************/
        	
        	  var ctx = document.getElementById("mycanvasLHP").getContext("2d");
              new Chart(ctx, config);
        	
        	//console.log("************** OS: " + os_arr[0] );
        	//console.log("************** DR: " + dr_arr[0] );
        }, // end ajax success
        error:	function(data) {
        	console.log(data);
        },  // end ajax error
        
    }); // end ajax
  
});