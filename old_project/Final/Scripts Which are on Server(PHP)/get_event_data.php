<?php

$id  = $_POST['user_email'];
require_once('dbconnect.php');

$sql = "SELECT * FROM tbl_event_data WHERE user_email='".$id."' ORDER BY id DESC";
$result = $con->query($sql);

if ($result->num_rows >0) {
 // output data of each row
 while($row[] = $result->fetch_assoc()) {
 
 $tem = $row;
 
 $json = json_encode($tem);
 
 
 }
 
} else {
 echo "0 results";
}
 echo $json;
$con->close();

?>