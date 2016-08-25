package io.saagie.example.mongodb;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.Block;

import com.mongodb.client.FindIterable;
import org.bson.Document;

public class Main {

	private static final Logger logger = Logger.getLogger("io.saagie.example.mongo.ReadWrite");
	private static String connectionUri;

	public static void main(String[] args) throws IOException {

		if (args.length < 1) {
			logger.severe("1 arg is required :\n\t- connectionuri ex: mongodb://user:password@host:27017/database");
			System.err.println("1 arg is required :\n\t-connectionuri  ex:  mongodb://user:password@host:27017/database");
			System.exit(128);
		}

		// Creating examples BSON documents
		String restaurantJson1 = "{\"address\":{\"building\":\"1480\",\"coord\":[-73.9557413,40.7720266],\"street\":\"2 Avenue\",\"zipcode\":\"10075\"},\"borough\":\"Manhattan\",\"cuisine\":\"Italian\",\"name\":\"Vella\"}";
		String restaurantJson2 = "{\"address\":{\"building\":\"1007\",\"coord\":[-73.856077,40.848447],\"street\":\"Morris Park Ave\",\"zipcode\":\"10462\"},\"borough\":\"Bronx\",\"cuisine\":\"Bakery\",\"name\":\"Morris Park Bake Shop\"}";
		HashMap<String,Object> result1 = new ObjectMapper().readValue(restaurantJson1, HashMap.class);
		HashMap<String,Object> result2 = new ObjectMapper().readValue(restaurantJson2, HashMap.class);
		Document restaurant1 = new Document(result1);
		Document restaurant2 = new Document(result2);

		// Get MongoUri Connection
		connectionUri = args[0];
		String mongoDatabase = "sandbox";

		// ====== Connection to a MongoDB database with a user and a password
		MongoClientURI mongoUri = new MongoClientURI(connectionUri);
		MongoClient mongoClient = new MongoClient(mongoUri);
		MongoDatabase db = mongoClient.getDatabase(mongoDatabase);
		logger.info("Connected to MongoDB "+ mongoDatabase + " database");

		// ====== Inserting a list of BSON Documents in Mongodb
		logger.info("Begin Inserting documents in Mongodb");
		List<Document> places = Arrays.asList(restaurant1,restaurant2);
		db.getCollection("restaurants").insertMany(places);
		logger.info("End Inserting documents in Mongodb");

		// ====== Finding Documents
		logger.info("Finding documents in Mongodb");
		FindIterable<Document> iterable = db.getCollection("restaurants").find();
		iterable.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				logger.info(document.toString());
			}
		});

	}
}
