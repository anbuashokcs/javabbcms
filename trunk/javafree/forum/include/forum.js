// Startup variables
var imageTag = false;
var theSelection = false;


// Check for Browser & Platform for PC & IE specific bits
// More details from: http://www.mozilla.org/docs/web-developer/sniffer/browser_type.html
var clientPC = navigator.userAgent.toLowerCase(); // Get client info
var clientVer = parseInt(navigator.appVersion); // Get browser version

var is_ie = ((clientPC.indexOf("msie") != -1) && (clientPC.indexOf("opera") == -1));
var is_nav  = ((clientPC.indexOf('mozilla')!=-1) && (clientPC.indexOf('spoofer')==-1)
                && (clientPC.indexOf('compatible') == -1) && (clientPC.indexOf('opera')==-1)
                && (clientPC.indexOf('webtv')==-1) && (clientPC.indexOf('hotjava')==-1));

var is_win   = ((clientPC.indexOf("win")!=-1) || (clientPC.indexOf("16bit") != -1));
var is_mac    = (clientPC.indexOf("mac")!=-1);


// Helpline messages
b_help = "Negrito: [b]texto[/b]  (alt+b)";
i_help = "Itálico: [i]texto[/i]  (alt+i)";
u_help = "Sublinhado: [u]texto[/u]  (alt+u)";
q_help = "Citação: [quote]texto[/quote]  (alt+q)";
c_help = "Código: [code]código[/code]  (alt+c)";
l_help = "Lista: [list]texto[/list] (alt+l)";
o_help = "Lista ordenada: [list=]texto[/list]  (alt+o)";
p_help = "Imagem: [img]http://url_da_imagem[/img]  (alt+p)";
w_help = "Inserir URL: [url]http://url[/url] ou [url=http://url]Texto da URL[/url]  (alt+w)";
a_help = "Fechar todas as marcas de bbCode";
s_help = "Cor: [color=red]texto[/color]  Dica: você também pode usar color=#FF0000";
f_help = "Fonte: [size=x-small]texto pequeno[/size]";

// Define the bbCode tags
bbcode = new Array();
bbtags = new Array('[b]','[/b]','[i]','[/i]','[u]','[/u]','[quote]','[/quote]','[code]','[/code]','[list]','[/list]','[list=]','[/list]','[img]','[/img]','[url]','[/url]');
imageTag = false;

// Shows the help messages in the helpline window
function helpline(help) {
	document.post.helpbox.value = eval(help + "_help");
}

function popup(url,w,h,s) {
	window.open(url,'popup','width='+w+',height='+h+',directories=0,location=0,menubar=0,resizable=0,scrollbars='+s+',status=0,toolbar=0,marginleft=0,margintop=0,left='+(((screen.availWidth-w)/2)+-10)+',top='+(((screen.height-h)/2)+-10));
}

// Replacement for arrayname.length property
function getarraysize(thearray) {
	for (i = 0; i < thearray.length; i++) {
		if ((thearray[i] == "undefined") || (thearray[i] == "") || (thearray[i] == null))
			return i;
		}
	return thearray.length;
}

// Replacement for arrayname.push(value) not implemented in IE until version 5.5
// Appends element to the array
function arraypush(thearray,value) {
	thearray[ getarraysize(thearray) ] = value;
}

// Replacement for arrayname.pop() not implemented in IE until version 5.5
// Removes and returns the last element of an array
function arraypop(thearray) {
	thearraysize = getarraysize(thearray);
	retval = thearray[thearraysize - 1];
	delete thearray[thearraysize - 1];
	return retval;
}


function notImplemented() {
    alert("Não implementado");
}

function emoticon(text) {
	text = ' ' + text + ' ';
	if (document.post.message.createTextRange && document.post.message.caretPos) {
		var caretPos = document.post.message.caretPos;
		caretPos.text = caretPos.text.charAt(caretPos.text.length - 1) == ' ' ? text + ' ' : text;
		document.post.message.focus();
	} else {
	document.post.message.value  += text;
	document.post.message.focus();
	}
}

function bbfontstyle(bbopen, bbclose) {
	if ((clientVer >= 4) && is_ie && is_win) {
		theSelection = document.selection.createRange().text;
		if (!theSelection) {
			document.post.message.value += bbopen + bbclose;
			document.post.message.focus();
			return;
		}
		document.selection.createRange().text = bbopen + theSelection + bbclose;
		document.post.message.focus();
		return;
	} else {
		document.post.message.value += bbopen + bbclose;
		document.post.message.focus();
		return;
	}
	storeCaret(document.post.message);
}


