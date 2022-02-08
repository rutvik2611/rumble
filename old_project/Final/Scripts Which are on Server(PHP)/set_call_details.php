<?php
require_once('dbconnect.php');

$user_email  = $_POST['user_email'];
$event_date  = $_POST['event_date'];
$event_id  = $_POST['event_id'];
$payload  = $_POST['payload'];
$contact_number  = $_POST['contact_number'];
$first_name  = $_POST['first_name'];
$last_name  = $_POST['last_name'];
$message = $_POST['message'];
$final = "failed";

$sql = "INSERT INTO tbl_event_data (user_email,message, event_id, event_date) VALUES ('".$user_email."', '".$message."', '".$event_id."','".$event_date."')";

    if (mysqli_query($con, $sql)) {
        $final = "done";
    } else {
        $final = "done";
}


$someArray = json_decode($payload, true);
foreach ($someArray as $key => $value) {
    
   $sql2 = "INSERT INTO tbl_event_call_data (event_id,conntact_number, first_name, last_name) VALUES ('".$event_id."', '".$value["contact_number"]."','".$value["first_name"]."','".$value["last_name"]."')";
   if (mysqli_query($con, $sql2)) {
     $final = "done";
    }else {
     $final = "done";
    }

}

echo $final;
