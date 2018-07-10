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

void initialize() {
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
