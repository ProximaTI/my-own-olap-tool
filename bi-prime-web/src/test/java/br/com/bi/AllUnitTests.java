/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi;

import br.com.bi.ui.CubeHandlerTest;
import br.com.bi.ui.FilterHandlerTest;
import br.com.bi.ui.MeasureHandlerTest;

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
