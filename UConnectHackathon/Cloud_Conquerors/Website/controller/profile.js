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
const User = require("../model/users.model");
const tryCatch = require("../utils/tryCatch")
// const EmployerData = require("../model/employers");
// const EmployeeData = require("../model/employee")
const jsonParser = bodyParser.json()
const cookieParser = require("cookie-parser");
const { json } = require("stream/consumers");

app.use(cookieParser());
app.use(bodyParser.urlencoded({extended:false}))
app.use(bodyParser.json())

exports.getProfile = tryCatch(async(req,res)=>{
    const user = jwt.verify(req.cookies.access_token,process.env.ACCESS_TOKEN);
    const data1 = await EmployerData.find({key:user.newUser[0]._id})
    if(data1)
    {
        return res.redirect('/employerprofile');
    }
    const data2 = await EmployeeData.find({key:user.newUser[0]._id})
    if(data2)
    {
        return res.redirect('/employeeuserprofile');
    }
  
})