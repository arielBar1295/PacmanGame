package GUI;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The class represents the main frame.
 * creating the menu ,using the ImageBoard for drawing the elements,the map .
 * @author moshe and ariel.
 *
 */

public class gameBoard extends JFrame {
	private File file;
	private ImageBoard ImageBoard;
	 // Constructor 
	public gameBoard() {
		initGUI();	

	}
	private void initGUI() {
		ImageBoard=new ImageBoard();
		this.setContentPane(ImageBoard);
		this.setSize(1433,640);
		this.setVisible(true);
		this.setDefaultCloseOperation(gameBoard.EXIT_ON_CLOSE); 
		Menu add = new Menu("add");
		Menu Run = new Menu ("RUN");
		Menu Clear = new Menu ("Clear");
		MenuItem addCsv = new MenuItem("Add Csv");
		MenuItem addPlayer = new MenuItem("Add Player");
		MenuItem clear = new MenuItem("Clear Game");
		MenuItem step=new MenuItem("step");
		MenuItem auto=new MenuItem("auto");
		MenuItem algo=new MenuItem("algo");
		MenuBar menuBar = new MenuBar();
		this.setMenuBar(menuBar);
		menuBar.add(add);
		menuBar.add(Clear);
		menuBar.add(Run);
		Clear.add(clear);
		add.add(addPlayer);
		add.add(addCsv);
		Run.add(step);
		Run.add(auto);
		Run.add(algo);
		//***ActionListener to add csv to the game***
		class Addcsv implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser= new JFileChooser("./data");
				fileChooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV file", "csv");
				fileChooser.setFileFilter(filter);
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					String s=file.getAbsolutePath();
					ImageBoard.addFile(s);
				}

			}
		}
		//***ActionListener to Clear game***
		class Clear implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				ImageBoard.clear();

			}

		}
		//***ActionListener to run the algorithm***
		class algo implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {

				
				ImageBoard.startAlgo();

			}
		}
		//***ActionListener to run the game step by step ***
		class stepBy implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {

				ImageBoard.autoFlag=1;

			}
		}
		//***ActionListener to set a location to the player***
		class Addplayer implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				ImageBoard.setPlayer("player");
			}
		}
		//***ActionListener to run the game auto ***
		class auto implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				ImageBoard.autoFlag=2;
				ImageBoard.threadFlag=true;
			}
		}
		Clear.addActionListener(new Clear());
		auto.addActionListener(new auto());
		addCsv.addActionListener(new Addcsv());
		addPlayer.addActionListener(new Addplayer());
		step.addActionListener(new stepBy());
		algo.addActionListener(new algo());
	}
	public static void main(String[] args) {
		new gameBoard();
	}
}
