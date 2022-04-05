package com.couchbase.example.config;

import java.util.List;

import java.security.cert.X509Certificate;

import com.couchbase.client.core.deps.io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import com.couchbase.client.core.env.SecurityConfig;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.ClusterOptions;
import com.couchbase.client.java.env.ClusterEnvironment;
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
        return Cluster.connect(dbProp.getHostName(), ClusterOptions.clusterOptions(
              dbProp.getUsername(),
              dbProp.getPassword()
            ).environment(getClusterEnvironment())
          );
    }

    public ClusterEnvironment getClusterEnvironment() {
      ClusterEnvironment.Builder environmentBuilder = ClusterEnvironment.builder();
      
      /* Uncomment this for Capella connections
      SecurityConfig.Builder securityConfig = SecurityConfig.enableTls(true)
        .trustManagerFactory(InsecureTrustManagerFactory.INSTANCE);
      environmentBuilder.securityConfig(securityConfig);
      */

      return environmentBuilder.build();
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
