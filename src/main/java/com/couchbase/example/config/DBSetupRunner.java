package com.couchbase.example.config;

import com.couchbase.client.core.error.IndexExistsException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.example.model.DemoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * This class run after the application startup. It automatically setup all indexes needed
 */
@Component
public class DBSetupRunner implements CommandLineRunner {

    @Autowired
    private Cluster cluster;
    @Autowired
    private Bucket bucket;

    @Override
    public void run(String... args) {

        try {
            final QueryResult result = cluster.query("CREATE PRIMARY INDEX default_col_index ON "+bucket.name()
                    +"."+bucket.defaultScope().name()+"."
                    + bucket.defaultCollection().name());
            Thread.sleep(5000);
        } catch (IndexExistsException e){
            System.out.println(String.format("Collection's primary index already exists"));
        } catch (Exception e){
            System.out.println(String.format("General error <%s> when trying to create index ",e.getMessage()));
        }

        try {
            cluster.queryIndexes().createPrimaryIndex(bucket.name());
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("Primary index already exists on bucket "+bucket.name());
        }


        cluster.query("DELETE FROM "+bucket.name());
        cluster.query("DELETE FROM "+bucket.name()
                    +"."+bucket.defaultScope().name()+"."
                    + bucket.defaultCollection().name());

        bucket.defaultCollection().insert("user1", new DemoUser("user1", "Penko", "Ruben", 100));
        bucket.defaultCollection().insert("user2", new DemoUser("user2", "Malo", "Murad", 70));
        bucket.defaultCollection().insert("user3", new DemoUser("user3", "Hendry", "Pelagius", 20));
        bucket.defaultCollection().insert("user4", new DemoUser("user4", "Alejandra", "Loraine", 80));

    }
}
