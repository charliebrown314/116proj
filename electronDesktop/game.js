var socket = new WebSocket("ws://localhost:9000/socket");
var id = undefined;
socket.onmessage = function (event) {
    // received a message from the server
    if(event.data !== undefined){
        var msg = JSON.parse(event.data);
        if(msg.hasOwnProperty('gameState')) {updateGame(msg)}
        else{id = msg['id']}
    }
};

    var game = document.getElementById("game");
    var context = game.getContext("2d");
    function getTeam(player){
        if(player.team === "red"){return '#DD1C19'}
        else if (player.team === "yellow"){return '#EED926'}
        else if (player.team === "blue"){return '#2C4DE1'}
        else if (player.team === "purple"){return '#DB2CE1'}
        else if (player.team === "grey"){return '#899191'}
    }
    function updateGame(gamestate) {
        var gameState = JSON.parse(gamestate.gameState);
        context.clearRect(0, 0, game.width, game.height);
        for (let cp of gameState.CPs) {
            makeCP(cp['x'], cp['y'], getTeam(cp))
        }
        for (let wall of gameState.walls) {
            makeWall(wall['x'], wall['y'], wall['h'], wall['w'])
        }
        for (let projectile of gameState.projectiles){
            makeProjectile(projectile['x'], projectile['y'])
        }
        for (let player of gameState.players){
            makePlayer(player['x'], player['y'], getTeam(player), player['id'])
        }
    }

    function makeWall(x, y, height, width) {
        context.fillStyle = '#686868';
        context.fillRect(x, y, width, height);
    }

    function makePlayer(x, y, team, playerID) {
        context.fillStyle = team;
        context.beginPath();
        context.arc(x, y, 5.0, 0, 2 * Math.PI);
        context.fill();
        var color = '#000';
        if(playerID === id) {
            color = '#fff'
        }
        context.strokeStyle = color;
        context.stroke()
    }

    function makeCP(x, y, team) {
        context.fillStyle = team;
        context.fillRect(x, y, 25, 25);
        context.fillStyle = '#000';
        context.strokeRect(x,y,25,25);
        context.fillStyle = '#fff';
        context.beginPath();
        context.arc(x + 25/2, y + 25/2, 10, 0, 2 * Math.PI);
        context.fill();
        context.fillStyle = '#000';
        context.stroke()
    }
    function makeProjectile(x,y) {
        context.fillStyle = '#000000';
        context.beginPath();
        context.arc(x,y, 2.0, 0, 2 * Math.PI);
        context.fill()
    }