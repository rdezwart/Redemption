class Defense extends Action {
  // --Fields-- //


  // --Constructor(s)-- //
  Defense(Person o, Person p, Weapon w) {
    super(o, p, w);
  }

  // --Runtime-- //


  // --Methods-- //
  void go() {
    float res = 1.5;
    owner.anim(def);
    if (doAudio) {
      owner.defSpell.play(0);
    }

    if (owner.resist > owner.baseResist) {
      actionStrings.add(turn+1 + ": " + owner.name + "'s resistance has already been increased!");
      println("GAME: " + owner.name + "'s resistance has already been increased!");
    } else {
      actionStrings.add(turn+1 + ": " + owner.name + " increased their resistance from " + owner.resist + " to " + nf(owner.resist*res, 1, 1) + "!");
      println("GAME: " + owner.name + " increased their resistance from " + owner.resist + " to " + nf(owner.resist*res, 1, 1) + "!");
      owner.resist *= res;
    }
  }
}
