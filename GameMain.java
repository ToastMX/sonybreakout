public class GameMain {

	public static void main(String[] args) {
		
		Game player1 = new Game("Conny");
		AdminFrame Admin = new AdminFrame(player1);
		player1.Thread.start();
	}
}
