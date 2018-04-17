package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.values.RectangleSpawnShapeValue;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.Scanner;


public class MyGdxGame extends ApplicationAdapter {

	// Graphics
    BitmapFont font;
	SpriteBatch batch;
	Texture img;
	Texture bckgrd;

	// Box2D & Graphics
	Matrix4 matrix4;
	private OrthographicCamera camera;

	// Box2D
	public static final float SCALE = 128;
	double xAccelChange;
	double yAccelChange;
	double velocity;
	Box2DDebugRenderer debugRenderer;
	World world;
	Body body;

	// Box2D Wall Stuff
	int[] wallsX =    {   0,  70,  70,1850, 258, 310, 328, 423, 700, 990, 1500, 1740, 1500};
	int[] wallsY =    {   0,1010,   0,   0, 380, 310, 733, 380,  70,   0, -127,  -59,  400};
	int[] wallsH =    {1080,  70,  70,1080, 420,  70,  70, 360, 490, 720,  490,  420,  350};
	int[] wallsW =    {  70,1780,1780,  70,  70, 140,1330,  70,  70,  70,   70,   70,   70};
	double[] wallsR = {   0,   0,   0,   0,   0,   0,   0,   0,   0,1.02, 1.29, -.85,   0};
	ArrayList<Body> walls = new ArrayList<Body>();

	@Override
	public void create () {
		// Graphics Setup
		batch = new SpriteBatch();
		img = new Texture("LongerTestCar.png");
		bckgrd = new Texture("Background Tets.png");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);
		font = new BitmapFont();
		font.setColor(.0f,.0f,0f,1f);
		font.setUseIntegerPositions(true);

		// Box2d Setup
		world = new World(new Vector2(0,0), true);
		debugRenderer = new Box2DDebugRenderer();
		matrix4 = new Matrix4(camera.combined);
		matrix4.scl(SCALE);

		// Creating CAR
		BodyDef def = new BodyDef();
		def.type = BodyDef.BodyType.DynamicBody;
		def.position.set(1,1);
		body = world.createBody(def);
		PolygonShape rect = new PolygonShape();
		rect.setAsBox(20/SCALE,40/SCALE);
		FixtureDef fDef = new FixtureDef();
		fDef.shape = rect;
		fDef.density = 0.25f;
		fDef.friction = .2f;
		fDef.restitution = .001f;
		body.createFixture(fDef);
		body.setLinearDamping(.2f);
		body.setAngularDamping(5f);
		rect.dispose();


		// Creating WALLS
		BodyDef wallDef;
		PolygonShape wallShape = new PolygonShape();
		FixtureDef wallFixDef = new FixtureDef();
		wallFixDef.density = .25f;
		wallFixDef.friction = .2f;
		wallFixDef.restitution = .6f;
		for(int i = 0; i<wallsH.length; i++){
			double midY = (((wallsH[i])/2)+wallsY[i])/SCALE;
			double midX = (((wallsW[i])/2)+wallsX[i])/SCALE;
			wallDef = new BodyDef();
			wallDef.angle = (float)wallsR[i];
			wallDef.type = BodyDef.BodyType.StaticBody;
			wallDef.position.set((float)midX,(float)midY);
			wallShape.setAsBox((wallsW[i]/2)/SCALE,(wallsH[i]/2)/SCALE);
			wallFixDef.shape = wallShape;
			walls.add(world.createBody(wallDef));
			walls.get(i).createFixture(wallFixDef);
		}
		wallShape.dispose();
	}

	@Override
	public void render () {
		world.step(Gdx.graphics.getDeltaTime(),6,2);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			xAccelChange = -Math.sin(body.getAngle());
			yAccelChange = Math.cos(body.getAngle());
			body.setLinearVelocity((float)(body.getLinearVelocity().x+.25*xAccelChange),(float)(body.getLinearVelocity().y+.25*yAccelChange));
		}
		batch.begin();
		if(!Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			batch.draw(bckgrd,0,0);
		}
		batch.draw(img, body.getPosition().x*SCALE-20, body.getPosition().y*SCALE-40, 20, 40,40,80,1,1, (float)(body.getAngle()*180/Math.PI),0,0,40,40,false,false);


		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			velocity = Math.sqrt(Math.pow(body.getLinearVelocity().x,2)+Math.pow(body.getLinearVelocity().y,2))-.0125;
			if(velocity<0){
				body.setLinearVelocity(0,0);
			}
			else {
				xAccelChange = -Math.sin(body.getAngle());
				yAccelChange = Math.cos(body.getAngle());
				body.setLinearVelocity((float) (body.getLinearVelocity().x - .0125 * xAccelChange), (float) (body.getLinearVelocity().y - .0125 * yAccelChange));
			}
		}
		if(Gdx.input.isKeyPressed((Input.Keys.A))){
			double turnRate;
			if(body.getLinearVelocity().x == 0 && body.getLinearVelocity().y==0){
				turnRate = 0;
				velocity=0;
			}
			else {
				velocity = Math.sqrt(Math.pow(body.getLinearVelocity().x,2)+Math.pow(body.getLinearVelocity().y,2));
			}

			if(velocity > 1){
				turnRate = -Math.log(velocity)*.0225+.2 ;//Math.PI;
			}
			else{
				turnRate = velocity;
			}
			double newAngle = body.getAngle()+(.25*turnRate);
			body.setTransform(body.getPosition().x,body.getPosition().y,(float)newAngle);
			xAccelChange = -Math.sin(body.getAngle());
			yAccelChange = Math.cos(body.getAngle());
			body.setLinearVelocity((float)(velocity*xAccelChange),(float)(velocity*yAccelChange));
        }
        if(Gdx.input.isKeyPressed((Input.Keys.D))){
			double turnRate;
			if(body.getLinearVelocity().x == 0 && body.getLinearVelocity().y==0){
				turnRate = 0;
				velocity=0;
			}
			else {
				velocity = Math.sqrt(Math.pow(body.getLinearVelocity().x,2)+Math.pow(body.getLinearVelocity().y,2));
			}

			if(velocity > 1){
				turnRate = -Math.log(velocity)*.0225+.2 ;//Math.PI;
			}
			else if(velocity>0){
				turnRate = .2;
			}
			else{
				turnRate = 0;
			}
			//System.out.println("turnRate: "+turnRate);
			//System.out.println("oldAngle: "+body.getAngle()*180/Math.PI);
			double newAngle = body.getAngle()-(.25*turnRate);
			//System.out.println("newAngle: "+(float)newAngle*180/Math.PI);

			body.setTransform(body.getPosition().x,body.getPosition().y,(float)newAngle);
			xAccelChange = -Math.sin(body.getAngle());

			yAccelChange = Math.cos(body.getAngle());
			body.setLinearVelocity((float)(velocity*xAccelChange),(float)(velocity*yAccelChange));
        }
        if(Gdx.input.isKeyPressed((Input.Keys.Z))){
			// Possible Use
        }
        if(Gdx.input.isKeyPressed((Input.Keys.X))){
			// Possible Use
        }
		batch.end();
		debugRenderer.render(world, matrix4);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		bckgrd.dispose();
	}
}
