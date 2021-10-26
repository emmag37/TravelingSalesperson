PROJECT TITLE: Traveling Salesperson Problem
PURPOSE OF PROJECT: Implement two greedy heuristics in the attempt to find
an optimal solution to the Traveling Salesperson Problem
DATE: Dec 11, 2020
AUTHOR: Emma Glenn
ALGORITHM DESCRIPTIONS: 
    insertNearest()
First, the method traverses the entire list. This first loop calculates the distance between the new Point and the current
Point it is on. If this distance is smaller than the current minimum distance, it will set the minimum distance to the new value
and save the current node as the position to add the Point after. After it has gone through all of the points, the method adds
the new node after the one saved when the smallest distance was found.
O(N^2) complexity
    
    insertSmallest()
This method also traverses the entire list. In this loop, the method calculates the increase in distance the point would cause
if appended after each point using the equation (distance between current point and new point) + (distance between successive
point to the current point and new point) - (distance between current and its successive point). The point after the final point
in the list is the first point. After calculating this value, still in the loop, the method compares it to the current smallest
value. If the calculated value is smaller, it is the new minimum distance value and the current node is saved. After going
through each node in the list, the method adds the point after the saved point.
O(N^2) complexity