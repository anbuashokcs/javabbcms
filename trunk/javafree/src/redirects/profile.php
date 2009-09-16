<?php

//Header("location: http://www.javafree.com.br/home/modules.php?name=News&file=article&sid=2502&mode=&order=0&thold=0");

if (isset($u)) {
	Header("location: http://www.javafree.org/javabb/viewprofile.jbb?usersPortal.idUser=$u");
} else {
	Header("location: http://www.javafree.org/javabb/viewprofile.jbb");
}

%>
