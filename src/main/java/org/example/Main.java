package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.Array;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

public class Main {
    public static MongoDatabase database =  Conexiones.ofrecerDatabase();

    public static void main(String[] args) {


        borrarRegistroPorIdCiudadania();

    }

    public static void insertarCiudadania(){

        MongoCollection<Document> collection = database.getCollection("Pastas");
        Document doc = collection.find(eq("NombrePasta", "Fideos")).first();
        var data = new ArrayList<>(doc.values());

        // Se inserta un nuevo registro
        database .getCollection("Ciudadania").insertOne(
                new Document().append("Fecha_muerte","1980-02-12").append("Fecha_nacimiento","1910-02-12")
                        .append("KG_pasta",60).append("Pasta_favorita",data.get(1)).append("Sexo","H")
                        .append("apellidos","apellido2").append("nombre","Lana"));


    }


    public static void mostrarRegistroCiudadaniaPorNombre(){
        MongoCollection<Document> collection = database.getCollection("Ciudadania");
        Document doc = collection.find(eq("nombre", "Lana")).first();
        if (doc != null) {
            System.out.println(doc.toJson());
        } else {
            System.out.println("No matching documents found.");
        }

    }

    public static void borrarRegistroPorIdCiudadania(){
        MongoCollection<Document> collection = database.getCollection("Ciudadania");
        collection.deleteMany(eq("_id",new ObjectId("63eca982e3c22c313d7af585")));
    }




    public static void mostrarTodosRegistrosCiudadania(){
        MongoCollection<Document> collection = database.getCollection("Ciudadania");
            FindIterable<Document> document =  collection.find();
        for(Document doc : document) {

            System.out.println(doc.toJson());
        }
    }

    public static void actualizarRegistro(){



    }
}