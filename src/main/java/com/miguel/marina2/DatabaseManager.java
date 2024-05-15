package com.miguel.marina2;

import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    private ClientController clientController;
    public DatabaseManager() {
        mongoClient = MongoClients.create();
        database = mongoClient.getDatabase(DATABASE_NAME);
        collection = database.getCollection(COLLECTION_NAME);
    }

    public MongoDatabase getDatabase() {
        if (database == null) {
            // Substitua "nome_do_seu_banco_de_dados" pelo nome do seu banco de dados MongoDB
            database = mongoClient.getDatabase("Marina2");
        }
        return database;
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


    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

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


    //Vessel

    public void insertVessel(Vessel vessel){
        try {
            MongoCollection<Document> vesselCollection = database.getCollection("vessels");

            String pierType = String.valueOf(vessel.getPierType());

            String clientId= String.valueOf(vessel.getClientId());

            String countryId = String.valueOf(vessel.getCountryId());

            Document vesselDocument = new Document()
                    .append("registration", vessel.getRegistration())
                    .append("capitanName", vessel.getCapitanName())
                    .append("numPassanger", vessel.getNumPassenger())
                    .append("entryDate", vessel.getEntryDate())
                    .append("pierType", pierType)
                    .append("exitDate", vessel.getExitDate())
                    .append("numberDayStay", vessel.getNumberDaysStay())
                    .append("countryId", countryId)
                    .append("clientId", clientId);

            vesselCollection.insertOne(vesselDocument);

        }catch (Exception e) {
            System.out.println("Error inserting Vessel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteVessel(String registration) {
        MongoCollection<Document> clientCollection = database.getCollection("vessel");

        Bson filter = eq("registration", registration);

        DeleteResult deleteResult = clientCollection.deleteOne(filter);

        if (deleteResult.getDeletedCount() == 0) {

            throw new RuntimeException("Vessel don´t found");
        }
    }

    public void close() {
        mongoClient.close();
    }

    }

