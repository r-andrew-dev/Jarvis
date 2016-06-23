package utils;

public class Test2 {

	public static void main(String[] args) {
		final int numAttempts = 10;
		final SimpleTask task = new SimpleTask();
		for(int attempt = 0;attempt<numAttempts;attempt++) {
			task.run();
			double duration = task.elapsedTimeMicros();
			System.out.println("Attempt "+attempt+", Duration = "+duration);
		}

	}
	
	public static class SimpleTask {
		private long elapsedTimeMicros = 0L;
		public final long elapsedTimeMicros() {
			return this.elapsedTimeMicros;
		}
		
		public void run() {
			long sum = 0L;
			final long start = System.nanoTime();
			for(int ix = 0; ix<10000000;ix++) {
				sum += ix;
			}
			//System.out.println("Sum: " + sum);
			final long end = System.nanoTime();
			this.elapsedTimeMicros = (end-start)/1000L;
		}
	}

}
