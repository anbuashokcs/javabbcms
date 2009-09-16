<?php

//Header("location: http://www.javafree.com.br/home/modules.php?name=News&file=article&sid=2502&mode=&order=0&thold=0");

if (isset($t)) {
  Header("location: http://www.javafree.org/javabb/viewtopic.jbb?t=$t");
} else if (isset($p)) {
  Header("location: http://www.javafree.org/javabb/viewtopic.jbb?p=$p#$p");
} else {
  Header("location: http://www.javafree.org/javabb");
}

?>
