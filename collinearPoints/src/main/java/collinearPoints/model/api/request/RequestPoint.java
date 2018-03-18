package collinearPoints.model.api.request;

import javax.validation.constraints.NotNull;

public class RequestPoint {
	@NotNull(message="x coordinate must be not null")
    private Integer x;
	
	@NotNull(message="y coordinate must be not null")
    private Integer y;

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}
}
