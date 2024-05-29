package com.miguel.marina2;

import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class DatabaseManager implements AutoCloseable {

    private static final String DATABASE_NAME = "Marina2";
    private static final String COLLECTION_NAME_VESSELS = "vessels";
    private static final String COLLECTION_NAME_CLIENTS = "clients";
    private static final String COLLECTION_NAME_ADMINS = "admins";
    private static final String COLLECTION_NAME_COUNTRIES = "countries";

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public DatabaseManager() {
        mongoClient = MongoClients.create("mongodb://localhost:27017"); // Use a connection string from your configuration
        database = mongoClient.getDatabase(DATABASE_NAME);
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    // Método para adicionar um documento ao banco de dados
    public void insertDocument(String collectionName, Document document) {
        getCollection(collectionName).insertOne(document);
    }

    // Método para buscar documentos no banco de dados
    public Document findDocument(String collectionName, String key, Object value) {
        return getCollection(collectionName).find(eq(key, value)).first();
    }

    // Método para atualizar um documento no banco de dados
    public void updateDocument(String collectionName, String key, Object value, Document updatedDocument) {
        getCollection(collectionName).updateOne(eq(key, value), new Document("$set", updatedDocument));
    }

    // Método para deletar um documento do banco de dados
    public void deleteDocument(String collectionName, String key, Object value) {
        getCollection(collectionName).deleteOne(eq(key, value));
    }

    private MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    public void insertAdmin(Admin admin) {
        Document adminDocument = new Document()
                .append("id", admin.getId())
                .append("name", admin.getName())
                .append("username", admin.getUserName())
                .append("password", admin.getPassword());
        insertDocument(COLLECTION_NAME_ADMINS, adminDocument);
    }

    public void insertClient(Client client) {
        Document clientDocument = new Document()
                .append("id", client.getId())
                .append("name", client.getName())
                .append("email", client.getEmail())
                .append("phone", client.getPhone());
        insertDocument(COLLECTION_NAME_CLIENTS, clientDocument);
    }

    public void updateClient(Client client, Stage stage) {
        Document updateDoc = new Document()
                .append("name", client.getName())
                .append("email", client.getEmail())
                .append("phone", client.getPhone());
        updateDocument(COLLECTION_NAME_CLIENTS, "id", client.getId(), updateDoc);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("clientForm.fxml"));
            AnchorPane root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(int clientId) {
        deleteDocument(COLLECTION_NAME_CLIENTS, "id", clientId);
    }

    public void insertCountry(Country country) {
        Document countryDocument = new Document()
                .append("id", country.getId())
                .append("name", country.getName())
                .append("code", country.getCode());
        insertDocument(COLLECTION_NAME_COUNTRIES, countryDocument);
    }

    public void insertVessel(Vessel vessel) {
        Document vesselDocument = new Document()
                .append("registration", vessel.getRegistration())
                .append("capitanName", vessel.getCapitanName())
                .append("numPassanger", vessel.getNumPassenger())
                .append("entryDate", vessel.getEntryDate())
                .append("pierType", String.valueOf(vessel.getPierType()))
                .append("exitDate", vessel.getExitDate())
                .append("numberDayStay", vessel.getNumberDaysStay())
                .append("countryId", String.valueOf(vessel.getCountryId()))
                .append("clientId", String.valueOf(vessel.getClientId()));
        insertDocument(COLLECTION_NAME_VESSELS, vesselDocument);
    }

    public void deleteVessel(String registration) {
        deleteDocument(COLLECTION_NAME_VESSELS, "registration", registration);
    }

    public boolean validateAdminCredentials(String username, String password) {
        Document admin = findDocument(COLLECTION_NAME_ADMINS, "username", username);
        return admin != null && admin.getString("password").equals(password);
    }

    @Override
    public void close() {
        mongoClient.close();
    }
}

