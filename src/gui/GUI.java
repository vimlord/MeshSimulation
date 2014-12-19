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
        frame = new JFrame("MeshSimulation");
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
        g2.drawLine(getCenterX(), getCenterY() - 15, getCenterX(), getCenterY() + 15);
        g2.drawLine(getCenterX() - 15,getCenterY(),getCenterX() + 15,getCenterY());
        
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
    
    public Camera getCamera(){ return camera; }
    
    
    //-----------------------
    //Controller
    //-----------------------
    
    public void setController(Controller c) { controller = c; }
    
    
    
    public Controller getController(){ return controller; }
    
    /**
     * This method runs a pre-built controller, and can be replaced later.
     */
    public void cycleController(){
        
        controller.execute();
        
        
        
        
        
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
