package edu.uoc.tfgmonitorsystem.common.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.DbSequence;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de secuencias encargado de generar la secuencia para una colección.
 */
@Service
public class DbSequenceService implements IDbSequenceService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public long generateDbSequence(final String seqName) {
        DbSequence counter = mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(seqName)),
                new Update().inc("sequence", 1), FindAndModifyOptions.options().returnNew(true).upsert(true),
                DbSequence.class);
        return !Objects.isNull(counter) ? counter.getSequence() : 1;
    }
}
