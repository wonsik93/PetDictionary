<?php
	$db['host'] = "18.216.142.72";
	$db['user'] = "caps";
	$db['pass'] = "";
	$db['dbname'] = "capstone";

	$connect=connectDB($db);
	
	function connectDB($db){
		$con = mysqli_connect($db['host'],$db['user'],$db['pass'],$db['dbname']);
		mysqli_set_charset($con, "utf8");
		if(mysqli_connect_errno()){
			echo "Fail to connect DB : ".mysqli_connect_errno();
			exit;
		}
		else {
			return $con;
		}
	}
?>

