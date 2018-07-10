class Attack extends Action {
  // --Fields-- //


  // --Constructor(s)-- //
  Attack(Person o, Person p, Weapon w) {
    super(o, p, w);
  }

  // --Runtime-- //


  // --Methods-- //
  void go() {
    owner.anim(atk);
    float dmg = weapon.damage;

    if (owner.stance.name == target.weak.name) {
      dmg *= 1.5;
    }

    dmg /= target.resist;

    if (doAudio) {
      switch (owner.active.name) {
      case "dagger":
        owner.daggerSpell.play(0);
        break;
      case "spear":
        owner.spearSpell.play(0);
        break;
      case "wand":
        owner.wandSpell.play(0);
        break;
      }
    }
    actionStrings.add(turn+1 + ": " + owner.name + " attacked " + target.name + " using their " + owner.active.name + " for " + nf(dmg, 1, 1) + " damage!");
    println("GAME: " + owner.name + " attacked " + target.name + " using their " + owner.active.name + " for " + nf(dmg, 1, 1) + " damage!");
    target.takeDamage(dmg);
    //target.health -= dmg;
  }
}
