package sw.cos226.eightpuzzle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Stack;

import sw.cos226.utils.MinPQ;

public class Solver {

	static int moves = 0;

	public static Comparator<Board> BOARDCOMPARATOR = new Comparator<Board>() {

		public int compare(Board b1, Board b2) {
			return Integer.valueOf((b1.hamming() + moves)).compareTo(Integer.valueOf((b1.hamming() + moves)));
		}
	};

	MinPQ<Board> minPQ = new MinPQ<Board>(BOARDCOMPARATOR);
	
	/**
	 * Find a solution to the initial {@link Board} (using the A* algorithm)
	 * 
	 * @param initial
	 */
	public Solver(Board initial) {
		minPQ.insert(initial);
	}

	/**
	 * Is the initial board solvable?
	 * 
	 * @return
	 */
	public boolean isSolvable() {
		return true;
	}

	/**
	 * Minimum number of moves to solve initial board
	 * 
	 * @return -1 if no solution
	 */
	public int moves() {
		return 0;
	}

	/**
	 * sequence of boards in a shortest solution
	 * 
	 * @return null if no solution
	 */
	public Iterable<Board> solution() {
		Stack<Board> solution = new Stack<Board>();
		if(isSolvable()) {
			Board temp = minPQ.delMin();
			Board prevTemp = temp;
			solution.add(temp);
			while(!temp.isGoal()) {
				for(Board neighbor : temp.neighbors()) {
					if(!prevTemp.equals(neighbor)) {
						minPQ.insert(neighbor);
					}
				}
				prevTemp = temp;
				temp = minPQ.delMin();
				System.out.print(temp);
				solution.add(temp);
				moves++;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		int[][] blocks = null;
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(args[0])));
			Integer N = Integer.parseInt(in.readLine().trim());
			blocks = new int[N][N];
			String line = in.readLine();
			int i = 0;
			while (line != null) {
				String[] stringLine = line.trim().split("\\s+");
				for (int j = 0; j < N; j++) {
					blocks[i][j] = Integer.parseInt(stringLine[j]);
				}
				i++;
				line = in.readLine();
			}
			in.close();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Board initial = new Board(blocks);
//		System.out.println(initial);
//		System.out.println("Hamming: " + initial.hamming());
//		System.out.println("Manhattan: " + initial.manhattan());
//		System.out.println("IsGoal: " + initial.isGoal());
//		for(Board b : initial.neighbors()) {
//			System.out.println(b);
//			System.out.println("Hamming: " + b.hamming());
//			System.out.println("Manhattan: " + b.manhattan());
//			System.out.println("IsGoal: " + b.isGoal());
//		}

		Solver solver = new Solver(initial);
		if (!solver.isSolvable())
			System.out.println("No solution possible");
		else {
			System.out.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution()) {
				System.out.println(board);
			}
		}
	}
}