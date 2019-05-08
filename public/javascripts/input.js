function keyPressHandler(event){
    socket.send(JSON.stringify({"action": "keydown", "key": event.code}))
}
function keyUpHandler(event) {
    socket.send(JSON.stringify({"action": "keyup", "key": event.code}))
}
function clickHandler(){
    const x = event.clientX;
    const y = event.clientY;
    socket.send(JSON.stringify({"action": "click","pos":{"x": x, "y": y}}))
}
socket.onopen = function (ev) {
    socket.send(JSON.stringify({"action": "connected"}))
};
socket.onclose = function (ev) {
    socket.send(JSON.stringify({"action": "disconnected"}))
};
window.setInterval(function(ev){
    socket.send(JSON.stringify({"action": "update"}))
}, 36);
document.addEventListener('keydown', keyPressHandler);
document.addEventListener('keyup', keyUpHandler);
document.addEventListener('click', clickHandler);