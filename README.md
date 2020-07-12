This is **Demo** game project.

First it builds a maze.
You can specify **width**, **heigth** and **stones count** similar to:

``java -jar ./MazeRunner.jar 52 36 500``

If you will not specify these parameters then default values will be used: `width=52, height=36, stones=500`.

After the maze is built then it is trying to find path from **start** to **target**.

There are few game types (defined in `GameType.java`). For each game type results will be printed to the console.