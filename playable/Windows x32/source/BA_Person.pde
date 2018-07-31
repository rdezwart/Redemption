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
      resist = baseResist = 1.1;
      break;
    case mag:
      resist = baseResist = 1.0;
      break;
    case rog:
      resist = baseResist = 0.9;
      break;
    }

    imgString = chooseImg(true);
    charImg = getImg(imgString);

    doPrints();
  }

  // --Runtime-- //
  void tick() {
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


    if (health < 0.1) {
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

  void draw() {
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
  String toString() {
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

  void changeStance() {
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
      resist = baseResist = 1.1;
      break;
    case mag:
      resist = baseResist = 1.0;
      break;
    case rog:
      resist = baseResist = 0.9;
      break;
    }

    doPrints();
  }

  String chooseImg(boolean b) {
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

  void setImg(String s, boolean b) {
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

  void anim(String s) {
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
  void takeDamage(float f) {
    takenDamage = true;
    damageTake = f;
  }

  void rot(String s) {
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
  void doPrints() {
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
