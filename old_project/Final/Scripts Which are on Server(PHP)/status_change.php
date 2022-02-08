<?php
// the message

$id  = $_POST['id'];
$type  = $_POST['type'];
$value = $_POST['value'];

require_once('dbconnect.php');


if($type=='call'){
    $sql2 = "UPDATE tbl_event_call_data SET status_call='".$value."' WHERE id='".$id."'";
    if (mysqli_query($con, $sql2)) {
        echo "done";
    } else {
     echo "failed";
    }
}elseif($type=='status'){
    $sql2 = "UPDATE tbl_event_call_data SET confirm_status='".$value."' WHERE id='".$id."'";
    if (mysqli_query($con, $sql2)) {
        echo "done";
    } else {
     echo "failed";
    }
}

