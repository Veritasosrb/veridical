const express =require("express");
const ejs=require("ejs");
const app = express();
const bodyParser = require("body-parser")
const mongoose=require("mongoose");
const dotenv=require("dotenv");
dotenv.config();
const User = require('../model/users.model')
// const User = require('../model/users.model')
const AppError = require("../utils/AppError")
const tryCatch = require("../utils/tryCatch")
// const EmployerData = require("../model/employers");
// const EmployeeData = require("../model/employee");
// const { db } = require("../model/jobsapply");
const jsonParser = bodyParser.json()

// Body-parser middleware
app.use(bodyParser.urlencoded({extended:false}))
app.use(bodyParser.json())


exports.postRegister = tryCatch(async(req,res,next)=>{
    console.log(req.body)
    const newUser= new User(req.body);
    // newUser.name = req.body.name;
    // newUser.email = req.body.email;
    // newUser.age = req.body.age;
    // newUser.phoneNo = req.body.phoneNo;
    // newUser.username = req.body.username;
    // newUser.password = req.body.password;
    console.log(newUser.name);
    if(
        !newUser.username ||
        !newUser.email ||
        !newUser.password||
        !newUser.phoneNo
    ){
        
        throw new AppError(300,"input field not provided",404)

    }
    // newUser.location.coordinates[0] = 0;
    // newUser.location.coordinates[1] = 0;
    // newUser.location.type = "Point"
    //     // db('test').User.createIndex({"location":"2d"});
    newUser.save();
    console.log(newUser._id);
    // if(req.body.applyType == 1)
    // {
    //     const employer = new EmployerData();
    //     employer.key = newUser._id;
    //     employer.jobsposted = 0;
    //     employer.hired = 0;
    //     employer.save();
    // }
    // if(req.body.applyType == 2)
    // {
    //     const employee = new EmployeeData();
    //     employee.key = newUser._id;
    //     employee.jobsapplied = 0;
    //     employee.jobsdone = 0;
    //     employee.save();
    // }
    

    
    res.render("Search");
})


exports.getRegister = tryCatch(async(req,res,next)=>{
    
    User.find(function(err, users) {
		if (err) {
			console.log(err);
		} else {
			res.json(users);
		}
	})
//    res.render("register")
})


















