package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Coords.MyCoords;
import Geom.Point3D;
import Maps.Convert;
import Robot.Play;
import types.Game;

/**
 * This class represents the image background, responsible for the game 
 * @author moshe and ariel
 *
 */
public class ImageBoard extends JPanel implements MouseListener {
	public BufferedImage myImage;


	private Play play1 ;
	private Convert c;
	private Game game;
	private boolean flag=false;
	private String player="";
	ArrayList<String> board_data;
	private boolean playerPrint=false;
	public boolean flag3=false;
	private Point3D pointclicked;
	private MyCoords m;
	public int autoFlag=0;
	private moveThread m1=null;
	public int counterAzimut=0;


	public boolean threadFlag=false;
	// Constructor 
	public ImageBoard() 
	{
		try {
			myImage = ImageIO.read(new File("image.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		this.c=new Convert();
		this.addMouseListener(this); 
		this.m=new MyCoords();
	}
	public void paint (Graphics g) {
		//****print the image****
		int w = this.getWidth();
		int h = this.getHeight();
		g.drawImage(myImage, 0, 0, w, h, this);	
		if (this.flag) {
			//paint the packman ,box ,ghost  and fruit 
			for(int i=0;i<game.getPackman().size();i++) {
				int r = 20;
				Point3D pX=c.conToPix(game.getPackman().get(i).getP(), this.getWidth(), this.getHeight());
				double x =pX.x() - (r / 2);
				double y = pX.y() - (r / 2);
				Color co = new Color(0.0f, 0.3f, 1.0f);	
				g.setColor(co.ORANGE);
				g.fillOval((int)x, (int)y, r, r);
			}
			for(int i=0;i<game.getFruit().size();i++) {
				int r = 10;
				Point3D pX=c.conToPix(game.getFruit().get(i).getP(), this.getWidth(), this.getHeight());
				double x = pX.x() - (r / 2);
				double y = pX.y() - (r / 2);
				Color co = new Color(0.0f, 0.3f, 1.0f);
				g.setColor(co.GREEN);
				g.fillOval((int)x, (int)y, r, r);	

			}
			for(int i=0;i<game.getGhost().size();i++) {
				int r = 25;
				Point3D pX=c.conToPix(game.getGhost().get(i).getP(), this.getWidth(), this.getHeight());
				double x =pX.x() - (r / 2);
				double y = pX.y() - (r / 2);
				Color co = new Color(0.0f, 0.3f, 1.0f);	
				g.setColor(co.RED);
				g.fillOval((int)x, (int)y, r, r);
			}
			for(int i=0;i<game.getBox().size();i++) {
				
				Point3D p=c.conToPix(game.getBox().get(i).getrU(), this.getWidth(), this.getHeight());
				Point3D p1=c.conToPix(game.getBox().get(i).getlD(), this.getWidth(), this.getHeight());
				int wq=p1.ix()-p.ix();
				int hq=Math.abs(p1.iy()-p.iy());
				Color co = new Color(0.0f, 0.3f, 1.0f);	
				g.setColor(co.black);
				Rectangle r1=new Rectangle(p.ix(), p1.iy(), wq, hq);
				g.fillRect(r1.x,r1.y,r1.width,r1.height);


			}
			if(playerPrint) {
				//print the player
				int r = 35;
				Point3D pX=c.conToPix(game.getPlayerP().getP(), this.getWidth(), this.getHeight());
				double x =pX.x() - (r / 2);
				double y = pX.y() - (r / 2);
				Color co = new Color(0.0f, 0.3f, 1.0f);	
				g.setColor(co.blue);
				g.fillOval((int)x, (int)y, r, r);
			}
		}
	}
	/**
	 * this function load the game
	 * @param file_name the csv to load
	 */
	public void addFile(String file_name) {
		play1 = new Play(file_name);
		game=new Game(play1);
		this.flag=true;
		repaint();

	}
	/**
	 * this function run the game step by step
	 */
	public void runStep() {
		play1.setInitLocation(game.getPlayerP().getP().y(),game.getPlayerP().getP().x());
		play1.setIDs(307967992,313383259);
		play1.start();
		double [] e=m.azimuth_elevation_dist(game.getPlayerP().getP(),pointclicked);
		play1.rotate(e[0]);
		game.update(play1);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Point3D p=new Point3D(x,y);
		this.pointclicked=c.pixToCo(p, this.getWidth(), this.getHeight());
		counterAzimut++;
		if (this.player.equals("player")) {
			Point3D pointPlayer=c.pixToCo(p, this.getWidth(), this.getHeight());
			this.play1.setInitLocation(pointPlayer.x(), pointPlayer.y());
			this.board_data=this.play1.getBoard();
			String [] s=this.board_data.get(0).split(",");
			this.game.getPlayerP().setP(pointPlayer);
			this.game.getPlayerP().setId(Integer.parseInt(s[1]));
			this.game.getPlayerP().setSpeed(Double.parseDouble(s[5]));
			this.game.getPlayerP().setRadius(Double.parseDouble(s[6]));
			playerPrint=true;
			this.setPlayer(" ");
			repaint();
		}
		//step by step
		if (autoFlag==1)
		{  
			this.runStep();
		}
		//auto run
		if(autoFlag==2) {
			//for creating only 1 thread.
			if(threadFlag) {
				m1=new moveThread(this,game,play1,e);
				m1.start();
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	public String getPlayer() {
		return player;
	}



	public void setPlayer(String player) {
		this.player = player;
	}
	/**
	 * this function repaint the game.
	 */
	public void update() {
		repaint();
	}
	public Point3D getPointclicked() {
		return pointclicked;
	}
	public void setPointclicked(Point3D pointclicked) {
		this.pointclicked = pointclicked;
	}
	/**
	 *this function start the algorithm 
	 */
	public void startAlgo() {
		//locating the packman on the first fruit.
		Point3D pointpackman=game.getFruit().get(0).getP();
		pointpackman=m.add(pointpackman, new Point3D(1,1,0));
		play1.setInitLocation(pointpackman.y(), pointpackman.x());
		game.update(play1);
		playerPrint=true;
		repaint();
		algoThread t=new algoThread(this,game,play1);
		t.start();

	}
	/**
	 * this function clear the game.
	 */
	public void clear() {
		game.getBox().removeAll(game.getBox());
		game.getFruit().removeAll(game.getFruit());
		game.getGhost().removeAll(game.getGhost());
		game.getPackman().removeAll(game.getPackman());
		game.getPlayerP().setP(new Point3D(0,0));
		//this.play1=new Play();
		counterAzimut=0;
		autoFlag=0;
		repaint();
	}
}
