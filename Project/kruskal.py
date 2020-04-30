import os
import time
import pygame
import random
import math
import copy
from collections import defaultdict

# INSTRUCTIONS AND DESCRIPTION
# Just run this file
# a screen will popup with a graph and it will automatically start finding the minimum spanning tree
# the minimum spanning tree algorithm would make the lines green
# after it finishes the algorithm, resets the graph with new random edges and does the algorithm again

# CODE FOR KRUSKALS ALGORITHM
#epresent a graph with set of nodes and edges and stuff
class Graph: 
    def __init__(self,nodes): 
        self.n= nodes # nodes
        self.graph = [] # list to store the edges and stuff
   
    # function to add an edge to graph 
    def addEdge(self,u,v,w): 
        self.graph.append([u,v,w]) 
        # (src,dst,weight)
        # in this programs case, weight is just hte length of the edge/line
  
    #function to find set of an element i using path compression
    # used to check if the set of edges form a loop
    def find(self, parent, i): 
        if parent[i] == i: 
            return i 
        return self.find(parent, parent[i]) 
  
    # A function that does union by rank of two sets of x and y 
    def union(self, parent, rank, x, y): 
        xroot = self.find(parent, x) 
        yroot = self.find(parent, y) 
  
        #like a bstree
        #smaller rank tree would be under a higher rank tree
        if rank[xroot] < rank[yroot]: 
            parent[xroot] = yroot 
        elif rank[xroot] > rank[yroot]: 
            parent[yroot] = xroot 
        else: # if ranks are same, then make one be the root and increase its rank by 1 
            parent[yroot] = xroot 
            rank[xroot] += 1
  
    # kruskals algorithm
    def KruskalMST(self): 
        result = []
        i = 0 # var for edges
        ridx = 0 # var for the result index
  
        # sorts the graph by the weight
        #the lowest weight is first
        self.graph =  sorted(self.graph,key=lambda item: item[2]) 
        #print(self.graph)
        parent = []
        rank = []
        # Create node subsets with single elements 
        for node in range(self.n): 
            parent.append(node) 
            rank.append(0) 
      
        # Number of edges to be taken is equal to V-1 
        # this is just logic, the minimum edges to connect all could only be the number of nodes - 1
        # while result idx is not nodes - 1, then still more to go
        # so loops through the edges, from least weight to highest
        while ridx < self.n -1 : 
            #get an edge, from least to highest, as i increases, so does the weight
            u,v,w =  self.graph[i] 
            i = i + 1
            # increment i, the edge that it's at currently

            # gets x and y so can check if it forms a loop
            x = self.find(parent, u) 
            y = self.find(parent ,v) 
  
            # checks if adding x y edge would result in a loop then dont
            # else doesnt do anything, keeps ridx same and result same
            if x != y: 
                ridx += 1 # increments index
                # adds to result, found an edge that does not result in a loop
                result.append((nodes[u], nodes[v])) 
                self.union(parent, rank, x, y)               
        return result

# CODE FOR THE GUI

# dimensions of the screen
WIDTH = 700
HEIGHT = 700

# where the nodes are at based on the screen
# the numbers also indicate what node - 1
one = (350,120)
two = (350,630)
three = (625,375)
four = (75,375)
five = (250,275)
six = (450,275)
seven = (250,475)
eight = (450,475)
nodes = [one, two, three, four, five, six, seven, eight]

def drawNodes(window):
    #draws 8 nodes
    pygame.draw.circle(window, (255,255,255), one, 10)
    pygame.draw.circle(window, (255,255,255), two, 10)
    pygame.draw.circle(window, (255,255,255), three, 10)
    pygame.draw.circle(window, (255,255,255), four, 10)
    pygame.draw.circle(window, (255,255,255), five, 10)
    pygame.draw.circle(window, (255,255,255), six, 10)
    pygame.draw.circle(window, (255,255,255), seven, 10)
    pygame.draw.circle(window, (255,255,255), eight, 10)
    pygame.display.update()

# draws the edges
def drawEdges(window, g):
    for node in nodes:
        # copies the node and removes the current node, so it doesnt for an edge to itself
        temp = copy.deepcopy(nodes)
        temp.remove(node)
        for i in range(random.randint(1,3)): #random number of connections for each node, at least 1 , at most 3
            randomNode = random.choice(temp) # gets random node for destination
            temp.remove(randomNode) # removes the random node from current list, so it doesnt pick it again
            pygame.draw.line(window, (255,255,255), node, randomNode) # draws line from one node to another
            g.addEdge(nodes.index(node), nodes.index(randomNode), getLength(node, randomNode)) # add edge to graph for algorithm
    pygame.display.update()

# gets the length fo the edge, in this case its just the length of the line
def getLength(tup1, tup2):
    return math.sqrt(math.pow(tup1[0] - tup2[0], 2) + math.pow(tup1[1] - tup2[1], 2))

def main():
    window = pygame.display.set_mode((WIDTH,HEIGHT))
    #clock rate
    clock = pygame.time.Clock()
    run = True
    while run:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                run = False
                pygame.quit()
                quit()
        # restarts the screen
        window.fill((0,0,0))
        # draws nodes
        drawNodes(window)
        # makes a graph for the algo
        g = Graph(8)
        # draws random edges and adds edges to graph
        drawEdges(window, g)
        minimum = g.KruskalMST() # gets a list of connectsion of minimum [(src, dst), (src, dst)]
        # draws the minim
        drawMinimum(window, minimum)
        clock.tick(0.5)

# draws the minimum spanning tree with green lines
def drawMinimum(window, mini):
    clock = pygame.time.Clock()
    for conn in mini:
        pygame.draw.line(window, (0,255,0), conn[0], conn[1], 2)
        pygame.display.update()
        #pygame.time.wait(500)
        # so even while its doing this, can exit
        # if not, it takes a while for the user to exit lmao
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                run = False
                pygame.quit()
                quit()
        clock.tick(1)

if __name__ == '__main__':
    main()
