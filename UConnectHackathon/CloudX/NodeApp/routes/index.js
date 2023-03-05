const express = require('express');
const router = express.Router();
const homeController = require('../controllers/home_controller');

console.log('router loaded');

router.get('/', homeController.home);
router.post('/check', homeController.check);
// router.use('/users', require('./users'));
// router.use('/students', require('./students'));
module.exports = router;