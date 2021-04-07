/**
 * StateFull GUI Plugin using jQuery
 * value1: int parameter Input
 * value1Sync: function parameter value1 synchronization callback
 * value2: double parameter Input
 * termination: function callback to terminate plugin
 */
function showChart(value1,value1Sync,value2,termination)
{
	//use jQuery to modify elements in the HTML-file of the plugin
    jQuery('#dialog').hide();
    //Update synced value1 in GUI Model
    value1Sync(99);
        
    var i = 0;
	//Use jQuery event handling
    jQuery('#myChart').click(function () {
    		//Async callback to sync value1
        value1Sync(i);
        i++;
        
        if(i > 5){
        		//Use termination Callback to end with the branch 'finished'
        		//And Provide the named ports in 'values'
        		termination({
        			branchName: 'finished',
        			values: {
        				'caluclated':i
        			}
        		});
        }
    });
	
	createChart();
    
}

function createChart()
{
    var pieData = [
        {
            value: 20,
            color:"#878BB6"
        },
        {
            value : 40,
            color : "#4ACAB4"
        },
        {
            value : 10,
            color : "#FF8153"
        },
        {
            value : 30,
            color : "#FFEA88"
        }
    ];
    // Get the context of the canvas element we want to select
    var countries= document.getElementById("myChart").getContext("2d");
    new Chart(countries).Pie(pieData);
    //alert("paint");
}