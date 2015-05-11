
/*
 * GET home page.
 */
var fs = require('fs');

exports.index = function(req, res){
    console.log("Method");
	fs.readFile("simple.txt", "utf8", function (error, data) {
        console.log(data);
    });
  res.render('index', { title: 'Express' });
};