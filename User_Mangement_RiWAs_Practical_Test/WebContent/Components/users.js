 $(document).ready(function() 
{
	if ($("#alertSuccess").text().trim() == "") { 
		$("#alertSuccess").hide(); 
	 } 
	
	 $("#alertError").hide(); 
});

//Save Button
$(document).on("click", "#btnSave", function(event)
{ 
	// Clear alerts---------------------
	$("#alertSuccess").text(""); 
	$("#alertSuccess").hide(); 
	$("#alertError").text(""); 
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateItemForm(); 
	if (status != true) 
	{ 
		 $("#alertError").text(status); 
		 $("#alertError").show(); 
		 
		 return; 
	}
	
	// If valid------------------------
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT"; 
	 
	$.ajax( 
	{ 
		 url : "UsersAPI", 
		 type : type, 
		 data : $("#formUser").serialize(), 
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 onItemSaveComplete(response.responseText, status); 
		 } 
	 });

});

//On item save
function onItemSaveComplete(response, status)
{ 
	if (status == "success") 
	{ 
		 var resultSet = JSON.parse(response); 
		 
		 if (resultSet.status.trim() == "success") 
		 { 
			 $("#alertSuccess").text("Successfully saved."); 
			 $("#alertSuccess").show(); 
			 
			 $("#divUsersGrid").html(resultSet.data); 
		 } else if (resultSet.status.trim() == "error") 
		 { 
			 $("#alertError").text(resultSet.data); 
			 $("#alertError").show(); 
		 } 
	 } else if (status == "error") 
	 { 
		 $("#alertError").text("Error while saving."); 
		 $("#alertError").show(); 
	 } else
	 { 
		 $("#alertError").text("Unknown error while saving.."); 
		 $("#alertError").show(); 
	 }
	
	 $("#hidItemIDSave").val(""); 
	 $("#formUser")[0].reset(); 
}


//Update Button 
$(document).on("click", ".btnUpdate", function(event)
{ 	
	$("#hidItemIDSave").val($(this).data("itemid")); 
	$("#name").val($(this).closest("tr").find('td:eq(0)').text()); 
	$("#email").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#userType").val($(this).closest("tr").find('td:eq(2)').text()); 
	$("#pw").val($(this).closest("tr").find('td:eq(3)').text()); 
});

//CLIENT-MODEL================================================================
function validateItemForm() 
{ 
	// USER NAME
	if ($("#name").val().trim() == "") 
	{ 
		return "Insert User Name."; 
	} 
	
	// USER EMAIL
	if ($("#email").val().trim() == "") 
	{ 
		return "Insert User Email."; 
	} 
	
	//Validate Email
	var emailPattern = /^[^\s@]+@[^\s@]+$/;
	
	if (!emailPattern.test($("#email").val()) ) {
		return "Must be a valid email address.";
	} 
	
	// USER TYPE 
	if ($("#userType").val().trim() == "") 
	{ 
		return "Insert User Type."; 
	} 
	
	// PASSWORD
	if ($("#pw").val().trim() == "") 
	{ 
		return "Insert User Password."; 
	} else if ($("#pw").val().length<4) {
		return "Must have al least 4 characters";
	}
	
	return true; 
}

$(document).on("click", ".btnRemove", function(event)
{ 
	$.ajax( 
	{ 
		url : "UsersAPI", 
		type : "DELETE", 
		data : "userID=" + $(this).data("itemid"),
		dataType : "text", 
		complete : function(response, status) 
		{ 
		onItemDeleteComplete(response.responseText, status); 
		} 
	}); 
});

function onItemDeleteComplete(response, status)
{ 
	if (status == "success") 
	{ 
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success") 
		{ 
			$("#alertSuccess").text("Successfully deleted."); 
			$("#alertSuccess").show(); 
			$("#divUsersGrid").html(resultSet.data); 
		} else if (resultSet.status.trim() == "error") 
		{ 
			$("#alertError").text(resultSet.data); 
			$("#alertError").show(); 
		} 
	} else if (status == "error") 
	{ 
		$("#alertError").text("Error while deleting."); 
		$("#alertError").show(); 
	} else
	{ 
		$("#alertError").text("Unknown error while deleting.."); 
		$("#alertError").show(); 
	} 
}