class Utility extends Action {
  // --Fields-- //


  // --Constructor(s)-- //
  Utility(Person o, Person p, Weapon w) {
    super(o, p, w);
  }

  // --Runtime-- //


  // --Methods-- //
  void go() {
    // printing is done in-class
    if (doAudio) {
      owner.utlSpell.play(0);
    }
    target.anim(utl);
  }
}
