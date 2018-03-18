package collinearPoints.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import collinearPoints.exception.ApiError;
import collinearPoints.exception.CollinearPointValidationError;
import collinearPoints.model.Point;
import collinearPoints.model.api.request.RequestPoint;
import collinearPoints.service.CollinearPointService;

@RestController
public class CollinearPointsApi {

	@Autowired
	private CollinearPointService collinearPointService;

	@PostMapping("/point")
	public ResponseEntity<Point> addPoint(@Valid @RequestBody RequestPoint requestPoint, BindingResult bindingResult)
			throws CollinearPointValidationError {
		if (bindingResult.hasErrors()) {
			List<ObjectError> errors = bindingResult.getAllErrors();
			StringBuilder strBuilder = new StringBuilder();
			for (ObjectError error : errors) {
				strBuilder.append(error.getDefaultMessage()).append("\n");
			}
			String errorMessage = strBuilder.toString();
			throw new CollinearPointValidationError(errorMessage);
		}

		Point insertedPoint = this.collinearPointService.addPoint(new Point(requestPoint.getX(), requestPoint.getY()));
		return new ResponseEntity<Point>(insertedPoint, HttpStatus.CREATED);
	}

	@GetMapping("/space")
	public ResponseEntity<List<Point>> getAllPoints() {
		List<Point> listPoints = this.collinearPointService.getPoints();
		return new ResponseEntity<List<Point>>(listPoints, HttpStatus.OK);
	}

	@DeleteMapping("/space")
	public ResponseEntity<Void> deleteAllPoints() {
		this.collinearPointService.deleteAllPoints();
		return ResponseEntity.status(HttpStatus.OK).build();

	}

	@GetMapping("/lines/{numberPoints}")

	public ResponseEntity<List<Point[]>> getCollinearPoints(@PathVariable("numberPoints") String nPoints)
			throws Exception {

		int	numberPoints = Integer.parseInt(nPoints);
		List<Point[]> listLines = this.collinearPointService.getCollinearPointsFromSpace(numberPoints);
		return new ResponseEntity<List<Point[]>>(listLines, HttpStatus.OK);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> manageApiException(Exception ex) {
		List<String> errors = new ArrayList<String>();
		errors.add(ex.getMessage());

		ApiError apiError = null;
		if (ex instanceof CollinearPointValidationError || ex instanceof NumberFormatException) {
			apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
			return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
		}
		else {
			apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), errors);
			return new ResponseEntity<Object>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
