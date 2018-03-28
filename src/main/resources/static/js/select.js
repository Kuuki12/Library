/**
 * 
 */


	$('.wypozycz').on('click', function(e){
	    var value = $("#table tr.selected td:first").html();
	    window.location = "http://localhost:8080/book/select?id="+value;
	});
	
	function odrzuc(value, url){
	    //window.location = "http://localhost:8080/book/takeBackBook?accept=false&idBook="+value;
		var hostname = window.location.hostname;
	    window.location = "http://"+ hostname +":8080/"+url+"book/takeBackBook?accept=false&idBook="+value;
	}
	
	function akceptuj(value, url){
		//window.location = "http://localhost:8080/book/takeBackBook?accept=true&idBook="+value;
		var hostname = window.location.hostname;
		window.location = "http://"+ hostname +":8080/"+url+"book/takeBackBook?accept=true&idBook="+value;
	}
	
	function oddaj(value, url){
		window.location = "http://localhost:8080/book/return?idBook="+value;
	}
	
	function przedluz(value, url){
		window.location = "http://localhost:8080/book/renew?idBook="+value;
	}
	
	