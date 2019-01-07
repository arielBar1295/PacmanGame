package Algo;

import Geom.Point3D;
import Maps.Convert;
import Robot.Play;
import graph.Graph;
import graph.Graph_Algo;
import graph.Node;
import types.Box;
import types.Game;
import GUI.ImageBoard;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;


import Coords.MyCoords;
public class path {
	private Game game;
	private MyCoords m;
	private Convert c;
	private ArrayList<adj> ad;
	private ArrayList<Boolean> visited;
	private  LinkedList<adj> queue;
	private ArrayList<distance> dis;
	private int width,height;

	public path(Game _game,int _width,int _height) {
		this.game=_game;
		this.width=_width;
		this.height=_height;
		this.m=new MyCoords();
		this.ad=new ArrayList<adj>();
		this.c=new Convert();
		this.visited=new ArrayList<Boolean>();
		this.queue=new LinkedList<adj>();
		this.dis=new ArrayList<>();
	}

	public distance shortPath() { 
		distance Dis=new distance();
		//no box
		if (game.getBox().size()==0) {
			
			Dis.getPath().add(c.conToPix(game.getPlayerP().getP(), this.width, this.height));
			Dis.getPath().add(c.conToPix(game.closestF().getP(), this.width, this.height));
		}else {
		for (int k = 0; k <game.getFruit().size() ; k++)
		{
			int counterId=0;
			Point3D source=new Point3D(c.conToPix(game.getPlayerP().getP(), this.width, this.height));
			adj Adj=new adj();
			Adj.setSource(source);
			Adj.setId(0);
			queue.add(Adj);
			ad.add(Adj);
			int size = game.getBox().size()*4+1;
			Point3D target=new Point3D(c.conToPix(game.getFruit().get(k).getP(), this.width, this.height));
			adj fruit = new adj();
			fruit.setId(size);
			fruit.setSource(target);
			ad.add(fruit);
			graph g = new graph(game.getBox().size()*4+2);
			while(!(queue.isEmpty())){
				Adj=new adj(queue.poll());
				source=new Point3D(Adj.getSource());
				for(int i=0;i<game.getBox().size();i++) {
					//*****set line between source to all the vertex box and the fruit *****
					ArrayList<Line2D> aLine = new ArrayList<>();
					Point3D temp=new Point3D(c.conToPix(game.getBox().get(i).getlD(), this.width, this.height));
					temp.add(new Point3D(3,-3,0));
					Line2D l = new Line2D.Double(source.x(), source.y(), temp.x(), temp.y());		
					aLine.add(l);
					Point3D temp1=new Point3D(c.conToPix(game.getBox().get(i).getLu(),  this.width, this.height));
					temp1.add(new Point3D(3, 3));
					Line2D l2=new Line2D.Double(source.x(), source.y(), temp1.x(), temp1.y());
					aLine.add(l2);
					Point3D temp2=new Point3D(c.conToPix(game.getBox().get(i).getrD(), this.width, this.height));
					temp2.add(new Point3D(-3, -3));
					Line2D l3=new Line2D.Double(source.x(), source.y(), temp2.x(), temp2.y());
					aLine.add(l3);
					Point3D temp3=new Point3D(c.conToPix(game.getBox().get(i).getrU(), this.width, this.height));
					temp3.add(new Point3D(-3, 3));
					Line2D l4=new Line2D.Double(source.x(), source.y(), temp3.x(), temp3.y());
					aLine.add(l4);
					Line2D l5=new Line2D.Double(source.x(), source.y(), target.x(), target.y());
					aLine.add(l5);
					//*****check if exists intersect between each line to the box ****
					for (Line2D line:aLine) {
						boolean isadj=true;
						for (Box b:game.getBox()) {
							Point3D p=c.conToPix(b.getrU(),  this.width, this.height);
							Point3D p1=c.conToPix(b.getlD(),  this.width, this.height);
							int wq=p1.ix()-p.ix();
							int hq=Math.abs(p1.iy()-p.iy());
							Rectangle r1=new Rectangle(p.ix(), p1.iy(), wq, hq);
							if(line.intersects(r1)) {
								isadj=false;
							}
						}
						if(isadj) {
							boolean isin=true;
							boolean istargat=false;
							for (int j = 0; j < ad.size(); j++) {
								if(ad.get(j).getSource().x()==line.getX2()&&ad.get(j).getSource().y()==line.getY2()) {
									isin=false;
									if (ad.get(j).getSource().x()==fruit.getSource().x()&&ad.get(j).getSource().y()==fruit.getSource().y()) {
										istargat=true;
									}
								}

							}
							if(isin) {

								adj t=new adj();
								t.setId(++counterId);
								t.setSource(new Point3D(line.getX2(),line.getY2()));
								ad.add(t);
								queue.add(t);
								g.addEdge(Adj.getId(), t.getId());
							}
							if(istargat) {
								g.addEdge(Adj.getId(), fruit.getId());
							}
						}
					}
				}

			}
			 Dis= dijkstra(g,ad);
			dis.add(Dis);
			ad.removeAll(ad);

		}
		int minDistance=min(dis);//return the index of the minimum distance
		return (dis.get(minDistance));
		}
		return Dis;
	}
	private int min(ArrayList<distance> dis2) {
		int min = 0;
		for (int i = 0; i < dis2.size(); i++) {
			if (dis.get(min).getDis()>dis.get(i).getDis())
				min=i;
		}
		return min;
	}
//********Calculate the path with Dijkstra graph*******
	public distance dijkstra(graph g, ArrayList<adj>ad) {
		String source=Integer.toString(ad.get(0).getId());
		String target=Integer.toString(ad.get(1).getId());
		Graph G = new Graph(); 
		G.clear_meta_data();
		G.add(new Node(source));
		for(int i=2;i<ad.size();i++) {
			Node d = new Node(Integer.toString(ad.get(i).getId()));
			G.add(d);
		}
		G.add(new Node(target));
		for(int i=0;i<g.getAdj().length;i++) {
			if(g.getAdj()[i].size()>0) {
				Point3D s=new Point3D(getPoint(ad,i));
				for (int j = 0; j < g.getAdj()[i].size(); j++) {
				
					Point3D t=new Point3D(getPoint(ad,g.getAdj()[i].get(j)));
					G.addEdge(Integer.toString(i),Integer.toString( g.getAdj()[i].get(j)),s.distance2D(t));
				
				}
			}
		}
		Graph_Algo.dijkstra(G, source);

		//creating distance!!
		Node b = G.getNodeByName(target);
//		System.out.println("***** Graph Demo for OOP_Ex4 *****");
//		System.out.println(b);
//		System.out.println("Dist: "+b.getDist());
		ArrayList<String> shortestPath = b.getPath();
//		for(int i=0;i<shortestPath.size();i++) {
//			System.out.print(","+shortestPath.get(i));
//			
//		}
		
		distance dis= new distance();
		dis.setDis(b.getDist());
		for (int i = 0; i <shortestPath.size() ; i++) {
			Point3D poi =getPoint(ad,Integer.parseInt(shortestPath.get(i)));
			dis.getPath().add(poi);
		}
		Point3D tar=ad.get(1).getSource();
		dis.getPath().add(tar);
		return dis;

	}
	private Point3D getPoint(ArrayList<adj>ad,int id){
		for (adj a:ad) {
			if(a.getId()==id) {
				return new Point3D(a.getSource());
			}
		}
		return new Point3D(0,0,0);
	}


}