function bbstyle(bbnumber) {

	donotinsert = false;
	theSelection = false;
	bblast = 0;

	if (bbnumber == -1) { // Close all open tags & default button names
		while (bbcode[0]) {
			butnumber = arraypop(bbcode) - 1;
			document.post.message.value += bbtags[butnumber + 1];
			buttext = eval('document.post.addbbcode' + butnumber + '.value');
			eval('document.post.addbbcode' + butnumber + '.value ="' + buttext.substr(0,(buttext.length - 1)) + '"');
		}
		imageTag = false; // All tags are closed including image tags :D
		document.post.message.focus();
		return;
	}

	if ((clientVer >= 4) && is_ie && is_win)
		theSelection = document.selection.createRange().text; // Get text selection

	if (theSelection) {
		// Add tags around selection
		document.selection.createRange().text = bbtags[bbnumber] + theSelection + bbtags[bbnumber+1];
		document.post.message.focus();
		theSelection = '';
		return;
	}

	// Find last occurance of an open tag the same as the one just clicked
	for (i = 0; i < bbcode.length; i++) {
		if (bbcode[i] == bbnumber+1) {
			bblast = i;
			donotinsert = true;
		}
	}

	if (donotinsert) {		// Close all open tags up to the one just clicked & default button names
		while (bbcode[bblast]) {
				butnumber = arraypop(bbcode) - 1;
				document.post.message.value += bbtags[butnumber + 1];
				buttext = eval('document.post.addbbcode' + butnumber + '.value');
				eval('document.post.addbbcode' + butnumber + '.value ="' + buttext.substr(0,(buttext.length - 1)) + '"');
				imageTag = false;
			}
			document.post.message.focus();
			return;
	} else { // Open tags

		if (imageTag && (bbnumber != 14)) {		// Close image tag before adding another
			document.post.message.value += bbtags[15];
			lastValue = arraypop(bbcode) - 1;	// Remove the close image tag from the list
			document.post.addbbcode14.value = "Img";	// Return button back to normal state
			imageTag = false;
		}

		// Open tag
		document.post.message.value += bbtags[bbnumber];
		if ((bbnumber == 14) && (imageTag == false)) imageTag = 1; // Check to stop additional tags after an unclosed image tag
		arraypush(bbcode,bbnumber+1);
		eval('document.post.addbbcode'+bbnumber+'.value += "*"');
		document.post.message.focus();
		return;
	}
	storeCaret(document.post.message);
}

// Insert at Claret position. Code from
// http://www.faqts.com/knowledge_base/view.phtml/aid/1052/fid/130
function storeCaret(textEl) {
	if (textEl.createTextRange) textEl.caretPos = document.selection.createRange().duplicate();
}


function deleteMsg(id,idUserPost,idTopic,idForum, msg){
	if(confirm(msg)){
		document.location.href="deletepost.jbb?post.user.idUser=" + idUserPost + "&post.idPost=" + id +"&post.topic.idTopic=" + idTopic + "&post.topic.forum.idForum=" + idForum;
	}
}

function deleteTopic(idTopic, idForum, msg){
    if(confirm(msg)){
        document.location.href="admin/deletetopic.jbb?topic.idTopic=" + idTopic + "&forum.idForum=" + idForum;
    }
}

function lockTopic(idTopic, idForum, msg){
    if(confirm(msg + "?")){
        document.location.href="admin/locktopic.jbb?topic.idTopic=" + idTopic + "&forum.idForum=" + idForum;
    }
}

function unlockTopic(idTopic, idForum, msg){
    if(confirm(msg + "?")){
        document.location.href="admin/unlocktopic.jbb?topic.idTopic=" + idTopic + "&forum.idForum=" + idForum;
    }
}

function deleteUser(idUser, msg){
    if(confirm(msg)){
        document.location.href="delete_user.jbb?user.idUser=" + idUser;
    }
}

function trim(s) {
	// Remove leading spaces and carriage returns
  	while ((s.substring(0,1) == ' ') || (s.substring(0,1) == '\n') || (s.substring(0,1) == '\r')) {
    	s = s.substring(1,s.length);
	}

	// Remove trailing spaces and carriage returns
	while ((s.substring(s.length-1,s.length) == ' ') || (s.substring(s.length-1,s.length) == '\n') || (s.substring(s.length-1,s.length) == '\r')) {
    	s = s.substring(0,s.length-1);
	}
	return s;
}


//use # for separate name of fields
function validateRequiredFields(objForm,nmField,msg) {
	arrFields = nmField.split('#');
	for(i=0; i < arrFields.length; i++) {
		if(trim(objForm.elements[arrFields[i]].value) == ""){
			alert(msg);
			objForm.elements[arrFields[i]].focus();
			return false;
		}
	}
	return true;
}

//with an Array, you can verify all fields of you form
function validateRequiredFieldsByArr(arrFields, msg) {
	for(i=0; i < arrFields.length; i++) {
		if(trim(document.getElementById(arrFields[i]).value) == ""){
			alert(msg);
			document.getElementById(arrFields[i]).focus();
			return false;
		}
	}
	return true;
}


