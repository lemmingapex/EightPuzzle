package sw.cos226.eightpuzzle;

public class Node implements Comparable<Node> {

	private int moves = 0;
	private Board board;
	private Node previousNode;

	public Node(int moves, Board board) {
		this(moves, board, null);
	}
	
	public Node(int moves, Board board, Node previousNode) {
		super();
		this.moves = moves;
		this.board = board;
		this.previousNode = previousNode;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public Node getPreviousNode() {
		return previousNode;
	}

	public void setPreviousNode(Node previousNode) {
		this.previousNode = previousNode;
	}

	public Board getBoard() {
		return board;
	}
	
	public Integer priority() {
		return Integer.valueOf((getBoard().manhattan() + getMoves()));
	}
	
	@Override
	public int compareTo(Node o) {
		return priority().compareTo(o.priority());
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("priority   = ");
		b.append(priority());
		b.append("\nmoves      = ");
		b.append(getMoves());
		b.append("\nmanhattan  = ");
		b.append(getBoard().manhattan());
		b.append("\n");
		b.append(getBoard());
		
		return b.toString();
	}	
}
