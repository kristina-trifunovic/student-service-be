package kristina.trifunovic.exception;

public class EntityExistsException extends MyException {
	private static final long serialVersionUID = 518134314295819071L;
	
	private final Object entity;

	public EntityExistsException(String message, Object entity) {
		super(message);
		this.entity = entity;
	}
	
	public Object getEntity() {
		return entity;
	}
	

}
