void pTurn() {
  pTurn = true;
  eTurn = false;
}

void eTurn() {
  eTurn = true;
  pTurn = false;
}

String randStance() {
  float ran = random(3);
  if (ran < 1) {
    return war;
  } else if (ran >= 1 && ran < 2) {
    return mag;
  } else { // ran >= 2
    return rog;
  }
}

void makeMove() {
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

void nextTurn() {
  turn++;
}

PImage getImg(String s) {
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

void restartGame() {
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

void prog() {
  loadingProgress++;
  loadingPercentage += 100f / OBJECTS_TO_LOAD;
}

void resetImgs() {
  you.setImg(you.stance.name, false);
  e.setImg(e.stance.name, false);
}

void fadeMusic() {
  float turnVol = turnMusic.getGain();
  turnMusic.shiftGain(turnVol, -80, 5000);

  screwMusic.setGain(-80);
  if (doAudio) {
    screwMusic.loop();
  }
  screwMusic.shiftGain(-80, 0, 5000);
}
