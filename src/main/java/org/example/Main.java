package org.example;

import com.mongodb.client.*;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import java.util.Scanner;

import static com.mongodb.client.model.Filters.eq;

public class Main {
    public static MongoDatabase database =  Conexiones.ofrecerDatabase();

    public static void main(String[] args) {

        menu();

    }

    /**
     * Menú con las diferentes opciones del crud. Cada opción llama al método correspondiente que
     * realizará la operación necesaria.
     */
    private static void menu(){

        Scanner sc = new Scanner(System.in);
        int opcion;

        opciones();
        opcion=sc.nextInt();

        while (opcion!=11){

            switch (opcion){

                case 1:
                  insertarCiudadania();
                    break;

                case 2:
                    borrarRegistroPorIdCiudadania("63ecbf257ff81f9aba34a015");
                    break;

                case 3:
                   actualizarRegistroCiudadania();
                    break;

                case 4:
                    mostrarTodosRegistrosCiudadania();
                    break;

                case 5:
                   mostrarRegistroCiudadaniaPorNombre("Lara actualizada");
                    break;


                case 6:
                    insertarPasta();
                    break;

                case 7:
                    borrarRegistroPorIdPasta("63ec9cca7ff81f9abad87d34");
                    break;

                case 8:
                    actualizarRegistroPasta();
                    break;

                case 9:
                    mostrarTodosRegistrosPasta();
                    break;

                case 10:
                    mostrarRegistroPastaPorNombre("Tagliatelle");
                    break;



                default:
                    System.out.println("Opción incorrecta");
            }

            opciones();
            opcion=sc.nextInt();

        }

    }

    /**
     * Método que muestra en pantalla las opciones del menu
     */
    private static void opciones(){
        System.out.println("1. Insertar Ciudadania");
        System.out.println("2. Eliminar Ciudadania");
        System.out.println("3. Actualizar Ciudadania ");
        System.out.println("4. Mostrar datos de Ciudadania");
        System.out.println("5. Mostrar datos de un registro de Ciudadania");


        System.out.println("6. Insertar Pastas");
        System.out.println("7. Eliminar Pastas");
        System.out.println("8. Actualizar Pastas ");
        System.out.println("9. Mostrar datos de Pastas");
        System.out.println("10. Mostrar datos de un registro de Pastas");


        System.out.println("11. Salir");
    }



    /**  Ciudadania  **/

    /**
     * Método que primero se obtiene un objeto de la colección de pastas y se guarda en una variable de tipo
     * Document. Luego se crea un nuevo objeto de la colección de Ciudadania y se rellenan los datos
     * correspondientes, rellenando la pasta favorita con el objeto de pastas creado anteriormente.
     */
    public static void insertarCiudadania(){

        MongoCollection<Document> collection = database.getCollection("Pastas");
        Document doc = collection.find(eq("NombrePasta", "Fideos")).first();

        // Se inserta un nuevo registro
        database .getCollection("Ciudadania").insertOne(
                new Document()
                        .append("Fecha_muerte","1980-02-12")
                        .append("Fecha_nacimiento","1910-02-12")
                        .append("KG_pasta",60)
                        .append("Pasta_favorita",doc)
                        .append("Sexo","H")
                        .append("apellidos","apellido2")
                        .append("nombre","Lana"));
    }


    /**
     * Método que recibe como parámetro un String con el nombre de las personas
     * que será introducido como parámetro de búsqueda para recoger una persona con
     * ese nombre. Luego se mostrará por pantalla el json de esa persona.
     * @param nombre
     */
    public static void mostrarRegistroCiudadaniaPorNombre(String nombre){
        MongoCollection<Document> collection = database.getCollection("Ciudadania");
        Document doc = collection.find(eq("nombre", nombre)).first();
        if (doc != null) {
            System.out.println(doc.toJson());
        } else {
            System.out.println("No se encontró.");
        }

    }

    /**
     * Método que servirá para borrar una persona de la base de datos, para ello se recoge como
     * parámetro un id de mongo, que se pasará al método deleteOne para que busque ese id y
     * lo borre.
     * @param id
     */
    public static void borrarRegistroPorIdCiudadania(String id){
        MongoCollection<Document> collection = database.getCollection("Ciudadania");
        collection.deleteOne(eq("_id",new ObjectId(id)));
    }


    /**
     * Método que mostrará por pantalla todos los registros que tenga la tabla de ciudadania. Para ello se crea
     * un objeto de tipo MongoCollection donde se recogerá la colección de ciudadania y luego se crea un
     * objeto de tipo FindIterable donde se meterá todos lso registros de esa colección. Por último se recorrerá
     * con un foreach y se irá mostrando por pantalla todos los datos.
     */
    public static void mostrarTodosRegistrosCiudadania(){
        MongoCollection<Document> collection = database.getCollection("Ciudadania");
            FindIterable<Document> document =  collection.find();
        for(Document doc : document) {

            System.out.println(doc.toJson());
        }
    }

    /**
     * Método que actualizará un registro. Par aello se recoge la colección correspondiente y se crea un objeto
     * Document con el registro a cambiar. Luego se crea un objeto Bson con los datos que se desea cambiar. Por
     * último, se hace la actualización pasando primero el objeto a actualizar, luego los nuevos datos y por último
     * un objeto de tipo UpdateOptions
     */
    public static void actualizarRegistroCiudadania(){

        MongoCollection<Document> collection = database.getCollection("Ciudadania");
        Document doc = collection.find(eq("nombre", "Lana")).first();

        Bson actualizar = Updates.combine(
                Updates.set("nombre", "Lara actualizada")
        );

        UpdateOptions options = new UpdateOptions().upsert(true);

        database.getCollection("Ciudadania").updateMany(doc, actualizar, options);

    }



    /**  Pastas  **/

    /**
     * Método insertar de la tabla pastas.
     */
    public static void insertarPasta(){

        database.getCollection("Pastas").insertOne(
                new Document().append("NombrePasta","Pasta nueva")
        );
    }


    /**
     * Igual que el método de mostrarRegistroCiudadaniaPorNombre pero con la tabla Pastas
     * @param nombre
     */
    public static void mostrarRegistroPastaPorNombre(String nombre){
        MongoCollection<Document> collection = database.getCollection("Pastas");
        Document doc = collection.find(eq("NombrePasta", nombre)).first();
        if (doc != null) {
            System.out.println(doc.toJson());
        } else {
            System.out.println("No se encontró.");
        }

    }

    /**
     * Igual que el método borrarRegistroPorIdCiudadania
     * @param id
     */
    public static void borrarRegistroPorIdPasta(String id){
        MongoCollection<Document> collection = database.getCollection("Pastas");
        collection.deleteMany(eq("_id",new ObjectId(id)));
    }


    /**
     * Igual que el método mostrarTodosRegistrosCiudadania
     */
    public static void mostrarTodosRegistrosPasta(){
        MongoCollection<Document> collection = database.getCollection("Pastas");
        FindIterable<Document> document =  collection.find();
        for(Document doc : document) {

            System.out.println(doc.toJson());
        }
    }

    /**
     * Igual que el método actualizarRegistroCiudadania
     */
    public static void actualizarRegistroPasta(){

        MongoCollection<Document> collection = database.getCollection("Pastas");
        Document doc = collection.find(eq("NombrePasta", "Spagueti")).first();

        Bson actualizar = Updates.combine(
                Updates.set("NombrePasta", "Spagueti actualizado")
        );

        UpdateOptions options = new UpdateOptions().upsert(true);

        database.getCollection("Pastas").updateMany(doc, actualizar, options);

    }




}