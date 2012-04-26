package models.java.mongodb;

import com.mongodb.*;

import java.util.Set;

/*
MongoDBScalaStarter$ erstellt eine Datenbank "techradar".
Darin dann eine Collection "item" für die Verwaltung der Items des Technologieradars.
Anschließend werden insert, update und find aus der Java API verwendet.

Setup:
- MongoDB ist gestartet mit Befehl "mongod" (default port)
- default file storage => /data/db (das Verzeichnis muss vor dem Start der DB per Hand angelegt werden)
- mongodb-VERSION.jar muss im Classpath sein
- Um die Inhalte der DB zu sehen empfiehlt sich der MongoDB Data Viewer "mViewer": https://github.com/Imaginea/mViewer
 */
public class MongoDBStarter {

    public static void main(String[] args) throws Exception {

        // ######################################
        // Create DB and Collection
        // ######################################

        Mongo mongo = new Mongo();
        DB db = mongo.getDB("techradar");

        // Collection in MongoDB is similar to Table in RDBMS
        Set<String> collectionNames = db.getCollectionNames();

        System.out.println("Collection Names: ");
        for (String s : collectionNames) {
            System.out.println(s);
        }

        DBCollection collection = db.getCollection("item");
        collection.drop();

        // ######################################
        // Insert Documents
        // ######################################

        /*
        JSON document for a Technology Radar item:

        {
                "name" : "MongoDB",
                "type" : "database",
                "sector" : "tools",
                "circle" : "hold",
                "information" : {
                            "url" : "http://www.mongodb.org/",
                            "confluence" : "http://intranet.mwea.de/display/MWeaMethodik/MongoDB"
                         }
        }
        */

        // add the above JSON document with Java API
        // (a document in MongoDB is similar to a record in RDBMS)
        BasicDBObject document = new BasicDBObject();
        document.put("name", "MongoDB");
        document.put("type", "database");
        document.put("sector", "tools");
        document.put("circle", "hold");

        BasicDBObject information = new BasicDBObject();
        information.put("url", "http://www.mongodb.org/");
        information.put("confluence", "http://intranet.mwea.de/display/MWeaMethodik/MongoDB");
        document.put("information", information);

        collection.insert(document);


        // add 10 other documents:
        for (int i=0; i < 10; i++) {
            collection.insert(new BasicDBObject().append("i", i));
        }

        // Count elements of collection
        System.out.println("count: " + collection.getCount());

        // ######################################
        // Find and Update Document
        // ######################################

        // Find first document:
        DBObject myDoc = collection.findOne();
        System.out.println("findOne: " + myDoc);

        // BEFORE UPDATE

        // Find document where key "name" has value "MongoDB" (using DBCursor):

        BasicDBObject query = new BasicDBObject();
        query.put("name", "MongoDB");
        DBCursor cur = collection.find(query);
        DBObject o = cur.next(); // <-- exactly one document found!

        System.out.println("before update: " + o);

        // update a single field: (circle from hold to assess)
        BasicDBObject newQuery = new BasicDBObject();
        newQuery.put("circle", "hold");

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("circle", "assess");

        // Targeted update via Modifier Operation: $set
        BasicDBObject set = new BasicDBObject("$set", newDocument);

        collection.update(newQuery, set);

        // ######################################
        // Find Document after Update
        // ######################################
        System.out.println("after update: " + collection.find(query).next());





    }
}
