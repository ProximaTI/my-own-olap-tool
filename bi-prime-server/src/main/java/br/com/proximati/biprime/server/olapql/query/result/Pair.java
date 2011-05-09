/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.query.result;

/**
 *
 * @author luiz
 */
public class Pair<T, U> {

    private T obj1;
    private U obj2;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair<T, U> other = (Pair<T, U>) obj;
        if (this.obj1 != other.obj1 && (this.obj1 == null || !this.obj1.equals(other.obj1))) {
            return false;
        }
        if (this.obj2 != other.obj2 && (this.obj2 == null || !this.obj2.equals(other.obj2))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.obj1 != null ? this.obj1.hashCode() : 0);
        hash = 89 * hash + (this.obj2 != null ? this.obj2.hashCode() : 0);
        return hash;
    }

    public Pair(T obj1, U obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    public T getObj1() {
        return obj1;
    }

    public void setObj1(T obj1) {
        this.obj1 = obj1;
    }

    public U getObj2() {
        return obj2;
    }

    public void setObj2(U obj2) {
        this.obj2 = obj2;
    }
}
