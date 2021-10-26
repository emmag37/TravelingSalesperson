import java.util.*;
import java.awt.Graphics;

/**
 * This class is a specialized Linked List of Points that represents a
 * Tour of locations attempting to solve the Traveling Salesperson Problem
 * 
 * @Emma Glenn
 * @version 1
 */

public class Tour implements TourInterface
{
    // instance variables
    private int size;
    private ListNode front;
    private ListNode rear;
    
    // constructor
    public Tour()
    {
        front = null;
        rear = null;
        size = 0;
    }
        
    // return the number of points (nodes) in the list   
    public int size()
    {
        return size;
    }

    // append Point p to the end of the list
    public void add(Point p)
    {
        ListNode newPoint = new ListNode(p); // creates new node to hold Point p
        if (size == 0) // adds the point to the front of the list if it is empty
            front = newPoint;
        else // adds point to the end of the list if it is not empty
            rear.setLink(newPoint);
        rear = newPoint; // sets the rear to Point p
        size++; // increments size
    } 
    
    // print every node in the list 
    public void print()
    {
        ListNode currPos = front; // creates a node to traverse the list
        int i = 0; // index to keep track of position in the list
        
        while (i < size)
        {
            System.out.println(currPos.getPoint()); // prints the point in a new line
            currPos = currPos.getLink();
            i++;
        }
    }
    
    // draw the tour using the given graphics context
    public void draw(Graphics g)
    {
        ListNode currPos = front; // creates a new node to traverse the list
        int i = 0; // index to track position in the list
        int x = 0; // x variable of the current point
        int y = 0; // y variable of the current point
        int x1 = 0; // x variable of the previous point
        int y1 = 0; // y variable of the previous point
        
        // draws all of the points, draws all of the lines except from the last to the first point
        while (i < size)
        {
            x = (int)currPos.getPoint().getX(); // sets the current x variable
            y = (int)currPos.getPoint().getY(); // sets the current y variable
            g.fillOval(x - 2, y - 2, 5, 5); // draws the point centered at x,y; with a diameter of 5
            
            if (i > 0) // draws the line between the current and previous point
                g.drawLine(x, y, x1, y1);
            x1 = x; // sets the previous x to the current x
            y1 = y; // sets the previous y to the current y
            currPos = currPos.getLink();
            i++;
        }
        
        int fx = (int)front.getPoint().getX(); // get the x value of the first point
        int fy = (int)front.getPoint().getY(); // get the y value of the first point
        g.drawLine(x, y, fx, fy); // draws the line between the first and last points
    }
    
    // calculate the distance of the Tour, but summing up the distance between adjacent points
    // NOTE p.distance(p2) gives the distance where p and p2 are of type Point
    public double distance()
    {
        ListNode currPos = front; // new node to traverse the list
        int i = 0; // index variable
        double d = 0; // distance variable to return
        
        // adds the distances between each adjacent points in the list
        while (i < size - 1)
        {
            d += currPos.getPoint().distance(currPos.getLink().getPoint());
            currPos = currPos.getLink();
            i++;
        }
        
        d += front.getPoint().distance(rear.getPoint()); // adds distance between first and last point
        return d;
    }

    // add Point p to the list according to the NearestNeighbor heuristic
    public void insertNearest(Point p)
    {   
        ListNode newPoint = new ListNode(p); // new node to hold the given Point
        
        if (size == 0) // adds the point to the list if it is empty
        {
            front = newPoint;
            rear = newPoint;
        }
        else // adds the new Point after the Point it is closest to
        {
            ListNode currPos = front; // new node to traverse the list
            ListNode minPos = null; // new node to hold the Point that is closest to p
            double minDistance = 1000; // variable to hold the current smallest distance between p and another Point
            double distance = 0; // variable to hold the distance between p and the current Point
            int i = 0; // index variable
            
            while (i < size) // find the index of the point that p is closest to
            {
                distance = currPos.getPoint().distance(p);
                
                if (distance < minDistance) // compares current distance to the smallest distance
                {
                    minDistance = distance;
                    minPos = currPos; // sets the minimum distance node to the current node
                }
                
                currPos = currPos.getLink();
                i++;
            }
            
            newPoint.setLink(minPos.getLink()); // adds the new Point after the node it is closest to
            minPos.setLink(newPoint); 
            
            if (rear.equals(minPos)) // sets rear to the new node if it has been apphended to the end of the list
                rear = newPoint;
        }
        
        size++; // increments size
    }
        
    // add Point p to the list according to the InsertSmallest heuristic
    public void insertSmallest(Point p)
    { 
        ListNode newPoint = new ListNode(p); // creates a new node to hold the new Point
        
        if (size == 0) // adds the Point to the beginning of the list if it is empty
        {
            front = newPoint;
            rear = newPoint;
        }
        else // adds the Point after which is causes the smallest increase in total distance
        {
            ListNode currPos = front; // node to traverse the list
            ListNode minPos = front; // node to hold the node that p will be added after
            int i = 0; // index variable
            double minDistance = 10000; // smallest distance variable
            double distance2 = 0; // variable for the distance between p and and Point after the current Point
            double oldDistance = 0; // variable for the distance between the current Point and its successor
            double incDistance = 0; // variable for the increase in distance caused by p
            
            while (i < size) // find the node to add p after
            {
                if (i == size - 1) // uses the front Point to find distance values if the current Point is at the end of the list
                {
                    distance2 = front.getPoint().distance(p);
                    oldDistance = front.getPoint().distance(currPos.getPoint());
                }
                else // uses the Point after the current one to find distance values
                {
                    distance2 = currPos.getLink().getPoint().distance(p);
                    oldDistance = currPos.getPoint().distance(currPos.getLink().getPoint());
                }
                
                incDistance = (currPos.getPoint().distance(p) + distance2) - oldDistance; // calculates the change in distance
                
                if (incDistance < minDistance) // sets incDistance to minDistance if it is smaller
                {
                    minDistance = incDistance;
                    minPos = currPos; // sets the minimum node to the current node
                }
                
                currPos = currPos.getLink();
                i++;
            }
            
            newPoint.setLink(minPos.getLink()); // adds p after the minimum node
            minPos.setLink(newPoint);
            
            if (rear.equals(minPos)) // sets rear to the new node if it has been added to the end of the list
                rear = newPoint;
        }
        
        size++; //increments size
    }
    
    // This is a private inner class, which is a separate class within a class.
    private class ListNode
    {
        private Point data;
        private ListNode next;
        public ListNode(Point p, ListNode n)
        {
            this.data = p;
            this.next = n;
        }
        
        public ListNode(Point p)
        {
            this(p, null);
        }
        
        //returns the Listnode that it points to
        public ListNode getLink()
        {
            return next;
        }
        
        //changes the link of a node
        public void setLink(ListNode n)
        {
            next = n;
        }
        
        //returns the data in a node
        public Point getPoint()
        {
            return data;
        }
    }
    
    
  

}