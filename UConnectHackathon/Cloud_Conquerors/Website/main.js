const express = require("express");
const dotenv = require("dotenv");
const mongoose = require("mongoose");
const bodyParser = require("body-parser");
const errorHandler = require("./middleware/errorHandler");
const tryCatch = require("./utils/tryCatch");
const AppError = require("./utils/AppError");
const cookieParser = require("cookie-parser");
const path = require("path");
const route = require("./routes/route");
dotenv.config();
const app = express();
const PORT = 7000 || process.env.PORT;

// serving static files
app.set("view engine", "ejs");
app.set("views", path.join(__dirname, "views"));
app.use(express.static(__dirname + "/public"));

// Body-parser middleware
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(cookieParser());

// connecting db
mongoose.set("strictQuery", false);
mongoose.connect(process.env.URI, () => {
  console.log("Db connected");
});
console.log(mongoose.connection.readyState);

const getUser = () => undefined;

app.get(
  "/",
  tryCatch(async (req, res, next) => {
    res.render("index");
  })
);

app.get("/signup",(req, res) =>{
  res.render("signup");
});

app.get("/signin",(req, res) =>{
  res.render("signin");
});

app.get("/Search",(req, res) =>{
  res.render("Search");
});
//middleware
app.use(errorHandler);
app.use(route);

// listining port
app.listen(PORT, (req, res) => {
  console.log(`server is listining on port ${PORT}`);
});
