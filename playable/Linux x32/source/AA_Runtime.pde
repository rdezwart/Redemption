void update() {
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
void render() {
  hud();
  you.draw();
  e.draw();
}

// Draws buttons, and info screen if necessary
void hud() {
  buttons();

  if (infoScreen) {
    drawInfoScreen();
  }
}

// Draws all buttons in arraylist
void buttons() {
  for (Button b : menu) {
    b.tick();
    b.draw();
  }
}

// Manages the turns of player and enemies
void turnManager() {
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
void gameState() {
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
