package servletManager.functions;

import com.mongodb.*;
import servletManager.model.AssociatedHashTag;
import servletManager.model.CountByHour;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Spurthy on 5/12/2015.
 */
public class OperateMongoDBCollections
{
    public static void deleteMongoCollections(){
        String textUri =   "mongodb://cmpe273:cmpe273@ds061741.mongolab.com:61741/cmpeproject273";
        MongoClientURI uri = new MongoClientURI(textUri);
        MongoClient mongoClient = new MongoClient(uri);
        DB db = mongoClient.getDB("cmpeproject273");
        Set<String> collections = db.getCollectionNames();
        for (String colName : collections) {
            if (!colName.equalsIgnoreCase("searchDetails")) {
                DBCollection col = db.getCollection(colName);
                col.drop();
            }
        }
        mongoClient.close();
    }

    public static void addSearchStringsToDB(String ht1, String ht2){
        String textUri =   "mongodb://cmpe273:cmpe273@ds061741.mongolab.com:61741/cmpeproject273";
        MongoClientURI uri = new MongoClientURI(textUri);
        MongoClient mongoClient = new MongoClient(uri);
        DB db = mongoClient.getDB("cmpeproject273");
        DBCollection collection = db.getCollection("searchDetails");
        BasicDBObject document1 = new BasicDBObject();
        document1.put("hashValue", ht1);
        BasicDBObject document2 = new BasicDBObject();
        document2.put("hashValue", ht2);
        collection.insert(document1);
        collection.insert(document2);

        mongoClient.close();
    }

    public static List<CountByHour> addTagCountByHourFromDBTweets1() {
        List<CountByHour> countByHourList = new ArrayList<CountByHour>();
        String textUri = "mongodb://cmpe273:cmpe273@ds061741.mongolab.com:61741/cmpeproject273";
        MongoClientURI uri = new MongoClientURI(textUri);
        MongoClient mongoClient = new MongoClient(uri);
        DB db = mongoClient.getDB("cmpeproject273");
        DBCollection collection = db.getCollection("tweets1");
        List<DBObject> tagCountList = collection.find().toArray();


        for (DBObject basicDBObject : tagCountList) {

            CountByHour countByHour = new CountByHour();
            String wholeDate = String.valueOf(basicDBObject.get("date"));
            String[] splits = wholeDate.split("-");

            countByHour.setHour(Integer.parseInt(splits[3]));
            countByHour.setCount((Integer) basicDBObject.get("count"));
            countByHourList.add(countByHour);
        }
        for(CountByHour countByHour:countByHourList){
            System.out.println(countByHour.getHour() + "  " + countByHour.getCount());
        }
        mongoClient.close();

        return countByHourList;
    }

    public static List<CountByHour> addTagCountByHourFromDBTweets2() {
        List<CountByHour> countByHourList = new ArrayList<CountByHour>();
        String textUri = "mongodb://cmpe273:cmpe273@ds061741.mongolab.com:61741/cmpeproject273";
        MongoClientURI uri = new MongoClientURI(textUri);
        MongoClient mongoClient = new MongoClient(uri);
        DB db = mongoClient.getDB("cmpeproject273");
        DBCollection collection = db.getCollection("tweets2");
        List<DBObject> tagCountList = collection.find().toArray();


        for (DBObject basicDBObject : tagCountList) {

            CountByHour countByHour = new CountByHour();
            String wholeDate = String.valueOf(basicDBObject.get("date"));
            String[] splits = wholeDate.split("-");

            countByHour.setHour(Integer.parseInt(splits[3]));
            countByHour.setCount((Integer) basicDBObject.get("count"));
            countByHourList.add(countByHour);
        }
        for(CountByHour countByHour:countByHourList){
            System.out.println(countByHour.getHour() + "  " + countByHour.getCount());
        }
        mongoClient.close();

        return countByHourList;
    }

    public static List<AssociatedHashTag> addAssociatedHashTagsFromDB() {
        List<AssociatedHashTag> hashCountList = new ArrayList<AssociatedHashTag>();
        String textUri = "mongodb://cmpe273:cmpe273@ds061741.mongolab.com:61741/cmpeproject273";
        MongoClientURI uri = new MongoClientURI(textUri);
        MongoClient mongoClient = new MongoClient(uri);
        DB db = mongoClient.getDB("cmpeproject273");
        DBCollection collection = db.getCollection("tag1_hashes");
        List<DBObject> tagCountList = collection.find().toArray();


        for (DBObject basicDBObject : tagCountList) {

            AssociatedHashTag associatedHashTag = new AssociatedHashTag();
            associatedHashTag.setHashTagName(String.valueOf(basicDBObject.get("hash")));
            associatedHashTag.setCount((Integer) basicDBObject.get("count"));
            hashCountList.add(associatedHashTag);
        }
        for(AssociatedHashTag associatedHashTag:hashCountList){
            System.out.println(associatedHashTag.getHashTagName() + "  " + associatedHashTag.getCount());
        }
        mongoClient.close();

        return hashCountList;
    }

    public static List<String> getSearchResultsFromDB() {
        List<String> hashesList = new ArrayList<String>();
        String textUri = "mongodb://cmpe273:cmpe273@ds061741.mongolab.com:61741/cmpeproject273";
        MongoClientURI uri = new MongoClientURI(textUri);
        MongoClient mongoClient = new MongoClient(uri);
        DB db = mongoClient.getDB("cmpeproject273");
        DBCollection collection = db.getCollection("searchDetails");
        List<DBObject> tagCountList = collection.find().toArray();
        for (DBObject basicDBObject : tagCountList) {
            hashesList.add(String.valueOf(basicDBObject.get("hashValue")));
        }
        mongoClient.close();
        return hashesList;
    }


}
