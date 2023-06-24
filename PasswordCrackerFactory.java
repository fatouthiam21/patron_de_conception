class PasswordCrackerFactory {
    public PasswordCrackingAlgorithm createAlgorithm(String algorithmType, String password,String hash,String chemin, boolean estUnHash) {
        if (algorithmType.equalsIgnoreCase("bruteforce")) {
            return new BruteForceCracker(password, hash, estUnHash);
        } else  if (algorithmType.equalsIgnoreCase("dictionary")) {
            return new DictionaryCracker(chemin, password, estUnHash);
        } else  {
            throw new IllegalArgumentException("Algorithme non pris en charge : " + algorithmType);
        }
    }
}