<?php
include ('db_conn.php');

if (isset($_POST['login']) && isset($_POST['password'])) {

 // receiving the post params
 $login = $_POST['login'];
 $password = $_POST['password'];
 $query = mysqli_query($link, "SELECT * FROM customers WHERE login = '$login' AND password = '$password'");
 
 if (mysqli_num_rows($query) != 0) {
    while($row = mysqli_fetch_array($query)) {
        $client['id'] = $row['id'];
        $client['login'] = $row['login'];
        $client['password'] = $row['password'];
        $client['name'] = $row['name'];
        $client['surname'] = $row['surname'];
        $client['patronymic'] = $row['patronymic'];
        $client['tarif'] = $row['tarif'];
        $client['balance'] = $row['balance'];
        echo (json_encode($client));
    }
 } else {
    $response = "User does not exist or the data is incorrect!";
    echo json_encode($response);
 }
 mysqli_close($link);
}
?>
