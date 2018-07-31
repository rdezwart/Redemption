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

void drawStory() {
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

void nextString() {
  storyReady = false;
  charArray = showStory[storyPhase][stringIndex].toCharArray();
  stringIndex++;
}

boolean introDone = false;
boolean tutDone = false;
float storyTimer = 0;
float storySpeed = 0.5;
void testScreen() {
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
