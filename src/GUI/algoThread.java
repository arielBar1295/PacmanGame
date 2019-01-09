package GUI;

import java.awt.event.MouseEvent;

import Algorithm.distance;
import Algorithm.path;
import Coords.MyCoords;
import Geom.Point3D;
import Maps.Convert;
import Robot.Play;
import SQL.readSQL;
import types.Fruit;
import types.Game;
/**
 * this class is extends thread ,responsible for running the algorithm.
 * @author ariel and moshe
 *
 */
public class algoThread extends Thread{
	private ImageBoard image;
	private Game game;
	private Play play;
	private Convert c;
	private MyCoords m;
	
	/**
	 *  A constructor
	 * @param _image is an ImageBoard,for accessing the board data
	 * @param _game is the game
	 * @param _play is the server play
	 */
	public algoThread(ImageBoard _image, Game _game,Play _play) {
		this.image=_image;
		this.game=_game;
		this.play=_play;
		this.c=new Convert();
		this.m=new MyCoords();	
	}
	/*
	 * This functions checks if the specific fruit is still in the game.
	 * @param p is the point of the fruit.
	 */
	private boolean inTheGame(Point3D p) {
		boolean isIn=false;
		for(Fruit f:game.getFruit()) {
			if(f.getP().x()==p.x()&&f.getP().y()==p.y()) {
				isIn=true;
			}
		}
		return isIn;
	}
	/**
	 * this function calculating the exact point of the fruit after converting.
	 * @param p the point of the fruit
	 * @return a new point
	 */
	private Point3D changeF(Point3D p) {
		Point3D p1=new Point3D(0,0,0);
		if(game.getFruit().size()>0) {
		double dis=game.getFruit().get(0).getP().distance3D(p);
		 p1=new Point3D(game.getFruit().get(0).getP());
		for(Fruit f:game.getFruit()) {
			 {
				if(f.getP().distance2D(p)<dis) {
				p1=new Point3D(f.getP());
				dis=f.getP().distance2D(p);
				}
			}
		}
		}
		return p1;
	}
	/**
	 * run function of the thread.
	 */
	public void run() {
		play.setIDs(307967992,313383259);
		//setting the player location.
		play.setInitLocation(game.getPlayerP().getP().y(), game.getPlayerP().getP().x());
		play.start();
		boolean isIn=true;
		double [] e=new double[3];
		//building the path.
		path p=new path(game,image.getWidth(),image.getHeight());
		//d is holding the specific path.
		distance d=p.shortPath();
		while(play.isRuning()) {
			Point3D fruit=c.pixToCo(d.getPath().get(d.getPath().size()-1),image.getWidth(), image.getHeight());
			//change to the accurate coordinates.
			Point3D oFruit= changeF(fruit);
			fruit= new Point3D(oFruit);
			//running over the path (d).
			for(int i=1;i<d.getPath().size()-1&&isIn&&play.isRuning();i++) {
				//check if the fruit is still in the game and had not been eaten.
				isIn=inTheGame(fruit);
				//defining the target 
				Point3D target=c.pixToCo(d.getPath().get(i), image.getWidth(), image.getHeight());
				//calculate the angle.
				e=m.azimuth_elevation_dist(game.getPlayerP().getP(),target);
				play.rotate(e[0]);
				while(m.distance3d(game.getPlayerP().getP(), target)>1&&isIn&&play.isRuning()) 
				{
				e=m.azimuth_elevation_dist(game.getPlayerP().getP(),target);
				play.rotate(e[0]);
				//synchronized the paint and update.
				synchronized (game) {
					game.update(play);
				}
				//repaint
				image.update();
				try {
					this.sleep(10);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//check again if the fruit is still on the board ,or else calculate a new path.
				isIn=inTheGame(fruit);
				}
			}
			e=m.azimuth_elevation_dist(game.getPlayerP().getP(),fruit);
			play.rotate(e[0]);
			isIn=true;
			//going straight to the fruit,target.
			while(m.distance3d(game.getPlayerP().getP(), fruit)>1&&isIn&&play.isRuning()) {
			e=m.azimuth_elevation_dist(game.getPlayerP().getP(),fruit);
			play.rotate(e[0]);
			synchronized (game) {
				game.update(play);
			}
			image.update();
			try {
				this.sleep(10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			isIn=inTheGame(fruit);
			}
			//if there are still fruits and the game is still running ,calculating a new path.
			if (!(game.getFruit().isEmpty())&&play.isRuning()) {
			 p=new path(game,image.getWidth(),image.getHeight());
			 d=p.shortPath();
			 isIn=true;
			}
			 System.out.println(play.getStatistics());
		}
		//printing the final data.
		 System.out.println("final : "+play.getStatistics());
    	 System.out.println("**********sql resulte**********");
		 readSQL sql=new readSQL();
		 sql.getSqlresulte();
	}
	
}
