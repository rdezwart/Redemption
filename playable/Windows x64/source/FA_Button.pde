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
  void tick() {
    lSide = pos.x-dim.x/2f;
    rSide = pos.x+dim.x/2f;
    tSide = pos.y-dim.y/2f;
    bSide = pos.y+dim.y/2f;
  }

  void draw() {
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
  void setAction(String s) {
    todo = s;
  }

  void setDisplay(Button b) {
    show = true;
    todo = dis;
    target = b;
  }

  void go() {
  }

  void fin() {
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

  void buttonDown() {
    if (!pressed) {

      pressed = true;
      go();
      if (doDebug) 
        println("button down");
    }
  }

  void buttonUp() {
    if (pressed) {
      pressed = false;
      fin();
      if (doDebug)
        println("button up");
    }
  }

  boolean mouseOver() {
    boolean withinX = mouseX >= lSide && mouseX <= rSide;
    boolean withinY = mouseY >= tSide && mouseY <= bSide;

    return withinX && withinY;
  }

  void checkCustom() {
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
