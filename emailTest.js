console.log('Loading function');
var aws = require('aws-sdk');
var s3 = new aws.S3({apiVersion: '2006-03-01'});
var nodemailer = require("nodemailer");
//var MongoClient = require('mongodb').MongoClient;

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

          console.log(content.length);
          hashTag1=content[0];
          hashTag2=content[1];
          startTime=content[2];
          endTime=content[3];
          emailId=content[4];
          console.log(emailId);

          console.log("Trying to send mail");
        smtpTransport.sendMail({
               from: "Tweet Totaller ✔ <cmpe273team@gmail.com>", // sender address
               to: emailId, // comma separated list of receivers
               subject: "Requested Tweet Results for hashtags - "+hashTag1+" and "+hashTag2, // Subject line
               text: "Here are the results for the 2 hashtags that you searched. The number of tweets for "+hashTag1+" is 2. The number of tweets for " +hashTag2+" is 4. "// plaintext body 
                  }, function(error, response){
               if(error){
                   console.log(error);
                   context.fail ("Error sending email: " + err)
               }else{
                   console.log("Message sent: " + response.message);
                   context.succeed();
               }
            });
            }

        });
  };


