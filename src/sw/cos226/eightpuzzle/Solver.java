package sw.cos226.eightpuzzle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Solver {
	private PriorityQueue<Node> minPQ = new PriorityQueue<Node>();
	private Node solution = null;
	
	/**
	 * Find a solution to the initial {@link Board} (using the A* algorithm)
	 * 
	 * @param initial
	 */
	public Solver(Board initial) {
		minPQ.add(new Node(0, initial));
		solve();
	}

	/**
	 * Minimum number of moves to solve initial board
	 * 
	 * @return -1 if no solution
	 */
	public int moves() {
		return solution.getMoves();
	}

	/**
	 * The main algorithm to slove the puzzle
	 * 
	 */
	private void solve() {
		if(minPQ.peek().getBoard().isSolvable()) {
			Node temp = minPQ.poll();
			Node prevTemp = temp;
			Board tempBoard = temp.getBoard(); 
			while(!tempBoard.isGoal()) {
				for(Board neighbor : tempBoard.neighbors()) {
					if(!prevTemp.getBoard().equals(neighbor)) {
						minPQ.add(new Node(temp.getMoves() + 1, neighbor, temp));
					}
				}
				prevTemp = temp;
				temp = minPQ.poll();
				tempBoard = temp.getBoard();
				//System.out.println(tempBoard);
			}
			solution = temp;
		}
	}
	
	
	/**
	 * Sequence of boards in a shortest solution
	 * 
	 * @return Empty collection if no solution
	 */
	public Iterable<Board> solution() {
		LinkedList<Board> solutionStack = new LinkedList<Board>();
		Node tsol = solution;
		while(tsol != null && tsol.getBoard() != null) {
			solutionStack.addFirst(tsol.getBoard());
			tsol = tsol.getPreviousNode();
		}
		
		return solutionStack;
	}

	public static void main(String[] args) {
		if (args.length != 1) {
	        System.err.println("Incorrect number of arguments.");
	        System.err.println("Usage: ./eightpuzzle.jar <puzzlefile.txt>");
	        System.exit(1);
		}
		
		int[][] blocks = null;
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(args[0])));
			Integer N = Integer.parseInt(in.readLine().trim());
			blocks = new int[N][N];
			String line = in.readLine();
			int i = 0;
			while (line != null && !line.trim().isEmpty()) {
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

		if (!initial.isSolvable()) {
			System.out.println("No solution possible");
		} else {
			Solver solver = new Solver(initial);
			System.out.println("Minimum number of moves = " + solver.moves());
			for (Board n : solver.solution()) {
				System.out.println(n);
			}
		}
	}
}