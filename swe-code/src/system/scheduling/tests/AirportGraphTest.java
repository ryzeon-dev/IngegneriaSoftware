package system.scheduling.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.Airport;
import model.DimensionClass;
import system.scheduling.AirportGraph;

public class AirportGraphTest {
    @Test
    public void graphTest(){
        Airport a1= new Airport("a1", DimensionClass.C3, "T1", "T1", "T1");
        Airport a2= new Airport("a2", DimensionClass.E4, "T2", "T2", "T2");
        Airport a3= new Airport("a3", DimensionClass.E4, "T3", "T3", "T3");
        Airport a4= new Airport("a4", DimensionClass.E4, "T4", "T4", "T4");
        AirportGraph graph= new AirportGraph();
        graph.addEdge(a1, a2,10);
        graph.addEdge(a1, a3, 40);
        graph.addEdge(a2, a1,10);
        graph.addEdge(a3, a2,20);
        assertEquals("a1-10->a2-40->a3\na2-10->a1\na3-20->a2\n", graph.toString());
        assertTrue(graph.containsVertex(a1));
        assertTrue(graph.containsVertex(a2));
        assertTrue(graph.containsVertex(a3));
        assertFalse(graph.containsVertex(a4));

    }
}
