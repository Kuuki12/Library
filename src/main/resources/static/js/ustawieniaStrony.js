/**
 * 
 */

function url(){
	return "";
}

function bladWyboru(){
	alert("Nic nie wybrałeś");
}

function validationSearch(){
	
	var title = document.getElementById("title").value;
	var isbn = document.getElementById("isbn").value;
	var yearofrelease = document.getElementById("yearofrelease").value;
	var firstname = document.getElementById("firstname").value;
	var lastname = document.getElementById("lastname").value;
	var namepublisher = document.getElementById("namepublisher").value;
	
	if(!title && !isbn && !yearofrelease && !firstname && !lastname && !namepublisher){
		alert("Wypełnij chociaż jedno pole");
		return false;
	}
	return true;	
}

function validationSingUp(){
	
	var mail = document.getElementById("mail").value;
	var password = document.getElementById("password").value;
	var firstname = document.getElementById("firstname").value;
	var lastname = document.getElementById("lastname").value;
	
	if(!mail || !password || !firstname || !lastname){
		alert("Wypełnij wszystkie pola");
		return false;
	}
	return true;	
}