package org.acme.hibernate.reactive;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.hibernate.reactive.mutiny.Mutiny;

@ApplicationScoped
public class FruitRepository {

    @Inject
    Mutiny.Session mutinySession;

    public Multi<Fruit> findAll() {
        return mutinySession
            .createNamedQuery( "Fruits.findAll", Fruit.class ).getResults();
    }

    public Uni<Void> create(final Fruit fruit) {
        return mutinySession
            .persist(fruit)
            .chain(mutinySession::flush);
    }

}
