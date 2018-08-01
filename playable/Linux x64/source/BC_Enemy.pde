class Enemy extends Person {
  // --Fields-- //

  // --Constructor(s)-- //
  // Default
  Enemy() {
    this(randStance());
  }

  // Stance name
  Enemy(String s) {
    super(s);
    type = "enemy";
    setImg(stance.name, true);
  }

  // --Runtime-- //

  // --Methods-- //
}
