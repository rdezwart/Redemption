class Generator {
  private String[] letters = {
    "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", 
    "r", "s", "t", "v", "w", "x", "z", "bl", "br", "ch", "cl", "cr", 
    "dr", "fl", "fr", "gl", "gr", "pl", "pr", "sc", "sh", "sk", "sl", 
    "sm", "sn", "sp", "st", "sw", "th", "tr", "tw", "wh", "wr", "sch", 
    "scr", "shr", "sph", "spl", "spr", "squ", "str", "thr"};

  private String[] vowels = {
    "a", "e", "i", "o", "u", "y", "ai", "au", "ay", "ea", "ee", "ei", 
    "ey", "ie", "oi", "oo", "ou", "oy"};

  private String[] consonants = {
    "b", "c", "d", "f", "g", 
    "h", "j", "k", "l", "m", 
    "n", "p", "q", "r", "s", 
    "t", "v", "w", "x", "y", 
    "z", "th", "sh", "st", 
    "sht", "ph"};

  public String name() {
    String ret = "";
    ret += letters[(int)random(letters.length)]; // 3
    ret += vowels[(int)random(vowels.length)]; // 2
    ret += consonants[(int)random(consonants.length)]; // 3
    ret += "'"; // 1
    ret += consonants[(int)random(consonants.length)]; // 3
    ret += vowels[(int)random(vowels.length)]; //2
    ret += consonants[(int)random(consonants.length)]; // 3
    String sub = ret.substring(0, 1);
    String subUp = sub.toUpperCase();
    ret = subUp + ret.substring(1);
    return ret;
  }
}
