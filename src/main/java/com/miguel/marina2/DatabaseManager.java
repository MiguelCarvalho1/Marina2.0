package com.miguel.marina2;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String DATABASE_NAME = "Marina2";
    private static final String COLLECTION_NAME = "vessels";

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public DatabaseManager() {
        mongoClient = MongoClients.create();
        database = mongoClient.getDatabase(DATABASE_NAME);
        collection = database.getCollection(COLLECTION_NAME);
    }


    public void insertDocument(Document document) {
        collection.insertOne(document);
    }


    public Document findDocument(String key, Object value) {
        return collection.find(new Document(key, value)).first();
    }


    public void updateDocument(String key, Object value, Document updatedDocument) {
        collection.updateOne(new Document(key, value), new Document("$set", updatedDocument));
    }


    public void deleteDocument(String key, Object value) {
        collection.deleteOne(new Document(key, value));
    }

    public void insertAnchorages(List<Anchorages> anchoragesList) {
        MongoCollection<Document> anchoragesCollection = database.getCollection("anchorages");
        for (Anchorages anchorage : anchoragesList) {
            Document document = new Document()
                    .append("id", anchorage.getId())
                    .append("pierType", anchorage.getPierType())
                    .append("length", anchorage.getLength())
                    .append("price", anchorage.getPrice())
                    .append("places", anchorage.getPlaces());

            anchoragesCollection.insertOne(document);
        }
    }

    public List<Anchorages> getAllAnchorages() {
        MongoCollection<Document> anchoragesCollection = database.getCollection("anchorages");
        List<Anchorages> anchoragesList = new ArrayList<>();
        FindIterable<Document> documents = anchoragesCollection.find();

        try (MongoCursor<Document> cursor = documents.iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Anchorages anchorage = new Anchorages(
                        doc.getInteger("id"),
                        doc.getString("pierType").charAt(0),
                        doc.getDouble("length"),
                        doc.getDouble("price"),
                        doc.getInteger("places")
                );
                anchoragesList.add(anchorage);
            }
        }
        return anchoragesList;
    }


    public void insertAdmin(Admin admin) {
        MongoCollection<Document> adminCollection = database.getCollection("admins");

        Document adminDocument = new Document()
                .append("id", admin.getId())
                .append("name", admin.getName())
                .append("username", admin.getUserName())
                .append("password", admin.getPassword());

        adminCollection.insertOne(adminDocument);
    }

    public void close() {
        mongoClient.close();
    }



}
