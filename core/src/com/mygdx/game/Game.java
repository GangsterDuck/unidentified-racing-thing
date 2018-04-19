package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game extends ApplicationAdapter{
    // TODO: Forever clean up
    // GRAPHICS ENGINE PROPERTIES
    SpriteBatch batch;
    BitmapFont forDebug;
    BitmapFont tempGUI;
    ShapeRenderer shapeRenderer;

    // GRAPHICS LEVEL ARRAYS PROPERTIES
    // Higher Levels appear above Lower Levels
    // Arrays: 0 shown lower than 1, and so on. Reasonably.
    ArrayList<GameTexture> background;
    ArrayList<GameTexture> foreground;
    ArrayList<MenuButton> menuButtons;

    // PHYSICS TO GRAPHICS PROPERTIES
    Matrix4 matrix4;
    private OrthographicCamera camera;
    int cameraWidth = 1920; //TODO: When options are added, this should be changeable
    int cameraHeight = 1080; //TODO: When options are added, this should be changeable

    // PHYSICS ENGINE PROPERTIES
    public static final float SCALE = 128;
    static World physWorld;
    Box2DDebugRenderer physDebugRenderer;

    // GAME PROPERTIES
    ArrayList<Car> cars = new ArrayList<Car>();
    ArrayList<Wall> walls = new ArrayList<Wall>();
    ArrayList<LapZone> zones = new ArrayList<LapZone>();
    ArrayList<String> loadCatcher = new ArrayList<String>();
    static ArrayList<AIPoint> aiPoints = new ArrayList<AIPoint>();//TODO: Redo?

    // IMPORTANT PROPERTIES
    public static boolean showDebug = true;
    AIController ai; //TODO: Remove
    AIController ai2; //TODO: Remove
    PlayerController plyr;
    int gameState = 1;
    /*   Game States:
         -1 : Dev Mode
          0 : Loading
          1 : Menus
          2 : Game
     */

    @Override
    public void create(){
        // GRAPHICS SETUP
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,cameraWidth,cameraHeight);
        forDebug =  new BitmapFont();
        forDebug.setColor(.0f,.0f,.0f,1f);
        forDebug.setUseIntegerPositions(true);
        tempGUI = new BitmapFont();
        tempGUI.setColor(.0f,.0f,.0f,1f);
        tempGUI.setUseIntegerPositions(true);
        tempGUI.getData().scale(3);
        background = new ArrayList<GameTexture>();
        foreground = new ArrayList<GameTexture>();
        menuButtons = new ArrayList<MenuButton>();

        // PHYSICS SETUP
        physWorld = new World(new Vector2(0,0),true);
        physDebugRenderer = new Box2DDebugRenderer();
        matrix4 = new Matrix4(camera.combined);
        matrix4.scl(SCALE);

        // GAME STUFF
        if(gameState==-1) {
            try {
                mapLoad("TestWorld");
            } catch (FileNotFoundException why) {
                System.out.println("ERROR");
                gameState=1;
            }
        }
        if(gameState==1) { // Menus
            try {
                menuLoad("MainMenu");
            }catch (FileNotFoundException ex){
                System.out.println("Hope for redemption");
            }
        }
    }

    @Override
    public void render(){
        doGraphics();
        if(gameState == -1 || gameState == 2) { // Dev Mode or Game
            if(true/*!finished*/) {
                physWorld.step(Gdx.graphics.getDeltaTime(), 6, 2);
            }
            gameTick();
            if(false/*finished*/){
                if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)){
                    gameState = 0;
                    loadCatcher.add("menu");
                    loadCatcher.add("MainMenu");
                }
            }
        }
        else if(gameState == 1){ // Menus

        }
        else if(gameState == 0){ // Loading
            doGraphics();
            if(loadCatcher.size()!=0){
                if(loadCatcher.get(0).equalsIgnoreCase("menu")){
                    try {
                        menuLoad(loadCatcher.get(1));
                    } catch (FileNotFoundException ex){
                        System.out.println("May your hope survive");
                    }
                    loadCatcher.clear();
                }
                else if(loadCatcher.get(0).equalsIgnoreCase("map")){
                    try {
                        mapLoad(loadCatcher.get(1));
                    } catch (FileNotFoundException ex){
                        System.out.println("May your hope survive");
                    }
                    //cars.add(new Car());
                    loadCatcher.clear();
                }
                else if(loadCatcher.get(0).equalsIgnoreCase("exit")){
                    Gdx.app.exit();
                }

            }
        }
        else{ // TODO: Safety Net: Loads to main menu and resets game state to 1(Menu)

        }

        checkInputs();
    }

    /**
     * Loads Map from files
     * TODO: Fix this you dolt, WIP
     * @param mapName The maps folder name, Ex: TestWorld
     * @throws FileNotFoundException One of the files looked up is not Found
     */
    public void mapLoad(String mapName) throws FileNotFoundException{
        // Last Map Parts Removal - Needed so they are not in the way in the new map
        for(MenuButton button : menuButtons){
            button.destroy();
        }
        menuButtons.clear();
        for(Wall wall : walls){
            wall.destroyBody();
        }
        walls.clear();
        for(Car car : cars){
            car.destroy();
        }
        cars.clear();
        for(GameTexture tex : background){
            tex.destroy();
        }
        background.clear();
        for(GameTexture tex : foreground){
            tex.destroy();
        }
        foreground.clear();
        zones.clear();
        //winLaps = 3;
        //finished = false;

        // Texture Loading
        // Gets .txt file that has the names and places for all of the Maps textures
        Scanner scan = new Scanner(new File("levels\\"+mapName+"\\WorldTextures.txt"));
        String textureLine;
        String[] splitTextureLine;
        Texture temptex;
        //Goes through all lines for textures
        while (scan.hasNextLine()){
            // Takes Each Line and puts it into a usable form
            textureLine = scan.nextLine();
            splitTextureLine =  textureLine.split(",");
            for(int i = 0; i < splitTextureLine.length; i++){
                splitTextureLine[i] = splitTextureLine[i].trim();
            }
            // If the length is the correct amount, assume it's correct and make an GameTexture object
            if(splitTextureLine.length == 4) {
                if (splitTextureLine[2].equalsIgnoreCase("B")) {
                    temptex = new Texture("levels\\" + mapName + "\\" + splitTextureLine[3]);
                    background.add(
                            new GameTexture(
                                    Integer.parseInt(splitTextureLine[0]),
                                    Integer.parseInt(splitTextureLine[1]),
                                    temptex
                            ));
                } else if (splitTextureLine[2].equalsIgnoreCase("F")) {
                    temptex = new Texture("levels\\" + mapName + "\\" + splitTextureLine[3]);
                    foreground.add(
                            new GameTexture(
                                    Integer.parseInt(splitTextureLine[0]),
                                    Integer.parseInt(splitTextureLine[1]),
                                    temptex
                            ));
                }
            }
        }
        scan.close();
        scan = new Scanner(new File("levels\\"+mapName+"\\Zones.txt"));
        String zoneLine;
        String[] splitZoneLine;
        while(scan.hasNextLine()){
            zoneLine = scan.nextLine();
            splitZoneLine = zoneLine.split(",");
            for (int i = 0; i < splitZoneLine.length; i++){
                splitZoneLine[i] = splitZoneLine[i].trim();
            }
            if(splitZoneLine.length==5){
                zones.add( new LapZone(
                    Integer.parseInt(splitZoneLine[0]),
                    Integer.parseInt(splitZoneLine[1]),
                    Integer.parseInt(splitZoneLine[2]),
                    Integer.parseInt(splitZoneLine[3]),
                    Integer.parseInt(splitZoneLine[4])
                ));
            }
        }
        scan.close();

        // physWorld Wall Loading
        scan = new Scanner(new File("levels\\"+mapName+"\\WorldWalls.txt"));
        String wallLine;
        String[] splitWallLine;
        while(scan.hasNextLine()){
            // Scans line and puts it into correct form
            wallLine = scan.nextLine();
            splitWallLine = wallLine.split(",");
            for (int i = 0; i < splitWallLine.length; i++){
                splitWallLine[i]=splitWallLine[i].trim();
            }
            // Creates Wall Object
            if(splitWallLine.length==5){
                walls.add(new Wall(
                        Integer.parseInt(splitWallLine[0]),
                        Integer.parseInt(splitWallLine[1]),
                        Integer.parseInt(splitWallLine[2]),
                        Integer.parseInt(splitWallLine[3]),
                        Double.parseDouble(splitWallLine[4])));
            }
            else if(splitWallLine.length==4){
                walls.add(new Wall(
                        Integer.parseInt(splitWallLine[0]),
                        Integer.parseInt(splitWallLine[1]),
                        Integer.parseInt(splitWallLine[2]),
                        Integer.parseInt(splitWallLine[3]),
                        0));
            }
        }
        scan.close();
        aiPoints.clear();
        aiPoints.add(new AIPoint(930, 906, 50, 50, 0, new ArrayList<AICommand>()));
        aiPoints.add(new AIPoint(1620,920,50,50, 1, new ArrayList<AICommand>()));
        aiPoints.add(new AIPoint(1750, 750, 50, 50, 2, new ArrayList<AICommand>()));
        aiPoints.add(new AIPoint(1650, 400, 50,50,3,new ArrayList<AICommand>()));
        aiPoints.add(new AIPoint(1450, 300, 50, 50, 4, new ArrayList<AICommand>()));
        aiPoints.add(new AIPoint(750, 640, 50, 50, 5, new ArrayList<AICommand>()));
        aiPoints.add(new AIPoint(604, 525, 50, 50, 6, new ArrayList<AICommand>()));
        aiPoints.add(new AIPoint(600, 346, 50, 50, 7, new ArrayList<AICommand>()));
        aiPoints.add(new AIPoint(377, 216, 50, 50, 8, new ArrayList<AICommand>()));
        aiPoints.add(new AIPoint(173, 381, 50, 50, 9, new ArrayList<AICommand>()));
        aiPoints.add(new AIPoint(177, 790, 50, 50, 10, new ArrayList<AICommand>()));
        aiPoints.add(new AIPoint(350, 930, 50, 50, 11, new ArrayList<AICommand>()));
        cars.add(new Car(500, 900));
        cars.add(new Car());
        cars.add(new Car(350, 900));
        plyr = new PlayerController(cars.get(0));
        ai2 = new AIController(cars.get(1),aiPoints.get(0));
        ai = new AIController(cars.get(2),aiPoints.get(0));
        gameState = 2;
    }

    /**
     * Loads Menu from files
     * @param menuName The menus folder name, Ex: MainMenu
     * @throws FileNotFoundException One of the files looked up is not Found
     */
    public void menuLoad(String menuName) throws FileNotFoundException{
        // Last Map Parts Removal - Needed so they are not in the way in the new map
        for(Wall wall : walls){
            wall.destroyBody();
        }
        walls.clear();
        for(Car car : cars){
            car.destroy();
        }
        cars.clear();
        for(GameTexture tex : background){
            tex.destroy();
        }
        background.clear();
        for(GameTexture tex : foreground){
            tex.destroy();
        }
        foreground.clear();
        for(MenuButton mb  : menuButtons){
            mb.destroy();
        }
        menuButtons.clear();
        zones.clear();
        aiPoints.clear();

        background.add(new GameTexture(0,0,new Texture("menus\\"+menuName+"\\Background.png")));
        Scanner scan = new Scanner(new File("menus\\"+menuName+"\\MenuButtons.txt"));
        String buttonLine;
        String[] splitButtonLine;
        while(scan.hasNextLine()){
            buttonLine = scan.nextLine();
            splitButtonLine = buttonLine.split(",");
            for(int i = 0; i < splitButtonLine.length; i++){
                splitButtonLine[i] = splitButtonLine[i].trim();
            }
            if(splitButtonLine.length == 8){
                menuButtons.add(
                        new MenuButton(
                           Integer.parseInt(splitButtonLine[0]),
                           Integer.parseInt(splitButtonLine[1]),
                           Integer.parseInt(splitButtonLine[2]),
                           Integer.parseInt(splitButtonLine[3]),
                           new Texture("menus\\"+menuName+"\\"+splitButtonLine[4]),
                           new Texture("menus\\"+menuName+"\\"+splitButtonLine[5]),
                           splitButtonLine[6],
                           splitButtonLine[7]
                        ));
            }
        }
        scan.close();
        gameState = 1;
    }

    /**
     * Runs all graphics things, to get is out of render
     */
    public void doGraphics(){
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for(GameTexture tex : background){
            batch.draw(tex.texture,tex.x,tex.y);
        }
        if(gameState==1){
            for(MenuButton mb : menuButtons) {
                batch.draw(mb.getCurrentTexture(),mb.x1,mb.y1);
            }
        }
        if(gameState==2 || gameState==-1) {
            for (Car car : cars) {
                batch.draw(car.getTexture(), car.getX(), car.getY(),
                        20, 40, 40, 80, 1, 1,
                        car.getAngle(), 0, 0, 40,
                        40, false, false);
            }
            tempGUI.draw(batch, "LAPS", 1311, 700);
            tempGUI.draw(batch, plyr.currentLaps+"/"+plyr.finishLaps, 1311,645);
            if(false/*finished*/){
                tempGUI.draw(batch, "FINISH", 1000, 500);
            }
        }
        if(showDebug){

            for(AIPoint point : aiPoints){
                batch.draw(point.getDebugTexture(),point.x1*SCALE,point.y1*SCALE);
                batch.draw(point.getDebugTexture(),point.x2*SCALE-10,point.y2*SCALE-10);
                batch.draw(point.getDebugTexture(),point.x1*SCALE,point.y2*SCALE-10);
                batch.draw(point.getDebugTexture(),point.x2*SCALE-10,point.y1*SCALE);
                forDebug.draw(batch, "P"+ point.pointNum,point.midx*SCALE-10,point.midy*SCALE+5);
            }
            //TODO Fix LapZones. Where coordinates are gotten from need to be switched to (x,y,width,height)
            for(LapZone zone : zones){
                batch.draw(zone.getDebugTexture(),zone.x1*SCALE,zone.y1*SCALE);
                batch.draw(zone.getDebugTexture(),zone.x2*SCALE-10,zone.y2*SCALE-10);
                batch.draw(zone.getDebugTexture(),zone.x1*SCALE,zone.y2*SCALE-10);
                batch.draw(zone.getDebugTexture(),zone.x2*SCALE-10,zone.y1*SCALE);
                forDebug.draw(batch, "Zone "+zone.getZoneNum(),zone.midx*SCALE-10, zone.midy*SCALE);
            }

            forDebug.draw(batch,"Debug On", 10,20);
            forDebug.draw(batch, "MouseX: "+Gdx.input.getX(),10,80);
            forDebug.draw(batch, "MouseY: "+(1080-Gdx.input.getY()),10,100);

            batch.end();
            physDebugRenderer.render(physWorld,matrix4);

        }
        else {
            batch.end();
        }
    }

    /**
     * Checks for all inputs, to get it out of render
     */
    public void checkInputs(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            showDebug = !showDebug;
        }

        if(gameState==1){
            int x = Gdx.input.getX();
            int y = 1080-Gdx.input.getY();
            for(MenuButton mb : menuButtons){
                if(mb.checkWithin(x,y)){
                    if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                        gameState = 0;
                        loadCatcher = mb.getLoadType();
                    }
                }
            }
        }
        // TODO: REDO INPUTS
        if(gameState==2) {
            /*
            Car car = cars.get(0);
            Body body = car.getBody();
            //if()
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                double xAccelChange = -Math.sin(body.getAngle());
                double yAccelChange = Math.cos(body.getAngle());
                body.setLinearVelocity((float) (body.getLinearVelocity().x + .25 * xAccelChange), (float) (body.getLinearVelocity().y + .25 * yAccelChange));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                double velocity = Math.sqrt(Math.pow(body.getLinearVelocity().x, 2) + Math.pow(body.getLinearVelocity().y, 2)) - .0125;
                if (velocity < 0) {
                    body.setLinearVelocity(0, 0);
                } else {
                    double xAccelChange = -Math.sin(body.getAngle());
                    double yAccelChange = Math.cos(body.getAngle());
                    body.setLinearVelocity((float) (body.getLinearVelocity().x - .0125 * xAccelChange), (float) (body.getLinearVelocity().y - .0125 * yAccelChange));
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                double turnRate;
                double velocity;
                if (body.getLinearVelocity().x == 0 && body.getLinearVelocity().y == 0) {
                    turnRate = 0;
                    velocity = 0;
                } else {
                    velocity = Math.sqrt(Math.pow(body.getLinearVelocity().x, 2) + Math.pow(body.getLinearVelocity().y, 2));
                }

                if (velocity > 1) {
                    turnRate = -Math.log(velocity) * .0225 + .2;//Math.PI;
                } else if (velocity > 0) {
                    turnRate = .2;
                } else {
                    turnRate = velocity;
                }
                double newAngle = body.getAngle() + (.25 * turnRate);
                body.setTransform(body.getPosition().x, body.getPosition().y, (float) newAngle);
                double xAccelChange = -Math.sin(body.getAngle());
                double yAccelChange = Math.cos(body.getAngle());
                body.setLinearVelocity((float) (velocity * xAccelChange), (float) (velocity * yAccelChange));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                double turnRate;
                double velocity;
                if (body.getLinearVelocity().x == 0 && body.getLinearVelocity().y == 0) {
                    turnRate = 0;
                    velocity = 0;
                } else {
                    velocity = Math.sqrt(Math.pow(body.getLinearVelocity().x, 2) + Math.pow(body.getLinearVelocity().y, 2));
                }

                if (velocity > 1) {
                    turnRate = -Math.log(velocity) * .0225 + .2;//Math.PI;
                } else if (velocity > 0) {
                    turnRate = .2;
                } else {
                    turnRate = 0;
                }
                double newAngle = body.getAngle() - (.25 * turnRate);
                body.setTransform(body.getPosition().x, body.getPosition().y, (float) newAngle);
                double xAccelChange = -Math.sin(body.getAngle());
                double yAccelChange = Math.cos(body.getAngle());
                body.setLinearVelocity((float) (velocity * xAccelChange), (float) (velocity * yAccelChange));
            }
            */
        }
    }

    /**
     * Does game stuff, to get it out of render
     */
    public void gameTick(){
        if(ai!=null) {
            plyr.drive();
            ai.drive();
            ai2.drive();
        }
        for(Car car : cars){
            for(LapZone zone : zones){
                if(zone.isWithin((int)car.getX(),(int)car.getY())){
                    if(car.zone==0 && zone.getZoneNum()==1){
                        plyr.currentLaps++;
                    }
                    /**
                    if(car.lapOn>winLaps){
                        finished = true;
                        car.lapOn = winLaps;
                    }
                     */
                    if(zone.getZoneNum()==0 || zone.getZoneNum()==1) {
                        car.zone = zone.getZoneNum();
                    }
                }
            }
        }
    }

    /**
     * Accessor for physWorld
     * @return physWorld
     */
    public static World getPhysWorld(){
        return physWorld;
    }

    @Override
    public void dispose () {
        for(Wall wall : walls){
            wall.destroyBody();
        }
        for(GameTexture tex : background){
            tex.destroy();
        }
        for(GameTexture tex : foreground){
            tex.destroy();
        }
        for(Car car : cars){
            car.destroy();
        }
        for(MenuButton mb: menuButtons){
            mb.destroy();
        }
        batch.dispose();
        physWorld.dispose();
        AIPoint.destroy();
        LapZone.destroy();

    }

}
