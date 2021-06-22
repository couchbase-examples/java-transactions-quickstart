package com.couchbase.example.config;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.manager.bucket.BucketSettings;
import com.couchbase.client.java.manager.bucket.BucketType;
import com.couchbase.transactions.TransactionDurabilityLevel;
import com.couchbase.transactions.Transactions;
import com.couchbase.transactions.config.TransactionConfigBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouchbaseConfig {

    @Autowired
    private DBProperties dbProp;

    @Bean
    public Cluster getCouchbaseCluster(){
        return Cluster.connect(dbProp.getHostName(), dbProp.getUsername(), dbProp.getPassword());
    }

    @Bean
    public Bucket getCouchbaseBucket(Cluster cluster){

        //Creates the cluster if it does not exist yet
        if( !cluster.buckets().getAllBuckets().containsKey(dbProp.getBucketName())) {
            cluster.buckets().createBucket(BucketSettings.create(dbProp.getBucketName())
                    .bucketType(BucketType.COUCHBASE)
                    .ramQuotaMB(256));
        }
        return cluster.bucket(dbProp.getBucketName());
    }

    @Bean
    public Transactions transactions(final Cluster couchbaseCluster) {
      return Transactions.create(
        couchbaseCluster,
        TransactionConfigBuilder.create()
          .durabilityLevel(TransactionDurabilityLevel.NONE)
                // The configuration can be altered here, but in most cases the defaults are fine.
          .build()
      );
    }

}
