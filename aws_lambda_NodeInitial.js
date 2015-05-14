console.log('Loading function');
var aws = require('aws-sdk');
var fs = require('fs');
var s3 = new aws.S3({apiVersion: '2006-03-01'});

exports.handler = function(event, context) {
    var bucket = event.Records[0].s3.bucket.name;
    var key = event.Records[0].s3.object.key;
    console.log('Getting content from S3...');
    s3.getObject({Bucket: bucket, Key: key}, function(err, data) {
        if (err) {
            console.log("Error getting object " + key + " from bucket " + bucket +
                ". Make sure they exist and your bucket is in the same region as this function.");
            context.fail ("Error getting file: " + err)      
        } else {
            //var jsonData =  JSON.parse(data.Body.toString());
            textData = data.Body.toString('utf8');
            parsedBody = JSON.parse(textData);
            var sjsu = parsedBody.sjsu;
            var sjpd = parsedBody.sjpd;
            //First Hash Tag
            //console.log(sjsu.length);
            for( var i = 0; i < sjsu.length; i++)
               {
                    var obj1 =  sjsu[i];
                    console.log(obj1.time);
               } 
               var array1 = [];
               for( i = 0; i < sjsu.length; i++)
               {
                    obj1 =  sjsu[i];
                    if(array1.indexOf(obj1.location)<0 && obj1.location !== "")
                    array1.push(obj1.location);
               }
               //console.log(array1.length);
               if(array1.length !==0)
               console.log(array1);
               var reachCount1=0;
               for( i = 0; i < sjsu.length; i++)
               {
                    obj1 =  sjsu[i];
                    reachCount1 +=obj1.followerCount;
               }
               console.log(reachCount1);
           //Second Hash Tag
            //console.log(sjpd.length);
            for( i = 0; i < sjpd.length; i++)
               {
                    var obj2 =  sjpd[i];
                    console.log(obj2.time);
               } 
               var array2 = [];
               for( i = 0; i < sjpd.length; i++)
               {
                    obj2 =  sjpd[i];
                    if(array2.indexOf(obj2.location)<0 && obj1.location !== "")
                    array2.push(obj2.location);
               }
               //console.log(array2.length);
               if(array2.length !==0)
               console.log(array2);
               var reachCount2=0;
               for( i = 0; i < sjpd.length; i++)
               {
                    obj2 =  sjpd[i];
                    reachCount2 +=obj2.followerCount;
               }
               console.log(reachCount2);
            context.succeed();
        }
    });

};