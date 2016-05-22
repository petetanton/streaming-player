package com.streaming.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.streaming.domain.stats.PlayerStat;
import org.apache.log4j.Logger;

public class DynamoDao {
    private final static Logger LOG = Logger.getLogger(DynamoDao.class);
    private final DynamoDBMapper mapper;

    public DynamoDao(AmazonDynamoDBClient client) {
        this.mapper = new DynamoDBMapper(client);
    }

    public void savePlayerStat(PlayerStat playerStat) {
        try {
            mapper.save(playerStat);
        } catch (Exception e) {
            LOG.error(e);
            e.printStackTrace();
        }
    }
}
