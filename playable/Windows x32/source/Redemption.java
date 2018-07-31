import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 
import ddf.minim.effects.*; 
import ddf.minim.signals.*; 
import ddf.minim.spi.*; 
import ddf.minim.ugens.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Redemption extends PApplet {

public void setup() {
  
  surface.setTitle("Redemption | v1.0 | Robin de Zwart");
}

public void draw() {
  switch (loadingPhase) {
  case 0:
    println("GAME: Launched!");
    println("SCREEN: Loading");

    // Font creation
    font = createFont("lunchds.ttf", 16);
    textFont(font);

    // Starts all most global initializations
    thread("initialize");
    loadingPhase++;
    break;

  case 1:
    // Loading screen graphics
    loadScreen();
    break;

  case 2:
    image(bg, 0, 0);
    screen();
  }
}
public void update() {
  // Updates player and current enemy.
  you.tick();
  e.tick();

  // Sets boolean for who's turn it is.
  if (turn % 2 == 0) {
    pTurn();
  } else {
    eTurn();
  }

  // Manages enemy turns
  turnManager();

  gameState();
}

// Draws the HUD, player, and enemy
public void render() {
  hud();
  you.draw();
  e.draw();
}

// Draws buttons, and info screen if necessary
public void hud() {
  buttons();

  if (infoScreen) {
    drawInfoScreen();
  }
}

// Draws all buttons in arraylist
public void buttons() {
  for (Button b : menu) {
    b.tick();
    b.draw();
  }
}

// Manages the turns of player and enemies
public void turnManager() {
  if (!pTurn) {
    turnCount++;

    if (turnCount >= 60) {
      turnCount = 0;
      makeMove();
      nextTurn();
    }
  }
}

int tempTimer = 0;
boolean dying = false;
public void gameState() {
  if (e.health <= 0 || you.health <= 0) {
    eTurn = false;
    pTurn = false;
    if (e.health <= 0 && !dying) {
      dying = true;
      score++;
      e.anim("die");
    } else if (you.health <= 0 && !dying) {
      dying = true;
      you.anim("die");
    }

    tempTimer++;

    if (tempTimer >= 60) {
      tempTimer = 0;
      if (you.health <= 0) {
        screen = OVER;
      } else if (e.health <= 0) {
        e = new Enemy();
        dying = false;
      }
    }
  }
}
boolean doDebug = false; // Debug: toggles print statements
boolean doAudio = true; // Debug: toggles audio output

Generator generate = new Generator(); //

boolean initReady = false;

Minim minim = new Minim(this);
AudioPlayer mainDefSpell, mainHealSpell, mainSpearSpell, mainDaggerSpell, mainWandSpell, mainUtlSpell;
AudioPlayer screwMusic, turnMusic;

int loadingPhase = 0;
int loadingProgress = 0;
float loadingPercentage = 0;
int OBJECTS_TO_LOAD = 31;

final int RESTART = -2;
final int LOAD = -1;
final int MAIN = 0;
final int INTRO = 1;
final int CHAR = 2;
final int GAME = 3;
final int OVER = 4;
int screen = MAIN;

int turn = 0;
int turnCount = 0;
boolean pTurn = true;
boolean eTurn = false;

int healCount = 0;
boolean healUsed = false;

int score = 0;

Player you;
Enemy e;
final String inf = "info";
final String hea = "heal";
final String ent = "enter";
final String atk = "attack";
final String def = "defense";
final String utl = "utility";
final String dis = "display";
final String mag = "mage";
final String war = "warrior";
final String rog = "rogue";

ArrayList<Button> menu = new ArrayList<Button>();
Button atkButton, atkTarget;
Button defButton, defTarget;
Button utlButton, utlTarget;
Button infButton;
Button heaButton, heaTarget;

ArrayList<Button> custom = new ArrayList<Button>();
Button haiTarget, rehButton, rahButton, blhButton, brhButton;
Button claTarget, magButton, warButton, rogButton;
Button genTarget, malButton, femButton;
Button staButton, staTarget;
Button namButton, namTarget;
Button rotTarget, rolButton, rorButton;
Button ranButton, ranTarget;

boolean infoScreen = false;
ArrayList<String> actionStrings = new ArrayList<String>();
PFont font;

PImage bg, vg;
PImage buttonReady, buttonDown, buttonHover, buttonLocked;
// Male
PImage mrra, mrre, mrbl, mrbr;
PImage mwra, mwre, mwbl, mwbr;
PImage mmra, mmre, mmbl, mmbr;

// Female
PImage frra, frre, frbl, frbr;
PImage fwra, fwre, fwbl, fwbr;
PImage fmra, fmre, fmbl, fmbr;

public void initialize() {
  println("GAME: Initializing...");
  
  for (int i = 0; i < 3; i++) {
    actionStrings.add("");
  }

  print("   LOADING: Sound (");
  mainDefSpell = minim.loadFile("def.mp3");
  print("def");
  mainHealSpell = minim.loadFile("heal.mp3");
  print(", heal");
  mainSpearSpell = minim.loadFile("spear.mp3");
  print(", spear");
  mainDaggerSpell = minim.loadFile("dagger.mp3");
  print(", dagger");
  mainWandSpell = minim.loadFile("wand.mp3");
  print(", wand");
  mainUtlSpell = minim.loadFile("change.mp3");
  print(", change");
  screwMusic = minim.loadFile("Turning The Screw.mp3");
  print(", screw");
  turnMusic = minim.loadFile("Wrong Turn.mp3");
  print(", turn");
  println(")");
  prog();

  loadImages();
}
boolean fastLoading = false;

public void loadButtons() {
  print("   LOADING: Buttons (");

  atkButton = new Button("ATTACK", 110, 75, 120, 50);
  atkTarget = new Button(e.name, 110, 120, 110, 40);
  print("atk, ");
  defButton = new Button("DEFENSE", 245, 75, 120, 50);
  defTarget = new Button("Resist * 1.5", 245, 120, 110, 40);
  print("def, ");
  utlButton = new Button("UTILITY", 380, 75, 120, 50);
  utlTarget = new Button("Cycle Stance", 380, 120, 110, 40);
  print("utl, ");
  infButton = new Button("INSPECT", 1170, 75, 120, 50);
  print("info, ");
  heaButton = new Button("HEAL", 1035, 75, 120, 50);
  heaTarget = new Button("dummy", -666, -666, 120, 50);
  print("heal, ");
  rehButton = new Button("Red", 310, 270, 120, 50);
  rahButton = new Button("Black", 310, 345-10, 120, 50);
  blhButton = new Button("Blond", 310, 420-20, 120, 50);
  brhButton = new Button("Brown", 310, 495-30, 120, 50);
  haiTarget = new Button("dummy", -666, -666, 120, 50);
  print("hair, ");
  magButton = new Button("Mage", 110, 515, 120, 50);
  warButton = new Button("Warrior", 110, 590-10, 120, 50);
  rogButton = new Button("Rogue", 110, 665-20, 120, 50);
  claTarget = new Button("dummy", -666, -666, 120, 50);
  print("class, ");
  malButton = new Button("Male", 310, 75, 120, 50);
  femButton = new Button("Female", 310, 150-10, 120, 50);
  genTarget = new Button("dummy", -666, -666, 120, 50);
  print("gender, ");
  staButton = new Button("Start!", 1102.5f, 230, 255, 100);
  staTarget = new Button("dummy", -666, -666, 120, 50);
  print("start, ");
  rolButton = new Button("Rotate Left", 1035, 75, 120, 50);
  rorButton = new Button("Rotate Right", 1170, 75, 120, 50);
  rotTarget = new Button("dummy", -666, -666, 120, 50);
  print("rotate, ");
  namButton = new Button("Random Name", 510, 75, 120, 50);
  namTarget = new Button("dummy", -666, -666, 120, 50);
  print("name, ");
  ranButton = new Button("Randomize All", 510, 150-10, 120, 50);
  ranTarget = new Button("dummy", -666, -666, 120, 50);
  print("random)");
  setButtons();

  println();
}

public void setButtons() {
  atkButton.setDisplay(atkTarget);
  atkTarget.setAction(atk);

  defButton.setDisplay(defTarget);
  defTarget.setAction(def);

  utlButton.setDisplay(utlTarget);
  utlTarget.setAction(utl);

  infButton.setDisplay(infButton);
  infButton.setAction(inf);

  heaButton.setDisplay(heaTarget);
  heaButton.setAction(hea);

  rehButton.setDisplay(haiTarget);
  rehButton.setAction("re");
  rahButton.setDisplay(haiTarget);
  rahButton.setAction("ra");
  blhButton.setDisplay(haiTarget);
  blhButton.setAction("bl");
  brhButton.setDisplay(haiTarget);
  brhButton.setAction("br");

  magButton.setDisplay(claTarget);
  magButton.setAction("ma");
  warButton.setDisplay(claTarget);
  warButton.setAction("wa");
  rogButton.setDisplay(claTarget);
  rogButton.setAction("ro");

  malButton.setDisplay(genTarget);
  malButton.setAction("m");
  femButton.setDisplay(genTarget);
  femButton.setAction("f");

  staButton.setDisplay(staTarget);
  staButton.setAction("s");

  namButton.setDisplay(namTarget);
  namButton.setAction("nam");

  rolButton.setDisplay(rotTarget);
  rolButton.setAction("rol");
  rorButton.setDisplay(rotTarget);
  rorButton.setAction("ror");

  ranButton.setDisplay(ranTarget);
  ranButton.setAction("ran");

  menu.add(atkButton);
  menu.add(atkTarget);
  menu.add(defButton);
  menu.add(defTarget);
  menu.add(utlButton);
  menu.add(utlTarget);
  menu.add(infButton);
  menu.add(heaTarget);
  menu.add(heaButton);

  custom.add(rehButton);
  custom.add(rahButton);
  custom.add(blhButton);
  custom.add(brhButton);
  custom.add(haiTarget);

  custom.add(magButton);
  custom.add(warButton);
  custom.add(rogButton);
  custom.add(claTarget);

  custom.add(malButton);
  custom.add(femButton);
  custom.add(genTarget);

  custom.add(staButton);
  custom.add(staTarget);

  custom.add(namButton);
  custom.add(namTarget);

  custom.add(rolButton);
  custom.add(rorButton);
  custom.add(rotTarget);

  custom.add(ranButton);
  custom.add(ranTarget);
}

public void loadImages() {
  print("   LOADING: PImages (");
  thread("loadMales");
  thread("loadFemales");

  bg = loadImage("bg.png");
  prog();
  print("bg");
  
  vg = loadImage("vignette.png");
  prog();
  print(", vg");

  buttonReady = loadImage("button ready.png");
  prog();
  buttonHover = loadImage("button hover.png");
  prog();
  buttonDown = loadImage("button down.png");
  prog();
  buttonLocked = loadImage("button locked.png");
  prog();
  print(", but");
}

public void loadMales() {
  thread("loadMR");
  thread("loadMW");
  thread("loadMM");
}

public void loadMR() {
  if (fastLoading) {
    mrra = loadImage("download.png"); 
    prog();
    mrre = loadImage("download.png"); 
    prog();
    mrbl = loadImage("download.png"); 
    prog();
    mrbr = loadImage("download.png");
    prog();
  } else {
    mrra = loadImage("mrra.png");
    prog();
    mrre = loadImage("mrre.png");
    prog();
    mrbl = loadImage("mrbl.png");
    prog();
    mrbr = loadImage("mrbr.png");
    prog();
    print(", mr");
  }
  mrDone = true;
}

public void loadMW() {
  if (fastLoading) {
    mwra = loadImage("download.png"); 
    prog();
    mwre = loadImage("download.png"); 
    prog();
    mwbl = loadImage("download.png"); 
    prog();
    mwbr = loadImage("download.png");
    prog();
  } else {
    mwra = loadImage("mwra.png");
    prog();
    mwre = loadImage("mwre.png");
    prog();
    mwbl = loadImage("mwbl.png");
    prog();
    mwbr = loadImage("mwbr.png");
    prog();
  }
  print(", mw");
  mwDone = true;
}

public void loadMM() {
  if (fastLoading) {
    mmra = loadImage("download.png"); 
    prog();
    mmre = loadImage("download.png");
    prog();
    mmbl = loadImage("download.png"); 
    prog();
    mmbr = loadImage("download.png"); 
    prog();
  } else {
    mmra = loadImage("mmra.png");
    prog();
    mmre = loadImage("mmre.png");
    prog();
    mmbl = loadImage("mmbl.png");
    prog();
    mmbr = loadImage("mmbr.png");
    prog();
  }
  print(", mm");
  mmDone = true;
}

public void loadFemales() {
  thread("loadFR");
  thread("loadFW");
  thread("loadFM");
}

public void loadFR() {
  if (fastLoading) {
    frra = loadImage("download.png"); 
    prog();
    frre = loadImage("download.png"); 
    prog();
    frbl = loadImage("download.png"); 
    prog();
    frbr = loadImage("download.png"); 
    prog();
  } else {
    frra = loadImage("frra.png");
    prog();
    frre = loadImage("frre.png");
    prog();
    frbl = loadImage("frbl.png");
    prog();
    frbr = loadImage("frbr.png");
    prog();
  }
  print(", fr");
  frDone = true;
}

public void loadFW() {
  if (fastLoading) {
    fwra = loadImage("download.png"); 
    prog();
    fwre = loadImage("download.png"); 
    prog();
    fwbl = loadImage("download.png"); 
    prog();
    fwbr = loadImage("download.png"); 
    prog();
  } else {
    fwra = loadImage("fwra.png");
    prog();
    fwre = loadImage("fwre.png");
    prog();
    fwbl = loadImage("fwbl.png");
    prog();
    fwbr = loadImage("fwbr.png");
    prog();
  }
  print(", fw");
  fwDone = true;
}

public void loadFM() {
  if (fastLoading) {
    fmra = loadImage("download.png"); 
    prog();
    fmre = loadImage("download.png"); 
    prog();
    fmbl = loadImage("download.png"); 
    prog();
    fmbr = loadImage("download.png"); 
    prog();
  } else {
    fmra = loadImage("fmra.png");
    prog();
    fmre = loadImage("fmre.png");
    prog();
    fmbl = loadImage("fmbl.png");
    prog();
    fmbr = loadImage("fmbr.png");
    prog();
  }
  print(", fm");
  fmDone = true;
}
public void pTurn() {
  pTurn = true;
  eTurn = false;
}

public void eTurn() {
  eTurn = true;
  pTurn = false;
}

public String randStance() {
  float ran = random(3);
  if (ran < 1) {
    return war;
  } else if (ran >= 1 && ran < 2) {
    return mag;
  } else { // ran >= 2
    return rog;
  }
}

public void makeMove() {
  if (e.health > 0) {
    if (healCount > 3) {
      healCount = 0;
      healUsed = false;
    }

    if (healUsed) {
      healCount++;
    }

    float ran = random(5);
    if (ran < 1) {
      e.action(def, e);
    } else {
      e.action(atk, you);
    }
  }
}

public void nextTurn() {
  turn++;
}

public PImage getImg(String s) {
  switch (s) {
  case "mrra":
    return mrra;
  case "mrre":
    return mrre;
  case "mrbl":
    return mrbl;
  case "mrbr":
    return mrbr;
  case "mwra":
    return mwra;
  case "mwre":
    return mwre;
  case "mwbl":
    return mwbl;
  case "mwbr":
    return mwbr;
  case "mmra":
    return mmra;
  case "mmre":
    return mmre;
  case "mmbl":
    return mmbl;
  case "mmbr":
    return mmbr;

  case "frra":
    return frra;
  case "frre":
    return frre;
  case "frbl":
    return frbl;
  case "frbr":
    return frbr;
  case "fwra":
    return fwra;
  case "fwre":
    return fwre;
  case "fwbl":
    return fwbl;
  case "fwbr":
    return fwbr;
  case "fmra":
    return fmra;
  case "fmre":
    return fmre;
  case "fmbl":
    return fmbl;
  case "fmbr":
    return fmbr;
  }

  return new PImage();
}

public void restartGame() {
  you = new Player();
  e = new Enemy();
  dying = false;

  score = 0;
  
  stringIndex = 0;
  storyPhase = 0;
  storyString = "";
  charIndex = 0;
  
  actionStrings = new ArrayList<String>();
  for (int i = 0; i < 3; i++) {
    actionStrings.add("");
  }
  
  turn = 0;
  turnCount = 0;
  pTurn = true;
  eTurn = false;

  healCount = 0;
  healUsed = false;
  tempTimer = 0;
  infoScreen = false;

  menu = new ArrayList<Button>();
  custom = new ArrayList<Button>();
  loadButtons();
  screen = MAIN;
  println("GAME: Ready!");
}

public void prog() {
  loadingProgress++;
  loadingPercentage += 100f / OBJECTS_TO_LOAD;
}

public void resetImgs() {
  you.setImg(you.stance.name, false);
  e.setImg(e.stance.name, false);
}

public void fadeMusic() {
  float turnVol = turnMusic.getGain();
  turnMusic.shiftGain(turnVol, -80, 5000);

  screwMusic.setGain(-80);
  if (doAudio) {
    screwMusic.loop();
  }
  screwMusic.shiftGain(-80, 0, 5000);
}
public void keyPressed() {
  if (key == ' ') {
    if (screen == MAIN && initReady) {
      println("GAME: Started!");
      startFade(120, INTRO);
    }

    if (screen == OVER) {
      startFade(120, RESTART);
    }
  }

  if (key == 'q' || key == 'Q') {
    if (screen == INTRO) {
      startFade(120, CHAR);
    }
  }

  if (key == 't' || key == 'T') {
    if (screen == INTRO) {
      if (introDone) {
        println("SCREEN: Tutorial");
        showStory = tutStory;
        storyPhase = 0;
        stringIndex = 0;
        introDone = false;
      }
    }
  }

  if (keyCode == ENTER) {
    if (screen == INTRO) {
      if (tutDone || introDone) {
        startFade(120, CHAR);
      } else if (storyReady) {
        storyString += "\n";
        stringIndex = 0;
        storyPhase++;
      }
    }
  }
}
public void screen() {
  switch (screen) {
  case MAIN:
    menuScreen();
    break;

  case CHAR:
    introScreen();
    break;

  case GAME:
    gameScreen();
    break;

  case OVER:
    overScreen();
    break;

  case INTRO:
    testScreen();
    break;
  }

  transition();
}

int actionButtons = 100;
public void gameScreen() {
  if (atkButton.target.show || defButton.target.show || utlButton.target.show) {
    actionButtons = 140;
  } else {
    actionButtons = 100;
  }

  pushMatrix();
  pushStyle();
  translate(25, 25);
  rectMode(CORNER);
  noStroke();
  fill(255, 127);
  rect(0, 0, 440, actionButtons);
  rect(925, 0, 305, 100);

  translate(-10, 580);
  rect(0, 0, 700, 100);
  rect(10, 10, 680, 80);
  fill(0);
  textSize(15);
  textAlign(LEFT, TOP);
  text(actionStrings.get(actionStrings.size()-3), 20, 15+3);
  text(actionStrings.get(actionStrings.size()-2), 20, 40+3);
  text(actionStrings.get(actionStrings.size()-1), 20, 65+3);
  popStyle();
  popMatrix();
  update();
  render();
}

int menuAlpha = 255;
int menuAlphaSpd = -5;
public void menuScreen() {
  if (menuAlpha >= 0) {
    menuAlpha += menuAlphaSpd;
  }
  pushStyle();
  pushMatrix();
  translate(width/2, height/2);
  rectMode(CENTER);
  fill(255, 127);
  noStroke();
  rect(0, 0, 1000, 550);
  fill(0);
  textSize(20);
  textAlign(CENTER, CENTER);
  text("WELCOME TO REDEMPTION!\n\nThis game is a turn based RPG, set in an underground fighting arena." +
    "\nThis game is endless. You cannot escape. Good luck." + 
    "\n\n Press SPACE to begin.", 0, 0);
  popMatrix();
  popStyle();

  noStroke();
  fill(0, menuAlpha);
  rect(0, 0, width, height);
}

public void overScreen() {
  pushStyle();
  pushMatrix();
  rectMode(CENTER);
  fill(255, 127);
  noStroke();
  rect(width/2, height/2, 1000, 550);
  fill(0);
  textSize(30);
  textAlign(CENTER, CENTER);
  translate(width/2, height/2);

  String scoreText = "" + score;

  if (score > 1) {
    scoreText += " enemies";
  } else if (score == 1) {
    scoreText += " enemy";
  } 
  if (score > 0) {
    text("GAME OVER\n\nYou died, but took down " + scoreText + " with you!\nPress SPACE to try again.", 0, 0);
  } else {
    text("GAME OVER\n\nYou died, and didn't take down any enemies with you. :(\nPress SPACE to try again!", 0, 0);
  }
  popMatrix();
  popStyle();
}

int loadAlpha = 0;
int loadAlphaSpd = 5;
boolean femalesDone = false;
boolean malesDone = false;
boolean frDone = false;
boolean fwDone = false;
boolean fmDone = false;
boolean mrDone = false;
boolean mwDone = false;
boolean mmDone = false;
public void loadScreen() {
  if (frDone && fwDone && fmDone) {
    femalesDone = true;
  }
  if (mrDone && mwDone && mmDone) {
    malesDone = true;
  }

  if (malesDone && femalesDone && !initReady) {
    println(")");

    print("   LOADING: Persons (");
    you = new Player();
    print("you, ");
    e = new Enemy();
    print("e)");
    println();
    loadButtons();

    println("GAME: Ready!");
    println("SCREEN: Main Menu");
    initReady = true;
  }

  pushMatrix();
  pushStyle();
  background(0, 0, 0);
  textAlign(CENTER, CENTER);
  textSize(80);
  fill(255, loadAlpha);
  translate(width/2, height/2);
  text("LOADING... " + nf(loadingPercentage, 1, 1) + "%", 0, -60);

  rectMode(CORNER);
  noStroke();
  fill(200);
  rect(-218, -5, 436, 40);
  fill(170);
  rect(-213, 0, 426, 30);
  fill(255);  
  rect(-213, 0, map(loadingProgress, 0, OBJECTS_TO_LOAD, 0, 426), 30);

  fill(255);
  textSize(15);
  text("If Processing crashes during loading, you need to change the maximum available memory from the default 256MB to 5000MB in Preferences." +
    "\nThis is due to the 24 sprite sheets I am loading. Sorry for any inconvenience.", 0, 80);

  loadAlpha += loadAlphaSpd;

  if (initReady && !fading) {
    startFade(120, LOAD);
  } else {
    if (loadAlpha > 350 || loadAlpha < 0) {
      loadAlphaSpd *= -1;
    }
  }
  popStyle();
  popMatrix();

  transition();
}

public void introScreen() {
  you.draw();

  pushMatrix();
  pushStyle();
  translate(25, 25);
  rectMode(CORNER);
  fill(255, 127);
  rect(0, 0, 170, 410); // info
  rect(10, 10, 150, 390); // info2
  rect(0, 440, 170, 230); // classes
  rect(200, 0, 170, 165); // gender
  rect(200, 195, 170, 295); // hair
  rect(400, 0, 170, 165); // random
  rect(925, 130, 305, 150); // start
  rect(925, 0, 305, 100); // rotate
  textAlign(LEFT, TOP);
  textSize(14);
  fill(0);
  text(getPrints(you), 25, 25);
  popStyle();
  popMatrix();

  for (Button b : custom) {
    b.tick();
    b.draw();
  }
}

public void drawInfoScreen() { 
  pushMatrix();
  pushStyle();
  rectMode(CENTER);
  translate(width/2, 360+4.5f);

  fill(255, 127);
  noStroke();
  rect(0, 0, 500, 400);
  rect(0, 0, 480, 380);

  fill(0);
  textSize(14);
  textAlign(LEFT, CENTER);
  text(getPrints(you), -225, 0);
  textAlign(RIGHT, CENTER);
  text(getPrints(e), 225, 0);
  pushStyle();
  popMatrix();
}

public String getPrints(Person p) {
  return 
    "-NAME-\n   " + p.name + "   \n" +
    "-GENDER-\n   " + p.tempGender + "   \n" + 
    "-HAIR-\n   " + p.tempHair + "   \n" +
    "-CLASS-\n   " + p.tempClass + "   \n" +
    "-STRENGTH-\n   " + p.tempStrong + "   \n" +
    "-HEALTH-\n   " + nf(p.health, 1, 1) + "   \n" +
    "-RESISTANCE-\n   " + nf(p.resist, 1, 1) + "   \n\n" +
    "-WEAPON-\n   " + p.tempWep + "   \n" + 
    "-DAMAGE-\n   " + p.active.damage + "   ";
}

boolean fading = false;
int fadeFrames = 0;
int fadeEnd = 10;
float fadeSpd = 0;
float fadeAlpha = 0;
int toScreen = 0;
public void transition() {
  if (fading == true) {
    fadeFrames++;

    if (fadeAlpha >= 255) {
      fadeSpd *= -1;
      screen = toScreen;

      switch (toScreen) {
      case GAME: 
        resetImgs();
        break;

      case LOAD:
        if (doAudio) {
          turnMusic.loop();
        }
        loadingPhase++;
        screen = MAIN;
        break;

      case INTRO:
        showStory = introStory;
        screen = INTRO;
        nextString();
        println("SCREEN: Introduction");
        break;

      case CHAR:
        println("SCREEN: Character Creator");
        break;
        
        case RESTART:
        println("GAME: Restarting...");
      restartGame();
      }

      toScreen = -666;
    }

    fadeAlpha += fadeSpd;

    if (fadeAlpha < 0) {
      fadeSpd = 0;
      fadeAlpha = 0;
      fading = false;
    }

    pushMatrix();
    fill(0, fadeAlpha);
    noStroke();
    rectMode(CORNER);
    rect(0, 0, 1280, 720);
    popMatrix();
  }
}

public void startFade(int frames, int scr) {
  fadeEnd = frames;
  fadeSpd = (225/frames) * 3;
  fading = true;
  toScreen = scr;
}
int storyPhase = 0;
int stringIndex = 0;
String[] intro0 = {
  "You wake up, head pounding.", 
  "Slowly, you open your eyes and try to look around.", 
  "It's very dark, you can barely see anything.", 
  "Your body hurts too much to move."};
String[] intro1 = {
  "Your eyes have adjusted to the low light", 
  "It seems you're in a large room, with no visible exit."};
String[] intro2 = {
  "You don't remember anything.", 
  "You don't even remember what you look like!", 
  "As you become more aware of your surroundings, you notice something."};
String[] intro3 = {
  "There are three sets of gear on the floor!", 
  "Plate armour and a spear, fit for a warrior;", 
  "Leather armour and a dagger, perfect for a rogue;", 
  "And a set of robes with a wand, made for a mage.", 
  "\nThere is also a note pinned to the wall. You pick it up."};
String[] intro4 = {
  "It reads:", 
  "\"If you're reading this, you've been chosen for redemption.", 
  "You were sentenced to a life in captivity for the crimes you've committed.", 
  "As part of a new initiative, you have the opportunity to fight for your freedom.\""};
String[] intro5 = {
  "You flip the note over. There's more on the back:", 
  "\"Pick up the armour and weapons. They are your only hope.", 
  "You can swap sets in battle, but you must choose one to start with.", 
  "If you successfully defeat enough enemies, you will be free to go.", 
  "Good luck.\""};
String[] introFinish = {
  "\n\nIf you're new here, press T for a tutorial.", 
  "If you're all ready to go, press ENTER to begin!"};

String[] tut1 = {
  "\n\nYou have three main actions you can make: ATTACK, DEFENSE, and UTILITY.", 
  "\nATTACK: you use your weapon against your opponent to deal damage. Simple.", 
  "\nDEFENSE: you take a defensive stance and increase your base resistance by an amount.", 
  "This bonus can only be used once, unless you reset it by changing stances. Speaking of which...", 
  "\nUTILITY: you can change your fighting style.\nWarrior to Mage, Mage to Rogue, and Rogue to Warrior.", 
  "\nEach one of these actions end your turn."};
String[] tut2 = {
  "Each class has slightly different base stats, and each stance is strong against one other.", 
  "You will deal extra damage to your strength, and take more damage from your weakness."};
String[] tut3 = {
  "Warriors are strong against Mages, but weak to Rogues.", 
  "Mages are strong against Rogues, but weak to Warriors.", 
  "Rogues are strong against Warriors, but weak to Mages."};
String[] tut4 = {
  "In addition to the ATTACK, DEFENSE, and UTILITY actions, you have two more options.", 
  "HEAL and INSPECT. They're pretty self-explanatory.", 
  "\nHEAL ends your turn, and will restore health based on how much you're missing.\nIt has a cooldown, so use it wisely!", 
  "\nINSPECT is free, and will reveal the stats of both you and your opponent.\nUse it to gain extra information!"};
String[] tutFinish = {
  "\n\nThat should be everything you need to survive in battle!", 
  "Press ENTER to begin."};

char[] charArray;
int charIndex = 0;

String[][] introStory = {intro0, intro1, intro2, intro3, intro4, intro5, introFinish};
String[][] tutStory = {tut1, tut2, tut3, tut4, tutFinish};
String[][] showStory = {{""}};

String storyString = "";

boolean storyReady = false;

public void drawStory() {
  int textSpd = 5;
  if (keyPressed && key == ' ') {
    textSpd = 0;
  } else {
    textSpd = 5;
  }

  if ((textSpd == 0 || (frameCount % textSpd == 0)) && (charIndex < charArray.length)) {
    storyString += charArray[charIndex];
    charIndex++;
  }

  if (charIndex >= charArray.length && storyPhase < showStory.length) {
    if (stringIndex < showStory[storyPhase].length) {
      nextString();
      storyString += "\n";
      charIndex = 0;
    } else {
      storyReady = true;
    }
  }
}

public void nextString() {
  storyReady = false;
  charArray = showStory[storyPhase][stringIndex].toCharArray();
  stringIndex++;
}

boolean introDone = false;
boolean tutDone = false;
float storyTimer = 0;
float storySpeed = 0.5f;
public void testScreen() {
  clear();

  fill(255);
  drawStory();
  pushMatrix();
  textAlign(CENTER, BOTTOM);
  translate(width/2, 500);
  text(storyString, 0, 0);

  if (storyReady) {
    storyTimer += storySpeed;

    if (storyTimer >= 10 || storyTimer <= 0) {
      storySpeed *= -1;
    }

    translate(0, 10 + storyTimer);
    triangle(-10, 0, 10, 0, 0, 10);
  }
  popMatrix();

  if (showStory[storyPhase] == introFinish && storyReady) {
    introDone = true;
  } else if (showStory[storyPhase] == tutFinish && storyReady) {
    tutDone = true;
  } else {
    introDone = false;
    tutDone = false;
  }
  
  imageMode(CORNER);
  image(vg, 0, 0);

fill(127);
  text("Press SPACE to speed up text. Press ENTER to continue. Press Q to skip entirely.", width/2, 710);
}






class Person {
  // --Fields-- //
  int xPos;
  int yPos = 260;

  String name;
  String type;
  String gender = "m";
  String hair = "";

  float health = 50;
  float maxHealth = health;
  float resist = 1;
  float baseResist = resist;

  boolean dead = false;

  Stance stance;
  Stance weak;
  Stance strong;
  Weapon active;

  String imgString;
  PImage charImg;
  int row = 0;
  int col = 0;
  boolean animate = false;
  boolean utlAnim = false;
  boolean dieAnim = false;
  boolean entAnim = false;
  boolean defAnim = false;
  int animTimer = 0;
  int frame = 0;
  int maxFrame = 0;
  int facing = 0;

  AudioPlayer defSpell, healSpell, wandSpell, daggerSpell, spearSpell, utlSpell;

  // --Constructor(s)-- //
  // Default
  Person() {
    this(war);
  }

  // Stance name
  Person(String s) {
    defSpell = mainDefSpell;
    healSpell = mainHealSpell;
    wandSpell = mainWandSpell;
    daggerSpell = mainDaggerSpell;
    spearSpell = mainSpearSpell;
    utlSpell = mainUtlSpell;

    name = generate.name();
    type = "person";
    float ran = random(2);
    if (ran < 1) {
      gender = "m";
    } else if (ran >= 1) {
      gender = "f";
    }

    switch(s) {
    case war:
      stance = new Warrior();
      weak = new Rogue();
      strong = new Mage();
      active = new Spear();
      break;
    case mag:
      stance = new Mage();
      weak = new Warrior();
      strong = new Rogue();
      active = new Wand();
      break;
    case rog:
      stance = new Rogue();
      weak = new Mage();
      strong = new Warrior();
      active = new Dagger();
      break;
    }

    switch (stance.name) {
    case war:
      resist = baseResist = 1.1f;
      break;
    case mag:
      resist = baseResist = 1.0f;
      break;
    case rog:
      resist = baseResist = 0.9f;
      break;
    }

    imgString = chooseImg(true);
    charImg = getImg(imgString);

    doPrints();
  }

  // --Runtime-- //
  public void tick() {
    doPrints();

    if (takenDamage) {
      dTimer++;

      if (dTimer >= 6*5) {
        takenDamage = false;
        dTimer = 0;
        health -= damageTake;
        damageTake = 0;
      }
    }


    if (health < 0.1f) {
      dead = true;
    }

    if (animate) {
      animTimer++;

      if (entAnim) {
        if (type == "player") {
          xPos++;
        } else if (type == "enemy") {
          xPos--;
        }
      }
      if (animTimer == 6) {
        animTimer = 0;
        frame++;
      }

      if (frame == maxFrame) {
        if (utlAnim) {
          if (resist > baseResist) {
            actionStrings.add(turn + ": " + "All " + name + "'s buffs were reset!");
            println("GAME: All " + name + "'s buffs were reset!");
          }

          String tempString = name + " changed their stance from " + stance.name;
          changeStance();
          setImg(stance.name, false);
          tempString += " to " + stance.name + "!";

          actionStrings.add(turn + ": " + tempString);
          println("GAME: " + tempString);
          utlAnim = false;
        } else if (dieAnim) {
          actionStrings.add(turn+1 + ": " + name + " has been slain!");
          println("GAME: " + name + " has been slain!");
        }

        if (!dieAnim) {
          frame = 0;
        } else {
          frame = 5;
        }

        if (defAnim) {
          defAnim = false;
          setImg(stance.name, false);
        }

        animate = false;
      }
    }
    col = frame;
  }

  public void draw() {
    PImage temp = charImg.get(col*(64*5), row*(64*5), (64*5), (64*5));
    pushMatrix();
    pushStyle();
    image(temp, xPos, yPos);

    // health bar
    if (screen != CHAR) {
      if (resist > baseResist) {
        strokeWeight(4);
        stroke(127);
      } else {
        strokeWeight(2);
        stroke(0);
      }

      rectMode(CORNER);
      fill(150, 0, 0);
      rect(xPos+64, yPos, 192, 32);
      fill(255, 0, 0);
      noStroke();
      rect(xPos+64+2, yPos+2, constrain(map(health, maxHealth, 0, 192-3, 0), 0, 192-3), 32-3);
    }

    popStyle();
    popMatrix();
  }

  // --Methods-- //
  public String toString() {
    if (dead) {
      return ("| " + name + " (" + gender + ") | " + type + " | " + stance.name + " | " + "DEAD" + " | " + resist + "R |");
    }

    return ("| " + name + " (" + gender + ") | " + type + " | " + stance.name + " | " + health + "HP | " + resist + "R |");
  }

  public void action(String s, Person p) {
    switch (s) {
    case atk:
      attack(p, active);
      break;
    case def: 
      defense(p, active);
      break;
    case utl:
      utility(p, active);
      break;
    case hea:
      float h = (maxHealth - health) / 2;
      health += h;
      healUsed = true;
      if (doAudio) {
        healSpell.play(0);
      }
      actionStrings.add(turn+1 + ": " + name + " healed themselves for " + nf(h, 1, 1) + "HP!");
      println("GAME: " + name + " healed themselves for " + nf(h, 1, 1) + "HP!");
      break;

    case "re":
      hair = "re";
      facing = 0;
      setImg(stance.name, false);
      break;
    case "ra":
      hair = "ra";
      facing = 0;
      setImg(stance.name, false);
      break;
    case "bl":
      hair = "bl";
      facing = 0;
      setImg(stance.name, false);
      break;
    case "br":
      hair = "br";
      facing = 0;
      setImg(stance.name, false);
      break;

    case "ma":
      facing = 0;
      while (stance.name != mag) {
        changeStance();
      }
      setImg(s, false);
      break;
    case "wa":
      facing = 0;
      while (stance.name != war) {
        changeStance();
      }
      setImg(stance.name, false);
      break;
    case "ro":
      facing = 0;
      while (stance.name != rog) {
        changeStance();
      }
      setImg(stance.name, false);
      break;

    case "m":
      gender = s;
      setImg(stance.name, false);
      break;
    case "f":
      gender = s;
      setImg(stance.name, false);
      break;

    case "s":
      if (screen == CHAR) {
        println("SCREEN: Gameplay");
        fadeMusic();
        startFade(120, GAME);
      }
      break;

    case "nam":
      name = generate.name();
      break;

    case "rol":
      rot(s);
      break;
    case "ror":
      rot(s);
      break;
    }

    doPrints();
  }

  private void attack(Person p, Weapon w) {
    Attack a = new Attack(this, p, w);
    a.go();
  }

  private void defense(Person p, Weapon w) {
    Defense d = new Defense(this, p, w);
    d.go();
  }

  private void utility(Person p, Weapon w) {
    Utility u = new Utility(this, p, w);
    u.go();
  }

  public void changeStance() {
    switch(stance.name) {
    case rog:
      stance = new Warrior();
      weak = new Rogue();
      strong = new Mage();
      active = new Spear();
      break;
    case war:
      stance = new Mage();
      weak = new Warrior();
      strong = new Rogue();
      active = new Wand();
      break;
    case mag:
      stance = new Rogue();
      weak = new Mage();
      strong = new Warrior();
      active = new Dagger();
      break;
    }

    switch (stance.name) {
    case war:
      resist = baseResist = 1.1f;
      break;
    case mag:
      resist = baseResist = 1.0f;
      break;
    case rog:
      resist = baseResist = 0.9f;
      break;
    }

    doPrints();
  }

  public String chooseImg(boolean b) {
    String ret = "";

    ret += gender;
    switch (stance.name) {
    case war:
      ret += "w";
      break;

    case mag:
      ret += "m";
      break;

    case rog:
      ret += "r";
      break;
    }


    // raven, redhead, light blonde 2, brunette
    // plain, messy 2, long, shorthawk

    // pixie, ponytail 2, swoop, 
    // ra, re, bl, br
    if (b) {
      float ran = random(4);
      if (ran >= 0 && ran < 1) {
        // raven
        ret += "ra";
        hair = "ra";
      } else if (ran >= 1 && ran < 2) {
        // redhead
        ret += "re";
        hair = "re";
      } else if (ran >= 2 && ran < 3) {
        // blonde
        ret += "bl";
        hair = "bl";
      } else if (ran >= 3 && ran < 4) {
        // brunette
        ret += "br";
        hair = "br";
      }
    } else {
      ret += hair;
    }

    return ret;
  }

  public void setImg(String s, boolean b) {
    String temp = chooseImg(b);
    charImg = getImg(temp);

    if (screen != GAME) {
      facing = 0;
      xPos = width/2 - 320/2;
    } else {
      if (type == "player") {
        facing = 1;
        xPos = 000 + 80;
      } else if (type == "enemy") {
        facing = -1;
        xPos = 960 - 80;
      }
    }

    switch(s) {
    case war:
      row = 6 + facing;
      col = 0;
      break;

    default: 
      row = 14 + facing;
      col = 0;
      break;
    }
  }

  public void anim(String s) {
    animate = true;

    switch (s) {
    case ent:
      row = 11 + facing;
      maxFrame = 9;
      break;

    case atk:
      if (stance.name == war) {
        row = 6 + facing;
        maxFrame = 8;
      } else {
        row = 14 + facing;
        maxFrame = 6;
      }
      break;

    case utl:
      utlAnim = true;
      row = 2 + facing;
      maxFrame = 7;
      break;

    case "die": 
      dieAnim = true;
      row = 20;
      maxFrame = 6;
      break;

    case def:
      defAnim = true;
      row = 2 + facing;
      maxFrame = 7;
      break;
    }
  }

  boolean takenDamage = false;
  float damageTake = 0;
  int dTimer = 0;
  public void takeDamage(float f) {
    takenDamage = true;
    damageTake = f;
  }

  public void rot(String s) {
    int turn = 0;

    if (s == "rol") {
      turn = -1;
    } else if (s == "ror") {
      turn = 1;
    }

    if (facing == 1 && turn > 0) {
      facing = -2;
    } else if (facing == -2 && turn < 0) {
      facing = 1;
    } else {
      facing += turn;
    }

    switch(stance.name) {
    case war:
      row = 6 + facing;
      col = 0;
      break;

    default: 
      row = 14 + facing;
      col = 0;
      break;
    }
  }


  String tempHair = "";
  String tempClass = "";
  String tempStrong = "";
  String tempWeak = "";
  String tempWep = "";
  String tempGender = "";
  public void doPrints() {
    tempClass = this.stance.name.substring(0, 1).toUpperCase() + this.stance.name.substring(1);
    tempStrong = this.strong.name.substring(0, 1).toUpperCase() + this.strong.name.substring(1);
    tempWeak = this.weak.name.substring(0, 1).toUpperCase() + this.weak.name.substring(1);
    tempWep = this.active.name.substring(0, 1).toUpperCase() + this.active.name.substring(1);

    if (this.gender == "m") {
      tempGender = "Male";
    } else if (this.gender == "f") {
      tempGender = "Female";
    }

    switch (this.hair) {
    case "re":
      tempHair = "Red";
      break;
    case "ra":
      tempHair = "Black";
      break;
    case "bl":
      if (this.gender == "f") {
        tempHair = "Blonde";
      } else if (this.gender == "m") {
        tempHair = "Blond";
      }
      break;
    case "br":
      tempHair = "Brown";
      break;
    }
  }
}
class Player extends Person {
  // --Fields-- //


  // --Constructor(s)-- //
  // Default
  Player() {
    this(randStance());
  }

  // Stance name
  Player(String s) {
    super(s);
    type = "player";
    setImg(stance.name, true);
  }

  // --Runtime-- //


  // --Methods-- //
}
class Enemy extends Person {
  // --Fields-- //

  // --Constructor(s)-- //
  // Default
  Enemy() {
    this(randStance());
  }

  // Stance name
  Enemy(String s) {
    super(s);
    type = "enemy";
    setImg(stance.name, true);
  }

  // --Runtime-- //

  // --Methods-- //
}
class Weapon {
  // --Fields-- //
  float damage;
  String name;
  Stance stance;

  // --Constructor(s)-- //
  // Name, damage
  Weapon(String n, float d) {
    name = n;
    damage = d;
  }

  // --Runtime-- //


  // --Methods-- //
}
class Spear extends Weapon {
  // --Fields-- //
  
  
  // --Constructor(s)-- //
  Spear() {
    super("spear", 6);
    stance = new Warrior();
  }
  
  // --Runtime-- //
  
  
  // --Methods-- //
  
}
class Wand extends Weapon {
  // --Fields-- //
  
  
  // --Constructor(s)-- //
  Wand() {
    super("wand", 8);
    stance = new Mage();
  }
  
  // --Runtime-- //
  
  
  // --Methods-- //
  
}
class Dagger extends Weapon {
  // --Fields-- //


  // --Constructor(s)-- //
  Dagger() {
    super("dagger", 10);
    stance = new Rogue();
  }

  // --Runtime-- //


  // --Methods-- //
}
class Action {
  // --Fields-- //
  Person owner;
  Person target;
  Weapon weapon;

  // --Constructor(s)-- //
  Action(Person o, Person p, Weapon w) {
    owner = o;
    target = p;
    weapon = w;
  }

  // --Runtime-- //


  // --Methods-- //
  public void go() {
    // dummy
  }
}
class Attack extends Action {
  // --Fields-- //


  // --Constructor(s)-- //
  Attack(Person o, Person p, Weapon w) {
    super(o, p, w);
  }

  // --Runtime-- //


  // --Methods-- //
  public void go() {
    owner.anim(atk);
    float dmg = weapon.damage;

    if (owner.stance.name == target.weak.name) {
      dmg *= 1.5f;
    }

    dmg /= target.resist;

    if (doAudio) {
      switch (owner.active.name) {
      case "dagger":
        owner.daggerSpell.play(0);
        break;
      case "spear":
        owner.spearSpell.play(0);
        break;
      case "wand":
        owner.wandSpell.play(0);
        break;
      }
    }
    actionStrings.add(turn+1 + ": " + owner.name + " attacked " + target.name + " using their " + owner.active.name + " for " + nf(dmg, 1, 1) + " damage!");
    println("GAME: " + owner.name + " attacked " + target.name + " using their " + owner.active.name + " for " + nf(dmg, 1, 1) + " damage!");
    target.takeDamage(dmg);
    //target.health -= dmg;
  }
}
class Defense extends Action {
  // --Fields-- //


  // --Constructor(s)-- //
  Defense(Person o, Person p, Weapon w) {
    super(o, p, w);
  }

  // --Runtime-- //


  // --Methods-- //
  public void go() {
    float res = 1.5f;
    owner.anim(def);
    if (doAudio) {
      owner.defSpell.play(0);
    }

    if (owner.resist > owner.baseResist) {
      actionStrings.add(turn+1 + ": " + owner.name + "'s resistance has already been increased!");
      println("GAME: " + owner.name + "'s resistance has already been increased!");
    } else {
      actionStrings.add(turn+1 + ": " + owner.name + " increased their resistance from " + owner.resist + " to " + nf(owner.resist*res, 1, 1) + "!");
      println("GAME: " + owner.name + " increased their resistance from " + owner.resist + " to " + nf(owner.resist*res, 1, 1) + "!");
      owner.resist *= res;
    }
  }
}
class Utility extends Action {
  // --Fields-- //


  // --Constructor(s)-- //
  Utility(Person o, Person p, Weapon w) {
    super(o, p, w);
  }

  // --Runtime-- //


  // --Methods-- //
  public void go() {
    // printing is done in-class
    if (doAudio) {
      owner.utlSpell.play(0);
    }
    target.anim(utl);
  }
}
class Stance {
  // --Fields-- //
  String name;

  // --Constructor(s)-- //
  Stance() {
    name = "stance";
  }

  // --Runtime-- //


  // --Methods-- //
}
class Warrior extends Stance {
  // --Fields-- //


  // --Constructor(s)-- //
  Warrior() {
    super();
    name = war;
  }

  // --Runtime-- //


  // --Methods-- //
}
class Mage extends Stance {
  // --Fields-- //
  
  
  // --Constructor(s)-- //
  Mage() {
    super();
    name = mag;
  }
  
  // --Runtime-- //
  
  
  // --Methods-- //
  
}
class Rogue extends Stance {
  // --Fields-- //


  // --Constructor(s)-- //
  Rogue() {
    super();
    name = rog;
  }

  // --Runtime-- //


  // --Methods-- //
}
class Button {
  // --Fields-- //
  PVector pos;
  PVector dim;
  boolean pressed = false;
  String todo = "";
  String display = "";

  PImage bImg;

  boolean show = false;

  Button target;

  float lSide, rSide, tSide, bSide;

  // --Constructor(s)-- //
  // Default
  Button() {
    this("");
  }

  // display
  Button(String s) {
    this(s, width/2, height/2, 100, 50);
  }

  // xPos, yPos
  Button(String s, float xp, float yp) {
    this(s, xp, yp, 100, 50);
  }

  // xPos, yPos, xSiz, ySiz
  Button(String s, float xp, float yp, float xs, float ys) {
    this(s, new PVector(xp, yp), new PVector(xs, ys));
  }

  // pos, dim
  Button(String s, PVector p, PVector d) {
    bImg = buttonReady;
    pos = p;
    dim = d;
    display = s;

    lSide = pos.x-dim.x/2f;
    rSide = pos.x+dim.x/2f;
    tSide = pos.y-dim.y/2f;
    bSide = pos.y+dim.y/2f;
  }

  // --Runtime-- //
  public void tick() {
    lSide = pos.x-dim.x/2f;
    rSide = pos.x+dim.x/2f;
    tSide = pos.y-dim.y/2f;
    bSide = pos.y+dim.y/2f;
  }

  public void draw() {
    if (show) {
      pushMatrix();
      pushStyle();

      boolean withinX = mouseX >= lSide && mouseX <= rSide;
      boolean withinY = mouseY >= tSide && mouseY <= bSide;

      textSize(dim.x/9);
      //fill(255);
      bImg = buttonReady;
      if (withinX && withinY) {
        //fill(230);
        bImg = buttonHover;
        if (mousePressed) {
          //fill(127);
          bImg = buttonDown;
          if (pTurn) {
            buttonDown();
          }
        }
        if (!mousePressed && pressed) {
          //fill(127);
          bImg = buttonDown;
          if (pTurn) {
            buttonUp();
          }
        }
      } else {
        pressed = false;
      }

      if (!pTurn) {
        //fill(230);
        bImg = buttonLocked;
      }

      if (display == "HEAL" && healUsed) {
        //fill(230);
        bImg = buttonLocked;
      }

      checkCustom();

      translate(pos.x, pos.y);
      imageMode(CENTER);
      image(bImg, 0, 0, dim.x, dim.y);
      textAlign(CENTER, CENTER);
      fill(0);
      text(display, 0, -1);
      popStyle();
      popMatrix();
    }
  }

  // -- Methods-- //
  public void setAction(String s) {
    todo = s;
  }

  public void setDisplay(Button b) {
    show = true;
    todo = dis;
    target = b;
  }

  public void go() {
  }

  public void fin() {
    if (todo != inf && infoScreen) {
      infoScreen = false;
    }

    switch(todo) {
    case atk:
      you.action(atk, e);
      break;

    case def:
      you.action(def, you);
      break;

    case utl:
      you.action(utl, you);
      break;

    case "display":
      target.show = true;
      break;

    case inf: 
      target.show = true;
      if (infoScreen) {
        infoScreen = false;
      } else {
        infoScreen = true;
      }
      break;

    case hea: 
      if (!healUsed) {
        you.anim(def);
        you.action(hea, you);
        nextTurn();
      }
      break;

    case "ran":
      you = new Player();
      break;

    default:
      you.action(todo, you);
    }

    if (target == null) {
      show = false;
      nextTurn();
    }
  }

  public void buttonDown() {
    if (!pressed) {

      pressed = true;
      go();
      if (doDebug) 
        println("button down");
    }
  }

  public void buttonUp() {
    if (pressed) {
      pressed = false;
      fin();
      if (doDebug)
        println("button up");
    }
  }

  public boolean mouseOver() {
    boolean withinX = mouseX >= lSide && mouseX <= rSide;
    boolean withinY = mouseY >= tSide && mouseY <= bSide;

    return withinX && withinY;
  }

  public void checkCustom() {
    switch (todo) {
    case "re":
      if (you.hair == todo) {
        bImg = buttonLocked;
      }
      break;
    case "ra":
    if (you.hair == todo) {
        bImg = buttonLocked;
      }
      break;
    case "bl":
    if (you.hair == todo) {
        bImg = buttonLocked;
      }
      break;
    case "br":
    if (you.hair == todo) {
        bImg = buttonLocked;
      }
      break;

    case "ma":
    if (you.stance.name == mag) {
        bImg = buttonLocked;
      }
      break;
    case "wa":
    if (you.stance.name == war) {
        bImg = buttonLocked;
      }
      break;
    case "ro":
    if (you.stance.name == rog) {
        bImg = buttonLocked;
      }
      break;

    case "m":
    if (you.gender == todo) {
        bImg = buttonLocked;
      }
      break;
    case "f":
    if (you.gender == todo) {
        bImg = buttonLocked;
      }
      break;
    }
  }
}
class Generator {
  private String[] letters = {
    "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", 
    "r", "s", "t", "v", "w", "x", "z", "bl", "br", "ch", "cl", "cr", 
    "dr", "fl", "fr", "gl", "gr", "pl", "pr", "sc", "sh", "sk", "sl", 
    "sm", "sn", "sp", "st", "sw", "th", "tr", "tw", "wh", "wr", "sch", 
    "scr", "shr", "sph", "spl", "spr", "squ", "str", "thr"};

  private String[] vowels = {
    "a", "e", "i", "o", "u", "y", "ai", "au", "ay", "ea", "ee", "ei", 
    "ey", "ie", "oi", "oo", "ou", "oy"};

  private String[] consonants = {
    "b", "c", "d", "f", "g", 
    "h", "j", "k", "l", "m", 
    "n", "p", "q", "r", "s", 
    "t", "v", "w", "x", "y", 
    "z", "th", "sh", "st", 
    "sht", "ph"};

  public String name() {
    String ret = "";
    ret += letters[(int)random(letters.length)]; // 3
    ret += vowels[(int)random(vowels.length)]; // 2
    ret += consonants[(int)random(consonants.length)]; // 3
    ret += "'"; // 1
    ret += consonants[(int)random(consonants.length)]; // 3
    ret += vowels[(int)random(vowels.length)]; //2
    ret += consonants[(int)random(consonants.length)]; // 3
    String sub = ret.substring(0, 1);
    String subUp = sub.toUpperCase();
    ret = subUp + ret.substring(1);
    return ret;
  }
}
  public void settings() {  size(1280, 720); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Redemption" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
