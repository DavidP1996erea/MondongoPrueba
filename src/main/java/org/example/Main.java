package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class Main {
    public static void main(String[] args) {



        ConnectionString connectionString = new ConnectionString("mongodb+srv://David:1234@prueba.r0l0tfy.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("prueba");

        // Se inserta un nuevo registro
        database.getCollection("Alumnos").insertOne(new Document().append("nombre","Antonio"));

            MongoCollection<Document> collection = database.getCollection("Alumnos");
            Document doc = collection.find(eq("nombre", "Antonio")).first();
            if (doc != null) {
                System.out.println(doc.toJson());
            } else {
                System.out.println("No matching documents found.");
            }












    }
}