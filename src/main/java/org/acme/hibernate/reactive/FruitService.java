package org.acme.hibernate.reactive;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.acme.hibernate.reactive.Fruit.FruitList;

@ApplicationScoped
public class FruitService {

    @Inject
    FruitRepository fruitRepository;

    @ConsumeEvent("find-all-fruits")
    public Uni<FruitList> findAllFruits(final String unused) {
        return fruitRepository.findAll().collectItems().asList().map(FruitList::new);
    }

    @ConsumeEvent("create-fruit")
    public Uni<Void> createFruit(final Fruit fruit) {
        return fruitRepository.create(fruit);
    }

}
