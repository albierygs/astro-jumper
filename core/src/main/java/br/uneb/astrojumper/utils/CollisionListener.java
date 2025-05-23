package br.uneb.astrojumper.utils;

import br.uneb.astrojumper.tiles.TileObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

public class CollisionListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Gdx.app.log("Begin Contact", "");
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (TileObject.class.isAssignableFrom(fixA.getUserData().getClass()) ||
                TileObject.class.isAssignableFrom(fixB.getUserData().getClass())) {

                Fixture tile = TileObject.class.isAssignableFrom(fixA.getUserData().getClass()) ? fixA : fixB;
                Fixture other = tile == fixA ? fixB : fixA;

                ((TileObject) tile.getUserData()).colide();
            }
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
