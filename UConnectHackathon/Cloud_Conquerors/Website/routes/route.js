const express = require("express");
const app = express();
const bodyParser = require('body-parser');
const router = express.Router();
const register = require("../controller/register")
const login = require("../controller/login")
const service = require("../controller/service")
// const employer = require("../controller/employer");
// const alljobs = require('../controller/alljobs')
// const applyjob = require("../controller/apply")
// const userprofile = require("../controller/employeeProfile")
// const authetication = require("../middleware/authetication");
// const employerprofile = require("../controller/employerProfile");
// const listallapplied = require("../controller/listpostedjobs");
// const clickedAcc = require('../controller/samplecall');
// const getLocation = require("../controller/getLcoation");
// const acceptedList = require('../controller/acceptedJobsList')
const profile = require("../controller/profile");
const logout = require('../controller/logout');
const cookieParser = require("cookie-parser");
// Body-parser middleware
app.use(bodyParser.urlencoded({extended:false}))
app.use(bodyParser.json())
app.use(cookieParser);

//login
router.get('/login',login.getLogin);
router.post('/login',login.postLogin);


// register
router.get('/register',register.getRegister);
router.post('/register',register.postRegister);

router.get('/service',service.getService);
router.post('/service',service.postService);


// employer application request
// router.get('/employerpost',authetication,employer.getEmployer);
// router.post('/employerpost',authetication,employer.postEmployer);


// alljobs
// router.get('/alljobs',alljobs.getalljobs);


// emloyee job apply
// router.post('/applyjob',authetication,applyjob.postApply);
// router.get('/applyjob',authetication,applyjob.getApply);

// profile
router.get("/profile",profile.getProfile);

//employee profile
// router.get('/employeeuserprofile',authetication,userprofile.getEmployee);  

//employer profile
// router.get('/employerprofile',authetication,employerprofile.getEmployer);

//list of all jobs applied
// router.get('/listallapplied',authetication,listallapplied.getListAppliedJobs);
// 
// get location
// router.post('/getlocation',getLocation.getLocation);
//logout
router.get('/logout',logout.logout);


// sample
// router.post('/clickedAcc',clickedAcc.call)


// accepted job list
// router.get('/acceptedList',acceptedList.getAcceptedJobs);


module.exports = router;    