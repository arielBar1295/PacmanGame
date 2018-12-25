package types;

import java.util.ArrayList;

import Geom.Point3D;
import Robot.Play;



public class Game {
	private ArrayList<Packman> packman;
	private ArrayList<Fruit> fruit;
	private ArrayList<Box> box;
	private ArrayList<Ghost> ghost;
	private Play play1;
	private Packman playerP;
	public Game(Play _play1) {
		this.play1=_play1;
		this.box=new ArrayList<Box>();
		this.packman=new ArrayList<Packman>();
		this.ghost=new ArrayList<Ghost>();
		this.fruit= new ArrayList<Fruit>();
		this.playerP=new Packman();
		init();
	}
	public Packman getPlayerP() {
		return playerP;
	}
	public void setPlayerP(Packman playerP) {
		this.playerP = playerP;
	}
	private void init() {
		ArrayList<String> board_data= play1.getBoard();
		for(int i=0;i<board_data.size();i++) {
			String[] s=board_data.get(i).split(",");
			if(s[0].equals("M")){
				int id=Integer.parseInt(s[1]);
				double speed=Double.parseDouble(s[5]);
				double x=Double.parseDouble(s[3]);
				double y=Double.parseDouble(s[2]);
				Point3D p=new Point3D(x,y);
				double raduis=Double.parseDouble(s[6]);
				Packman pac=new Packman(id,speed,p,raduis);
				this.setPlayerP(pac);
			}
			if(s[0].equals("P")) {
				int id=Integer.parseInt(s[1]);
				double speed=Double.parseDouble(s[5]);
				double x=Double.parseDouble(s[3]);
				double y=Double.parseDouble(s[2]);
				Point3D p=new Point3D(x,y);
				double raduis=Double.parseDouble(s[6]);
				Packman pac=new Packman(id,speed,p,raduis);
				packman.add(pac);
			}
			if(s[0].equals("F")) {
				int id=Integer.parseInt(s[1]);
				double weight=Double.parseDouble(s[5]);
				double x=Double.parseDouble(s[3]);
				double y=Double.parseDouble(s[2]);
				Point3D p=new Point3D(x,y);
				Fruit f=new Fruit(id,weight,p);
				fruit.add(f);

			}
			if(s[0].equals("G")) {
				int id=Integer.parseInt(s[1]);
				double speed=Double.parseDouble(s[5]);
				double x=Double.parseDouble(s[3]);
				double y=Double.parseDouble(s[2]);
				Point3D p=new Point3D(x,y);
				double raduis=Double.parseDouble(s[6]);
				Ghost g=new Ghost(id,speed,p,raduis);
				ghost.add(g);
			}
			if(s[0].equals("B")) {
				int id=Integer.parseInt(s[1]);

				double x=Double.parseDouble(s[3]);
				double y=Double.parseDouble(s[2]);
				Point3D p=new Point3D(x,y);
				double x1=Double.parseDouble(s[6]);
				double y1=Double.parseDouble(s[5]);
				Point3D p1=new Point3D(x1,y1);
				Box b=new Box(id,p,p1);
				box.add(b);
			}
		}
	}
	public ArrayList<Packman> getPackman() {
		return packman;
	}
	public void setPackman(ArrayList<Packman> packman) {
		this.packman = packman;
	}
	public ArrayList<Fruit> getFruit() {
		return fruit;
	}
	public void setFruit(ArrayList<Fruit> fruit) {
		this.fruit = fruit;
	}
	public ArrayList<Box> getBox() {
		return box;
	}
	public void setBox(ArrayList<Box> box) {
		this.box = box;
	}
	public ArrayList<Ghost> getGhost() {
		return ghost;
	}
	public void setGhost(ArrayList<Ghost> ghost) {
		this.ghost = ghost;
	}
}
