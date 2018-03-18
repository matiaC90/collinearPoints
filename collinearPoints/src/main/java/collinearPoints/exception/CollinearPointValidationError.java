package collinearPoints.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CollinearPointValidationError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CollinearPointValidationError() {
		super();
	}

	public CollinearPointValidationError(String message) {
		super(message);
	}

}
