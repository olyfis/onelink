var myLineChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: labels,
        datasets: [{
            label: "Total Commission",
            data: d[0],
            backgroundColor: background_colors,
            hoverBackgroundColor: hover_background_colors,
            //yAxyesID: "id1"
            yAxisID: "id1" // typo in property name.
        },{
            label: "# of Commissions",
            data: d[1],
            type: 'line',
            //yAxesID: "id2"
            yAxisID: "id2" // typo in property name.
        }],
    },
    options: {
        responsive: true,
        elements: {
            line :{
                fill: false
            }
        },
        title: {
            display: true,
            position: 'bottom',
            text: 'Commissions Paid',
            fontSize: 14
        },
        //scales: [{
        scales: { // Shouldn't be an array.
           yAxes: [{
               display: true,
               position: 'left',
               type: "linear",
                scaleLabel: {
                    display: true,
                    labelString: 'USD',
                    beginAtZero: true,
                },
               //yAxisID: "id1"
               id: "id1" // incorrect property name.
            },{
               scaleLabel: {
                    display: true,
                    labelString: 'Commissions',
                    beginAtZero: true,
                },
               //display: false,
               display: true, // Hopefully don't have to explain this one.
               type: "linear",
               position:"right",
               gridLines: {
                   display: false
               },
               //yAxisID: "id2"
               id: "id2" // incorrect property name.
            }]
        //}]
        } // Shouldn't be an array.
    }
});