console.log('Loading function');
var aws = require('aws-sdk');
var s3 = new aws.S3({apiVersion: '2006-03-01'});
var nodemailer = require("nodemailer");
var MongoClient = require('mongodb').MongoClient;
var async = require("async");

var smtpTransport = nodemailer.createTransport("SMTP",{
   service: "Gmail",
   auth: {
       user: "cmpe273team@gmail.com",
       pass: "CMPE273123"
   }
});

exports.handler = function(event, context){
  //get bucket and key details
    var bucket = event.Records[0].s3.bucket.name;
    var key = event.Records[0].s3.object.key;
    //variables to be defined before async starts
    var hashTag1="";
          var hashTag2="";
          var startTime="";
          var endTime="";
          var emailId="";
          var count1=0;
          var count2=0;

    async.waterfall([
      //get data from s3
        function(callback){
    console.log('Getting content from S3...');
    s3.getObject({Bucket: bucket, Key: key}, function(err, data) {
        if (err) {
            console.log("Error getting object " + key + " from bucket " + bucket +
                ". Make sure they exist and your bucket is in the same region as this function.");
            context.fail ("Error getting file: " + err)      
        } else {
          textData = data.Body.toString('utf8');
          var content= textData.split(",");
          //console.log(content.length);
          hashTag1=content[0];
          hashTag2=content[1];
          startTime=content[2];
          endTime=content[3];
          emailId=content[4];
          console.log(emailId);
          callback(null);
        }
      });
  },
    function (callback){
      console.log('Connecting to mongodb...');
      MongoClient.connect("mongodb://cmpe273:cmpe273@ds061741.mongolab.com:61741/cmpeproject273", 
        function(err, db) {
  if(err) { console.log('Unable to connect to the mongoDB server. Error:', err); 
    return console.dir(err); }
    db.collection('tweets1',function(err, collection){
      if(!err){
        console.log(startTime);
        collection.findOne({'date': startTime}, function(err, item) {
              if(item!=null){
                count1=item.count;
                console.log("Count1:"+count1);
                console.log(item);
                db.close();
                callback(null);

              }
              else
                {console.log("No doc for the start time in tweets1");
              db.close();
            callback(null);
          }
            });
      } else{console.log("No tweets1 found"); db.close(); console.fail("No proper comparison available");}
    }); //end db.collection
        });
    },
    function (callback){
      console.log('Connecting to mongodb...');
      MongoClient.connect("mongodb://cmpe273:cmpe273@ds061741.mongolab.com:61741/cmpeproject273", 
        function(err, db) {
  if(err) { console.log('Unable to connect to the mongoDB server. Error:', err); 
    return console.dir(err); }
    db.collection('tweets2',function(err, collection){
      if(!err){
        console.log(startTime);
        collection.findOne({'date': startTime}, function(err, item) {
              if(item!=null){
                count2=item.count;
                console.log("Count2:"+count2);
                console.log(item);
                db.close();
                callback(null);
              }
              else
                {console.log("No doc for the start time in tweets2");
                db.close();
                callback(null);
            }
            });
      } else{console.log("No tweets2 found"); 
      db.close();
      console.fail("No proper comparison available");}
    }); //end db.collection
        });
    },
    function(callback){
      //begin mail
          console.log("Trying to send mail");
          console.log("Count for tag1: "+count1);
          console.log("Count for tag2: "+count2);
          var timeHrs1= startTime.split("-");
          var hrs1=timeHrs1[3];
          var timeHrs2=endTime.split("-");
          var hrs2=timeHrs2[3];
          smtpTransport.sendMail({
               from: "Tweet Totaller ✔ <cmpe273team@gmail.com>", // sender address
               to: emailId, // comma separated list of receivers
               subject: "Requested Tweet Results for hashtags - "+hashTag1+" and "+hashTag2+" - "+hrs1+" to "+hrs2+" hours", // Subject line
               text: "Here are the results for the 2 hashtags that you searched. The number of tweets for "+hashTag1+" is "+count1+". The number of tweets for " +hashTag2+" is "+count2+". "// plaintext body 
                  }, function(error, response){
               if(error){
                   console.log(error);
                   //context.fail ("Error sending email: " + err)
               }else{
                   console.log("Message sent: " + response.message);
                   callback();
                   //context.succeed();
               }

            });
        //end mail
        callback();
    }
 ], function(err,result){
    
    //if (err) context.done(err, "Drat!!");
    //if (!err) context.done(null, "Count successfully sent.");
  });
  };


