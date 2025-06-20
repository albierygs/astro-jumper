package br.uneb.astrojumper.entities.meteor;

import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.tiles.ITileObject;
import br.uneb.astrojumper.tiles.TileObject;
import br.uneb.astrojumper.utils.AssetLoader;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Meteor extends TileObject implements ITileObject {
    private float animationTime;
    private boolean exploding;
    private boolean finished;

    private BaseMeteor baseMeteor;

    public Meteor(PlayScreen playScreen, Vector2 initialPixelPosition, BaseMeteor baseMeteor) {
        super(playScreen, new RectangleMapObject(
            initialPixelPosition.x,
            initialPixelPosition.y,
            AssetLoader.get("meteor.png", Texture.class).getWidth(),
            AssetLoader.get("meteor.png", Texture.class).getHeight()
        ));

        // configurações do body do meteoro
        body.setType(BodyDef.BodyType.DynamicBody);
        body.setGravityScale(0); // remove a gravidade

        body.setTransform(initialPixelPosition.x / Constants.PIXELS_PER_METER,
            initialPixelPosition.y / Constants.PIXELS_PER_METER,
            0
        ); // posição inicial do meteoro

        Texture explosionTexture = AssetLoader.get("explosion.png", Texture.class);

        // configurações da forma da explosão
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((explosionTexture.getWidth() / 8f / 2f) / Constants.PIXELS_PER_METER,
            (explosionTexture.getHeight() / 2f) / Constants.PIXELS_PER_METER
        );

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.filter.categoryBits = Constants.METEOR_BIT;
        fdef.filter.maskBits = Constants.GROUND_BIT | Constants.PLAYER_BIT;

        fixture = body.createFixture(fdef);
        fixture.setUserData(this);
        shape.dispose();

        exploding = false;
        finished = false;
        animationTime = 0f;

        this.baseMeteor = baseMeteor;
    }

    @Override
    public void render(Batch batch) {
        if (exploding) {
            TextureRegion currentFrame = baseMeteor.getExplosionAnimation().getKeyFrame(animationTime);
            float width = currentFrame.getRegionWidth() / Constants.PIXELS_PER_METER;
            float height = currentFrame.getRegionHeight() / Constants.PIXELS_PER_METER;
            batch.draw(currentFrame, body.getPosition().x - width / 2, body.getPosition().y - height / 2, width, height);
        } else if (!finished) {
            Texture meteorTexture = AssetLoader.get("meteor.png", Texture.class);
            float width = meteorTexture.getWidth() / Constants.PIXELS_PER_METER;
            float height = meteorTexture.getHeight() / Constants.PIXELS_PER_METER;
            batch.draw(meteorTexture, body.getPosition().x - width / 2, body.getPosition().y - height / 2, width, height);
        }
    }

    @Override
    public void update(float delta) {
        if (!exploding) {
            body.setLinearVelocity(0, baseMeteor.getFallVelocity()); // atualiza a posição y do meteoro enquanto ele ainda não
            // atingiu o chão / astronauta
        } else {
            animationTime += delta;
            if (baseMeteor.getExplosionAnimation().isAnimationFinished(animationTime)) {
                finished = true;
            }
        }
    }

    @Override
    public void colide() {
        if (!exploding) {
            if (baseMeteor.getPlayer() != null && baseMeteor.getPlayer().getBody() != null) {
                float distance = this.body.getPosition().dst(baseMeteor.getPlayer().getBody().getPosition()); // Calcula a distância
                // entre o meteoro e o astronauta

                float maxDistance = 15f; // distância para volume total diminuir
                float maxVolume = 0.5f; // Volume máximo para o som da explosão

                // Calcula o volume com base na distância
                float volume = maxVolume * (1 - Math.min(1, distance / maxDistance));

                baseMeteor.getImpactSound().play(volume);
            }
            exploding = true;
            animationTime = 0f;
            body.setLinearVelocity(0, 0); // para o meteoro ao atingir o chão / astronauta
            setCategoryFilter(Constants.DESTROYED_BIT); // destroi o meteoro
        }
    }

    @Override
    public void dispose() {
        if (body != null && playScreen.getWorld() != null) {
            playScreen.getWorld().destroyBody(body);
            body = null;
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isExploding() {
        return exploding;
    }
}
