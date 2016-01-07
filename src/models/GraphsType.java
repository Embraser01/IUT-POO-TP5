package models;

public enum GraphsType {

    Cycle("Cycle"),
    Chaine("Chaine"),
    Tore("Tore"),
    Grille_carree("Grille carrée"),
    Arbre_n_aire_complet("Arbre n-aire complet"),
    Graphe_aleatoire("Graphe aléatoire");


    private final String name;

    private GraphsType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }

}
