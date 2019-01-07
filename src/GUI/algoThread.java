package GUI;

import java.awt.event.MouseEvent;

import Algo.distance;
import Algo.path;
import Coords.MyCoords;
import Geom.Point3D;
import Maps.Convert;
import Robot.Play;
import SQL.readSQL;
import types.Fruit;
import types.Game;

public class algoThread extends Thread{
	private ImageBoard image;
	private Game game;
	private Play play;
	private Convert c;
	private MyCoords m;
	
	
	public algoThread(ImageBoard _image, Game _game,Play _play) {
		this.image=_image;
		this.game=_game;
		this.play=_play;
		this.c=new Convert();
		this.m=new MyCoords();	
	}
	private boolean inTheGame(Point3D p) {
		boolean isIn=false;
		for(Fruit f:game.getFruit()) {
			if(f.getP().x()==p.x()&&f.getP().y()==p.y()) {
				isIn=true;
			}
		}
		return isIn;
	}
	private Point3D changeF(Point3D p) {
		double dis=game.getFruit().get(0).getP().distance3D(p);
		Point3D p1=new Point3D(game.getFruit().get(0).getP());
		for(Fruit f:game.getFruit()) {
			 {
				if(f.getP().distance2D(p)<dis) {
				p1=new Point3D(f.getP());
				dis=f.getP().distance2D(p);
				}
			}
		}
		return p1;
		
	}
	public void run() {
		play.setIDs(307967992,313383259);
		play.setInitLocation(game.getPlayerP().getP().y(), game.getPlayerP().getP().x());
		play.start();
		boolean isIn=true;
		double [] e=new double[3];
		path p=new path(game,image.getWidth(),image.getHeight());
		distance d=p.shortPath();
		while(play.isRuning()) {
		
			Point3D fruit=c.pixToCo(d.getPath().get(d.getPath().size()-1),image.getWidth(), image.getHeight());
			
			Point3D po= changeF(fruit);
			fruit= new Point3D(po);
			for(int i=1;i<d.getPath().size()-1&&isIn&&play.isRuning();i++) {
				isIn=inTheGame(fruit);
				Point3D target=c.pixToCo(d.getPath().get(i), image.getWidth(), image.getHeight());
				e=m.azimuth_elevation_dist(game.getPlayerP().getP(),target);
				play.rotate(e[0]);
			
				while(m.distance3d(game.getPlayerP().getP(), target)>1&&isIn&&play.isRuning()) 
				{
				;
				e=m.azimuth_elevation_dist(game.getPlayerP().getP(),target);
				play.rotate(e[0]);
				game.update(play);
				image.update();
				try {
					this.sleep(10);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				isIn=inTheGame(fruit);
				}
			}
			e=m.azimuth_elevation_dist(game.getPlayerP().getP(),fruit);
			play.rotate(e[0]);
			
			isIn=true;
			while(m.distance3d(game.getPlayerP().getP(), fruit)>1&&isIn&&play.isRuning()) {
			e=m.azimuth_elevation_dist(game.getPlayerP().getP(),fruit);
			play.rotate(e[0]);
			game.update(play);
			image.update();
			try {
				this.sleep(10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			isIn=inTheGame(fruit);
			}
			if (!(game.getFruit().isEmpty())&&play.isRuning()) {
			 p=new path(game,image.getWidth(),image.getHeight());
			 d=p.shortPath();
			 isIn=true;
			}
			 System.out.println(play.getStatistics());
			
			
		}
		
		 System.out.println("final : "+play.getStatistics());
		System.out.println("**********sql resulte**********");
		 readSQL sql=new readSQL();
		 sql.getSqlresulte();
	}
	
}
