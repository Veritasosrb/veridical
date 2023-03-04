const AppError = require("../utils/AppError")

 const errorHandler = (error,req,res,next)=>{
    console.log("App Error")

    // we can check error.name == validation ans all stuffs
    if(error instanceof AppError)
    {

        return res.status(error.statusCode).send(error.message);
    }
    return res.status(404).send("Somethinf went wrong");

};

module.exports = errorHandler;