function quoteMsg(id,whoQuote, page){
	document.location.href="quote.jbb?post.idPost=" + id +"&whoQuote=" + whoQuote+"&page="+page;
}


function topicParam(objForm){

	if(document.getElementById("subject").value != ""){
		objForm.elements["topic.titleTopic"].value = objForm.elements["subject"].value;
	}
	if(document.getElementById("message").value != ""){
		objForm.elements["post.postBody"].value = objForm.elements["message"].value;
	}

	if(trim(document.getElementById("subject").value) == ""){
		alert(i18nTopicRequired);
		objForm.elements["subject"].focus();
		return false;
	}
	var msgTmp = trim(objForm.elements["message"].value);
	
	if (msgTmp.length < 1) {
		alert(i18nMessageRequired);
		objForm.elements["message"].focus();
		return false;
	}

	//Change value of submit button
	//changeBtnValue();
}

function checkMail(email){
	if (email.indexOf ('@',0) == -1 ||
      email.indexOf ('.',0) == -1) {
      return false;
    } else {
    	return true;
    }
}

function submitEditProfile(objForm,nmField,msg, msgPws, msgNoValidMail){
	
	//Verfica os campos obrigatórios
	if(validateRequiredFields(objForm,nmField,msg)) {
		//Checa a validade do email
		if(!checkMail(objForm.elements["emailTmp"].value)){
			alert(msgNoValidMail);
			objForm.elements["emailTmp"].focus();
			return false;
		}
		
		//Verifica se as senhas digitadas coincidem
		if(objForm.elements["new_password"].value != objForm.elements["password_confirm"].value){
			alert(msgPws);
			objForm.elements["new_password"].focus();
			return false;
		} else {
			objForm.elements["user.passwordHash"].value = objForm.elements["new_password"].value;
		}

		//Verifica se o usuário modificou o email
		if(objForm.elements["user.email"].value != objForm.elements["emailTmp"].value){
			objForm.elements["user.email"].value = objForm.elements["emailTmp"].value;
		} else {
			objForm.elements["user.email"].value = "";
		}
	
	} else {
		return false;
	}
}



function submitInsertProfile(objForm,nmField,msg, msgPws, msgNoValidMail){

	//Verfica os campos obrigatórios
	if(validateRequiredFields(objForm,nmField,msg)){
		//Checa a validade do email
		if(!checkMail(objForm.elements["user.email"].value)){
			alert(msgNoValidMail);
			objForm.elements["user.email"].focus();
			return false;
		}
		
		//Verifica se as senhas digitadas coincidem
		if(objForm.elements["passwordHash"].value != objForm.elements["password_confirm"].value){
			alert(msgPws);
			objForm.elements["passwordHash"].focus();
			return false;
		} else {
			objForm.elements["user.passwordHash"].value = objForm.elements["passwordHash"].value;
		}
	} else {
		return false;
	}
}

function changeForumByCombo(idForum){
	if(idForum != -1){
		document.location.href="viewforum.jbb?f=" + idForum;
	}
}


//Autor: Rafael Steil 
var quick = false;
function activateQuickReply(){
	quick = !quick;

	document.getElementById("trQuickBody").style.display = (quick ? "" : "none");
	document.getElementById("trQuickSubmit").style.display = (quick ? "" : "none");

	if (quick) {
		document.location = "#quick";
	}
}

function viewWatchTopicsByUser(userId){
	popup('watch_topics_by_user.jbb?u='+userId,'600','250','1');
}

function viewWatchTopicsByTopic(){
	popup('watch_topics_by_topic.jbb?t='+topicId,'400','250','1');
}

function viewFavoriteTopicsByTopic(){
	popup('favorite_topics_by_topic.jbb?t='+topicId,'400','250','1');
}

function viewFavoriteTopicsByUser(userId){
	popup('favorite_topics_by_user.jbb?u='+userId,'600','250','1');
}

var varDot = "";
function makeShowDots(){
	makeShowDotBusiness();
	setTimeout("eval('makeShowDots2()')",250);
}

function makeShowDots2(){
	setTimeout("eval('makeShowDots()')",250);
}

function makeShowDotBusiness(){
	if(varDot == '.'){
		varDot = '..';
	} else if(varDot == '..'){
		varDot = '...';
	} else if(varDot == '...'){
		varDot = '';
	} else if(varDot == ''){
		varDot = '.';
	}
	document.getElementById("btnSubmit").value =  i18nWait + varDot;
}

function changeBtnValue(){
	btn = document.getElementById("btnSubmit");
	btn.disabled = true;
	setTimeout("makeShowDots()",250);
}

function enableBtnSubmit(label) {
	btn = document.getElementById("btnSubmit");
	btn.value = label;
	btn.disabled = false;
}


