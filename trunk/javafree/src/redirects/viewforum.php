<?php

//Header("location: http://www.javafree.com.br/home/modules.php?name=News&file=article&sid=2502&mode=&order=0&thold=0");

if (isset($f)) {
  Header("location: http://www.javafree.org/javabb/viewforum.jbb?f=$f");
} else {
  Header("location: http://www.javafree.org/javabb");
}

?>
