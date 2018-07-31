void keyPressed() {
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
