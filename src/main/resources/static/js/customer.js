/**
 * 
 */
	function ajaxPost(){
		
		var firstname = $('#firstname').val();
		var lastname = $('#lastname').val();
		
		var customer = {};
		customer.firstname = $('#firstname').text();
		customer.lastname = $('#lastname').text();
		
		$.ajax({
			contentType: 'application/json',
			url : '/searchBook',
			type : 'POST',
			dataType: 'json',
			data : JSON.stringify(customer),
			success : function(result){
				
				alert("success");
			},
			error : function(e){
				alert("blad "+e);
			}
		});
	}	