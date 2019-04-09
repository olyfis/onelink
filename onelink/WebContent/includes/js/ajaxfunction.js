//Browser Support Code
function ajaxFunction(){
        var ajaxRequest;  // The variable that makes Ajax possible!
	//alert("Getting Hosts: Please be patient.");

        try{
                // Opera 8.0+, Firefox, Safari
                ajaxRequest = new XMLHttpRequest();
        } catch (e){
                // Internet Explorer Browsers
                try{
                        ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e) {
                        try{
                                ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
                        } catch (e){
                                // Something went wrong
                                alert("Your browser broke!");
                                return false;
                        }
                }
        }
        // Create a function that will receive data sent from the server
        ajaxRequest.onreadystatechange = function(){
                if(ajaxRequest.readyState == 4){
                        var ajaxDisplay = document.getElementById('ajaxDiv');
                        ajaxDisplay.innerHTML = ajaxRequest.responseText;
                // document.actionform.actiontypeprogram.value = ajaxRequest.responseText;

                }
        }

//      var atype = document.actionform.getElementById('actiontype').value;
        //var atype = document.actionform.actiontype.value;

        //var queryString = "?atype=" + atype + "&wpm=" + wpm + "&ex=" + ex;
        //var queryString = "?atype=" + atype;
        var queryString = "?atype=20" ;
        //alert(queryString);
        ajaxRequest.open("GET", "/WebReports/nbva?id=101-0013105-008" , true);
        ajaxRequest.send(null);
}
