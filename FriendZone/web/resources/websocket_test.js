if (window.WebSocket) {
    var ws = new WebSocket("ws://localhost:8080/rtchat");



    ws.onmessage = function(event) {
        var text = event.data;
        console.log(text);
        //push message to a component
    };

    ws.onopen = function(event){
        ws.send("Hello!");
    }
}
else {
    // Bad luck. Browser doesn't support it. Consider falling back to long polling.
    // See http://caniuse.com/websockets for an overview of supported browsers.
    // There exist jQuery WebSocket plugins with transparent fallback.
}