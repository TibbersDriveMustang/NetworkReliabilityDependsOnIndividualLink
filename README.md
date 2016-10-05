For 5 nodes 10 edges, we assume nodes all work well, each edge is either Up or Down, and we will know the p for each edge. if the system is operational, it means there is no unconnected node.
Exhaustive enumeration:
List all possible states of the system.
Assign “up” and “down” system condition to each state Reliability can be obtained by summing the probability of the “up” states.
We are going to calculate all possible combinations, since 10 edges will have 210 combinations, we store each system state in one int array: int[11] , first 10 integer will be represent the components`(edges`s) states, the last integer will represent system condition(Up or Down). 
All integer arrarys will be combined into one list : List<int[]>, thus if we want choose one random system condition, we just need to generate a random index of the List, then pick the correspond integer array out, that will be one random combination.
While calculating the System Reliability, we first check the last integer in the integer arrays to see if the System will be Up, if it is down, ignore it. We will check all 1024 integer arrays and pick those System Condition is Up, then find the p of each edges to figure out the final system reliabilities .

![alt tag](https://github.com/TibbersDriveMustang/NetworkReliabilityDependsOnIndividualLink/blob/master/screenshot/randomSetNodesCreated.png)
Random Set of Nodes Created

![alt tag](https://github.com/TibbersDriveMustang/NetworkReliabilityDependsOnIndividualLink/blob/master/screenshot/originalGraphGenerated.png)
Original Graph Created

![alt tag](https://github.com/TibbersDriveMustang/NetworkReliabilityDependsOnIndividualLink/blob/master/screenshot/totalCostGraph.png)
Total Path Cost Graph

![alt tag](https://github.com/TibbersDriveMustang/NetworkReliabilityDependsOnIndividualLink/blob/master/screenshot/systemReliability.png)
System Reliability Calculated
