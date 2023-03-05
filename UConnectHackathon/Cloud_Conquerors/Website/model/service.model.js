const mongoose = require("mongoose");
const Schema = mongoose.Schema;

// const education = require('./educationmodel')
  
// Schema
const serviceSchema = new Schema({
    applicantId: {
        type:Number ,
        // ref: 'User',
        // required: true
    },
    AWSSheild:{
        type:Boolean,
        required :[true,"Please provide your username"] 
    },
    SecurityGroup:{
        type:Boolean,
        required :[true,"Please provide your username"] 
    },
    AWSKMS:{
        type:Boolean,
        required :[true,"Please provide your username"] 
    },
    EC2:{
        type:Boolean,
        required :[true,"Please provide your username"] 
    },
	EC2:{
        type:Boolean,
        required :[true,"Please provide your username"] 
    },
    S3:{
        type:Boolean,
        required :[true,"Please provide your email"]  
    },
    VPC:{
        type:Boolean,
        required :[true,"Please provide your password"] 
    },
    RouteS3:{
        type:Boolean,
        required :[true,"Please provide your Phone Number"]  
    },
    
    

});

module.exports = Service = mongoose.model("Service", serviceSchema);