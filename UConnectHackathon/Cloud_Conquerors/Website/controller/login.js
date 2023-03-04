const express =require("express");
const cookieParser = require("cookie-parser");
const ejs=require("ejs");
const bodyParser=require("body-parser");
const mongoose=require("mongoose");
const dotenv=require("dotenv");
const tryCatch = require("../utils/tryCatch");
const AppError = require("../utils/AppError");
const User = require("../model/users.model");
dotenv.config();
const app = express();
const jwt = require("jsonwebtoken");
const authetication = require("../middleware/authetication");
// Body-parser middleware
app.use(bodyParser.urlencoded({extended:false}))
app.use(cookieParser());
app.use(bodyParser.json())

exports.postLogin = tryCatch(async(req,res,next)=>{
    
    const {email,password} = req.body;
    if(!email || !password)
    {
        throw new AppError(300,"Invalid username or password",404);
    }
    // const newUser = await User.find({email,password});
    //  console.log(newUser)
    // if(!newUser)
    // {
    //     throw AppError(300,"User Not Found",404);
    // }

    // const accesssToken = jwt.sign({newUser},process.env.ACCESS_TOKEN);
    // res.cookie("access_token",accesssToken).redirect("/Search")

    //new new new//

    // res.cookie("access_token",accesssToken).status(201).json({
    //     status:"succsess",
    //     data: {
    //         newUser,
            
    //     },
    //     accesssToken : accesssToken
    // });

    //redner to all jobs

    User.findOne({ email }).then(user => {
        // Check if user exists
        if (!user) {
            return res.status(404).json({ emailnotfound: "Email not found" });
        }
        // Check password
        if(password===user.password){
            res.render("Search");
        }
        else {
           return res
           .status(400)
           .json({ passwordincorrect: " Password incorrect" });
       }
    });
})



exports.getLogin = tryCatch(async(req,res,next)=>{
    
    
    res.render("login");
})

app.use(authetication);
