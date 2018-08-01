void screen() {
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
void gameScreen() {
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
void menuScreen() {
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

void overScreen() {
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
void loadScreen() {
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

void introScreen() {
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

void drawInfoScreen() { 
  pushMatrix();
  pushStyle();
  rectMode(CENTER);
  translate(width/2, 360+4.5);

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

String getPrints(Person p) {
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
void transition() {
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

void startFade(int frames, int scr) {
  fadeEnd = frames;
  fadeSpd = (225/frames) * 3;
  fading = true;
  toScreen = scr;
}
