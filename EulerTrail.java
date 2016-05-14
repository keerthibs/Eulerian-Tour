import java.util.*;

/**
 * Created by monish kumar, keerthi bala sundram on 3/7/16.
 */
public class EulerTrail {

    //the initiation function for finding Euler tour
    public static List<Edge> findEulerTour(Graph g) {
        Vertex startVertex = g.verts.get(1);
        return findEulerTour(g, startVertex);
    }

    //This is the main function to find the Euler tour traversing each edge connected to every vertex once.
    public static List<Edge> findEulerTour(Graph g, Vertex startVertex) {
        //Stack of Vertices
        Stack<Vertex> verticesStack = new Stack<>();
        //Stack of the vertices as they are added in the tour
        Stack<Vertex> VerticesTour = new Stack<>();
        Vertex currentVertex;

        List<Vertex> vertices = new ArrayList<>(g.verts);
        vertices.remove(0);
        List<Vertex> oddDegreeVertices = getOddDegreeVertices(vertices);
        if(oddDegreeVertices == null) {
            return null;
        }
        int oddDegreeCount = oddDegreeVertices.size();
        if( oddDegreeCount == 0 || oddDegreeCount == 2) {
            if(oddDegreeCount == 2) {
                currentVertex = oddDegreeVertices.get(0);
            } else {
                currentVertex = startVertex;
            }
            Edge currentEdge;

            if(vertices.size()<2)
                return null;
            verticesStack.push(currentVertex);

           while(!verticesStack.empty() ) {
               currentEdge = nextUnvisitedEdge(currentVertex);

               if (currentEdge != null) {
                   verticesStack.push(currentVertex);
                   currentVertex = currentEdge.otherEnd(currentVertex);
               } else {
                   VerticesTour.push(currentVertex);
                   if(!verticesStack.empty())
                       currentVertex = verticesStack.pop();
                   else
                       break;
               }
           }
        } else {
            System.out.println("Graph is not Eulerian");
        }

        return getPathFromVertices(VerticesTour);
    }

    //Verifies the if the tour is a valid one or not
    public static boolean verifyTour(Graph g, List<Edge> tour, Vertex start) {
        List<Edge> path = findEulerTour(g, start);

        if(path.isEmpty())
            return false;
        else if(checkGraph(g))
            return true;
        else
            return false;
    }

    //Returns the set of vertices which have an odd degree i.e if the off number of edges are connected to that vertex
    //There can be either 0 or 2 odd degree vertices
    public static List<Vertex> getOddDegreeVertices(List<Vertex> vertexList) {

        List<Vertex> vertices = new ArrayList<>();
        Iterator<Vertex> vertexIterator = vertexList.listIterator();
        int i = 0;

        while(vertexIterator.hasNext()) {
            Vertex vertex = vertexIterator.next();
            if(vertex.degree == 0) {
                return null;
            } else if(vertex.degree % 2 != 0) {
                vertices.add(i++, vertex);
            }
        }

        return vertices;

    }

    //Finds the unvisited edge connected to a vertex which is passed to the function.
    //The function returns null if no such edge exists
    public static Edge nextUnvisitedEdge(Vertex v) {
        for (Edge e : v.Adj) {
            if (!e.Visited) {
                e.Visited = true;
                return e;
            }
        }
        return null;
    }

    //The path is found from the stack of EulerTour which was created in the findEukerTour method.
    //The stack stores the vertices in the order of Euler Path.
    // i.e. if, 1 -> 2 -> 3 -> 4, the stack will store the vertices as 4 3 2 1, which is in the reverse order of discovery of the vertices, based on the edges.
    public static List<Edge> getPathFromVertices(Stack<Vertex> verticesTour) throws EmptyStackException{
        LinkedList<Edge> path = new LinkedList<>();

        int i = 0;
        Vertex v1, v2;
            if(verticesTour.size()<2) {
                return null;
            }
            v1 = verticesTour.pop();
            v2 = verticesTour.pop();
            while(v2!=null) {
                Edge edge = v1.getEdge(v2);
                path.add(i++, edge);
                v1 = v2;
                if(verticesTour.size()>0)
                    v2 = verticesTour.pop();
                else
                    v2 = null;
            }
        return path;
    }

    
    public static boolean checkGraph(Graph g) {
        List<Vertex> vertices = new ArrayList<>(g.verts);
        vertices.remove(0);
        List<Vertex> oddDegreeVertices = getOddDegreeVertices(vertices);
        int oddDegreeCount = oddDegreeVertices.size();
        if(oddDegreeCount == 0 || oddDegreeCount == 2)
            return true;
        else
            return false;
    }
}
