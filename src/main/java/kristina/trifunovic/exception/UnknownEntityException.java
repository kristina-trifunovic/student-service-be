package kristina.trifunovic.exception;

public class UnknownEntityException extends MyException {
	private static final long serialVersionUID = -4496737329498741179L;
	
	private final Object entity;
	
	public UnknownEntityException(String message, Object entity) {
		super(message);
		this.entity = entity;
	}
	
	public Object getEntity() {
		return entity;
	}

}
