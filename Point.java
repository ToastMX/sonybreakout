public class Point {

	int x;
	int y;

	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}

	public String toString(){
		return "x: " + x + ", y: " + y;
	}

	public static void multiMove(int mx, int my, Point... plist){

		for (Point po : plist){
			po.x = po.x + mx;
			po.y = po.y + my;
		}
	}
	public void setPoint(int x, int y){
		this.x = x;
		this.y = y;
	}
}
