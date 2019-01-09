# Ex4
<h1><span style="text-decoration: underline;"><strong>Packman Game</strong></span></h1>
<p>&copy;&nbsp;Wrriten by Ariel Bar and Moshe Elhadad.</p>
<p>In the project we started developing an infrastructure in order to represent a geographic information in a graphic tools ,in particular we created a pacman game .</p>
<p>&nbsp;In the project there are several packages:</p>
<h2>Geom:</h2>
<p>a package of geometric information including points , paths ,lines ,circles , squares.</p>
<h2>Coords:</h2>
<p>a basic coordinate system converter, using the following class Mycoords: including the following operation:</p>
<ol>
<li>The 3D vector between two lat,lon, alt points</li>
<li>Adding a 3D vector in meters to a global point.</li>
<li>convert a 3D vector from meters to polar coordinate</li>
</ol>
<h2>Algorithm:</h2>
<h3><strong>* Path:</strong></h3>
<p>This class responsible for calculating the shortest path for the pacman to a specific target-fruit.</p>
<p>the class will return the an object including the path tha player has to go in oreder to get to the fruit.</p>
<h2>Type:</h2>
<p><strong>*Fruits</strong>-the class represent a single fruit which the pacman can eat , &nbsp;each fruit holds its own data such as:id ,weight,coordinates.</p>
<p>*<strong>Box-&nbsp;</strong>the class represent the box on the game board ,</p>
<p>Obstacles for the pacman, each box holds its own data .(coordinates,id).</p>
<p><strong>*ghost-</strong>this class represent a ghost , the meaning of the ghost is to chase&nbsp;the pacman .</p>
<p><strong>*Packmans</strong>-the class represents a single packman which can eat fruits.each pacman holds its own information such as: id,radiues of eat , speed , coordinates.</p>
<p><strong>*Game</strong>-the game represents a complete game including a collection of packmans box,ghost,fruits.</p>
<h2>Maps:</h2>
<p><strong>*Convert</strong>- converting from pixels to coordinates and the other way around</p>
<h2>GUI</h2>
<p><strong>*ImageBoard</strong>- the calss responsible for the image background and for the game.</p>
<p><strong>*gameboard</strong>-the class responsible for the main frame ,creating the menu , using the ImageBackground for drawing the elements,the map.</p>
<h2>SQL:</h2>
<p>&nbsp;<strong>*readSQL</strong>- this class calculating the average by taking data from the data base about all the 9 games.in addition calculating our results.</p>
<h2>How to play?</h2>
<p>There are several options:</p>
<p>First you choose which of the 9 games you want to play ,by loading a csv file.</p>
<p>After you have all the elements on the board game you have 2 options:</p>
<p>1.push "algo" and the pacman will go by it self .</p>
<p>2.push "add player" in order to locate the pacman ,then push "auto"</p>
<p>and start palying by clicking on the game board and the pacman will move to the direction of your click</p>
<p>After running ,you can clear the game board and start playing again!</p>
<p><span style="text-decoration: underline;"><strong>example game:</strong></span></p>
<p>the blue circle represents the player , the green circles represent the fruits,the red circles represents the ghost and the yellow represents the pacmans.</p>
<p><span style="text-decoration: underline;"><strong><img src="https://github.com/moshelh/Ex4/blob/master/runningExample.PNG" alt="" /></strong></span></p>
<p><strong>Class diagram:</strong></p>
<p><strong><img src="https://github.com/moshelh/Ex4/blob/master/classdiagram.jpg" alt="" /></strong></p>
