const express = require("express");
const app = express();
const port = 8080;
const path = require("path");
const  {exec }  = require("child_process");
const shell = require("shelljs")
// const { Storage } = require("@google-cloud/storage");
const Multer = require("multer");
const src = path.join(__dirname,"views")
app.use(express.static(src));
app.get("/", (req, res) => {
    res.sendFile(src + "/index.html");
});

app.listen(port, () => {
    console.log('Server started on port', port );
});