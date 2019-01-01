package Algo;

import Geom.Point3D;
import Maps.Convert;
import Robot.Play;
import types.Game;
import GUI.ImageBoard;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Coords.MyCoords;
public class path {
	private Game game;
	private ImageBoard Image;
	private MyCoords m;
	private Convert c;
	private ArrayList<Point3D> box;
	private ArrayList<adj> ad;
	public BufferedImage myImage;
	public path(Game _game, ImageBoard _Image) {
		this.game=_game;
		this.Image=_Image;
		try {
			myImage = ImageIO.read(new File("image.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		this.m=new MyCoords();
		this.box=new ArrayList<Point3D>();
		this.ad=new ArrayList<adj>();
		this.c=new Convert();
	}
	public void shortPath() {
		Point3D source=new Point3D(c.conToPix(game.getPlayerP().getP(),myImage.getWidth(), myImage.getHeight()));
		Point3D target=new Point3D(c.conToPix(game.closestF().getP(),myImage.getWidth(), myImage.getHeight()));
//			box.add(source);
//			for(int i=0;i<game.getBox().size();i++) {
//				//Point3D temp=new Point3D(m.add(game.getBox().get(i).getlD(), new Point3D(1,1,0)));
//				Point3D temp=c.conToPix(m.add(game.getBox().get(i).getlD(), new Point3D(1,1,0)), myImage.getWidth(), myImage.getHeight());
//				box.add(temp);
//				//Point3D temp1=new Point3D(m.add(game.getBox().get(i).getLu(), new Point3D(1,1,0)));
//				Point3D temp1=c.conToPix(m.add(game.getBox().get(i).getLu(), new Point3D(1,1,0)), myImage.getWidth(), myImage.getHeight());
//				temp1=c.conToPix(temp,this.myImage.getWidth(),this.myImage.getHeight());
//				box.add(temp1);
//				//Point3D temp2=new Point3D(m.add(game.getBox().get(i).getrU(), new Point3D(1,1,0)));
//				Point3D temp2=c.conToPix(m.add(game.getBox().get(i).getrU(), new Point3D(1,1,0)), myImage.getWidth(), myImage.getHeight());
//				temp2=c.conToPix(temp,this.myImage.getWidth(),this.myImage.getHeight());
//				box.add(temp2);
//				//Point3D temp3=new Point3D(m.add(game.getBox().get(i).getrD(), new Point3D(1,1,0)));
//				Point3D temp3=c.conToPix(m.add(game.getBox().get(i).getrD(), new Point3D(1,1,0)), myImage.getWidth(), myImage.getHeight());
//				temp3=c.conToPix(temp,this.myImage.getWidth(),this.myImage.getHeight());
//				box.add(temp3);
//			}
		box.add(source);
		for(int i=0;i<game.getBox().size();i++) {

			Point3D temp1=new Point3D(c.conToPix(game.getBox().get(i).getlD(), myImage.getWidth(), myImage.getHeight()));
			Point3D temp2=new Point3D(c.conToPix(game.getBox().get(i).getLu(), myImage.getWidth(), myImage.getHeight()));
			Point3D temp3=new Point3D(c.conToPix(game.getBox().get(i).getrU(), myImage.getWidth(), myImage.getHeight()));
			Point3D temp4=new Point3D(c.conToPix(game.getBox().get(i).getrD(), myImage.getWidth(), myImage.getHeight()));
			box.add(temp1);
			box.add(temp2);
			box.add(temp3);
			box.add(temp4);
		}
		box.add(target);
		for(int i=0;i<box.size()-1;i++) {
			adj temp=new adj();
			temp.setSource(box.get(i));
			ad.add(temp);
			for(int j=0;j<box.size();j++) {
				Line l=new Line(temp.getSource(),box.get(j));
				for (int k = 0; k < game.getBox().size(); k++) {
					double y =l.valOfY(c.conToPix(game.getBox().get(k).getLu(), myImage.getWidth(), myImage.getHeight()).x());
					if ((y>c.conToPix(game.getBox().get(k).getlD(), myImage.getWidth(), myImage.getHeight()).y())&&(y<c.conToPix(game.getBox().get(k).getLu(), myImage.getWidth(), myImage.getHeight()).y())) {
						y=l.valOfY(c.conToPix(game.getBox().get(k).getrU(), myImage.getWidth(), myImage.getHeight()).x());
						if((y>c.conToPix(game.getBox().get(k).getrD(), myImage.getWidth(), myImage.getHeight()).y())&&(y<c.conToPix(game.getBox().get(k).getrU(), myImage.getWidth(), myImage.getHeight()).y())) {
							double x = l.valOfX(c.conToPix(game.getBox().get(k).getlD(), myImage.getWidth(), myImage.getHeight()).x());
							if ((x>c.conToPix(game.getBox().get(k).getrD(), myImage.getWidth(), myImage.getHeight()).x())&&(x<c.conToPix(game.getBox().get(k).getrU(), myImage.getWidth(), myImage.getHeight()).x())) {
								x=l.valOfX(c.conToPix(game.getBox().get(k).getLu(), myImage.getWidth(), myImage.getHeight()).x());
								if ((x>c.conToPix(game.getBox().get(k).getrU(), myImage.getWidth(), myImage.getHeight()).x())&&(x<c.conToPix(game.getBox().get(k).getLu(), myImage.getWidth(), myImage.getHeight()).x())) {
									temp.getAd().add(box.get(j));
								}
							}
						}
					}
				}
			}
			
		}




	}
	public static void main(String[] args) {
		String file_name = "data/Ex4_OOP_example5.csv";
		BufferedImage myImage = null;
		try {
			 myImage = ImageIO.read(new File("image.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		Convert c = new Convert();
		Point3D p1 = c.pixToCo(new Point3D(762,60), myImage.getWidth(),  myImage.getHeight());
		System.out.println(p1);
		Play play1 = new Play(file_name);
		play1.setInitLocation(p1.y(),p1.x());
		Game g = new Game(play1);
		ImageBoard im= new ImageBoard();
		path p = new path(g, im);
		p.shortPath();
		for (int i = 0; i < p.ad.size(); i++) {
			System.out.print("source : "+p.ad.get(i).getSource());
			for (int j = 0; j <p.ad.get(i).getAd().size(); j++) {
				System.out.print("path : "+p.ad.get(i).getAd().get(j));
			}
			System.out.println();
		}
		
	}
	
}
