package br.uneb.astrojumper.utils;

import br.uneb.astrojumper.entities.Astronaut;
import br.uneb.astrojumper.entities.Meteor;
import br.uneb.astrojumper.entities.Ray;
import br.uneb.astrojumper.tiles.Damage;
import br.uneb.astrojumper.tiles.FinalSpaceship;
import com.badlogic.gdx.physics.box2d.*;

public class CollisionListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        Object userDataA = fixA.getUserData();
        Object userDataB = fixB.getUserData();

        // colisão do meteoro com o chão para acionar a explosão
        if (userDataA instanceof Meteor) {
            ((Meteor) userDataA).colide();
        } else if (userDataB instanceof Meteor) {
            ((Meteor) userDataB).colide();
        }

        // lógica para colisão entre astronauta e meteoro
        if ((userDataA instanceof Meteor && userDataB instanceof Astronaut) ||
            (userDataA instanceof Astronaut && userDataB instanceof Meteor)) {

            // astronauta recebendo dano -- DESATIVADO
            /*
            if (userDataA instanceof Astronaut) {
                ((Astronaut) userDataA).receiveDamage(0, 0);
            } else {
                ((Astronaut) userDataB).receiveDamage(0, 0);
            }
            */

        } else if ((userDataA instanceof Damage && userDataB instanceof Astronaut) ||
            (userDataA instanceof Astronaut && userDataB instanceof Damage)) {

            // astronauta recebendo dano -- DESATIVADO
            /*
            if (userDataA instanceof Astronaut) {
                ((Astronaut) userDataA).receiveDamage(4f, -1);
            } else {
                ((Astronaut) userDataB).receiveDamage(4f, -1);
            }
            */

        } else if ((userDataA instanceof FinalSpaceship && userDataB instanceof Astronaut) ||
            (userDataA instanceof Astronaut && userDataB instanceof FinalSpaceship)) {

            // fim da fase
            if (userDataA instanceof FinalSpaceship) {
                ((FinalSpaceship) userDataA).colide();
            } else {
                ((FinalSpaceship) userDataB).colide();
            }
        } else if ((userDataA instanceof Ray && userDataB instanceof Astronaut) ||
            (userDataA instanceof Astronaut && userDataB instanceof Ray)) {

            // colisão com o raio
            if (userDataA instanceof Ray) {
                ((Ray) userDataA).colide();
            } else {
                ((Ray) userDataB).colide();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        Object userDataA = fixA.getUserData();
        Object userDataB = fixB.getUserData();

        // restaurando estado do astronauta quando a colisão termina
        if (userDataA instanceof Astronaut) {
            ((Astronaut) userDataA).resetDamageState();
        }
        if (userDataB instanceof Astronaut) {
            ((Astronaut) userDataB).resetDamageState();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
