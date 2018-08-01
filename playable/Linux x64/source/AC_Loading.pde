boolean fastLoading = false;

void loadButtons() {
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
  staButton = new Button("Start!", 1102.5, 230, 255, 100);
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

void setButtons() {
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

void loadImages() {
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

void loadMales() {
  thread("loadMR");
  thread("loadMW");
  thread("loadMM");
}

void loadMR() {
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

void loadMW() {
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

void loadMM() {
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

void loadFemales() {
  thread("loadFR");
  thread("loadFW");
  thread("loadFM");
}

void loadFR() {
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

void loadFW() {
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

void loadFM() {
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
