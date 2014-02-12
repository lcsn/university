
var MAX_SPEED = 10;
var MIN_SPEED = 1;

var isPlaying = false;
var foreground;
var foregroundCtx;
var background;
var backgroundCtx;

var player = new Player();

function Zgame() {
	this.version = '0.0.2';
}

Zgame.prototype.start = function() {
	
	alert('start up zgame '+this.version);
	
	bgCanvas 	= $("#backgroundCanvas")[0];
	bgContext 	= bgCanvas.getContext("2d");
	fgCanvas 	= $("#foregroundCanvas")[0];
	fgContext	= fgCanvas.getContext("2d");
	
	fgCanvas.addEventListener("click", onMouseClick, false);
	fgCanvas.addEventListener("mousemove", onMouseMove, false);
	document.addEventListener("keydown", key_down, false);
	document.addEventListener("keyup", key_up, false);

	/*
	fgCanvas.fillStyle   = '#fff';
	fgCanvas.strokeStyle = '#000';
	fgCanvas.lineWidth   = 1;
	
	fgContext.beginPath();
	fgContext.rect(player.drawX, player.drawY, 50, 50);
	fgContext.fillStyle = 'yellow';
	fgContext.fill();
	fgContext.lineWidth = 2;
	fgContext.strokeStyle = 'black';
	fgContext.stroke();
	 */
	
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
	
};

function Player() {
	this.drawX = 0;
	this.drawY = 0;
	this.speed = 1;
	this.is_keyup = false;
	this.is_keydown = false;
	this.is_keyleft = false;
	this.is_keyright = false;
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
	if (this.is_keyup) {
		this.drawY-=this.speed;
	}
	if (this.is_keydown) {
		this.drawY+=this.speed;
	}
	if (this.is_keyleft) {
		this.drawX-=this.speed;
	} 
	if (this.is_keyright) {
		this.drawX+=this.speed;
	} 
};

Player.prototype.increaseSpeed = function () {
	if(this.speed < MAX_SPEED) {
		this.speed++;
	}
};

Player.prototype.decreaseSpeed = function () {
	if(this.speed > MIN_SPEED) {
		this.speed--;
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
