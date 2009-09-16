$(document).ready(function() { 
		
		$('#holder > div:gt(4)').fadeEachDown(); // initial fade
		$('#holder').spy({ limit: 25, fadeLast: 10, ajax: 'spy_table.action', isDupes : check_for_dupe, timeout: 2000, 'timestamp' : myTimestamp, fadeInSpeed: 1400 });
	});
	
	// I've built my own custom timestamp function so that I can use it directly 
	// in MySQL becasue of the way I code my SQL statements.
	function myTimestamp() {
		var d = new Date();
		var timestamp = d.getFullYear() + '-' + pad(d.getMonth()) + '-' + pad(d.getDate());
		timestamp += ' ';
		timestamp += pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds());
		return timestamp;
	}
	
	// The pad function ensures that the date looks like 2006-09-13 rather than 2006-9-13
	function pad(n) {
		n = n.toString();
		return (n.length == 1 ? '0' + n : n);
	}
	
	// first I'm ensuring that 'last' has been initialised (with last.constructor == Object),
	// then prev.html() == last.html() will return true if the HTML is the same, or false,
	// if I have a different entry.
	function check_for_dupe(prev, last)
	{
		if (last.constructor == Object)	return (prev.html() == last.html());
		else return 0;
	}