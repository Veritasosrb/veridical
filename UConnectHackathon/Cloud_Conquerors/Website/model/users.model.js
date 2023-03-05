const mongoose = require("mongoose");
const Schema = mongoose.Schema;

// const education = require('./educationmodel')
  
// Schema
const userSchema = new Schema({
	username:{
        type:String,
        required :[true,"Please provide your username"] 
    },
    email:{
        type:String,
        required :[true,"Please provide your email"]  
    },
    password:{
        type:String,
        required :[true,"Please provide your password"] 
    },
    phoneNo:{
        type:Number,
        required :[true,"Please provide your Phone Number"]  
    },
    

});

module.exports = User = mongoose.model("User", userSchema);