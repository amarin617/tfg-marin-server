package marin.tfg.server.libprovider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaDummyProcess {
	private static final Logger LOGGER = LoggerFactory.getLogger(JavaDummyProcess.class
			.getName());
	private static final int SPIN_COUNT = 5000;
	private static final int NUMTHREADS = 4;

	public static void start() {
		long startWhen = System.nanoTime();
		SpinThread threads[] = new SpinThread[NUMTHREADS];
		for (int i = 0; i < NUMTHREADS; i++) {
			threads[i] = new SpinThread(i);
			threads[i].start();
		}
		for (int i = 0; i < NUMTHREADS; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException ie) {
				LOGGER.error("join " + i + " failed: " + ie);
			}
		}
		long endWhen = System.nanoTime();
		LOGGER.info("Finished in "
				+ ((endWhen - startWhen) / 1000000) + " ms");
	}

	private static class SpinThread extends Thread {
		private int mTid;

		SpinThread(int tid) {
			mTid = tid;
		}

		public void run() {
			int tid = mTid;
			int reps = SPIN_COUNT + tid;
			int ret = 0;

			for (int i = 0; i < reps; i++) {
				for (int j = 0; j < 100000; j++) {
					ret += i * j;
				}
			}
			LOGGER.info(tid + ":" + ret);
		}
	}
}
