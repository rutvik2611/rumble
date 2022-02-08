<?php

$id  = $_POST['event_id'];
require_once('dbconnect.php');

$sql = "SELECT * FROM tbl_event_call_data WHERE status_call='0' ";
$result = $con->query($sql);

if ($result->num_rows >0) {
 while($row[] = $result->fetch_assoc()) {
    $tem = $row;
 }
 
 $end = count($tem)-1;
 for($i=0;$i<=$end;$i++){
    $sql2 = "SELECT * FROM tbl_event_data WHERE event_id='".$tem[$i]['event_id']."'";
    $sql3 = "UPDATE tbl_event_call_data SET status_call='1' WHERE id='".$tem[$i]['id']."'";
    $z = mysqli_query($con, $sql3);
    $r2 = mysqli_query($con,$sql2);
    $res2 = mysqli_fetch_array($r2);
    $tem[$i]['message'] = $res2['message'];
    $tem[$i]['event_date'] = $res2['event_date'];
 }
    
 echo json_encode($tem);
    
} else {
 echo "failed";
}


$con->close();
?>