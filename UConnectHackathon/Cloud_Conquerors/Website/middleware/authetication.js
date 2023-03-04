require("dotenv").config();
const express = require("express");
const jwt = require("jsonwebtoken");
const cookieParser = require("cookie-parser");
const app = express();
const AppError = require("../utils/AppError");
const tryCatch = require("../utils/tryCatch");
// app.use(cookieParser());

const autheticationToken =  (req,res,next)=>{
  
    const token = req.cookies.access_token;
//    console.log(token);

   try {
    if(token == null) throw new AppError(300,"have no access",401);

    try {
        const data = jwt.verify(token,process.env.ACCESS_TOKEN);
        req.username = data.username;
        req.password = data.password; 
        
        return next();
    } catch (error) {
        throw new AppError(300,"token expire",403);
    }
   } catch (error) {
        return res.render("register");
   }
    

    

}
module.exports = autheticationToken;