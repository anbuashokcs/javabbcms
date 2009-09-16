<?php

//Header("location: http://www.javafree.com.br/home/modules.php?name=News&file=article&sid=2502&mode=&order=0&thold=0");

if (isset($c)) {
   Header("location: http://www.javafree.org/javabb/loadCategory.jbb?category.idCategory=$c");
} else {
   Header("location: http://www.javafree.org/javabb/");
}

?>

