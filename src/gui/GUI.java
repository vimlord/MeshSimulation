/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import gui.camera.Camera;
import gui.control.Controller;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import mesh.edge.AdjustableEdge;
import mesh.vertex.Vertex;
import physics.Coordinate;
import physics.Vector;
import world.WorldManager;

/**
 *
 * @author WHS-D4B1W8
 */
public class GUI extends Applet implements KeyListener, MouseListener, MouseMotionListener{
    
    //The GUI object
    private static GUI gui = new GUI();
    
    //The controller
    private static Controller controller;
    
    //Pixels per radian
    private double PPR = 400;
    
    //Applet stuff
    private Graphics graphics;
    private JFrame frame;
    
    //The camera
    private Camera camera = new Camera(new Coordinate(0,0,0),Math.toRadians(0),0);
    
    ////////////////////////////////////////////////////
    
    private GUI(){
        this(800,600);
    }
    
    private GUI(int width, int height){
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        
        //Creates a JFrame with a title
        frame = new JFrame("Applet");
        //Puts the Tester object into thhe JFrame
	frame.add(this);
        //Sets the size of the applet to be 800 pixels wide  by 600 pixels high
	frame.setSize(width, height);
        //Makes the applet visible
	frame.setVisible(true);
        //Sets the applet so that it can't be resized
        frame.setResizable(false);
        //This will make the program close when the red X in the top right is
        //clicked on
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    public void redraw(){
        repaint();
    }

    public void update(Graphics g){
        Image image = null;
        if (image == null) {
            image = createImage(this.getWidth(), this.getHeight());
            graphics = image.getGraphics();
        }
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,  0,  this.getWidth(),  this.getHeight());
        graphics.setColor(getForeground());
        paint(graphics);
        g.drawImage(image, 0, 0, this);
        
        cycleController();
        
    }
    
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        WorldDrawer.drawWorld(g2, camera);
        g2.drawLine(getCenterX(), 0, getCenterX(), frame.getHeight());
        g2.drawLine(0,getCenterY(),frame.getWidth(),getCenterY());
        
    }
    
    public void setPixelsPerRadian(double ppr){ PPR = ppr; }
    
    public void setDegreesPerRadian(double dpr){
        setPixelsPerRadian(dpr*180.0/Math.PI);
    }
    
    public double getPixelsPerDegree(){
        return PPR * Math.PI/180.0;
    }
    
    public double getPixelsPerRadian() { return PPR; }
    public static GUI getGUI(){ return gui; }
    
    public static void initialize(){
        gui = new GUI(800,600);
    }
    
    public static void initialize(int width, int height){
        gui = new GUI(width, height);
    }
    
    
    //-----------------------
    //Controller
    //-----------------------
    
    public void setController(Controller c) { controller = c; }
    
    
    
    public Controller getController(){ return controller; }
    
    /**
     * This method runs a pre-built controller, and can be replaced later.
     */
    public void cycleController(){
        if(controller.getState(0))
            camera.getPosition().addVector(new Vector(2.5 * Math.pow(10, -2), camera.getXZ(), camera.getY()));
        if(controller.getState(1))
            camera.getPosition().addVector(new Vector(2.5 * Math.pow(10, -2), camera.getXZ() + Math.PI, -camera.getY()));
        if(controller.getState(2))
            camera.getPosition().addVector(new Vector(2.5 * Math.pow(10, -2), camera.getXZ() + Math.PI/2.0, 0));
        if(controller.getState(3))
            camera.getPosition().addVector(new Vector(2.5 * Math.pow(10, -2), camera.getXZ() - Math.PI/2.0, 0));

        if(controller.getState(4))
            camera.setDirection(new Vector(1, camera.getXZ(), camera.getY() + 2.5 * Math.pow(10, -3)));
        if(controller.getState(5))
            camera.setDirection(new Vector(1, camera.getXZ() + 2.5 * Math.pow(10, -3), camera.getY()));
        if(controller.getState(6))
            camera.setDirection(new Vector(1, camera.getXZ(), camera.getY() - 2.5 * Math.pow(10, -3)));
        if(controller.getState(7))
            camera.setDirection(new Vector(1, camera.getXZ() - 2.5 * Math.pow(10, -3), camera.getY()));
        
        
        if(true){
            if(controller.getState(8)){
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(3).getEdges().get(0))).incrementDistance(-.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(4).getEdges().get(0))).incrementDistance(.001);
            }
            if(controller.getState(9)){
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(3).getEdges().get(0))).incrementDistance(.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(4).getEdges().get(0))).incrementDistance(-.001);
            }
            if(controller.getState(10)){
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(1).getEdges().get(0))).incrementDistance(.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(2).getEdges().get(0))).incrementDistance(-.001);
            }
            if(controller.getState(11)){
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(1).getEdges().get(0))).incrementDistance(-.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(2).getEdges().get(0))).incrementDistance(.001);
            }

            if(controller.getState(12)){
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(9).getEdges().get(4))).incrementDistance(-.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getEdges().get(3))).incrementDistance(.001);
            }
            if(controller.getState(13)){
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(7).getEdges().get(2))).incrementDistance(-.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(8).getEdges().get(2))).incrementDistance(-.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(9).getEdges().get(3))).incrementDistance(-.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getEdges().get(2))).incrementDistance(-.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(7).getEdges().get(3))).incrementDistance(.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(8).getEdges().get(1))).incrementDistance(.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(9).getEdges().get(2))).incrementDistance(.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getEdges().get(3))).incrementDistance(.001);
            }
            if(controller.getState(14)){
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(9).getEdges().get(4))).incrementDistance(.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getEdges().get(3))).incrementDistance(-.001);
            }
            if(controller.getState(15)){
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(7).getEdges().get(2))).incrementDistance(.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(8).getEdges().get(2))).incrementDistance(.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(9).getEdges().get(3))).incrementDistance(.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getEdges().get(2))).incrementDistance(.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(7).getEdges().get(3))).incrementDistance(-.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(8).getEdges().get(1))).incrementDistance(-.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(9).getEdges().get(2))).incrementDistance(-.001);
                ((AdjustableEdge) (WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getEdges().get(3))).incrementDistance(-.001);
            }
        }
        
        
        
    }
    
    //-----------------------
    //Keyboard and Mouse
    //-----------------------
    

    public int getCenterX() {
        return frame.getWidth()/2;
    }

    public int getCenterY() {
        return frame.getHeight()/2;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        controller.setState(ke.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        controller.setState(ke.getKeyCode(), false);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        
    }
    
}
