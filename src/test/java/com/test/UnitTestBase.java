package com.test;

import org.junit.After;
import org.junit.Before;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class UnitTestBase {
	private MongoClient mongo;
	private Morphia morphia;
	
	protected Datastore ds;
	protected DB db;
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 27017;
	private static final String DB_NAME = "model_db";

	@Before
	public void init() {
		mongo = new MongoClient(HOST, PORT);
		morphia = new Morphia();
		db = new DB(mongo,DB_NAME);
		ds = morphia.createDatastore(mongo, DB_NAME);
	}

	@After
	public void desotry() {
		mongo.close();
	}
}
