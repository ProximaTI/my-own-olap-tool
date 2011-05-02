/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime;

import br.com.proximati.biprime.ui.CubeHandlerTest;
import br.com.proximati.biprime.ui.FilterHandlerTest;
import br.com.proximati.biprime.ui.MeasureHandlerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author carlos
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({CubeHandlerTest.class,MeasureHandlerTest.class,FilterHandlerTest.class})
public class AllUnitTests {
    
}
