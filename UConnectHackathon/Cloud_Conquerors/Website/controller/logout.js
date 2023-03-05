const express =require("express");
const ejs=require("ejs");
const app = express();
const bodyParser = require("body-parser")
const mongoose=require("mongoose");
const dotenv=require("dotenv");
dotenv.config();
const jwt = require("jsonwebtoken");
// const Employer = require('../model/emp')
// const JobApply = require("../model/jobsapply")
const AppError = require("../utils/AppError")
const tryCatch = require("../utils/tryCatch")
// const EmployerData = require("../model/employers");
const jsonParser = bodyParser.json()
const cookieParser = require("cookie-parser");

app.use(cookieParser());
app.use(bodyParser.urlencoded({extended:false}))
app.use(bodyParser.json())

exports.logout = tryCatch(async(req,res)=>{
    res.clearCookie('access_token').render('index');
})