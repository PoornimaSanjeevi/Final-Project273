console.log('Loading function');
var aws = require('aws-sdk');
var s3 = new aws.S3({apiVersion: '2006-03-01'});
var nodemailer = require("nodemailer");
var MongoClient = require('mongodb').MongoClient;

var smtpTransport = nodemailer.createTransport("SMTP",{
   service: "Gmail",
   auth: {
       user: "cmpe273team@gmail.com",
       pass: "CMPE273123"
   }
});

exports.handler = function(event, context){
    var bucket = event.Records[0].s3.bucket.name;
    var key = event.Records[0].s3.object.key;
    console.log('Getting content from S3...');
    s3.getObject({Bucket: bucket, Key: key}, function(err, data) {
        if (err) {
            console.log("Error getting object " + key + " from bucket " + bucket +
                ". Make sure they exist and your bucket is in the same region as this function.");
            context.fail ("Error getting file: " + err)      
        } else {
          textData = data.Body.toString('utf8');
          var content= textData.split(",");
          var hashTag1="";
          var hashTag2="";
          var startTime="";
          var endTime="";
          var emailId="";
          var count1=0;
          var count2=0;

          //console.log(content.length);
          hashTag1=content[0];
          hashTag2=content[1];
          startTime=content[2];
          endTime=content[3];
          emailId=content[4];
          console.log(emailId);
          MongoClient.connect("mongodb://cmpe273:cmpe273@ds061741.mongolab.com:61741/cmpeproject273", function(err, db) {
  if(err) { console.log('Unable to connect to the mongoDB server. Error:', err); 
    return console.dir(err); }
    db.collection('tweets1',function(err, collection){
      if(!err){
        console.log(startTime);
        collection.findOne({'date': startTime}, function(err, item) {
              if(!err){
                var count1=item.count;
                console.log("Count:"+count1);
                console.log(item);
              }
              else
                {console.log("No doc for the start time in tweets1");}
            });
        /*collection.find({'date': startTime}).toArray(function(err,docs){
          if(!err){
            db.close();
            var intCount=docs.lenght;
            console.log("docs length: "+intCount);
            if(intCount>0){
              var strJson="";
              console.log("Till now working");
            }else {
              //onErr(err,callback);
              console.log("error in finding doc with start date");
            }
          }
        });*///end collection.find
      } else{console.log("No tweets1 found"); db.close(); console.fail("No proper comparison available");}
    }); //end db.collection
    db.collection('tweets2',function(err, collection){
      if(!err){
        console.log(startTime);
        collection.findOne({'date': startTime}, function(err, item) {
              if(!err){
                count2=item.count;
                console.log("Count:"+count2);
                console.log(item);
              }
              else
                {console.log("No doc for the start time in tweets2");db.close();}
            });
      } else{console.log("No tweets2 found"); db.close();console.fail("No proper comparison available");}
    }); //end db.collection

  /*var collection1 = db.collection('tweets1');
  var collection2 = db.collection('tweets2');
  console.log("Db connected");
  collection1.find().toArray(function(err, docs) {
    console.log(docs);
});
  var dc1= collection1.find({date: startTime}).count();
  var dc2=collection2.find({date: startTime}).count();

  console.log(dc1);
  console.log(dc2);
            if (dc1>1)
            {
            var doc1 = collection1.findOne({date: startTime}, function(err, document) {
            console.log("Got for hashTag1");
              });
            count1=doc1.count;
            console.log(count1);
            }
          if(dc2>1)
            {
            var doc2 = collection2.findOne({date: startTime}, function(err, document) {
            console.log("Got for hashTag2");
              });
            count2=doc2.count;
            console.log(count2);
            }*/
            //close db
            //db.close();
            

        });
          //end mongodb
          //begin mail
          console.log("Trying to send mail");
          console.log("Count for tag1: "+count1);
          console.log("Count for tag2: "+count2);
          smtpTransport.sendMail({
               from: "Tweet Totaller ✔ <cmpe273team@gmail.com>", // sender address
               to: emailId, // comma separated list of receivers
               subject: "Requested Tweet Results for hashtags - "+hashTag1+" and "+hashTag2, // Subject line
               text: "Here are the results for the 2 hashtags that you searched. The number of tweets for "+hashTag1+" is "+count1+". The number of tweets for " +hashTag2+" is "+count2+". "// plaintext body 
                  }, function(error, response){
               if(error){
                   console.log(error);
                   context.fail ("Error sending email: " + err)
               }else{
                   console.log("Message sent: " + response.message);
                   context.succeed();
               }
               
            });
        //end mail


          
            }
            //end else

        });
        //end S3
  };


