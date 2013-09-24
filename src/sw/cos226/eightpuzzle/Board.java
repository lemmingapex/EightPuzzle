package sw.cos226.eightpuzzle;

import java.util.Arrays;
import java.util.Stack;

public class Board {

	private int[][] blocks;
	private int N = 0;
	private int zi = N;
	private int zj = N;

	/**
	 * Construct a board from an N-by-N array of blocks, where blocks[i][j] =
	 * block in row i, column j
	 * 
	 * @param blocks
	 */
	public Board(int[][] blocks) {
		this.blocks = blocks;
		this.N = blocks.length;

		setZeroIndex();		
	}
	
	private void setZeroIndex() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] == 0) {
					zi = i;
					zj = j;
					return;
				}
			}
		}
	}

	public int dimension() {
		return N;
	}

	/**
	 * number of blocks out of place
	 * 
	 * @return
	 */
	public int hamming() {
		int hamming = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] != 0 && blocks[i][j] != ((i * N) + j + 1)) {
					hamming++;
				}
			}
		}
		return hamming;
	}

	/**
	 * Sum of Manhattan distances between blocks and goal
	 * 
	 * @return
	 */
	public int manhattan() {
		int manhattan = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				int block = blocks[i][j];
				if (block != 0) {
					int blockCol = ((block - 1) % N) + 1;
					int blockRow = ((block - 1) / N) + 1;

					manhattan += Math.abs(blockRow - (i + 1));
					manhattan += Math.abs(blockCol - (j + 1));
				}
			}
		}
		return manhattan;
	}

	public boolean isGoal() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] != ((i * N) + j + 1) % (N*N)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * a board obtained by exchanging two adjacent blocks in the same row
	 * 
	 * @return
	 */
	public Board twin() {
		return null;
	}
	
	public Iterable<Board> neighbors() {
		Stack<Board> stack = new Stack<Board>();

		for (int k = -1; k < 2; k = k + 2) {
			if (zi + k >= 0 && zi + k < N) {
				int[][] blocksCopy = copyBlocks();
				blocksCopy[zi][zj] = blocksCopy[zi + k][zj];
				blocksCopy[zi + k][zj] = 0;
				stack.add(new Board(blocksCopy));
			}
		}
		for (int k = -1; k < 2; k = k + 2) {
			if (zj + k >= 0 && zj + k < N) {
				int[][] blocksCopy = copyBlocks();
				blocksCopy[zi][zj] = blocksCopy[zi][zj + k];
				blocksCopy[zi][zj + k] = 0;
				stack.add(new Board(blocksCopy));
			}
		}

		return stack;
	}
	
	private int[][] copyBlocks() {
		int[][] copyBlocks = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				copyBlocks[i][j] = blocks[i][j];
			}
		}
		return copyBlocks;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(dimension() + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", blocks[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + N;
		result = prime * result + Arrays.hashCode(blocks);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (N != other.N)
			return false;
		if (!Arrays.equals(blocks, other.blocks))
			return false;
		return true;
	}
}
