package kristina.trifunovic.exception;

public class MyException extends Exception {
	private static final long serialVersionUID = 9161071862919514542L;

	public MyException() {
		super();
	}

	public MyException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyException(String message) {
		super(message);
	}


}
