const axios = require('axios')
var parseString = require('xml2js').parseString;

module.exports.home = function(req, res) {
    return res.render('home');
}

module.exports.check = async(req, res)=> {
    const bucketName = req.body.bucketname;
    const url = 'https://'+bucketName+'.s3.amazonaws.com';
    
    var xmlresp;
     await axios.get(url)

    .then(res => xmlresp = res.data)
    .catch(err => console.log(err.response.data))

        // console.log(xmlresp);
        let res1;
    parseString(xmlresp, function (err, results) {
  
        // parsing to json
        let data = JSON.stringify(results)
        res1 = JSON.parse(data);
        // // display the json data
        console.log(res1.ListBucketResult.Contents);
        // document.getElementById("output").innerHTML = data;


        });
        return res.render('./bucket', {
            data: res1
        });

};