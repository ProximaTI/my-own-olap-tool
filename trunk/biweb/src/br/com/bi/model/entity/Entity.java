package br.com.bi.model.entity;

/**
 * Classe base entidades.
 * @author Luiz Augusto Garcia da Silva
 */
public abstract class Entity implements Cloneable {
    private Boolean persisted = Boolean.FALSE;

    public Boolean isPersisted() {
        return persisted;
    }

    public void setPersisted(Boolean persisted) {
        this.persisted = persisted;
    }

    public boolean compareObjects(Object obj1, Object obj2) {
        return (obj1 == null && obj2 == null) ||
            (obj1 != null && obj2 != null && obj1.equals(obj2));
    }

    public abstract Entity clone();
}