function setFocus(param){
	document.getElementById(param).focus();
}

function viewLegend(){
	popup('view_legend.jbb','350','190','1');
}

	/*********  AJAX Universal CallBack  *********/
	function callback (res) {
	
		/**** FAVORITE TOPICS ****/
		if(res == "addFavoriteTopic=1"){
			favoriteTopic = 1;
			document.getElementById("spanLinkFavorite").innerHTML = messageDeleteFavorite;
			imageNameAndPath = imageDone;
			inputImageOnSomeLayer();
		} else if(res == "addFavoriteTopic=0"){
			favoriteTopic = 1;
			document.getElementById("spanLinkFavorite").innerHTML = messageDeleteFavorite;
			imageNameAndPath = imageDone;
			inputImageOnSomeLayer();
		}
		
		if(res == "deleteFavoriteTopic=1"){
			favoriteTopic = 0;
			document.getElementById("spanLinkFavorite").innerHTML = messageAddFavorite;
			imageNameAndPath = imageDone;
			inputImageOnSomeLayer();
		} else if(res == "deleteFavoriteTopic=0"){
			favoriteTopic = 0;
			document.getElementById("spanLinkFavorite").innerHTML = messageAddFavorite;
			imageNameAndPath = imageDone;
			inputImageOnSomeLayer();
		}

		/**** // FAVORITE TOPICS ****/

		
		/**** WATCH TOPICS ****/
		if(res == "addWatchTopic=1"){
			varWatchTopic = 1;
			document.getElementById("spanWatchTopic").innerHTML = varMessageWatchTopicDelete;
			imageNameAndPath = imageDone;
			inputImageOnSomeLayer();
		} else if(res == "addWatchTopic=0"){
			varWatchTopic = 1;
			document.getElementById("spanWatchTopic").innerHTML = varMessageWatchTopicDelete;
			imageNameAndPath = imageDone;
			inputImageOnSomeLayer();
		}
		
		if(res == "deleteWatchTopic=1"){
			varWatchTopic = 0;
			document.getElementById("spanWatchTopic").innerHTML = varMessageWatchTopicAdd;
			imageNameAndPath = imageDone;
			inputImageOnSomeLayer();
		} else if(res == "deleteWatchTopic=0"){
			varWatchTopic = 0;
			document.getElementById("spanWatchTopic").innerHTML = varMessageWatchTopicAdd;
			imageNameAndPath = imageDone;
			inputImageOnSomeLayer();
		}

		/**** // WATCH TOPICS ****/		
		
		
		/**** ATTACHMENTS OF FILES ****/
		if(res == "deleteFile=1"){
			deleteAttachChangeInput();
			imageNameAndPath = imageDone;
			inputImageOnSomeLayer();
		} else if(res == "deleteFile=0"){
			imageNameAndPath = imageDone;
			inputImageOnSomeLayer();
			alert("No files have been deleted.");
		}
		/**** // ATTACHMENTS OF FILES ****/	
		
	}
	/*********************************************/

	function inputImageOnSomeLayer(){
		imgNm = '<img src="' + imageNameAndPath + '">';
		if(imageNameAndPath == ""){
			imgNm = '';
		}
		document.getElementById(layerName).innerHTML = imgNm;
	}

	function doFavTopic(){
		if(imageNameAndPath != imageRolling){
			imageNameAndPath = imageRolling;
			if(favoriteTopic == "1"){
				deleteFavoriteTopic();
			}else {
				addFavoriteTopic();
			}
		}
	}

    function addFavoriteTopic() {
    	inputImageOnSomeLayer();
        RemoteFunctions.addFavoriteTopic(callback, topicId);
   	}
   	
    function deleteFavoriteTopic() {
    	inputImageOnSomeLayer();
		RemoteFunctions.deleteFavoriteTopic(callback, topicId);
   	}


	function watchTopic(){
		if(imageNameAndPath != imageRolling){
			imageNameAndPath = imageRolling;
			if(varWatchTopic == "1"){
				deleteWatchTopic();
			}else {
				addWatchTopic();
			}
		}
	}

    function addWatchTopic() {
    	inputImageOnSomeLayer();
        RemoteFunctions.addWatchTopic(callback, topicId);
   	}
   	
    function deleteWatchTopic() {
    	inputImageOnSomeLayer();
		RemoteFunctions.deleteWatchTopic(callback, topicId);
   	}
   	
   	
    function deleteAttach(fileId, layerId) {
    	layerName = layerId;
	   	inputImageOnSomeLayer();
        RemoteFunctions.deleteFile(callback, fileId);
   	}

   	
	function deleteAttachChangeInput(){
		inputImageOnSomeLayer();
		//document.getElementById(attachSpanId).innerHTML = newInputFile;
	}   	
   	