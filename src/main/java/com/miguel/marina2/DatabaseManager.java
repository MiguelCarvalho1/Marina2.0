package com.miguel.marina2;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String DATABASE_NAME = "marina2.0";
    private static final String COLLECTION_NAME = "vessels";

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public DatabaseManager() {
        mongoClient = MongoClients.create();
        database = mongoClient.getDatabase(DATABASE_NAME);
        collection = database.getCollection(COLLECTION_NAME);
    }

    // Método para adicionar um documento ao banco de dados
    public void insertDocument(Document document) {
        collection.insertOne(document);
    }

    // Método para buscar documentos no banco de dados
    public Document findDocument(String key, Object value) {
        return collection.find(new Document(key, value)).first();
    }

    // Método para atualizar um documento no banco de dados
    public void updateDocument(String key, Object value, Document updatedDocument) {
        collection.updateOne(new Document(key, value), new Document("$set", updatedDocument));
    }

    // Método para deletar um documento do banco de dados
    public void deleteDocument(String key, Object value) {
        collection.deleteOne(new Document(key, value));
    }

   /* public void insertAnchorages(Anchorages anchorage, MongoCollection<Document> collection) {
        Document doc = new Document("type", anchorage.getType())
                .append("classLevel", anchorage.getClassLevel())
                .append("length", anchorage.getLength())
                .append("dailyPrice", anchorage.getDailyPrice())
                .append("availableSpots", anchorage.getAvailableSpots());
        collection.insertOne(doc);
    }

    public List<Anchorages> getAllAnchorages(MongoCollection<Document> collection) {
        List<Anchorages> anchoragesList = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Anchorages anchorage = new Anchorages(
                        doc.getString("type"),
                        doc.getInteger("classLevel"),
                        doc.getDouble("length"),
                        doc.getDouble("dailyPrice"),
                        doc.getInteger("availableSpots")
                );
                anchoragesList.add(anchorage);
            }
        } finally {
            cursor.close();
        }
        return anchoragesList;
    }*/

}
