var fs = require('fs');
var jf = require('jsonfile');
var util = require('util');
var HashMap = require('hashmap');
fs.readFile('hashtag.json', function(err,data)
{
   if(err)	return console.log(err);
   var jsonData =  JSON.parse(data.toString());
   var tag1 = jsonData.tag1;
   var tag2 = jsonData.tag2;
   var count_sjsu=tag1.length, count_sjpd =tag2.length;  
   var map_tag1 = new HashMap();
   var map_tag2 = new HashMap();
   var count = 0;
   var count2 = 0; 
  for( var i = 0; i < tag1.length; i++)
   {
	var obj =  tag1[i];
        if(! map_tag1.get(obj.time)) 
		map_tag1.set(obj.time,1);
	else { 
		count = map_tag1.get(obj.time)+1;
		map_tag1.set(obj.time,count);
        }
   }
   for( var i = 0; i < tag2.length; i++)
   {
	var obj =  tag2[i];
        if(! map_tag2.get(obj.time)) 
		map_tag2.set(obj.time,1);
	else { 
		count2 = map_tag2.get(obj.time)+1;
		map_tag2.set(obj.time,count2);
        }
   }  
   map_tag1.forEach(function(value,key){
   console.log("map1"+key+":"+value);
   });
   map_tag2.forEach(function(value,key){
   console.log("map2"+key+":"+value);
   });
  
   var MongoClient = require('mongodb').MongoClient;
   console.log("test");
   console.log("calling connect");
   MongoClient.connect("mongodb://cmpe273:cmpe273@ds043358.mongolab.com:43358/cmpe273project", function(err, db) {
		console.log("inside");
   var collection = db.collection('tweets');
   collection.insert(map_tag1);	
  console.log(" document inserted" );
}
)
}
);
