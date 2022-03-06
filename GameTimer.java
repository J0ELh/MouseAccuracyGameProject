/*
 * @author Joel Hempel
 * @since March 5th 2022
 */
public class GameTimer implements Runnable{
	private double time;
	
	public GameTimer(double time) {
		if (time < 0) {
			throw new IllegalArgumentException("time must be positive");
		}
		this.time = time;
	}
	
	public GameTimer(int time) {
		this((double)time);
	}
	
	public Boolean decrease() {
		time--;
		if (time <= 0) {
			return true;
		}
		return false;
	}
	
	public void setTimer(double time) {
		if (time > 0) {
			this.time = time;
		}
		else {
			throw new IllegalArgumentException("Invalid timer");
			
		}
	}
	
	public void setTimer(int time) {
		if (time > 0) {
			this.time = (double)time;
		}
		else {
			throw new IllegalArgumentException("Invalid timer");
			
		}
	}
	
	public double getTime() {
		return time;
	}
	
	@Override
	public void run(){
		while (time > 0) {
			time--;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
