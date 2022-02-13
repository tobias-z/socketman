const Client = require("websocket").client;

const clientAmount = process.argv[2];

const client = new Client();

for (let i = 0; i < clientAmount; i++) {
  createClient(i);
}

function createClient(id) {
  client.on("connect", (connection) => {
    console.log("connected");
    connection.on("message", (data) => {
      console.log(`message: ${JSON.stringify(data, null, 2)}`);
    });

    setTimeout(
      () =>
        connection.send(
          JSON.stringify({
            channelName: "message",
            message: "bob",
          })
        ),
      getRandomNumber(1000, 3000)
    );
  });
}

client.on("connectFailed", (err) => {
  console.log("connection failed. Did you start the server?", err.message);
});

client.connect("ws://localhost:5050");

function getRandomNumber(min, max) {
  return Math.floor(Math.random() * (max - min)) + min + 1;
}
