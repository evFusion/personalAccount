<?php
$host = "******";
$user = "******";
$db = "******";
$pass = "******";

$link = mysqli_connect($host, $user, $pass, $db);
mysqli_query($link, 'SET NAMES utf8');
if (mysqli_connect_errno($link)) {
    echo "Не удалось подключиться к MySQL: " . mysqli_connect_error();
}
