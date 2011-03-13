
import br.com.bi.model.Application;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luiz
 */
public class Test {

    public static void main(String args[]) {

        System.out.println(Application.getCubeDao().findByName("Roger Cube").getId());
    }
}
