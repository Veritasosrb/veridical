const express =require("express");
const ejs=require("ejs");
const app = express();
const bodyParser = require("body-parser")
const mongoose=require("mongoose");
const dotenv=require("dotenv");
dotenv.config();
const Service = require('../model/service.model')
const AppError = require("../utils/AppError")
const tryCatch = require("../utils/tryCatch")
// const EmployerData = require("../model/employers");
// const EmployeeData = require("../model/employee");
// const { db } = require("../model/jobsapply");
const jsonParser = bodyParser.json()

// Body-parser middleware
app.use(bodyParser.urlencoded({extended:false}))
app.use(bodyParser.json())

let num = 0;
exports.postService = tryCatch(async(req,res,next)=>{
    console.log(req.body)
    // const newService= new Service(req.body);
    // newUser.name = req.body.name;
    // newUser.email = req.body.email;
    // newUser.age = req.body.age;
    // newUser.phoneNo = req.body.phoneNo;
    // newUser.username = req.body.username;
    // newUser.password = req.body.password;
    // console.log(newService.name);

    //
    // if(
    //     !newService.EC2 ||
    //     !newService.S3 ||
    //     !newService.VPC||
    //     !newService.RouteS3
    // ){
        
    //     throw new AppError(300,"input field not provided",404)

    // }
    //
    applicantId=num;
    num = num +1;
    applicantId=num;
    const newService = new Service({
        applicantId:applicantId,
        AWSSheild:req.body.AWSSheild,
        SecurityGroup:req.body.SecurityGroup,
        AWSKMS:req.body.AWSKMS,
        EC2: req.body.EC2,
        S3: req.body.S3,
        VPC: req.body.VPC,
        RouteS3: req.body.RouteS3
        // skills: req.body.skills
    });
    // newUser.location.coordinates[0] = 0;
    // newUser.location.coordinates[1] = 0;
    // newUser.location.type = "Point"
    //     // db('test').User.createIndex({"location":"2d"});
   
    newService.save();
    console.log(newService._id);

    var r="Dear user,Your system can get ";
    if(newService.AWSSheild===false){
        r+="Ddos," 
    }
    if(newService.SecurityGroup===false){
        r+="(MIM)Man in the middle attack," 
    }
    if(newService.AWSKMS===false){
        r+="Brute Force Attack," 
    }
    r+="Be safe\n"
    r+="Dear User You lacking these services:"
    
    
    if(newService.EC2===false){
        r+="EC2 , " 
    }
     if(newService.S3===false){
        r+="S3 , "
    }
     if(newService.VPC===false){
        r+="VPC , " 
    }
     if(newService.RouteS3===false){
        r+="RouteS3 " 
    }

    
    // newService.save()
    // .then(service => {
        
    //        res.status(200).json(r);
    //     //    res.status(200).json(service);
    // })
    // .catch(err => {
    // res.status(400).send(err);
    // });
    
    res.send(r);
    // res.send(m );
})


exports.getService = tryCatch(async(req,res,next)=>{
    
    Service.find(function(err, services) {
		if (err) {
			console.log(err);
		} else {
			res.json(services);
		}
	})
//    res.render("register")
})


















