package servletManager.functions; /**
 * Created by Spurthy on 5/11/2015.
 */


import com.mongodb.*;

import java.util.Set;

public class JavaMongoDBConnection {

    public static void main(String[] args) {
        String textUri =   "mongodb://cmpe273:cmpe273@ds061741.mongolab.com:61741/cmpeproject273";
        MongoClientURI uri = new MongoClientURI(textUri);
        MongoClient mongoClient = new MongoClient(uri);




        //List<String> databases = mongoClient.getDatabaseNames();


            //System.out.println("- Database: " + dbName);

            DB db = mongoClient.getDB("cmpeproject273");

            Set<String> collections = db.getCollectionNames();
            for (String colName : collections) {
                System.out.println("\t + Collection: " + colName);
            }
        DBCollection col = db.getCollection("tweets1");

        DBCursor results = col.find();
        for (DBObject result : results) {


            System.out.println(result.toString());
        }


        mongoClient.close();

    }
}