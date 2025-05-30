package br.uneb.astrojumper.tiles;

import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class FinalSpaceship extends TileObject implements ITileObject {

    public FinalSpaceship(PlayScreen playScreen, MapObject bounds) {
        super(playScreen, bounds);
        body.setType(BodyDef.BodyType.StaticBody);

        fixture.getFilterData().categoryBits = Constants.FINAL_SPACESHIP_BIT;
        fixture.getFilterData().maskBits = Constants.PLAYER_BIT;
    }

    // termina a fase
    @Override
    public void colide() {
        setCategoryFilter(Constants.DESTROYED_BIT);
        playScreen.setGameOver(true);
        playScreen.setCompleted(true);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Batch batch) {

    }

    @Override
    public void dispose() {

    }
}
