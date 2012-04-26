package models.java.mongodb;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.ObjectId;
import net.vz.mongodb.jackson.WriteResult;
import org.codehaus.jackson.annotate.JsonProperty;

/*
MongoDB Jackson Mapper
http://vznet.github.com/mongo-jackson-mapper/tutorial.html
 */
public class JacksonMapper {

    public static void main(String[] args) throws Exception {

        Mongo mongo = new Mongo();
        DB db = mongo.getDB("techradar");
        DBCollection dbCollection = db.getCollection("myobject");
        dbCollection.drop();


        JacksonDBCollection<MyObject, String> coll = JacksonDBCollection.wrap(dbCollection, MyObject.class,
                String.class);
        MyObject myObject = new MyObject();
        myObject.setName("Kai");
        WriteResult<MyObject, String> result = coll.insert(myObject);
        String id = result.getSavedId();
        MyObject savedObject = coll.findOneById(id.toString());

        System.out.println("saved Object => " + savedObject);
    }

    static class MyObject {

        String id;

        String name;

        @ObjectId
        @JsonProperty("_id")
        public String getId() {
            return id;
        }

        @ObjectId
        @JsonProperty("_id")
        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Id: ");
            sb.append(this.id);
            sb.append(" ### name: ");
            sb.append(this.name);

            return sb.toString();
        }
    }
}
