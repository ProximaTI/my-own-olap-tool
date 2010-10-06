package br.com.bi.model.entity.queries;

/**
 * Classe que representa uma propriedade de um nó de uma consulta.
 * @author Luiz Augusto Garcia da Silva
 */
public class Property {
    public static final int TYPE_STRING = 1;
    public static final int TYPE_INTEGER = 2;
    public static final int TYPE_DOUBLE = 3;
    public static final int TYPE_BOOLEAN = 4;
    public static final int TYPE_DATE = 5;

    private String name;
    private String value;
    private int type;

    public Property(String name, String value, int type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public Property(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public Property() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
