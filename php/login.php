<?php
	
	require_once 'connection.php';
		
	session_start();

	$id = $_POST[userid];
	$pw = $_POST[userpw];

	$sql = "SELECT * FROM User WHERE UserName = '$id' AND Password = '$pw'";

	$result = mysqli_query($connect, $sql);

	if($result){
		echo "success";
	}
	else
		echo "false";
	
	mysqli_close($connect);

?>
