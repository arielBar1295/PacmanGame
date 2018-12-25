package GUI;

import java.awt.Color;
import java.awt.Graphics;
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

public class ImageBoard extends JPanel implements MouseListener {
	public BufferedImage myImage;


	private Play play1 ;
	private Convert c;
	private Game game;
	private boolean flag=false;
	private String player="";
	ArrayList<String> board_data;
	private boolean flag2=false;
	private boolean flag3=false;
	private Point3D pointclicked;
	private MyCoords m;
	public int x=0;

	public ImageBoard() {
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
		int w = this.getWidth();
		int h = this.getHeight();
		g.drawImage(myImage, 0, 0, w, h, this);	
		if (this.flag) {
		
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

				Point3D p=c.conToPix(game.getBox().get(i).getP(), this.getWidth(), this.getHeight());
				Point3D p1=c.conToPix(game.getBox().get(i).getP1(), this.getWidth(), this.getHeight());
				int wq=p1.ix()-p.ix();
				int hq=Math.abs(p1.iy()-p.iy());
				Color co = new Color(0.0f, 0.3f, 1.0f);	
				g.setColor(co.black);
				g.fillRect(p.ix(), p1.iy(), wq,hq);
				//g.drawRect(p.ix(), p.iy(), p1.ix()-p.ix(),p1.iy()-p.iy());
			}
			if(flag2) {
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
	public void addFile(String file_name) {
		play1 = new Play(file_name);
		game=new Game(play1);
		this.flag=true;
		repaint();

	}
	public void runStep() {
	
		if(flag3) {
		play1.start();
		double [] e=m.azimuth_elevation_dist(game.getPlayerP().getP(),pointclicked);
		play1.rotate(e[0]);
		System.out.println(e[0]);
		game=new Game(play1);
		repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Point3D p=new Point3D(x,y);
		if (this.player.equals("player")) {
			Point3D pX=c.pixToCo(p, this.getWidth(), this.getHeight());
			this.play1.setInitLocation(pX.x(), pX.y());
			this.board_data=this.play1.getBoard();
			String [] s=this.board_data.get(0).split(",");
			this.game.getPlayerP().setP(pX);
			this.game.getPlayerP().setId(Integer.parseInt(s[1]));
			this.game.getPlayerP().setSpeed(Double.parseDouble(s[5]));
			this.game.getPlayerP().setRadius(Double.parseDouble(s[6]));
			flag2=true;
			repaint();
		}
		this.player=""; //so the user can set the packman player on the board  only one time.
		System.out.println(this.player.equals("steps"));
		//if (this.player.equals("steps"))
		if (x!=0)
		{
			System.out.println("a");
			this.pointclicked=c.pixToCo(p, this.getWidth(), this.getHeight());
			System.out.println(pointclicked);
			flag3=true;
			
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
	public void update() {
		repaint();
	}
}
