

var MAX_SPEED = 10;
var MIN_SPEED = 1;

var isPlaying = false;
var foreground;
var foregroundCtx;
var background;
var backgroundCtx;

var player;

var wsUri = "ws://localhost:8080/zgame_web/ws/player";
var webSocket = new WebSocket(wsUri);

function Zgame() {
	this.version = '0.0.4';
}

function onOpen(event) {
	alert("Connection established to: " + wsUri);
}

function onMessage(event) {
	var data = event.data;
	var message = data.playerName + "@" + data.x + " / " + data.y;
	$("#location")[0].innerHTML = message;
	 var pre = document.createElement("p");
     pre.style.wordWrap = "break-word";
     pre.innerHTML = message;
     players.appendChild(pre);
}

Zgame.prototype.start = function(name) {
	alert("Zgame("+this.version+") started!");
	
	players = $("#players")[0];

	player = new Player(name);
	
	webSocket.onerror = function(event) {
		onError(event);
	};

	webSocket.onopen = function(event) {
		onOpen(event);
		webSocket.send(name + " has entered");
	};

	webSocket.onclose = function(event) {
		webSocket.send(name + " has left");
	};

	webSocket.onmessage = function(event) {
		onMessage(event);
	};
 
	bgCanvas 	= $("#backgroundCanvas")[0];
	bgContext 	= bgCanvas.getContext("2d");
	fgCanvas 	= $("#foregroundCanvas")[0];
	fgContext	= fgCanvas.getContext("2d");
	
	fgCanvas.addEventListener("click", onMouseClick, false);
	fgCanvas.addEventListener("mousemove", onMouseMove, false);
	document.addEventListener("keydown", key_down, false);
	document.addEventListener("keyup", key_up, false);

	requestaframe = (function() {
		return window.requestAnimationFrame	||
		window.webkitRequestAnimationFrame 	||
		window.mozRequestAnimationFrame 	||
		window.oRequestAnimationFrame 		||
		window.msRequestAnimationFrame 		||
		function (callback) {
			window.setTimeout(callback, 1000 / 60);
		};
	})();
	
	start_game_loop();
	
};

function Player(name, offsetX, offsetY) {
	this.name 			= name;
	this.drawY 			= typeof offsetX!=="undefined"?offsetX:0;
	this.drawX 			= typeof offsetY!=="undefined"?offsetY:0;
	this.speed			= 1;
	this.is_keyup		= false;
	this.is_keydown		= false;
	this.is_keyleft		= false;
	this.is_keyright	= false;
}

Player.prototype.draw = function() {
	fgContext.beginPath();
	fgContext.rect(this.drawX, this.drawY, 50, 50);
	fgContext.fillStyle = 'yellow';
	fgContext.fill();
	fgContext.lineWidth = 2;
	fgContext.strokeStyle = 'black';
	fgContext.stroke();
	this.keyCheck();
};

Player.prototype.keyCheck = function() {
	var sendViaSocket = false;
	if (this.is_keyup) {
		if(((this.drawY-this.speed)<0)) {
			this.drawY=0;
		}
		else {
			this.drawY-=this.speed;
		}
		sendViaSocket=true;
	}
	if (this.is_keydown) {
		if(((this.drawY+this.speed)>fgCanvas.height)) {
			this.drawY=fgCanvas.height;
		}
		else {
			this.drawY+=this.speed;
		}
		sendViaSocket=true;
	}
	if (this.is_keyleft) {
		if(((this.drawX-this.speed)<0)) {
			this.drawX=0;
		}
		else {
			this.drawX-=this.speed;
		}
		sendViaSocket=true;
	} 
	if (this.is_keyright) {
		if(((this.drawX+this.speed)>fgCanvas.width)) {
			this.drawX=fgCanvas.width;
		}
		else {
			this.drawX+=this.speed;
		}
		sendViaSocket=true;
	}
	if(sendViaSocket) {
		c = new Object();
		c.playerName = this.name;
		c.x = this.drawX;
		c.Y = this.drawY;
		//webSocket.send(this.name+" @ "+this.drawX+" / "+this.drawY);
		webSocket.send(c);
	}
};

Player.prototype.increaseSpeed = function () {
	if(this.speed < MAX_SPEED) {
		this.speed++;
		alert($("#tfSpeed")[0]);
		$("#tfSpeed")[0].innerHTML = this.speed;
	}
};

Player.prototype.decreaseSpeed = function () {
	if(this.speed > MIN_SPEED) {
		this.speed--;
		alert($("#tfSpeed")[0]);
		$("#tfSpeed")[0].innerHTML = this.speed;
	}
};


function onMouseClick(e) {
	//$("#x")[0].innerHTML = e.pageX;
	//$("#y")[0].innerHTML = e.pageY;
}

function onMouseMove(e) {
	var rect = fgCanvas.getBoundingClientRect();
	var x = e.clientX - rect.left;
	var y = e.clientY - rect.top;
	$("#x")[0].innerHTML = x;
	$("#y")[0].innerHTML = y;
}

function key_down(e) {
	var key_id = e.keyCode || e.which;
	if(key_id == 38) { // up
		player.is_keyup = true;
	}
	if(key_id == 40) { // down
		player.is_keydown = true;
	}
	if(key_id == 37) { // left
		player.is_keyleft = true;
	}
	if(key_id == 39) { // right
		player.is_keyright = true;
	}
	e.preventDefault();
}

function key_up(e) {
	var key_id = e.keyCode || e.which;
	if(key_id == 38) { // up
		player.is_keyup = false;
	}
	if(key_id == 40) { // down
		player.is_keydown = false;
	}
	if(key_id == 37) { // left
		player.is_keyleft = false;
	}
	if(key_id == 39) { // right
		player.is_keyright = false;
	}
	e.preventDefault();
}

function reset_game() {
	player.drawX = 0;
	player.drawY = 0;
}

function clear_foreground() {
	fgContext.clearRect(0, 0, fgCanvas.width, fgCanvas.height);
}

function game_loop() {
	clear_foreground();
	player.draw();
	if(isPlaying) {
		requestaframe(game_loop);
	}
}

function start_game_loop() {
	if(!isPlaying) {
		isPlaying = true;
		game_loop();
	}
}

function stop_game_loop() {
	if(isPlaying) {
		isPlaying = false;
	}
}

function increaseSpeed() {
	player.increaseSpeed();
}

function decreaseSpeed() {
	player.decreaseSpeed();
}
