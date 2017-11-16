<?php
	require_once 'connection.php';
	
	session_start();

	$barcode = $_POST['barcode'];
	//$barcode = 8801121103555;

	$sql = "SELECT * FROM Food WHERE Barcode = '$barcode'";	
	$result = mysqli_query($connect, $sql);
	$return_arr = array();
	while($row = mysqli_fetch_array($result)){
		$row_array['Barcode'] = $row['Barcode'];
		$row_array['FoodName'] = $row['FoodName'];
		$row_array['Prices'] = $row['Prices'];
		$row_array['FoodWeight'] = $row['FoodWeight'];
		$row_array['Origin'] = $row['Origin'];
		$row_array['Maker'] = $row['Maker'];
		$row_array['Functional'] = $row['Functional'];
		$row_array['Whom'] = $row['Whom'];
		$row_array['AgeFrom'] = $row['AgeFrom'];
		$row_array['AgeTo'] = $row['AgeTo'];
		array_push($return_arr, $row_array);
	}

	echo json_encode($return_arr);
?>