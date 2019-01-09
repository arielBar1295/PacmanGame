package Algorithm;

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
/**
 * This class responsible for calculating the shortest path for the player to the next fruit.
 * @author ariel and moshe
 *
 */
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
/**
 * This function calculates the shortest path for the pacman to a specific fruit,using dijkstra.
 * @return the minimum distance after calculating all the possible distances to each fruit.
 */
	public distance shortPath() { 
		
		double disToAdd=3.4;
		distance Dis=new distance();
		//if there are no box ,meaning no obstacles,go to the closest fruit.
		if (game.getBox().size()==0) {
			Dis.getPath().add(c.conToPix(game.getPlayerP().getP(), this.width, this.height));
			Dis.getPath().add(c.conToPix(game.closestF().getP(), this.width, this.height));
		}
		//there are boxes.
		else {
			//running over all the existing fruits.
		for (int k = 0; k <game.getFruit().size() ; k++)
		{
			int counterId=0;
			//converting
			Point3D source=new Point3D(c.conToPix(game.getPlayerP().getP(), this.width, this.height));
			//creating a new object.
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
			//running over all the elements in the queue ,using BFS.
			//pulling from the queue the specific adj,and check whose neighbors .
			while(!(queue.isEmpty())){
				Adj=new adj(queue.poll());
				source=new Point3D(Adj.getSource());
				//running over the box ,drawing a line between the our source node to each edge of every box.
				
				for(int i=0;i<game.getBox().size();i++) {
					//*****set line between source to all the vertex box and the fruit *****
					//saving all the line in the arraylist ,checking if exists intersections.
					ArrayList<Line2D> aLine = new ArrayList<>();
					Point3D temp=new Point3D(c.conToPix(game.getBox().get(i).getlD(), this.width, this.height));
					temp.add(new Point3D(disToAdd,-disToAdd,0));
					Line2D l = new Line2D.Double(source.x(), source.y(), temp.x(), temp.y());		
					aLine.add(l);
					Point3D temp1=new Point3D(c.conToPix(game.getBox().get(i).getLu(),  this.width, this.height));
					temp1.add(new Point3D(disToAdd, disToAdd));
					Line2D l2=new Line2D.Double(source.x(), source.y(), temp1.x(), temp1.y());
					aLine.add(l2);
					Point3D temp2=new Point3D(c.conToPix(game.getBox().get(i).getrD(), this.width, this.height));
					temp2.add(new Point3D(-disToAdd, -disToAdd));
					Line2D l3=new Line2D.Double(source.x(), source.y(), temp2.x(), temp2.y());
					aLine.add(l3);
					Point3D temp3=new Point3D(c.conToPix(game.getBox().get(i).getrU(), this.width, this.height));
					temp3.add(new Point3D(-disToAdd, disToAdd));
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
							//if there is an intersection ,surly not a neighbor
							if(line.intersects(r1)) {
								isadj=false;
							}
						}
						//after checking all the possible cuts , if there are no intersections ,means this node is a neighbor.
						if(isadj) {
							boolean isin=true;
							boolean istargat=false;
							//if this neighbor is already neighbor of another node ,it can be added ,unless its the specific target.
							for (int j = 0; j < ad.size(); j++) {
								if(ad.get(j).getSource().x()==line.getX2()&&ad.get(j).getSource().y()==line.getY2()) {
									isin=false;
									if (ad.get(j).getSource().x()==fruit.getSource().x()&&ad.get(j).getSource().y()==fruit.getSource().y()) {
										istargat=true;
									}
								}

							}
							//adding the neighbor 
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
			// calling the dijkstra after finishing builiding the grapgh.
			 Dis= dijkstra(g,ad);
			 dis.add(Dis);
			 ad.removeAll(ad);

		}
		int minDistance=min(dis);//return the index of the minimum distance
		return (dis.get(minDistance));
		}
		return Dis;
	}
	/**
	 * the function searching for the object which holding the minimum distance
	 * @param dis2 is an array list of dis.
	 * @return the object with the minimum distance
	 */
	private int min(ArrayList<distance> dis2) {
		int min = 0;
		for (int i = 0; i < dis2.size(); i++) {
			if (dis.get(min).getDis()>dis.get(i).getDis())
				min=i;
		}
		return min;
	}
	/**
	 * the function building the graph in order to call the dijkstra graph
	 * @param g is the temp graph.
	 * @param ad is the ArrayList of the adj elements.
	 * @return a distance object including the the path by dijkstra.
	 */
	public distance dijkstra(graph g, ArrayList<adj>ad) {
		String source=Integer.toString(ad.get(0).getId());
		String target=Integer.toString(ad.get(1).getId());
		Graph G = new Graph(); 
		G.clear_meta_data();
		//adding the source
		G.add(new Node(source));
		for(int i=2;i<ad.size();i++) {
			Node d = new Node(Integer.toString(ad.get(i).getId()));
			G.add(d);
		}
		//adding the target.
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

		//creating distance
		Node b = G.getNodeByName(target);
		//building the  arraylist which holds the path.
        ArrayList<String> shortestPath = b.getPath();
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
	/**
	 * the function gets an array list of adj object and an id and find the the point of the specific object.
	 * @param ad is the arraylist
	 * @param id the id of the object
	 * @return the point of the specific id
	 */
	private Point3D getPoint(ArrayList<adj>ad,int id){
		for (adj a:ad) {
			if(a.getId()==id) {
				return new Point3D(a.getSource());
			}
		}
		return new Point3D(0,0,0);
	}


}
