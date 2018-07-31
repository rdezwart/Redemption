class Player extends Person {
  // --Fields-- //


  // --Constructor(s)-- //
  // Default
  Player() {
    this(randStance());
  }

  // Stance name
  Player(String s) {
    super(s);
    type = "player";
    setImg(stance.name, true);
  }

  // --Runtime-- //


  // --Methods-- //
}
