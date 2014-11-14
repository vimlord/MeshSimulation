MeshSimulation
==============

This program is an engine that allows for simulation of objects that contain point masses. The program also includes code for controllers that can be used to control aspects within the simulation.

##Components


###Physics
The program is designed to follow the laws of physics in the real world. This includes conservation of momentum and the Newtonian laws of motion. The program also includes gravitational and electromagnetic forces, whic occur between every mass in the simulator.

###Meshes
Meshes are sets of vertices, which have velocity, position, mass, and electromagnetic charge. Vertices can be connected to one another by Edges, which keep Vertices within a specified distance of another and act like springs.

###Controllers
Controllers are currently only a framework. Their purpose is to allow a user to interact with the simulation. For example, I slightly modified the program to create an arm that can be controlled via the keyboard.

##How to Use

The use of this code will require knowledge of Java. To find a tutorial, one can use their favorite search engine to look up "How to write in Java", or something similar.

###Simulation

The program consists of several layers (in some ways like a Matryoshka doll) that require an awareness of their existence to be used effectively.


#####World Level

The outermost layer is the World-level classes. This includes the WorldManager class and the World Class, which is contained within the World class. The WorldManager class manages the World class (as the name suggests) by allowing for things like physics to be run. The WorldManager class contains a class called WorldThread, which is used by WorldManager to cycle the physics in the simulation. That way, only a single line of code is required in a tester class to begin the simulation.

#####Mesh Level

The next level is the Mesh level. This consists of the Mesh class, which contains Vertices. Meshes are used to contain and execute physics between the Vertices it owns, although there are some aspects of simulation that it does not control, such as gravity, which is run by the World class.

#####Vertex Level

Vertices, which are created via the Vertex class, serve as the mass of Meshes. They have mass, velocity, position, electromagnetic charge, and Edges. Through the fact that they have momentum, plus the fact that Edges can be used to connect them to other Vertices, they are an important component of building 3D shapes and forms that can be used for various purposes.

#####Edge Level

Edges are objects created by the Edge class that are used to connect Vertices. They are programmed to act like springs, so they will attempt to make sure that they stay within a certain distance from one another. There is a special type of Edge called an AdjustableEdge, which can be given a new length or spring constant on demand.

#####Properties

This isn't a layer per se, but it does deserve mention. Property subclasses are designed to give properties to Meshes, Vertices, and Edges, depending on which Property subclass the specific Property is based off of. For example, the EdgeFriction class, which is a subclass of EdgeProperty, which is a Property subclass designed for Edges, gives Edges friction proportional to the force of the Edge. This will reduce the mechanical energy of a spring system over time.

###GUI

The GUI for the program is broken down into two components: input and output. There isn't much else to say, so their descriptions are below.

#####Controller

The Controller class is the only class really involved in controlling the simulation. This allows for key mapping and naming. This data can later be used to determine which keys are being pressed, and therefore whether or not to execute a program.

#####Visual Interface

The visual interface consists of several components, The GUI class is the central hub for most of the graphics. This class allows for only one interface to be displayed at a given time, which avoids the problems associated with having too many interfaces. The Camera class is an object designed to serve as a frame of reference as well as a drawer. Using angles and polar coordinates, the Camera class plots where points need to go in order to draw various entities in the simulation. It also draws the items. The WorldDrawer is used to control a Camera, which can be moved throughout the environment in the demo. The GUI class uses the WorldDrawer to draw the world.

###Modification

The program can be modified as much as one needs to get the desired result. To ensure the program's safety, I might suggest leaving the core functions alone unless you intend to modify them. However, it is up to you to choose how to use the program.
