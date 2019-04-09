function checkInput(form) {

        var illegalChars = /\W/; // allow letters, numbers, and underscores

	//alert("** FORM = " + form.actiontype.value  );

 	if (form.changedesc.value == "") {
                form.changedesc.style.background = 'Yellow';
                alert("Please enter your Email Subject!");
                form.changedesc.focus();
                return false ;
        } else {
                form.changedesc.style.background = 'White';
        }

 	if (form.teamtrack.value == "") {
                form.teamtrack.style.background = 'Yellow';
                alert("Please enter your TeamTrack Number!");
                form.teamtrack.focus();
                return false ;
        } else {
                form.teamtrack.style.background = 'White';
        }

        if (form.fromdate.value == "") {
                form.fromdate.style.background = 'Yellow';
                alert("Please enter your Start Date!");
                form.fromdate.focus();
                return false ;
        } else {
                form.fromdate.style.background = 'White';
        }

        if (form.todate.value == "") {
                form.todate.style.background = 'Yellow';
                alert("Please enter your End Date!");
                form.todate.focus();
                return false ;
        } else {
                form.todate.style.background = 'White';
        }

     return true;


}
