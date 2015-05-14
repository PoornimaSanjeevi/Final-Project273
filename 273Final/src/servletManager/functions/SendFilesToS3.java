package servletManager.functions;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

/**
 * Created by Spurthy on 5/13/2015.
 */
public class SendFilesToS3
{
    public static void putToS3(String tweetsFile, String tweetsFilePath, String csvFileName, String csvFilePath) {
        // credentials object identifying user for authentication
        // user must have AWSConnector and AmazonS3FullAccess for
        // this example to work
        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIAKUYRMSMQ",
                "nKADaVV7edQbHHYjIpgrKP0BH1c5\n");

        // create a client connection based on credentials
        AmazonS3 s3client = new AmazonS3Client(credentials);
        // create bucket - name must be unique for all S3 users
        String bucketName2 = "s3emailbucket338";
        String bucketName1 = "my338bucket";

        // upload file to folder and set it to public


        s3client.putObject(new PutObjectRequest(bucketName1, tweetsFile,new File(tweetsFilePath))
                .withCannedAcl(CannedAccessControlList.PublicRead));
        s3client.putObject(new PutObjectRequest(bucketName2, csvFileName, new File(csvFilePath
               ))
                .withCannedAcl(CannedAccessControlList.PublicRead));

    }

    }



