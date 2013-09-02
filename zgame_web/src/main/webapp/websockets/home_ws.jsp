<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1">
    </head>

    <body>
        <meta charset="utf-8">
        <title>WebSocket Echo Client</title>
        <script language="javascript" type="text/javascript">
            var wsUri = "ws://localhost:8080/zgame_web/ws";
            var websocket = new WebSocket(wsUri);
            websocket.onopen = function(evt) { onOpen(evt); };
            websocket.onmessage = function(evt) { onMessage(evt); };
            websocket.onerror = function(evt) { onError(evt); };

            function init() {
                output = document.getElementById("output");
            }

            function send_echo() {
                websocket.send(textID.value);
                writeToScreen("SENT: " + textID.value);
            }

            function onOpen(evt) {
                writeToScreen("CONNECTED");
            }

            function onMessage(evt) {
                writeToScreen("RECEIVED: " + evt.data);
            }

            function onError(evt) {
                writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
            }

            function writeToScreen(message) {
                var pre = document.createElement("p");
                pre.style.wordWrap = "break-word";
                pre.innerHTML = message;
                output.appendChild(pre);
            }

            window.addEventListener("load", init, false);
        </script>

        <h2 style="text-align: center;">WebSocket Echo Client</h2>
        <div style="text-align: center;"><img style=" width: 64px; height: 64px;" alt=""src="resources/gfx/HTML5_Logo_512.png"></div>
        <br></br>
        <div style="text-align: center;">
            <form action=""> 
                <input onclick="send_echo()" value="Press me" type="button"> 
                <input id="textID" name="message" value="Hello WebSocket!" type="text"><br>
            </form>
        </div>
        <div id="output"></div>
    </body>
</html>