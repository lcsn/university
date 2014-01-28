var version = '0.0.1';

var isPlaying = false;
var foreground;
var foregroundCtx;
var background;
var backgroundCtx;

var player;

function initZgame() {

	alert('start up zgame');
	
	bgCanvas 	= $("#backgroundCanvas")[0];
	bgContext 	= bgCanvas.getContext("2d");
	fgCanvas 	= $("#foregroundCanvas")[0];
	fgContext	= fgCanvas.getContext("2d");
	
	fgCanvas.addEventListener("click", onMouseClick, false);
	fgCanvas.addEventListener("mousemove", onMouseMove, false);
	document.addEventListener("keydown", key_down, false);

	/*
	fgCanvas.fillStyle   = '#fff';
	fgCanvas.strokeStyle = '#000';
	fgCanvas.lineWidth   = 1;
	 */
	
	fgContext.beginPath();
	fgContext.rect(r_x, r_y, 50, 50);
	fgContext.fillStyle = 'yellow';
	fgContext.fill();
	fgContext.lineWidth = 2;
	fgContext.strokeStyle = 'black';
	fgContext.stroke();
	
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
	
	player = new Player();
}

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

function clear_foreground() {
	fgContext.clearRect(0, 0, fgCanvas.width, fgCanvas.height);
}

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
};

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

function key_down(e) {
	var key_id = e.keyCode || e.which;
	if(key_id == 38) { // up
		r_y-=5;
	}
	if(key_id == 40) { // down
		r_y+=5;
	}
	if(key_id == 37) { // left
		r_x-=5;
	}
	if(key_id == 39) { // right
		r_x+=5;
	}
	e.preventDefault();
}
