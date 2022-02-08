<?php

$id  = $_POST['event_id'];
require_once('dbconnect.php');

$sql = "SELECT * FROM tbl_event_call_data WHERE event_id='".$id."'";
$result = $con->query($sql);

if ($result->num_rows >0) {
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