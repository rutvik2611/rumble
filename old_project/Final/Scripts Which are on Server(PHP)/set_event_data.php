<?php
require_once('dbconnect.php');

$event_id  = $_POST['event_id'];
$conntact_number  = $_POST['conntact_number'];
$first_name  = $_POST['first_name'];
$last_name  = $_POST['last_name'];

$message = $_POST['message'];

$sql = "INSERT INTO tbl_event_data (user_email,message, event_id, event_date) VALUES ('".$user_email."', '".$message."', '".$event_id."','".$event_date."')";

if (mysqli_query($con, $sql)) {
    echo "done";
} else {
     echo "failed";
}

