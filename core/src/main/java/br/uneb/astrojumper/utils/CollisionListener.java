package br.uneb.astrojumper.utils;

import br.uneb.astrojumper.entities.Astronaut;
import br.uneb.astrojumper.entities.Meteor;
import br.uneb.astrojumper.tiles.ITileObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

public class CollisionListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Gdx.app.log("Begin Contact", "");
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        Object userDataA = fixA.getUserData();
        Object userDataB = fixB.getUserData();

        Meteor meteor = null;
        Object otherObject = null;

        // verificando se algum dos objetos é o meteoro
        if (userDataA instanceof Meteor) {
            meteor = (Meteor) userDataA;
            otherObject = userDataB;
        } else if (userDataB instanceof Meteor) {
            meteor = (Meteor) userDataB;
            otherObject = userDataA;
        }

        // acionando a colisão do meteoro, se ele existir
        if (meteor != null) {
            meteor.colide();
        }

        // acionando a colisão dos objetos se não existir meteoro
        if (userDataA instanceof ITileObject && !(userDataA instanceof Meteor)) {
            ((ITileObject) userDataA).colide();
        }
        if (userDataB instanceof ITileObject && !(userDataB instanceof Meteor)) {
            ((ITileObject) userDataB).colide();
        }

        // lógica para colisão entre astronauta e meteoro
        if ((userDataA instanceof Meteor && userDataB instanceof Astronaut) ||
            (userDataA instanceof Astronaut && userDataB instanceof Meteor)) {
            Gdx.app.log("Collision", "Meteor hit Astronaut");

        }
    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("End Contact", "");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
