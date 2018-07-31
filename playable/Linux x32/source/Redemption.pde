void setup() {
  size(1280, 720);
  surface.setTitle("Redemption | v1.0 | Robin de Zwart");
}

void draw() {
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
