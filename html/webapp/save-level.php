<?php
$name = $_POST["level-name"];
$level = $_POST["level"];

$allowed_chars = array('.', '[', ']', "\n", "\r");
$count = 0;
foreach (str_split($level) as $char) {
    if (!in_array($char, $allowed_chars)) {
        die('Character not allowed: ' . $char . ' (' . ord($char) . ')');
    }
    $count += 1;
}

if ($count != 1230) {
    die("Incorrect number of characters! ($count != 1230)");
}

if (file_exists("assets/levels/$name")) {
    die("Level of name '$name' already exists");
}

if(!preg_match('/^[a-z0-9-]+\.txt$/', $name)) {
    die('The name of level "' . $name . '" is incorrect. It can only contain "a-z", "0-9" and "-" and have extension ".txt"');
}

$file = fopen("assets/levels/$name", "w");
fwrite($file, $level);
fclose($file);

$file = fopen("assets/levels-list.txt", "a");
fwrite($file, $name . "\n");
fclose($file);

$file = fopen("assets/assets.txt", "a");
$size = filesize("assets/levels/$name");
fwrite($file, "t:levels/$name:$size:text/plain\n");
fclose($file);

header("Location: https://wordtower.lonski.pl");
header("Cache-Control: no-cache, must-revalidate");
header("Expires: Mon, 26 Jul 1997 05:00:00 GMT");
header("Content-Type: application/xml; charset=utf-8");

?>
