
/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
  , user = require('./routes/user')
  , http = require('http')
  , path = require('path');
var fs = require('fs');
var csv = require("fast-csv");
var async = require('async');
var MongoClient = require('mongodb').MongoClient, format = require('util').format;
var router = express.Router();
var app = express();

// all environments
app.set('port', process.env.PORT || 3000);
app.set('views', __dirname + '/views');
app.set('view engine', 'jade');
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.bodyParser());
app.use(express.methodOverride());
app.use(app.router);
app.use(express.static(path.join(__dirname, 'public')));

// development only
if ('development' == app.get('env')) {
  app.use(express.errorHandler());
}

app.get('/', function(req,res)
		{
	      

    fs.readFile("simple.txt", "utf8", function (error, data) {
        console.log(data);
    });
    res.render('index', { title: 'Express' });
    
    
    	//database name:csvimporter
    	//collection name :myCSVs
    //"mongodb://<root>:<root>@ds061288.mongolab.com:61288/csvimporter"
   //MongoClient.connect('mongodb://localhost:27017/csvdb', function(err, db) {
    MongoClient.connect('mongodb://root:root@ds061288.mongolab.com:61288/csvimporter', function(err, db) {
    	if (err) throw err;
    	console.log("in mongo-method");
    	
    	//var collection = db.collection('myCSVs');
    	var collection = db.collection('myCSVs');
    	var queue = async.queue(collection.insert.bind(collection), 5);
    	
    	console.log("in mongo-method - 2");
    	
    	
    	var collection3lenght ;
    	var collection3 = db.collection('previousLength');
			collection3.count({}, function(error, numOfDocs) {
			    console.log('I have '+numOfDocs+' documents in my collection');
			    collection3lenght=numOfDocs;
			});
    	
    	
			//var i = 0 ; 
    	csv
    	.fromPath("input.csv")
 		.on("data", function(data){
 			console.log(data);
 			
 			console.log("check");
 			
 		/*
 				console.log(collection);
 				if(i+1>collection3lenght){
 					collection3lenght++;
 					i++;*/
 					
 					
 					var data1 = JSON.stringify(data);
 		 			var data2 = {data1: data1};
 		 		
 		 			
 		 			
 		 		/*	//db.previousLength.update({_id:ObjectId("554fc886cc315a4671830aa9")},{length:2});
 		 		collection3.update({identifier:"jude"},{length:collection3lenght}, function (err, result){
 		 				if (err) {
		 			          console.log(err);
		 			        } else {
		 			          console.log('Inserted %d documents into the "users" collection. The documents inserted with "_id" are:', result.length, result);
		 			        }
 		 				
 		 			})
 		 			console.log('before insert');
 		 			*/collection.insert(data2, function (err, result) {
 		 			        if (err) {
 		 			          console.log(err);
 		 			        } else {
 		 			          console.log('Inserted %d documents into the "users" collection. The documents inserted with "_id" are:', result.length, result);
 		 			        }
 		 			        //Close connection
 		 			        db.close();
 		 			      })
		
 			
 			
 		})
 	
 		
	
 		.on("end", function(){
 			console.log("done");
 		})
 		
 		
    	
    	.on('error', function (err) {
    		console.log('ERROR: ' + err.message);
    	})
    	
    }); 
 

    res.render('index', { title: 'Express' });
	
		});
app.get('/users', user.list);

http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});


