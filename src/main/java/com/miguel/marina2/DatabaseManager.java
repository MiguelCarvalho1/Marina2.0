package com.miguel.marina2;

import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.result.UpdateResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

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

    public void insertAdmin(Admin admin) {
        MongoCollection<Document> adminCollection = database.getCollection("admins");

        Document adminDocument = new Document()
                .append("id", admin.getId())
                .append("name", admin.getName())
                .append("username", admin.getUserName())
                .append("password", admin.getPassword());

        adminCollection.insertOne(adminDocument);
    }

    //Client insert, update e remove

    public void insertClient(Client client){
        try {
            MongoCollection<Document> clientCollection = database.getCollection("client");

            Document clientDocument = new Document()
                    .append("id", client.getId())
                    .append("name", client.getName())
                    .append("email", client.getEmail())
                    .append("phone", client.getPhone());

            clientCollection.insertOne(clientDocument);

            System.out.println("Client inserted successfully.");
        } catch (Exception e) {
            System.out.println("Error inserting client: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateClient(Client client , Stage stage){
        MongoCollection<Document> clientCollection = database.getCollection("client");

        Bson filter = eq("id", client.getId());
        Document updateDoc = new Document()
                .append("name", client.getName())
                .append("email", client.getEmail())
                .append("phone", client.getPhone());

        UpdateResult updateResult = clientCollection.updateOne(filter, new Document("$set", updateDoc));

        if (updateResult.getModifiedCount() == 0) {

            throw new RuntimeException("Cliente não encontrado para atualização");
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("clientForm.fxml"));
           AnchorPane root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // ou lidar com a exceção de forma apropriada
        }
    }

    public void deleteClient(int clientId){
        MongoCollection<Document> clientCollection = database.getCollection("client");

        Bson filter = eq("id", clientId);

        DeleteResult deleteResult = clientCollection.deleteOne(filter);

        if (deleteResult.getDeletedCount() == 0) {

            throw new RuntimeException("Cliente não encontrado para remoção");
        }
    }

    public void close() {
        mongoClient.close();
    }


    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        MongoCollection<Document> clientCollection = database.getCollection("client");

        try (MongoCursor<Document> cursor = clientCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();

                Client client = new Client();
                client.setId(doc.getInteger("id"));
                client.setName(doc.getString("name"));
                client.setEmail(doc.getString("email"));
                client.setPhone(doc.getInteger("phone"));

                clients.add(client);
            }
        }

        return clients;
    }

    //Country

    public void insertCountry(Country country){
        try {
            MongoCollection<Document> countryCollection = database.getCollection("country");

            Document countryDocument = new Document()
                    .append("id", country.getId())
                    .append("name", country.getName())
                    .append("code", country.getCode());

            countryCollection.insertOne(countryDocument);

            System.out.println("Client inserted successfully.");
        } catch (Exception e) {
            System.out.println("Error inserting client: " + e.getMessage());
            e.printStackTrace();
        }
    }

    }

